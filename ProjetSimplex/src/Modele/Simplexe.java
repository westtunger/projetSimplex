package Modele;

import java.util.List;
import java.util.stream.Collectors;

import Utilitaire.Matrice;

public abstract class Simplexe 
{
	public static String resoudre(Matrice matrice)
	{
		int lignePivot, colonnePivot, i=1;
		String resultat = "";
		
		resultat += "Etat initial : \n\n";
		resultat += "Matrice : \n"+matrice.toString()+"\n";
		resultat += ecrireSb(matrice);
		resultat += "-----------------------------------------------------------------------------------\n";
	
		while(matrice.objectifEstPositive())
		{
			resultat += "Etape n°"+i+" : \n\n";
			
			colonnePivot = trouverColonnePivot(matrice);
			lignePivot = trouverLignePivot(matrice,colonnePivot);
			resultat += "Ligne du pivot : "+lignePivot+"\n";
			resultat += "Colonne du pivot : "+colonnePivot+"\n\n";
			matrice = rendrePivotUnitaire(matrice, lignePivot, colonnePivot);
			resultat += "Matrice après avoir rendu le pivot unitaire : \n"+matrice.toString()+"\n";
			matrice = faireRentrerPivotDansLaBase(matrice, lignePivot, colonnePivot);
			resultat += "Matrice après avoir fait rentrer le pivot dans la base : \n"+matrice.toString()+"\n";
			resultat += ecrireSb(matrice);
			resultat += "-----------------------------------------------------------------------------------\n";
			i++;
		}
		
		resultat += sortirResultat(matrice);
		
		return resultat;
	}
	
	private static String ecrireSb(Matrice matrice) 
	{
		String sb = "Solution de base : \n(";
		
		for(int i=0; i < matrice.getLargeur()-1; i++)
		{
			int n = -1;
			
			for(int j = 0;j<matrice.getHauteur();j++)
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
				sb += " "+matrice.getValeur(n, matrice.getLargeur()-1)+" ";
			}
			else
			{
				sb += 0;
			}
			
			if(i < matrice.getLargeur()-2)
			{
				sb += ",";
			}
		}
		
		sb+= ") ";
		
		sb += "Z = "+Math.abs(matrice.getValeur(matrice.getHauteur()-1, matrice.getLargeur()-1))+"\n";
		
		return sb;
	}
	
	private static String sortirResultat(Matrice matrice) 
	{
		String resultat = "";
		
		for(int i=0; i < matrice.getNbVar(); i++)
		{
			int n = -1;
			
			for(int j = 0;j<matrice.getHauteur();j++)
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
				resultat += "x"+(i+1)+"* = "+matrice.getValeur(n, matrice.getLargeur()-1)+"\n";
			}
			else
			{
				resultat += "x"+(i+1)+"* = "+0+"\n";
			}
		}
		
		resultat += "Z* = "+Math.abs(matrice.getValeur(matrice.getHauteur()-1, matrice.getLargeur()-1))+"\n";
		
		return resultat;
	}
	
	private static int trouverLignePivot(Matrice matrice, int colonnePivot)
	{
		int lignePivot = 0;
		double valeurLigne;
		double min = matrice.getValeur(0, matrice.getLargeur()-1)/matrice.getValeur(0, colonnePivot);
		
		for(int i = 1; i < matrice.getHauteur()-1;i++)
		{
			valeurLigne = matrice.getValeur(i, matrice.getLargeur()-1)/matrice.getValeur(i, colonnePivot);
			if(valeurLigne < min || min < 0)
			{
				min = valeurLigne;
				lignePivot = i;
			}
		}
		
		return lignePivot;
	}
	
	private static int trouverColonnePivot(Matrice matrice)
	{
		int colonnePivot;
		
		colonnePivot = matrice.getMaxLignePos(matrice.getHauteur()-1);
		
		return colonnePivot;
	}
	
	private static Matrice rendrePivotUnitaire(Matrice matrice, int lignePivot, int colonnePivot)
	{
		double div = matrice.getValeur(lignePivot, colonnePivot);
		
		//Utilisation du stream pour faciliter le travail.
		List<Double> ligne = matrice.getLigne(lignePivot).stream().map(valeur -> valeur/div).collect(Collectors.toList());
		
		matrice.setLigne(lignePivot,ligne);
		
		return matrice;
	}
	
	private static Matrice faireRentrerPivotDansLaBase(Matrice matrice, int lignePivot, int colonnePivot) 
	{
		for(int i = 0; i < matrice.getHauteur();i++)
		{
			if(i != lignePivot)
			{
				double mult = matrice.getValeur(i, colonnePivot) / matrice.getValeur(lignePivot, colonnePivot);
				
				for(int j = 0; j < matrice.getLargeur();j++)
				{
					matrice.setValeur(i, j, matrice.getValeur(i, j)-mult*matrice.getValeur(lignePivot, j));
				}
			}
		}
		
		
		return matrice;
	}
}
