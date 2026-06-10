package jeuduo.metier;
import java.util.ArrayList;

/**
 * Représente une partie d'Orientis. Une partie est composée de cinq manches,
 * met en jeu deux joueurs sur un plateau et cumule les scores finaux.
 *
 * @author Groupe 2
 * @version 2.0
 */
public class Partie
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	/** Numéro de la manche en cours. */
	private int numeroManche;

	/** Premier joueur participant à la partie. */
	private Joueur joueur1;

	/** Deuxième joueur participant à la partie. */
	private Joueur joueur2;

	/** Joueur dont c'est actuellement le tour. */
	private Joueur joueurCourant;

	/** Plateau de jeu sur lequel se déroule la partie. */
	private Plateau plateau;

	/** Liste des manches de la partie. */
	private ArrayList<Manche> manches;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	/**
	 * Construit une partie pour deux joueurs sur un plateau donné.
	 * La partie démarre à la manche zéro, sans aucune manche encore créée.
	 * C'est joueur1 qui commence.
	 *
	 * @param joueur1 premier joueur participant à la partie
	 * @param joueur2 deuxième joueur participant à la partie
	 * @param plateau plateau de jeu
	 */
	public Partie(Joueur joueur1, Joueur joueur2, Plateau plateau)
	{
		this.numeroManche  = 0;
		this.joueur1       = joueur1;
		this.joueur2       = joueur2;
		this.joueurCourant = joueur1;
		this.plateau       = plateau;
		this.manches       = new ArrayList<Manche>();
	}

		/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return le premier joueur */
	public Joueur getJoueur1() { return this.joueur1; }

	/** @return le deuxième joueur */
	public Joueur getJoueur2() { return this.joueur2; }

	/** @return le joueur dont c'est le tour */
	public Joueur getJoueurCourant() { return this.joueurCourant; }

	/** @return le numéro de la manche courante */
	public int getNumeroManche() { return this.numeroManche; }

	/** @return le plateau de jeu */
	public Plateau getPlateau() { return this.plateau; }

	/** @return la manche en cours (dernière de la liste) */
	public Manche getMancheCourante()
	{
		return this.manches.get(this.manches.size() - 1);
	}

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Démarre la partie et lance la première manche.
	 */
	public void demarrer()
	{
		Manche premiereManche = this.nouvelleManche();
		this.manches.add(premiereManche);
	}

	/**
	 * Crée et démarre une nouvelle manche.
	 *
	 * @return la manche nouvellement créée
	 */
	public Manche nouvelleManche()
	{
		return new Manche(this.numeroManche++, new Pioche());
	}

	/**
	 * Termine la manche courante : cumule et réinitialise le score
	 * des deux joueurs, puis crée une nouvelle manche si la partie continue.
	 */
	public void terminerManche()
	{
		/* Calcule et cumule le score de la manche pour chaque joueur. */
		this.joueur1.ajouterScoreManche();
		this.joueur2.ajouterScoreManche();

		/* Remet les deux joueurs à zéro pour la prochaine manche. */
		this.joueur1.reinitialiserManche();
		this.joueur2.reinitialiserManche();

		/* Si la partie n'est pas terminée, on crée et stocke la prochaine manche. */
		if (!this.estTerminee())
		{
			Manche manche = this.nouvelleManche();
			this.manches.add(manche);
		}
	}

	/**
	 * Indique si la partie est terminée (cinq manches jouées).
	 *
	 * @return {@code true} si la partie est terminée
	 */
	public boolean estTerminee()
	{
		return this.manches.size() >= 5
			&& this.manches.get(this.manches.size() - 1).estTerminee();
	}

	/**
	 * Détermine le gagnant de la partie.
	 * Comparaison dans l'ordre :
	 *   1. le plus grand score total gagne
	 *   2. en cas d'égalité, le meilleur score sur une manche gagne
	 *   3. en cas d'égalité parfaite, renvoie null (match nul)
	 *
	 * @return le joueur gagnant, ou {@code null} en cas d'égalité parfaite
	 */
	public Joueur getGagnant()
	{
		int score1 = this.joueur1.getScoreTotal();
		int score2 = this.joueur2.getScoreTotal();

		if (score1 > score2) return this.joueur1;
		if (score2 > score1) return this.joueur2;

		/* Égalité sur le score total : on compare le meilleur score par manche. */
		int meilleur1 = this.joueur1.getMeilleurScoreManche();
		int meilleur2 = this.joueur2.getMeilleurScoreManche();

		if (meilleur1 > meilleur2) return this.joueur1;
		if (meilleur2 > meilleur1) return this.joueur2;

		/* Égalité parfaite : match nul. */
		return null;
	}

	/**
	 * Bascule le tour vers l'autre joueur.
	 */
	public void joueurSuivant()
	{
		if (this.joueurCourant == this.joueur1)
			this.joueurCourant = this.joueur2;
		else
			this.joueurCourant = this.joueur1;
	}

	/**
	 * Retourne le score final d'un joueur donné.
	 *
	 * @param j le joueur dont on veut le score
	 * @return le score total du joueur
	 */
	public int getScoreFinal(Joueur j)
	{
		return j.getScoreTotal();
	}

}