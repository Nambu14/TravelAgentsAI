/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Things.CronogramaTransporte;
import Things.Paquete;
import Ventanas.VentanaTransporte;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase transporte, es el agente encargado de representar a una empresa de
 * transporte específica.
 */
public class AgenteTransporte extends Agent {

    //Hasta determinada cantidad de personas hay descuento, esa cantidad es la longitud del arreglo
    private float[] descuentoPorPersonas;
    private float[] descuentoPorAnticipacion;
    //Descuento dado por promociones en determinados días.
    private float[] descuentoPorDias;
    private String nombre;

    public enum TipoEmpresa {

        TERRESTRE, AEREA
    };
    private TipoEmpresa tipo;

    private ArrayList<CronogramaTransporte> rutas;
    private VentanaTransporte myGui;

    @Override
    protected void setup() {
        myGui = new VentanaTransporte(this);
        myGui.setVisible(true);

        rutas = new ArrayList<>();
        nombre = getLocalName();

        //Registro en paginas amarillas;
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Transporte");
        sd.setName(nombre);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        //addBehaviour(new RecibirPedido());

    }

    //Quitar registro de las paginas amarillas
    /**
     *
     */
    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    
    public void agregarComportamiento(){
        addBehaviour(new RecibirPedido());
    }

    // Métodos llamados desde la interfaz, donde ya se crean los arreglos
    public void asignarDescuentoPersonas(float[] dtoPsas) {
        descuentoPorPersonas = dtoPsas;
    }

    public void asignarDescuentoDias(float[] dtoDias) {
        descuentoPorDias = dtoDias;
    }

    public void asignarDescuentoAnticipación(float[] dtoAnticipacion) {
        descuentoPorAnticipacion = dtoAnticipacion;
    }

    public void addCronogramas(ArrayList<CronogramaTransporte> rutas) {
        this.rutas = rutas;
    }
        
    public void setTransporte(TipoEmpresa tipo){
        nombre = this.getLocalName();
        this.tipo = tipo; 
    }

    private class RecibirPedido extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.CFP), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                    ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage respuestaT = msg.createReply();
                Paquete pref;
                pref = Paquete.stringToPaquete(msg.getContent());
                if (msg.getPerformative() == ACLMessage.CFP) {
                    //Verificar que existan rutas para los días de salida y vuelta
                    boolean existeRutaIda = false;
                    boolean existeRutaVuelta = true;
                    GregorianCalendar fechaSalida = pref.getFechaInicialInferior();
                    if (pref.daysBetween() == 0) {
                        for (CronogramaTransporte ruta : rutas) {
                            if ((ruta.getOrigen().equalsIgnoreCase(pref.getOrigen())) && (ruta.getDestino().equalsIgnoreCase(pref.getDestino()))) {
                                if (ruta.askForDate(fechaSalida)) {
                                    existeRutaIda = true;
                                }
                                GregorianCalendar vuelta;
                                vuelta = fechaSalida;
                                vuelta.add(Calendar.DAY_OF_MONTH, pref.getDuracion());
                                if (ruta.askForDate(vuelta)) {
                                    existeRutaIda = true;
                                }
                                if (existeRutaIda && existeRutaVuelta) {
                                    pref.setPresupuestoMax(0);
                                    pref.setCalidadTransporte(ruta.getCalidad());
                                    pref.setFechaInicialInferior(fechaSalida);
                                    pref.setPrecio(ruta.getPrecioPersona() * pref.getCantidadPersonas());
                                    break;
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < pref.daysBetween(); i++) {
                            for (CronogramaTransporte ruta : rutas) {
                                if ((ruta.getOrigen().equalsIgnoreCase(pref.getOrigen())) && (ruta.getDestino().equalsIgnoreCase(pref.getDestino()))) {
                                    if (ruta.askForDate(fechaSalida)) {
                                        existeRutaIda = true;
                                    }
                                    GregorianCalendar vuelta;
                                    vuelta = fechaSalida;
                                    vuelta.add(Calendar.DAY_OF_MONTH, pref.getDuracion());
                                    if (ruta.askForDate(vuelta)) {
                                        existeRutaIda = true;
                                    }
                                    if (existeRutaIda && existeRutaVuelta) {
                                        pref.setPresupuestoMax(0);
                                        pref.setCalidadTransporte(ruta.getCalidad());
                                        pref.setFechaInicialInferior(fechaSalida);
                                        pref.setPrecio(ruta.getPrecioPersona() * pref.getCantidadPersonas());
                                        break;
                                    }
                                }

                            }
                            if (existeRutaIda && existeRutaVuelta) {
                                break;
                            }
                            fechaSalida.add(Calendar.DAY_OF_MONTH, 1);
                        }
                    }
                    if (existeRutaIda && existeRutaVuelta) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        respuestaT.setContent(pref.toStringForMessage());
                        myAgent.send(respuestaT);
                    } else {
                        respuestaT.setPerformative(ACLMessage.REFUSE);
                        myAgent.send(respuestaT);
                    }
                } else if (msg.getPerformative() == ACLMessage.INFORM) {
                    //Dar descuentos
                    if (pref.getCantidadPersonas() != 0 && pref.getCantidadPersonas()<descuentoPorPersonas.length) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        float dbt = descuentoPorPersonas[pref.getCantidadPersonas()];
                        pref.setPresupuestoMax(dbt);
                        pref.setCantidadPersonas(0);
                    } else if (pref.getDuracion() != 0 && pref.getDuracion()< descuentoPorAnticipacion.length) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        GregorianCalendar cal = new GregorianCalendar();
                        float dbt = descuentoPorAnticipacion[Paquete.daysBetween(cal, pref.getFechaInicialInferior())];
                        pref.setPresupuestoMax(pref.getPresupuestoMax() + dbt);
                        pref.setDuracion(0);
                    } else if (pref.getAnticipacion() != 0 && pref.getAnticipacion()<descuentoPorDias.length) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        float dto = descuentoPorDias[(pref.getFechaInicialInferior().get(Calendar.DAY_OF_WEEK) - 1)];
                        pref.setPresupuestoMax(pref.getPresupuestoMax() + dto);
                        pref.setAnticipacion(0);
                    } else {
                        respuestaT.setPerformative(ACLMessage.INFORM);
                    }
                    respuestaT.setContent(pref.toStringForMessage());
                    myAgent.send(respuestaT);
                }
            } else {
                block();
            }
        }

    }
}
