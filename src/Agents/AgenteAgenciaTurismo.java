/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Things.Paquete;
import Ventanas.VentanaAgencia;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase Agencia de Turismo, es el agente encargado de representar a una Agencia
 * de Turismo específica.
 */
public class AgenteAgenciaTurismo extends Agent {

    private String nombre;
    private ArrayList<Paquete> ofertasLugar = new ArrayList<>();
    private ArrayList<Paquete> ofertasTransporte = new ArrayList<>();
    private AID mejorLugar;
    private AID mejorTransporte;
    //Descuentos máximos a pedir a una empresa de transporte o lugar.
    private float descuentoTransporte;
    private float descuentoLugar;
    //Descuentos por pago con efectivo o tarjeta.
    private ArrayList<AID> transportes;
    private ArrayList<AID> lugares;
    private float comision;
    private VentanaAgencia myGui;

    @Override
    protected void setup() {
        nombre = this.getLocalName();
        myGui = new VentanaAgencia(this);
        myGui.setVisible(true);

        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Agencia de Turismo");
        sd.setName(nombre);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        //addBehaviour(new ActualizarLugares());
        addBehaviour(new BuscarPaquete());
        addBehaviour(new AsociarServicio());
    }

    //Métodos llamados desde la interfaz
    public void definirAgencia(float dtoTransporte, float dtoLugar, float comision) {
        AID id = new AID(nombre, AID.ISLOCALNAME);
        this.descuentoLugar = dtoLugar / 100;
        this.comision = 1 + (comision / 100);
        this.descuentoTransporte = dtoTransporte / 100;
    }

    public void asignarServicios(ArrayList<AID> transportes, ArrayList<AID> lugares) {
        this.transportes = transportes;
        this.lugares = lugares;
    }

    //Quitar registro de las paginas amarillas
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    private class BuscarPaquete extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String pref = msg.getContent();
                Paquete preferencias;
                preferencias = Paquete.stringToPaquete(pref);
                Paquete prefSemiHeuristica;
                prefSemiHeuristica = Paquete.stringToPaquete(pref);
                prefSemiHeuristica.setPresupuestoMax(preferencias.getPresupuestoMax() * 0.7f);
                //EMPIEZA LA NEGOCIACION CON LUGARES
                ACLMessage cfpLugares = new ACLMessage(ACLMessage.CFP);
                for (AID lugar : lugares) {
                    cfpLugares.addReceiver(lugar);
                }
                cfpLugares.setContent(pref);
                cfpLugares.setConversationId("Busqueda de Lugar");
                myAgent.send(cfpLugares);
                for (AID lugar : lugares) {
                    MessageTemplate mtlugar = MessageTemplate.and(MessageTemplate.MatchConversationId("Busqueda de Lugar"),
                            MessageTemplate.MatchSender(lugar));
                    ACLMessage respuestaLugar = myAgent.receive(mtlugar);
                    if (respuestaLugar != null) {
                        switch (respuestaLugar.getPerformative()) {
                            case ACLMessage.PROPOSE: {
                                // presupuestomax va a tomar como el descuento realizado
                                Paquete paqLugar = Paquete.stringToPaquete(respuestaLugar.getContent());
                                if (descuentoLugar <= paqLugar.getPresupuestoMax()) {
                                    //guarda paquete ordenado en ofertasLugar
                                    paqLugar.setCalidadTransporte(preferencias.getCalidadTransporte());
                                    paqLugar.setHeuristica(prefSemiHeuristica);
                                    ofertasLugar.add(paqLugar);
                                    Collections.sort(ofertasLugar);
                                    Collections.reverse(ofertasLugar);
                                    if (ofertasLugar.indexOf(paqLugar) == 0) {
                                        mejorLugar = lugar;
                                    }
                                } else {
                                    ACLMessage pedirRebaja = respuestaLugar.createReply();
                                    pedirRebaja.setPerformative(ACLMessage.CFP);
                                    pedirRebaja.setContent(respuestaLugar.getContent());
                                    send(pedirRebaja);
                                }
                            }
                            break;
                            case ACLMessage.INFORM: {
                                //El lugar ha descontado lo máximo que puede
                                Paquete paqLugar = Paquete.stringToPaquete(respuestaLugar.getContent());
                                //guarda paquete ordenado en ofertasLugar
                                paqLugar.setCalidadTransporte(preferencias.getCalidadTransporte());
                                paqLugar.setHeuristica(prefSemiHeuristica);
                                ofertasLugar.add(paqLugar);
                                Collections.sort(ofertasLugar);
                                Collections.reverse(ofertasLugar);
                                if (ofertasLugar.indexOf(paqLugar) == 0) {
                                    mejorLugar = lugar;
                                }
                            }
                            break;
                            default:
                                break;
                        }
                    }
                }
                // EMPIEZA LA NEGOCIACION CON TRANSPORTES
                ACLMessage cfpTransportes = new ACLMessage(ACLMessage.CFP);
                for (AID transporte : transportes) {
                    cfpTransportes.addReceiver(transporte);
                }
                cfpTransportes.setContent(pref);
                cfpTransportes.setConversationId("Busqueda de Transportes");
                myAgent.send(cfpTransportes);
                prefSemiHeuristica.setPresupuestoMax(preferencias.getPresupuestoMax() * 0.3f);
                for (AID transporte : transportes) {
                    MessageTemplate mttransporte = MessageTemplate.and(MessageTemplate.MatchConversationId("Busqueda de Transportes"),
                            MessageTemplate.MatchSender(transporte));
                    ACLMessage respuestaTransporte = myAgent.receive(mttransporte);
                    if (respuestaTransporte != null) {
                        switch (respuestaTransporte.getPerformative()) {
                            case ACLMessage.PROPOSE: {
                                // presupuestomax va a tomar como el descuento realizado.
                                Paquete paqTrans = Paquete.stringToPaquete(respuestaTransporte.getContent());
                                if (descuentoTransporte <= paqTrans.getPresupuestoMax()) {
                                    //guarda paquete ordenado en ofertasTransporte
                                    paqTrans.setAlojamiento(preferencias.getAlojamiento());
                                    paqTrans.setHeuristica(prefSemiHeuristica);
                                    ofertasTransporte.add(paqTrans);
                                    Collections.sort(ofertasTransporte);
                                    Collections.reverse(ofertasTransporte);
                                    if (ofertasTransporte.indexOf(paqTrans) == 0) {
                                        mejorTransporte = transporte;
                                    }
                                } else {
                                    ACLMessage pedirRebaja = respuestaTransporte.createReply();
                                    pedirRebaja.setPerformative(ACLMessage.INFORM);
                                    pedirRebaja.setContent(respuestaTransporte.getContent());
                                    send(pedirRebaja);
                                }
                            }
                            break;
                            case ACLMessage.INFORM: {
                                //El transporte ha descontado lo máximo que puede
                                Paquete paqTrans = Paquete.stringToPaquete(respuestaTransporte.getContent());
                                //guarda paquete ordenado en ofertasTransporte
                                paqTrans.setAlojamiento(preferencias.getAlojamiento());
                                paqTrans.setHeuristica(prefSemiHeuristica);
                                ofertasTransporte.add(paqTrans);
                                Collections.sort(ofertasTransporte);
                                Collections.reverse(ofertasTransporte);
                                if (ofertasTransporte.indexOf(paqTrans) == 0) {
                                    mejorTransporte = transporte;
                                }
                            }
                            break;
                            default:
                        }
                    }
                }
                ACLMessage propuesta;
                propuesta = new ACLMessage();
                if (!ofertasLugar.isEmpty() && !ofertasTransporte.isEmpty()) {
                    aceptarPropuestas(mejorLugar, mejorTransporte);
                    Paquete paqueteArmado;
                    paqueteArmado = armarPaquete(ofertasLugar.get(0), ofertasTransporte.get(0), preferencias);
                    propuesta.setPerformative(ACLMessage.PROPOSE);
                    propuesta.setContent(paqueteArmado.toStringForMessage());
                } else {
                    propuesta.setPerformative(ACLMessage.REFUSE);
                }
                propuesta.addReceiver(msg.getSender());
                myAgent.send(propuesta);
            } else {
                block();
            }
        }

        private void aceptarPropuestas(AID mejorLugar, AID mejorTransporte) {
            ACLMessage aceptar = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
            aceptar.addReceiver(mejorLugar);
            aceptar.addReceiver(mejorTransporte);
            myAgent.send(aceptar);
        }

        private Paquete armarPaquete(Paquete lugar, Paquete transporte, Paquete preferencias) {
            Paquete propuesta = new Paquete();
            propuesta.setOrigen(preferencias.getOrigen());
            propuesta.setDestino(preferencias.getDestino());
            propuesta.setPresupuestoMax(preferencias.getPresupuestoMax());
            propuesta.setCantidadPersonas(preferencias.getCantidadPersonas());
            propuesta.setFechaInicialInferior(transporte.getFechaInicialInferior());
            propuesta.setFechaInicialSuperior(transporte.getFechaInicialInferior());
            propuesta.setDuracion(preferencias.getDuracion());
            propuesta.setAlojamiento(lugar.getAlojamiento());
            propuesta.setPonderacion(preferencias.getPonderacionPrecio());
            propuesta.setCalidadTransporte(transporte.getCalidadTransporte());
            propuesta.setPrecio((lugar.getPrecio() * lugar.getPresupuestoMax() + transporte.getPrecio() * transporte.getPresupuestoMax()) * comision);
            return propuesta;
        }
    }

    //Nuevos transportes o lugares que se quieren asociar
    private class AsociarServicio extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String contenido = msg.getContent();
                if (contenido.equals("Transporte")) {
                    transportes.add(msg.getSender());
                } else {
                    lugares.add(msg.getSender());
                }

            }
        }
    }
}
