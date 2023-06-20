/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import sae.Model.FoncEnum;
import static sae.Model.FoncEnum.CHECK2DISTANCE;
import static sae.Model.FoncEnum.COMPARAISON2NOEUDS;
import static sae.Model.FoncEnum.COMPLEXITEALGO;
import static sae.Model.FoncEnum.LISTERARRETES;
import static sae.Model.FoncEnum.LISTERPARCATEGORIE;
import static sae.Model.FoncEnum.LISTERSOMMETS;
import static sae.Model.FoncEnum.LISTERVOSINSTYPE;
import static sae.Model.FoncEnum.MODIFIERARRETE;
import static sae.Model.FoncEnum.MODIFIERSOMMET;
import static sae.Model.FoncEnum.PLUSCOURT;
import static sae.Model.FoncEnum.PLUSFIABLE;
import static sae.Model.FoncEnum.VOISINS1DISTANCE;
import static sae.Model.FoncEnum.VOISINS2DISTANCE;
import sae.Model.Graphe;
import sae.Logiciel;
import sae.Model.Graphe.Arete;
import sae.Model.Graphe.Sommet;
import sae.Model.Noeud;
import sae.Model.TableModelModArete;
import sae.Model.TableModelModSommet;
import sae.Vue.ChooseFoncDialog;
import sae.Vue.GraphScreen;
import sae.Vue.LogicielDecision;
import sae.Vue.ModArete;
import sae.Vue.ModSommet;

/**
 * Classe contenant les méthodes pour controller les Vues et faire appel au fonctions du graphe
 * @author gheri
 */

public class LogicielController implements Logiciel{
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    
    /**
     * Modele du graphe
     */
    Graphe g;
    
    /**
     * Vue Principale de l'Ecran Principal du logiciel
     */
    LogicielDecision l;
    
    /**
     * Classe contenant les fonctions pour manipuler le graphe
     */
    GrapheFonc gf;
    /**
     * Booleen pour s'assurer de generer un graphe valide
     */
    boolean generate = true;
    // </editor-fold>
    
    // Constructeur tres basique
    LogicielController(){
    }

    // <editor-fold defaultstate="collapsed" desc="GETTERS">
    public Graphe getG() {
        return g;
    }

    @Override
    public LogicielDecision getL() {
        return l;
    }

    public void setG(Graphe g) {
        this.g = g;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="SETTERS">
    public void setL(LogicielDecision l) {
        this.l = l;
    }

    public void setGf(GrapheFonc gf) {
        this.gf = gf;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PUBLIC METHODES LOGICICIEL">
    
    /**
     * Methode pour generer des noeuds aux posistion aléatoires
     * @return Liste de Noeuds;
     */
    @Override
    public List<Noeud> generationNoeuds() {
        
        if ( g == null ) { return null;}
        
        // Si g n'est pas null
        List<Noeud> results = new ArrayList<>();
        Sommet tmpS = g.getPremier();
        Random dice = new Random();
        
        
        // On crée un booléen pour ne pas avoir 2 noeuds au meme endroit
        boolean samePlace = false;
        
        // Boucle pour créer les noeuds
        while ( tmpS != null){
            
            // On genere 2 points sur notre panel d'affichage de maniére aléatoire
            int X = dice.nextInt(l.getAffichagePanel().getWidth()- 25);
            int Y = dice.nextInt(l.getAffichagePanel().getHeight()- 25);
            
            // On verifie que ces 2 coordonnées ne sont pas déjà prises ou tres proches
            
            for ( Noeud tmp : results){
                if( X >= tmp.getNoeudX()-45 && X<= tmp.getNoeudX() + 45 && Y >= tmp.getNoeudY() -45 && Y<=tmp.getNoeudY() + 45 ){
                    samePlace = true;
                }
            }
            
            // Si tout est bon on crée et ajoute un noeud dans la liste sinon on refait
            if (samePlace){
                samePlace = false;
            }
            else{
                // On crée le noeud 
                Noeud tmp = new Noeud(tmpS,X,Y);
                
                
                // On ajoute à la liste et on passe au noeud suivant
                results.add(tmp);
                tmpS = tmpS.getSuivant();
            }
           
        }
        
        return results;
    }  
    
    /**
     *  Methode qui permet de generer une liste de noeuds avec le noeud sélectionné en paramètre (s) au millieu du Panel d'affichage, et ses vosins direct avec des positions aléatoires
     * @param s Sommet
     * @return  Liste de Noeuds
     */
    @Override
    public List<Noeud> generationNoeudsDistance1(Sommet s) {
        
        // Liste pour stocker les résultats
        List<Noeud> results = new ArrayList<>();
        ArrayList<Sommet> sommets = new ArrayList<>();
        
        // Création d'un générateur de nombres aléatoires
        Random dice = new Random();
        
        // Variable temporaire pour les arêtes et booleen pour espacer les noeuds
        Arete tmpA1;
        boolean samePlace = false;

        // On génère 2 points sur notre panel d'affichage au milieu
        int X = l.getAffichagePanel().getWidth()/2 + 1;
        int Y = l.getAffichagePanel().getHeight()/2 + 1;

        // Création du premier nœud avec le sommet initial
        Noeud tmp = new Noeud(s, X, Y);

        // Ajout du premier nœud à la liste de résultats
        results.add(tmp);

        // Récupération des successeurs directs et génération de leurs arêtes
        tmpA1 = s.getArete();
        while (tmpA1 != null) {
            Sommet tmpS1= g.getSommet(tmpA1.getDest());
            X = dice.nextInt(l.getAffichagePanel().getWidth()-15);
            Y = dice.nextInt(l.getAffichagePanel().getHeight()-15);
            
            tmp = new Noeud(tmpS1, X, Y);
            sommets.add(s);

            
            results.add(tmp);
            tmpA1 = tmpA1.getSuivant();
        }
        return results;
    }

    /**
     * Methode qui permet de generer une liste de noeuds avec le noeud sélectionné en paramètre (s) au millieu du Panel d'affichage, et ses vosins à 2-distance avec des positions aléatoires
     * @param s Sommet
     * @return Liste de Noeuds
     */
    @Override
    public List<Noeud> generationNoeudsDistance2(Sommet s) {
        
        // Listes pour stocker les résultats
        List<Noeud>  results          = new ArrayList<>();
        List<Sommet> suivantsSommet   = new ArrayList<>();
        List<Sommet> suivantsSuivants = new ArrayList<>();
        
        boolean samePlace  = false;
        boolean samePlace2 = false;
        
        // Création d'un générateur de nombres aléatoires
        Random dice = new Random();
        
        // On génère 2 points sur notre panel d'affichage au milieu
        int X = l.getAffichagePanel().getWidth()/2 + 1;
        int Y = l.getAffichagePanel().getHeight()/2 + 1;
        Noeud sN = new Noeud(s,X,Y);
        results.add(sN);
        // On recuperere les voisins de notre Sommet
        Arete tmpA = s.getArete();
        while( tmpA != null){
            
            // On crée un noeud de maniere aléatoire 
            X = dice.nextInt(l.getAffichagePanel().getWidth()-20);
            Y = dice.nextInt(l.getAffichagePanel().getHeight()-20);
            
            // On verifie que ces 2 coordonnées ne sont pas déjà prises ou tres proches
            for ( Noeud tmpN : results){
                if( X >= tmpN.getNoeudX()-30 && X<= tmpN.getNoeudX() +30 && Y >= tmpN.getNoeudY() -30 && Y<=tmpN.getNoeudY() +30){
                    samePlace = true;
                }
            }
            if (samePlace){
                samePlace = false;
            }
            else{
            Sommet tmpS = g.getSommet(tmpA.getDest());
            Noeud tmpN = new Noeud(tmpS,X,Y);
            
            // On remplit les listes
            suivantsSommet.add(tmpS);
            results.add(tmpN);
            
            tmpA = tmpA.getSuivant();
            }
        }
        
        // On recupere les suivants de chaque suivant du sommet
        for (Sommet tmpS : suivantsSommet){
            
            tmpA = tmpS.getArete();
            while (tmpA != null){
                Sommet tmpS2 = g.getSommet(tmpA.getDest());
                
                // On verifie que le sommet n'appartient pas au suivants de notre sommet 
                if (!suivantsSommet.contains(tmpS2) && tmpS2 != s && !suivantsSuivants.contains(tmpS2)){
                    X = dice.nextInt(l.getAffichagePanel().getWidth()-20);
                    Y = dice.nextInt(l.getAffichagePanel().getHeight()-20);
                    
                    for ( Noeud tmpN : results){
                        if( X >= tmpN.getNoeudX()-30 && X<= tmpN.getNoeudX() +30 && Y >= tmpN.getNoeudY() -30 && Y<=tmpN.getNoeudY() +30){
                    samePlace = true;
                        }
                    }
                    if (samePlace){
                    samePlace = false;
                    samePlace2 = true;
                    }
                    else{
                    Noeud tmpN = new Noeud(tmpS2,X,Y);
                    results.add(tmpN);
                    suivantsSuivants.add(tmpS2);
                    }
                }
                if (!samePlace2){
                tmpA = tmpA.getSuivant();
                }
                else{
                samePlace2 = false;
                }
            }
            
        }
        
        return results;
    }

    
    /**
     * Methode pour afficher des infos sur le sommet selectionné dans la console du logiciel
     * @param S Sommet
     * @param sourceID Indique de quel composant le sommet est selectionné
     */
    @Override
    public void noeudClique(Sommet S,int sourceID) {
        l.getConsole().setText("");
        
        // On verifie que S est null ou pas, permet de vider l'ouput en envoyant null à cette methode
        if ( S!= null){
        String message = S.getNom() + " : Suivants : " ;
        Arete tmp = S.getArete();
        while (tmp!= null){
            message+= tmp.getDest() + " : Distance : " + tmp.getDist() + " ; ";
            tmp = tmp.getSuivant();
        }
        l.getConsole().setText(message);
        }
        // On communique au menu ou à l'ecran le changement 
        // ID GraphScreen = 1 / ID MenuForm == 2
        if (sourceID == 1){
            
            // Comme s'est censé etre la methode appelée pour choisir un premier sommet, on reset les sommets choisis à chaque fois
            l.getMenu().newSelectionne(null);
            l.getMenu().newSelectionne(S);
        }
        if (sourceID == 2){
            l.getGraphScreen().colorierNoeud(S,S);
        }
        
    }

    /**
     * Methode pour afficher des infos sur l'arete selectionnée dans la console du logiciel
     * @param S1 Sommet 1 de l'arete selectionnée
     * @param S2 Sommet 2 de l'arete selectionnée
     * @param sourceID Indique de quel composant l'arete est selectionnée
     */
    @Override
    public void noeudsCliques(Sommet S1, Sommet S2,int sourceID) {
        l.getConsole().setText("");
        //String message
        if( S1 != null && S2 != null){
            String message;
            String s1Name = S1.getNom();
            String s2Name = S2.getNom();
            
            // On verifie si ils sont liés ou pas
            if (g.checkArrete(s1Name, s2Name) ){
                message =  s1Name + " et " + s2Name + " sont liés : " ;
                
                // On recupere l'arrete 
                Arete tmpA = S1.getArete();
                while (tmpA != null && ! tmpA.getDest().equals(s2Name)){
                        tmpA = tmpA.getSuivant();
                }
                message += "Proprietés de l'arretes : (Distance : " + tmpA.getDist()+ " ; Durée : " + tmpA.getDur() + "; Fiabilité : " + tmpA.getFiab()+ " ).";
            }
            else{
                message = "Les 2 sommets ne sont pas liés";
            }
            l.getConsole().setText(message);
        // On communique au menu ou à l'ecran le changement 
        // ID GraphScreen = 1 / ID MenuForm == 2 
        if (sourceID == 1){
            
            // Comme il s'agit d'un methode appelée uniquement pour un deuxieme sommet appelé, on sait que ca ira direct au s2
            // On ne sait pas dans quel ordre seront envoyés les sommets, pour eviter toute confusion on verifie quel sommet est le s1
            if (l.getMenu().checkS1(S1)){
            l.getMenu().newSelectionne(S2);
            }
            else{ l.getMenu().newSelectionne(S1);}
        }
        if (sourceID == 2){
            l.getGraphScreen().colorierNoeud(S1, S2);
        }
        }
       
    }
    
    /**
     * Methode pour deselectionner un noeud ou une arete
     * @param s1 Sommet 1
     * @param s2 Sommet 2 éventuel
     */
    @Override
    public void deselectionnerNoeud(Sommet s1,Sommet s2) {
        if (s1 != null){
            l.getGraphScreen().decolorierNoeud(s1);
        }
        if (s2!= null){
            this.noeudClique(s2, 2);
        }
        else{
            l.getConsole().setText("");
        }
    }
   
    /**
     * Methodes pour l'implementation des fonctions du graphe (Menu)
     * @param fonc FoncEnum
     */
    @Override
    public void DeclencherFonction(FoncEnum fonc) {
        if (fonc != null){
            
            // On cherche quelle fonction est declanchée et on l'appele
            switch(fonc){
                
                case COMPLEXITEALGO     : this.afficherComplexiteAlgo();
                                          break;
                case LISTERSOMMETS      : this.listerSommets();
                                          break;
                case LISTERARRETES      : this.listerArretes();
                                          break;
                case MODIFIERSOMMET     : this.fonctionModifierSommet(l.getMenu().getSelectionne());
                                          break;
                case VOISINS1DISTANCE   : this.afficherVoisins1Distance(l.getMenu().getSelectionne());
                                          break;
                case VOISINS2DISTANCE   : this.afficherVoisins2Distance(l.getMenu().getSelectionne());
                                          break;
                case PLUSCOURT          : this.afficherplusCourtChemin(l.getMenu().getSelectionnes()[0],l.getMenu().getSelectionnes()[1]);
                                          break;
                case PLUSFIABLE         : this.afficherplusFiableChemin(l.getMenu().getSelectionnes()[0],l.getMenu().getSelectionnes()[1]);
                                          break;
                case MODIFIERARRETE     : this.fonctionModifierArrete(l.getMenu().getSelectionnes()[0],l.getMenu().getSelectionnes()[1]);
                                          break;
                case COMPARAISON2NOEUDS : this.Comparaison2noeuds(l.getMenu().getSelectionnes()[0],l.getMenu().getSelectionnes()[1]);
                                          break;
                case LISTERPARCATEGORIE : this.listerParCategorie();
                                          break;
                case LISTERVOSINSTYPE   : this.listerVoisinsType(l.getMenu().getSelectionnes()[0],l.getMenu().getSelectionnes()[1]);
                                          break;
                case CHECK2DISTANCE     : this.check2Noeuds2Distance(l.getMenu().getSelectionnes()[0],l.getMenu().getSelectionnes()[1]);
                                          break;
                default                 : JOptionPane.showMessageDialog(null,"Veuillez choisir une fonctionnalité","Selection Incorrecte",JOptionPane.ERROR_MESSAGE);
                                          break;
        }
    }
    }

    /**
     * Methode pour afficher dans la console la complexité de l'algorithme
     */
    @Override
    public void afficherComplexiteAlgo() {
        System.out.println("Complexite Algo");
    }

    /**
     * Methode permettant d'afficher une fenetre listant tous les sommets du graphe
     */
    @Override
    public void listerSommets() {
        // On crée un Model de JTable avant d'ouvrir une fenetre qui l'utilise
        List<Noeud> noeuds = l.getGraphScreen().getNoeuds();
        TableModelModSommet noeudModelTable = new TableModelModSommet();
        
        for (Noeud tmp : noeuds){
            noeudModelTable.addNoeud(tmp);
        }
        
        ModSommet modSommet = new ModSommet(this,noeudModelTable,"Liste Sommets");
        modSommet.setLocation(l.getX(),l.getY());
        modSommet.setVisible(true);
    }
    
    /**
     * Methode permettant d'afficher une fenetre listant tous les sommets du graphe d'une certaine categorie
     */
    @Override
    public void listerParCategorie() {
        // on invoque une JOptionPanel pour chosir le type
        String type = null;
        while (type == null){
            String response = JOptionPane.showInputDialog(l,"Saisissez le type désiré");
            if (response.equals("M") || response.equals("O") || response.equals("N")){
                type = response;
            }
            else {
                JOptionPane.showMessageDialog(l,"Veuillez saisir une de ces 3 options : M || N ||O","Erreur de saisie ",JOptionPane.ERROR_MESSAGE);
            }
        }
        // On fait une liste à partir du type recuperé
        List<Noeud> noeuds = l.getGraphScreen().getNoeuds();
        TableModelModSommet noeudModelTable = new TableModelModSommet();
        
        for (Noeud tmp : noeuds){
            if (tmp.getS().getType().equals(type)){
            noeudModelTable.addNoeud(tmp);
            }
        }
        // On appelle le meme dialog pour les sommets
        ModSommet modSommet = new ModSommet(this,noeudModelTable,"Liste Sommets Par catégorie");
        modSommet.setLocation(l.getX(),l.getY());
        modSommet.setVisible(true);
    }
    
    /**
     * Methode permettant d'afficher une fenetre listant tous les voisins de 2 sommets d'une certaine categorie
     * @param S1
     * @param S2 
     */
    @Override
    public void listerVoisinsType(Sommet S1, Sommet S2) {
        // on invoque une JOptionPanel pour chosir le type
        String type = null;
        while (type == null){
            String response = JOptionPane.showInputDialog(l,"Saisissez le type désiré");
            if (response.equals("M") || response.equals("O") || response.equals("N")){
                type = response;
            }
            else {
                JOptionPane.showMessageDialog(l,"Veuillez saisir une de ces 3 options : M || N ||O","Erreur de saisie ",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // On fait une liste à partir du type recuperé
        List<Noeud> noeuds = l.getGraphScreen().getNoeuds();
        TableModelModSommet noeudModelTable = new TableModelModSommet();
        
        List<Sommet> voisinsS1 = gf.lister_voisins(S1);
        List<Sommet> voisinsS2 = gf.lister_voisins(S2);
        List<Sommet> voisinsCommuns = new ArrayList<>();
        
        // On crée la liste de voisins communs 
        for (Sommet tmp : voisinsS1){
            if(voisinsS2.contains(tmp) && tmp.getType().equals(type)){
                voisinsCommuns.add(tmp);
            }
        }
        
        // On crée la liste de noeuds
        for (Noeud tmp : noeuds){
            if (voisinsCommuns.contains(tmp.getS())){
                noeudModelTable.addNoeud(tmp);
            }
        }
        
        // On appelle le meme dialog pour les sommets
        ModSommet modSommet = new ModSommet(this,noeudModelTable,"Liste des Voisins Par Type");
        modSommet.setLocation(l.getX(),l.getY());
        modSommet.setVisible(true);
        modSommet.checkList();  
    }
    
    /**
     * Methode permettant de liste tous les aretes du graphe
     */
    @Override
    public void listerArretes() {
        // On crée un Model de JTable avant d'ouvrir une fenetre qui l'utilise
        List<Noeud> noeuds = l.getGraphScreen().getNoeuds();
        TableModelModArete tableModelModArete = new TableModelModArete();
        List<Noeud> noeudsTraites = new LinkedList<>();
                
        for (Noeud tmp : noeuds ){
            Arete tmpA = tmp.getS().getArete();
            while (tmpA != null){
                
                // On trouve le noeud associé à la destination
                Noeud noeudDest = null;
                for (Noeud tmp2 : noeuds){
                    if (tmp2.getS().getNom().equals(tmpA.getDest())){
                        noeudDest = tmp2;
                    }
                } 
                if (noeudDest != null) {
                // On verifie que l'arrete n'a pas deja eté traitée
                if ( !noeudsTraites.contains(noeudDest)){
                    tableModelModArete.addArete(tmp, noeudDest,tmpA);
                }
                }
                tmpA = tmpA.getSuivant();
                
            }
            noeudsTraites.add(tmp);
                
        }
        
        // On crée notre fenetre 
        ModArete modArete = new ModArete(this,tableModelModArete,"Liste Aretes");
        modArete.setLocation(l.getX(),l.getY());
        modArete.setVisible(true);
    }

    /**
     * Methode permettant d'afficher un nouvel ecran qui contient les voisins directs du sommet en paramètre
     * @param S Sommet
     */
    @Override
    public void afficherVoisins1Distance(Sommet S) {
        GraphScreen tmp = new GraphScreen(this,l.getAffichagePanel().getWidth(),l.getAffichagePanel().getHeight(),S);
        l.getAffichagePanel().remove(l.getGraphScreen());
        l.setGraphScreen(tmp);
        l.getAffichagePanel().add(l.getGraphScreen());
        l.getGraphScreen().colorierNoeud(S, S);
        l.revalidate();
        l.repaint();
    }

    /**
     * Methode permettant d'afficher un nouvel ecran qui contient les voisins à 2-distance du sommet en paramètre
     * @param S 
     */
    @Override
    public void afficherVoisins2Distance(Sommet S) {
        GraphScreen tmp = new GraphScreen(this,l.getAffichagePanel().getWidth(),l.getAffichagePanel().getHeight(),S,true);
        l.getAffichagePanel().remove(l.getGraphScreen());
        l.setGraphScreen(tmp);
        l.getAffichagePanel().add(l.getGraphScreen());
        l.getGraphScreen().colorierNoeud(S, S);
        l.revalidate();
        l.repaint();
    }

    /**
     * Methode permettant d'afficher le chemin le plus court entre 2 sommets
     * @param S1 Sommet Depart
     * @param S2 Sommet Arrivée
     */
    @Override
    public void afficherplusCourtChemin(Sommet S1, Sommet S2) {
        List<Sommet> chemin = gf.plus_court_chemin(S1, S2);
        List<Arete> trace = new LinkedList<>();
        List<Arete> dontTrace = new LinkedList<>();
        String mess;
        l.getConsole().setText("");
        
        
        if (chemin != null){
        // On cherche les aretes à ne pas afficher
        for (int i = 1;i<chemin.size();i++){
            Arete tmpA = g.getArete2(chemin.get(i-1).getNom(), chemin.get(i).getNom());
            trace.add(tmpA);
        }
        
        for (Sommet tmp : chemin){
            Arete tmpA = tmp.getArete();
            while (tmpA != null){
                if (!trace.contains(tmpA)){
                    dontTrace.add(tmpA);
                }
                tmpA = tmpA.getSuivant();
            }
        }
            
            l.getGraphScreen().Afficher_Chemin(chemin,trace);
            mess = "Chemin le plus court entre " + S1.getNom() + " et " + S2.getNom() + " : ";
            mess += S1.getNom() + " --> ";
            for ( Sommet tmp : chemin){
                if (!tmp.equals(S2) && tmp.getNom()!= null  && !tmp.equals(S1)){
                mess += tmp.getNom() + " --> ";
                }
            }
            mess += S2.getNom() + " . ";
            mess += "Distance du plus court chemin : " + (int)gf.distance_plus_court_chemin(S1, S2);
        }
        else {
            mess = "Les 2 sommets ne sont pas joignables ";
        }  
        l.getConsole().setText(mess);
        l.revalidate();
        l.repaint();
        
       }

    /**
     * Methode pour afficher le chemin le plus fiable entre 2 sommets 
     * @param S1 Sommet Depart
     * @param S2 Sommet Arrivée
     */
    @Override
    public void afficherplusFiableChemin(Sommet S1, Sommet S2) {
        List<Sommet> chemin = gf.plus_fiable_chemin(S1, S2);
        List<Arete> trace = new LinkedList<>();
        List<Arete> dontTrace = new LinkedList<>();
        String mess;
        l.getConsole().setText("");
        
        if (chemin != null){
        // On cherche les aretes à ne pas afficher
        for (int i = 1;i<chemin.size();i++){
            Arete tmpA = g.getArete2(chemin.get(i-1).getNom(), chemin.get(i).getNom());
            trace.add(tmpA);
        }
        
        for (Sommet tmp : chemin){
            Arete tmpA = tmp.getArete();
            while (tmpA != null){
                if (!trace.contains(tmpA)){
                    dontTrace.add(tmpA);
                }
                tmpA = tmpA.getSuivant();
            }
        }
        }
        
        if (gf.distance_plus_fiable_chemin(S1, S2) != 0){
            
            l.getGraphScreen().Afficher_Chemin(chemin,dontTrace);
            mess = "Chemin le plus fiable entre " + S1.getNom() + " et " + S2.getNom() + " : ";
            mess += S1.getNom() + " --> ";
            for ( Sommet tmp : chemin){
                if (!tmp.equals(S2) && tmp.getNom()!= null  && !tmp.equals(S1)){
                mess += tmp.getNom() + " --> ";
                }
            }
            mess += S2.getNom() + " . ";
            mess += "Fiabilite du plus fiable chemin : " + (int)gf.distance_plus_fiable_chemin(S1, S2);
        }
        else {
            mess = "Les 2 sommets ne sont pas joignables ";
        }  
        l.getConsole().setText(mess);
        l.revalidate();
        l.repaint();
    }

    /**
     * Methode pour afficher une fenetre de modification d'une arete
     * @param S1 Sommet 1 de l'arete
     * @param S2 Sommet 2 de l'arete
     */
    @Override
    public void fonctionModifierArrete(Sommet S1, Sommet S2) {
        List<Noeud> noeuds = l.getGraphScreen().getNoeuds();
        TableModelModArete tableModelModArete = new TableModelModArete();
        Noeud noeud = null;
        Noeud noeudDest = null;
        
        for (Noeud tmp : noeuds){
            if (tmp.getS().equals(S1)){
                noeud = tmp;
            }
            if (tmp.getS().equals(S2)){
                noeudDest = tmp;
            }
        }
        Arete tmpA = S1.getArete();
            while (tmpA != null){
                
                if (noeudDest != null && tmpA.getDest().equals(S2.getNom())) {
                   tableModelModArete.addArete(noeud, noeudDest,tmpA);
                }
                tmpA = tmpA.getSuivant();
            }
        // On crée notre fenetre 
        ModArete modArete = new ModArete(this,tableModelModArete,"Modifier une arete");
        modArete.setLocation(l.getX(),l.getY());
        modArete.setVisible(true);
    }

    /**
     * Methode pour afficher une fonction de modification de Sommet
     * @param S Sommet
     */
    @Override
    public void fonctionModifierSommet(Sommet S) {
        List<Noeud> noeuds = l.getGraphScreen().getNoeuds();
        TableModelModSommet noeudModelTable = new TableModelModSommet();
        Noeud n = null;
        
        for (Noeud tmp : noeuds){
            if (tmp.getS().equals(S)){
                n = tmp;
            }
        }
         noeudModelTable.addNoeud(n);
        ModSommet modSommet = new ModSommet(this,noeudModelTable,"Modifier un Sommet");
        modSommet.setLocation(l.getX(),l.getY());
        modSommet.setVisible(true);
    }
    
    /**
     * Methode pour permettre l'affichage d'une comparaison entre 2 noeuds
     * @param S1 Sommet 1
     * @param S2 Sommet 2
     */
    @Override
    public void Comparaison2noeuds(Sommet S1, Sommet S2) {
        
        // on invoque une JOptionPanel pour chosir le type
        String type = null;
        while (type == null){
            String response = JOptionPane.showInputDialog(l,"Saisissez le type désiré");
            if (response.equals("M") || response.equals("O") || response.equals("N")){
                type = response;
            }
            else {
                JOptionPane.showMessageDialog(l,"Veuillez saisir une de ces 3 options : M || N ||O","Erreur de saisie ",JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // On recupere le sommet vainqueur et le nombre de voisins de chaque
        Sommet result   = gf.comparaison_2_noeuds(S1, S2, type);
        int nbcentresS1        = gf.lister_voisins_par_type(result,type).size();
        int nbcentresS2;
        if (result == S1){
            nbcentresS2 = gf.lister_voisins_par_type(S2, type).size();
        }
        else{
            nbcentresS2 = gf.lister_voisins_par_type(S1, type).size();
        }
        
        // On affiche le resultat
        if (nbcentresS1 != nbcentresS2){
            l.getConsole().setText(result.getNom() + " est le sommet qui a le plus de centres " + type + " à proximité : "+ nbcentresS1 + " tandis que l'autre centre a " + nbcentresS2 + " centres du meme type.");
        }
        else{
            l.getConsole().setText("Les 2 sommets ont le meme nombre de sommet de type " + type);
        }
        l.getGraphScreen().colorierNoeud(result, result);
    }

    /**
     * Methode pour supprimer un noeud
     * @param N Noeud
     */
    @Override
    public void supprimerNoeud(Noeud N) {
        gf.supprimerNoeud(N.getS());
        l.getGraphScreen().suppNoeud(N);
        l.revalidate();
        l.repaint();
    }

    /**
     * Methode pour supprimer une arete
     * @param N1 Noeud 1 de l'arete
     * @param N2 Noeud 2 de l'arete
     */
    @Override
    public void supprimerArete(Noeud N1, Noeud N2) {
        gf.supprimerArrete(N1.getS(), N2.getS());
        l.revalidate();
        l.repaint();
    }

    /**
     * Methode pour modifier une arete
     * @param N1 Noeud 1 de l'arete
     * @param N2 Noeud 2 de l'arete
     * @param dist Nouvelle distance
     * @param fiab Nouvelle Fiabilité
     * @param dur Nouvelle Durée
     */
    @Override
    public void modifierArete(Noeud N1, Noeud N2, double dist, double fiab, double dur) {
        gf.modifierArrete(N1.getS(), N2.getS(), dist, fiab, dur);
        l.revalidate();
    }

    /**
     * Methode pour afficher dans la console la verification de la proximité à 2-distance de 2 sommets
     * @param S1 Sommet 1
     * @param S2 Sommet 2
     */
    @Override
    public void check2Noeuds2Distance(Sommet S1, Sommet S2) {
        
        boolean result = gf.sommets_2distances(S1, S2);
        
        if(result){
            l.getConsole().setText("Les sommets sont à 2 distance");
        }
        else{
            l.getConsole().setText("Les sommets ne sont pas à 2 distance");
        }
    }

    /**
     * Methode pour generer un grapphe 
     * @param file1
     * @param file2 
     */
    @Override
    public void genererGraphe(String file1,String file2) {
        this.generate = true;
        Graphe g = new Graphe();
        Generation gen = new Generation(file1,file2,g,this);
        GrapheFonc graphefonc = new GrapheFonc(g);
        
        // On verifie si le graphe est generable
        if (generate){
        this.setG(g);
        this.setGf(graphefonc);
        this.l.newGraphScreen();
        l.disenable();
        }
    }

    /**
     * Methode pour recuperer un sommet à partir de son nom
     * @param sNom
     * @return 
     */
    @Override
    public Sommet getSommet(String sNom) {
        return g.getSommet(sNom);
    }

    /**
     * Methodes pour determiner en fonction des noeuds selectionnés quel set de fonctions proposer
     * @param nbSelectionnes Nombre de sommets selectionnés
     */
    @Override
    public void listerFonctions(int nbSelectionnes) {
        List<FoncEnum> fonctions= new ArrayList<>();
        FoncEnum response = null;
        // On vide la liste actuelle en premier temps
        fonctions.clear();
        
        if (nbSelectionnes == 0){
            // On liste les fonctions adaptées à la situation où aucun sommet n'est selectionné
            fonctions.add(FoncEnum.COMPLEXITEALGO);
            fonctions.add(FoncEnum.LISTERSOMMETS);
            fonctions.add(FoncEnum.LISTERARRETES);
            fonctions.add(FoncEnum.LISTERPARCATEGORIE);
                    
            // On appelle la JDialog
            ChooseFoncDialog tmp = new ChooseFoncDialog(null,true,fonctions);
            response = tmp.ShowDialog();
        }
        else if(nbSelectionnes == 1){
            // On liste les fonctions adaptées à la situation où Un seul somme et selectionné
            fonctions.add(FoncEnum.MODIFIERSOMMET);
            fonctions.add(FoncEnum.VOISINS1DISTANCE);
            fonctions.add(FoncEnum.VOISINS2DISTANCE);
                    
            // On appelle la JDialog 
            ChooseFoncDialog tmp = new ChooseFoncDialog(null,true,fonctions);
            response = tmp.ShowDialog();
            
        }
        else{
            // On liste les fonctions adaptées à la situationn ou les 2 sommets sont selectionnés
            fonctions.add(FoncEnum.PLUSCOURT);
            fonctions.add(FoncEnum.PLUSFIABLE);
            fonctions.add(FoncEnum.MODIFIERARRETE);
            fonctions.add(FoncEnum.COMPARAISON2NOEUDS);
            fonctions.add(FoncEnum.LISTERVOSINSTYPE);
            fonctions.add(FoncEnum.CHECK2DISTANCE);
                    
            // On appelle la JDialog 
            ChooseFoncDialog tmp = new ChooseFoncDialog(null,true,fonctions);
            response = tmp.ShowDialog();
            this.DeclencherFonction(response);
                
            
        }
        if (response != null){
        this.DeclencherFonction(response);
        }
        else{ JOptionPane.showMessageDialog(this.getL(), "Aucune fonction selectionée", "Choix invalide", JOptionPane.ERROR_MESSAGE);}
    }

    /**
     * Methode pour verifier si un graphe est chargé
     * @return 
     */
    @Override
    public boolean graphCheck() {
        if (this.g!= null ){ return true;}
        else  {return false;}
    }

    /**
     * Methode pour notifier cette classe que le chargement du graphe a echoué
     */
    @Override
    public void errorGraph() {
        this.l.errorGraph();
        this.generate = false;
    }
    

    /**
     * Methode pour ajouter un noeud sur le graphe 
     * @param Type
     */
    @Override
    public void ajouterNoeud(String Type) {
        
        // On determine un nom au sommet
        int idSommet = g.compte_Sommets() +1;
        String nomSommet = "S" + idSommet;
        
        g.addSommet(nomSommet, Type);
        this.l.newGraphScreen();
    }

    @Override
    public void ajouterArete(Sommet S1, Sommet S2, double dist, double fiab, double dur) {
        if (!g.checkArrete(S1.getNom(), S2.getNom())){
            g.addArete(S1.getNom(), S2.getNom(), fiab, dist, dur);
            this.l.newGraphScreen();
        }
        else{
            this.l.message();
        }
    }
    //</editor-fold>

}



