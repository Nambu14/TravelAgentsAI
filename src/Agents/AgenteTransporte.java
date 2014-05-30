/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Things.CronogramaTransporte;
import Ventanas.VentanaTransporte;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;


/**
* Clase transporte, es el agente encargado de representar a una
* empresa de transporte específica.
*/
public class AgenteTransporte extends Agent{
    //Hasta determinada cantidad de personas hay descuento, esa cantidad es la longitud del arreglo
    private float[] descuentoPorPersonas;
    private float[] descuentoPorAnticipacion;
    //Descuento dado por promociones en determinados días.
    private float[] descuentoPorDias;
    private String nombre;
    public enum TipoEmpresa {TERRESTRE, AEREA};
    private TipoEmpresa tipo;
    private ArrayList<CronogramaTransporte> rutas;
    private VentanaTransporte myGui;
    
    @Override
    protected void setup() {
        myGui= new VentanaTransporte(this);
        myGui.setVisible(true);

        rutas = new ArrayList<>();
        //Registro en paginas amarillas;
        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Transporte");
        sd.setName(nombre);
        dfd.addServices(sd);
        try{
            DFService.register(this, dfd);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
    
    }
    public void definirTransporte(String name){
        nombre = name;
   }
    //Quitar registro de las paginas amarillas

    /**
     *
     */
        @Override
    protected void takeDown(){
                try{
            DFService.deregister(this);
        }
        catch (FIPAException fe){
            fe.printStackTrace();
        }
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
   
   public void addCronograma(CronogramaTransporte ruta){
       rutas.add(ruta);
   }
}
