package jeu.metier;

import java.util.ArrayList;

/**
 * Représente la pioche de fanions d'une manche. Elle contient les fanions
 * clairs, foncés et jokers mélangés, dans lesquels les joueurs piochent à
 * chaque tour.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Pioche
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Liste des fanions encore présents dans la pioche. */
	private ArrayList<Fanion> fanions;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une pioche vide. Les fanions sont ajoutés ensuite via
	 * {@link #reconstituer()}.
	 */
	public Pioche()
	{
		this.fanions = new ArrayList<Fanion>();
	}

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Mélange aléatoirement les fanions de la pioche.
	 */
	public void melanger()
	{
	}

	/**
	 * Pioche et retire le fanion situé au sommet de la pioche.
	 *
	 * @return le fanion pioché, ou {@code null} si la pioche est vide
	 */
	public Fanion piocher()
	{
		return null;
	}

	/**
	 * Indique si la pioche est vide.
	 *
	 * @return {@code true} si la pioche ne contient plus de fanion
	 */
	public boolean estVide()
	{
		return false;
	}

	/**
	 * Reconstitue la pioche avec l'ensemble complet des fanions, puis la mélange.
	 */
	public void reconstituer()
	{
	}
}
