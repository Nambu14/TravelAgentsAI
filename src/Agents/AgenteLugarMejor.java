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

    public AgenteLugarMejor(int calidad, Tipo tipo) {
            if(calidad>0 && calidad<6){
            this.calidad = calidad;
            }
            this.tipo=tipo;
    }
    
}
