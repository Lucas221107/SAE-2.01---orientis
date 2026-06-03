package source.metier;

/**
 * Représente la balise de départ d'un joueur.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class BaliseDepart extends Balise
{
	/* Attribut */
	private String couleur;

	/* Constructeur */
	public BaliseDepart(int numero, TypeBiome biome, Position position, String couleur)
	{
		super(numero, biome, position);
		this.couleur = couleur;
	}

	/* Accesseur */
	public String getCouleur()
	{
		return this.couleur;
	}

	@Override
	public String toString()
	{
		return "BaliseDepart " + this.getNumero() + " (" + this.couleur + ")";
	}
}