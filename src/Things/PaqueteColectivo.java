/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Things;


/**
 *
 * @author Lucas
 */
public class PaqueteColectivo extends Paquete{

    public static enum Comodidad {EJECUTIVO, CAMA, SEMICAMA};
    private Comodidad comodidad;
    
    public Comodidad getComodidad() {
        return comodidad;
    }

    public void setComodidad(Comodidad comodidad) {
        this.comodidad = comodidad;
    }
    
    
}
