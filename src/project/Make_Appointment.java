/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package project;

import com.google.maps.errors.ApiException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import project.Available_Doctors;
import project.Doctor;
import static project.Geocoding.*;
import static project.DBLoginData.*;

/**
 *
 * @author Dwipa
 */
public class Make_Appointment extends javax.swing.JFrame implements ActionListener, ItemListener, ChangeListener {

    /**
     * Creates new form Make_Appointment
     */
    Connection conn = null;
    PreparedStatement stmt,pst = null;
    String ID;
    String Street;
    String Number;
    String PC;
    String City;
    String LatLng;
    private String keyword;

    public Make_Appointment(String id) {
        ID = id;
        initComponents();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);   // to get window in center
    }

    private Make_Appointment() {
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

        jPanel1 = new javax.swing.JPanel();
        lbOther = new javax.swing.JLabel();
        sliderRadius = new javax.swing.JSlider();
        btnBack = new javax.swing.JButton();
        label = new javax.swing.JLabel();
        labelHP = new javax.swing.JLabel();
        lbRadius = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cbProblem = new javax.swing.JComboBox<>();
        cbArea = new javax.swing.JComboBox<>();
        tfOther = new javax.swing.JTextField();
        cbSpecific = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(235, 248, 254));

        lbOther.setText("Other :");

        sliderRadius.setBackground(new java.awt.Color(235, 248, 254));
        sliderRadius.setMajorTickSpacing(10);
        sliderRadius.setMaximum(50);
        sliderRadius.setMinorTickSpacing(5);
        sliderRadius.setPaintLabels(true);
        sliderRadius.setPaintTicks(true);
        sliderRadius.setToolTipText("");
        sliderRadius.setValue(0);
        sliderRadius.addChangeListener(this);

        btnBack.setText("Back");
        btnBack.setActionCommand("");
        btnBack.addActionListener(this);

        labelHP.setText("Select Health Problem :");

        lbRadius.setText("Search Radius :");

        btnSearch.setText("Search");
        btnSearch.addActionListener(this);

        cbProblem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Physically", "Mentally" }));
        cbProblem.setSelectedIndex(-1);
        cbProblem.addItemListener(this);
        cbProblem.addActionListener(this);

        cbArea.addItemListener(this);

        cbSpecific.setToolTipText("");
        cbSpecific.addItemListener(this);
        cbSpecific.addActionListener(this);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pharmacy.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelHP)
                    .addComponent(lbOther)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(7, 7, 7))
                    .addComponent(lbRadius))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sliderRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cbArea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbProblem, 0, 140, Short.MAX_VALUE)
                    .addComponent(cbSpecific, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfOther)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHP)
                    .addComponent(cbProblem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(cbArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSpecific, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfOther, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbOther))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sliderRadius, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbRadius, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBack)
                        .addComponent(btnSearch))
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == btnSearch) {
            Make_Appointment.this.btnSearchActionPerformed(evt);
        }
        else if (evt.getSource() == cbProblem) {
            Make_Appointment.this.cbProblemActionPerformed(evt);
        }
        else if (evt.getSource() == cbSpecific) {
            Make_Appointment.this.cbSpecificActionPerformed(evt);
        }
        else if (evt.getSource() == btnBack) {
            Make_Appointment.this.btnBackActionPerformed(evt);
        }
    }

    public void itemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getSource() == cbProblem) {
            Make_Appointment.this.cbProblemItemStateChanged(evt);
        }
        else if (evt.getSource() == cbArea) {
            Make_Appointment.this.cbAreaItemStateChanged(evt);
        }
        else if (evt.getSource() == cbSpecific) {
            Make_Appointment.this.cbSpecificItemStateChanged(evt);
        }
    }

    public void stateChanged(javax.swing.event.ChangeEvent evt) {
        if (evt.getSource() == sliderRadius) {
            Make_Appointment.this.sliderRadiusStateChanged(evt);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        try {

            conn = DriverManager.getConnection(url, uname, password);
            String query;
            
            query = "SELECT * FROM register WHERE ID=?";   //Veränderung 17.01.2022 5 -> 6 ?
            stmt = conn.prepareStatement(query);            
            stmt.setString(1, ID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Street = rs.getString("street");
                Number = rs.getString("hnr");
                PC = rs.getString("postcode");
                City = rs.getString("city");
            }

        } catch (HeadlessException | SQLException e) {

            JOptionPane.showMessageDialog(null, e);
        }
        int radius = sliderRadius.getValue() * 1000;
        try {
            LatLng = geolocation(Street, Number, PC, City);
        } catch (IOException | ApiException | InterruptedException ex) {
            Logger.getLogger(Make_Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query2 = "SELECT in_type FROM register WHERE ID=?";
        try {
            pst = conn.prepareStatement(query2);
            pst.setString(1, ID);
            ResultSet rs2 = pst.executeQuery();
            rs2.next();
            String InsuranceType = rs2.getString("in_type");

            if ((cbArea.getSelectedItem() == "Joints" || cbArea.getSelectedItem() == "Back") && (cbSpecific.getSelectedItem() != "-Not available-")) {
                keyword = "chiropraktiker";
            } else if (cbArea.getSelectedItem() == "-Not available-" || cbSpecific.getSelectedItem() == "-Not available-") {
                keyword = tfOther.getText();
            } else if (cbArea.getSelectedItem() == "Face" && cbSpecific.getSelectedItem() == "Skin") {
                keyword = "dermatologe";
            } else if (cbSpecific.getSelectedItem() == "Teeth Problems") {
                keyword = "zahnarzt";
            } else if (cbSpecific.getSelectedItem() == "Nasal Congestion" || (cbArea.getSelectedItem() == "Chest" && (cbSpecific.getSelectedItem() == "Pain" || cbSpecific.getSelectedItem() == "Numbness")) || (cbArea.getSelectedItem() == "Legs" && (cbSpecific.getSelectedItem() == "Pain" || cbSpecific.getSelectedItem() == "Numbness")) || (cbArea.getSelectedItem() == "Arm" && (cbSpecific.getSelectedItem() == "Pain" || cbSpecific.getSelectedItem() == "Numbness")) || (cbArea.getSelectedItem() == "Head" && (cbSpecific.getSelectedItem() == "Headache" || cbSpecific.getSelectedItem() == "Migraine"))) {
                keyword = "hausarzt";
            } else if (cbArea.getSelectedItem() == "Feet" && (cbSpecific.getSelectedItem() == "Blisters" || cbSpecific.getSelectedItem() == "Ingrown Toenail" || cbSpecific.getSelectedItem() == "Fungal Nail Infection")) {
                keyword = "podiatrist";
            } else if (cbArea.getSelectedItem() == "Abdomen" && cbSpecific.getSelectedItem() != "-Not available-") {
                keyword = "gastroenterologie";
            } else if ((cbArea.getSelectedItem() == "Face" && cbSpecific.getSelectedItem() == "Nose bleeding") || cbSpecific.getSelectedItem() == "Hearing Problems") {
                keyword = "hno";
            } else if (cbSpecific.getSelectedItem() == "Eye Problems") {
                keyword = "augenarzt";
            } else if (cbProblem.getSelectedItem() == "Mentally") {
                keyword = "psychiatrist";
            }
            Doctor[] doctors_list = null;
            doctors_list = generate_doctors_list(LatLng, radius, keyword, InsuranceType);
            System.out.println(keyword);
            Available_Doctors newFrame = new Available_Doctors(ID, doctors_list);
            newFrame.setVisible(true);
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(Make_Appointment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Make_Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cbProblemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProblemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbProblemActionPerformed

    private void cbProblemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProblemItemStateChanged
        // TODO add your handling code here:
        if (cbProblem.getSelectedItem() == "Physically") {
            cbArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Head", "Arm", "Legs", "Feet", "Chest", "Abdomen", "Joints", "Face", "Back", "-Not available-"}));

        }
        if (cbProblem.getSelectedItem() == "Mentally") {
            cbArea.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Anxiety", "Depression", "Self-harm", "-Not available-"}));
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"---"}));
        }
    }//GEN-LAST:event_cbProblemItemStateChanged

    private void cbAreaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbAreaItemStateChanged
        // TODO add your handling code here:
        if (cbArea.getSelectedItem() == "Head") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Headache", "Migraine", "Hearing Problems", "Eye Problems", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Arm") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pain", "Numbness", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Legs") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pain", "Numbness", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Feet") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pain", "Numbness", "Blisters", "Ingrown Toenail", "Fungal Nail Infection", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Chest") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pain", "Numbness", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Abdomen") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Stomach ache", "Crohn's Disease", "Hemorrhoids", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Joints") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pain", "Numbness", "Stiffness", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Face") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Skin", "Nose bleeding", "Teeth problems", "Nasal Congestion", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "Back") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pain", "Numbness", "Stiffness", "-Not available-"}));
        }
        if (cbArea.getSelectedItem() == "-Not available-") {
            cbSpecific.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"-Not available-"}));
        }
    }//GEN-LAST:event_cbAreaItemStateChanged

    private void cbSpecificItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSpecificItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSpecificItemStateChanged

    private void cbSpecificActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSpecificActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSpecificActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        LoggedinAsPatient pg = new LoggedinAsPatient(ID);
        pg.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void sliderRadiusStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderRadiusStateChanged
        // TODO add your handling code here:
        label.setText(sliderRadius.getValue() + "km");
    }//GEN-LAST:event_sliderRadiusStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Make_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Make_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Make_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Make_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Make_Appointment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbArea;
    private javax.swing.JComboBox<String> cbProblem;
    private javax.swing.JComboBox<String> cbSpecific;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel labelHP;
    private javax.swing.JLabel lbOther;
    private javax.swing.JLabel lbRadius;
    private javax.swing.JSlider sliderRadius;
    private javax.swing.JTextField tfOther;
    // End of variables declaration//GEN-END:variables
}