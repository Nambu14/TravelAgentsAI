/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Things;

import Agents.AgenteLugar;
import java.util.GregorianCalendar;

/**
 *
 * @author Lucas
 */
public class Paquete {
    private String origen;
    private String destino;
    private float presupuestoMax;
    private int cantidadPersonas;
    private GregorianCalendar fechaInicialInferior;
    private GregorianCalendar fechaInicialSuperior;
    private int duracion;
    private AgenteLugar alojamiento;
    private float ponderacionPrecio;
    private float ponderacionCalidad;
    
    public void setPonderacion (float precio, float calidad){
        if ((precio+calidad)==1){
            this.setPonderacionCalidad(calidad);
            this.setPonderacionPrecio(precio);
        }
    }

    public float getPonderacionPrecio() {
        return ponderacionPrecio;
    }

    private void setPonderacionPrecio(float ponderacionPrecio) {
        this.ponderacionPrecio = ponderacionPrecio;
    }

    public float getPonderacionCalidad() {
        return ponderacionCalidad;
    }

    private void setPonderacionCalidad(float ponderacionCalidad) {
        this.ponderacionCalidad = ponderacionCalidad;
    }
}