package Utilitaire;

import java.io.IOException;
import java.util.Scanner;

import exceptions.CheminInvalide;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String nomFichier;
		Matrice m1 = new Matrice();
		Scanner lc = new Scanner(System.in);
		
		while(true)
		{

				try {
					System.out.print("Nom du fichier de sauvegarde : ");
					nomFichier = lc.next();
					Utilitaire.Fichier.setChemin(nomFichier);
					break;
				} catch (IOException | CheminInvalide e) {
					// TODO Auto-generated catch block
				
				}	
		}
		
		
		Utilitaire.Fichier.ecriture(m1.toString());
	}

}
