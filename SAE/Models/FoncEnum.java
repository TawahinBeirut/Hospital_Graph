/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package sae.Model;

/**
 * Classe énumeratrice des fonctions choisissables par l'utilisateur
 * @author gheri
 */
public enum FoncEnum {
    PLUSCOURT{
        @Override
        public String toString(){
            return "Afficher le chemin le plus court";
        }
    },
    PLUSFIABLE{
        @Override
        public String toString(){
            return "Afficher le chemin le plus fiable";
        }
    },
    VOISINS1DISTANCE{
        @Override
        public String toString(){
            return "Afficher les voisins à 1 distance";
        }
    },
    VOISINS2DISTANCE{
        @Override
        public String toString(){
            return "Afficher les voisins à 2 distance";
        }
    },
    CHECK2DISTANCE{
        @Override
        public String toString(){
            return "Verifier si 2 sommets sont liés";
        }
    },
    COMPARAISON2NOEUDS{
        @Override
        public String toString(){
            return "Comparer les 2 noeuds selectionnes";
        }
    },
    COMPLEXITEALGO{
        @Override
        public String toString(){
            return "Afficher la complexité de l'algorithme";
        }
    },
    LISTERARRETES{
        @Override
        public String toString(){
            return "Lister et modifier les arretes du graphe";
        }
    },
    LISTERSOMMETS{
        @Override
        public String toString(){
            return "Lister et modifier les sommets du graphe";
        }
    },
    MODIFIERSOMMET{
        @Override
        public String toString(){
            return "Modifier en particulier un sommet du graphe";
        }
    },
    MODIFIERARRETE{
        @Override
        public String toString(){
            return "Modifier en particulier une arrete";
        }
    },
    LISTERPARCATEGORIE{
        @Override
        public String toString(){
            return "Lister les Sommets d'un type donné";
        }
    },
    LISTERVOSINSTYPE{
        @Override
        public String toString(){
            return "Lister les voisins d'un type donné des 2 sommets";
        }
    },
}
