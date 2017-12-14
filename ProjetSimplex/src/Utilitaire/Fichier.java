package Utilitaire;
import java.io.*;

import exceptions.CheminInvalide;

public abstract class Fichier {

	private static String chemin;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;

		/**
		 * 
		 * @param chaine 
		 * @throws IOException
		 * @see PrintWriter
		 * @see FileOutputSteam
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
		 * @param c
		 * @throws CheminInvalide
		 */
		public static void setChemin (String c) throws CheminInvalide
		{
			// Regex : [A-Z]{1}:\/(.+\/)*(\w+.\w{1,})
			if(c.length()<=0){
				throw new CheminInvalide(c);
			}
			chemin=c;
		}
	}
