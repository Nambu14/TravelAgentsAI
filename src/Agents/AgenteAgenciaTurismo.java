/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Things.Paquete;
import Ventanas.VentanaAgencia;
import Ventanas.VentanaMostrarAgencia;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Agencia de Turismo, es el agente encargado de representar a una Agencia
 * de Turismo específica.
 */
public class AgenteAgenciaTurismo extends Agent {

    //Atributos:
    private String nombre;
    //Listas de ofertas recibidas tanto de lugares como de transportes.
    private ArrayList<Paquete> ofertasLugar = new ArrayList<>();
    private ArrayList<Paquete> ofertasTransporte = new ArrayList<>();
    //Variables para almacenar los AID de las mejores ofertas
    private AID mejorLugar;
    private AID mejorTransporte;
    //Descuentos máximos a pedir a una empresa de transporte o lugar.
    private float descuentoTransporte;
    private float descuentoLugar;
    //Listas de transportes y lugares.
    private ArrayList<AID> transportes;
    private ArrayList<AID> lugares;

    int cuentaLugar;
    int cuentaTransporte;
    private float comision;
    private VentanaAgencia myGui;
    private VentanaMostrarAgencia mostrarGui;

    //VAriables usadas en beahaviours
    int step = 0;
    String pref = new String();
    Paquete preferencias = new Paquete();
    Paquete prefSemiHeuristica = new Paquete();
    ACLMessage msg;

    @Override
    protected void setup() {
        nombre = this.getLocalName();
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            //para el caso de la creación de escenarios
            cargarAgencia(args);
            asociarTodosServicios();
            agregarComportamiento();
        } else {
            //para el caso de llamada desde la interfaz    
            myGui = new VentanaAgencia(this);
            myGui.setVisible(true);
        }
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

    public ArrayList<AID> getTransportes() {
        return transportes;
    }

    public ArrayList<AID> getLugares() {
        return lugares;
    }

    //Deregistrar de las paginas amarillas
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    public float getDescuentoTransporte() {
        return descuentoTransporte;
    }

    public float getDescuentoLugar() {
        return descuentoLugar;
    }

    public float getComision() {
        return comision;
    }

    private void mostrarDatos() {
        mostrarGui = new VentanaMostrarAgencia(this);
        mostrarGui.setVisible(true);
    }

    public void agregarComportamiento() {
        addBehaviour(new BuscarPaquete());
        addBehaviour(new MostrarInformacion());
        addBehaviour(new AsociarServicio());
    }

    public void asociarTodosServicios() {
        ArrayList<AID> transportesDF = new ArrayList<>();
        ArrayList<AID> lugaresDF = new ArrayList<>();
        DFAgentDescription[] resultadosTransporte;
        DFAgentDescription[] resultadosLugar;
        //Buscar todos los transportes registrados
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Transporte");
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.addServices(servicio);
        try {
            resultadosTransporte = DFService.search(this, descripcion);
            for (DFAgentDescription resultadosTransporte1 : resultadosTransporte) {
                transportesDF.add(resultadosTransporte1.getName());
            }
        } catch (FIPAException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        //Buscar todos los Lugares registrados 
        ServiceDescription servicio2 = new ServiceDescription();
        servicio2.setType("Lugar");
        DFAgentDescription descripcion2 = new DFAgentDescription();
        descripcion2.addServices(servicio2);
        try {
            resultadosLugar = DFService.search(this, descripcion2);
            for (DFAgentDescription resultadosLugar1 : resultadosLugar) {
                lugaresDF.add(resultadosLugar1.getName());
            }
        } catch (FIPAException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        //Asignar los lugares y transportes encontrados
        asignarServicios(transportesDF, lugaresDF);
    }

    private void cargarAgencia(Object[] args) {

        float argsComision = Integer.parseInt(args[0].toString());
        float argsDtoLugar = Integer.parseInt(args[1].toString());
        float argsDtoT = Integer.parseInt(args[2].toString());
        this.descuentoLugar = argsDtoLugar / 100;
        this.comision = 1 + (argsComision / 100);
        this.descuentoTransporte = argsDtoT / 100;

    }

    private class MostrarInformacion extends CyclicBehaviour {

        private ACLMessage infoM;

        @Override
        public void action() {
            MessageTemplate mtInfo = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            infoM = myAgent.receive(mtInfo);
            if (infoM != null) {
                mostrarDatos();
            }
        }

    }

    private class BuscarPaquete extends Behaviour {

        @Override
        public void action() {

            switch (step) {
                case 0: {
                    //Recibe mensaje del turista
                    MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("Busqueda de Paquete"), MessageTemplate.MatchPerformative(ACLMessage.CFP));
                    msg = myAgent.receive(mt);
                    if (msg != null) {
                        cuentaLugar = 0;
                        cuentaTransporte = 0;
                        pref = msg.getContent();
                        preferencias = Paquete.stringToPaquete(pref);
                        prefSemiHeuristica = Paquete.stringToPaquete(pref);
                        step = 1;
                    } else {
                        block();
                    }
                }
                ;
                break;
                case 1: {
                    // Envía mensaje a todos los lugares.
                    ACLMessage cfpLugares = new ACLMessage(ACLMessage.CFP);
                    for (AID lugar : lugares) {
                        cfpLugares.addReceiver(lugar);
                    }
                    cfpLugares.setContent(pref);
                    cfpLugares.setConversationId("Busqueda de Lugar");
                    myAgent.send(cfpLugares);

                    //envía mensaje a todos los transportes.
                    ACLMessage cfpTransportes = new ACLMessage(ACLMessage.CFP);
                    for (AID transporte : transportes) {
                        cfpTransportes.addReceiver(transporte);
                    }
                    cfpTransportes.setContent(pref);
                    cfpTransportes.setConversationId("Busqueda de Transportes");
                    myAgent.send(cfpTransportes);

                    step = 2;
                }
                ;
                break;
                case 2: {
                    //EMPIEZA LA NEGOCIACION CON LUGARES
                    prefSemiHeuristica.setPresupuestoMax(preferencias.getPresupuestoMax() * 0.7f);
                    MessageTemplate mtlugar = MessageTemplate.MatchConversationId("Busqueda de Lugar");
                    //Recibir respuesta de lugar
                    ACLMessage respuestaLugar = myAgent.receive(mtlugar);
                    if (respuestaLugar != null) {
                        switch (respuestaLugar.getPerformative()) {
                            case ACLMessage.PROPOSE: {
                                // presupuestomax va a ser utilizado temporalmente como variables para almacenar el descuento realizado
                                Paquete paqLugar = Paquete.stringToPaquete(respuestaLugar.getContent());
                                if (descuentoLugar <= paqLugar.getPresupuestoMax()) {
                                    //Si el descuento realizado por la empresa de lugar es satisfactorio
                                    //guarda paquete ordenado en ofertasLugar
                                    //verificar que el descuento sea como máximo 100%
                                    if(paqLugar.getPresupuestoMax()>1)
                                        paqLugar.setPresupuestoMax(1);
                                    paqLugar.setPrecio(paqLugar.getPrecio()*(1-paqLugar.getPresupuestoMax()));
                                    paqLugar.setCalidadTransporte(preferencias.getCalidadTransporte());
                                    paqLugar.setHeuristica(prefSemiHeuristica);
                                    ofertasLugar.add(paqLugar);
                                    Collections.sort(ofertasLugar);
                                    Collections.reverse(ofertasLugar);
                                    if (ofertasLugar.indexOf(paqLugar) == 0) {
                                        mejorLugar = respuestaLugar.getSender();
                                    }
                                    cuentaLugar++;
                                } else {
                                    //Si el descuento realizado no es satisfactorio se pide más descuento
                                    ACLMessage pedirRebaja = respuestaLugar.createReply();
                                    pedirRebaja.setPerformative(ACLMessage.CFP);
                                    pedirRebaja.setContent(respuestaLugar.getContent());
                                    send(pedirRebaja);
                                }
                            }
                            break;
                            case ACLMessage.INFORM: {
                                //En éste punto, el lugar ha descontado lo máximo que puede
                                Paquete paqLugar = Paquete.stringToPaquete(respuestaLugar.getContent());
                                //guarda paquete ordenado en ofertasLugar
                                //verificar que el descuento sea como máximo 100%
                                if(paqLugar.getPresupuestoMax()>1)
                                    paqLugar.setPresupuestoMax(1);
                                paqLugar.setPrecio(paqLugar.getPrecio()*(1-paqLugar.getPresupuestoMax()));
                                paqLugar.setCalidadTransporte(preferencias.getCalidadTransporte());
                                paqLugar.setHeuristica(prefSemiHeuristica);
                                ofertasLugar.add(paqLugar);
                                Collections.sort(ofertasLugar);
                                Collections.reverse(ofertasLugar);
                                if (ofertasLugar.indexOf(paqLugar) == 0) {
                                    mejorLugar = respuestaLugar.getSender();
                                }
                                cuentaLugar++;
                            }
                            break;
                            default:
                                cuentaLugar++;
                                break;
                        }
                    } else {
                        block();
                    }
                    if (cuentaLugar == lugares.size()) {
                        step = 3;
                    }/*else{
                     block();
                     }*/

                }
                ;
                break;
                case 3: {

                    // EMPIEZA LA NEGOCIACION CON TRANSPORTES
                    prefSemiHeuristica.setPresupuestoMax(preferencias.getPresupuestoMax() * 0.3f);
                    MessageTemplate mttransporte = MessageTemplate.MatchConversationId("Busqueda de Transportes");
                    ACLMessage respuestaTransporte = myAgent.receive(mttransporte);
                    if (respuestaTransporte != null) {
                        switch (respuestaTransporte.getPerformative()) {
                            case ACLMessage.PROPOSE: {
                                // presupuestomax va a ser utilizado temporalmente como variables para almacenar el descuento realizado
                                Paquete paqTrans = Paquete.stringToPaquete(respuestaTransporte.getContent());
                                if (descuentoTransporte <= paqTrans.getPresupuestoMax()) {
                                    //Si el descuento realizado es satisfactorio
                                    //guarda paquete ordenado en ofertasTransporte
                                    //verificar que el descuento sea como máximo 100%
                                    if(paqTrans.getPresupuestoMax()>1)
                                        paqTrans.setPresupuestoMax(1);
                                    paqTrans.setPrecio(paqTrans.getPrecio()*(1-paqTrans.getPresupuestoMax()));
                                    paqTrans.setAlojamiento(preferencias.getAlojamiento());
                                    paqTrans.setHeuristica(prefSemiHeuristica);
                                    ofertasTransporte.add(paqTrans);
                                    Collections.sort(ofertasTransporte);
                                    Collections.reverse(ofertasTransporte);
                                    cuentaTransporte++;
                                    if (ofertasTransporte.indexOf(paqTrans) == 0) {
                                        mejorTransporte = respuestaTransporte.getSender();
                                    }
                                } else {
                                    //En caso de que no sea satisfactorio el descuento se solicitan más descuentos.
                                    ACLMessage pedirRebaja = respuestaTransporte.createReply();
                                    pedirRebaja.setPerformative(ACLMessage.CFP);
                                    pedirRebaja.setContent(respuestaTransporte.getContent());
                                    send(pedirRebaja);
                                }
                            }
                            break;
                            case ACLMessage.INFORM: {
                                //El transporte ha descontado lo máximo que puede
                                Paquete paqTrans = Paquete.stringToPaquete(respuestaTransporte.getContent());
                                //guarda paquete ordenado en ofertasTransporte
                                //verificar que el descuento sea como máximo 100%
                                if(paqTrans.getPresupuestoMax()>1)
                                        paqTrans.setPresupuestoMax(1);
                                paqTrans.setPrecio(paqTrans.getPrecio()*(1-paqTrans.getPresupuestoMax()));
                                paqTrans.setAlojamiento(preferencias.getAlojamiento());
                                paqTrans.setHeuristica(prefSemiHeuristica);
                                ofertasTransporte.add(paqTrans);
                                Collections.sort(ofertasTransporte);
                                Collections.reverse(ofertasTransporte);
                                cuentaTransporte++;
                                if (ofertasTransporte.indexOf(paqTrans) == 0) {
                                    mejorTransporte = respuestaTransporte.getSender();
                                }
                            }
                            break;
                            default:
                                cuentaTransporte++;
                                break;
                        }

                    } else {
                        block();
                    }
                    if (cuentaTransporte == transportes.size()) {
                        step = 4;
                    }/*else {
                     block();
                     }*/

                }
                ;
                break;
            }

        }

        //Método para informar a las empresas que han ofrecido las mejores propuestas
        private void aceptarPropuestas(AID mejorLugar, AID mejorTransporte) {
            ACLMessage aceptar = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
            aceptar.addReceiver(mejorLugar);
            aceptar.addReceiver(mejorTransporte);
            myAgent.send(aceptar);
        }

        //Método para armar el paquete compuesto por las mejores ofertas de lugar y transporte
        private Paquete armarPaquete(Paquete lugar, Paquete transporte, Paquete preferencias) {
            Paquete propuesta = new Paquete();
            propuesta.setOrigen(preferencias.getOrigen());
            propuesta.setDestino(preferencias.getDestino());
            propuesta.setPresupuestoMax(preferencias.getPresupuestoMax());
            propuesta.setCantidadPersonas(preferencias.getCantidadPersonas());
            propuesta.setFechaInicialInferior(transporte.getFechaInicialInferior().getTime());
            propuesta.setFechaInicialSuperior(transporte.getFechaInicialInferior());
            propuesta.setDuracion(preferencias.getDuracion());
            propuesta.setAlojamiento(lugar.getAlojamiento());
            propuesta.setPonderacion(preferencias.getPonderacionPrecio());
            propuesta.setCalidadTransporte(transporte.getCalidadTransporte());

            //ASIGNAR COMISION DE AGENCIA
            propuesta.setPrecio((lugar.getPrecio() + transporte.getPrecio()) * comision);
            return propuesta;
        }

        @Override
        public boolean done() {
            ACLMessage propuesta;
            if (step == 4) {
                if (!ofertasLugar.isEmpty() && !ofertasTransporte.isEmpty()) {
                    aceptarPropuestas(mejorLugar, mejorTransporte);
                    Paquete paqueteArmado;
                    paqueteArmado = armarPaquete(ofertasLugar.get(0), ofertasTransporte.get(0), preferencias);
                    propuesta = new ACLMessage(ACLMessage.PROPOSE);
                    propuesta.setContent(paqueteArmado.toStringForMessage());

                } else {
                    propuesta = new ACLMessage(ACLMessage.REFUSE);
                }
                propuesta.addReceiver(msg.getSender());
                propuesta.setConversationId("Busqueda de Paquete");
                myAgent.send(propuesta);
                ofertasLugar = new ArrayList<>();
                ofertasTransporte = new ArrayList<>();
                step = 0;
            }
            return false;

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
                if (contenido.equalsIgnoreCase("Transporte")) {
                    transportes.add(msg.getSender());
                } else {
                    lugares.add(msg.getSender());
                }

            }
        }
    }
}
