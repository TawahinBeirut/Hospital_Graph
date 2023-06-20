/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package sae.Vue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import sae.Model.Graphe.Sommet;

/**
 * Fenetre modale pour choisir un sommmet
 * @author gheri
 */
public class ChooseSommetDialog extends java.awt.Dialog {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    
    /**
     * Attribut correspoondant à une liste de sommets à afficher
     */
    List<Sommet> sommetsList;
    
    /**
     * Attribut correspondant à un modele de liste par défaut pour la JTable
     */
    DefaultListModel defaultListModel = new DefaultListModel();
    
    /**
     * Attribut correspondant à la réponse renvoyée par la JDialog
     */
    Sommet response;
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTEUR">
    public ChooseSommetDialog(java.awt.Frame parent, boolean modal,List<Sommet> sommetsList) {
        super(parent, modal);
        initComponents();
        tailleWindow();
        this.sommetsList = sommetsList;
        
        // Intitialise les données
        bindData();
        
        /**
         * Listener en classe Anonyme pour le bouton Chercher qui appelle la fonction Search()
         */
        searchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchBar.getText());
            }
        });
        
        /**
         * Listener en classe Anonyme pour la JTable qui permet de selectionner un sommer de le renvoyer en reponse
         */
        myJTable.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                
                // On cherche le sommet selectionné
                for(Sommet tmp : sommetsList){
                    if (tmp.getNom().equals(myJTable.getSelectedValue())){
                        response = tmp;
                    }    
                }
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        
    }
    //</editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="PRIVATE METHODES">
    
    /**
     * Methode pour faire la liaison avec la JList
     */
    private void bindData(){
        for (Sommet tmp : sommetsList){
            defaultListModel.addElement(tmp.getNom());
        }
        
        myJTable.setModel(defaultListModel);
        myJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    }
    
    
    /**
     * Methode pour chercher dans les noms de sommets la valeur renseignée dans la barre de recherche
     * @param motRech String
     */
    public void search(String motRech){
        
        DefaultListModel itemsFiltres = new DefaultListModel();
        
        for ( Sommet tmp : sommetsList){
            String tmpName = tmp.getNom().toLowerCase();
            if (tmpName.contains(motRech.toLowerCase())){
                itemsFiltres.addElement(tmp.getNom());
            }
        }
        defaultListModel = itemsFiltres;
        myJTable.setModel(itemsFiltres);
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
    //</editor-fold>
  
    
    // Methode ShowDialog
    public Sommet ShowDialog(){
        this.setVisible(true);
        return response;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        myJTable = new javax.swing.JList<>();
        searchBar = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setText("Choisissez Votre Sommet");

        myJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(myJTable);

        searchBar.setText("");

        searchButton.setBackground(new java.awt.Color(0, 204, 255));
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(searchBar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 76, Short.MAX_VALUE)
                                .addComponent(jLabel1)))
                        .addGap(3, 3, 3)
                        .addComponent(searchButton)
                        .addGap(3, 3, 3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
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

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> myJTable;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    // End of variables declaration//GEN-END:variables
}
