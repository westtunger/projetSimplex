package Utilitaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LectureDonnees {
	
	
	private static int i,j,k, nbcontraintes, nbvariables;
	private static String [] tableauNombres, tabFonctionObj;
	public static Double[] fonctionObj;
	private static String  valeurs, chaineContrainte = "", chaineFonctionObj = "Max Z = ", nomFichierLecture;
	public static Matrice matlec;
	private static Scanner lc = new Scanner(System.in);
	
	public static Matrice lecConsole()
	{

		System.out.print("Nombre de contraintes : ");
		nbcontraintes = lc.nextInt();

		lc.nextLine();
		 
		System.out.print("Fonction objectif : \n");
		System.out.print("Valeur des variables de la fonction objective : ");
		valeurs = lc.nextLine();
		
		tableauNombres = valeurs.split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve
		nbvariables = tableauNombres.length;
		System.out.println(nbvariables);
		fonctionObj = new Double[nbvariables];
		
		for (i=0;i<nbvariables;i++)
		{
			fonctionObj[i] = Double.parseDouble(tableauNombres[i]); //Ajoute chaque valeur convertie en double à la ligne
		}

		matlec = new Matrice(nbcontraintes, nbvariables, fonctionObj);
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
			matlec.ajouterContrainte(ti,ligne);
		}
		/*
		 * while(true)
		{
			System.out.print("Valeur des variables de la contrainte n°" + (i) + " : ");
			valeurs = lc.nextLine();

			if(valeurs.equals("ZZ"))
			{
				System.out.println("aaaaaaaa");
				nbcontraintes = i;
				break;
			}
			else{
				i++;
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
				matlec.ajouterContrainte(ti,ligne);
			}
		}
		 */
		miseEnFormeContraintes();
		return matlec;
	}

	public static Matrice lecFichier() throws IOException 
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

		matlec = new Matrice(nbcontraintes, nbvariables, fonctionObj);
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
			matlec.ajouterContrainte(ti,ligne);
		}
		r.close();
		miseEnFormeContraintes();
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
}
