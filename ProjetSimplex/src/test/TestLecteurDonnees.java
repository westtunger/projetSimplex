package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Modele.Matrice;
import Utilitaire.LecteurDonnees;

public class TestLecteurDonnees {
	
	private static Matrice matrice;
	private static List<String> listeVal;
	private static int nbvariables, nbcontraintes;
	private static Double[] fonctionObj;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		matrice = new Matrice(2,2,4.,14.);
		listeVal = new ArrayList<>();
		nbvariables = 2;
		nbcontraintes = 3;
		fonctionObj = new Double[nbcontraintes];
		fonctionObj[0] = 6.;
		fonctionObj[1] = 9.;
		listeVal.add("1 2 18");
		listeVal.add("2 1 20");
		listeVal.add("1 1 12");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLecConsole() {
		
		matrice = LecteurDonnees.lecConsole();
	}
	
	@Test
	public void testStockageValeurs() {
		matrice = LecteurDonnees.stockageValeurs(listeVal, matrice, fonctionObj, nbvariables, nbcontraintes);
	}

}
