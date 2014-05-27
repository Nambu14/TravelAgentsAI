/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Ventanas.VentanaLugarMejor;

/**
 *
 * @author Torre
 */
public class AgenteLugarMejor extends AgenteLugar {
    
    private enum Tipo{HOTEL, APART};
    private Tipo tipo;
    private int calidad;
    private VentanaLugarMejor myGui;

   protected void setup() {
         myGui= new VentanaLugarMejor(this);
         myGui.showGui();
      
    }
    public void definirLugarMejor(Tipo tipo, int calidad){
        this.calidad = calidad;
        this.tipo = tipo;
    }
       
    
}
