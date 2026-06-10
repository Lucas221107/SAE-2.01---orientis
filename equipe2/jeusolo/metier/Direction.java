package jeusolo.metier;

/**
 * Représente les huit directions de la rose des vents utilisées pour relier
 * deux balises. Chaque direction porte le décalage en ligne et en colonne
 * permettant de passer d'une case à la suivante dans cette direction.
 *
 * @author Groupe 2
 * @version 1.0
 */
public enum Direction
{
	/** Vers le haut. */
	NORD       ( -1,  0 ),

	/** Vers le haut à droite. */
	NORD_EST   ( -1,  1 ),

	/** Vers la droite. */
	EST        (  0,  1 ),

	/** Vers le bas à droite. */
	SUD_EST    (  1,  1 ),

	/** Vers le bas. */
	SUD        (  1,  0 ),

	/** Vers le bas à gauche. */
	SUD_OUEST  (  1, -1 ),

	/** Vers la gauche. */
	OUEST      (  0, -1 ),

	/** Vers le haut à gauche. */
	NORD_OUEST ( -1, -1 );

	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Décalage en ligne associé à cette direction. */
	private int dLigne;

	/** Décalage en colonne associé à cette direction. */
	private int dColonne;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une direction avec ses décalages de ligne et de colonne.
	 *
	 * @param dLigne   décalage en ligne
	 * @param dColonne décalage en colonne
	 */
	private Direction(int dLigne, int dColonne)
	{
		this.dLigne   = dLigne  ;
		this.dColonne = dColonne;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return le décalage en ligne de cette direction */
	public int getDLigne()   { return this.dLigne  ; }

	/** @return le décalage en colonne de cette direction */
	public int getDColonne() { return this.dColonne; }
}
