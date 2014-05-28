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
    //presupouesto máximo que una persona pretende gastar,
    //o en su defecto el precio del paquete armado por la Agencia
    private float presupuestoMax;
    private int cantidadPersonas;
    private GregorianCalendar fechaInicialInferior;
    private GregorianCalendar fechaInicialSuperior;
    private int duracion;
    private String alojamiento;
    private float ponderacionPrecio;
    private float ponderacionCalidad;
    public enum Calidad {EJECUTIVO, CAMA, SEMICAMA, PRIMERACLASE, BUSSINES, TURISTA};
    private Calidad calidadTransporte;

    public Paquete(String origen, String destino, float presupuestoMax,
            int cantidadPersonas, GregorianCalendar fechaInicialInferior, 
            GregorianCalendar fechaInicialSuperior, int duracion, 
            String alojamiento, float ponderacionPrecio, 
            float ponderacionCalidad, Calidad calidad) throws Exception {
        this.origen = origen;
        this.destino = destino;
        this.presupuestoMax = presupuestoMax;
        this.cantidadPersonas = cantidadPersonas;
        this.fechaInicialInferior = fechaInicialInferior;
        this.fechaInicialSuperior = fechaInicialSuperior;
        this.duracion = duracion;
        this.alojamiento = alojamiento;
        this.setPonderacion(presupuestoMax, ponderacionCalidad);
        this.calidadTransporte = calidad;
    }
    
    private Paquete() {
    }
        
    private void setPonderacion (float precio, float calidad) throws Exception{
        if ((precio+calidad)==1){
            this.setPonderacionCalidad(calidad);
            this.setPonderacionPrecio(precio);
        }else{
            throw new Exception("Las ponderaciones para el precio y la calidad no suman 1");
        }
    }
    
    public static Paquete stringToPaquete (String string) throws Exception{
        Paquete paquete;
        paquete = new Paquete();
        String[] str = string.split(",,,");
        /*
        1: origen String
        2: destino String
        3: presupuestoMax float
        4: cantidadPersonas int
        5: fechaInicialInferior GregorianCalendar
        6: fechaInicialSuperior GregorianCalendar
        7: duracion int
        8: alojamiento String
        9: ponderacionPrecio float
        10: ponderacionCalidad float
        11: calidadTransporte Calidad
        */
        if(str.length==11){
            paquete.setOrigen(str[1]);
            paquete.setDestino(str[2]);
            paquete.setPresupuestoMax(Float.parseFloat(str[3]));
            paquete.setCantidadPersonas(Integer.parseInt(str[4]));
            //Acá faltan el tema de las fechas
            //paquete.setFechaInicialInferior(new GregorianCalendar);
            paquete.setDuracion(Integer.parseInt(str[7]));
            paquete.setAlojamiento(str[8]);
            paquete.setPonderacionPrecio(Float.parseFloat(str[9]));
            paquete.setPonderacionCalidad(Float.parseFloat(str[10]));
            String p = str[11];
            switch (p){
                case "EJECUTIVO": paquete.setCalidad(Calidad.EJECUTIVO); break;
                case "CAMA": paquete.setCalidad(Calidad.CAMA); break;
                case "SEMICAMA": paquete.setCalidad(Calidad.SEMICAMA); break;
                case "PRIMERACLASE": paquete.setCalidad(Calidad.PRIMERACLASE); break;
                case "BUSSINES": paquete.setCalidad(Calidad.BUSSINES); break;
                case "TURISTA": paquete.setCalidad(Calidad.TURISTA); break;
                default: throw new Exception("Error dentro del paquete en calidad");     
            }
        }else{
            throw new Exception("Paquete incompleto");
        }
        return paquete;
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

    public String getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(String alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Calidad getCalidad() {
        return calidadTransporte;
    }

    public void setCalidad(Calidad calidad) {
        this.calidadTransporte = calidad;
    }

    public String toStringForMessage() {
        return  origen + ",,," + destino + ",,," + presupuestoMax + 
                ",,," + cantidadPersonas + ",,," + 
                fechaInicialInferior + ",,," + fechaInicialSuperior + 
                ",,," + duracion + ",,," + alojamiento + ",,," +
                ponderacionPrecio + ",,," + ponderacionCalidad + 
                ",,," + calidadTransporte + '}';
    }
}