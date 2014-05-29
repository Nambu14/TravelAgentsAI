/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Things;

import Agents.AgenteLugar;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas
 */
public final class Paquete {
    //Ciudad de origen del viaje.
    private String origen;
    //Ciudad de destino del viaje.
    private String destino;
    //Presupouesto máximo que una persona pretende gastar,
    //o en su defecto el precio del paquete armado por la Agencia.
    private float presupuestoMax;
    //Cantidad de personas que viajarán.
    private int cantidadPersonas;
    //Rango de fechas en las que se puede INICIAR el viaje.
    private GregorianCalendar fechaInicialInferior;
    private GregorianCalendar fechaInicialSuperior;
    //Duración del viaje.
    private int duracion;
    //Información relativa al lugar donde se alojará, calidad y precio.
    private String alojamiento;
    //Ponderaciones que le da el turista a la importancia que tengan
    //a su criterio el confort y el precio a pagar.
    private float ponderacionPrecio;
    private float ponderacionCalidad;
    //Calidades ofrecidas para los tipos de transporte, ya sea aéreo o terrestre.
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
    
    public Paquete() {
        this.origen = new String();
        this.destino = new String();
        this.presupuestoMax = 0;
        this.cantidadPersonas = 0;
        this.fechaInicialInferior = new GregorianCalendar();
        this.fechaInicialSuperior = new GregorianCalendar();
        this.duracion = 0;
        this.alojamiento = new String();
        try {
            this.setPonderacion((float)0.5, (float)0.5);
        } catch (Exception ex) {
            Logger.getLogger(Paquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.calidadTransporte = Calidad.SEMICAMA;
    }
    
    public void setPonderacion (float precio, float calidad) throws Exception{
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
        0: origen String
        1: destino String
        2: presupuestoMax float
        3: cantidadPersonas int
        4: fechaInicialInferior GregorianCalendar
        5: fechaInicialSuperior GregorianCalendar
        6: duracion int
        7: alojamiento String
        8: ponderacionPrecio float
        9: ponderacionCalidad float
        10: calidadTransporte Calidad
        */
        if(str.length==11){
            paquete.setOrigen(str[0]);
            paquete.setDestino(str[1]);
            paquete.setPresupuestoMax(Float.parseFloat(str[2]));
            paquete.setCantidadPersonas(Integer.parseInt(str[3]));
            //tratamiento de la primera fecha: fechaInicialInferior
            String[] fechaInferior = str[4].split("--");
            GregorianCalendar fechaInferiorIncial = new GregorianCalendar();
            int ano1 = Integer.parseInt(fechaInferior[2]);
            int mes1 = Integer.parseInt(fechaInferior[1]);
            int dia1 = Integer.parseInt(fechaInferior[0]);
            fechaInferiorIncial.set(ano1,mes1,dia1);
            paquete.setFechaInicialInferior(fechaInferiorIncial);
            //tratamiento de la segunda fecha: fechaInicialSuperior
            String[] fechaSuperior = str[5].split("--");
            GregorianCalendar fechaSuperiorIncial = new GregorianCalendar();
            int ano2 = Integer.parseInt(fechaSuperior[2]);
            int mes2 = Integer.parseInt(fechaSuperior[1]);
            int dia2 = Integer.parseInt(fechaSuperior[0]);
            fechaSuperiorIncial.set(ano2,mes2,dia2);
            paquete.setFechaInicialInferior(fechaInferiorIncial);
            //fin tratamiento fechas
            paquete.setDuracion(Integer.parseInt(str[6]));
            paquete.setAlojamiento(str[7]);
            paquete.setPonderacionPrecio(Float.parseFloat(str[8]));
            paquete.setPonderacionCalidad(Float.parseFloat(str[9]));
            //Tratamiento calidad
            String p = str[10];
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
        this.fechaInicialInferior.set(fechaInicialInferior.get(Calendar.YEAR),fechaInicialInferior.get(Calendar.MONTH),
                                        fechaInicialInferior.get(Calendar.DAY_OF_MONTH));
    }

    public GregorianCalendar getFechaInicialSuperior() {
        return fechaInicialSuperior;
    }

    public void setFechaInicialSuperior(GregorianCalendar fechaInicialSuperior) {
        this.fechaInicialSuperior.set(fechaInicialSuperior.get(Calendar.YEAR),fechaInicialSuperior.get(Calendar.MONTH),
                                        fechaInicialSuperior.get(Calendar.DAY_OF_MONTH));
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
    
    public int getDiaFechaInicialInferior(){
        int var1 = fechaInicialInferior.get(Calendar.DAY_OF_MONTH);
        return var1;
    }
    
    public int getMesFechaInicialInferior(){
        int var1 = fechaInicialInferior.get(Calendar.MONTH);
        return var1;
    }
    
    public int getAnoFechaInicialInferior(){
        int var1 = fechaInicialInferior.get(Calendar.YEAR);
        return var1;
    }
    
    public int getDiaFechaInicialSuperior(){
        int var1 = fechaInicialSuperior.get(Calendar.DAY_OF_MONTH);
        return var1;
    }
    
    public int getMesFechaInicialSuperior(){
        int var1 = fechaInicialSuperior.get(Calendar.MONTH);
        return var1;
    }
    
    public int getAnoFechaInicialSuperior(){
        int var1 = fechaInicialSuperior.get(Calendar.YEAR);
        return var1;
    }

    public String toStringForMessage() {
        String paqueteString = (origen + ",,," + destino + ",,," + presupuestoMax + 
                ",,," + cantidadPersonas + ",,," + 
                getDiaFechaInicialInferior() + "--" + getMesFechaInicialInferior() +
                "--"+getAnoFechaInicialInferior() + ",,," + getDiaFechaInicialSuperior() + 
                "--" + getMesFechaInicialSuperior() + "--" + getAnoFechaInicialSuperior() + 
                ",,," + duracion + ",,," + alojamiento + ",,," +
                ponderacionPrecio + ",,," + ponderacionCalidad + 
                ",,," + calidadTransporte);
        /*
        String[] paqueteSubString = paqueteString.split(",,,");
        if (paqueteSubString.length <> 11){
            throw new Exception("Paquete incompleto");
            }
        */
        return paqueteString;
    }

    @Override
    public String toString() {
        return "Paquete{" + "origen=" + origen + ", destino=" + destino + ", presupuestoMax=" + presupuestoMax + ", cantidadPersonas=" + cantidadPersonas + ", fechaInicialInferior=" + fechaInicialInferior + ", fechaInicialSuperior=" + fechaInicialSuperior + ", duracion=" + duracion + ", alojamiento=" + alojamiento + ", ponderacionPrecio=" + ponderacionPrecio + ", ponderacionCalidad=" + ponderacionCalidad + ", calidadTransporte=" + calidadTransporte + '}';
    }
    
}