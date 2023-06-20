/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Panel.java to edit this template
 */
package sae.Vue;


import sae.Vue.ChooseSommetDialog;
import sae.Vue.ChooseFoncDialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sae.Logiciel;
import sae.Model.Graphe.Sommet;
import sae.Model.Noeud;

/**
 * Panel Proposant la recherche manuelle de sommets ainsi que de choisir une fonctio
 * @author gheri
 */
public class MenuForm extends java.awt.Panel {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    
    /**
     * Attribut correspondant son ID indiquant d'ou vient la selection d'un sommet
     */
    private static int sourceID = 2;
    
    /**
     * Attribut correspondant au logiciel avec qui on communique
     */
    Logiciel l;
    
    /**
     * Attributs correspondants aux sommets selectionnés
     */
    private Sommet s1 = null;
    private Sommet s2 = null;
    
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="CONSTRUCTEUR">
    
    public MenuForm(Logiciel newl,int width,int height) {
        initComponents();
        
        l = newl;
        this.setSize(new Dimension(width,height));
        this.setVisible(true);
       
       /**
        * Listener en classe Anonyme pour lancer la fonction searchSommet et gerer le retour de celle ci pour le sommet S1
        */
       searchButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                s1 = searchSommet();
                if (s1 == null){
                    JOptionPane.showMessageDialog(null,"Veuillez choisir un sommet Valide dans un graphe Valide","Selection Incorrecte",JOptionPane.ERROR_MESSAGE);
                    s1Label.setText("S1");
                }
                else{
                s1Label.setText("S1 : " + s1.getNom());
                sendLogiciel();
                }
            }
       });
       
       /**
        * Listener en classe Anonyme pour lancer la fonction searchSommet et gerer le retour de celle ci pour le sommet S2
        */
       searchButton2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                s2 = searchSommet();
                if (s2 == null){
                    JOptionPane.showMessageDialog(null,"Veuillez choisir un sommet Valide dans un graphe Valide","Selection Incorrecte",JOptionPane.ERROR_MESSAGE);
                    s2Label.setText("S2");
                }
                else{
                s2Label.setText("S2: " + s2.getNom());
                sendLogiciel();
                }
            }
        });
       
       
       /**
        * Listener en class Anonyme pour chercher une fonction 
        */
       searchFoncButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
               // en fonction du sommet on propose certaines fonctions
               if ( l.graphCheck()){
                if (s1 == null && s2 == null){
                    l.listerFonctions(0);
                }
                
                else if ((s1 == null && s2 != null) || (s1 != null && s2 == null)){
                    l.listerFonctions(1);
                } 
                else{
                    l.listerFonctions(2);
                }
            }
            }
           
       });
       
       // On ajoute un listener sur les boutons supp pour deselectionner un sommet
       delButton1.addMouseListener(new suppListener());
       delButton2.addMouseListener(new suppListener());
        
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PRIVATE METHODES">
    
    /**
     * Méthode pour lancer la JDialog de recherche de sommet
     * @return 
     */
    private Sommet searchSommet(){
        List<Sommet> sommets = new ArrayList<>();
        
        // On genere une liste de sommets et on gere le cas ou l'utilisateur n'a pas choisi de graphe
        List<Noeud> tmpList = l.generationNoeuds();
        if ( tmpList == null) { return null;}
        
        for (Noeud tmp : tmpList){
            sommets.add(tmp.getS());
        }
        
        // On recupere la reponse de la choixDialog
        ChooseSommetDialog choixDialog = new ChooseSommetDialog(null,true,sommets);
        choixDialog.setLocation(l.getL().getX()/2,l.getL().getY()/2);
        Sommet response = choixDialog.ShowDialog();
       
        return response;
    }

    /**
     * Methode pour communiquer au logiciel les resultats de la selection
     */
    private void sendLogiciel(){
        if (s1 == null && s2 == null){}
        else if (s1 != null && s2 == null) {l.noeudClique(s1,sourceID);}
        else if (s2 == null && s2 != null) {l.noeudClique(s2,sourceID);}
        else {l.noeudsCliques(s1,s2,sourceID);}
    }
    
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PUBLIC METHODES">
    
    /**
     * Methode pour communiquer au menuForm la selection d'un nouveau Sommet
     * @param s 
     */
    public void newSelectionne(Sommet s){
        if (s != null){
        if (s1 == null){
            s1 = s;
            s1Label.setText("S1 : " + s1.getNom());
        }
        else{
            s2 = s;
            s2Label.setText("S2 : " + s2.getNom());
        }
        }
        else{
            s1 = null;
            s1Label.setText("S1");
            s2 = null;
            s2Label.setText("S2");
        }
        
    }
    
    /**
     * Methode pour verifier la position (S1 ou S2) d'un sommet
     * @param s
     * @return 
     */
    public boolean checkS1(Sommet s){
        if (s1 == s){return true;}
        else {return false;}
    }
    
    /**
     * Methode pour recuper le seul sommet selectionné
     * @return 
     */
    public Sommet getSelectionne(){
        if((s1 == null && s2 == null) || (s1!= null && s2!=null)){
            return null;
        }
        else{
            if (s1 != null ){return s1;}
            else return s2;
        }
    }
    
    /**
     * Methode pour recuperer les deux sommets selectionnés
     * @return 
     */
    public Sommet[] getSelectionnes(){
        if (s1 == null || s2 == null){
            return null;
        }
        else{
            Sommet[] tmp = new Sommet[2];
            tmp[0] = s1;
            tmp[1] = s2;
            return tmp;
        }
    }
    // </editor-fold>
    
    /**
     * Listener pour Deselectionner un sommet 
     */
    public class suppListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {
            if (delButton1 == e.getComponent() ){
                l.deselectionnerNoeud(s1,s2);
                s1 = null;
                s1Label.setText("S1");
                
            }
            else{
                l.deselectionnerNoeud(s2,s1);
                s2 = null;
                s2Label.setText("S2");
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        s1Label = new javax.swing.JLabel();
        searchButton1 = new javax.swing.JButton();
        delButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        s2Label = new javax.swing.JLabel();
        searchButton2 = new javax.swing.JButton();
        delButton2 = new javax.swing.JButton();
        foncPanel = new javax.swing.JPanel();
        choixFoncLabel = new javax.swing.JLabel();
        searchFoncButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Choix Sommet");
        jPanel1.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        s1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s1Label.setText("S1");
        jPanel2.add(s1Label);

        searchButton1.setText("Rechercher");
        searchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(searchButton1);

        delButton1.setText("Supp");
        delButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(delButton1);

        s2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s2Label.setText("S2");
        jPanel3.add(s2Label);

        searchButton2.setText("Rechercher");
        searchButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(searchButton2);

        delButton2.setText("Supp");
        jPanel3.add(delButton2);

        choixFoncLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        choixFoncLabel.setText("Choix Fonctionnalité");
        foncPanel.add(choixFoncLabel);

        searchFoncButton.setText("Choisir");
        searchFoncButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFoncButtonActionPerformed(evt);
            }
        });
        foncPanel.add(searchFoncButton);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(foncPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(foncPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButton1ActionPerformed

    private void searchButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButton2ActionPerformed

    private void searchFoncButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFoncButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFoncButtonActionPerformed

    private void delButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_delButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel choixFoncLabel;
    private javax.swing.JButton delButton1;
    private javax.swing.JButton delButton2;
    private javax.swing.JPanel foncPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel s1Label;
    private javax.swing.JLabel s2Label;
    private javax.swing.JButton searchButton1;
    private javax.swing.JButton searchButton2;
    private javax.swing.JButton searchFoncButton;
    // End of variables declaration//GEN-END:variables
}
