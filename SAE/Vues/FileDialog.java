/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package sae.Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Fenetre modale permettant de selectionner les fichiers csv nécessaires à la création du graphe
 * @author gheri
 */
public class FileDialog extends java.awt.Dialog {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Atrribut correspondant à un tableau de Deux Chaines de caractères contenent les 2 chemins relatifs des fichiers csv nécessaires à la creation du graphe
     */
    private String[] files = new String[2];
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTEUR">
    /**
     * Constructeur permettant de créer la fenetre
     * @param parent
     * @param modal 
     */
    public FileDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tailleWindow();
        
        // Ajout des evenements
        chooseButton1.addActionListener(new button1Listener());
        chooseButton2.addActionListener(new button2Listener());
        cancelButton.addActionListener(new cancelListener());
        confirmButton.addActionListener(new registerListener());
        
    }
    // </editor-fold>
      
    // <editor-fold defaultstate="collapsed" desc="LISTENERS">
    /**
     * Listener pour e bouton Numero 1 qui permet d'obtenir le chemin relatif du fichier 1
     */
    public class button1Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String result = chooseFile();
            files[0] = result;
            confirmation1Label.setText(result);
    }
    }
    
    /**
     * Listener pour le bouton Numero 2 qui permet d'obtenir le chemin relatif du fichier 2
     */
    public class button2Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String result = chooseFile();
            files[1] = result;
            confirmation2Label.setText(result);
            
    }
    }
    
    /**
     * Listener pour le bouton annuler qui permet de fermer la fenetre
     */
    public class cancelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            files = null;
            dispose();
        }
        
    }
    
    /**
     * Listener pour bouton Numero 1 qui permet d'obtenir le chemin relatif du fichier 1
     */
    public class registerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(files[0] == null || files[1] == null){
                errorMessage();
            }
            else{
                dispose();
            }
            
            
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PRIVATE METHODES">
    /**
     * Methode pour le message d'erreur
     */
    private void errorMessage(){
        JOptionPane.showMessageDialog(this, "Veuillez choisir 2 fichiers valides", "Fichiers non renseignés", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Methode choisir un fichier et retourner le chemin relatif
     * @return 
     */
    private String chooseFile(){
        String Result = null;
        
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichier CSV (*.csv)","csv"));
        chooser.setApproveButtonText("Ouvrir");
        chooser.setDialogTitle("Choisir le fichier");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION){
            Result = chooser.getSelectedFile().getAbsolutePath();
        }else if(result == JFileChooser.CANCEL_OPTION);
        
        return Result;
    }
    
    /**
     * Methode pour dimensioner et positionner la fenetre
     */
    private void tailleWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);

        setLocation(x, y);
    }
    // </editor-fold>
    
    // Methode showDialog
    public String[] showDialog(){
        this.setVisible(true);
        return files;
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
        chooseButton1 = new javax.swing.JToggleButton();
        chooseButton2 = new javax.swing.JToggleButton();
        cancelButton = new javax.swing.JToggleButton();
        confirmButton = new javax.swing.JToggleButton();
        confirmation1Label = new javax.swing.JLabel();
        confirmation2Label = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setText("Premier Fichier CSV : Liste Adjacence :");

        jLabel2.setText("Deuxieme Fichier CSV : Liste successeurs :");

        chooseButton1.setText("Choisir");
        chooseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseButton1ActionPerformed(evt);
            }
        });

        chooseButton2.setText("Choisir");
        chooseButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseButton2ActionPerformed(evt);
            }
        });

        cancelButton.setText("Annuler");

        confirmButton.setText("Confirmer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(chooseButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(confirmation1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(chooseButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(confirmation2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chooseButton1))
                .addGap(18, 18, 18)
                .addComponent(confirmation1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(chooseButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmation2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(confirmButton))
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void chooseButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chooseButton1ActionPerformed

    private void chooseButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chooseButton2ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JToggleButton chooseButton1;
    private javax.swing.JToggleButton chooseButton2;
    private javax.swing.JToggleButton confirmButton;
    private javax.swing.JLabel confirmation1Label;
    private javax.swing.JLabel confirmation2Label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
