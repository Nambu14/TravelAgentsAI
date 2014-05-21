/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Things.Paquete;
import jade.core.AID;
import jade.core.Agent;

/**
* Clase Turista, es el agente encargado de representar 
* a los intereses de un Turista específico.
 */

public class AgentTurist extends Agent {
    
    private Paquete preferencias;
    private AID[] agenciasTurismo;

    public AgentTurist(Paquete preferencias) {
        this.preferencias = preferencias;
  }
    
    @Override
    protected void setup(){
        
    }
    
}
