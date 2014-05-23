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

    @Override
    protected void setup() {
      Object [] args = getArguments();
      if (args != null && args.length >0) {
          tipo = (Tipo) args[0];      
          int cal = (int) args [1];
          if(cal>0 && cal<6){
            calidad = cal;
              }
      }
    }
    
}
