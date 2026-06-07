package conception.source.metier;

public class TestPlateau
{
	public static void main ( String[] args )
	{
		Plateau plateau = new Plateau ( 7, 7 );
		plateau.genererPlateau();

		System.out.println ( plateau );
	}
}