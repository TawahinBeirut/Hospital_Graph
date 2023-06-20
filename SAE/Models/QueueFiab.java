/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Model;

/**
 * Queue pour l'alogrithme dijktra Fiabilité
 * @author gheri
 */
public class QueueFiab implements Comparable<QueueFiab>{

    // Attributs
    public Graphe.Sommet sommet;
    public double priorite;
    
    // Constructeur
    public QueueFiab(Graphe.Sommet s,double p){
        this.sommet = s;
        this.priorite = p;
    }

    
    @Override
    public int compareTo(QueueFiab o) {
        
        if (this.priorite == o.priorite){
            return 0;
        }
        else if (this.priorite > o.priorite){
            return 1;
        }
        else {
            return -1;
        }
    
    }
    
}
