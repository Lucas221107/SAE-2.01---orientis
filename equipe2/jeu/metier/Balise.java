package jeu.metier;

import java.util.ArrayList;

/**
 * Représente une balise positionnée sur le plateau d'Orientis.
 * <p>
 * Le plateau étant partagé par tous les joueurs, l'état « visitée » n'est pas
 * porté par la balise mais par chaque {@link Joueur} : une balise est visitée
 * par un joueur si elle figure parmi ses balises capturées.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Balise
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Numéro unique identifiant la balise. */
	private int numero;

	/** Biome associé à la balise (détermine sa couleur et son type de terrain). */
	private TypeBiome biome;

	/** Position de la balise sur le plateau (ligne, colonne). */
	private Position position;

	/** Liste des balises directement adjacentes à celle-ci. */
	private ArrayList<Balise> voisins;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une balise avec son numéro, son biome et sa position.
	 * La balise est initialement sans voisins.
	 *
	 * @param numero   numéro unique de la balise
	 * @param biome    type de biome associé
	 * @param position position sur le plateau
	 */
	public Balise(int numero, TypeBiome biome, Position position)
	{
		this.numero   = numero                  ;
		this.biome    = biome                   ;
		this.position = position                ;
		this.voisins  = new ArrayList<Balise>() ;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return le numéro de la balise */
	public int               getNumero  ()              { return this.numero                  ; }

	/** @return le biome de la balise */
	public TypeBiome         getBiome   ()              { return this.biome                   ; }

	/** @return la position de la balise sur le plateau */
	public Position          getPosition()              { return this.position                ; }

	/** @return la liste des balises voisines */
	public ArrayList<Balise> getVoisins ()              { return this.voisins                 ; }

	/**
	 * Vérifie si une balise donnée est voisine de celle-ci.
	 *
	 * @param balise la balise à tester
	 * @return {@code true} si la balise est dans la liste des voisins
	 */
	public boolean           estVoisin  (Balise balise) { return this.voisins.contains(balise); }

	/* - - - - - - - - - - - - - */
	/* Modificateurs             */
	/* - - - - - - - - - - - - - */

	/**
	 * Ajoute une balise voisine si elle n'est pas déjà présente dans la liste.
	 *
	 * @param balise la balise à ajouter comme voisine
	 */
	public void ajouterVoisin(Balise balise)
	{
		if (!this.voisins.contains(balise))
		{
			this.voisins.add(balise);
		}
	}

	/**
	 * Retourne une représentation textuelle de la balise sous la forme
	 * de la première lettre (en majuscule) de la couleur du biome suivie du numéro.
	 * Exemple : {@code "V3"} pour une balise verte numéro 3.
	 *
	 * @return chaîne de la forme [Lettre][Numéro]
	 */
	public String toString()
	{
		char premiereLettreCouleur = Character.toUpperCase(this.biome.getCouleur().toString().charAt(0));

		return "" + premiereLettreCouleur + this.numero;
	}
}