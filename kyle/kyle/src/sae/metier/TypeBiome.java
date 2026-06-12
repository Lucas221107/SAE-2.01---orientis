package sae.metier;

import java.awt.Color;

/** Represente les différents type de biomes ainsi que ca couleur 
  *
  * @author Groupe 2
  * @version 1.0
  */

public enum TypeBiome
{
	FORET     ("Forêt claire", new Color(  34, 139,  34) ),
	VALLEE    ("Vallée"      , new Color(  85,  85,  85) ),
	CLAIRIERE ("Clairière"   , new Color( 144, 238, 144) ),
	LANDE     ("Lande"       , new Color( 188, 143, 143) ),
	MONTAGNE  ("Montagne"    , new Color(255, 255, 255 ) ),
	MARAIS    ("Marais"      , new Color(  47,  79,  47) ),
	LAC       ("Lac"         , new Color(  64, 164, 223) ),
	URBAIN    ("Urbain"      , new Color( 169,  68,  66) ),
	INDUSTRIEL("Industriel"  , new Color( 169, 169, 169) ),
	DUNE      ("Dune"        , new Color( 210, 180, 140) );

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