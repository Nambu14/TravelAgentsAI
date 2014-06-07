/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia2014;

import Agents.AgenteLugar;
import Things.CronogramaTransporte;
import Things.CronogramaTransporte.Calidad;
import Things.LugarWrapper;
import Things.Paquete;
import java.util.ArrayList;
import java.util.Collections;
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
    public static void main(String[] args) throws Exception {

        String[] service = {"Pileta", "Pool", "Baño", "sala de estar", "juegos"};
        String[] service2 = {"Baño", "sala de estar", "juegos"};
        LugarWrapper lugarcito = new LugarWrapper("mundo canino", AgenteLugar.Tipo.HOTEL, 5, service);
        LugarWrapper lugarcito3 = new LugarWrapper("mundo Mundial", AgenteLugar.Tipo.CABAÑA, 2, service2);
        String temp = lugarcito.lugarToString();
        LugarWrapper lugarcito2 = LugarWrapper.stringToLugar(temp);
        lugarcito2.setNombre("Mundo Felino");
        lugarcito2.setServicios(service2);
        Paquete paq = new Paquete();
        paq.setOrigen("Corrientes");
        paq.setDestino("Resistencia");
        paq.setPrecio((float) 9847.23);
        paq.setCantidadPersonas(5);
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(2015, 6, 7);
        paq.setFechaInicialInferior(cal);
        GregorianCalendar cal2 = new GregorianCalendar();
        cal2.set(2012, 2, 10);
        paq.setFechaInicialSuperior(cal2);
        paq.setDuracion(10);
        paq.setAlojamiento(lugarcito3);
        paq.setPonderacion((float) 1);
        paq.setCalidadTransporte(Calidad.EJECUTIVO);
        String str = paq.toStringForMessage();
        Paquete paq2 = new Paquete();
        try {
            paq2 = Paquete.stringToPaquete(str);
        } catch (Exception ex) {
            Logger.getLogger(IA2014.class.getName()).log(Level.SEVERE, null, ex);
        }
        paq2.setOrigen("guatemala");
        paq2.setAlojamiento(lugarcito);
        paq2.setPrecio(7500);
        Paquete paq3 = Paquete.stringToPaquete(str);
        paq3.setPrecio(4000);
        paq3.setCalidadTransporte(Calidad.SEMICAMA);
        System.out.println("calidad de paquete pedido: " + paq.getCalidadPaquete());
        System.out.println("calidad de propuesta paq2: " + paq2.getCalidadPaquete());
        System.out.println("calidad de propuesta paq3: " + paq3.getCalidadPaquete());
        System.out.println("heuristica de paq2: " + paq2.heuristica(paq));
        System.out.println("heuristica de paq3: " + paq3.heuristica(paq));
        paq2.setHeuristica(paq);
        System.out.println(paq2.getHeuristica());
        paq3.setHeuristica(paq);
        System.out.println(paq3.getHeuristica());
        if (paq2.compareTo(paq3) > 0) {
            System.out.println("Paquete paq2 es mejor");
        } else if (paq2.compareTo(paq3) < 0) {
            System.out.println("Paquete paq3 es mejor");
        } else {
            System.out.println("son iguales");
        }
        ArrayList<Paquete> ofertas = new ArrayList<>();
        ofertas.add(paq2);
        ofertas.add(paq3);
        Collections.sort(ofertas);
        for (Paquete oferta : ofertas) {
            System.out.println(oferta.getHeuristica() + " " + oferta.toStringForMessage());
        }
        Collections.reverse(ofertas);
        for (Paquete oferta : ofertas) {
            System.out.println(oferta.getHeuristica() + " " + oferta.toStringForMessage());
        }
        System.out.println(paq.daysBetween());
        System.out.println(paq2.daysBetween());
        System.out.println(paq3.daysBetween());
        GregorianCalendar asd = new GregorianCalendar();
        System.out.println(Paquete.daysBetween(asd, cal));


    }

}
