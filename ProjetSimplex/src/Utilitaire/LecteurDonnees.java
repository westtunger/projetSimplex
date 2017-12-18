package Utilitaire;




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
public abstract class LecteurDonnees {


	private static Scanner lc;

	/**
	 * 
	 * @return Matrice, renvoie la matrice qui permet de calculer le Simplexe
	 * @throws NumberFormatException Attrape une exception si ce qui est encodé n'est pas un nombre
	 */
	public static Matrice lecConsole() throws NumberFormatException
	{
		String  valeurs;
		int nbcontraintes, nbvariables = 0;
		int i,k;
		String [] tableauNombres = null;
		Double[] fonctionObj = null;

		Matrice matlec;
		List<String> listeValeurs =  new ArrayList<>();
		lc = new Scanner(System.in);

		String[] splitValeurs;
		boolean err = true;
		while(err == true)
		{
			System.out.print("Valeur des variables de la fonction objectif : ");
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
		matlec = stockageValeurs(listeValeurs, matlec, fonctionObj, nbvariables, nbcontraintes);
		System.out.println("\n--- RECAPITULATIF ---");
		System.out.println(Main.getChaineFonctionObj());
		System.out.println(Main.getChaineContrainte());

		return matlec;
	}

	/**
	 * Stocke toutes les contraintes encodées par l'utilisateur
	 */
	public static Matrice stockageValeurs(List<String> listeVal, Matrice m,Double[] fonctionObj, int nbvariables, int nbcontraintes)
	{	

		int i,j,k;
		String[] tableauNombres;
		Double[] ligne = new Double[nbvariables];
		double ti;
		for(i = 0; i < nbcontraintes; i++)
		{

			tableauNombres = listeVal.get(i).split(" "); // Sépare la chaine de caractère à chaque espace qui s'y trouve

			for (j=0;j<nbvariables;j++)
			{
				ligne[j] = (Double.parseDouble(tableauNombres[j])); //Ajoute chaque valeur convertie en double à la ligne
			}

			ti = Double.parseDouble(tableauNombres[nbvariables]);
			Main.ajouterAChaineContrainte("Contrainte n°"+(i+1)+" : ");
			for(k=0;k<nbvariables;k++)
			{
				Main.ajouterAChaineContrainte(ligne[k]+"*x"+(k+1)) ;

				if(k!=nbvariables-1)
				{
					Main.ajouterAChaineContrainte(" + ");
				}		
			}
			Main.ajouterAChaineContrainte(" <= "+ ti +"\n");



			try {
				m.ajouterContrainte(ti,ligne);
			} catch (doublonContrainteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for(k=1;k<=nbvariables;k++)
		{
			Main.ajouterAChaineContrainte("x"+k+" >= 0");
			if(k!=nbvariables)
			{
				Main.ajouterAChaineContrainte(" ; ");
			}	
		}
		for(k = 0;k<nbvariables;k++)
		{
			Main.ajouterAChaineContrainte(fonctionObj[k]+"*x"+(k+1));
			if(k!=nbvariables-1)
			{
				Main.ajouterAChaineContrainte(" + ");
			}

		}

		return m;
	}

}
