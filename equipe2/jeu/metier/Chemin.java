package jeu.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente le chemin tracé par un joueur au cours d'une manche.
 * Un chemin est constitué d'une succession de {@link Segment} reliant des balises.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Chemin
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Liste ordonnée des segments composant le chemin. */
	private ArrayList<Segment> segments;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit un chemin vide, sans aucun segment.
	 */
	public Chemin()
	{
		this.segments = new ArrayList<Segment>();
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return la liste des segments composant le chemin */
	public List<Segment> getSegments() { return this.segments; }

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Ajoute un segment à l'extrémité du chemin.
	 *
	 * @param segment le segment à ajouter
	 */
	public void ajouterSegment(Segment segment)
	{
		this.segments.add(segment);
	}

	/**
	 * Retourne les balises situées aux deux extrémités du chemin.
	 * Une extrémité est une balise n'appartenant qu'à un seul segment
	 * (les balises internes appartiennent à deux segments).
	 *
	 * @return la liste des balises aux extrémités du chemin
	 *         (vide si le chemin ne contient aucun segment)
	 */
	public List<Balise> getExtremites()
	{
		List<Balise> extremites = new ArrayList<Balise>();

		for (Segment segment : this.segments)
		{
			Balise depart  = segment.getDepart() ;
			Balise arrivee = segment.getArrivee();

			/* Une balise présente une seule fois dans le chemin en est une extrémité. */
			if (compterOccurrences(depart) == 1 && !extremites.contains(depart))
				extremites.add(depart);

			if (compterOccurrences(arrivee) == 1 && !extremites.contains(arrivee))
				extremites.add(arrivee);
		}

		return extremites;
	}

	/**
	 * Indique si le chemin est vide (aucun segment tracé).
	 *
	 * @return {@code true} si le chemin ne contient aucun segment
	 */
	public boolean estVide()
	{
		return this.segments.isEmpty();
	}

	/**
	 * Indique si un segment donné croise un des segments déjà tracés.
	 *
	 * @param segment le segment à tester
	 * @return {@code true} si le segment croise le chemin
	 */
	public boolean croise(Segment segment)
	{
		for (Segment trace : this.segments)
		{
			if (trace.seCroisent(segment))
				return true;
		}
		return false;
	}

	/**
	 * Efface tous les segments du chemin.
	 */
	public void effacer()
	{
		this.segments.clear();
	}

	/**
	 * Compte le nombre de segments du chemin dont une extrémité est la balise donnée.
	 *
	 * @param balise la balise à dénombrer
	 * @return le nombre d'occurrences de la balise parmi les extrémités des segments
	 */
	private int compterOccurrences(Balise balise)
	{
		int nb = 0;

		for (Segment segment : this.segments)
		{
			if (segment.getDepart()  == balise) nb++;
			if (segment.getArrivee() == balise) nb++;
		}

		return nb;
	}
}