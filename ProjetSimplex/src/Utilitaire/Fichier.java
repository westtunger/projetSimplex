package Utilitaire;
import java.io.*;

import exceptions.CheminInvalide;

public abstract class Fichier {

	private static String chemin;
	ObjectOutputStream out = null;
	
	/*public String lecture() throws IOException
	{
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(chemin)))
		{
			while(true)
			{
				Matrice m = (Matrice)in.readObject();
				System.out.println(m);
			}
		}
		catch(EOFException e)
		{}
		catch(ClassNotFoundException | IOException e)
		{
			System.out.println("Erreur durant la lecture du fichier");
		}
	}*/
	
	public static void ecriture(String chaine) throws IOException
	{
		//Ecriture du fichier via le chemin d'accès encodé par l'utilisateur
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(chemin));){	
			pw.write(chaine.toLowerCase());
		} catch(IOException e)
		{
			System.out.println("Erreur durant l'écriture dans un fichier");
		}
		
	}
	
	public static void setChemin (String c) throws IOException, CheminInvalide
	{
		// Regex : [A-Z]{1}:\/(.+\/)*(\w+.\w{1,})
		if(c==null){
			throw new CheminInvalide();
		}
		chemin=c;
	}
}
