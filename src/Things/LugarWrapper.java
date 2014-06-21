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
    private float calidadGeneral;

    public LugarWrapper(String nombre, Tipo tipo, int calidad, String[] servicios2) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.calidad = calidad;
        this.servicios = servicios2;
    }

    public void setCalidadGeneral() {
        this.calidadGeneral = getCalidadGeneral();
    }

    public LugarWrapper() {
        this.servicios = new String[0];
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

    public float getCalidadGeneral() {
        /*
         Este método devuelve la calidad general del alojamiento teniendo
         en cuenta el tipo de hospedaje que sea, la cantidad de "estrellas"
         que tenga y la cantidad de servicios que brinde a los clientes.
         */
        float calidadTipo;
        float calidadEstrella = 0;
        float calidadServicios;
        if (getTipo() == Tipo.HOTEL) {
            calidadTipo = 0.7f;
        } else if (getTipo() == Tipo.APART) {
            calidadTipo = 0.7f;
        } else if (getTipo() == Tipo.CABAÑA) {
            calidadTipo = 0.3f;
        } else {
            calidadTipo = 0f;
        }
        switch (calidad) {
            case 5:
                calidadEstrella = 1f;
                break;
            case 4:
                calidadEstrella = 0.6f;
                break;
            case 3:
                calidadEstrella = 0.3f;
                break;
            case 2:
                calidadEstrella = 0.1f;
                break;
            case 1:
                calidadEstrella = 0f;
                break;
            default:
                calidadEstrella = 0f;
        }
        calidadServicios = (float) 0.1f * servicios.length;
        return (calidadEstrella + calidadServicios + calidadTipo);
    }

    public String lugarToString() {
        String str;
        String temp;
        if (servicios.length != 0) {
            temp = servicios[0];
        } else {
            temp = new String();
        }
        for (int i = 1; i < servicios.length; i++) {
            temp = temp + "-_-" + servicios[i];
        }
        str = nombre + "--" + tipo + "--" + calidad + "--" + temp;
        return str;
    }

    public static LugarWrapper stringToLugar(String str) {
        LugarWrapper lugar = new LugarWrapper();
        String[] wrapper = str.split("--");
        if("null".equalsIgnoreCase(wrapper[0])){
            lugar.setNombre(null);
        }else{
            lugar.setNombre(wrapper[0]);
        }
        
        switch (wrapper[1]) {
            case "HOTEL":
                lugar.setTipo(Tipo.HOTEL);
                break;
            case "APART":
                lugar.setTipo(Tipo.APART);
                break;
            case "CABAÑA":
                lugar.setTipo(Tipo.CABAÑA);
                break;
            case "HOSTAL":
                lugar.setTipo(Tipo.HOSTAL);
                break;
            default:
                lugar.setTipo(null);
        }
        lugar.setCalidad(Integer.parseInt(wrapper[2]));
        if (wrapper.length == 4) {
            String[] servicios;
            servicios = wrapper[3].split("-_-");
            lugar.setServicios(servicios);
        }
        return lugar;
    }

}
