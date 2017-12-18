package Utilitaire;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Modele.Simplexe;

import Utilitaire.LecteurDonnees;
import Modele.Matrice;




/**
 * Classe qui permet de faire le lien entre chaque partie du programme
 * G�re les choix faits par l'utilisateur
 * @author Julian
 *
 */
public abstract class Main {
	private static Scanner lc = new Scanner(System.in);

	 static String chaineContrainte = "", chaineFonctionObj = "Max Z = ", solution;

	public static Matrice m;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Bienvenue dans ce programme permettant la r�solution de probl�mes li�s au Simplexe");
		choixLectureDonnees();
		
		choixSauvegarde();
		
	}

	/*
	 * Demande � l'utilisateur de choisir par quel moyen il d�sire encoder ses donn�es
	 */
	public static void choixLectureDonnees()
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
				lc.nextLine(); //Si une faute de frappe est d�tect�e, on vide le Scanner afin de pouvoir relire
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
	 *  Demande � l'utilisateur s'il d�sire sauvegarder le fichier
	 */
	public static void choixSauvegarde()
	{
		lc.nextLine(); //On vide le Scanner pour pouvoir encoder le choix
		String choixSauve;
		while(true)
		{
			System.out.println("Voulez-vous sauvegarder vos r�sultats dans un fichier ? (oui/non)");
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
				System.out.println("Veuillez r�it�rer votre choix");
			}
		}
	}




}
