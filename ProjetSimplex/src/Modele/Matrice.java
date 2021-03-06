package Modele;
import java.util.List;

import exceptions.doublonContrainteException;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Classe Visant � fournir toutes les methodes n�cessaires � la cr�ation d'une Matrice.
 * @author Ahmad Mohamad
 *
 */
public class Matrice {
	private List<List<Double>> matrice;
	private int nbContraintes; 
	
	/**
	 * Assigne une valeur � nbContraintes.
	 * @param nbContraintes Le nombre de contraintes
	 */
	public void setNbContraintes(int nbContraintes) {
		this.nbContraintes = nbContraintes;
	}
	
	/**
	 * Constructeur de Matrice
	 * @param nbContraintes Le nombre de Contraintes
	 * @param nbVariables Le nombre de Variables
	 * @param fonctionObjectif la fonction Objectif
	 */
	public Matrice(int nbContraintes, int nbVariables, Double... fonctionObjectif) {
		this.nbContraintes = nbContraintes;
		this.matrice = new ArrayList<List<Double>>();		
		List<Double> ligneObjectif = new ArrayList<>();
		
		for (Double valeur : fonctionObjectif) 
		{
			ligneObjectif.add(valeur);
		}
		
		for(int i = 0;i<nbContraintes;i++)
		{
			ligneObjectif.add(0.);
		}
		

		ligneObjectif.add(0.);
		
		matrice.add(ligneObjectif);
		
	}
	
	/**
	 * Renvoie en format de Matrice le contenu de la matrice.
	 */
	@Override
	public String toString() { /* test � faire afin de verifier le toString de la liste */
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
	 * M�thode permettant de r�cup�rer une valeur de la matrice en fonction des coordonn�es introduites en argument
	 * @param ligne La ligne de la matrice
	 * @param colonne La colonne de la matrice
	 * @return La valeur sera retourn�e en Double
	 */
	public Double getValeur(int ligne,int colonne){
		return (Double) matrice.get(ligne).get(colonne);
	}
	
	/**
	 * Getter d'une ligne de la matrice
	 * @param ligne On intoduit l'index de la ligne � r�cup�rer
	 * @return L'�l�ment retourn� sera une Liste de Double ( la Ligne)
	 */	
	public List<Double> getLigne(int ligne) {
		return matrice.get(ligne);
	}
	
	/**
	 * Permet d'assigner la ligne ( Setter) 
	 * @param lignePos La ligne � modifier
	 * @param ligne La ligne rempla�ante
	 */
	public void setLigne(int lignePos, List<Double> ligne ) {
		matrice.set(lignePos, ligne);
		
	}
	
	/**
	 * Permet d'assigner une Valeur, on selectionne a valeur � partir de ses coordonn�es (Setter)
	 * @param ligne La ligne de la valeur
	 * @param colonne La colonne de la valeur
	 * @param valeur La valeur rempla�ante
	 */
	public void setValeur(int ligne,int colonne, Double valeur) {
		matrice.get(ligne).set(colonne, valeur);
		
	} 
	
	/**
	 * Renvoie le nombre de contraintes.
	 * @return Permet de retourner une valeur enti�re 
	 */
	public int getNbContraintes() {
		return this.getNbLignes()-1;
	}

	/**
	 * Renvoie le nombre de variables
	 * @return le nombre retourn� est un Entier
	 */
	public int getNbVariables() {
		return this.getTailleLigne()-(1+getNbContraintes());
		// Nombre de variable = Taille de la ligne - 1 (terme ind�pendant) - N(nombre de contraintes)
	}

	/**
	 *Renvoie la taille de la ligne.
	 * @return on r�cup�re la taille de la matrice sous forme de nombre entier.
	 */
	public int getTailleLigne() {
		return matrice.get(0).size();
	}
	
	/**
	 * Renvoie le nombre de lignes 
	 * @return La valeur retourn�e est sous forme de nombre entier.
	 */
	public int getNbLignes() {
		return matrice.size();
	}
	
	/**
	 * Permet d'ajouter une contrainte.
	 * @param termeIndependant Le terme ind�pendant de la contrainte ( en Double)
	 * @param variables Les variables � ajouter ( en Vecteur de Double)
	 * @throws doublonContrainteException Si la contrainte est equivalente � une autre contrainte d�j� ins�r�e, l'exception se d�clenche
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
	 * Renvoie l'indice de la valeur.
	 * @param numLigne La ligne concern�e.
	 * @return La valeur de l'indice retourn� est un Entier
	 */
	public int getMaxLignePos(int numLigne){
		List<Double> ligne=matrice.get(numLigne); 
		int indiceMax=0;
		double max = 0;
		for(int i=0;i<ligne.size()-1;i++) {
			if (ligne.get(i) > max) {
				max =  ligne.get(i);
				indiceMax = i;
			}
		}
		return indiceMax;
	}
	
	
	

}
