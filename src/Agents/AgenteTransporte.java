/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Things.CronogramaTransporte;
import Things.Paquete;
import Ventanas.VentanaTransporte;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase transporte, es el agente encargado de representar a una empresa de
 * transporte específica.
 */
public class AgenteTransporte extends Agent {

    //Hasta determinada cantidad de personas hay descuento, esa cantidad es la longitud del arreglo
    private float[] descuentoPorPersonas = new float[1];
    private float[] descuentoPorAnticipacion = new float[1];
    //Descuento dado por promociones en determinados días.
    private float[] descuentoPorDias = new float[7];
    private String nombre;

    

    public enum TipoEmpresa {

        TERRESTRE, AEREA
    };
    private TipoEmpresa tipo;

    private ArrayList<CronogramaTransporte> rutas;
    private VentanaTransporte myGui;

    @Override
    protected void setup() {
        //Inicializar descuentos
        descuentoPorPersonas[0] = 0;
        descuentoPorAnticipacion[0]=0;
        for(int i=0; i<7; i++){
            descuentoPorDias[i]=0;
        }
        rutas = new ArrayList<>();
        nombre = getLocalName();
        
        Object [] args = getArguments();
        if (args!=null && args.length>0){
            //para el caso de la creación de escenarios
            cargarTransporte(args);
            addBehaviour(new RecibirPedido());
        }else{
            //para el caso de llamada desde la interfaz    
            myGui = new VentanaTransporte(this);
            myGui.setVisible(true);
        }    
        

        //Registro en paginas amarillas;
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Transporte");
        sd.setName(nombre);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        

    }

    //Quitar registro de las paginas amarillas
    /**
     *
     */
    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    
    public void agregarComportamiento(){
        addBehaviour(new RecibirPedido());
    }
    
    private void definirDescuentosEscenario(int d10anti, int d3psas, int d6Psas) {
        //descuentos anticipacion
        for(int i=0; i<10; i++){
            descuentoPorAnticipacion[i]= 0;
        }
        descuentoPorAnticipacion[10]=d10anti/100;
        for (int i=11; i< descuentoPorAnticipacion.length; i++){
            descuentoPorAnticipacion[i]= descuentoPorAnticipacion[i-1]+d10anti/1000;
        }
        
        //descuentos personas
        for(int i=0; i<3; i++){
            descuentoPorPersonas[i]=0;
        }
        for(int i=3; i<6; i++){
            descuentoPorPersonas[i]=d3psas;
        }
        descuentoPorPersonas[6]=d6Psas;
        
    }

    // Métodos llamados desde la interfaz, donde ya se crean los arreglos
    public void asignarDescuentoPersonas(float[] dtoPsas) {
        descuentoPorPersonas = dtoPsas;
    }

    public void asignarDescuentoDias(float[] dtoDias) {
        descuentoPorDias = dtoDias;
    }

    public void asignarDescuentoAnticipación(float[] dtoAnticipacion) {
        descuentoPorAnticipacion = dtoAnticipacion;
    }

    public void addCronogramas(ArrayList<CronogramaTransporte> rutas) {
        this.rutas = rutas;
    }
        
    public void setTransporte(TipoEmpresa tipo){
        nombre = this.getLocalName();
        this.tipo = tipo; 
    }
    
    private void cargarTransporte(Object[] args) {
        CronogramaTransporte cr;
        int escenario = Integer.parseInt(args[1].toString());
        descuentoPorAnticipacion = new float[91];
        descuentoPorPersonas = new float[7];
        descuentoPorPersonas[0] = 0;
        descuentoPorAnticipacion[0]=0;
        int precioEsc;
        ArrayList<DayOfWeek> salidasT = new ArrayList<>();
        salidasT.add(DayOfWeek.MONDAY);
        salidasT.add(DayOfWeek.TUESDAY);
        salidasT.add(DayOfWeek.WEDNESDAY);
        salidasT.add(DayOfWeek.THURSDAY);
        salidasT.add(DayOfWeek.FRIDAY);
        salidasT.add(DayOfWeek.SATURDAY);
        salidasT.add(DayOfWeek.SUNDAY);
        switch (escenario){
            case 1:
                //Primer escenario: descuento de 5% desde 10 dias de anticipacion y aumentando diariamente en 0,5%
                //Descuentos por días: Lunes a Jueves : 16%
                //Descuento por cantidad de personas: no hay
                definirDescuentosEscenario(5,0,0);
                for(int i= 0; i<=3; i++){
                    descuentoPorDias[i]= 16/100;
                }
                
                if(args[0].toString().equalsIgnoreCase("Buenos Aires")){
                    precioEsc=583;
                }else{precioEsc=213;}
                
                tipo = TipoEmpresa.TERRESTRE;
                cr= new CronogramaTransporte("Corrientes", args[0].toString(), precioEsc, 100, salidasT, CronogramaTransporte.Calidad.SEMICAMA);
                rutas.add(cr);
                break;
            case 2: 
                //Segundo escenario: descuento de 7% desde 10 dias de anticipacion y aumentando diariamente en 0,7%
                //Descuentos por días: no hay
                //Descuento por cantidad de personas: desde 3 3%, desde 6 6%;
                definirDescuentosEscenario(7,3,6);

                 if(args[0].toString().equalsIgnoreCase("Buenos Aires")){
                    precioEsc=624;
                }else{precioEsc=250;}
                 
                tipo = TipoEmpresa.TERRESTRE;
                cr= new CronogramaTransporte("Corrientes", args[0].toString(), precioEsc, 100, salidasT, CronogramaTransporte.Calidad.CAMA);
                rutas.add(cr); 
                break;
            case 3:
                //Tercer escenario: descuento de 2% desde 10 dias de anticipacion y aumentando diariamente en 0,2%
                //Descuentos por días: Miércoles Viernes y Domingo : 22%
                //Descuento por cantidad de personas: desde 3 5%, desde 6 11%;
                definirDescuentosEscenario(2,5,11);
                descuentoPorDias[2]= 22/100;
                descuentoPorDias[4]= 22/100;
                descuentoPorDias[6]= 22/100;
                
                precioEsc = 716;
                
                tipo = TipoEmpresa.TERRESTRE;
                cr= new CronogramaTransporte("Corrientes", args[0].toString(), precioEsc, 100, salidasT, CronogramaTransporte.Calidad.EJECUTIVO);
                rutas.add(cr);
                break;
            case 4:
                //Cuarto escenario: descuento de 4% desde 10 dias de anticipacion y aumentando diariamente en 0,4%
                //Descuentos por días: Miércoles : 18%
                //Descuento por cantidad de personas: desde 3 6%, desde 6 15%;
                definirDescuentosEscenario(4,6,15);
                descuentoPorDias[2]= 18/100;
                
                precioEsc=1600;
                
                tipo = TipoEmpresa.AEREA;
                cr= new CronogramaTransporte("Corrientes", args[0].toString(), precioEsc, 100, salidasT, CronogramaTransporte.Calidad.TURISTA);
                rutas.add(cr);
                break;
                
        }
        
    }

    private class RecibirPedido extends CyclicBehaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.CFP), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                    ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage respuestaT = msg.createReply();
                Paquete pref;
                pref = Paquete.stringToPaquete(msg.getContent());
                if (msg.getPerformative() == ACLMessage.CFP) {
                    //Verificar que existan rutas para los días de salida y vuelta
                    boolean existeRutaIda = false;
                    boolean existeRutaVuelta = true;
                    GregorianCalendar fechaSalida = pref.getFechaInicialInferior();
                    if (pref.daysBetween() == 0) {
                       
                        for (CronogramaTransporte ruta : rutas) {
                            if ((ruta.getOrigen().equalsIgnoreCase(pref.getOrigen())) && (ruta.getDestino().equalsIgnoreCase(pref.getDestino()))) {
                                if (ruta.askForDate(fechaSalida)) {
                                    existeRutaIda = true;
                                }
                                
                                if (existeRutaIda && existeRutaVuelta) {
                                    pref.setPresupuestoMax(0);
                                    pref.setCalidadTransporte(ruta.getCalidad());
                                    pref.setFechaInicialInferior(fechaSalida.getTime());
                                    pref.setPrecio(ruta.getPrecioPersona() * pref.getCantidadPersonas());
                                    break;
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < pref.daysBetween(); i++) {
                            for (CronogramaTransporte ruta : rutas) {
                                if ((ruta.getOrigen().equalsIgnoreCase(pref.getOrigen())) && (ruta.getDestino().equalsIgnoreCase(pref.getDestino()))) {
                                    if (ruta.askForDate(fechaSalida)) {
                                        existeRutaIda = true;
                                    }
                                    
                                    if (existeRutaIda && existeRutaVuelta) {
                                        pref.setPresupuestoMax(0);
                                        pref.setCalidadTransporte(ruta.getCalidad());
                                        pref.setFechaInicialInferior(fechaSalida.getTime());
                                        pref.setPrecio(ruta.getPrecioPersona() * pref.getCantidadPersonas());
                                        break;
                                    }
                                }

                            }
                            if (existeRutaIda && existeRutaVuelta) {
                                break;
                            }
                            fechaSalida.add(Calendar.DAY_OF_MONTH, 1);
                        }
                    }
                    if (existeRutaIda && existeRutaVuelta) {
                        pref.setTipoTransporte(tipo);
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        respuestaT.setContent(pref.toStringForMessage());
                        myAgent.send(respuestaT);
                    } else {
                        respuestaT.setPerformative(ACLMessage.REFUSE);
                        myAgent.send(respuestaT);
                    }
                } else if (msg.getPerformative() == ACLMessage.INFORM) {
                    //Dar descuentos
                    if (pref.getCantidadPersonas() != 0 && pref.getCantidadPersonas()<descuentoPorPersonas.length) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        float dbt = descuentoPorPersonas[pref.getCantidadPersonas()];
                        pref.setPresupuestoMax(dbt);
                        pref.setCantidadPersonas(0);
                    } else if (pref.getDuracion() != 0 && pref.getDuracion()< descuentoPorAnticipacion.length) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        GregorianCalendar cal = new GregorianCalendar();
                        float dbt = descuentoPorAnticipacion[Paquete.daysBetween(cal, pref.getFechaInicialInferior())];
                        pref.setPresupuestoMax(pref.getPresupuestoMax() + dbt);
                        pref.setDuracion(0);
                    } else if (pref.getAnticipacion() != 0 && pref.getAnticipacion()<descuentoPorDias.length) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        float dto = descuentoPorDias[(pref.getFechaInicialInferior().get(Calendar.DAY_OF_WEEK) - 1)];
                        pref.setPresupuestoMax(pref.getPresupuestoMax() + dto);
                        pref.setAnticipacion(0);
                    } else {
                        respuestaT.setPerformative(ACLMessage.INFORM);
                    }
                    respuestaT.setContent(pref.toStringForMessage());
                    myAgent.send(respuestaT);
                }
            } else {
                block();
            }
        }

    }

    public ArrayList<CronogramaTransporte> getRutas() {
        return rutas;
    }
    
}
