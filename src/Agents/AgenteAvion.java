/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Agents;

import Things.CronogramaTransporteAereo;
import Ventanas.VentanaTransporteAereo;

/**
 *
 * @author Torre
 */
public class AgenteAvion extends AgentTransport {
    private CronogramaTransporteAereo [] rutas;
    private VentanaTransporteAereo myGui;
    
    @Override
    protected void setup() {
        myGui= new VentanaTransporteAereo(this);
        myGui.showGui();
    
    }
    public void asignarRutas(CronogramaTransporteAereo[] cronograma){
        rutas= cronograma;
    }
}
