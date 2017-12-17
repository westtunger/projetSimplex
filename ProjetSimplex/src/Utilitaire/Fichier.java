package Utilitaire;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Modele.Matrice;
import exceptions.CheminInvalide;

import Utilitaire.LecteurDonnees;

/**
 * G�re l'�criture des donn�es dans un fichier
 * @author Julian
 *
 */
public abstract class Fichier {

	private static String chemin;


		/**
		 * 
		 * @param chaine = chaine qui sera stock�e dans le fichier de sauvegarde
		 * @throws IOException Exception v�rifiant qu'il n'y a pas d'erreurs durant l'�crituee
		 * @see PrintWriter Ecrit dans le fichier
		 * @see FileOutputSteam Permet d'�crire dans le fichier
		 */
		public static void ecriture(String chaine) throws IOException
		{
			//Ecriture du fichier via le chemin d'acc�s encod� par l'utilisateur
			try (PrintWriter pw = new PrintWriter(new FileOutputStream(chemin))){	//On v�rifie que le fichier peut �tre cr�er
				pw.write(chaine); 
			} catch(IOException e)
			{
				System.out.println("Erreur durant l'�criture dans un fichier");
			}
		}

		/**
		 * 
		 * @param c = chemin d'acc�s voulu pour le fichier
		 * @throws CheminInvalide V�rifie la validit� du chemin
		 */
		public static void setChemin (String c) throws CheminInvalide
		{
			// Regex : [A-Z]{1}:\/(.+\/)*(\w+.\w{1,})
			if(c.length()<=0 || c.contains(" ")){
				throw new CheminInvalide(c);
			}
			
			chemin=c;
			if(!chemin.contains(".")) //Si pas d'extension de fichier trouv�e, on en ajoute une
			{
				chemin += ".txt";
			}
		}
		
		/**
		 * G�re la lecture des donn�es via un fichier 
		 * @return Renvoie la matrice obtenue en lisant le fichier choisi
		 * @throws IOException Permet de fermer le Reader de fichier
		 */
		public static Matrice lecFichier() throws IOException
		{
			
			String [] tableauNombres = null;
			Double[] fonctionObj = null;
			String nomFichierLecture, valeurs;
			int i, nbvariables, nbcontraintes;
			Scanner lc = new Scanner(System.in);
			List<String>listeValeurs = new ArrayList<>();
			System.out.print("Nom du fichier � lire : ");
			nomFichierLecture = lc.nextLine();

			BufferedReader r = new BufferedReader(new FileReader(nomFichierLecture)); //Cr�e un Reader pour qui va permettre de lire le fichier ligne par ligne
			String[] tabval;

			valeurs = r.readLine();

			tabval = valeurs.split(" ");
			nbvariables = tabval.length;
			fonctionObj = new Double[nbvariables];


			tableauNombres = valeurs.split(" "); // S�pare la chaine de caract�re � chaque espace qui s'y trouve

			for (i=0;i<nbvariables;i++)
			{

				fonctionObj[i] = Double.parseDouble(tableauNombres[i]); //Ajoute chaque valeur convertie en double � la ligne
			}
			while(true)
			{
				valeurs = r.readLine();
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

			Matrice matlec = new Matrice(nbcontraintes, nbvariables, fonctionObj);
			
			matlec = LecteurDonnees.stockageValeurs(listeValeurs, matlec, fonctionObj, nbvariables, nbcontraintes);
			System.out.println("\n--- RECAPITULATIF ---");
			System.out.println(Main.chaineFonctionObj);
			System.out.println(Main.chaineContrainte);
			r.close();
			return matlec;
		}
	}
