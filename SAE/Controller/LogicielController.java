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