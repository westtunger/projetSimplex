package Utilitaire;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Modele.Simplexe;

import Utilitaire.LecteurDonnees;
import Modele.Matrice;




/**
 * Classe qui permet de faire le lien entre chaque partie du programme
 * Gère les choix faits par l'utilisateur
 * @author Julian
 *
 */
public abstract class Main {
	private static Scanner lc = new Scanner(System.in);

	 static String chaineContrainte = "", chaineFonctionObj = "Max Z = ", solution;

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
	public static void choixLectureDonnees()
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
					m=LecteurDonnees.lecConsole(); //On sauvegarde une matrice via la lecture console
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			else if(choixLecture == 2)
			{
				try {

					m=Fichier.lecFichier(); //On sauvegarde une matrice via la lecture fichier

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
	public static void choixSauvegarde()
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
				String chaine = chaineFonctionObj + chaineContrainte +"\n"+ solution;
				try {
					Fichier.ecriture(chaine);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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




}
