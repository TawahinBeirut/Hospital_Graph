package sae.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import sae.Model.Graphe;

import static java.lang.Integer.parseInt;
import sae.Logiciel;
import sae.Model.Graphe.Arete;
import sae.Model.Graphe.Sommet;

/**
 * Cette classe représente la partie où à partir d'un Ficher csv notre gaphe est generé du projet
 * 
 * @author Gheribi
 * @version 1.1
 */
public class Generation {
    
    // <editor-fold defaultstate="collapsed" desc="ATTRIBUTS">
    
    /**
     * Correspond au chemin relatif au dossier SAE du fichier csv (liste d'adjacence)
     */
    static String path1 = null ;
    
    /**
     * Correspond au chemin relatif au dossier SAE du fichier csv (liste de sucesseurs)
     */
    static String path2 = null;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTEUR">
    
    /**
     * Génere le graphe et demande au logiciel d'afficher une fenetre d'errreur si les fichiers ne sont pas valides
     * @param newPath1 Chemin relatif du fichier csv "Liste-adjacence"
     * @param newPath2 Chemin relatif du fichier csv "Liste-sucesseurs"
     * @param g        Modele du graphe
     * @param l        Interface logicielle
     */
    public Generation(String newPath1,String newPath2,Graphe g,Logiciel l){
        this.path1 = newPath1;
        this.path2 = newPath2;
        try{
        this.Creation_sommets(g);
        this.Creation_arretes(g);
        }
        catch (IOException e){
            l.errorGraph();
        }
    }
    //</editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="PRIVATE METHODES">
    /**
     * Methode permettent de trier les sommmets du fichier 2
     * @param tab tableau de String d'une ligne du fichier 2 avec pour séparateur le ";"
     */
    private static void Tri(String[] tab ){
        for (int i = 1; i < tab.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < tab.length; j++)
            {
                if (parseInt(tab[j]) < parseInt(tab[index])){
                    index = j;
                }
            }
            String  min = tab[index];
            tab[index] = tab[i];
            tab[i] = min;
        }
    }
    /**
     * Methode permettant de generer les sommets du graphe à partir du fichier 1 (liste-adjacence)
     * @param g Modele du graphe
     * @throws IOException 
     */
    private void Creation_sommets(Graphe g) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(path1));
        String line;
        String[] lines;
        while ((line = br.readLine()) != null){
            lines = line.split(";");
            
            // Permet d'eliminer les fichiers non adaptés
            if (lines.length >= 2){
            // Sert à enlever d'eventuels espaces 
            lines[0] = lines[0].replaceAll("\\s","");
            lines[1] = lines[1].replaceAll("\\s","");
            g.addSommet(lines[0],lines[1]);
            }
            else{
                throw new IOException();
            }
        }
    }

    /**
     * Methode permettant de generer les aretes du graphe à partir du fichier 1 (liste-adjacence) et 2 (liste sucesseurs)
     * @param g Modele du graphe
     * @throws IOException 
     */
    private void Creation_arretes(Graphe g) throws IOException{
        
        // Initialisation 
        BufferedReader br = new BufferedReader(new FileReader(path1));
        BufferedReader br2 = new BufferedReader(new FileReader(path2));
        String line;
        String line2;
        String[] lines;
        String[] lines2;
        
        
        // Pour chaque ligne on decompose la ligne pour generer des objets
        while (((line = br.readLine()) != null) && ((line2 = br2.readLine()) != null)){
            lines = line.split(";");
            lines2 = line2.split(";");
            
            // Permet d'eliminer les fichiers non adaptés
            if ( lines2.length != lines.length){
            Tri(lines2);
            int counter = 1;
                for (int i = 2; i < lines.length; i++) {
                    if (!Objects.equals(lines[i], "0")) {
                        lines[i] = lines[i].replaceAll("\\s","");
                        String[] tmplines = lines[i].split(",");
                        
                        // On verifie qu'on a pas deja crée l'arrete entre les 2 sommets precedemment
                        if(!g.checkArrete("S"+lines2[counter],lines[0])) {
                            g.addArete(lines[0], "S" + lines2[counter], parseInt(tmplines[0]), parseInt(tmplines[1]), parseInt(tmplines[2]));

                        }
                        counter += 1;
                    }
                }
            }
            else {
                throw new IOException();
            }
        }
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="PUBLIC METHODES">
    /**
     * Methode pour modeliser un fichier csv à partir d'un Modele de graphe
     * @param g Modele du graphe
     * @throws IOException 
     */
    public void sauvegarde_fichier(Graphe g) throws IOException{
        
        // On chosit un nom Adapté
        int nbFichiers = 0;
        String fileName1 = "Liste-succeseurs";
        String fileName2 = "Liste-adjacence";
        boolean fileExist = true;
        
        while (fileExist){
        File file1 = new File(fileName1);
        if (file1.exists()){
            nbFichiers++;
            fileName1 = "Liste-succeseurs";
            fileName1 += " (" + nbFichiers + ")";
            fileName2 = "Liste-adjacence";
            fileName2 += " (" + nbFichiers + ")";
        }
        else{
            fileExist = false;
        }
        }
        
        // On parcourt le graphe et on crée le premier fichier liste de sucesseurs;
        Sommet elliste = g.getPremier();
        FileWriter succFile = new FileWriter(fileName1);
        BufferedWriter output = new BufferedWriter(succFile);
        
        while( elliste != null){
            output.write(elliste.getNom());
            output.append(";");
            Arete tmpA = elliste.getArete();
            while (tmpA != null){
                String tmpNom = tmpA.getDest();
                tmpNom = tmpNom.replaceAll("S", "");
                output.write(tmpNom);
                output.append(";");
                tmpA = tmpA.getSuivant();
            }
            output.newLine();
            elliste = elliste.getSuivant();
        }
        output.close();
        

        // On crée le 2eme fichier
        elliste = g.getPremier();
        FileWriter adjFile = new FileWriter(fileName2);
        BufferedWriter output2 = new BufferedWriter(adjFile);
        int nbSommets = g.compte_Sommets();
                System.out.print(nbSommets);
        
        while (elliste != null){
            output2.write(elliste.getNom());
            output2.append(";");
            output2.write(elliste.getType());
            output2.append(";");
            for (int i = 1; i <= nbSommets;i++){
                
                
                // Il faut exlure le sommet concerné 
                String tmp = "S" + i;
                if (g.checkArrete(elliste.getNom(),tmp)){
                    Arete tmpA = elliste.getArete();
                    while (tmpA != null){
                        if (tmpA.getDest().equals(tmp)){
                            String msg = (int)tmpA.getFiab() + ","+ (int) tmpA.getDist() + "," + (int) tmpA.getDur();
                            output2.write(msg);
                            output2.append(";");
                        }
                        tmpA = tmpA.getSuivant();
                    }
                }
                else{
                    output2.write("0");
                    output2.append(";");
                }
            }
            output2.newLine();
            elliste = elliste.getSuivant();
        }
        output2.close();
    }
    //</editor-fold>
}
