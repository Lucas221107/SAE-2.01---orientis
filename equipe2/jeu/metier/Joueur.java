package jeu.metier;

import java.util.ArrayList;

/**
 * Représente un joueur (orienteur) d'Orientis. Le joueur trace un chemin à
 * partir de sa balise de départ, capture des balises et cumule un score.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Joueur
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Nom du joueur. */
	private String nom;

	/** Couleur attribuée au joueur. */
	private String couleur;

	/** Score total cumulé sur l'ensemble des manches. */
	private int scoreTotal;

	/** Balise de départ du joueur. */
	private BaliseDepart baliseDepart;

	/** Chemin tracé par le joueur durant la manche courante. */
	private Chemin chemin;

	/** Balises capturées par le joueur durant la manche courante. */
	private ArrayList<Balise> balisesCapturees;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit un joueur avec son nom, sa couleur et sa balise de départ.
	 * Le score est initialisé à zéro, le chemin est vide et aucune balise
	 * n'est encore capturée.
	 *
	 * @param nom          nom du joueur
	 * @param couleur      couleur du joueur
	 * @param baliseDepart balise de départ du joueur
	 */
	public Joueur(String nom, String couleur, BaliseDepart baliseDepart)
	{
		this.nom              = nom                    ;
		this.couleur          = couleur                ;
		this.scoreTotal       = 0                      ;
		this.baliseDepart     = baliseDepart           ;
		this.chemin           = new Chemin()           ;
		this.balisesCapturees = new ArrayList<Balise>();
	}

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Capture une balise en traçant un segment depuis une balise du chemin.
	 *
	 * @param depart  balise de départ du segment (extrémité du chemin)
	 * @param arrivee balise capturée
	 */
	public void capturer(Balise depart, Balise arrivee)
	{
	}

	/**
	 * Indique si le joueur peut jouer avec le fanion donné.
	 *
	 * @param fanion le fanion pioché
	 * @return {@code true} si le joueur peut capturer une balise valide
	 */
	public boolean peutJouer(Fanion fanion)
	{
		return false;
	}

	/**
	 * Calcule le score réalisé durant la manche courante.
	 *
	 * @return le score de la manche
	 */
	public int calculerScoreManche()
	{
		return 0;
	}

	/**
	 * Ajoute le score de la manche courante au score total du joueur.
	 */
	public void ajouterScoreManche()
	{
	}

	/**
	 * Réinitialise l'état du joueur pour une nouvelle manche
	 * (chemin effacé, balises capturées vidées).
	 */
	public void reinitialiserManche()
	{
	}
}
