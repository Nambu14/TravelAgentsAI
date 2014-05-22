/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Things;

/**
 *
 * @author Torre
 */
public class LugarMejor extends Lugar {
    
    private enum Tipo{HOTEL, APART};
    private int calidad;

    public LugarMejor(int calidad) {
            if(calidad>0 && calidad<6){
            this.calidad = calidad;
            }
    }
    
}
