package Utilitaire;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Modele.Simplexe;

import Utilitaire.LectureDonnees;
import Modele.Matrice;
import exceptions.CheminInvalide;


/**
 * Classe qui permet de faire le lien entre chaque partie du programme
 * G�re les choix faits par l'utilisateur
 * @author Julian
 *
 */
public class Main {
	private static Scanner lc = new Scanner(System.in);

	private static String nomFichier, solution;

	public static Matrice m;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		choixLectureDonnees();
		choixSauvegarde();

	}

	/*
	 * Demande � l'utilisateur de choisir par quel moyen il d�sire encoder ses donn�es
	 */
	private static void choixLectureDonnees()
	{
		int choixLecture = 0;


		while(true) // Tant que le nom du fichier est incorrect, on boucle sur la demande de nom
		{
			System.out.println("Choix du type de lecture des valeurs");
			System.out.println("1. Valeurs encod�es par l'utilisateur");
			System.out.println("2. Valeurs encod�es dans un fichier textuel");
			System.out.print("Choix : ");
			
			try
			{
				choixLecture = lc.nextInt();
			}
			catch(InputMismatchException e)
			{
				lc.nextLine();
			}

			if(choixLecture == 1)
			{
				try {
					m=LectureDonnees.lecConsole();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		solution = Simplexe.resoudre(m);
		System.out.println(solution);
	}

	/*
	 *  Demande � l'utilisateur s'il d�sire sauvegarder le fichier
	 */
	private static void choixSauvegarde()
	{
		lc.nextLine();
		String choixSauve;
		while(true)
		{
			System.out.println("Voulez-vous sauvegarder vos r�sultats dans un fichier ? (oui/non)");
			System.out.print("Choix : ");
			choixSauve = lc.nextLine();
			if(choixSauve.equalsIgnoreCase("oui"))
			{
				ecritureFichier();
				break;
			}
			else if(choixSauve.equalsIgnoreCase("non"))
			{
				System.out.println("Au revoir");
				break;
			}
			else {
				System.out.println("Veuillez r�it�rer votre choix");
			}
		}
	}

	/**
	 *  G�re l'�criture dans le fichier choisi par l'utilisateur
	 */
	private static void ecritureFichier()
	{

		System.out.print("Nom du fichier � sauvegarder : ");
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
			Utilitaire.Fichier.ecriture(LectureDonnees.getChaineFonctionObj()+ "\n" + LectureDonnees.getChaineContrainte() + "\n" + solution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Ecriture de la matrice dans le fichier
	}

}
