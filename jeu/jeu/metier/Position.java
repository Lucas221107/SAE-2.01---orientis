package jeu.metier;


/**
 * Représente une position (ligne, colonne) sur le plateau d'Orientis.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Position
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Numéro de ligne de la position (axe vertical). */
	private int ligne;

	/** Numéro de colonne de la position (axe horizontal). */
	private int colonne;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une position avec les coordonnées spécifiées.
	 *
	 * @param ligne   le numéro de ligne
	 * @param colonne le numéro de colonne
	 */
	public Position ( int ligne, int colonne )
	{
		this.ligne   = ligne  ;
		this.colonne = colonne;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/**
	 * Retourne le numéro de ligne de cette position.
	 *
	 * @return la ligne
	 */
	public int getLigne () { return this.ligne; }

	/**
	 * Retourne le numéro de colonne de cette position.
	 *
	 * @return la colonne
	 */
	public int getColonne () { return this.colonne; }

	/**
	 * Indique si cet objet est égal à un autre.
	 * Deux positions sont égales si elles ont la même ligne et la même colonne.
	 *
	 * @param o l'objet à comparer
	 * @return {@code true} si {@code o} est une {@code Position} avec les mêmes coordonnées,
	 *         {@code false} sinon
	 */
	public boolean equals ( Object o )
	{
		// Vérifie que o est bien une instance de Position
		if ( ! ( o instanceof Position ) )
			return false;

		// Cast sûr après vérification du type
		Position p = (Position) o;

		// Comparaison des coordonnées ligne et colonne
		return this.ligne == p.ligne && this.colonne == p.colonne;
	}
}