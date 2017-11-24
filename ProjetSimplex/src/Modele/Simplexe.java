package Modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Utilitaire.Matrice;

public class Simplexe 
{
	Matrice matrice;
	Simplexe instance;

	private Simplexe()
	{
		
	}
	
	public Simplexe getInstance()
	{
		if(instance == null)
			instance = new Simplexe();
	
		return instance;
	}
	
	public void setMatrice(Matrice matrice) 
	{
		this.matrice = matrice;
	}
	
	public void resoudre()
	{
		while(matrice.objectifEstPositive())
		{
			Point pivot = trouverPivot();
		}
		
		//Le resultat final est disponible
	}
	
	public Point trouverPivot()
	{
		int lignePosPivot = matrice.getMinLignePos(matrice.getLargeur()-1);
		int colonnePosPivot = matrice.getMaxColonnePos(matrice.getHauteur()-1);
		
		return new Point(lignePosPivot, colonnePosPivot);
	}
}
