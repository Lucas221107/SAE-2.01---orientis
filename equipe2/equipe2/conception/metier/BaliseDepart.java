package conception.metier;

/**
 * Représente la balise de départ d'un joueur, identifiée par une couleur.
 * Hérite de {@link Balise} et sert de point de départ au chemin tracé par le joueur.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class BaliseDepart extends Balise
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Couleur du joueur auquel appartient cette balise de départ. */
	private String couleur;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une balise de départ avec les informations de base d'une balise
	 * et la couleur du joueur associé.
	 *
	 * @param numero   numéro unique de la balise
	 * @param biome    type de biome associé
	 * @param position position sur le plateau
	 * @param couleur  couleur du joueur propriétaire de cette balise de départ
	 */
	public BaliseDepart(int numero, TypeBiome biome, Position position, String couleur)
	{
		super(numero, biome, position);
		this.couleur = couleur;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return la couleur du joueur associé à cette balise de départ */
	public String getCouleur() { return this.couleur; }

	/**
	 * Retourne une représentation textuelle de la balise de départ sous la forme
	 * [initiale du biome][numéro][initiale du joueur].
	 * Exemple : {@code "F2R"} pour un biome Forêt, numéro 2, joueur Rouge.
	 *
	 * @return chaîne de la forme [Biome][Numéro][Joueur]
	 */
	public String toString()
	{
		char premiereLettreJoueur = Character.toUpperCase(this.couleur.charAt(0));
		return "" + this.getBiome().name().charAt(0) + this.getNumero() + premiereLettreJoueur;
	}
}