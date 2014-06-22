/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Ventanas.ServiciosDisponibles;
import jade.core.Agent;

/**
 *
 * @author Torre
 */
public class AgenteBuscar extends Agent{
    
    private ServiciosDisponibles myGui;
    
    @Override
    protected void setup(){
        myGui = new ServiciosDisponibles(this);
        myGui.setVisible(true);
    }
}
