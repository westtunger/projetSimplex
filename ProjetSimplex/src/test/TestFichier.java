package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Modele.Matrice;
import Utilitaire.Fichier;
import exceptions.CheminInvalideException;

public class TestFichier {
	
	private static String chaine;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		chaine =  "Matrice : \n 1,00   2,00   1,00   0,00   0,00  18,00 ";
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEcriture() {
		try {
			Fichier.ecriture(chaine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetChemin() {
		String nomFichier = "test.txt";
		try {
			Fichier.setChemin(nomFichier);
		} catch (CheminInvalideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLecFichier() {
		Matrice m = new Matrice(2,2,4.,14.);
		try {
			m = Fichier.lecFichier();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
