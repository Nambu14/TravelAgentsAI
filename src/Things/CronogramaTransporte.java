/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Things;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 *
 */
public class CronogramaTransporte {

    private String origen;
    private String destino;
    private float precioPersona;
    private int capacidad;
    private ArrayList<DayOfWeek> salidas;

    public enum Calidad {

        EJECUTIVO, CAMA, SEMICAMA, PRIMERACLASE, BUSSINES, TURISTA
    };
    private Calidad calidad;

    /**
     *
     * @param origen
     * @param destino
     * @param precio
     * @param capacidad
     * @param salidas
     */
    public CronogramaTransporte(String origen, String destino, float precio, int capacidad, ArrayList<DayOfWeek> salidas) {
        this.origen = origen;
        this.destino = destino;
        this.precioPersona = precio;
        this.capacidad = capacidad;
        this.salidas = salidas;
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

    public float getPrecioPersona() {
        return precioPersona;
    }

    public void setPrecioPersona(float precio) {
        this.precioPersona = precio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList<DayOfWeek> getSalidas() {
        return salidas;
    }

    public void setSalidas(ArrayList<DayOfWeek> salidas) {
        this.salidas = salidas;
    }

    public boolean askForDate(GregorianCalendar fecha) {
        for (DayOfWeek estedia : salidas) {
            if (((fecha.get(Calendar.DAY_OF_WEEK) - 1) == estedia.getValue()) || ((fecha.get(Calendar.DAY_OF_WEEK) == 0) && (estedia.getValue() == 7))) {
                return true;
            }
        }
        return false;

    }
    
    public CronogramaTransporte(String origen, String destino, float precioPersona, int capacidad, ArrayList<DayOfWeek> salidas, Calidad calidad) {
        this.origen = origen;
        this.destino = destino;
        this.precioPersona = precioPersona;
        this.capacidad = capacidad;
        this.salidas = salidas;
        this.calidad = calidad;
    }

    public Calidad getCalidad() {
        return calidad;
    }

    public void setCalidad(Calidad calidad) {
        this.calidad = calidad;
    }

}
