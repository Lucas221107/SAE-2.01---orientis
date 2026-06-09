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
	}

	/**
	 * Crée et démarre une nouvelle manche.
	 *
	 * @return la manche nouvellement créée
	 */
	public Manche nouvelleManche()
	{
		return null;
	}

	/**
	 * Termine la manche courante et calcule les scores associés.
	 */
	public void terminerManche()
	{
	}

	/**
	 * Indique si la partie est terminée (cinq manches jouées).
	 *
	 * @return {@code true} si la partie est terminée
	 */
	public boolean estTerminee()
	{
		return false;
	}

	/**
	 * Retourne le score final cumulé sur l'ensemble des manches.
	 *
	 * @return le score final de la partie
	 */
	public int getScoreFinal()
	{
		return 0;
	}
}
