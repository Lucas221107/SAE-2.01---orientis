package sae.metier;

/** Represente une position (ligne, colonne) sur le plateau d'Orientis.
  *
  * @author Groupe 2
  * @version 1.0
  */
public class Position
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private int ligne  ;
	private int colonne;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public Position ( int ligne, int colonne )
	{
		this.ligne   = ligne  ;
		this.colonne = colonne;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public int getLigne   () { return this.ligne  ; }
	public int getColonne () { return this.colonne; }

	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Position ) ) 
			return false;
		
		Position p = (Position) o;

		return this.ligne == p.ligne && this.colonne == p.colonne;
	}
}