/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sae.Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sae.Logiciel;
import sae.Model.Graphe.Sommet;
import sae.Model.Noeud;
import sae.Model.TableModelModSommet;

/**
 *
 * @author gheri
 */
public class ModSommet extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    
    /**
     * Attribut correspondant au logiciel avec lequel on communique
     */
    private Logiciel logiciel;
    
    /**
     * Attribut correspondant au modele de notre JTable
     */
    private TableModelModSommet tableModelModSommet;
    
    /**
     * Atrribut correspondant à la liste de noeuds que l'on doit afficher
     */
    private List<Noeud> noeuds;
    
    /**
     * Attribut correspondant au noeud selectionné par l'utilisateur 
     */
    private Noeud noeudSelected;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTEUR">
    
    /**
     * Contructeur pour afficher la fenetre
     * @param logiciel
     * @param tableModelModSommet
     * @param title 
     */
    public ModSommet(Logiciel logiciel, TableModelModSommet tableModelModSommet,String title) {
        this.logiciel = logiciel;
        this.tableModelModSommet = tableModelModSommet;
        this.noeuds = tableModelModSommet.getList();
        
        
        initComponents();
        initializeModel();
        this.setTitle(title);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        
        // On mets les listneners
        noeudJTable.addMouseListener(new tableListener());
        modButton.addActionListener(new modListener());
        delButton.addActionListener(new delListener());
    }    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PRIVATE METHODES">
    /**
     * Methode pour retourner un index selon le type selectionné 
     * @param type
     * @return 
     */
    private int selectedIndex(String type){
        switch (type) {
            case "M":
                return 0;
            case "O":
                return 1;
            case "N":
                return 2;
            default:
                return -1;
        }
    }
    /**
     * Set le modele à notre JTable 
     */
    private void initializeModel(){
        this.noeudJTable.setModel(tableModelModSommet);
        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PRIVATE METHODES">
    /**
     * Methode pour gerer le cas ou la liste donnné est vide
     */
    public void checkList(){
        if (this.noeuds.isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Aucun Sommet Trouvé");
            this.dispose();
        }
    }@Override
    public void dispose(){
        logiciel.getL().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.dispose();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="LISTENERS">
    
    
    /**
     * Listener pour selectionner un element 
     */
    public class tableListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {}

        /* Fonction re-écrite pour selectionner un noeud*/ 
        @Override
        public void mousePressed(MouseEvent e) {
            // On recuperere le sommet à modifier
            Object mod = tableModelModSommet.getValueAt(noeudJTable.getSelectedRow(), 0);
                    
            for (Noeud tmp : noeuds){
                if (mod.equals(tmp.getS().getNom())){
                    noeudSelected = tmp;
                  
                    typeComboBox.setSelectedItem(tmp.getS().getType());
                    typeComboBox.setSelectedIndex(selectedIndex(tmp.getS().getType()));
                    xSpinner.setValue(tmp.getNoeudX());
                    ySpinner.setValue(tmp.getNoeudY());
                    repaint();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
    
   
    /**
     *  Listener pour modifier un element
     */
    public class modListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // Attributs de la modification
            String newType = (String) typeComboBox.getSelectedItem();
            int    newX    = (int) xSpinner.getValue();
            int    newY    = (int) ySpinner.getValue();
            
            if  (noeudSelected != null){
                
            // On modifie le sommet en fonction
            noeudSelected.getS().setType(newType);
            noeudSelected.setNoeudX(newX);
            noeudSelected.setNoeudY(newY);

            // On modifie la table en direct
            tableModelModSommet.setValueAt(newType, noeudJTable.getSelectedRow(), 1);
            tableModelModSommet.setValueAt(newX, noeudJTable.getSelectedRow(), 2);
            tableModelModSommet.setValueAt(newY, noeudJTable.getSelectedRow(), 3);
            
            logiciel.getL().repaint();
            }
            revalidate();
            repaint();
        }
    }
    /**
     * Listener pour supprimer un element
     */
    public class delListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // On supprime l'objet en verifiant si l'utilsateur est bien sur
            int check = JOptionPane.showConfirmDialog(rootPane, "Vous etes sur ?");
            if (check == JOptionPane.OK_OPTION){
                if ( noeudSelected != null){
                logiciel.supprimerNoeud(noeudSelected);
                tableModelModSommet.delNoeud(noeudSelected);
                repaint();
                revalidate();
                }
            }
            
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        noeudJTable = new javax.swing.JTable();
        rightPanel = new javax.swing.JPanel();
        attributsPanel = new javax.swing.JPanel();
        TypeLabel = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        X = new javax.swing.JLabel();
        xSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        ySpinner = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        delButton = new javax.swing.JButton();
        modButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        noeudJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(noeudJTable);

        rightPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Proprietes"));
        rightPanel.setToolTipText("");

        TypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TypeLabel.setText("Type :");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "O", "N" }));

        X.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        X.setText("X:");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Y:");

        javax.swing.GroupLayout attributsPanelLayout = new javax.swing.GroupLayout(attributsPanel);
        attributsPanel.setLayout(attributsPanelLayout);
        attributsPanelLayout.setHorizontalGroup(
            attributsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attributsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(attributsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(X, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(attributsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ySpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(typeComboBox, 0, 95, Short.MAX_VALUE)
                    .addComponent(xSpinner))
                .addContainerGap())
        );
        attributsPanelLayout.setVerticalGroup(
            attributsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attributsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(attributsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(attributsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(X)
                    .addComponent(xSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(attributsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        delButton.setText("Supprimer");

        modButton.setText("Modifier");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(delButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(modButton))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delButton)
                    .addComponent(modButton))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(attributsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addComponent(attributsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
                dispose();
        }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TypeLabel;
    private javax.swing.JLabel X;
    private javax.swing.JPanel attributsPanel;
    private javax.swing.JButton delButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton modButton;
    private javax.swing.JTable noeudJTable;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JSpinner xSpinner;
    private javax.swing.JSpinner ySpinner;
    // End of variables declaration//GEN-END:variables
}
