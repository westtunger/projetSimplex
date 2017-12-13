package Utilitaire;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Modele.Matrice;
import exceptions.CheminInvalide;
import exceptions.doublonContrainteException;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
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
