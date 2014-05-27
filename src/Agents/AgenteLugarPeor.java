/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Ventanas.VentanaLugarPeor;

/**
 *
 * @author Torre
 */
public class AgenteLugarPeor extends AgenteLugar {
    public enum Tipo{CABAÑA, HOSTAL};
    private Tipo tipo;
    private int calidad;
    private VentanaLugarPeor myGui;

    @Override
    protected void setup() {
        myGui= new VentanaLugarPeor(this);
        myGui.showGui();
          
      
    }
    //ver bien la definicion del tipo en la gui
    public void definirLugarPeor(Tipo tipo, int calidad){
        this.calidad = calidad;
        this.tipo = tipo;
    }
    
    
   
}
