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
 * Gère les choix faits par l'utilisateur
 * @author Julian
 *
 */
public class Main {
	private static Scanner lc = new Scanner(System.in);

	private static String nomFichier, solution;

	public static Matrice m;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Bienvenue dans ce programme permettant la résolution de problèmes liés au Simplexe");
		choixLectureDonnees();
		choixSauvegarde();

	}

	/*
	 * Demande à l'utilisateur de choisir par quel moyen il désire encoder ses données
	 */
	private static void choixLectureDonnees()
	{
		int choixLecture = 0;


		while(true) // Tant que le nom du fichier est incorrect, on boucle sur la demande de nom
		{
			System.out.println("Choix du type de lecture des valeurs");
			System.out.println("1. Valeurs encodées par l'utilisateur");
			System.out.println("2. Valeurs encodées dans un fichier textuel");
			System.out.print("Choix : ");
			
			try
			{
				choixLecture = lc.nextInt();
			}
			catch(InputMismatchException e)
			{
				lc.nextLine(); //Si une faute de frappe est détectée, on vide le Scanner afin de pouvoir relire
			}

			if(choixLecture == 1)
			{
				try {
					m=LectureDonnees.lecConsole(); //On sauvegarde une matrice via la lecture console
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			else if(choixLecture == 2)
			{
				try {
					m=LectureDonnees.lecFichier(); //On sauvegarde une matrice via la lecture fichier
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
		solution = Simplexe.resoudre(m); //On trouve la solution en envoyant la matrice
		System.out.println(solution);
	}

	/*
	 *  Demande à l'utilisateur s'il désire sauvegarder le fichier
	 */
	private static void choixSauvegarde()
	{
		lc.nextLine(); //On vide le Scanner pour pouvoir encoder le choix
		String choixSauve;
		while(true)
		{
			System.out.println("Voulez-vous sauvegarder vos résultats dans un fichier ? (oui/non)");
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
				System.out.println("Veuillez réitérer votre choix");
			}
		}
	}

	/**
	 *  Gère l'écriture dans le fichier choisi par l'utilisateur
	 */
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
			// On essaie de faire l'écriture de la fonction objective + les contraintes + la solution dans le fichier
			Utilitaire.Fichier.ecriture(LectureDonnees.getChaineFonctionObj()+ "\n" + LectureDonnees.getChaineContrainte() + "\n" + solution);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Ecriture de la matrice dans le fichier
	}

}
