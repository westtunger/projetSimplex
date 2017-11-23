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
			//Ecriture dans les observer
		}
		
		//Le resultat final est disponible
	}
	
	public Point trouverPivot()
	{
		int lignePosPivot = matrice.getMinLignePos(matrice.longueur()-1);
		int colonnePosPivot = matrice.getMaxColonePos(matrice.hauteur()-1);
		
		return new Point(lignePosPivot, colonnePosPivot);
	}
}
