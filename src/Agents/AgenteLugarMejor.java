/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

/**
 *
 * @author Torre
 */
public class AgenteLugarMejor extends AgenteLugar {
    
    private enum Tipo{HOTEL, APART};
    private Tipo tipo;
    private int calidad;

   private void AgenteLugarMejor(Tipo tipo, int calidad){
        if(calidad>0 && calidad<6){
            this.calidad = calidad;
              }
        this.tipo = tipo;
    }
    @Override
    protected void setup() {
     
          
      
    }
    
}
