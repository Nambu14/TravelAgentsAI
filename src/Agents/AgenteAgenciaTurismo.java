/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Ventanas.VentanaAgencia;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

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
        sd.setName(nombre);
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
        addBehaviour(new ActualizarLugares());
        //myGui= new VentanaAgencia(this);
        //myGui.showGui();
    }
    
    //Métodos llamados desde la interfaz
    
    public void definirAgencia(String name, float dtoTransporte, float dtoLugar){
        nombre= name;
        descuentoTransporte = dtoTransporte;
        descuentoLugar = dtoLugar;
    }
    
    public void asignarDescuentoPropio(float efectivo, float tarjeta){
        descuentoPropio[0] = efectivo;
        descuentoPropio[1] = tarjeta;
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
}
