/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import sae.Model.Graphe.Arete;

/**
 * Modele de Table pour les listes d'aretes
 * @author gheri
 */
public class TableModelModArete extends AbstractTableModel{

    // Attributs
    private final String[] titles = {"Index","Noeud 1","Noeud 2","Distance","Durée","Fiabilité"};
    private final Map<Integer,Arete> mapAretes  = new HashMap<>();
    private final Map<Integer,Noeud> mapNoeuds1 = new HashMap<>();
    private final Map<Integer,Noeud> mapNoeuds2 = new HashMap<>();
    private int index =0;
    
    public void addArete(Noeud N1,Noeud N2,Arete A){
        mapAretes.put(index,A);
        mapNoeuds1.put(index,N1);
        mapNoeuds2.put(index, N2);
        
        fireTableRowsInserted(0, mapAretes.size() + 1);
        index++;
    }
    public void delArete(int index){
        // If pour pallier à un bug 
        if (index != mapAretes.size() -2){
            
        mapAretes.remove(index);
        mapNoeuds1.remove(index);
        mapNoeuds2.remove(index);
        
        // On modifie la valeur des indexs dans les maps
        for (int i =index +1; i< mapAretes.size();i++){
                mapAretes.put(i-1, mapAretes.get(i));
                mapNoeuds1.put(i-1, mapNoeuds1.get(i));
                mapNoeuds2.put(i-1, mapNoeuds2.get(i));
        }
        if (index != mapAretes.size()){
        mapAretes.remove(mapAretes.size() -1);
        mapNoeuds1.remove(mapNoeuds1.size()-1);
        mapNoeuds2.remove(mapNoeuds2.size() -1);
        }
        
        }
        else {
            mapAretes.put(mapAretes.size()-2,mapAretes.get(mapAretes.size()-1));
            mapNoeuds1.put(mapNoeuds1.size()-2,mapNoeuds1.get(mapNoeuds1.size()-1));
            mapNoeuds2.put(mapNoeuds2.size()-2,mapNoeuds2.get(mapNoeuds2.size()-1));
            
            mapAretes.remove(mapAretes.size() -1);
            mapNoeuds1.remove(mapNoeuds1.size()-1);
            mapNoeuds2.remove(mapNoeuds2.size()-1);
        }
        
        fireTableDataChanged();
    }
    public Map<Integer,Arete> getMapArete(){
        return mapAretes;
    }
    public Map<Integer,Noeud> getMap1Noeud(){
        return mapNoeuds1;
    }
    public Map<Integer,Noeud> getMap2Noeud(){
        return mapNoeuds2;
    }
    
    @Override
    public String getColumnName(int index){
        return titles[index];
    }
    
    @Override
    public int getRowCount() {
        return mapAretes.size();
    }

    @Override
    public int getColumnCount() {
        return titles.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Noeud noeud1 = mapNoeuds1.get(rowIndex);
        Noeud noeud2 = mapNoeuds2.get(rowIndex);
        Arete arete = mapAretes.get(rowIndex);
        
        switch(columnIndex){
            case 0  : return "A" + (rowIndex+1);
            case 1  : return noeud1.getS().getNom();
            case 2  : return noeud2.getS().getNom();
            case 3  : return (int)arete.getDist();
            case 4  : return (int)arete.getDur();
            case 5  : return (int)arete.getFiab();
            default : return null;
        }
    }
    
}
