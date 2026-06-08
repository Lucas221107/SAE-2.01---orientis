package sae.metier;

import java.awt.Color;

/** Represente les différents type de biomes ainsi que ca couleur 
  *
  * @author Groupe 2
  * @version 1.0
  */

public enum TypeBiome
{
	FORET     ("Forêt claire", Color.GREEN     ),
	VALLEE    ("Vallée"      , Color.DARK_GRAY ),
	CLAIRIERE ("Clairière"   , Color.MAGENTA   ),
	LANDE     ("Lande"       , Color.PINK      ),
	MONTAGNE  ("Montagne"    , Color.LIGHT_GRAY),
	MARAIS    ("Marais"      , Color.BLACK     ),
	LAC       ("Lac"         , Color.BLUE      ),
	URBAIN    ("Urbain"      , Color.RED       ),
	INDUSTRIEL("Industriel"  , Color.CYAN      ),
	DUNE      ("Dune"        , Color.ORANGE    );

	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private String nom    ;
	private Color  couleur;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	private TypeBiome ( String nom, Color coul)
	{
		this.nom     = nom  ;
		this.couleur = coul ;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public String getNom    ()    { return this.nom    ; }
	public Color  getCouleur()    { return this.couleur; }


	/* Méthode permettant à partir d'une couleur de retrouver le biome correspondant */
	public static TypeBiome toColors ( Color c )
	{
		for ( TypeBiome biome : TypeBiome.values() )
		{
			if ( biome.getCouleur().equals( c ) )
				return biome;
		}

		return null;
	}

	/* Méthode permettant à partir d'un nom de retrouver le biome correspondant*/
	public static TypeBiome toNom ( String nom )
	{
		for ( TypeBiome biome : TypeBiome.values() )
		{
			if ( biome.getNom().equals( nom ) )
				return biome;
		}
		return null;
	}
}