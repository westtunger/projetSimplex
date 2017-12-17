package exceptions;

public class CheminInvalideException extends Exception{

	public CheminInvalideException(String chemin)
	{
		super("Le chemin d'accès" + chemin + " est invalide");
	}
}
