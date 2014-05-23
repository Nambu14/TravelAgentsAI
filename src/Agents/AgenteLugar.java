/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import jade.core.Agent;

/**
 *
 * @author Lucas
 */
public class AgenteLugar extends Agent{

    private String ciudad;
    private int precioPersona;
    private String[] servicios;
    private float[] descuentoPorPersonas;
    private float[] descuentoPorAnticipacion;
    private float[] descuentoPorCantidadDeDias;
    
    @Override
    protected void setup() {
        Object [] args = getArguments();
        if (args != null && args.length >0) {
        ciudad = (String) args[0];
        precioPersona = (int) args [1];
        servicios = (String []) args [2];
        descuentoPorPersonas = (float []) args[3];
        descuentoPorAnticipacion = (float []) args[4];
        descuentoPorCantidadDeDias = (float []) args[5];
        }
    }
}
