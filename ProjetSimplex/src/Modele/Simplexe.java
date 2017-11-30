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
	
		while(objectifEstPositive(matrice))
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
	
	private static String sortirResultat(Matrice matrice) 
	{
		String resultat = "";
		
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
	
	private static int trouverColonnePivot(Matrice matrice)
	{
		int colonnePivot;
		
		colonnePivot = matrice.getMaxLignePos(matrice.getNbLignes()-1);
		
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
