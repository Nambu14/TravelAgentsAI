/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agents;

import Things.CronogramaTransporte;
import Things.Paquete;
import Ventanas.VentanaMostrarTransporte;
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
    private VentanaMostrarTransporte mostrarGui;
    private String args1;
    private int args2;

    public enum TipoEmpresa {

        TERRESTRE, AEREA
    };
    private TipoEmpresa tipo;

    private ArrayList<CronogramaTransporte> rutas;
    private VentanaTransporte myGui;

    @Override
    protected void setup() {
        
        nombre = getLocalName();
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
        
        //Inicializar descuentos
        descuentoPorPersonas[0] = 0;
        descuentoPorAnticipacion[0]=0;
        for(int i=0; i<7; i++){
            descuentoPorDias[i]=0;
        }
        rutas = new ArrayList<>();
        
        //Inicializar atributos
        Object [] args = getArguments();
        if (args!=null && args.length>0){
            //para el caso de la creación de escenarios
            args1=args[0].toString();
            args2= Integer.parseInt(args[1].toString());
            cargarTransporte(args1,args2);
            agregarComportamiento();
            
        }else{
            //para el caso de llamada desde la interfaz    
            myGui = new VentanaTransporte(this);
            myGui.setVisible(true);
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
        addBehaviour(new MostrarInformacion());
    }
    
    private void mostrarDatos(){
        mostrarGui = new VentanaMostrarTransporte(this);
        mostrarGui.setVisible(true);
    }
    
    private void definirDescuentosEscenario(float d10anti, float d3psas, float d6Psas) {
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
            descuentoPorPersonas[i]=d3psas/100;
        }
        descuentoPorPersonas[6]=d6Psas/100;
        
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

    public float[] getDescuentoPorPersonas() {
        return descuentoPorPersonas;
    }

    public float[] getDescuentoPorAnticipacion() {
        return descuentoPorAnticipacion;
    }

    public float[] getDescuentoPorDias() {
        return descuentoPorDias;
    }

    public String getTipoString() {
        String tipoString;
        switch(tipo){
            case TERRESTRE:
                tipoString="TERRESTRE";
            break;
            default:
                tipoString="AEREA";
        }
        return tipoString;
    }
    
    
    
    private void cargarTransporte(String arg1, int arg2) {
        CronogramaTransporte cr;
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
        switch (arg2){
            case 1:
                //Primer escenario: descuento de 5% desde 10 dias de anticipacion y aumentando diariamente en 0,5%
                //Descuentos por días: Lunes a Jueves : 16%
                //Descuento por cantidad de personas: no hay
                definirDescuentosEscenario(5,0,0);
                for(int i= 0; i<=3; i++){
                    descuentoPorDias[i]= 16.0f/100;
                }
                
                if(arg1.equalsIgnoreCase("Buenos Aires")){
                    precioEsc=583;
                }else{precioEsc=213;}
                
                tipo = TipoEmpresa.TERRESTRE;
                cr= new CronogramaTransporte("Corrientes", arg1, precioEsc, 100, salidasT, CronogramaTransporte.Calidad.SEMICAMA);
                rutas.add(cr);
                break;
            case 2: 
                //Segundo escenario: descuento de 7% desde 10 dias de anticipacion y aumentando diariamente en 0,7%
                //Descuentos por días: no hay
                //Descuento por cantidad de personas: desde 3 3%, desde 6 7%;
                definirDescuentosEscenario(7,3,7);

                 if(arg1.equalsIgnoreCase("Buenos Aires")){
                    precioEsc=624;
                }else{precioEsc=250;}
                 
                tipo = TipoEmpresa.TERRESTRE;
                cr= new CronogramaTransporte("Corrientes", arg1, precioEsc, 100, salidasT, CronogramaTransporte.Calidad.CAMA);
                rutas.add(cr); 
                break;
            case 3:
                //Tercer escenario: descuento de 2% desde 10 dias de anticipacion y aumentando diariamente en 0,2%
                //Descuentos por días: Miércoles Viernes y Domingo : 22%
                //Descuento por cantidad de personas: desde 3 5%, desde 6 11%;
                definirDescuentosEscenario(2,5,11);
                descuentoPorDias[2]= 22.0f/100;
                descuentoPorDias[4]= 22.0f/100;
                descuentoPorDias[6]= 22.0f/100;
                
                precioEsc = 716;
                
                tipo = TipoEmpresa.TERRESTRE;
                cr= new CronogramaTransporte("Corrientes", arg1, precioEsc, 100, salidasT, CronogramaTransporte.Calidad.EJECUTIVO);
                rutas.add(cr);
                break;
            case 4:
                //Cuarto escenario: descuento de 4% desde 10 dias de anticipacion y aumentando diariamente en 0,4%
                //Descuentos por días: Miércoles : 18%
                //Descuento por cantidad de personas: desde 3 6%, desde 6 15%;
                definirDescuentosEscenario(4,6,15);
                descuentoPorDias[2]= 18.0f/100;
                
                precioEsc=1600;
                
                tipo = TipoEmpresa.AEREA;
                cr= new CronogramaTransporte("Corrientes", arg1, precioEsc, 100, salidasT, CronogramaTransporte.Calidad.TURISTA);
                rutas.add(cr);
                break;
                
        }
        
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
                    //Calcular anticipación
                    GregorianCalendar cal = new GregorianCalendar();
                    int anticipacion = Paquete.daysBetween(cal, pref.getFechaInicialInferior());
                    
                    //Dar descuentos
                    //Descuentos según cantidad de personas
                    if (pref.getCantidadPersonas() != 0 && descuentoPorPersonas.length>1) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        float dbt;
                        if(pref.getCantidadPersonas()<descuentoPorPersonas.length){
                            dbt = descuentoPorPersonas[pref.getCantidadPersonas()];
                        }else{
                            dbt = descuentoPorPersonas[descuentoPorPersonas.length - 1];
                        }
                        pref.setPresupuestoMax(dbt);
                        pref.setCantidadPersonas(0);
                        
                    //Descuento según días de anticipación
                    } else if (pref.getAnticipacion() != 0 && descuentoPorAnticipacion.length > 1) {
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        float dbt ;
                        if(anticipacion< descuentoPorAnticipacion.length){
                            dbt = descuentoPorAnticipacion[anticipacion];
                        }else{
                            dbt = descuentoPorAnticipacion[descuentoPorAnticipacion.length-1];
                        }
                        pref.setPresupuestoMax(pref.getPresupuestoMax() + dbt);
                        pref.setAnticipacion(0);
                        
                    //Descuentos según el día de viaje    
                    } else if (pref.getDuracion() != 0) {
                        int diaDeViaje = pref.getFechaInicialInferior().get(Calendar.DAY_OF_WEEK);
                        if (diaDeViaje == 0){
                            diaDeViaje = 6;
                        }else { 
                            diaDeViaje = diaDeViaje -1;
                        }
                        respuestaT.setPerformative(ACLMessage.PROPOSE);
                        float dto = descuentoPorDias[diaDeViaje];
                        pref.setPresupuestoMax(pref.getPresupuestoMax() + dto);
                        pref.setDuracion(0);
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
