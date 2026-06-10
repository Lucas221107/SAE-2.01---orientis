package jeu.metier;

/**
 * Représente un fanion de la pioche. Un fanion porte un numéro et peut être
 * clair ou foncé. Un fanion de numéro zéro est considéré comme un joker,
 * permettant de capturer n'importe quelle balise valide.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Fanion
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Numéro du fanion (0 pour un joker). */
	private int numero;

	/** Indique si le fanion est foncé ({@code true}) ou clair ({@code false}). */
	private boolean estFonce;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit un fanion avec son numéro et son type (clair ou foncé).
	 *
	 * @param numero   numéro du fanion (0 pour un joker)
	 * @param estFonce {@code true} si le fanion est foncé, {@code false} s'il est clair
	 */
	public Fanion(int numero, boolean estFonce)
	{
		this.numero   = numero  ;
		this.estFonce = estFonce;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return le numéro du fanion */
	public int getNumero() { return this.numero; }

	/**
	 * Indique si le fanion est un joker (numéro zéro).
	 *
	 * @return {@code true} si le fanion est un joker
	 */
	public boolean estJoker()
	{
		return this.numero == 0;
	}

	/**
	 * Indique si le fanion est foncé.
	 *
	 * @return {@code true} si le fanion est foncé
	 */
	public boolean estFonce()
	{
		return this.estFonce;
	}

	/**
	 * Indique si le fanion correspond à un numéro de balise donné.
	 * Un joker correspond à n'importe quel numéro ; sinon le numéro doit être identique.
	 *
	 * @param numero numéro de balise à tester
	 * @return {@code true} si le fanion permet de capturer ce numéro
	 */
	public boolean correspond(int numero)
	{
		return this.estJoker() || this.numero == numero;
	}

	/**
	 * Retourne une représentation textuelle du fanion sous la forme
	 * {@code "Clair 5"}, {@code "Fonce 3"} ou {@code "Fonce ∞"} pour un joker.
	 *
	 * @return chaîne représentant le fanion
	 */
	public String toString()
	{
		String type   = this.estFonce      ? "Fonce" : "Clair"          ;
		String valeur = this.estJoker()     ? "∞"     : "" + this.numero ;

		return type + " " + valeur;
	}
}
