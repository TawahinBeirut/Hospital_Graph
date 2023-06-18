/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import sae.Model.Graphe;
import sae.Model.Graphe.Arete;
import sae.Model.Graphe.Sommet;
import sae.Model.GrapheException;
import sae.Model.QueueDist;
import sae.Model.QueueFiab;

/**
 *
 * @author gheri
 */
public class GrapheFonc {
    
    // Attribut 
    /**
     * Attribut correspondant au graphe chargé
     */
    Graphe g;
    
    // Constructeur tres basique
    GrapheFonc(Graphe newG){
        this.g = newG;
    }
    
    // <editor-fold defaultstate="collapsed" desc="PUBLIC METHODES">
    
    /**
     * Methode qui permet pour un sommet donné, le listage des sommets voisins directs.
     * @param S1 Sommet 
     * @return 
     */
    public List<Sommet> lister_voisins(Sommet S1){
        List<Sommet> result = new ArrayList<>();
  
        Arete tmpA = S1.getArete();
        while(tmpA != null){
            Sommet dest = g.getSommet(tmpA.getDest());
            result.add(dest);
            tmpA = tmpA.getSuivant();
        }
        return result;
    }

    /**
     * Methode qui renvoie pour un sommet donné, le listage des voisins direct d’un type donné (O, M ou N).
     * @param S1 Sommet
     * @param type String correspondant au type voulu
     * @return 
     */
    public List<Sommet> lister_voisins_par_type(Sommet S1,String type){
        
        List<Sommet> result = new LinkedList<Sommet>();
            
        Arete tmpA = S1.getArete();
        while(tmpA != null){
            
            Sommet dest = g.getSommet(tmpA.getDest());
            
            if(Objects.equals(dest.getType(),type)){
                result.add(dest);
            }
            tmpA = tmpA.getSuivant();
        }
        return result;
    }

    /**
     * Methode qui permet de dire si 2 sommets sont à 2-distance ou non.
     * @param S1
     * @param S2
     * @return 
     */
    public boolean sommets_2distances(Sommet S1,Sommet S2){
       
        Boolean res = false;
        
        // Cas où les 2 sommets indiqués sont les memes 
        if (S1.equals(S2)){ return true;}
           
        // Double boucle pour verifier si il est à 2-distance
        Arete tmpA = S1.getArete();
        while(tmpA != null){
            
            // On parcourt tous les sommets et on voit si cela correspond à la destination de l'arrete en cours
            Sommet tmp2 = g.getPremier();
            while (tmp2!= null && !Objects.equals(tmp2.getNom(),tmpA.getDest())){
                tmp2 = tmp2.getSuivant();
            }
            
            // On verifie si le sommet en court est bien un voisin ou directement le sommet qu'on cherche
           
            if (tmp2 != null){
                
            if (tmp2.equals(S2)){res = true;}
            Arete tmpA2 = tmp2.getArete();
            while(tmpA2 != null){
                if(Objects.equals(tmpA2.getDest(),S2.getNom())){
                    res = true;
                }
                tmpA2 = tmpA2.getSuivant();
            }
            tmpA = tmpA.getSuivant();
        }
        }
        return res;
    }
    
    /**
     * Fonction qui trouve une route traversant au moins un dispensaire de chaque catégorie.
     * @return 
     */
    public String route_dispensaires() {
        String res = "";
        boolean foundM = false;
        boolean foundN = false;
        Sommet tmp = g.getPremier();
        
        while (tmp != null) {
            if (Objects.equals(tmp.getType(), "O")) {
                Arete tmpA = tmp.getArete();
                while (tmpA != null) {
                    Sommet tmp2 = g.getPremier();
                    while (tmp2 != null && !Objects.equals(tmp2.getNom(), tmpA.getDest())) {
                        tmp2 = tmp2.getSuivant();
                    }
                    if (Objects.equals(tmp2.getType(), "M")) {
                        foundM = true;
                        Arete tmpA2 = tmp2.getArete();
                        while (tmpA2 != null) {
                            Sommet tmp3 = g.getPremier();
                            while (tmp3 != null && !Objects.equals(tmp3.getNom(), tmpA2.getDest())) {
                                tmp3 = tmp3.getSuivant();
                            }
                            if (Objects.equals(tmp3.getType(), "N")) {
                                foundN = true;
                                res = tmp.getNom() + " -> " + tmp2.getNom()+ " -> " + tmp3.getNom();
                            }
                            tmpA2 = tmpA2.getSuivant();
                        }
                    }
                    tmpA = tmpA.getSuivant();
                }
            }
            tmp = tmp.getSuivant();
        }
        
        if (!foundM || !foundN) {
            res = "Il n'y a pas de route traversant au moins un dispensaire de chaque catégorie";
        }
        
        return res;
    }    
    
    /**
     * Methode pour lister les sommets voisins d’un type donné des centres S1 et S2.
     * @param s1 Sommet 1
     * @param s2 Sommet 2
     * @return String
     * @throws GrapheException 
     */
    public String voisins_2_sommets(String s1, String s2) throws GrapheException{
        ArrayList<Sommet> resultat = new ArrayList<>();
        
        // Boucle pour trouver le sommet S1
        Sommet tmpS = g.getPremier();
        while (tmpS != null && !tmpS.getNom().equals(s1)){
            tmpS = tmpS.getSuivant();
        }
        if( tmpS == null){ return null;}
        
        Arete s1Arrete = tmpS.getArete();
            
        while(s1Arrete != null){
            Sommet voisin = g.getPremier();
                
            // Verifie si le sommet est bien la destination de l'arrete et donc un voisin de s1
            while(voisin != null &&!voisin.getNom().equals(s1Arrete.getDest())){
                voisin = voisin.getSuivant();
            }
                
            // Verifie si la destination existait bien sinon erreur dans le graphe
            if (voisin == null) { throw new GrapheException();}
                
            Arete tmp = voisin.getArete();
                
            // Boucle pour verifier si un des voisins du voisin du sommet s1 est s2
            while (tmp != null){
                if(tmp.getDest().equals(s2)){
                     
                    Sommet tmp2 = g.getPremier();
                    
                    // Boucle pour retrouver la dest qui est donc voisin de s1 et s2
                    while (tmp2 != null && !tmp2.getNom().equals(tmp.getDest()) && tmp2 != voisin){
                        tmp2 = tmp2.getSuivant();
                    }
                    
                    // On verifie que le graphe ne contient pas d'erreur
                    if(tmp2 == null ){throw new GrapheException();}
                    else{
                        resultat.add(tmp2);
                    }
                }
                tmp = tmp.getSuivant(); 
            }
                
            s1Arrete = s1Arrete.getSuivant();
        }
        
        
        // Conversion des resultats en format texte
        String textResultat;
        if (resultat.isEmpty()){
            textResultat = " Pas de voisins communs entre " + s1 + " et " + s2;
        }
        else{
            textResultat = "Voisins communs entre " + s1 + " et " + s2 + " : ";
            for (Sommet tmp : resultat){
                textResultat += tmp.getNom()+ " ";
            }
        }
        return textResultat;
    }
    

    /**
     * Methode permettant de donner tous les chemins plus court en distance entre 2 sites pour un sommet donné (Dijkstra). Gheribi Abd-el-rahmen
     * @param s1
     * @return 
     */
    public HashMap[] DijkstraPlusCourt(Sommet s1){
       
        HashMap<Sommet,Double> distances    = new HashMap<>();
        HashMap<Sommet,Sommet> precedents   = new HashMap<>();
        
        // Sorte de file d'attente
        PriorityQueue<QueueDist> queue = new PriorityQueue<>();
        
        // Algorithme Djikistra
        
        // Initialisation 
        
        queue.add(new QueueDist(s1,0));
        
        // On met toute les distances pour chaque sommet à l'infini sauf s1 à 0.
        Sommet tmpInit = g.getPremier();
        while (tmpInit != null){
            if (tmpInit != s1){
                distances.put(tmpInit,(double)Integer.MAX_VALUE); // Equivalent de l'infini
            }
            precedents.put(tmpInit,null);
            tmpInit = tmpInit.getSuivant();
        }
        distances.put(s1, (double)0);
        
        // Debut
        while (queue.size() != 0){
            Sommet traite = queue.poll().sommet;
            
            // On traverse les arretes du sommet traite
            Arete tmpA = traite.getArete();
            while (tmpA != null){
                Double alternative =distances.get(traite) + tmpA.getDist();
                
                // On trouve le sommet destinataire
                Sommet dest = g.getPremier();
                while (!dest.getNom().equals(tmpA.getDest())){
                    dest = dest.getSuivant();
                }
                
                // On compare l'alternative à distance
                if ( alternative  < distances.get(dest)){
                    distances.put(dest, alternative);
                    precedents.put(dest, traite);
                    queue.add(new QueueDist(dest,distances.get(dest)));
                }
                tmpA = tmpA.getSuivant();
            }
        }
        return new HashMap[]{distances,precedents};
    }
    
    /**
     * Methode pour donnee le plus court chemin basé sur la methode Dijkstra
     * @param s1 Sommet depart
     * @param s2 Sommet arrivée
     * @return Liste dde sommets
     */
    public List<Sommet> plus_court_chemin(Sommet s1,Sommet s2){
        
        // On verifie que les sommets sont joignables
        
        // On recupere la HashMap des precedents
        HashMap[] dijkstraResult = DijkstraPlusCourt(s1);
        HashMap<Sommet,Sommet> precedents = dijkstraResult[1];
        HashMap<Sommet,Double> distances = dijkstraResult[0];
        
        double distanceTotale = distances.get(s2);
        // On verifie que la distance ne correspond pas à l'infini et que donc ca soit joibnable
        if (!(distanceTotale == (double)Integer.MAX_VALUE)){
        
        ArrayList<Sommet> chemin = new ArrayList<>();
        Sommet dest = s2;
        
        while(dest != null){
            chemin.add(0,dest);
            dest = precedents.get(dest);
        }
        return chemin;
        }
        else return null;
    }
    
    
    /**
     * Methode pour donner la distance du plus court chemin basé sur la methode dijkstra
     * @param s1
     * @param s2
     * @return 
     */
    public double distance_plus_court_chemin(Sommet s1,Sommet s2){
       
        HashMap[] dijkstraResult = DijkstraPlusCourt(s1);
        HashMap<Sommet,Double> distances = dijkstraResult[0];
        
        double distanceTotale = distances.get(s2);
        
        // On verifie que la distance ne correspond pas à l'infini
        if (!(distanceTotale == (double)Integer.MAX_VALUE)){
        return distanceTotale;}
        else return 0;
    }
    
    