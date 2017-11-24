package exceptions;

public class CheminInvalide extends Exception{

	public CheminInvalide(String chemin)
	{
		super("Le chemin d'accès" + chemin + "est invalide");
	}
}
