package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Modele.Matrice;
import Modele.Simplexe;
import exceptions.doublonContrainteException;

public class TestSimplexe {
	private static Matrice matrice;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		matrice = new Matrice(2,2,4.,14.);
		try {
			matrice.ajouterContrainte(21.,2.,7.);
		} catch (doublonContrainteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
		try {
			matrice.ajouterContrainte(21.,7.,2.);
		} catch (doublonContrainteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	public void testResoudre() 
	{
		String res = Simplexe.resoudre(matrice);
		
		String resAttendu = "Etat initial : \n\nMatrice : \n  2,00   7,00   1,00   0,00  21,00 \n  7,00   2,00   0,00   1,00  21,00 \n  4,00  14,00   0,00   0,00   0,00 \n\nSolution de base : \n(0,0, 21.0 , 21.0 ) Z = 0.0\n-----------------------------------------------------------------------------------\nEtape n°1 : \n\nLigne du pivot : 0\nColonne du pivot : 1\n\nMatrice après avoir rendu le pivot unitaire : \n  0,29   1,00   0,14   0,00   3,00 \n  7,00   2,00   0,00   1,00  21,00 \n  4,00  14,00   0,00   0,00   0,00 \n\nMatrice après avoir fait rentrer le pivot dans la base : \n  0,29   1,00   0,14   0,00   3,00 \n  6,43   0,00  -0,29   1,00  15,00 \n  0,00   0,00  -2,00   0,00 -42,00 \n\nSolution de base : \n(0, 3.0 ,0, 15.0 ) Z = 42.0\n-----------------------------------------------------------------------------------\nIl y a plusieurs couples X1, X2 possibles pour la valeur optimale Z=42.0 répondants aux contraintes du problème. L'un d'eux est :\nx1* = 0\nx2* = 3.0\nZ* = 42.0\n";
		
		assertTrue(res.equals(resAttendu));
	}

}
