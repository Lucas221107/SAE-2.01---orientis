package sae.metier;

import java.util.ArrayList;

/**
 * Représente une balise positionnée sur le plateau d'Orientis.
 *
 * @author Groupe 2
 * @version 1.0
 */

public class Balise
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private int               numero   ;
	private TypeBiome         biome    ;
	private boolean           visitee  ;

	private Position          position ;
	private ArrayList<Balise> voisins  ;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public Balise(int numero, TypeBiome biome, Position position)
	{
		this.numero   = numero                  ;
		this.biome    = biome                   ;
		this.position = position                ;
		this.visitee  = false                   ;
		this.voisins  = new ArrayList<Balise>() ;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public int               getNumero  ()              { return this.numero                  ; }
	public TypeBiome         getBiome   ()              { return this.biome                   ; }
	public Position          getPosition()              { return this.position                ; }
	public ArrayList<Balise> getVoisins ()              { return this.voisins                 ; }
	public boolean           estVisitee ()	            { return this.visitee                 ; }
	public boolean           estVoisin  (Balise balise)	{ return this.voisins.contains(balise);	}

	/* - - - - - - - - - - - - - */
	/* Modificateurs             */
	/* - - - - - - - - - - - - - */
	public void setVisitee    (boolean visitee)  {  this.visitee = visitee ; }
	
	public void ajouterVoisin (Balise  balise)
	{
		if ( ! this.voisins.contains(balise) )
		{
			this.voisins.add(balise);
		}
	}


	public String toString()
	{
		char premiereLettreColeur = Character.toUpperCase(this.biome.getCouleur().toString().charAt(0));
		
		return "" + premiereLettreColeur + this.numero;
	}
}