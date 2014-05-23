/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import jade.core.Agent;

/**
* Clase transporte, es el agente encargado de representar a una
* empresa de transporte específica.
*/
public abstract class AgentTransport extends Agent{
    //Hasta determinada cantidad de personas hay descuento, esa cantidad es la longitud del arreglo
    private float[] descuentoPorPersonas;
    private float[] descuentoPorAnticipacion;
    //Descuento dado por promociones en determinados días.
    private float[] descuentoPorDias;
    
    @Override
   protected void setup(){
       
       Object[] args = getArguments();
       if (args != null && args.length >0) {
           descuentoPorPersonas = (float []) args[0];
           descuentoPorAnticipacion = (float []) args[1];
           descuentoPorDias = (float []) args[2];
       }
       }
 
}
