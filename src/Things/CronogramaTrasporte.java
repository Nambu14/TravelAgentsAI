/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Things;

import java.util.ArrayList;

/**
 *
 * 
 */
public class CronogramaTrasporte {
    public String origen;
    public String destino;
    public float precio;
    public int capacidad;
    public static enum Comodidad {Ejecutivo, Cama, Semicama};
    public Comodidad comodidad;
    public static enum Dias {Lunes, Martes, Miercoles, Jueves, Sabado, Domingo};
    public ArrayList<Dias> salidas;

    public CronogramaTrasporte(String origen, String destino, float precio, int capacidad, Comodidad comodidad, ArrayList<Dias> salidas) {
        this.origen = origen;
        this.destino = destino;
        this.precio = precio;
        this.capacidad = capacidad;
        this.comodidad = comodidad;
        this.salidas = salidas;
    }
    
    
}
