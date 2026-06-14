package jeu.metier;

import java.util.ArrayList;

/**
 * Représente une partie d'Orientis. Une partie est composée de cinq manches,
 * met en jeu un joueur sur un plateau et cumule le score final.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Partie
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Numéro de la manche en cours. */
	private int numeroManche;

	/** Joueur participant à la partie. */
	private Joueur joueur;

	/** Plateau de jeu sur lequel se déroule la partie. */
	private Plateau plateau;

	/** Liste des manches de la partie. */
	private ArrayList<Manche> manches;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une partie pour un joueur sur un plateau donné.
	 * La partie démarre à la manche zéro, sans aucune manche encore créée.
	 *
	 * @param joueur  joueur participant à la partie
	 * @param plateau plateau de jeu
	 */
	public Partie(Joueur joueur, Plateau plateau)
	{
		this.numeroManche = 0                     ;
		this.joueur       = joueur                ;
		this.plateau      = plateau               ;
		this.manches      = new ArrayList<Manche>();
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
	 * Termine la manche courante et calcule les scores associés.
	 */
	public void terminerManche()
	{
		/* Calcule et cumule le score de la manche dans le score total du joueur. */
		this.joueur.ajouterScoreManche();

		/* Remet le joueur à zéro pour la prochaine manche. */
		this.joueur.reinitialiserManche();

		/* Si la partie n'est pas terminée, on crée et stocke la prochaine manche. */
		if (!this.estTerminee())
		{
			Manche manche = this.nouvelleManche();
			this.manches.add(manche);
			System.out.println( "manche suivante");
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
	 * Retourne le score final cumulé sur l'ensemble des manches.
	 *
	 * @return le score final de la partie
	 */
	public int getScoreFinal()
	{
		return this.joueur.getScoreTotal();
	}

	public Manche getMancheCourante()
	{
		if ( this.manches.isEmpty())
			return null;

		return this.manches.get( this.manches.size() - 1 );
	}


	public Joueur getJoueur () { return this.joueur; }
}