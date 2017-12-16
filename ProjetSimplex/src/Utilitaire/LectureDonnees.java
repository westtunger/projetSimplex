package Utilitaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import Modele.Matrice;
import exceptions.doublonContrainteException;


/**
 * Classe servant à lire les données permettant de résoudre un problème de Simplexe
 * @author Julian
 *
 */
public class LectureDonnees {


	/**
	 * 
	 * @return une contrainte sous forme de String
	 */
	public static String getChaineContrainte() {
		return chaineContrainte;
	}

	/**
	 * 
	 * @return La fonction objective sous forme de String
	 */
	public static String getChaineFonctionObj() {
		return chaineFonctionObj;
	}


	private static int i,j,k, nbcontraintes, nbvariables;
	private static String [] tableauNombres = null;
	public static Double[] fonctionObj;
	private static String  valeurs, chaineContrainte = "", chaineFonctionObj = "Max Z = ", nomFichierLecture;
	public static Matrice matlec;
	private static List<String> listeValeurs;
	private static Scanner lc = new Scanner(System.in);

	/**
	 * 
	 * @return Matrice, renvoie la matrice qui permet de calculer le Simplexe
	 * @throws NumberFormatException Attrape une exception si ce qui est encodé n'est pas un nombre
	 */
	public static Matrice lecConsole() throws NumberFormatException
	{

		String[] splitValeurs;
		listeValeurs = new ArrayList<>();
		boolean err = true;
		while(err == true)
		{
			System.out.print("Valeur des variables de la fonction objective : ");
			valeurs = lc.nextLine();
			tableauNombres = valeurs.split(" "); 
			nbvariables = tableauNombres.length;
			fonctionObj = new Double[nbvariables];

			for (i=0;i<nbvariables;i++)
			{
				try {
					fonctionObj[i] = Double.parseDouble(tableauNombres[i]);
					if(i==nbvariables-1)
					{
						err = false;
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Veuillez encodez uniquement des nombres, séparés par des espaces");					
					break;
				}
			}
		}
		k = 0;
		Double[] testNbVar = new Double[nbvariables+1];
		System.out.println("Veuillez encoder les valeurs de vos contraintes (tapez -1 pour terminer)");
		while(true)
		{
			err = true;
			System.out.print("Valeur des variables de la contrainte n°" + (k+1) + " : ");
			valeurs = lc.nextLine();
			

			if(valeurs.equals("-1")) //Fin de la ecture des contraintes si on encode -1
			{
				nbcontraintes = listeValeurs.size();
				break;
			}
			else
			{
				splitValeurs = valeurs.split(" ");
				
				for (i=0;i<=nbvariables;i++)
				{
					try {
						testNbVar[i] = Double.parseDouble(splitValeurs[i]);
						if(i==nbvariables-1)
						{
							err = false;
						}
					}
					catch (NumberFormatException e) {
						err = true;
						System.out.println("Veuillez encodez uniquement des nombres, séparés par des espaces");					
						break;
					}
				}
				
				if(splitValeurs.length != nbvariables+1 && err == true)
				{
					System.out.println("Nombre de variables incorrect, veuillez recommencer");
				}
				else if (err == false){
					k++;
					listeValeurs.add(valeurs);
				}
			}
		}


		matlec = new Matrice(nbcontraintes, nbvariables, fonctionObj); 
		stockageValeurs();
		miseEnFormeContraintes();
		afficherDonnees();
		return matlec;
	}

	/**
	 * Stocke toutes les contraintes encodées par l'utilisateur
	 */
	public static void stockageValeurs()
	{
		Double[] ligne = new Double[nbvariables];
		double ti;
		for(i = 0; i < nbcontraintes; i++)
		{
			tableauNombres = listeValeurs.get(i).split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve

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
			try {
				matlec.ajouterContrainte(ti,ligne);
			} catch (doublonContrainteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gère la lecture des données via un fichier 
	 * @return Renvoie la matrice obtenue en lisant le fichier choisi
	 * @throws IOException Permet de fermer le Reader de fichier
	 */
	public static Matrice lecFichier() throws IOException
	{
		listeValeurs = new ArrayList<>();
		System.out.print("Nom du fichier à lire : ");
		nomFichierLecture = lc.nextLine();

		BufferedReader r = new BufferedReader(new FileReader(nomFichierLecture)); //Crée un Reader pour qui va permettre de lire le fichier ligne par ligne
		String[] tabval;

		valeurs = r.readLine();

		tabval = valeurs.split(" ");
		nbvariables = tabval.length;
		fonctionObj = new Double[nbvariables];


		tableauNombres = valeurs.split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve

		for (i=0;i<nbvariables;i++)
		{

			fonctionObj[i] = Double.parseDouble(tableauNombres[i]); //Ajoute chaque valeur convertie en double à la ligne
		}

		while(true)
		{
			valeurs = r.readLine();
			System.out.println(valeurs);
			if(valeurs == null)
			{
				nbcontraintes = listeValeurs.size();
				break;
			}
			else
			{
				listeValeurs.add(valeurs);
			}
		}

		matlec = new Matrice(nbcontraintes, nbvariables, fonctionObj);
		stockageValeurs();
		miseEnFormeContraintes();
		afficherDonnees();
		r.close();
		return matlec;
	}

	/**
	 * Mets en forme les chaines de caractères des containtes et de la fonction obj
	 * Pour qu'elles soient lisibles correctement par l'utilisateur
	 */
	private static void miseEnFormeContraintes()
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

	/**
	 * Affiche la fonction objective et les contraintes
	 */
	private static void afficherDonnees()
	{	
		System.out.println(chaineFonctionObj);
		System.out.println(chaineContrainte);
	}
}
