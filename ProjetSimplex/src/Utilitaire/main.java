package Utilitaire;



import java.io.IOException;
import java.util.Scanner;

import Modele.Simplexe;
import exceptions.CheminInvalide;

//import exceptions.CheminInvalide;


public class Main {
	private static Scanner lc = new Scanner(System.in);
	private static int nbcontraintes, nbvariables,i,j,k;
	private static String nomFichier, valeurs, chaineContrainte = "", chaineFonctionObj = "Max Z = ", solution;
	private static String [] tableauNombres;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub


		/* ENCODAGE DES DONNEES*/
		System.out.print("Nombre de contraintes : ");
		nbcontraintes = lc.nextInt();

		System.out.print("Nombre de variables : ");
		nbvariables = lc.nextInt();

		lc.nextLine();
		Double[] fonctionObj = new Double[nbvariables];

		System.out.print("Fonction objectif : \n");

		System.out.print("Valeur des variables de la fonction objective : ");
		valeurs = lc.nextLine();

		tableauNombres = valeurs.split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve

		for (i=0;i<nbvariables;i++)
		{
			fonctionObj[i] = Double.parseDouble(tableauNombres[i]); //Ajoute chaque valeur convertie en double à la ligne

		}
		/* */
		Matrice m = new Matrice(nbcontraintes, nbvariables, fonctionObj);
		
		
		Double[] ligne = new Double[nbvariables];

		double ti;
		for(i = 0; i< nbcontraintes ; i++)
		{

			System.out.print("Valeur des variables de la contrainte n°" + (i+1) + " : ");
			valeurs = lc.nextLine();

			tableauNombres = valeurs.split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve

			for (j=0;j<nbvariables;j++)
			{
				ligne[j] = (Double.parseDouble(tableauNombres[j])); //Ajoute chaque valeur convertie en double à la ligne
			}

			ti = Double.parseDouble(tableauNombres[nbvariables]);
			chaineContrainte += "Contrainte n°"+(i+1)+" : ";
			for(k=0;k<nbvariables;k++)
			{
				chaineContrainte += ligne[k]+"*x"+(k+1);

				if(k!=nbvariables-1)
				{
					chaineContrainte += " + ";
				}		
			}
			chaineContrainte += " <= "+ ti +"\n";
			m.ajouterContrainte(ti,ligne);

		}
		
		for(i=1;i<=nbvariables;i++)
		{
			chaineContrainte+="x"+i+" >= 0";
			if(i!=nbvariables)
			{
				chaineContrainte += " ; ";
			}	
		}
		
		
		for(i=0;i<nbvariables;i++)
		{
			chaineFonctionObj += fonctionObj[i]+"*x"+(i+1);
			if(i!=nbvariables-1)
			{
				chaineFonctionObj+=" + ";
			}
		}

		System.out.println(chaineFonctionObj);
		System.out.println(chaineContrainte);
		solution = Simplexe.resoudre(m);
		System.out.println(solution);
		/* PARTIE SAUVEGARDE FICHIER */
		ecritureFichier();
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
