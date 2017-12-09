package Utilitaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Modele.Simplexe;
import exceptions.CheminInvalide;

//import exceptions.CheminInvalide;


public class Main {
	private static Scanner lc = new Scanner(System.in);
	private static int nbcontraintes, nbvariables,i,j,k,choixLecture;
	private static String nomFichier, valeurs, chaineContrainte = "", chaineFonctionObj = "Max Z = ", solution, nomFichierLecture;
	private static String [] tableauNombres;
	private static Double[] fonctionObj;
	private static Matrice m;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		while(true)
		{
			System.out.println("Choix du type de lecture des valeurs");
			System.out.println("1. Valeurs encodées par l'utilisateur");
			System.out.println("2. Valeurs encodées dans un fichier textuel");
			System.out.print("Choix : ");
			choixLecture= lc.nextInt();

			if(choixLecture == 1)
			{
				lectureDonnees();
				break;
			}
			else if(choixLecture == 2)
			{
				lectureDonneesFichier();
				break;
			}
			else {
				System.out.println("Choix incorrect");
			}
		}
		afficherDonnees();
		ecritureFichier();
	}

	private static void lectureDonnees()
	{
		System.out.print("Nombre de contraintes : ");
		nbcontraintes = lc.nextInt();
		System.out.print("Nombre de variables : ");
		nbvariables = lc.nextInt();
		lc.nextLine();
		fonctionObj = new Double[nbvariables];
		System.out.print("Fonction objectif : \n");
		System.out.print("Valeur des variables de la fonction objective : ");
		valeurs = lc.nextLine();

		tableauNombres = valeurs.split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve

		for (i=0;i<nbvariables;i++)
		{
			fonctionObj[i] = Double.parseDouble(tableauNombres[i]); //Ajoute chaque valeur convertie en double à la ligne
		}

		m = new Matrice(nbcontraintes, nbvariables, fonctionObj);
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
		miseEnFormeMatrice();
	}

	private static void lectureDonneesFichier() throws IOException 
	{
		lc.nextLine();
		System.out.print("Nom du fichier à lire : ");
		nomFichierLecture = lc.nextLine();
		
		BufferedReader r = new BufferedReader(new FileReader(nomFichierLecture));
		String[] tabval;

		valeurs = r.readLine();

		tabval = valeurs.split(" ");
		nbcontraintes = Integer.valueOf(tabval[0]);
		nbvariables = Integer.valueOf(tabval[1]);
		
		fonctionObj = new Double[nbvariables];
		System.out.print("Fonction objectif : \n");
		System.out.print("Valeur des variables de la fonction objective : ");
		valeurs = r.readLine();
		
		tableauNombres = valeurs.split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve

		for (i=0;i<nbvariables;i++)
		{
			
			fonctionObj[i] = Double.parseDouble(tableauNombres[i]); //Ajoute chaque valeur convertie en double à la ligne
		}

		m = new Matrice(nbcontraintes, nbvariables, fonctionObj);
		Double[] ligne = new Double[nbvariables];
		double ti;
		for(i = 0; i< nbcontraintes ; i++)
		{

			
			valeurs = r.readLine();

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
		r.close();
		miseEnFormeMatrice();

	}
	
	private static void miseEnFormeMatrice()
	{
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

	private static void afficherDonnees()
	{	
		System.out.println(chaineFonctionObj);
		System.out.println(chaineContrainte);
		solution = Simplexe.resoudre(m);
		System.out.println(solution);
	}
}
