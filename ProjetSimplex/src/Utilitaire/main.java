package Utilitaire;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.CheminInvalide;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String nomFichier;
		
		
		Matrice m1 = new Matrice(null);
		Scanner lc = new Scanner(System.in);
		
		
		/* */
		System.out.print("Nom du fichier à sauvegarder : ");
		while(true) // Tant que le nom du fichier est incorrect, on boucle sur la demande de nom
		{
				try {
					nomFichier = lc.nextLine();
					Utilitaire.Fichier.setChemin(nomFichier); 
					break;
				} catch (CheminInvalide e) {
					System.out.print("Veuillez encoder un nom de fichier valide : "); //Si le nom est invalide, on demande de ré-encoder
				}
		}
		Utilitaire.Fichier.ecriture(m1.toString()); //Ecriture de la matrice dans le fichier
	}

}
