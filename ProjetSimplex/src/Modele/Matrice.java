package Modele;
import java.util.List;

import exceptions.doublonContrainteException;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrice {
	private List<List<Double>> matrice;
	private int nbContraintes; 
	
	public void setNbContraintes(int nbContraintes) {
		this.nbContraintes = nbContraintes;
	}
/**
 * Constructeur d'une Matrice
 * @param nbContrainte
 * @param nbVariable
 * @param fonctionObjectif
 */
	
	public Matrice(int nbContrainte, int nbVariable, Double... fonctionObjectif) {
		this.nbContraintes = nbContrainte;
		this.matrice = new ArrayList<List<Double>>();		
		List<Double> ligneObjectif = new ArrayList<>();
		
		for (Double valeur : fonctionObjectif) 
		{
			ligneObjectif.add(valeur);
		}
		
		for(int i = 0;i<nbContrainte;i++)
		{
			ligneObjectif.add(0.);
		}
		
		//Z
		ligneObjectif.add(0.);
		
		matrice.add(ligneObjectif);
		
	}
	
	
	@Override
	public String toString() { /* test à faire afin de verifier le toString de la liste */
		String affichage = "";
		for (List<Double> ligne : matrice) {
			for(Double val : ligne) {
				affichage += String.format("%6.2f ", val);
			}
			affichage +="\n";
		}
		
		return affichage;
	}
	
	public double getValeur(int ligne,int colonne){
		return (double) matrice.get(ligne).get(colonne);
	}
	
	public List<Double> getLigne(int ligne) {
		return matrice.get(ligne);
	}
	
	public void setLigne(int lignePos, List<Double> ligne ) {
		matrice.set(lignePos, ligne);
		
	}
	
	public void setValeur(int ligne,int colonne, double valeur) {
		matrice.get(ligne).set(colonne, valeur);
		
	} 
	
	public int getNbContrainte() {
		return this.getNbLignes()-1;
	}


	public int getNbVariable() {
		return this.getTailleLigne()-(1+getNbContrainte());
		// Nombre de variable = Taille de la ligne - 1 (terme indépendant) - N(nombre de contraintes)
	}


	public int getTailleLigne() {
		return matrice.get(0).size();
	}
	
	public int getNbLignes() {
		return matrice.size();
	}
	public void ajouterContrainte(double termeIndependant, Double... variables) throws doublonContrainteException
	{
		
		List<Double> ligneContrainte = new ArrayList<>();
		
		for (Double valeur : variables) 
		{
			ligneContrainte.add(valeur);
		}
		
		for(int i = 0;i<this.nbContraintes;i++)
		{
			if(i == this.getNbLignes()-1)
			{
				ligneContrainte.add(1.);
				
			}
			else {
				ligneContrainte.add(0.);
			}
		}
		
		for(List<Double> liste : matrice) {
			double mult=0;
			boolean egaux = true;
			double multMem=0;
			for(int i=0;i<variables.length;i++) {
				if(i==0) {
				multMem = liste.get(i)/ligneContrainte.get(i);
				}else{
					mult = liste.get(i)/ligneContrainte.get(i);
					System.out.print(i+" - "+mult + " - " + multMem+"  ");
					if(mult != multMem) {
						egaux=false;
						break;
					}
				}
			}
			if(egaux) {
				mult=liste.get(getTailleLigne()-1)/termeIndependant;
				System.out.print(mult + " - " + multMem+"  ");
				if(mult == multMem) {
					throw new doublonContrainteException();
				}
			}
			System.out.println("");
		}
		
		
			
		ligneContrainte.add(termeIndependant);
		
		matrice.add(ligneContrainte);
		
		List<Double> tmp = matrice.get(matrice.size()-1);
		
		matrice.set(matrice.size()-1,matrice.get(matrice.size()-2));
		
		matrice.set(matrice.size()-2, tmp);
	}
	

	public int getMaxLignePos(int numLigne){
		List<Double> ligne=matrice.get(numLigne); 
		int indiceMax=0;
		double max = 0;
		for(int i=0;i<ligne.size();i++) {
			if (ligne.get(i) > max) {
				max =  ligne.get(i);
				indiceMax = i;
			}
		}
		return indiceMax;
	}
	
	
	

}
