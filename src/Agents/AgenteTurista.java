/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Things.Paquete;
import Ventanas.VentanaTurista;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Turista, es el agente encargado de representar los intereses de un
 * Turista específico.
 */
public class AgenteTurista extends Agent {

    private Paquete preferencias;
    private AID[] agenciasTurismo;
    private ArrayList<Paquete> ofertas;
    private VentanaTurista myGui;
    private boolean fin = false;
    private AID mejorAID;

    @Override
    protected void setup() {
        myGui = new VentanaTurista(this);
        myGui.setVisible(true);
        ofertas = new ArrayList<>();
        //Actualiza la lista de Agencias de Turismo.
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DFAgentDescription dfd = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Agencia de Turismo");
                dfd.addServices(sd);
                try {
                    DFAgentDescription[] agencias = DFService.search(myAgent, dfd);
                    agenciasTurismo = new AID[agencias.length];
                    for (int i = 0; i < agencias.length; ++i) {
                        agenciasTurismo[i] = agencias[i].getName();
                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
            }
        });
        addBehaviour(new SolicitarPaquetes());
    }

    public void setPreferencias(Paquete preferencias) {
        this.preferencias = preferencias;
    }

    private class SolicitarPaquetes extends SimpleBehaviour {

        //private Object[] paquetePonderado = new Array[3];

        private ArrayList<Array> listaPaquetes;
        private MessageTemplate mt;

        @Override
        public void action() {
            // Mandar cfp a todas las agencias
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            for (int i = 0; i < agenciasTurismo.length; ++i) {
                cfp.addReceiver(agenciasTurismo[i]);
            }
            cfp.setContent(preferencias.toStringForMessage());
            cfp.setConversationId("Busqueda de Paquete");
            myAgent.send(cfp);
            for (AID agencia : agenciasTurismo) {
                mt = MessageTemplate.MatchSender(agencia);
                ACLMessage respuesta = myAgent.receive(mt);
                if (respuesta != null) {
                    //Procesar ofertas
                    if (respuesta.getPerformative() == ACLMessage.PROPOSE) {
                        try {
                            Paquete propuesta = Paquete.stringToPaquete(respuesta.getContent());
                            //Acá agrega el paquete a ofertas, y ordena la lista.
                            propuesta.setHeuristica(preferencias);
                            ofertas.add(propuesta);
                            Collections.sort(ofertas);
                            Collections.reverse(ofertas);
                            if (ofertas.indexOf(propuesta) == 0) {
                                mejorAID = agencia;
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(AgenteTurista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        //No hay posiblidad de formar un paquete con los requerimientos solicitados en esta agencia.
                    }
                } else {
                    block();
                }
            }
            ACLMessage mensajeAcept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
            mensajeAcept.addReceiver(mejorAID);
            myAgent.send(mensajeAcept);
            fin = true;
        }

        @Override
        public boolean done() {
            return (fin);
        }
    }
}
