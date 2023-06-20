/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sae.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sae.Logiciel;
import sae.Model.Graphe.Arete;
import sae.Model.Noeud;
import sae.Model.TableModelModArete;

/**
 * Fenetre pour lister et modifier une ou plusieurs aretes
 * @author gheri
 */
public class ModArete extends javax.swing.JFrame {

    //<editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    
    /**
     * Attribut correspondant au logiciel avec lequel on communique
     */
    private Logiciel logiciel;
    
    /**
     * Attribut correspondant au model de Table d'aretes
     */
    private TableModelModArete tableModelModArete;
    
    /**
     * Attributs correspondants aux listes nécessaire à l'affichage de la JTable
     */
    private Map<Integer,Noeud> noeudsList1;
    private Map<Integer,Noeud> noeudsList2;
    private Map<Integer,Arete> aretes;
    
    /**
     * Attributs correspondant aux noeuds et donc arete selectionnée(s)
     */
    private Arete areteSelec;
    private Noeud noeud1Selec;
    private Noeud noeud2Selec;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="CONSTRUCTEUR">
    
    /**
     * Constructeur pour afficher la Fenetre
     * @param logiciel
     * @param tableModelModArete
     * @param title 
     */
    public ModArete(Logiciel logiciel,TableModelModArete tableModelModArete,String title) {
        this.logiciel   = logiciel;
        this.tableModelModArete = tableModelModArete;
        
        this.noeudsList1 = tableModelModArete.getMap1Noeud();
        this.noeudsList2 = tableModelModArete.getMap2Noeud();
        this.aretes      = tableModelModArete.getMapArete();
               
        
        initComponents();
        
        // Initialisation du modèle de la JTable
        initializeModel();
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        
        // On met les listeners
        areteJTable.addMouseListener(new tableListener());
        modButton.addActionListener(new modListener());
        delButton.addActionListener(new delListener());
    }
    // </editor-fold>
    
    /**
     * Integre le modele qu'on recoit
     */
    private void initializeModel(){
        this.areteJTable.setModel(tableModelModArete);  
    }
    
    @Override
    public void dispose(){
        logiciel.getL().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.dispose();
    }
    
    //<editor-fold defaultstate="collapsed" desc="LISTENERS">
    
    /**
     * Listener pour selectionner une ligne et donc 2 noeuds et 1 arete
     */
    public class tableListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {}

        /* Fonction re-écrite pour selectionner un noeud*/ 
        @Override
        public void mousePressed(MouseEvent e) {
            // On recuperere l'arete à modifier et on change les valeurs des champs
            areteSelec  = aretes.get(areteJTable.getSelectedRow());
            noeud1Selec = noeudsList1.get(areteJTable.getSelectedRow());
            noeud2Selec = noeudsList2.get(areteJTable.getSelectedRow());
                    
            distSpinner.setValue((int)areteSelec.getDist());
            durSpinner.setValue((int)areteSelec.getDur());
            fiabSpinner.setValue((int)areteSelec.getFiab());
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
   
    
    /**
     * Listener pour modifier une arete
     */
    public class modListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // Attributs de la modification
            int newDist    = (int) distSpinner.getValue();
            int newDur     = (int) durSpinner.getValue();
            int newFiab    = (int) fiabSpinner.getValue();
            
            if (noeud1Selec != null){
            // On modifie l'arete en fonction
            logiciel.modifierArete(noeud1Selec, noeud2Selec, newDist, newFiab, newDur);

            
            // On modifie la table en direct
            int index = 0;
            for (Integer i : aretes.keySet()){
                if (aretes.get(i) == areteSelec){
                    index = i;
                }
            }
            tableModelModArete.setValueAt(newDist, index, 3);
            tableModelModArete.setValueAt(newDist, index, 4);
            tableModelModArete.setValueAt(newFiab, index, 5);
            
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
                if (noeud1Selec != null){
                tableModelModArete.delArete(areteJTable.getSelectedRow());
                logiciel.supprimerArete(noeud1Selec,noeud2Selec);
                }
            }
                repaint();
                revalidate();
            
            }
        }
    
    // </editor-fold>
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areteJTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        distSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        durSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        fiabSpinner = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        modButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        areteJTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(areteJTable);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Proprietes"));

        jLabel1.setText("Distance");

        jLabel2.setText("Durée");

        jLabel3.setText("Fiabilité");

        modButton.setText("Modifier");
        modButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modButtonActionPerformed(evt);
            }
        });

        delButton.setText("Supprimer");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(delButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delButton)
                    .addComponent(modButton))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(durSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(distSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fiabSpinner)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(distSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(durSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fiabSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void modButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable areteJTable;
    private javax.swing.JButton delButton;
    private javax.swing.JSpinner distSpinner;
    private javax.swing.JSpinner durSpinner;
    private javax.swing.JSpinner fiabSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton modButton;
    // End of variables declaration//GEN-END:variables
}
