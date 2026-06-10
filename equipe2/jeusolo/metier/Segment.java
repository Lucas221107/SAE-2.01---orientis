package jeusolo.metier;

/**
 * Représente un segment de chemin reliant directement deux balises
 * (une balise de départ et une balise d'arrivée).
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Segment
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Balise située à l'origine du segment. */
	private Balise depart;

	/** Balise située à l'extrémité du segment. */
	private Balise arrivee;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit un segment reliant deux balises.
	 *
	 * @param depart  balise de départ du segment
	 * @param arrivee balise d'arrivée du segment
	 */
	public Segment(Balise depart, Balise arrivee)
	{
		this.depart  = depart ;
		this.arrivee = arrivee;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return la balise de départ du segment */
	public Balise getDepart()  { return this.depart ; }

	/** @return la balise d'arrivée du segment */
	public Balise getArrivee() { return this.arrivee; }

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Indique si ce segment en croise un autre.
	 * <p>
	 * Deux segments qui partagent une balise (extrémité commune) ne sont pas
	 * considérés comme se croisant : ils se rejoignent, ce qui est autorisé
	 * pour prolonger un chemin. Sinon, on applique le test géométrique
	 * d'intersection de deux segments basé sur l'orientation des triplets de
	 * points (produit vectoriel), en incluant les cas de colinéarité.
	 *
	 * @param autre l'autre segment à tester
	 * @return {@code true} si les deux segments se croisent
	 */
	public boolean seCroisent(Segment autre)
	{
		Position p1 = this.depart  .getPosition();
		Position p2 = this.arrivee .getPosition();
		Position p3 = autre.depart .getPosition();
		Position p4 = autre.arrivee.getPosition();

		/* Extrémité commune : les segments se rejoignent, ils ne se croisent pas. */
		if (p1.equals(p3) || p1.equals(p4) || p2.equals(p3) || p2.equals(p4))
			return false;

		int o1 = orientation(p1, p2, p3);
		int o2 = orientation(p1, p2, p4);
		int o3 = orientation(p3, p4, p1);
		int o4 = orientation(p3, p4, p2);

		/* Cas général : les deux segments se croisent franchement. */
		if (o1 != o2 && o3 != o4)
			return true;

		/* Cas particuliers de colinéarité : un point se trouve sur l'autre segment. */
		if (o1 == 0 && surSegment(p1, p3, p2)) return true;
		if (o2 == 0 && surSegment(p1, p4, p2)) return true;
		if (o3 == 0 && surSegment(p3, p1, p4)) return true;
		if (o4 == 0 && surSegment(p3, p2, p4)) return true;

		return false;
	}

	/**
	 * Calcule l'orientation du triplet de points (a, b, c) à partir du signe
	 * du produit vectoriel.
	 *
	 * @param a premier point
	 * @param b deuxième point
	 * @param c troisième point
	 * @return {@code 1} si le sens est horaire, {@code -1} si anti-horaire,
	 *         {@code 0} si les trois points sont colinéaires
	 */
	private static int orientation(Position a, Position b, Position c)
	{
		int val = (b.getLigne()   - a.getLigne()  ) * (c.getColonne() - a.getColonne())
		        - (b.getColonne() - a.getColonne()) * (c.getLigne()   - a.getLigne()  );

		if (val > 0) return  1;
		if (val < 0) return -1;
		return 0;
	}

	/**
	 * Vérifie, pour trois points colinéaires (a, b, c), si le point {@code b}
	 * est situé sur le segment [a ; c].
	 *
	 * @param a première extrémité du segment
	 * @param b point à tester
	 * @param c seconde extrémité du segment
	 * @return {@code true} si {@code b} appartient au segment [a ; c]
	 */
	private static boolean surSegment(Position a, Position b, Position c)
	{
		return Math.min(a.getLigne()  , c.getLigne()  ) <= b.getLigne()
		    && b.getLigne()   <= Math.max(a.getLigne()  , c.getLigne()  )
		    && Math.min(a.getColonne(), c.getColonne()) <= b.getColonne()
		    && b.getColonne() <= Math.max(a.getColonne(), c.getColonne());
	}

	/**
	 * Retourne une représentation textuelle du segment sous la forme
	 * {@code "depart -> arrivee"}.
	 *
	 * @return chaîne représentant le segment
	 */
	public String toString()
	{
		return this.depart + " -> " + this.arrivee;
	}
}
