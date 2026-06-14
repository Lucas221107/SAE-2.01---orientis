package jeu.metier;

/**
 * Représente une manche d'une partie d'Orientis. Une manche s'appuie sur une
 * pioche de fanions et se termine dès que le sixième fanion foncé a été tiré.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Manche
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Numéro de la manche dans la partie. */
	private int numero;

	/** Nombre de fanions foncés déjà tirés durant la manche. */
	private int nbFoncesTires;

	/** Pioche de fanions utilisée pendant la manche. */
	private Pioche pioche;

	/** Fanion actuellement tiré, sur lequel les joueurs jouent. */
	private Fanion fanionCourant;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une manche avec son numéro et sa pioche.
	 * Le compteur de fanions foncés est initialisé à zéro et aucun fanion
	 * n'est encore tiré.
	 *
	 * @param numero numéro de la manche
	 * @param pioche pioche de fanions de la manche
	 */
	public Manche(int numero, Pioche pioche)
	{
		this.numero        = numero;
		this.nbFoncesTires = 0     ;
		this.pioche        = pioche;
		this.fanionCourant = null  ;
	}

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Pioche un nouveau fanion pour l'ensemble des joueurs et le définit comme
	 * fanion courant.
	 *
	 * @return le fanion pioché
	 */
	public Fanion piocherFanion()
	{
		/* On pioche le fanion et on le retient comme fanion courant de la manche. */
		this.fanionCourant = this.pioche.piocher();

		/* La manche se termine au 6e fanion fonce : on incremente le compteur. */
		if (this.fanionCourant != null && this.fanionCourant.estFonce())
			this.nbFoncesTires++;

		return this.fanionCourant;
	}

	/**
	 * Indique si la manche est terminée (sixième fanion foncé tiré, ou pioche vide).
	 *
	 * @return {@code true} si la manche est terminée
	 */
	public boolean estTerminee()
	{
		return this.nbFoncesTires >= 6 || this.pioche.estVide();
	}
}