/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modele pour le modele de la table pour lister les sommets
 * @author gheri
 */
public class TableModelModSommet extends AbstractTableModel{
    // Attributs
    private final String[] titles = {"Noeud","Type","X","Y"};
    private final List<Noeud> listNoeuds = new LinkedList<>();
    
    public void addNoeud(Noeud noeud){
        listNoeuds.add(noeud);
        fireTableRowsInserted(0, listNoeuds.size() + 1);
        
    } 
    
    public List<Noeud> getList(){
        return listNoeuds;
    }
    
    public void delNoeud(Noeud noeud){
        listNoeuds.remove(noeud);
        fireTableDataChanged();  
    }
    @Override
    public String getColumnName(int index) {
        return titles[index];
    }
    @Override
    public int getRowCount() {
        return listNoeuds.size();
    }
    
    @Override
    public int getColumnCount() {
        return titles.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Noeud noeud = listNoeuds.get(rowIndex);
        switch(columnIndex){
            case 0  : return noeud.getS().getNom();
            case 1  : return noeud.getS().getType();
            case 2  : return noeud.getNoeudX();
            case 3  : return noeud.getNoeudY();
            default : return null;
           
        }
    }
    
    
    
}
