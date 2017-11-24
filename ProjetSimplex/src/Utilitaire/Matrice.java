package Utilitaire;
import java.util.List;
import java.util.ArrayList;

public class Matrice {
	List<List> matrice;
	int largeur;
	int hauteur=0;
	public Matrice(List<Double> fonctionObjectif) { /* pas encore modifiés */
		this.matrice = new ArrayList<List>();
		matrice.add(fonctionObjectif);
		this.largeur=fonctionObjectif.size();
		this.hauteur++;
		
	}
	
	
	@Override
	public String toString() { /* pas encore modifiés */
		return "Matrice [matrice=" + matrice + "]";
	}

	public double getValeur(int ligne,int colonne){
		return (double) matrice.get(ligne).get(colonne);
	}
	
	public List getLigne(int ligne) {
		return matrice.get(ligne);
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getMaxColonnePos(int ligne) {
		return 0;
	}
	
	public int getMinColonnePos(int ligne) {
		return 0;
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
		while(i<=ligne.size() && verif == false) {
			if(ligne.get(i)<=0) {
				verif = true;
			}else {
				verif = false;
			}
			i++;
		}
		return verif;
	}
	
	
	

}
