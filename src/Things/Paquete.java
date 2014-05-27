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
public final class Paquete {
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
    public enum Calidad {EJECUTIVO, CAMA, SEMICAMA, PRIMERACLASE, BUSSINES, TURISTA};
    private Calidad calidad;

    public Paquete(String origen, String destino, float presupuestoMax, int cantidadPersonas, GregorianCalendar fechaInicialInferior, GregorianCalendar fechaInicialSuperior, int duracion, AgenteLugar alojamiento, float ponderacionPrecio, float ponderacionCalidad, Calidad calidad) {
        this.origen = origen;
        this.destino = destino;
        this.presupuestoMax = presupuestoMax;
        this.cantidadPersonas = cantidadPersonas;
        this.fechaInicialInferior = fechaInicialInferior;
        this.fechaInicialSuperior = fechaInicialSuperior;
        this.duracion = duracion;
        this.alojamiento = alojamiento;
        this.setPonderacion(presupuestoMax, ponderacionCalidad);
        this.calidad = calidad;
    }
        
    private void setPonderacion (float precio, float calidad){
        if ((precio+calidad)==1){
            this.setPonderacionCalidad(calidad);
            this.setPonderacionPrecio(precio);
        }else{
        //throw new RuntimeException
        //arreglar esto 
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

    public AgenteLugar getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(AgenteLugar alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Calidad getCalidad() {
        return calidad;
    }

    public void setCalidad(Calidad calidad) {
        this.calidad = calidad;
    }

    @Override
    public String toString() {
        return "Paquete{" + "origen=" + origen + ", destino=" + destino + ", presupuestoMax=" + presupuestoMax + ", cantidadPersonas=" + cantidadPersonas + ", fechaInicialInferior=" + fechaInicialInferior + ", fechaInicialSuperior=" + fechaInicialSuperior + ", duracion=" + duracion + ", alojamiento=" + alojamiento + ", ponderacionPrecio=" + ponderacionPrecio + ", ponderacionCalidad=" + ponderacionCalidad + ", calidad=" + calidad + '}';
    }
    
    
}