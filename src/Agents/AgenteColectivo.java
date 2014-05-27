/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Things.CronogramaTransporteColectivo;
import Ventanas.VentanaTransporteColectivo;

/**
 *
 * @author Torre
 */
public class AgenteColectivo extends AgentTransport{
    private CronogramaTransporteColectivo [] rutas;
    private VentanaTransporteColectivo myGui;
   
    @Override
    protected void setup() {
        myGui= new VentanaTransporteColectivo(this);
        myGui.showGui();
    
    }
    
    public void asignarRutas(CronogramaTransporteColectivo[] cronograma){
        rutas= cronograma;
    }
}
