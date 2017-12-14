package Modele;
import java.util.List;

import exceptions.doublonContrainteException;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Classe Visant à fournir toutes les methodes nécessaires à la création d'une Matrice.
 * @author Ahmad Mohamad
 *
 */
public class Matrice {
	private List<List<Double>> matrice;
	private int nbContraintes; 
	
	public void setNbContraintes(int nbContraintes) {
		this.nbContraintes = nbContraintes;
	}
/**
 * Constructeur de Matrice
 * @param nbContrainte Le nombre de Contraintes
 * @param nbVariable Le nombre de Variables
 * @param fonctionObjectif la fonction Objectif
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
	/**
	 * Renvoie en format de Matrice le contenu de la matrice.
	 */
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
	/**
	 * Méthode permettant de récupérer une valeur de la matrice en fonction des coordonnées introduites en argument
	 * @param ligne La ligne de la matrice
	 * @param colonne La colonne de la matrice
	 * @return La valeur sera retournée en Double
	 */
	public double getValeur(int ligne,int colonne){
		return (double) matrice.get(ligne).get(colonne);
	}
	/**
	 * Getter d'une ligne de la matrice
	 * @param ligne On intoduit l'index de la ligne à récupérer
	 * @return L'élèment retourné sera une Liste de Double ( la Ligne)
	 */
	
	public List<Double> getLigne(int ligne) {
		return matrice.get(ligne);
	}
	/**
	 * Permet de paramètrer la ligne ( Setter) 
	 * @param lignePos La ligne à modifier
	 * @param ligne La ligne remplaçante
	 */
	public void setLigne(int lignePos, List<Double> ligne ) {
		matrice.set(lignePos, ligne);
		
	}
	/**
	 * Permet de paramètrer une Valeur, on selectionne a valeur à partir de ses coordonnées (Setter)
	 * @param ligne La ligne de la valeur
	 * @param colonne La colonne de la valeur
	 * @param valeur La valeur remplaçante
	 */
	public void setValeur(int ligne,int colonne, double valeur) {
		matrice.get(ligne).set(colonne, valeur);
		
	} 
	/**
	 * Getter du nombre de contraintes.
	 * @return Permet de retourner une valeur entière 
	 */
	public int getNbContrainte() {
		return this.getNbLignes()-1;
	}

	/**
	 * Getter Permettant de récupèrer le nombre de variables
	 * @return le nombre retourné est un Entier
	 */
	public int getNbVariable() {
		return this.getTailleLigne()-(1+getNbContrainte());
		// Nombre de variable = Taille de la ligne - 1 (terme indépendant) - N(nombre de contraintes)
	}

	/**
	 * Getter Permettant de récupèrer la taille de la ligne.
	 * @return on récupère la taille de la matrice sous forme de nombre entier.
	 */
	public int getTailleLigne() {
		return matrice.get(0).size();
	}
	/**
	 * Getter permettant de retourner le nombre de lignes 
	 * @return La valeur retournée est sous forme de nombre entier.
	 */
	public int getNbLignes() {
		return matrice.size();
	}
	/**
	 * Méthode permettant d'ajouter une Contrainte.
	 * @param termeIndependant Le terme indépendant de la contrainte ( en Double)
	 * @param variables Les variables à ajouter ( en Vecteur de Double)
	 * @throws doublonContrainteException Si la contrainte est equivalente à une autre contrainte déjà insérée, l'exception se déclenche
	 */
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
					if(mult != multMem) {
						egaux=false;
						break;
					}
				}
			}
			if(egaux) {
				mult=liste.get(getTailleLigne()-1)/termeIndependant;
				if(mult == multMem) {
					throw new doublonContrainteException();
				}
			}
		}
		
		
			
		ligneContrainte.add(termeIndependant);
		
		matrice.add(ligneContrainte);
		
		List<Double> tmp = matrice.get(matrice.size()-1);
		
		matrice.set(matrice.size()-1,matrice.get(matrice.size()-2));
		
		matrice.set(matrice.size()-2, tmp);
	}
	
	/**
	 * Getter de la valeur Maximale de la ligne. On récupère l'indice de la valeur.
	 * @param numLigne La ligne concernée.
	 * @return La valeur de l'indice retourné est un Entier
	 */
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
