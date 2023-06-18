/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import sae.Model.Graphe.Arete;
import sae.Model.Graphe.Sommet;

/**
 * Modele du Noeud ( Sommet + position X et Y)
 * @author gheri
 */
public class Noeud {
    
    // Attributs
    private Sommet s;
    private int noeudX;
    private int noeudY;
    private Color color;
    
    // Constructeur
    public Noeud(Sommet so,int newNoeudX, int newNoeudY){
        
        this.s = so;
        this.noeudX = newNoeudX;
        this.noeudY = newNoeudY;
        this.color = Color.red;
        
    }
    
    // Accesseurs 

    public Sommet getS() {
        return s;
    }

    public int getNoeudX() {
        return noeudX;
    }

    public int getNoeudY() {
        return noeudY;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setNoeudX(int noeudX) {
        this.noeudX = noeudX;
    }

    public void setNoeudY(int noeudY) {
        this.noeudY = noeudY;
    }

    public void setS(Sommet s) {
        this.s = s;
    }
    
}
