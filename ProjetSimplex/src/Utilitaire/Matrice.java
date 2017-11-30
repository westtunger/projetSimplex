package Utilitaire;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrice {
	private List<List<Double>> matrice;
	private int largeur;
	private int hauteur=0;
	private int nbContrainte;
	private int nbVariable;
	
	public Matrice(int nbContrainte, int nbVariable, Double... fonctionObjectif) { /*CONSTRUCTEUR DEGEU À MODIFIER*/
		this.nbVariable = nbVariable;
		
		this.nbContrainte = nbContrainte;
		
		this.matrice = new ArrayList<List<Double>>();		
		List<Double> ligneObjectif = new ArrayList<>();		
		//Arrays.asList(fonctionObjectif).forEach(valeur -> ligneObjectif.add(valeur));
		
		for(Double valeur : fonctionObjectif ) {
			ligneObjectif.add(valeur);
		};
		
		for(int i = 0;i<nbContrainte;i++)
		{
			ligneObjectif.add(0.);
		}
		
		//Z
		ligneObjectif.add(0.);
		
		matrice.add(ligneObjectif);
		this.largeur=ligneObjectif.size();
		this.hauteur++;
		
	}
	
	
	@Override
	public String toString() { /* test à faire afin de verifier le toString de la liste */
		String affichage = null;
		for (List<Double> ligne : matrice) {
			affichage += ligne.toString() + "\n";
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
		return nbContrainte;
	}


	public int getNbVariable() {
		return nbVariable;
	}


	public int getLargeur() {
		return largeur;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getMaxColonnePos(int colonne) {
		double max = 0;
		int ligne=-1;
		for(int i=0;i<=this.getHauteur();i++) {//verifier si le get hauteur depasse
			if(matrice.get(colonne).get(i) > max) {
				max = matrice.get(colonne).get(i);
				ligne =i;
			}
		}
			return ligne;
		}
		
	public void ajouterContrainte(double termeIndependant, Double... variables)
	{
		List<Double> ligneContrainte = new ArrayList<>();		
		Arrays.asList(variables).forEach(valeur -> ligneContrainte.add(valeur));
		
		for(int i = 0;i<nbContrainte;i++)
		{
			if(i == hauteur-1)
			{
				ligneContrainte.add(1.);
			}
			else
				ligneContrainte.add(0.);
		}
		ligneContrainte.add(termeIndependant);
		
		matrice.add(ligneContrainte);
		
		List<Double> tmp = matrice.get(matrice.size()-1);
		
		matrice.set(matrice.size()-1,matrice.get(matrice.size()-2));
		
		matrice.set(matrice.size()-2, tmp);
		hauteur++;
	}
	
	public int getMinColonnePos(int colonne) {
		double min = this.getMaxColonnePos(colonne);
		int ligne=-1;
		for(int i=0;i<=this.getHauteur();i++) {
			if(matrice.get(colonne).get(i) < min) {
				min = matrice.get(colonne).get(i);
				ligne =i;
			}
		}
			return ligne;
	}
	
	public int getMaxLignePos(int ligne){
		List<Double> pos=matrice.get(ligne); 
		int indiceMax=0;
		double max = 0;
		for(int i=0;i<=pos.size();i++) {
			if ((double)pos.get(i) > max) {
				max = (double) pos.get(i);
				indiceMax = i;
			}
		}
		return indiceMax;
	}
	public int getMinLignePos(int ligne){
		List<Double> pos=matrice.get(ligne); 
		int indiceMin=0;
		double min = getMaxLignePos(ligne);//Indou représente
		for(int i=0;i<=pos.size();i++) {
			if ((double)pos.get(i) > min) {
				min = (double) pos.get(i);
				indiceMin = i;
			}
		}
		return indiceMin;
	}
	public boolean objectifEstPositive() {
		List<Double> ligne = matrice.get(hauteur-1);
		int i=0;
		boolean verif=false;
		while(i<=ligne.size()-1 && verif == false) {
			if(ligne.get(i)>0) {
				verif = true;
			}else {
				verif = false;
			}
			i++;
		}
		return verif;
	}
	
	
	

}
