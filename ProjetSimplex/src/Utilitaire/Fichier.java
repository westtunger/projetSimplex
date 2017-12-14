package Utilitaire;
import java.io.*;

import javax.swing.SpringLayout.Constraints;

import exceptions.CheminInvalide;

/**
 * G�re l'�criture des donn�es dans un fichier
 * @author Julian
 *
 */
public abstract class Fichier {

	private static String chemin;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;

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
			if(!chemin.contains("."))
			{
				chemin += ".txt";
			}
		}
	}
