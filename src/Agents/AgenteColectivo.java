/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Things.CronogramaTransporteColectivo;
import Ventanas.VentanaTransporteColectivo;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 *
 * @author Torre
 */
public class AgenteColectivo extends AgentTransport{
    private String nombre;
    private CronogramaTransporteColectivo [] rutas;
    private VentanaTransporteColectivo myGui;
   
    @Override
    protected void setup() {
        myGui= new VentanaTransporteColectivo(this);
        myGui.showGui();
    //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Transporte Colectivo");
        sd.setName(nombre);
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }
    
    public void definirColectivo(String name, CronogramaTransporteColectivo[] cronograma){
        nombre = name;
        rutas = cronograma;
    }
    protected void takeDown(){
        //Quitar registro de las paginas amarillas
        try{
            DFService.deregister(this);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    }
    
}
