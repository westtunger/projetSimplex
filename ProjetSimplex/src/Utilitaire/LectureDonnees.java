package Utilitaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Modele.Matrice;
import Modele.Simplexe;
import exceptions.doublonContrainteException;

public class LectureDonnees {


	private static int i,j,k, nbcontraintes, nbvariables;
	private static String [] tableauNombres;
	public static Double[] fonctionObj;
	private static String  valeurs, chaineContrainte = "", chaineFonctionObj = "Max Z = ", nomFichierLecture;
	public static Matrice matlec;
	private static List<String> listeValeurs;
	private static Scanner lc = new Scanner(System.in);

	public static Matrice lecConsole()
	{
		listeValeurs = new ArrayList<>();
		System.out.print("Fonction objectif : \n");
		System.out.print("Valeur des variables de la fonction objective : ");
		valeurs = lc.nextLine();

		tableauNombres = valeurs.split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve
		nbvariables = tableauNombres.length;
		fonctionObj = new Double[nbvariables];

		for (i=0;i<nbvariables;i++)
		{
			fonctionObj[i] = Double.parseDouble(tableauNombres[i]); //Ajoute chaque valeur convertie en double à la ligne
		}
		i=0;

		while(true)
		{
			System.out.print("Valeur des variables de la contrainte n°" + (i+1) + " : ");
			valeurs = lc.nextLine();
			System.out.println(valeurs);
			if(valeurs.equals("-1"))
			{

				nbcontraintes = listeValeurs.size();
				break;
			}
			else
			{
				listeValeurs.add(valeurs);
				System.out.println("aaaaaa");
			}
		}


		matlec = new Matrice(nbcontraintes, nbvariables, fonctionObj);
		stockageValeurs();
		miseEnFormeContraintes();
		afficherDonnees();
		return matlec;
	}

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
	
	public static Matrice lecFichier() throws IOException 
	{
		listeValeurs = new ArrayList<>();
		System.out.print("Nom du fichier à lire : ");
		nomFichierLecture = lc.nextLine();

		BufferedReader r = new BufferedReader(new FileReader(nomFichierLecture));
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
				System.out.println("aaaaaa");
			}
		}
		
		matlec = new Matrice(nbcontraintes, nbvariables, fonctionObj);
		stockageValeurs();
		miseEnFormeContraintes();
		afficherDonnees();
		r.close();
		return matlec;
	}

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

	private static void afficherDonnees()
	{	
		System.out.println(chaineFonctionObj);
		System.out.println(chaineContrainte);

	}
}


/*
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
*/
