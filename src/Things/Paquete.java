/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Things;

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
    private Lugar alojamiento;

    public Paquete(String origen, String destino, float presupuestoMax, int cantidadPersonas, GregorianCalendar fechaInicialInferior, GregorianCalendar fechaInicialSuperior, int duracion, Lugar alojamiento) {
        this.origen = origen;
        this.destino = destino;
        this.presupuestoMax = presupuestoMax;
        this.cantidadPersonas = cantidadPersonas;
        this.fechaInicialInferior = fechaInicialInferior;
        this.fechaInicialSuperior = fechaInicialSuperior;
        this.duracion = duracion;
        this.alojamiento = alojamiento;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public float getPresupuestoMax() {
        return presupuestoMax;
    }

    public void setPresupuestoMax(float presupuestoMax) {
        this.presupuestoMax = presupuestoMax;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public GregorianCalendar getFechaInicialInferior() {
        return fechaInicialInferior;
    }

    public void setFechaInicialInferior(GregorianCalendar fechaInicialInferior) {
        this.fechaInicialInferior = fechaInicialInferior;
    }

    public GregorianCalendar getFechaInicialSuperior() {
        return fechaInicialSuperior;
    }

    public void setFechaInicialSuperior(GregorianCalendar fechaInicialSuperior) {
        this.fechaInicialSuperior = fechaInicialSuperior;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Lugar getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Lugar alojamiento) {
        this.alojamiento = alojamiento;
    }

    
    
    
}
