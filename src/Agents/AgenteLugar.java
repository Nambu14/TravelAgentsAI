/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Agents.AgenteLugar.Tipo;
import Things.Paquete;
import Ventanas.PantallaInicial;
import Ventanas.VentanaLugar;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Lucas
 */
public class AgenteLugar extends Agent {

    private String ciudad;
    private int precioPersona;
    private String[] servicios;
    private float[] descuentoPorPersonas;
    private float[] descuentoPorAnticipacion;
    private float[] descuentoPorCantidadDeDias;

    public enum Tipo {

        HOTEL, APART, CABAÑA, HOSTAL
    };
    private String nombre;
    private int calidad;
    private Tipo tipo;
    private VentanaLugar myGui;

    @Override
    protected void setup() {
        myGui = new VentanaLugar(this);
        myGui.setVisible(true);
        nombre = this.getLocalName();

        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Lugar");
        sd.setName(nombre);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        addBehaviour(new RecibirPedido());
    }

    // Métodos llamados desde la interfaz, donde ya se crean los arreglos
    public void definirLugar(String ciudad, int precio, int calidad, Tipo tipo) {
        this.ciudad = ciudad;
        precioPersona = precio;
        this.calidad = calidad;
        this.tipo = tipo;
    }

    public void asignarServicios(String[] servicios) {
        this.servicios = servicios;
    }

    public void asignarDescuentoPersonas(float[] dtoPsas) {
        descuentoPorPersonas = dtoPsas;
    }

    public void asignarDescuentoDias(float[] dtoDias) {
        descuentoPorCantidadDeDias = dtoDias;
    }

    public void asignarDescuentoAnticipación(float[] dtoAnticipacion) {
        descuentoPorAnticipacion = dtoAnticipacion;
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "AgenteLugar{" + "ciudad=" + ciudad + ", servicios=" + servicios + ", nombre=" + nombre + ", calidad=" + calidad + ", tipo=" + tipo + '}';
    }

    private class RecibirPedido extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // si ya se aplicaron descuentos los valores en el paquete van a ser 0 (CantPsas, duracion, fecha)
                ACLMessage respuestaLugar = msg.createReply();
                Paquete pref;
                pref = Paquete.stringToPaquete(msg.getContent());
                //Si el alojamiento no queda en la ciudad de destino envía un REFUSE
                if (pref.getDestino().equalsIgnoreCase(ciudad)) {
                    if (pref.getCantidadPersonas() > 0) {
                        pref.setPrecio(precioPersona * pref.getCantidadPersonas());
                        pref.setCantidadPersonas(-1);
                    } else if (pref.getCantidadPersonas() < 0) {
                        respuestaLugar.setPerformative(ACLMessage.PROPOSE);
                        float dto = descuentoPorPersonas[pref.getCantidadPersonas()];
                        pref.setPresupuestoMax(dto);
                        pref.setCantidadPersonas(0);
                    } else if (pref.getDuracion() != 0) {
                        respuestaLugar.setPerformative(ACLMessage.PROPOSE);
                        float dto = pref.getPresupuestoMax() + descuentoPorCantidadDeDias[pref.getDuracion()];
                        pref.setPresupuestoMax(dto);
                        pref.setDuracion(0);
                    } else if (pref.getAnticipacion() != 0) {
                        respuestaLugar.setPerformative(ACLMessage.PROPOSE);
                        float dto = pref.getPresupuestoMax() + descuentoPorAnticipacion[pref.getAnticipacion()];
                        pref.setPresupuestoMax(dto);
                        pref.setAnticipacion(0);
                    } else {
                        respuestaLugar.setPerformative(ACLMessage.INFORM);
                    }
                    String nuevoPrecio = pref.toStringForMessage();
                    respuestaLugar.setContent(nuevoPrecio);
                    send(respuestaLugar);
                } else {
                    respuestaLugar.setPerformative(ACLMessage.REFUSE);
                    respuestaLugar.setContent(ciudad);
                    myAgent.send(respuestaLugar);
                }
            } else {
                block();
            }

        }
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public float getCalidadGeneral() {
        float calidadTipo;
        float calidadEstrella = 0;
        float calidadServicios;
        if (getTipo() == Tipo.HOTEL) {
            calidadTipo = 0.7f;
        } else if (getTipo() == Tipo.APART) {
            calidadTipo = 0.7f;
        } else if (getTipo() == Tipo.CABAÑA) {
            calidadTipo = 0.3f;
        } else {
            calidadTipo = 0f;
        }
        switch (calidad) {
            case 5:
                calidadEstrella = 1f;
                break;
            case 4:
                calidadEstrella = 0.6f;
                break;
            case 3:
                calidadEstrella = 0.3f;
                break;
            case 2:
                calidadEstrella = 0.1f;
                break;
            case 1:
                calidadEstrella = 0f;
                break;
            default:
                System.out.println("No se tiene calidad");
        }
        calidadServicios = (float) 0.1f * servicios.length;
        return (calidadEstrella + calidadServicios + calidadTipo);
    }
}
