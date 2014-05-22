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
public class LugarPeor extends Lugar {
    private enum Tipo{CABAÑA, HOSTAL};
    private int calidad;

    public LugarPeor(int calidad) {
            if(calidad>0 && calidad<4){
            this.calidad = calidad;
            }
    }
}
