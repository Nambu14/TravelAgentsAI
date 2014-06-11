/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Things;

import Agents.AgenteTransporte.TipoEmpresa;
import Things.CronogramaTransporte.Calidad;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas
 */
public final class Paquete implements Comparable {

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
    //Información relativa al lugar donde se alojará nombre, tipo(hotel y demas), calidad, servicios.
    private LugarWrapper alojamiento;
    //Ponderaciones que le da el turista a la importancia que tengan
    //a su criterio el confort y el precio a pagar.
    private float ponderacionPrecio;
    private float ponderacionCalidad;
    //Calidades ofrecidas para los tipos de transporte, ya sea aéreo o terrestre.
    private Calidad calidadTransporte;
    //Precio a pagar por el paquete
    private float precio;
    private float heuristica = -50f;
    private int anticipacion = 1;
    private TipoEmpresa tipoTransporte;

    public Paquete(String origen, String destino, float presupuestoMax,
            int cantidadPersonas, GregorianCalendar fechaInicialInferior,
            GregorianCalendar fechaInicialSuperior, int duracion,
            LugarWrapper alojamiento, float ponderacionPrecio,
            float ponderacionCalidad, Calidad calidad) throws Exception {
        this.origen = origen;
        this.destino = destino;
        this.presupuestoMax = presupuestoMax;
        this.cantidadPersonas = cantidadPersonas;
        this.fechaInicialInferior = fechaInicialInferior;
        this.fechaInicialSuperior = fechaInicialSuperior;
        this.duracion = duracion;
        this.alojamiento = alojamiento;
        this.setPonderacion(ponderacionPrecio, ponderacionCalidad);
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
        this.alojamiento = new LugarWrapper();
        try {
            this.setPonderacion((float) 0.5, (float) 0.5);
        } catch (Exception ex) {
            Logger.getLogger(Paquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.calidadTransporte = Calidad.SEMICAMA;
    }

    public void setPonderacion(float precio, float calidad) throws Exception {
        if ((precio + calidad) == 1) {
            this.setPonderacionCalidad(calidad);
            this.setPonderacionPrecio(precio);
        } else {
            throw new Exception("Las ponderaciones para el precio y la calidad no suman 1");
        }
    }

    public static Paquete stringToPaquete(String string) {
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
         11: precio
         12: anticipacion
         13: tipoTransporte
         */
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
        fechaInferiorIncial.set(ano1, mes1, dia1);
        paquete.setFechaInicialInferior(fechaInferiorIncial.getTime());
        //tratamiento de la segunda fecha: fechaInicialSuperior
        String[] fechaSuperior = str[5].split("--");
        GregorianCalendar fechaSuperiorIncial = new GregorianCalendar();
        int ano2 = Integer.parseInt(fechaSuperior[2]);
        int mes2 = Integer.parseInt(fechaSuperior[1]);
        int dia2 = Integer.parseInt(fechaSuperior[0]);
        fechaSuperiorIncial.set(ano2, mes2, dia2);
        paquete.setFechaInicialSuperior(fechaSuperiorIncial);
        //fin tratamiento fechas
        paquete.setDuracion(Integer.parseInt(str[6]));
        //Tratamiento de alojamiento
        LugarWrapper lugar = LugarWrapper.stringToLugar(str[7]);
        paquete.setAlojamiento(lugar);
        paquete.setPonderacionPrecio(Float.parseFloat(str[8]));
        paquete.setPonderacionCalidad(Float.parseFloat(str[9]));
        //Tratamiento calidad
        String p = str[10];
        switch (p) {
            case "EJECUTIVO":
                paquete.setCalidadTransporte(Calidad.EJECUTIVO);
                break;
            case "CAMA":
                paquete.setCalidadTransporte(Calidad.CAMA);
                break;
            case "SEMICAMA":
                paquete.setCalidadTransporte(Calidad.SEMICAMA);
                break;
            case "PRIMERACLASE":
                paquete.setCalidadTransporte(Calidad.PRIMERACLASE);
                break;
            case "BUSSINES":
                paquete.setCalidadTransporte(Calidad.BUSSINES);
                break;
            case "TURISTA":
                paquete.setCalidadTransporte(Calidad.TURISTA);
                break;
            default:
                paquete.setCalidadTransporte(null);
        }
        paquete.setPrecio(Float.parseFloat(str[11]));
        paquete.setAnticipacion(Integer.parseInt(str[12]));
        p = str[13];
        switch (p) {
            case "AEREA":
                paquete.setTipoTransporte(TipoEmpresa.AEREA);
                break;
            case "TERRESTRE":
                paquete.setTipoTransporte(TipoEmpresa.TERRESTRE);
                break;
            default:
                paquete.setTipoTransporte(null);
        }
        return paquete;
    }

    public int getAnticipacion() {
        return anticipacion;
    }

    public void setAnticipacion(int i) {
        anticipacion = i;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPonderacionPrecio() {
        return ponderacionPrecio;
    }

    public TipoEmpresa getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(TipoEmpresa tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
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

    public void setFechaInicialInferior(Date fechainicial) {
        this.fechaInicialInferior.setTime(fechainicial);
    }

    public GregorianCalendar getFechaInicialSuperior() {
        return fechaInicialSuperior;
    }

    public void setFechaInicialSuperior(GregorianCalendar fechaInicialSuperior) {
        this.fechaInicialSuperior.set(fechaInicialSuperior.get(Calendar.YEAR), fechaInicialSuperior.get(Calendar.MONTH),
                fechaInicialSuperior.get(Calendar.DAY_OF_MONTH));
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public LugarWrapper getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(LugarWrapper alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Calidad getCalidadTransporte() {
        return calidadTransporte;
    }

    private int getDiaFechaInicialInferior() {
        int var1 = fechaInicialInferior.get(Calendar.DAY_OF_MONTH);
        return var1;
    }

    private int getMesFechaInicialInferior() {
        int var1 = fechaInicialInferior.get(Calendar.MONTH);
        return var1;
    }

    private int getAnoFechaInicialInferior() {
        int var1 = fechaInicialInferior.get(Calendar.YEAR);
        return var1;
    }

    private int getDiaFechaInicialSuperior() {
        int var1 = fechaInicialSuperior.get(Calendar.DAY_OF_MONTH);
        return var1;
    }

    private int getMesFechaInicialSuperior() {
        int var1 = fechaInicialSuperior.get(Calendar.MONTH);
        return var1;
    }

    private int getAnoFechaInicialSuperior() {
        int var1 = fechaInicialSuperior.get(Calendar.YEAR);
        return var1;
    }

    public void setPonderacion(float ponderacionPrecio) {
        this.setPonderacionCalidad(1 - ponderacionPrecio);
        this.setPonderacionPrecio(ponderacionPrecio);
    }

    public String toStringForMessage() {
        String paqueteString = (origen + ",,," + destino + ",,," + presupuestoMax
                + ",,," + cantidadPersonas + ",,,"
                + getDiaFechaInicialInferior() + "--" + getMesFechaInicialInferior()
                + "--" + getAnoFechaInicialInferior() + ",,," + getDiaFechaInicialSuperior()
                + "--" + getMesFechaInicialSuperior() + "--" + getAnoFechaInicialSuperior()
                + ",,," + duracion + ",,," + alojamiento.lugarToString() + ",,,"
                + ponderacionPrecio + ",,," + ponderacionCalidad
                + ",,," + calidadTransporte + ",,," + precio + ",,," + anticipacion + ",,,"
                + tipoTransporte);
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
        return "Paquete{" + "origen=" + origen + ", destino=" + destino + ", presupuestoMax=" + presupuestoMax + ", cantidadPersonas=" + cantidadPersonas + ", fechaInicialInferior=" + fechaInicialInferior + ", fechaInicialSuperior=" + fechaInicialSuperior + ", duracion=" + duracion + ", alojamiento=" + alojamiento + ", ponderacionPrecio=" + ponderacionPrecio + ", ponderacionCalidad=" + ponderacionCalidad + ", calidadTransporte=" + calidadTransporte + ", precio=" + precio + ", heuristica=" + heuristica + ", anticipacion=" + anticipacion + ", tipoTransporte=" + tipoTransporte + '}';
    }

    public float getCalidadPaquete() {
        float calidadGeneral;
        calidadGeneral = alojamiento.getCalidadGeneral();
        if ((calidadTransporte == Calidad.PRIMERACLASE) || (calidadTransporte == Calidad.EJECUTIVO)) {
            calidadGeneral = calidadGeneral + 0.3f;
        } else if ((calidadTransporte == Calidad.BUSSINES) || (calidadTransporte == Calidad.CAMA)) {
            calidadGeneral = calidadGeneral + 0.1f;
        }
        return calidadGeneral;
    }

    public float heuristica(Paquete preferencias) {
        int puntosPorPrecio = 0;
        int puntosPorCalidad = 0;
        if (precio >= 1.3f * preferencias.getPrecio()) {
            puntosPorPrecio = -3;
        } else if ((precio >= (1.2f * preferencias.getPrecio())) && (precio < 1.3f * preferencias.getPrecio())) {
            puntosPorPrecio = -2;
        } else if ((precio >= (1.1f * preferencias.getPrecio())) && (precio < 1.2f * preferencias.getPrecio())) {
            puntosPorPrecio = -1;
        } else if ((precio > (0.9f * preferencias.getPrecio())) && (precio < 1.1f * preferencias.getPrecio())) {
            puntosPorPrecio = 0;
        } else if ((precio > (0.8f * preferencias.getPrecio())) && (precio <= 0.9f * preferencias.getPrecio())) {
            puntosPorPrecio = 1;
        } else if ((precio > (0.7f * preferencias.getPrecio())) && (precio <= 0.8f * preferencias.getPrecio())) {
            puntosPorPrecio = 2;
        } else if (precio <= 0.7f * preferencias.getPrecio()) {
            puntosPorPrecio = 3;
        }

        if (getCalidadPaquete() >= 1.3f * preferencias.getCalidadPaquete()) {
            puntosPorCalidad = 3;
        } else if ((getCalidadPaquete() >= (1.2f * preferencias.getCalidadPaquete())) && (getCalidadPaquete() < 1.3f * preferencias.getCalidadPaquete())) {
            puntosPorCalidad = 2;
        } else if ((getCalidadPaquete() >= (1.1f * preferencias.getCalidadPaquete())) && (getCalidadPaquete() < 1.2f * preferencias.getCalidadPaquete())) {
            puntosPorCalidad = 1;
        } else if ((getCalidadPaquete() > (0.9f * preferencias.getCalidadPaquete())) && (getCalidadPaquete() < 1.1f * preferencias.getCalidadPaquete())) {
            puntosPorCalidad = 0;
        } else if ((getCalidadPaquete() > (0.8f * preferencias.getCalidadPaquete())) && (getCalidadPaquete() <= 0.9f * preferencias.getCalidadPaquete())) {
            puntosPorCalidad = -1;
        } else if ((getCalidadPaquete() > (0.7f * preferencias.getCalidadPaquete())) && (getCalidadPaquete() <= 0.8f * preferencias.getCalidadPaquete())) {
            puntosPorCalidad = -2;
        } else if (getCalidadPaquete() <= 0.7f * preferencias.getCalidadPaquete()) {
            puntosPorCalidad = -3;
        }
        float valorEuristico;
        valorEuristico = puntosPorCalidad * ponderacionCalidad + puntosPorPrecio * ponderacionPrecio;
        return valorEuristico;
    }

    public void setCalidadTransporte(Calidad calidadTransporte) {
        this.calidadTransporte = calidadTransporte;
    }

    public void setHeuristica(Paquete pq) {
        this.heuristica = this.heuristica(pq);
    }

    public float getHeuristica() {
        return heuristica;
    }

    @Override
    public int compareTo(Object o) {
        Paquete paquete = (Paquete) o;
        if (this.heuristica > paquete.getHeuristica()) {
            return 1;
        } else if (this.heuristica < paquete.getHeuristica()) {
            return (-1);
        } else {
            return 0;
        }
    }

    //Método para calcular la diferencia en dias entre dos fechas
    public int daysBetween() {
        GregorianCalendar date = getFechaInicialInferior();
        long daysBetween = 0;
        while (date.before(getFechaInicialSuperior())) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        Long dias;
        dias = daysBetween;
        return dias.intValue();
    }

    public static int daysBetween(GregorianCalendar cal1, GregorianCalendar cal2) {
        long daysBetween = 0;
        while (cal1.before(cal2)) {
            cal1.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        Long dias;
        dias = daysBetween;
        return dias.intValue();
    }

    public String getTipoTransporteString() {
        if(tipoTransporte == TipoEmpresa.AEREA){
            return "AEREA";
        }else{
            return "TERRESTRE";
        }
    }
}
