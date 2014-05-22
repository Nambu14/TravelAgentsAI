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
public class AgenteLugarPeor extends AgenteLugar {
    private enum Tipo{CABAÑA, HOSTAL};
    private Tipo tipo;
    private int calidad;

    public AgenteLugarPeor(int calidad, Tipo tipo) {
            if(calidad>0 && calidad<4){
            this.calidad = calidad;
            }
            this.tipo=tipo;
    }
}
