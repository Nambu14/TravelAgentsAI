/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Agents.AgenteLugar.Tipo;
import Things.LugarWrapper;
import Things.Paquete;
import Ventanas.VentanaLugar;
import Ventanas.VentanaMostrarLugar;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Lucas
 */
public class AgenteLugar extends Agent {

    private String ciudad;

    
    private int precioPersona;
    private String[] servicios;
       
    private float[] descuentoPorPersonas = new float[1];
    private float[] descuentoPorAnticipacion = new float[1];
    private float[] descuentoPorCantidadDeDias = new float [1];




    
    public enum Tipo {

        HOTEL, APART, CABAÑA, HOSTAL
    };
    private String nombre;
    private int calidad;
    private Tipo tipo;
    private VentanaLugar myGui;
    private VentanaMostrarLugar mostrarGui;
    private String args1;
    private int args2;
    
    @Override
    protected void setup() {
        nombre = this.getLocalName();
        
        //Registro en paginas amarillas
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Lugar");
        sd.setName(nombre);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        
        //Inicialización de atributos
        Object[] args = this.getArguments();
        if (args!=null && args.length>0){
            args1=args[0].toString();
            args2= Integer.parseInt(args[1].toString());
            //para el caso de la creación de escenarios
            cargarLugar(args1,args2);
            agregarComportamiento();
            
            
        }else{
            myGui = new VentanaLugar(this);
            myGui.setVisible(true);
        }    
        
        
    }

    // Métodos llamados desde la interfaz, donde ya se crean los arreglos
    public void definirLugar(String ciudad, int precio, int calidad, Tipo tipo) {
        this.ciudad = ciudad;
        precioPersona = precio;
        this.calidad = calidad;
        this.tipo = tipo;
    }

    public void asignarServicios(String[] servicios) {
        this.servicios = servicios;
    }

    public void asignarDescuentoPersonas(float[] dtoPsas) {
        descuentoPorPersonas = dtoPsas;
    }

    public void asignarDescuentoDias(float[] dtoDias) {
        descuentoPorCantidadDeDias = dtoDias;
    }
    
    public String[] getServicios() {
        return servicios;
    }

    public float[] getDescuentoPorPersonas() {
        return descuentoPorPersonas;
    }

    public float[] getDescuentoPorAnticipacion() {
        return descuentoPorAnticipacion;
    }

    public float[] getDescuentoPorCantidadDeDias() {
        return descuentoPorCantidadDeDias;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCalidad() {
        return calidad;
    }

    public void asignarDescuentoAnticipación(float[] dtoAnticipacion) {
        descuentoPorAnticipacion = dtoAnticipacion;
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    public int getPrecioPersona() {
        return precioPersona;
    }

    public String getCiudad() {
        return ciudad;
    }
    
        
    private void cargarLugar(String argsCiudad, int argES) {
        
        descuentoPorAnticipacion = new float[91];
        descuentoPorPersonas = new float[17];
        descuentoPorCantidadDeDias = new float[31];
        descuentoPorPersonas[0] = 0;
        descuentoPorAnticipacion[0]=0;
        descuentoPorCantidadDeDias[0] = 0;
        
        
        switch (argES){
            case 1:
                //Primer escenario: descuento de 5% desde 10 dias de anticipacion y aumentando diariamente en 0,5%
                //Descuentos por días: Desde 10 dias 0%, desde 20 5%, desde 30 10%
                //Descuento por cantidad de personas: no hay
                definirDescuentosEscenario(5, 0,20,30, 0,0);
                definirLugar(argsCiudad, 100, 2, Tipo.HOTEL);
                
                //servicios
                String [] serviciosDef= {"AA/CC - Calefacción", "TV con Cable", "Internet WiFi", "Servicio de Limpieza"};
                asignarServicios(serviciosDef);
                break;
            case 2: 
                //Segundo escenario: descuento de 7% desde 10 dias de anticipacion y aumentando diariamente en 0,7%
                //Descuentos por días: Desde 10 dias 2%, desde 20 8%, desde 30 15%
                //Descuento por cantidad de personas: desde 8 3%, desde 16 6%;
                definirDescuentosEscenario(7, 2, 8, 15, 3,6);
                definirLugar(argsCiudad, 200,4,Tipo.HOTEL);
                //servicios
                String [] serviciosDef2={"AA/CC - Calefacción", "Media Pensión", "Garage", "Salón de Juegos", "Piscina", "TV con Cable", "Internet WiFi", "Servicio de Limpieza", "Servicio al Cuarto", "Gimnasio"};
                asignarServicios(serviciosDef2);
                break;
            case 3:
                //Tercer escenario: descuento de 2% desde 10 dias de anticipacion y aumentando diariamente en 0,2%
                //Descuentos por días: Desde 10 dias 10%, desde 20 15%, desde 30 20%
                //Descuento por cantidad de personas: desde 8 5%, desde 16 11%;
                definirDescuentosEscenario(2, 10,15,20, 5,11);
                definirLugar(argsCiudad, 250,4,Tipo.APART);
                //SERVICIOS
                String [] serviciosDef3={"AA/CC - Calefacción", "Garage", "Piscina", "Sauna", "TV con Cable", "Internet WiFi", "Servicio de Limpieza", "Servicio al Cuarto", "Admiten Mascotas"};
                asignarServicios(serviciosDef3);
                break;
            case 4:
                //Cuarto escenario: descuento de 4% desde 10 dias de anticipacion y aumentando diariamente en 0,4%
                //Descuentos por días: Desde 10 dias 4%, desde 20 12%, desde 30 26%
                //Descuento por cantidad de personas: desde 8 6%, desde 16 15%;
                definirDescuentosEscenario(4, 4,12,26, 6,15);
                definirLugar(argsCiudad, 120,3,Tipo.CABAÑA);
                //SERVICIOS
                String [] serviciosDef4={"AA/CC - Calefacción", "Garage", "Piscina", "TV con Cable", "Internet WiFi", "Servicio de Limpieza", "Admiten Mascotas"};
                asignarServicios(serviciosDef4);
                break;
                
        }
        
            
    }

    @Override
    public String toString() {
        return "AgenteLugar{" + "ciudad=" + ciudad + ", servicios=" + servicios + ", nombre=" + nombre + ", calidad=" + calidad + ", tipo=" + tipo + '}';
    }
    public void agregarComportamiento(){
        addBehaviour(new RecibirPedido());
        addBehaviour(new MostrarInformacion());
    }
    
    
    private void definirDescuentosEscenario(float dtoAnti10, float DtoDias10, float DtoDias20, float DtoDias30, float dto8Psas, float dto16Psas) {
        //Descuentos Anticipacion
        for(int i=0; i<10;i++){
            descuentoPorAnticipacion[i]=0;
        }
        descuentoPorAnticipacion[10]=dtoAnti10/100;
        for(int i=11; i<descuentoPorAnticipacion.length;i++){
            descuentoPorAnticipacion[i]=descuentoPorAnticipacion[i-1]+dtoAnti10/1000;
        }
        
        //Descuentos Cantidad de dias
        for(int i=0; i<10;i++){
            descuentoPorCantidadDeDias[i]=0;
        }
        for(int i=10; i<20;i++){
            descuentoPorCantidadDeDias[i]=DtoDias10/100;
        }
        for(int i=20; i<30;i++){
            descuentoPorCantidadDeDias[i]=DtoDias20/100;
        }
        descuentoPorCantidadDeDias[30]=DtoDias30/100;
        
        
        //Descuentos cantidad de personas
        for(int i=0; i<8;i++){
            descuentoPorPersonas[i]=0;
        }
        for(int i=8; i<16;i++){
            descuentoPorPersonas[i]=dto8Psas/100;
        }
        descuentoPorPersonas[16]=dto16Psas/100;
    }

    
    private class MostrarInformacion extends CyclicBehaviour{
        private ACLMessage infoM;

        @Override
        public void action() {
            MessageTemplate mtInfo = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            infoM = myAgent.receive(mtInfo);
            if (infoM != null) {
                mostrarDatos();
            }
        }
        
    }
    
    
    private class RecibirPedido extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // si ya se aplicaron descuentos los valores en el paquete van a ser 0 (CantPsas, duracion, fecha)
                ACLMessage respuestaLugar = msg.createReply();
                Paquete pref;
                pref = Paquete.stringToPaquete(msg.getContent());
                //Si el alojamiento no queda en la ciudad de destino envía un REFUSE
                if (pref.getDestino().equalsIgnoreCase(getCiudad())) {
                    LugarWrapper alojamiento = new LugarWrapper(nombre, tipo, calidad, servicios);
                    pref.setAlojamiento(alojamiento);
                    if (pref.getCantidadPersonas() > 0) {
                        respuestaLugar.setPerformative(ACLMessage.PROPOSE);
                        pref.setPrecio(precioPersona * pref.getCantidadPersonas());
                        pref.setCantidadPersonas(-1*pref.getCantidadPersonas());
                        pref.setPresupuestoMax(0);
                    } else if ((pref.getCantidadPersonas() < 0) && (-1*pref.getCantidadPersonas())<descuentoPorPersonas.length){
                        respuestaLugar.setPerformative(ACLMessage.PROPOSE);
                        float dto = descuentoPorPersonas[-1*pref.getCantidadPersonas()];
                        pref.setPresupuestoMax(dto);
                        pref.setCantidadPersonas(0);
                    } else if (pref.getDuracion() != 0 && pref.getDuracion()<descuentoPorCantidadDeDias.length) {
                        respuestaLugar.setPerformative(ACLMessage.PROPOSE);
                        float dto = pref.getPresupuestoMax() + descuentoPorCantidadDeDias[pref.getDuracion()];
                        pref.setPresupuestoMax(dto);
                        pref.setDuracion(0);
                    } else if (pref.getAnticipacion() != 0 && pref.getAnticipacion()<descuentoPorAnticipacion.length) {
                        respuestaLugar.setPerformative(ACLMessage.PROPOSE);
                        float dto = pref.getPresupuestoMax() + descuentoPorAnticipacion[pref.getAnticipacion()];
                        pref.setPresupuestoMax(dto);
                        pref.setAnticipacion(0);
                    } else {
                        respuestaLugar.setPerformative(ACLMessage.INFORM);
                    }
                    String nuevoPrecio = pref.toStringForMessage();
                    respuestaLugar.setContent(nuevoPrecio);
                    send(respuestaLugar);
                } else {
                    respuestaLugar.setPerformative(ACLMessage.REFUSE);
                    respuestaLugar.setContent(ciudad);
                    myAgent.send(respuestaLugar);
                }
            } else {
                block();
            }

        }
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    private void mostrarDatos(){
        mostrarGui = new VentanaMostrarLugar(this);
        mostrarGui.setVisible(true);
    }

    public float getCalidadGeneral() {
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
                System.out.println("No se tiene calidad");
        }
        calidadServicios = (float) 0.1f * servicios.length;
        return (calidadEstrella + calidadServicios + calidadTipo);
    }
}
