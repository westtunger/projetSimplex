package Utilitaire;
import java.io.*;

import exceptions.CheminInvalide;

public abstract class Fichier {

	private static String chemin;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;

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

		public static void setChemin (String c) throws CheminInvalide
		{
			// Regex : [A-Z]{1}:\/(.+\/)*(\w+.\w{1,})
			if(c.length()<=0){
				throw new CheminInvalide(c);
			}
			chemin=c;
		}
	}
