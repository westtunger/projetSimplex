package Utilitaire;

import java.io.IOException;
import java.util.Scanner;

import Modele.Simplexe;
import exceptions.CheminInvalide;

//import exceptions.CheminInvalide;


public class Main {
	private static Scanner lc = new Scanner(System.in);
	private static int choixLecture;
	private static String nomFichier, chaineContrainte = "", chaineFonctionObj = "Max Z = ", solution;

	public static Matrice m;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		choixLectureDonnees();
		afficherDonnees();
		ecritureFichier();
	}

	private static void choixLectureDonnees()
	{
		while(true)
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
	}
	
	private static void ecritureFichier()
	{
		lc.nextLine();
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

	private static void afficherDonnees()
	{	
		System.out.println(chaineFonctionObj);
		System.out.println(chaineContrainte);
		solution = Simplexe.resoudre(m);
		System.out.println(solution);
	}
}
