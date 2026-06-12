package sae.metier;



/** Represente la balise de depart d'un joueur, identifiee par une couleur.
  * Herite de Balise et sert de point de depart au chemin trace par le joueur.
  *
  * @author Groupe 2
  * @version 1.0
  */
public class BaliseDepart extends Balise
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private String couleur;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public BaliseDepart ( int numero, TypeBiome biome, Position position, String couleur )
	{
		super ( numero, biome, position );
		this.couleur = couleur           ;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public String getCouleur () { return this.couleur; }


    public String toString ()
	{
		char premiereLettreJoueur = Character.toUpperCase(this.couleur.charAt(0));

		return "" + this.getBiome().name().charAt(0) + this.getNumero() + premiereLettreJoueur;
	}

}