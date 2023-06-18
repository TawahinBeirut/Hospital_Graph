/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Model;

import sae.Model.Graphe.Sommet;

/**
 * Queue pour l'algorithme dijkstra Distance
 * @author gheri
 */
public class QueueDist implements Comparable<QueueDist>{
    
    // Attributs
    public Sommet sommet;
    public double priorite;
    
    // Constructeur
    public QueueDist(Sommet s,double p){
        this.sommet = s;
        this.priorite = p;
    }

    
    @Override
    public int compareTo(QueueDist o) {
        if (this.priorite == o.priorite){
            return 0;
        }
        else if (this.priorite < o.priorite){
            return 1;
        }
        else {
            return -1;
        }
    }
    
}
