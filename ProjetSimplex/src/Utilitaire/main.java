package Utilitaire;

import java.io.IOException;
import java.util.Scanner;


import Modele.Simplexe;

import Modele.Matrice;
import exceptions.CheminInvalide;
import exceptions.doublonContrainteException;

//import exceptions.CheminInvalide;


public class main {
	private static Scanner lc = new Scanner(System.in);
	private static int choixLecture;
	private static String nomFichier, chaineContrainte = "", chaineFonctionObj = "Max Z = ", solution;

	public static Matrice m;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		choixLectureDonnees();
		
		
		
		ecritureFichier();
	}

	private static void choixLectureDonnees()
	{
		String nomFichier;	
		Matrice m1 = new Matrice(3,2,4.,4.);
		try {
			m1.ajouterContrainte(4, 1.,1.);
			m1.ajouterContrainte(4, 1.,1.);
			m1.ajouterContrainte(12, 3.,3.);
			
		} catch (doublonContrainteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/* Commentaire */
		Scanner lc = new Scanner(System.in);
		
		while(true) // Tant que le nom du fichier est incorrect, on boucle sur la demande de nom
		{
			System.out.println("Choix du type de lecture des valeurs");
			System.out.println("1. Valeurs encodées par l'utilisateur");
			System.out.println("2. Valeurs encodées dans un fichier textuel");
			System.out.print("Choix : ");
			choixLecture= lc.nextInt();

			if(choixLecture == 1)
			{
				m=LectureDonnees.lecConsole();
				break;
			}
			else if(choixLecture == 2)
			{
				try {
					m=LectureDonnees.lecFichier();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			else {
				System.out.println("Choix incorrect");
			}
		}
		System.out.println(Simplexe.resoudre(m));
	}
	
	private static void ecritureFichier()
	{

		System.out.print("Nom du fichier à sauvegarder : ");
		while(true) // Tant que le nom du fichier est incorrect, on boucle sur la demande de nom
		{
			nomFichier = lc.nextLine();
			try {
				Utilitaire.Fichier.setChemin(nomFichier);
			} catch (CheminInvalide e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			break;
		}
		try {
			Utilitaire.Fichier.ecriture(chaineFonctionObj + "\n" + chaineContrainte + "\n" + solution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Ecriture de la matrice dans le fichier
	}

}
