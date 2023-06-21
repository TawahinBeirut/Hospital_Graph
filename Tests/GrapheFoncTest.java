/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sae.Controller.Generation;
import sae.Controller.GrapheFonc;
import sae.Model.Graphe;
import sae.Model.Graphe.Arete;
import sae.Model.Graphe.Sommet;

/**
 *
 * @author gheri
 */
public class GrapheFoncTest {
    
    /**
     * Attributs Necessaires aux tests
     */
    private static Graphe g = new Graphe();
    private static String path1 = "liste-adjacenceSAE";
    private static String path2= "liste-succeseursSAE";
    
    private static final Generation gen = new Generation(path1,path2,g,null);
    private static final GrapheFonc gf = new GrapheFonc(g);
    
    public GrapheFoncTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testLister_voisins(){
        Sommet s1 = g.getSommet("S1");
        List<Sommet> toTest = gf.lister_voisins(s1);
        
        List<Sommet> results = new LinkedList<>();
        results.add(g.getSommet("S8"));
        results.add(g.getSommet("S10"));
        results.add(g.getSommet("S4"));
        
        assertEquals(results,toTest);
    }

    @Test
    public void testLister_voisins_ParType(){
        Sommet s1 = g.getSommet("S1");
        List<Sommet> toTest = gf.lister_voisins_par_type(s1,"M");
        
        List<Sommet> results = new LinkedList<>();
        results.add(g.getSommet("S8"));
        results.add(g.getSommet("S4"));
        
        assertEquals(results,toTest);
    }
    
    @Test
    public void testPlusCourtChemin(){
        List<Sommet> toTest = gf.plus_court_chemin(g.getSommet("S5"),g.getSommet("S9"));
        
        
        List<Sommet> results = new LinkedList<>();
        results.add(g.getSommet("S5"));
        results.add(g.getSommet("S8"));
        results.add(g.getSommet("S4"));
        results.add(g.getSommet("S9"));
        
        assertEquals(results,toTest);
    }
     @Test
    public void testDistancePlusCourtChemin(){
        Double toTest = gf.distance_plus_court_chemin(g.getSommet("S5"),g.getSommet("S9"));
        
        
        List<Sommet> results = new LinkedList<>();
        results.add(g.getSommet("S5"));
        results.add(g.getSommet("S8"));
        results.add(g.getSommet("S4"));
        results.add(g.getSommet("S9"));
        
        double result = 0;
        
        for (int i =1; i<results.size();i++){
            Arete tmpA = g.getArete2(results.get(i).getNom(),results.get(i-1).getNom());
            result += tmpA.getDist(); 
        }
        
        assertTrue(toTest == result);
    }
    
    @Test
    public void comparaison_2_noeuds(){
        Sommet toTest = gf.comparaison_2_noeuds(g.getSommet("S4"),g.getSommet("S3"),"M");
        
        Sommet result = g.getSommet("S4");
        
        assertEquals(result,toTest);
    }
    
    @Test
    public void test_Modifier_Arete(){
        Sommet S1 = g.getSommet("S1");
        Sommet S2 = g.getSommet("S8");
        Arete  toTest = g.getArete2("S1", "S8");
        gf.modifierArrete(S1, S2, 45, 12, 58);
        
        assertTrue(toTest.getDist() == (double)45);
        
    }
    
    @Test
    public void test_Supprimer_Arete(){
        Sommet S1 = g.getSommet("S1");
        Sommet S2 = g.getSommet("S8");
        
        gf.supprimerArrete(S1, S2);
        assertTrue(g.checkArrete("S1", "S8"));
        
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
