package Modele;

import java.util.stream.Collectors;
import Utilitaire.Matrice2;

public abstract class Simplexe 
{
	
	public static String resoudre(Matrice2 matrice)
	{
		int lignePivot, colonnePivot;
		String resultat = "";
	
		while(matrice.objectifEstPositive())
		{
			colonnePivot = trouverColonnePivot(matrice);
			lignePivot = trouverLignePivot(matrice,colonnePivot);
			System.out.println("Ligne du pivot : "+lignePivot);
			System.out.println("Colonne du pivot : "+colonnePivot);
			matrice = rendrePivotUnitaire(matrice, lignePivot, colonnePivot);
			matrice = faireRentrerPivotDansLaBase(matrice, lignePivot, colonnePivot);
			System.out.println(matrice);
		}
		
		return resultat;
	}
	
	private static int trouverLignePivot(Matrice2 matrice, int colonnePivot)
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
	
	private static int trouverColonnePivot(Matrice2 matrice)
	{
		int colonnePivot;
		
		colonnePivot = matrice.getMaxLignePos(matrice.getHauteur()-1);
		
		return colonnePivot;
	}
	
	private static Matrice2 rendrePivotUnitaire(Matrice2 matrice, int lignePivot, int colonnePivot)
	{
		double div = matrice.getValeur(lignePivot, colonnePivot);
		
		matrice.setLigne(lignePivot,matrice.getLigne(lignePivot).stream().map(valeur -> valeur/div).collect(Collectors.toList()));
		
		return matrice;
	}
	
	private static Matrice2 faireRentrerPivotDansLaBase(Matrice2 matrice, int lignePivot, int colonnePivot) 
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
