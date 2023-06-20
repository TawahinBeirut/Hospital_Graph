/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sae;

import sae.Model.Noeud;
import java.util.List;
import java.util.Map;
import sae.Model.FoncEnum;
import sae.Model.Graphe.Sommet;
import sae.Model.Noeud;
import sae.Vue.LogicielDecision;

/**
 *
 * @author gheri
 */
public interface Logiciel {
    
    public void genererGraphe(String file1,String file2);
    public Sommet getSommet(String sNom);
    public LogicielDecision getL();
    public boolean graphCheck();
    public void errorGraph();
    
    public List<Noeud> generationNoeuds();
    public void noeudClique(Sommet S,int sourceID);
    public void noeudsCliques(Sommet S1, Sommet S2,int sourceID);
    public void DeclencherFonction(FoncEnum fonc);
    public void deselectionnerNoeud(Sommet s1,Sommet s2);
    public List<Noeud> generationNoeudsDistance1(Sommet s);
    public List<Noeud> generationNoeudsDistance2(Sommet s);
    
    public void afficherComplexiteAlgo();
    public void listerSommets();
    public void listerArretes();
    public void listerParCategorie();
    public void listerVoisinsType(Sommet S1, Sommet S2);
    public void listerFonctions(int nb);
    
    public void fonctionModifierSommet(Sommet S);
    public void afficherVoisins1Distance(Sommet S);
    public void afficherVoisins2Distance(Sommet S);
    
    public void afficherplusCourtChemin(Sommet S1,Sommet S2);
    public void afficherplusFiableChemin(Sommet S1, Sommet S2);
    public void fonctionModifierArrete(Sommet S1, Sommet S2);
    public void check2Noeuds2Distance(Sommet S1, Sommet S2);
    public void Comparaison2noeuds(Sommet S1,Sommet S2);
    
    public void ajouterNoeud(String Type);
    public void ajouterArete(Sommet S1, Sommet S2, double dist,double fiab,double dur);
    public void supprimerNoeud(Noeud N);
    public void supprimerArete( Noeud N1, Noeud N2);
    public void modifierArete(Noeud N1, Noeud N2,double dist,double fiab,double dur);
}
