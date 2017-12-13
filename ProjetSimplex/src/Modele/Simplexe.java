package Modele;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe visant à frounir tout les outils pour résoudre un problème du simplexe.
 * @author Nicolas Viseur
 */
public abstract class Simplexe 
{
	/**
	 * Fonction centrale appelée lorsque l'on veut résoudre une matrice avec la méthode du simplexe.
	 * @param matrice La matrice du problème à résoudre.
	 * @return Une chaine de caractère contenant tout le développement de la résolution ainsi que la réponse finale.
	 * @see Matrice
	 */
	public static String resoudre(Matrice matrice)
	{
		int lignePivot, colonnePivot, i=1;
		String resultat = "";
		boolean solutionRestrainte = false;
		
		resultat += "Etat initial : \n\n";
		resultat += "Matrice : \n"+matrice.toString()+"\n";
		resultat += ecrireSb(matrice);
		resultat += "-----------------------------------------------------------------------------------\n";
		
		while(objectifEstPositive(matrice) && i <5)
		{
			resultat += "Etape n°"+i+" : \n\n";
			colonnePivot = trouverColonnePivot(matrice);
			solutionRestrainte = isSolutionRestreinte(matrice,colonnePivot);
			lignePivot = trouverLignePivot(matrice,colonnePivot);
			matrice = rendrePivotUnitaire(matrice, lignePivot, colonnePivot);			
			if(solutionRestrainte)
			{
				resultat += matrice;
				break;
			}
			else
			{
				resultat += "Ligne du pivot : "+lignePivot+"\n";
				resultat += "Colonne du pivot : "+colonnePivot+"\n\n";
			}
			resultat += "Matrice après avoir rendu le pivot unitaire : \n"+matrice.toString()+"\n";
			matrice = faireRentrerPivotDansLaBase(matrice, lignePivot, colonnePivot);
			resultat += "Matrice après avoir fait rentrer le pivot dans la base : \n"+matrice.toString()+"\n";
			resultat += ecrireSb(matrice);
			resultat += "-----------------------------------------------------------------------------------\n";
			i++;
		}
		
		resultat += sortirResultat(matrice,solutionRestrainte);
		
		return resultat;
	}
	
	/**
	 * Regarde si la solution est restreinte ou non.
	 * @param matrice La matrice du problème à résoudre.
	 * @param colonne La colonne à vérifier.
	 * @return Si oui ou non la solution est restreinte.
	 * @see Matrice
	 */
	private static boolean isSolutionRestreinte(Matrice matrice, int colonne)
	{
		boolean solutionRestrainte = false;
		int n = 0;
		
		for(int j = 0;j<matrice.getNbLignes()-1;j++)
		{
			double valeur = matrice.getValeur(j, colonne);
			
			if(valeur <= 0)
			{
				n++;
				continue;
			}
		}
		
		//Toute la colonne de la variable entrant en base est négative ou nulle
		//Il s'agit donc d'un cas de solution illimitée (restrainte)
		if(n == matrice.getNbLignes()-1)
		{
			solutionRestrainte = true;
		}
		
		return solutionRestrainte;
	}
	
	/**
	 * Ecrit la solution de base dans un chaine de caractères et la renvoie.
	 * @param matrice La matrice du problème à résoudre.
	 * @return La solution de base sous forme de chaine de caractères.
	 * @see Matrice
	 */
	private static String ecrireSb(Matrice matrice) 
	{
		String sb = "Solution de base : \n(";
		
		for(int i=0; i < matrice.getTailleLigne()-1; i++)
		{
			int n = -1;
			
			for(int j = 0;j<matrice.getNbLignes();j++)
			{
				double valeur = matrice.getValeur(j, i);
				
				if(valeur == 0)
				{
					continue;
				}
				else if(valeur == 1 && n == -1)
				{
					n = j;
					continue;
				}
				else
				{
					n = -2;
					break;
				}	
			}
			
			if(n >= 0)
			{
				sb += " "+matrice.getValeur(n, matrice.getTailleLigne()-1)+" ";
			}
			else
			{
				sb += 0;
			}
			
			if(i < matrice.getTailleLigne()-2)
			{
				sb += ",";
			}
		}
		
		sb+= ") ";
		
		sb += "Z = "+Math.abs(matrice.getValeur(matrice.getNbLignes()-1, matrice.getTailleLigne()-1))+"\n";
		
		return sb;
	}
	
	/**
	 * Ecrit le résultat du simplexe sous forme de chaine de caractères et le renvoi.
	 * @param matrice La matrice du problème à résoudre.
	 * @param solutionRestrainte Information si la solution est réstreinte ou non.
	 * @return Le résultat du problème sous forme de chaine de caractères.
	 * @see Matrice
	 */
	private static String sortirResultat(Matrice matrice, boolean solutionRestrainte) 
	{
		String resultat = "";
		boolean infinite = false;
		
		//Determination d'un cas d'infinité de solution
		for(int i=matrice.getNbVariable(); i < matrice.getTailleLigne()-1; i++)
		{
			int n = -1;
			
			for(int j = 0;j<matrice.getNbLignes();j++)
			{
				double valeur = matrice.getValeur(j, i);
				
				if(valeur == 0)
				{
					continue;
				}
				else if(valeur == 1 && n == -1)
				{
					n = j;
					continue;
				}
				else
				{
					n = -2;
					break;
				}	
			}
			
			if(n < 0)
			{
				//Si l'une des variables de décision non base a une valeur de 0 dans la ligne Z
				if(matrice.getValeur(matrice.getNbLignes()-1, i) == 0)
				{
					infinite = true;
					break;
				}
			}
		}
		
		if(infinite)
		{
			resultat+="Il y a une infinité de couples ";
			
			for(int i = 1; i <= matrice.getNbVariable(); i++)
			{
				resultat+="X"+i;
				
				if(i == matrice.getNbVariable())
					resultat += " ";
				else
					resultat += ", ";
			}
			
			resultat+="pour la valeur optimale Z="+Math.abs(matrice.getValeur(matrice.getNbLignes()-1, matrice.getTailleLigne()-1))+" répondants aux contraintes du problème. L'un d'eux est :\n";
		}
		
		//Solution restreinte
		if(solutionRestrainte)
		{
			resultat+="Le problème est restraint, càd qu'il n'y a aucune valeurs optimale spécifique pour la fonction objectif.\n Tant que les variabiables augmentes, Z augmente aussi.";
			return resultat;
		}
		
		for(int i=0; i < matrice.getNbVariable(); i++)
		{
			int n = -1;
			
			for(int j = 0;j<matrice.getNbLignes();j++)
			{
				double valeur = matrice.getValeur(j, i);
				
				if(valeur == 0)
				{
					continue;
				}
				else if(valeur == 1 && n == -1)
				{
					n = j;
					continue;
				}
				else
				{
					n = -2;
					break;
				}	
			}
			
			if(n >= 0)
			{
				resultat += "x"+(i+1)+"* = "+matrice.getValeur(n, matrice.getTailleLigne()-1)+"\n";
			}
			else
			{
				resultat += "x"+(i+1)+"* = "+0+"\n";
			}
		}
		
		resultat += "Z* = "+Math.abs(matrice.getValeur(matrice.getNbLignes()-1, matrice.getTailleLigne()-1))+"\n";
		
		return resultat;
	}
	
	/**
	 * Trouve et renvoie la ligne du pivot de l'étape actuelle.
	 * @param matrice La matrice du problème à résoudre.
	 * @param colonnePivot La colonne du pivot.
	 * @return La ligne du pivot de l'étape actuelle.
	 * @see Matrice
	 */
	private static int trouverLignePivot(Matrice matrice, int colonnePivot)
	{
		int lignePivot = 0;
		double valeurLigne;
		double min = matrice.getValeur(0, matrice.getTailleLigne()-1)/matrice.getValeur(0, colonnePivot);
		
		for(int i = 1; i < matrice.getNbLignes()-1;i++)
		{
			valeurLigne = matrice.getValeur(i, matrice.getTailleLigne()-1)/matrice.getValeur(i, colonnePivot);
			if(valeurLigne < min || min < 0)
			{
				min = valeurLigne;
				lignePivot = i;
			}
		}
		
		return lignePivot;
	}
	
	/**
	 * Trouve et renvoie la colonne du pivot de l'étape actuelle.
	 * @param matrice La matrice du problème à résoudre.
	 * @return La colonne du piot de l'étape actuelle.
	 * @see Matrice
	 */
	private static int trouverColonnePivot(Matrice matrice)
	{
		int colonnePivot;
		
		colonnePivot = matrice.getMaxLignePos(matrice.getNbLignes()-1);
		
		return colonnePivot;
	}
	
	/**
	 * Rend le pivot unitaire avant de renvoier la matrice modifiée.
	 * @param matrice La matrice du problème à résoudre.
	 * @param lignePivot La ligne du pivot de l'étape actuelle.
	 * @param colonnePivot La colonne du pivot de l'étape actuelle.
	 * @return	Une nouvelle Matrice avec la ligne du pivot rendue unitaire.
	 * @see Matrice
	 */
	private static Matrice rendrePivotUnitaire(Matrice matrice, int lignePivot, int colonnePivot)
	{
		double div = matrice.getValeur(lignePivot, colonnePivot);
		
		List<Double> ligne = matrice.getLigne(lignePivot);
		
		for (Double valeur : ligne) 
		{
			valeur /= div;
		}
		
		matrice.setLigne(lignePivot,ligne);
		
		return matrice;
	}
	
	/**
	 * Fait rentrer la variable de la colonne du pivot dans la base.
	 * @param matrice La matrice du problème à résoudre.
	 * @param lignePivot La ligne du pivot de l'étape actuelle.
	 * @param colonnePivot La colonne du pivot de l'étape actuelle.
	 * @return Une nouvelle Matrice
	 * @see Matrice
	 */
	private static Matrice faireRentrerPivotDansLaBase(Matrice matrice, int lignePivot, int colonnePivot) 
	{
		for(int i = 0; i < matrice.getNbLignes();i++)
		{
			if(i != lignePivot)
			{
				double mult = matrice.getValeur(i, colonnePivot) / matrice.getValeur(lignePivot, colonnePivot);
				
				for(int j = 0; j < matrice.getTailleLigne();j++)
				{
					matrice.setValeur(i, j, matrice.getValeur(i, j)-mult*matrice.getValeur(lignePivot, j));
				}
			}
		}
		
		
		return matrice;
	}
	
	/**
	 * Vérifie si la ligne objectif de la matrice contient toujours au moins une valeur supérieur à 0.
	 * @param matrice La matrice du problème à résoudre.
	 * @return Si la ligne objectif de la matrice contient toujours au moins une valeur supérieur à 0.
	 * @see Matrice
	 */
	private static boolean objectifEstPositive(Matrice matrice) 
	{
		List<Double> ligne = matrice.getLigne(matrice.getNbLignes()-1);
		int i=0;
		boolean verif=false;
		while(i<ligne.size() && verif == false) 
		{
			verif = ligne.get(i)>0 ? true : false;

			i++;
		}
		return verif;
	}
}
