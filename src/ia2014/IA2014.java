/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ia2014;

import Agents.AgenteLugar;
import Things.CronogramaTransporte.Calidad;
import Things.LugarWrapper;
import Things.Paquete;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lucas
 */
public class IA2014 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String[] service = {"Pileta", "Pool", "Baño", "sala de estar", "juegos"};
        String[] service2 = {"Baño", "sala de estar", "juegos"};
        LugarWrapper lugarcito = new LugarWrapper("mundo canino", AgenteLugar.Tipo.HOTEL, 5, service);
        String temp = lugarcito.lugarToString();
        System.out.println(temp);
        LugarWrapper lugarcito2 = LugarWrapper.stringToLugar(temp);
        System.out.println(lugarcito2.lugarToString());
        lugarcito2.setNombre("Mundo Felino");
        System.out.println(lugarcito2.lugarToString());
        System.out.println(lugarcito.lugarToString());
        lugarcito2.setServicios(service2);
        Paquete paq = new Paquete();
        paq.setOrigen("Corrientes");
        paq.setDestino("Resistencia");
        paq.setPresupuestoMax((float) 9847.23);
        paq.setCantidadPersonas(5);
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(2015, 10, 25);
        paq.setFechaInicialInferior(cal);
        GregorianCalendar cal2 = new GregorianCalendar();
        cal2.set(2016, 2, 29);
        paq.setFechaInicialSuperior(cal2);
        paq.setDuracion(10);
        paq.setAlojamiento(lugarcito);
        paq.setPonderacion((float) 0.5);
        paq.setCalidad(Calidad.BUSSINES);
        System.out.println(paq.toStringForMessage());
        String str = paq.toStringForMessage();
        Paquete paq2 = new Paquete();
        try {
            paq2 = Paquete.stringToPaquete(str);
        } catch (Exception ex) {
            Logger.getLogger(IA2014.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(paq2.toStringForMessage());
        paq2.setOrigen("guatemala");
        paq2.setAlojamiento(lugarcito2);
        paq2.setPresupuestoMax(17500);
        System.out.println(paq2.toStringForMessage());
        System.out.println(paq.toStringForMessage());
        System.out.println((double)lugarcito.getCalidadGeneral());
        System.out.println(paq.getCalidadPaquete());
        System.out.println(paq2.getCalidadPaquete());
        System.out.println(paq2.heuristica(paq));
    }
    
}
