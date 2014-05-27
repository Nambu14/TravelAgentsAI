/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Things.CronogramaTransporteAereo;
import Ventanas.VentanaTransporteAereo;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 *
 * @author Torre
 */
public class AgenteAvion extends AgentTransport {
    private String nombre;
    private CronogramaTransporteAereo [] rutas;
    private VentanaTransporteAereo myGui;
    
    @Override
    protected void setup() {
        myGui= new VentanaTransporteAereo(this);
        myGui.showGui();
        
        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Transporte Aereo");
        sd.setName(nombre);
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    
    }
    public void definirTransporte(String name, CronogramaTransporteAereo[] cronograma){
        nombre = name;
        rutas = cronograma;
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
