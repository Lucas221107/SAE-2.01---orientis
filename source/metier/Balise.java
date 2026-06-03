package src.metier;

/** Represente une balise positionnee sur le plateau d'Orientis.
  * Une balise appartient a un biome et peut etre visitee par un joueur.
  *
  * @author Groupe 2
  * @version 1.0
  */
public class Balise
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private int       numero;
	private TypeBiome biome;
	private boolean   visitee;

	// Attribut traduisant la composition
	private Position position;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public Balise ( int numero, TypeBiome biome, Position position )
	{
		this.numero   = numero;
		this.biome    = biome;
		this.visitee  = false;
		this.position = position;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public int       getNumero   () { return this.numero;   }
	public TypeBiome getBiome    () { return this.biome;    }
	public boolean   estVisitee  () { return this.visitee;  }
	public Position  getPosition () { return this.position; }

	/* - - - - - - - - - - - - - */
	/* Modificateur              */
	/* - - - - - - - - - - - - - */
	public void setVisitee ( boolean visitee )
	{
		this.visitee = visitee;
	}

	public String toString ()
	{
		return "Balise " + this.numero + " [" + this.biome + "]";
	}
}
