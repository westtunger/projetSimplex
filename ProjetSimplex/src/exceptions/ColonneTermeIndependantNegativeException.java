package exceptions;

public class ColonneTermeIndependantNegativeException extends Exception
{
	public ColonneTermeIndependantNegativeException()
	{
		super("Toute la colonne des termes ind�pendant est n�gative.");
	}
}
