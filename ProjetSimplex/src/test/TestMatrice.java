package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Modele.Matrice;
import Utilitaire.Explorateur;
import exceptions.doublonContrainteException;

public class TestMatrice {

	private static Matrice matrice;
	private static List<List<Double>> mat;
	private static Double termeIndependant;
	private static Double[] contrainte;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		matrice = new Matrice(2,2,4.,14.);
		mat = (List<List<Double>>) Explorateur.getField(matrice, "matrice");
		contrainte = new Double[2];
		contrainte[0] = 2.;
		contrainte[1] = 7.;
		termeIndependant = 21.;
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSetLigne() {
		matrice.setLigne(0, new ArrayList<Double>());
		
		assertTrue(matrice.getLigne(0).equals(new ArrayList<Double>()));
	}

	@Test
	public void testSetValeur() {
		matrice.setValeur(0, 0, 42.);
		
		assertTrue(matrice.getValeur(0, 0) == 42);
	}

	@Test
	public void testAjouterContrainte() {
		try {
			matrice.ajouterContrainte(termeIndependant, contrainte);
		} catch (doublonContrainteException e) {
			e.printStackTrace();
		}
		
		assertTrue(matrice.getLigne(1).equals(mat.get(1)));
	}
	
	@Test(expected = doublonContrainteException.class)
	public void testAjouterContrainte2() throws doublonContrainteException {
		matrice.ajouterContrainte(termeIndependant, contrainte);
		
		matrice.ajouterContrainte(termeIndependant, contrainte);
		
		assertFalse(matrice.getLigne(1).equals(mat.get(1)));
	}
	
	@Test
	public void testGetMaxLignePos() {
		try {
			matrice.ajouterContrainte(termeIndependant, contrainte);
		} catch (doublonContrainteException e) {
			e.printStackTrace();
		}
		
		int maxLigne = matrice.getMaxLignePos(0);
		
		System.out.println(matrice+"\n"+maxLigne);
		
		assertTrue(mat.get(0).get(maxLigne) == 7);
	}

}
