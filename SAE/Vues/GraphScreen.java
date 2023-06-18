/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sae.Vue;

import java.awt.BasicStroke;
import sae.Logiciel;
import sae.Model.Noeud;
import java.awt.Color;          
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import sae.Model.Graphe;
import sae.Model.Graphe.Arete;
import sae.Model.Graphe.Sommet;

/**
 * Panel affichant le graphe 
 * @author gheri
 */
public class GraphScreen extends javax.swing.JPanel{
    
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    /**
     * Attribut correspondant à l'identifiant du composant qui a appelé l'ecran ou une methode de l'ecran
     */
    private static int sourceID = 1;
    
    /**
     * Attribut correspondant au logicel avec qui la fenetre communique
     */
    private Logiciel logi;
    
    /**
     * Attributs correspondants au propriétés visuelles du noeuds
     */
    int noeudWidth        = 25;
    int noeudHeight       = 25;
    
    /**
     * Attribut correspondant à la liste de noeuds que le Panel doit afficher
     */
    List<Noeud> noeuds;
    
    /**
     * Attributs correspondant aux proprietés pour connaitre l'etat des noeuds ( selectionnés ou non)
     */
    int noeudSelect       = 0;
    boolean noeudSelected = false;
    Noeud selecDeplace    = null;
    Sommet S;
    
    /**
     * Attribut correspondant à une liste d'aretes pour eviter des erreurs d'affichage lors de l'affichage d'un chemin
     */
    List<Arete> aretestonotPaint = new ArrayList<>();
    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTEURS">
    
    /**
     * Constructeur par défault pour afficher un graphe complet
     * @param l
     * @param width
     * @param height 
     */
    public GraphScreen(Logiciel l,int width, int height) {
        initComponents();
        this.logi  = l;
        noeuds = logi.generationNoeuds();
        
        this.setVisible(true);
        this.setSize(new Dimension(width,height));
        this.setBackground(Color.white);
        
        this.addMouseMotionListener(new GraphScreenMotionListener());
        this.addMouseListener(new GraphScreenMouseListener());
    }
    
    /**
     * Constructeur pour afficher les voisins directs d'un sommet
     * @param l
     * @param width
     * @param height
     * @param S 
     */
    public GraphScreen(Logiciel l,int width, int height,Sommet S) {
        initComponents();
        this.S = S;
        this.logi = l;
        noeuds = logi.generationNoeudsDistance1(S);
        
        this.setBackground(Color.white);
        this.setSize(new Dimension(width,height));
        this.setVisible(true);
        
        this.addMouseMotionListener(new GraphScreenMotionListener());
        this.addMouseListener(new GraphScreenMouseListener());
        
    } 
    
    /**
     * Constructeur pour afficher les voisins à 2-distance d'un sommet
     * @param l
     * @param width
     * @param height
     * @param S
     * @param mode 
     */
    public GraphScreen(Logiciel l,int width, int height,Sommet S,boolean mode) {
        initComponents();
        this.S = S;
        this.logi = l;
        noeuds = logi.generationNoeudsDistance2(S);
        
        this.setBackground(Color.white);
        this.setSize(new Dimension(width,height));
        this.setVisible(true);
        
        this.addMouseMotionListener(new GraphScreenMotionListener());
        this.addMouseListener(new GraphScreenMouseListener());
        
    } 
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="METHODE PAINTCOMPONENT">
    
    // On dessine l'ecran en fonction du graphe rentré en parametre
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        List<Noeud> noeudsTraites = new LinkedList<>();
        aretestonotPaint.clear();
        // Attributs
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(Color.black);
        // On trace les arretes en premier
        for (Noeud tmp : noeuds){
            
            // On recupere les arretes du noeud
            Arete tmpA = tmp.getS().getArete();
            while( tmpA != null){
                Sommet dest = logi.getSommet(tmpA.getDest());
                
                // On trouve le noeud qui correspond à la dest
                for (Noeud tmp2 : noeuds){
                    
                    if (tmp2.getS().equals(dest)){
                        
                        // On verifie qu'une arrete n'a pas deja ete tracée et si les 2 sommmets sont selectionnes et liés
                        if (!noeudsTraites.contains(tmp2)){
                            if(this.checkLies(tmp, tmp2) && !aretestonotPaint.contains(tmpA)){
                                g2.setStroke(new BasicStroke((float)4));
                                g.setColor(Color.BLUE);
                            }
                            else{
                                g2.setStroke(new BasicStroke((float)1));
                                g.setColor(Color.BLACK);
                            }
                        g.drawLine(tmp.getNoeudX() + noeudWidth/2, tmp.getNoeudY() +noeudHeight/2, tmp2.getNoeudX()+noeudWidth/2, tmp2.getNoeudY()+noeudHeight/2); 
                        }
                    }
                }
                tmpA =tmpA.getSuivant();
            }
            noeudsTraites.add(tmp);
        }
        // On s'occupe ensuite de dessiner les noeuds avec des ronds
        for (Noeud tmp : noeuds){
            g.setColor(tmp.getColor());
            g.fillOval(tmp.getNoeudX(), tmp.getNoeudY(), noeudWidth, noeudHeight);
            
            g.setColor(Color.black);
            g2.setFont(new Font(Font.SERIF,Font.BOLD,12));
            
            // On place le texte en fonction de sa longeur
            if (tmp.getS().getNom().length() == 2){
                g.drawString(tmp.getS().getNom(), tmp.getNoeudX() + noeudWidth/4 -1,tmp.getNoeudY() + noeudHeight/2 + 5);
            }
            else{
                g.drawString(tmp.getS().getNom(), tmp.getNoeudX() + noeudWidth/4 -3,tmp.getNoeudY() + noeudHeight/2 + 5);
            }
        }
        
        
       
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="LISTENERS">
    public class GraphScreenMotionListener implements MouseMotionListener {

         // On ajoute l'evenement très important de modifier avec la souris la position des noeuds;
        @Override
        public void mouseDragged(MouseEvent e) {
            Noeud   selectmp = null;
            boolean selected = false;
            // On cherche le noeud selectionné
            for (Noeud tmp : noeuds){
                if ((e.getX() >= (tmp.getNoeudX() + (noeudWidth/2))- 20 &&  e.getX() <= (tmp.getNoeudX() + (noeudWidth/2)) + 20) && (e.getY() >= (tmp.getNoeudY() + (noeudHeight/2)- 20) && e.getY() <= (tmp.getNoeudY() +( noeudHeight /2 )) + 20 ))
                {
                    // Systeme de comparaison du noeud selectionne temporairement avec celui definitif pour eviter de selectionner un autre noeud en passant par dessus
                    if (tmp == selecDeplace){
                        selected = true;
                    }
                    else{
                        selectmp = tmp;
                    }
                }
            }
            if (!selected){
                selecDeplace = selectmp;
            }
            if ( selecDeplace != null){
            selecDeplace.setNoeudX(e.getX());
            selecDeplace.setNoeudY(e.getY());
            repaint(); 
            }
        }
            

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
    
}
    // Listener pour un event Mouse
    public class GraphScreenMouseListener implements MouseListener{

        // On ajoute l'evenement où on peut selectionner un noeud avec notre souris
        @Override
        public void mouseClicked(MouseEvent e) {
             
            // On verifie pour chaque noeud si il est cliqué
            for (Noeud tmp : noeuds){
                if ((e.getX() >= (tmp.getNoeudX() + (noeudWidth/2))- 15 &&  e.getX() <= (tmp.getNoeudX() + (noeudWidth/2)) + 15) && (e.getY() >= (tmp.getNoeudY() + (noeudHeight/2)- 15) && e.getY() <= (tmp.getNoeudY() +( noeudHeight /2 )) + 15 ))
                    {
                    // On verifie et actualise le nombre de noeuds cliqués
                    noeudSelect = 0;
                    for (Noeud tmp2 : noeuds){
                        if (tmp2.getColor() == Color.yellow ||tmp2.getColor() == Color.green){
                            noeudSelect ++;
                        }
                    }
                    // Pemet de cliquer 2 points max sur le graphe
                    if (noeudSelect >= 2){
                    for (Noeud tmp2 : noeuds){
                        tmp2.setColor(Color.red);
                    }
                    noeudSelect = 0;
                    }
                    
                    tmp.setColor(Color.yellow);
                    noeudSelect++;
                    noeudSelected = true;
                    
                    // Petit if qui permet d'envoyer directement au logiciel la notification d'un noeud selectionné si il est seul
                    if (noeudSelect == 1){
                        logi.noeudClique(tmp.getS(),sourceID);
                    }
                }
            }
            if (noeudSelected){
                noeudSelected = false;
            }
            else{
                for (Noeud tmp2 : noeuds){
                tmp2.setColor(Color.red);
                }
                noeudSelect = 0;
                logi.noeudClique(null,sourceID);
            }
            repaint();
            
            // Petit if pour envoyer les 2 noeuds au logiciel
            if (noeudSelect == 2){
                Noeud n1 = null;
                Noeud n2 = null;
                boolean first = true;
                for ( Noeud tmp : noeuds){
                    if(tmp.getColor() == Color.yellow){
                        if (first){
                            n1 = tmp;
                            first = false;
                        }
                        else n2 = tmp;
                    }
                }
                if (n1 != null && n2 != null){
                logi.noeudsCliques(n1.getS(), n2.getS(),sourceID);
                }
            }
            
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PUBLIC METHODS">
    
    /**
     * Methode pour colorier un ou 2 noeuds sur le graphe et ainsi les selectionner
     * @param S1
     * @param S2 
     */
    public void colorierNoeud(Sommet S1,Sommet S2){
        for (Noeud tmp : noeuds){
            if(tmp.getS() == S1 || tmp.getS() == S2){
                tmp.setColor(Color.yellow);
            }
            else{
                tmp.setColor(Color.red);
            }
        }
        repaint();
    }
    
    /**
     * Methode pour decolorier un noeud et ainsi le deselectionner
     * @param s 
     */
    public void decolorierNoeud(Sommet s){
        for (Noeud tmp : noeuds){
            if (tmp.getS() == s){
                tmp.setColor(Color.red);
            }
        }
        repaint();
    }
    
    /**
     * Methode pour afficher visuellement un chemin
     * @param chemin
     * @param aretes 
     */
    public void Afficher_Chemin(List<Sommet> chemin,List<Arete> aretes){
        aretestonotPaint = aretes;
        // Pour chaque noeud on verrifie si il est dans le chemin
        for (Noeud tmp : noeuds){
            if (chemin.contains(tmp.getS())){
                tmp.setColor(Color.yellow);
            }
        }
    }
    
    /**
     * Methode pour acceder à la liste du Panel
     * @return 
     */
    public List<Noeud> getNoeuds() {
        return noeuds;
    }
    
    /**
     * Methode pour supprimer un noeud de l'affichage
     * @param N 
     */
    public void suppNoeud(Noeud N){
        noeuds.remove(N); 
    }
     
    /**
     * Methode pour verifier si 2 noeuds sont selectionnes et lies
     * @param N1
     * @param N2
     * @return 
     */
    private boolean checkLies(Noeud N1, Noeud N2){
        boolean result = false;
        if (N1.getColor() == Color.YELLOW && N2.getColor() == Color.YELLOW){
            
            Arete tmpA = N1.getS().getArete();
            while (tmpA != null){
                
                if (N2.getS().getNom().equals(tmpA.getDest())){
                    result = true;
                }
                tmpA = tmpA.getSuivant();
            }
        }
        return result;
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
