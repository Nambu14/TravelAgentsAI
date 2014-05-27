/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Ventanas.VentanaLugarPeor;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 *
 * @author Torre
 */
public class AgenteLugarPeor extends AgenteLugar {
    private String nombre;
    public enum Tipo{CABAÑA, HOSTAL};
    private Tipo tipo;
    private int calidad;
    private VentanaLugarPeor myGui;

    @Override
    protected void setup() {
        myGui= new VentanaLugarPeor(this);
        myGui.showGui();
          
        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Lugar");
        sd.setName(nombre);
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }
    //ver bien la definicion del tipo en la gui
    public void definirLugarPeor(String nombre, Tipo tipo, int calidad){
        this.nombre = nombre;
        this.calidad = calidad;
        this.tipo = tipo;
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
    
    
   
}
