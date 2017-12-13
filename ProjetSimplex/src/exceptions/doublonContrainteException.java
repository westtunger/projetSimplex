package exceptions;

public class doublonContrainteException extends Exception {
	public doublonContrainteException(){
		super("Deux contraintes sont equivalentes.");
	}

}
