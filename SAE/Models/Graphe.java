package sae.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class Graphe {
    public class Arete {
        private double fiab;
        private double dist;
        private double dur;
        private String dest;
        private Arete suivant;

        Arete(double f, double dt , double dr,String d){
            fiab = f;
            dist = dt;
            dur = dr;
            dest = d;
            suivant = null;
        }
        public Arete getSuivant(){
        return this.suivant;
        }
        public String getDest(){
        return this.dest;
        }
        public double getDist(){
        return this.dist;
        }
        public double getDur(){
        return this.dur;
        }
        public double getFiab(){
        return this.fiab;
        }

        public void setFiab(double fiab) {
            this.fiab = fiab;
        }

        public void setDist(double dist) {
            this.dist = dist;
        }

        public void setDur(double dur) {
            this.dur = dur;
        }

        public void setSuivant(Arete suivant) {
            this.suivant = suivant;
        }
        
    }
    public class Sommet{
        private String Nom;
        private String type;
        private Arete lVois;
        private Sommet suivant;
        Sommet(String N,String T){
            Nom = N;
            type = T;
            lVois = null;
            suivant = null;
        }
        public String getNom(){
            return this.Nom;
        }
        public Arete getArete(){
        return this.lVois;
        }
        public Sommet getSuivant(){
        return this.suivant; 
        }
        public String getType(){
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setSuivant(Sommet suivant) {
            this.suivant = suivant;
        }

        public void setlVois(Arete lVois) {
            this.lVois = lVois;
        }
        
    }
    private Sommet Premier;
    public Graphe(){
        Premier = null;
    }
    
    //  Accesseurs
    public Sommet getPremier(){
        return Premier;
    }

    public void setPremier(Sommet Premier) {
        this.Premier = Premier;
    }
    
   
    // Fonction qui permet d'ajouter un sommet au graphe
    public void addSommet(String N,String T){
        Sommet nouv = new Sommet(N,T);
        nouv.suivant = this.Premier;
        this.Premier = nouv;
    }

    // Fonction qui permet d'ajouter une arrete au graphe
    public void addArete(String s1,String s2,double fiab,double dist,double dur){
        Arete nouv = new Arete(fiab,dist,dur,s2);
        Sommet tmp = this.Premier;
        while(tmp != null && !(tmp.Nom.equals(s1))){
            tmp = tmp.suivant;
        }
        if (tmp != null){
        nouv.suivant = tmp.lVois;
        tmp.lVois = nouv;
        Arete nouv2 = new Arete(fiab,dist,dur,s1);
        tmp = this.Premier;
        while(!(tmp.Nom.equals(s2))){
            tmp = tmp.suivant;
        }
        nouv2.suivant = tmp.lVois;
        tmp.lVois = nouv2;
    }
    }
    // Fonction pour trouver une arrete
    public Arete getArete2(String s1, String s2){
        Sommet tmp = this.Premier;
        // On retrouve le sommet s1
        while (tmp != null && !tmp.Nom.equals(s1)){
            tmp = tmp .suivant;
        }
        if(tmp == null ){return null;}
        
        // On cherche la destination s2 parmi les arretes de s1 pour trouver l'arrete voulue
        Arete result = tmp.lVois;
        
        while (result != null){
            if(result.dest.equals(s2)){
                return result;
            }
            result = result.suivant;
        }
       
        return null;
    }
    // Fonction pour obtenir un sommet à partir de son Nom
    public Sommet getSommet(String sNom){
        if (sNom != null){
        Sommet result = this.Premier;
        while (result!= null && !result.Nom.equals(sNom) ){
                result = result.suivant;
            }
        return result;
        }
        else{ return null;}
    }
    // Fonction qui permet d'afficher la fiablilité, la distance, la durée de trajets entre les arrêtes du graphe
    public String toString() {
        String s = "";
        Sommet tmp = this.Premier;
        while (tmp != null) {
            s = s + tmp.Nom + " : ";
            Arete tmp2 = tmp.lVois;
            while (tmp2 != null) {
                s = s + tmp2.dest + "(Fiabilite: " + tmp2.fiab + ",distance : " + tmp2.dist + ", duree : " + tmp2.dur
                        + "), ";
                tmp2 = tmp2.suivant;
            }
            s = s + '\n';
            tmp = tmp.suivant;
        }
        return s;

    }
    
    // Fonction qui permet de trouver l'arrête des deux sommets en parametre
    public boolean checkArrete(String S1,String S2){
        Boolean res = false;

        Sommet tmp = this.Premier;
        
        while (tmp != null && !tmp.Nom.equals(S1)){
            tmp = tmp .suivant;
        }
        if ( tmp == null) return false;
        
        
        Arete tmpA = tmp.lVois;
        while(tmpA != null){
            if(Objects.equals(tmpA.dest,S2)){
                res = true;
            }
            tmpA = tmpA.suivant;
        }

        return res;
    }

    // Fonction qui permet de compter le nombre d'arrêtes du graphe.
    public int compte_Arretes(){
        int conteur = 0;
        Sommet tmp = this.Premier;
        while(tmp!= null){
            Arete tmpA = tmp.lVois;
            while (tmpA != null){
                tmpA = tmpA.suivant;
                conteur+=1;
            }
            tmp = tmp.suivant;
        }
        return (conteur/2);
    }

    // Fonction qui permet de compter le nombre de sommets du graphe.
    public int compte_Sommets(){
        int conteur = 0;
        Sommet tmp = this.Premier;
        while(tmp!= null){
            tmp = tmp.suivant;
            conteur+=1;
        }
        return conteur;
    }
    public double getDistanceMax(){
        double max = 0;
        
        Sommet tmp = this.Premier;
        while(tmp!= null){
            Arete tmpA = tmp.lVois;
            while (tmpA != null){
                if(tmpA.dist > max ){
                    max = tmpA.dist;
                }
                tmpA = tmpA.suivant;
            }
            tmp = tmp.suivant;
        }
        return max;
    }
    public double getFiabiliteMax(){
        double max = 0;
        
        Sommet tmp = this.Premier;
        while(tmp!= null){
            Arete tmpA = tmp.lVois;
            while (tmpA != null){
                if(tmpA.fiab > max ){
                    max = tmpA.fiab;
                }
                tmpA = tmpA.suivant;
            }
            tmp = tmp.suivant;
        }
        return max;
    }
    
    
}

