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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Clase Agencia de Turismo, es el agente encargado de representar 
* a una Agencia de Turismo específica.
 */
public class AgenteAgenciaTurismo extends Agent{
    private String nombre;
    private ArrayList<Paquete> ofertasLugar;
    private ArrayList<Paquete> ofertasTransporte;
    private AID mejorLugar;
    private AID mejorTransporte;
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
        nombre = this.getLocalName();
        
        myGui= new VentanaAgencia(this);
        myGui.setVisible(true);
        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Agencia de Turismo");
        sd.setName(nombre);
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
        addBehaviour(new ActualizarLugares());
       
    }
    
    //Métodos llamados desde la interfaz
    
    public void definirAgencia(float dtoTransporte, float dtoLugar){
        descuentoTransporte = dtoTransporte/100;
        descuentoLugar = dtoLugar/100;
        AID id = new AID(nombre, AID.ISLOCALNAME);
    }
    
    public void asignarDescuentoPropio(float efectivo, float tarjeta){
        descuentoPropio[0] = efectivo/100;
        descuentoPropio[1] = tarjeta/100;
    }
    
    public void asignarServicios(AID[] transportes, AID[] lugares){
        this.transportes = transportes;
        this.lugares = lugares;
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
                
                //EMPIEZA LA NEGOCIACION CON LUGARES
                ACLMessage cfpLugares = new ACLMessage(ACLMessage.CFP);
                for (AID lugar: lugares){
                    cfpLugares.addReceiver(lugar);
                }
                cfpLugares.setContent(pref);
                cfpLugares.setConversationId("Busqueda de Lugar");
                myAgent.send(cfpLugares);
                for(AID lugar: lugares){
                    
                    MessageTemplate mtlugar = MessageTemplate.and(MessageTemplate.MatchConversationId("Busqueda de Lugar"),
                            MessageTemplate.MatchSender(lugar));
                    ACLMessage respuestaLugar = myAgent.receive(mtlugar);
                    if (respuestaLugar != null){
                        switch (respuestaLugar.getPerformative()){
                            case ACLMessage.PROPOSE: {
                                // presupuestomax va a tomar como el descuento realizado
                                
                                try {
                                    Paquete paqLugar = Paquete.stringToPaquete(respuestaLugar.getContent());
                                    if(descuentoLugar<=paqLugar.getPresupuestoMax()){
                                        //guarda paquete ordenado en ofertasLugar
                                        if(ofertasLugar.indexOf(paqLugar)== 0)
                                            mejorLugar = lugar;
                                    }else {
                                        ACLMessage pedirRebaja = respuestaLugar.createReply();
                                        pedirRebaja.setPerformative(ACLMessage.CFP);
                                        pedirRebaja.setContent(respuestaLugar.getContent());
                                        send(pedirRebaja);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(AgenteAgenciaTurismo.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            } break;
                            case ACLMessage.INFORM: {
                                try{
                                    Paquete paqLugar = Paquete.stringToPaquete(respuestaLugar.getContent());
                                    //guarda paquete ordenado en ofertasLugar
                                    if(ofertasLugar.indexOf(paqLugar)== 0)
                                        mejorLugar = lugar;
                                } catch (Exception ex) {
                                    Logger.getLogger(AgenteAgenciaTurismo.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } break;
                            default:{}                          
                        }
                        
                    }    
                }    
                
                // EMPIEZA LA NEGOCIACION CON TRANSPORTES
                ACLMessage cfpTransportes = new ACLMessage(ACLMessage.CFP);
                for (AID transporte: transportes){
                    cfpTransportes.addReceiver(transporte);
                }
                cfpTransportes.setContent(pref);
                cfpTransportes.setConversationId("Busqueda de Transportes");
                myAgent.send(cfpTransportes);
                for(AID transporte: transportes){
                    
                    MessageTemplate mttransporte = MessageTemplate.and(MessageTemplate.MatchConversationId("Busqueda de Transportes"),
                            MessageTemplate.MatchSender(transporte));
                    ACLMessage respuestaTransporte = myAgent.receive(mttransporte);
                    if (respuestaTransporte != null){
                        // igual que lugares
                    }    
                }
                aceptarPropuestas(mejorLugar, mejorTransporte);
                armarPaquete(ofertasLugar.get(0), ofertasTransporte.get(0));
                
            }
            else{
                block();
            }
        }

        private void aceptarPropuestas(AID mejorLugar, AID mejorTransporte) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private void armarPaquete(Paquete get, Paquete get0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        
            
        }
}   

