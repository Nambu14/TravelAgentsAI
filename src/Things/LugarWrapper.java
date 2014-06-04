/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Things;

import Agents.AgenteLugar.Tipo;

/**
 *
 * @author Lucas
 */
public class LugarWrapper {
    //Información relativa al lugar donde se alojará nombre, tipo(hotel y demas), calidad, servicios.
    private String nombre;
    private Tipo tipo;
    private int calidad;
    private String[] servicios;

    public LugarWrapper(String nombre, Tipo tipo, int calidad, String[] servicios) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.calidad = calidad;
        this.servicios = servicios;
    }
    
    public LugarWrapper(){
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getCalidad() {
        return calidad;
    }

    public void setCalidad(int calidad) {
        this.calidad = calidad;
    }

    public String[] getServicios() {
        return servicios;
    }

    public void setServicios(String[] servicios) {
        this.servicios = servicios;
    }
    
    public String lugarToString(){
        String str;
        String temp;
        temp = servicios[0];
        for (int i = 1; i < servicios.length; i++){
            temp = temp + "-_-" + servicios[i];
        }
        str = nombre + "--" + tipo + "--" + calidad + "--"  + temp;
        return str;
    }
    
    public static LugarWrapper stringToLugar(String str){
        LugarWrapper lugar = new LugarWrapper();
        String[] wrapper = str.split("--");
        lugar.setNombre(wrapper[0]);
        switch (wrapper[1]){
            case "HOTEL": lugar.setTipo(Tipo.HOTEL); break;
            case "APART": lugar.setTipo(Tipo.APART); break;
            case "CABAÑA": lugar.setTipo(Tipo.CABAÑA); break;
            case "HOSTAL": lugar.setTipo(Tipo.HOSTAL); break;
        }
        lugar.setCalidad(Integer.parseInt(wrapper[2]));
        String[] servicios = wrapper[3].split("-_-");
        lugar.setServicios(servicios);
        return lugar;
    }
    
}
