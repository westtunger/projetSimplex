package Modele;

import java.util.List;
import java.util.stream.Collectors;

import exceptions.ColonneTermeIndependantNegativeException;

/**
 * Classe visant � fournir tout les outils pour r�soudre un probl�me du simplexe.
 * Requier un objet de la classe Matrice correctement cr��.
 * @see Matrice
 * @author Nicolas Viseur
 */
public abstract class Simplexe 
{
	/**
	 * Fonction centrale appel�e lorsque l'on veut r�soudre une matrice avec la m�thode du simplexe.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @return Une chaine de caract�re contenant tout le d�veloppement de la r�solution ainsi que la r�ponse finale.
	 * @see Matrice
	 */
	public static String resoudre(Matrice matrice)
	{
		int lignePivot, colonnePivot, i=1;
		String resultat = "";
		boolean solutionNonBornee = false, solutionsMultiples = false;
		
		resultat += "Etat initial : \n\n";
		resultat += "Matrice : \n"+matrice.toString()+"\n";
		resultat += ecrireSb(matrice);
		resultat += "-----------------------------------------------------------------------------------\n";
		
		while(objectifEstPositive(matrice))
		{
			colonnePivot = trouverColonnePivot(matrice);
			solutionNonBornee = isSolutionNonBornee(matrice,colonnePivot);
			if(solutionNonBornee)
				break;
			try 
			{
				lignePivot = trouverLignePivot(matrice,colonnePivot);
			} 
			catch (ColonneTermeIndependantNegativeException e) 
			{
				return e.toString();
			}
			resultat += "Etape n�"+i+" : \n\n";
			matrice = rendrePivotUnitaire(matrice, lignePivot, colonnePivot);
			resultat += "Ligne du pivot : "+lignePivot+"\n";
			resultat += "Colonne du pivot : "+colonnePivot+"\n\n";
			resultat += "Matrice apr�s avoir rendu le pivot unitaire : \n"+matrice.toString()+"\n";
			matrice = faireRentrerPivotDansLaBase(matrice, lignePivot, colonnePivot);
			resultat += "Matrice apr�s avoir fait rentrer le pivot dans la base : \n"+matrice.toString()+"\n";
			resultat += ecrireSb(matrice);
			resultat += "-----------------------------------------------------------------------------------\n";
			i++;
		}
		solutionsMultiples = possedeSolutionsMultiples(matrice);
		resultat += sortirResultat(matrice,solutionNonBornee, solutionsMultiples);
		
		return resultat;
	}
	
	/**
	 * Regarde si la solution est restreinte ou non.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @param colonne La colonne � v�rifier.
	 * @return Si oui ou non la solution est restreinte.
	 * @see Matrice
	 */
	private static boolean isSolutionNonBornee(Matrice matrice, int colonne)
	{
		boolean solutionNonBornee = false;
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
		
		//Toute la colonne de la variable entrant en base est n�gative ou nulle
		//Il s'agit donc d'un cas de solution illimit�e.
		if(n == matrice.getNbLignes()-1)
		{
			solutionNonBornee = true;
		}
		
		return solutionNonBornee;
	}
	
	/**
	 * Ecrit la solution de base dans un chaine de caract�res et la renvoie.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @return La solution de base sous forme de chaine de caract�res.
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
	 * Retourne si oui ou non le probl�me poss�de des solutions multiples.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @return Si oui ou non le probl�me poss�de des solutions multiples.
	 */
	private static boolean possedeSolutionsMultiples(Matrice matrice) 
	{	
		boolean solutionsMultiples = false;
		
		//Determination d'un cas de solution multiples
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
			
			if(n < 0)
			{
				//Si l'une des variables de d�cision non base a une valeur de 0 dans la ligne Z
				if(matrice.getValeur(matrice.getNbLignes()-1, i) == 0)
				{
					solutionsMultiples = true;
					break;
				}
			}
		}
		
		return solutionsMultiples;
	}
	
	/**
	 * Ecrit le r�sultat du simplexe sous forme de chaine de caract�res et le renvoi.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @param solutionNonBornee Information si la solution est r�streinte ou non.
	 * @return Le r�sultat du probl�me sous forme de chaine de caract�res.
	 * @see Matrice
	 */
	private static String sortirResultat(Matrice matrice, boolean solutionNonBornee, boolean solutionsMultiples) 
	{
		String resultat = "";		
		
		if(solutionsMultiples)
		{
			resultat+="Il y a plusieurs couples ";
			
			for(int i = 1; i <= matrice.getNbVariables(); i++)
			{
				resultat+="X"+i;
				
				if(i == matrice.getNbVariables())
					resultat += " ";
				else
					resultat += ", ";
			}
			
			resultat+="possibles pour la valeur optimale Z="+Math.abs(matrice.getValeur(matrice.getNbLignes()-1, matrice.getTailleLigne()-1))+" r�pondants aux contraintes du probl�me. L'un d'eux est :\n";
		}
		
		if(solutionNonBornee)
		{
			resultat+="La solution n'est pas born�e, c�d qu'il n'y a aucune valeurs optimale sp�cifique pour la fonction objectif (le probl�me est mal pos�).\nTant que les variabiables augmentes, Z augmente aussi.\n";
			return resultat;
		}
		
		for(int i=0; i < matrice.getNbVariables(); i++)
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
	 * Trouve et renvoie la ligne du pivot de l'�tape actuelle.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @param colonnePivot La colonne du pivot.
	 * @return La ligne du pivot de l'�tape actuelle.
	 * @throws ColonneTermeIndependantNegativeException Si toutes les divisions depuis la colonne des termes ind�pendant retourne une valeur n�gative.
	 * @see Matrice
	 */
	private static int trouverLignePivot(Matrice matrice, int colonnePivot) throws ColonneTermeIndependantNegativeException
	{
		int lignePivot = 0;
		double valeurLigne;
		double min = matrice.getValeur(0, matrice.getTailleLigne()-1)/matrice.getValeur(0, colonnePivot);
		
		for(int i = 1; i < matrice.getNbLignes()-1;i++)
		{
			valeurLigne = matrice.getValeur(i, matrice.getTailleLigne()-1)/matrice.getValeur(i, colonnePivot);
			if((valeurLigne < min || min < 0) && valeurLigne > 0)
			{
				min = valeurLigne;
				lignePivot = i;
			}
		}
		
		if(min < 0)
		{
			throw new ColonneTermeIndependantNegativeException();
		}
		
		return lignePivot;
	}
	
	/**
	 * Trouve et renvoie la colonne du pivot de l'�tape actuelle.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @return La colonne du piot de l'�tape actuelle.
	 * @see Matrice
	 */
	private static int trouverColonnePivot(Matrice matrice)
	{
		int colonnePivot;
		
		colonnePivot = matrice.getMaxLignePos(matrice.getNbLignes()-1);
		
		return colonnePivot;
	}
	
	/**
	 * Rend le pivot unitaire avant de renvoyer la matrice modifi�e.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @param lignePivot La ligne du pivot de l'�tape actuelle.
	 * @param colonnePivot La colonne du pivot de l'�tape actuelle.
	 * @return	Une nouvelle Matrice avec la ligne du pivot rendue unitaire.
	 * @see Matrice
	 */
	private static Matrice rendrePivotUnitaire(Matrice matrice, int lignePivot, int colonnePivot)
	{
		double div = matrice.getValeur(lignePivot, colonnePivot);
		
		List<Double> ligne = matrice.getLigne(lignePivot);
		
		for(int i = 0;i<ligne.size();i++)
		{
			ligne.set(i,ligne.get(i)/div);
		}
		
		matrice.setLigne(lignePivot,ligne);
		
		return matrice;
	}
	
	/**
	 * Fait rentrer la variable de la colonne du pivot dans la base.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @param lignePivot La ligne du pivot de l'�tape actuelle.
	 * @param colonnePivot La colonne du pivot de l'�tape actuelle.
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
	 * V�rifie si la ligne objectif de la matrice contient toujours au moins une valeur sup�rieur � 0.
	 * @param matrice La matrice du probl�me � r�soudre.
	 * @return Si la ligne objectif de la matrice contient toujours au moins une valeur sup�rieur � 0.
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
