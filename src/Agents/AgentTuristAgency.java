/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import jade.core.AID;
import jade.core.Agent;

/**
* Clase Agencia de Turismo, es el agente encargado de representar 
* a una Agencia de Turismo específica.
 */
public class AgentTuristAgency extends Agent{
    
    //Descuentos máximos a pedir a una empresa de transporte o lugar.
    private float descuentoTransporte;
    private float descuentoLugar;
    //Descuentos por pago con efectivo o tarjeta.
    private float[] descuentoPropio;
    private AID[] transportes;
    private AID[] lugares;
    
    @Override
    protected void setup() {
        
        Object [] args = getArguments();
        if (args != null && args.length >0) {
            descuentoTransporte = (float) args[0];
            descuentoLugar = (float) args[1];
            descuentoPropio = (float []) args[2];
            transportes = (AID[]) args[3];
            lugares = (AID[]) args[4];
        }
    }
    
 
}
