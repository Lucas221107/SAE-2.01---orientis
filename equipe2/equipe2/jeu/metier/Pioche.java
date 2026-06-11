package jeu.metier;

import java.util.ArrayList;
import java.util.Collections;

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
		Collections.shuffle(this.fanions);
	}

	/**
	 * Pioche et retire le fanion situé au sommet de la pioche.
	 *
	 * @return le fanion pioché, ou {@code null} si la pioche est vide
	 */
	public Fanion piocher()
	{
		if (this.estVide())
			return null;

		/* On retire le dernier fanion : la pioche etant melangee, le choix est equivalent. */
		return this.fanions.remove(this.fanions.size() - 1);
	}

	/**
	 * Indique si la pioche est vide.
	 *
	 * @return {@code true} si la pioche ne contient plus de fanion
	 */
	public boolean estVide()
	{
		return this.fanions.isEmpty();
	}

	/**
	 * Reconstitue la pioche avec l'ensemble complet des fanions (5 clairs,
	 * 5 foncés numérotés de 1 à 5, plus 2 jokers de numéro 0 : un clair et un
	 * foncé), puis la mélange.
	 */
	public Pioche reconstituer()
	{
		this.fanions.clear();

		/* 5 fanions clairs et 5 fanions fonces, numerotes de 1 a 5. */
		for (int cpt = 1; cpt <= 5; cpt++)
		{
			this.fanions.add(new Fanion(cpt, false)); // clair
			this.fanions.add(new Fanion(cpt, true )); // fonce
		}

		/* 2 jokers (numero 0) : un clair et un fonce. */
		this.fanions.add(new Fanion(0, false));
		this.fanions.add(new Fanion(0, true ));

		return this.melanger();
	}
}