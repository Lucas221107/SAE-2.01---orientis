package jeu.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une partie d'Orientis. Une partie est composée de cinq manches et
 * met en jeu de 1 à 4 joueurs sur un plateau. Le mode solo correspond
 * simplement à une partie avec un seul joueur dans la liste.
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

	/** Joueurs participant à la partie (1 à 4). */
	private List<Joueur> joueurs;

	/** Indice, dans la liste, du joueur dont c'est le tour. */
	private int indiceJoueurCourant;

	/** Plateau de jeu sur lequel se déroule la partie. */
	private Plateau plateau;

	/** Liste des manches de la partie. */
	private ArrayList<Manche> manches;

	private int nbManche;
	private boolean partieTerminee = false;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une partie pour un ou plusieurs joueurs sur un plateau donné.
	 * La partie démarre à la manche zéro, sans aucune manche encore créée.
	 * C'est le premier joueur de la liste qui commence.
	 *
	 * @param joueurs liste des joueurs (1 pour le solo, 2 à 4 pour le multijoueur)
	 * @param plateau plateau de jeu
	 */
	public Partie(List<Joueur> joueurs, Plateau plateau)
	{
		this.numeroManche        = 0;
		this.joueurs             = joueurs;
		this.indiceJoueurCourant = 0;
		this.nbManche            = plateau.getBalisesDepart().size();
		this.plateau             = plateau;
		this.manches             = new ArrayList<Manche>();
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return la liste des joueurs de la partie */
	public List<Joueur> getJoueurs() { return this.joueurs; }

	/** @return le nombre de joueurs de la partie */
	public int getNbJoueurs() { return this.joueurs.size(); }

	/** @return le joueur dont c'est le tour */
	public Joueur getJoueurCourant() { return this.joueurs.get(this.indiceJoueurCourant); }

	/** @return le numéro de la manche courante */
	public int getNumeroManche() { return this.numeroManche; }

	/** @return le plateau de jeu */
	public Plateau getPlateau() { return this.plateau; }

	public int getNbManche() { return this.nbManche ; }

	/**
	 * @return la manche en cours (dernière de la liste), ou {@code null}
	 *         si aucune manche n'a démarré
	 */
	public Manche getMancheCourante()
	{
		if (this.manches.isEmpty())
			return null;

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
		this.numeroManche        = 0;
		this.indiceJoueurCourant = 0;
		this.manches.clear();

		this.nouvelleManche();
	}

	/**
	 * Crée une nouvelle manche avec une pioche reconstituée, l'ajoute à la
	 * partie et incrémente le numéro de manche.
	 *
	 * @return la manche nouvellement créée
	 */
	public Manche nouvelleManche()
	{
		/* Chaque manche repart d'une pioche complète et mélangée. */
		Pioche pioche = new Pioche( this.nbManche );
		pioche.reconstituer();

		this.numeroManche++;
		Manche manche = new Manche(this.numeroManche, pioche, this.nbManche);
		this.manches.add(manche);

		return manche;
	}

	/**
	 * Termine la manche courante : cumule et réinitialise le score de tous les
	 * joueurs, puis enchaîne sur une nouvelle manche si la partie continue.
	 */
	public void terminerManche()
	{
		for (Joueur joueur : this.joueurs)
		{
			joueur.ajouterScoreManche();
			joueur.reinitialiserManche();
		}

		if (!this.estTerminee())
			this.nouvelleManche();
		else
			this.partieTerminee = true;

	}

	/**
	 * Indique si la partie est terminée (cinq manches jouées).
	 *
	 * @return {@code true} si la partie est terminée
	 */
	public boolean estTerminee()
	{
		return this.partieTerminee || 
			(this.manches.size() >= this.nbManche && this.getMancheCourante().estTerminee());
	}
	/**
	 * Passe le tour au joueur suivant (de façon circulaire). En solo, le joueur
	 * courant reste le même.
	 */
	public void joueurSuivant()
	{
		this.indiceJoueurCourant++;

		/* Arrivé au bout de la liste, on revient au premier joueur. */
		if (this.indiceJoueurCourant >= this.joueurs.size())
			this.indiceJoueurCourant = 0;
	}

	/**
	 * Détermine le ou les gagnants de la partie. Comparaison dans l'ordre :
	 *
	 * le plus grand score total ;
	 * en cas d'égalité, le meilleur score réalisé sur une seule manche.
	 * 
	 * Si plusieurs joueurs restent à égalité parfaite, ils sont tous gagnants
	 * (victoire partagée, pas de tirage au sort). En solo, l'unique joueur est
	 * toujours renvoyé.
	 *
	 * @return la liste des joueurs gagnants (un seul en cas de vainqueur net,
	 *         plusieurs en cas d'égalité)
	 */
	public List<Joueur> getGagnants()
	{
		/* 1. Meilleur score total. */
		int meilleurScoreTotal = 0;
		for (Joueur joueur : this.joueurs)
			if (joueur.getScoreTotal() > meilleurScoreTotal)
				meilleurScoreTotal = joueur.getScoreTotal();

		/* Joueurs ayant ce meilleur score total. */
		List<Joueur> candidats = new ArrayList<Joueur>();
		for (Joueur joueur : this.joueurs)
			if (joueur.getScoreTotal() == meilleurScoreTotal)
				candidats.add(joueur);

		if (candidats.size() == 1)
			return candidats;

		/* 2. Départage : meilleur score sur une seule manche parmi les candidats. */
		int meilleurScoreManche = 0;
		for (Joueur joueur : candidats)
			if (joueur.getMeilleurScoreManche() > meilleurScoreManche)
				meilleurScoreManche = joueur.getMeilleurScoreManche();

		List<Joueur> gagnants = new ArrayList<Joueur>();
		for (Joueur joueur : candidats)
			if (joueur.getMeilleurScoreManche() == meilleurScoreManche)
				gagnants.add(joueur);

		/* S'il reste plusieurs gagnants : égalité parfaite, ils gagnent tous. */
		return gagnants;
	}

	/**
	 * Indique si la partie se termine sur une égalité (plusieurs gagnants).
	 *
	 * @return {@code true} si au moins deux joueurs sont gagnants
	 */
	public boolean estEgalite()
	{
		return this.getGagnants().size() > 1;
	}

	/**
	 * Retourne le score final d'un joueur donné.
	 *
	 * @param joueur le joueur dont on veut le score
	 * @return le score total du joueur
	 */
	public int getScoreFinal(Joueur joueur)
	{
		return joueur.getScoreTotal();
	}
}