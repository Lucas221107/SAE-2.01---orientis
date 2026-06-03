package source.metier;

import java.util.ArrayList;

/**
 * Représente une balise positionnée sur le plateau d'Orientis.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Balise
{
	/* Attributs */
	private int numero;
	private TypeBiome biome;
	private boolean visitee;

	private Position position;
	private ArrayList<Balise> voisins;

	/* Constructeur */
	public Balise(int numero, TypeBiome biome, Position position)
	{
		this.numero   = numero;
		this.biome    = biome;
		this.visitee  = false;
		this.position = position;
		this.voisins  = new ArrayList<Balise>();
	}

	/* Accesseurs */
	public int getNumero()
	{
		return this.numero;
	}

	public TypeBiome getBiome()
	{
		return this.biome;
	}

	public boolean estVisitee()
	{
		return this.visitee;
	}

	public Position getPosition()
	{
		return this.position;
	}

	public ArrayList<Balise> getVoisins()
	{
		return this.voisins;
	}

	/* Modificateurs */
	public void setVisitee(boolean visitee)
	{
		this.visitee = visitee;
	}

	public void ajouterVoisin(Balise balise)
	{
		if (!this.voisins.contains(balise))
		{
			this.voisins.add(balise);
		}
	}

	public boolean estVoisin(Balise balise)
	{
		return this.voisins.contains(balise);
	}

	@Override
	public String toString()
	{
		return "Balise " + this.numero + " [" + this.biome + "]";
	}
}