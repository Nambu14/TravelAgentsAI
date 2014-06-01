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

/**
* Clase Agencia de Turismo, es el agente encargado de representar 
* a una Agencia de Turismo específica.
 */
public class AgenteAgenciaTurismo extends Agent{
    private String nombre;
    //Descuentos máximos a pedir a una empresa de transporte o lugar.
    private float descuentoTransporte;
    private float descuentoLugar;
    //Descuentos por pago con efectivo o tarjeta.
    private float[] descuentoPropio;
    private AID[] transportes;
    private AID[] lugares;
    private VentanaAgencia myGui;
    
    @Override
    protected void setup() {
        
        descuentoPropio = new float[2];
        
        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Agencia de Turismo");
        //sd.setName(nombre);
        //para probar
        sd.setName(this.getLocalName());
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
        addBehaviour(new ActualizarLugares());
       // myGui= new VentanaAgencia(this);
       // myGui.setVisible(true);
    }
    
    //Métodos llamados desde la interfaz
    
    public void definirAgencia(String name, float dtoTransporte, float dtoLugar){
        nombre= name;
        descuentoTransporte = dtoTransporte/100;
        descuentoLugar = dtoLugar/100;
        AID id = new AID(nombre, AID.ISLOCALNAME);
    }
    
    public void asignarDescuentoPropio(float efectivo, float tarjeta){
        descuentoPropio[0] = efectivo/100;
        descuentoPropio[1] = tarjeta/100;
    }
    
    //Quitar registro de las paginas amarillas
    protected void takeDown(){
                try{
            DFService.deregister(this);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }
    
    //Falta lugares y transportes que deberían ser dinámicos
    //Behaviour para actualizar la lista con todos las empresas de Transportes y Lugares,
    //para luego ser mostradas en la GUI.
    private class ActualizarLugares extends OneShotBehaviour{

        @Override
        public void action() {
            DFAgentDescription dfd = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Lugar");
            dfd.addServices(sd);
            try {
                DFAgentDescription[] lugarcitos = DFService.search(myAgent, dfd); 
                lugares = new AID[lugarcitos.length];
                for (int i = 0; i < lugarcitos.length; ++i) {
                    lugares[i] = lugarcitos[i].getName();
                }
            }
            catch (FIPAException fe) {
            }
        }
        
    }
    
    private class ActualizarTransportes extends OneShotBehaviour{

        @Override
        public void action() {
            DFAgentDescription dfd = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("Empresa de Transporte");
            dfd.addServices(sd);
            try {
                DFAgentDescription[] transportitos = DFService.search(myAgent, dfd); 
                transportes = new AID[transportitos.length];
                for (int i = 0; i < transportitos.length; ++i) {
                    transportes[i] = transportitos[i].getName();
                }
            }
            catch (FIPAException fe) {
            }
        }
        
    }
    
    private class BuscarPaquete extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg!= null){
                String pref = msg.getContent();
                try {
                    Paquete preferencias = Paquete.stringToPaquete(pref);
                } catch (Exception ex) {
                    System.out.println("Error en conversión de paquete");
                }
                ACLMessage cfpLugares = new ACLMessage(ACLMessage.CFP);
                for (AID lugar: lugares){
                    cfpLugares.addReceiver(lugar);
                }
                cfpLugares.setContent("hola mi gente");
                cfpLugares.setConversationId("Busqueda de Lugar");
                myAgent.send(cfpLugares);
                for(AID lugar: lugares){
                    
                    MessageTemplate mtlugar = MessageTemplate.and(MessageTemplate.MatchConversationId("Busqueda de Lugar"),
                            MessageTemplate.MatchSender(lugar));
                    ACLMessage respuestaLugar = myAgent.receive(mtlugar);
                    if (respuestaLugar != null){
                        if (respuestaLugar.getPerformative() == ACLMessage.PROPOSE){
                            ACLMessage propuestaLugar = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                            propuestaLugar.addReceiver(lugar);
                            propuestaLugar.setContent("te acepto mi gente");
                            propuestaLugar.setConversationId("Busqueda de Lugar");
                            myAgent.send(propuestaLugar);
                        }
                    }    
                }    
                
                ACLMessage cfpTransportes = new ACLMessage(ACLMessage.CFP);
                for (AID transporte: transportes){
                    cfpTransportes.addReceiver(transporte);
                }
                cfpTransportes.setContent("hola mi gente");
                cfpTransportes.setConversationId("Busqueda de Transportes");
                myAgent.send(cfpTransportes);
                for(AID transporte: transportes){
                    
                    MessageTemplate mttransporte = MessageTemplate.and(MessageTemplate.MatchConversationId("Busqueda de Transportes"),
                            MessageTemplate.MatchSender(transporte));
                    ACLMessage respuestaTransporte = myAgent.receive(mttransporte);
                    if (respuestaTransporte != null){
                        if (respuestaTransporte.getPerformative() == ACLMessage.PROPOSE){
                            ACLMessage propuestaTransporte = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                            propuestaTransporte.addReceiver(transporte);
                            propuestaTransporte.setContent("te acepto");
                            propuestaTransporte.setConversationId("Busqueda de Transportes");
                            myAgent.send(propuestaTransporte);
                        }
                    }    
                }
                
            }
            else{
                block();
            }
        }

        
            
        }
}   

