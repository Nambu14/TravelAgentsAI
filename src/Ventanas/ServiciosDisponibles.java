/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ventanas;

import Agents.AgenteBuscar;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.tools.DummyAgent.DummyAgent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Torre
 */
public class ServiciosDisponibles extends javax.swing.JFrame {
    
    DFAgentDescription[] resultadosLugar;
    DFAgentDescription[] resultadosTransporte;
    DFAgentDescription[] resultadosAgencias;
    AgenteBuscar miAgente;

    /**
     * Creates new form ServiciosDisponibles
     */
    public ServiciosDisponibles(AgenteBuscar a) {
        super(a.getLocalName());
        miAgente = a;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        agenciasList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        lugaresList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        transportesList = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        verInfo = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Servicios Registrados");

        jLabel2.setText("Agencias");

        jLabel3.setText("Alojamientos");

        jLabel4.setText("Transportes");

        agenciasList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getAgenciasRegistradas();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(agenciasList);

        lugaresList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getLugaresRegistrados();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lugaresList);

        transportesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getTransportesRegistrados();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(transportesList);

        jLabel5.setText("*Para Selección múltiple presione la tecla Ctrl");

        verInfo.setText("Ver Información");
        verInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verInfoActionPerformed(evt);
            }
        });

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel2)
                .addGap(108, 108, 108)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(72, 72, 72))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(verInfo)
                        .addGap(94, 94, 94)
                        .addComponent(cancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(verInfo)
                    .addComponent(cancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
        miAgente.doDelete();
        dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void verInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verInfoActionPerformed
        
        ACLMessage mostrarInfo = new ACLMessage(ACLMessage.REQUEST);

        //Agregar agencias que hay que mostrar
        int[] seleccionA = agenciasList.getSelectedIndices();
        AID[] agenciasAID = new AID[seleccionA.length];
        for(int index=0; index<seleccionA.length; index++){
            agenciasAID[index]=resultadosAgencias[seleccionA[index]].getName();
        }
        
        for(AID ag: agenciasAID){
            mostrarInfo.addReceiver(ag);
        }
        
        //Agregar lugares que hay que mostrar
        int[] seleccionL = lugaresList.getSelectedIndices();
        AID[] lugaresAID = new AID[seleccionL.length];
        for(int index=0; index<seleccionL.length; index++){
            lugaresAID[index]=resultadosLugar[seleccionL[index]].getName();
        }
        
        for(AID lg: lugaresAID){
            mostrarInfo.addReceiver(lg);
        }
        
        //Agregar transportes que hay que mostrar
        int[] seleccionT = transportesList.getSelectedIndices();
        AID[] transportesAID = new AID[seleccionT.length];
        for(int index=0; index<seleccionT.length; index++){
            transportesAID[index]=resultadosLugar[seleccionT[index]].getName();
        }
        
        for(AID tr: transportesAID){
            mostrarInfo.addReceiver(tr);
        }
        
        mostrarInfo.setContent("Información");
        miAgente.send(mostrarInfo);
        
        
        
    }//GEN-LAST:event_verInfoActionPerformed
    private String[] getAgenciasRegistradas(){
        String[] agencias=null;
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Agencia de Turismo");
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.addServices(servicio);
        try {
            resultadosAgencias = DFService.search(miAgente, descripcion);
            agencias = new String[resultadosAgencias.length];
            for (int i = 0; i < resultadosAgencias.length; i++) {
                agencias[i] = resultadosAgencias[i].getName().getLocalName();
            }
        } catch (FIPAException ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
        return agencias;
    }
    
    private String[] getLugaresRegistrados(){
        String[] lugares= null;
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Lugar");
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.addServices(servicio);
        try {
            resultadosLugar = DFService.search(miAgente, descripcion);
            lugares = new String[resultadosLugar.length];
            for (int i = 0; i < resultadosLugar.length; i++) {
                lugares[i] = resultadosLugar[i].getName().getLocalName();
            }
        } catch (FIPAException ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
        return lugares;
        
    }
    
    private String[] getTransportesRegistrados(){
        String[] transportes = null;
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Transporte");
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.addServices(servicio);
        try {
            resultadosTransporte = DFService.search(miAgente, descripcion);
            transportes = new String[resultadosTransporte.length];
            for (int i = 0; i < resultadosTransporte.length; i++) {
                transportes[i] = resultadosTransporte[i].getName().getLocalName();
            }
        } catch (FIPAException ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
        return transportes;
            
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList agenciasList;
    private javax.swing.JButton cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList lugaresList;
    private javax.swing.JList transportesList;
    private javax.swing.JButton verInfo;
    // End of variables declaration//GEN-END:variables
}
