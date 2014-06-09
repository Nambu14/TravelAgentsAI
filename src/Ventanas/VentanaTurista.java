/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ventanas;

import Agents.AgenteLugar;
import Agents.AgenteLugar.Tipo;
import Agents.AgenteTransporte;
import Agents.AgenteTransporte.TipoEmpresa;
import Agents.AgenteTurista;
import Things.CronogramaTransporte.Calidad;
import Things.LugarWrapper;
import Things.Paquete;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Torre
 */
public class VentanaTurista extends javax.swing.JFrame {

    private AgenteTurista miAgente;
    private LugarWrapper alojamiento = new LugarWrapper();
    private Calendar fecha = Calendar.getInstance();
    
    /**
     * Creates new form VentanaTurista
     * @param a
     */
    public VentanaTurista(AgenteTurista a) {
        super(a.getLocalName());
	miAgente = a;
        initComponents();
                      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        origen = new javax.swing.JTextField();
        destino = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        duracion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        presupuesto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pondPrecio = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tipoTransporte = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        calidadTransporte = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tipoLugar = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        calidadLugar = new javax.swing.JComboBox();
        fechaMin = new com.toedter.calendar.JDateChooser();
        fechaMax = new com.toedter.calendar.JDateChooser();
        aceptar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        pondServicio = new javax.swing.JTextField();
        cantPsas = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Solicitar un Paquete");

        jLabel2.setText("Origen(*)");

        jLabel3.setText("Destino (*)");

        jLabel4.setText("Fechas de Salida posibles: dd/mm/aaaa (*)");

        jLabel5.setText("Mínima");

        jLabel6.setText("Máxima");

        jLabel7.setText("Duración (*)");

        duracion.setText("1");
        duracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                duracionKeyTyped(evt);
            }
        });

        jLabel8.setText("Cantidad de Personas (*)");

        jLabel9.setText("Presupuesto máximo ($)");

        presupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presupuestoActionPerformed(evt);
            }
        });
        presupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                presupuestoKeyTyped(evt);
            }
        });

        jLabel10.setText("Preferencias del usuario");

        jLabel11.setText("Ponderaciones servicios/precio");

        pondPrecio.setModel(new javax.swing.SpinnerNumberModel(50, 0, 100, 1));
        pondPrecio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pondPrecioStateChanged(evt);
            }
        });

        jLabel12.setText("Precio (%)");

        jLabel13.setText("Servicios (%)");

        jLabel14.setText("Transporte");

        jLabel15.setText("Tipo");

        tipoTransporte.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Terrestre", "Aereo" }));
        tipoTransporte.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tipoTransporteItemStateChanged(evt);
            }
        });
        tipoTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoTransporteActionPerformed(evt);
            }
        });

        jLabel16.setText("Comodidad");

        calidadTransporte.setEnabled(false);
        calidadTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calidadTransporteActionPerformed(evt);
            }
        });

        jLabel17.setText("Alojamiento");

        jLabel18.setText("Tipo");

        tipoLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "HOTEL", "APART", "HOSTAL", "CABAÑA" }));
        tipoLugar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tipoLugarItemStateChanged(evt);
            }
        });
        tipoLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoLugarActionPerformed(evt);
            }
        });

        jLabel19.setText("Calidad");

        calidadLugar.setEnabled(false);
        calidadLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calidadLugarActionPerformed(evt);
            }
        });

        fechaMin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fechaMinPropertyChange(evt);
            }
        });

        fechaMax.setEnabled(false);
        fechaMax.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fechaMaxPropertyChange(evt);
            }
        });

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        jLabel20.setText("(*) Campos Obligatorios");

        pondServicio.setText(" ");
        pondServicio.setEnabled(false);

        cantPsas.setModel(new javax.swing.SpinnerNumberModel(1, 1, 20, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(duracion, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(40, 40, 40)
                                .addComponent(cantPsas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(jLabel1))
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(31, 31, 31)
                                .addComponent(presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(origen, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(tipoLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addGap(18, 18, 18)
                                                .addComponent(pondPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addGap(18, 18, 18)
                                                .addComponent(tipoTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)
                                        .addComponent(pondServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel19))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(calidadLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(calidadTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(14, 14, 14)
                                    .addComponent(fechaMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(fechaMin, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(aceptar)
                        .addGap(33, 33, 33)
                        .addComponent(cancelar)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(origen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(destino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(fechaMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(duracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cantPsas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(presupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(pondPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(pondServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tipoTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(calidadTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(tipoLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(calidadLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(aceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void calidadTransporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calidadTransporteActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_calidadTransporteActionPerformed

    private void tipoTransporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoTransporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoTransporteActionPerformed

    private void tipoLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoLugarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoLugarActionPerformed

    private void calidadLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calidadLugarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calidadLugarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
        miAgente.doDelete();
        dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
      if(origen.getText()!=null && destino.getText()!= null && fechaMin.getDate() != null && fechaMax.getDate() !=null && duracion.getText() != null){
        
        //alojamiento
        alojamiento.setTipo(setearTipoLugar());
        if(calidadLugar.getSelectedItem() != null && calidadLugar.getSelectedIndex() != 0)
        alojamiento.setCalidad(Integer.parseInt(calidadLugar.getSelectedItem().toString()));
               
        
        //cantidad de personas
        int personas = Integer.parseInt(cantPsas.getValue().toString());
        
        //presupuesto maximo
        float presu;
        if (presupuesto.getText()==null){
            presu=(float)personas*10000;
        } else {
            presu=Float.parseFloat(presupuesto.getText());
        }
        
        //fechas
        //fechaMinPropertyChange(null);
        //fechaMaxPropertyChange(null);
        GregorianCalendar fechaInicial = new GregorianCalendar();
        fechaInicial.setTime(fechaMin.getDate());
        GregorianCalendar fechaFinal = new GregorianCalendar();
        fechaFinal.setTime(fechaMax.getDate());
        
        //duracion
        int dias = Integer.parseInt(duracion.getText());
        
        //Ponderaciones 
        float ponderacionP = Float.parseFloat(pondPrecio.getValue().toString());
        float ponderacionS = Float.parseFloat(pondServicio.getText());
        try {
            Paquete preferencias = new Paquete(origen.getText(), destino.getText(), presu, personas,fechaInicial, fechaFinal, dias, alojamiento, ponderacionP, ponderacionS, setearCalidadTransporte());
            preferencias.setTipoTransporte(setearTipoEmpresa());
            miAgente.setPreferencias(preferencias);
        } catch (Exception ex) {
            Logger.getLogger(VentanaTurista.class.getName()).log(Level.SEVERE, null, ex);
        }
        miAgente.agregarComportamiento();
      } else {
          JOptionPane.showMessageDialog(this, "Error: Los campos Obligatorios deben estar completos", "Error", JOptionPane.ERROR_MESSAGE);

      }
    }//GEN-LAST:event_aceptarActionPerformed

    private void fechaMinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fechaMinPropertyChange
        // TODO add your handling code here:
        if(fechaMin.getDate() != null){
        Calendar fechaViaje = fechaMin.getCalendar();
        if( fechaValida(fecha,fechaViaje)){
            fechaMax.setDate(fechaMin.getDate());
            fechaMax.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error: La fecha de salida debe ser posterior a la fecha actual", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_fechaMinPropertyChange

    private void tipoTransporteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tipoTransporteItemStateChanged
        // TODO add your handling code here:
        switch(tipoTransporte.getSelectedIndex()){
            case 0:
                calidadTransporte.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ""}));
                break;
            case 1:
                calidadTransporte.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"", "EJECUTIVO", "CAMA", "SEMICAMA"}));
                break;
            case 2:
                calidadTransporte.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"", "PRIMERA CLASE", "BUSSINES", "TURISTA"}));;
                break;
        }
        calidadTransporte.setEnabled(true);
    }//GEN-LAST:event_tipoTransporteItemStateChanged

    private void tipoLugarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tipoLugarItemStateChanged
        // TODO add your handling code here:
        switch(tipoLugar.getSelectedIndex()){
            case 0: 
                calidadLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] {""}));
                break;
            case 1: 
                calidadLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4", "5"}));
                break;
            case 2:
                calidadLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"", "1", "2", "3", "4", "5"}));
                break;
            case 3:
                calidadLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"", "1", "2", "3"}));
                break;
            case 4:
                calidadLugar.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"", "1", "2", "3"}));
                break;
        }
        calidadLugar.setEnabled(true);
    }//GEN-LAST:event_tipoLugarItemStateChanged

    private void pondPrecioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pondPrecioStateChanged
        // TODO add your handling code here:
        int valor = Integer.parseInt(pondPrecio.getValue().toString());
        pondServicio.setText(Integer.toString(100-valor));
        
    }//GEN-LAST:event_pondPrecioStateChanged

    private void fechaMaxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fechaMaxPropertyChange
        // TODO add your handling code here:
        if(fechaMax.getDate() != null){
        Calendar fechaViajeInicial = fechaMin.getCalendar();
        Calendar fechaViaje = fechaMax.getCalendar();
        if( fechaValida(fechaViajeInicial,fechaViaje)) {
        } else {
            JOptionPane.showMessageDialog(this, "Error: La fecha de salida máxima debe ser posterior a la fecha de salida mínima", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }   
    }//GEN-LAST:event_fechaMaxPropertyChange

    private void duracionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duracionKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_duracionKeyTyped

    private void presupuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_presupuestoKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_presupuestoKeyTyped

    private void presupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_presupuestoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar;
    private javax.swing.JComboBox calidadLugar;
    private javax.swing.JComboBox calidadTransporte;
    private javax.swing.JButton cancelar;
    private javax.swing.JSpinner cantPsas;
    private javax.swing.JTextField destino;
    private javax.swing.JTextField duracion;
    private com.toedter.calendar.JDateChooser fechaMax;
    private com.toedter.calendar.JDateChooser fechaMin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField origen;
    private javax.swing.JSpinner pondPrecio;
    private javax.swing.JTextField pondServicio;
    private javax.swing.JTextField presupuesto;
    private javax.swing.JComboBox tipoLugar;
    private javax.swing.JComboBox tipoTransporte;
    // End of variables declaration//GEN-END:variables

    private boolean fechaValida(Calendar fechaActual, Calendar fechaViaje) {
        if (fechaViaje != null && fechaActual != null){
        int diaActual = fechaActual.get(Calendar.DATE);
        int mesActual = fechaActual.get(Calendar.MONTH);
        int añoActual = fechaActual.get(Calendar.YEAR);
        int diaViaje = fechaViaje.get(Calendar.DATE);
        int mesViaje = fechaViaje.get(Calendar.MONTH);
        int añoViaje = fechaViaje.get(Calendar.YEAR);
        
        if(añoActual<añoViaje){
            return true;
        }else { 
            if(añoActual==añoViaje){
                if(mesActual<mesViaje){
                    return true;
                }else{
                    if(mesActual == mesViaje){
                        return diaActual<=diaViaje;
                    } else { 
                        return false;
                    }
                }
            } else { 
                return false;
            }
        }
        } else{
            return false;
        }
    } 

    private AgenteLugar.Tipo setearTipoLugar() {
        Tipo lugarTipo;
        switch(tipoLugar.getSelectedIndex()){
            case 1:
                lugarTipo = Tipo.HOTEL;
                break;
            case 2:
                lugarTipo = Tipo.APART;
                break;
            case 3:
                lugarTipo = Tipo.HOSTAL;
                break;
            case 4:
                lugarTipo = Tipo.CABAÑA;
                break;
            default:
                lugarTipo= null;
                
        }
        return lugarTipo;
    }

    private Calidad setearCalidadTransporte() {
        Calidad transpCalidad;
        switch(tipoTransporte.getSelectedItem().toString()){
            case "EJECUTIVO":
                transpCalidad = Calidad.EJECUTIVO;
                break;
            case "CAMA":
                transpCalidad = Calidad.CAMA;
                break;
            case "SEMICAMA":
                transpCalidad = Calidad.SEMICAMA;
                break;
            case "BUSSINES":
                transpCalidad = Calidad.BUSSINES;
                break;
            case "PRIMERA CLASE":
                transpCalidad = Calidad.PRIMERACLASE;
                break;
            case "TURISTA":
                transpCalidad = Calidad.TURISTA;
                break;
            default:
                transpCalidad = null;
        }
        return transpCalidad;
    }

    private AgenteTransporte.TipoEmpresa setearTipoEmpresa() {
        TipoEmpresa tipoEmpresa;
        switch (tipoTransporte.getSelectedIndex()){
            case 1:
                tipoEmpresa= TipoEmpresa.TERRESTRE;
                break;
            case 2:
                tipoEmpresa = TipoEmpresa.AEREA;
                break;
            default:
                tipoEmpresa = null;
        }
        return tipoEmpresa;       
    }
}
