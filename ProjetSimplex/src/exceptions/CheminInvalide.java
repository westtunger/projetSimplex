package exceptions;

public class CheminInvalide extends Exception{

	public CheminInvalide(String chemin)
	{
		super("Le chemin d'acc�s" + chemin + "est invalide");
	}
}
