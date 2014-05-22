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
    
    private float[] descuentoPorPersonas;
    private float[] descuentoPorAnticipacion;
    //Descuento dado por promociones en determinados días.
    private float[] descuentoPorDias;
    
    

}
