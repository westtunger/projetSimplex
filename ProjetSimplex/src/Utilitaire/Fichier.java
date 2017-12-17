package Utilitaire;
import java.io.*;

import javax.swing.SpringLayout.Constraints;

import exceptions.CheminInvalideException;

/**
 * Gère l'écriture des données dans un fichier
 * @author Julian
 *
 */
public abstract class Fichier {

	private static String chemin;


		/**
		 * 
		 * @param chaine = chaine qui sera stockée dans le fichier de sauvegarde
		 * @throws IOException Exception vérifiant qu'il n'y a pas d'erreurs durant l'écrituee
		 * @see PrintWriter Ecrit dans le fichier
		 */
		public static void ecriture(String chaine) throws IOException
		{
			//Ecriture du fichier via le chemin d'accès encodé par l'utilisateur
			try (PrintWriter pw = new PrintWriter(new FileOutputStream(chemin))){	//On vérifie que le fichier peut être créer
				pw.write(chaine); 
			} catch(IOException e)
			{
				System.out.println("Erreur durant l'écriture dans un fichier");
			}
		}

		/**
		 * 
		 * @param c = chemin d'accès voulu pour le fichier
		 * @throws CheminInvalideException Vérifie la validité du chemin
		 */
		public static void setChemin (String c) throws CheminInvalideException
		{
			// Regex : [A-Z]{1}:\/(.+\/)*(\w+.\w{1,})
			if(c.length()<=0 || c.contains(" ")){
				throw new CheminInvalideException(c);
			}
			
			chemin=c;
			if(!chemin.contains(".")) //Si pas d'extension de fichier trouvée, on en ajoute une
			{
				chemin += ".txt";
			}
		}
	}
