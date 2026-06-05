package sae.metier;

import java.awt.Color;

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

	private String nom ;
	private Color  couleur;

	private TypeBiome ( String nom, Color coul)
	{
		this.nom     = nom ;
		this.couleur = coul;
	}


	public String getNom    ()    { return this.nom    ; }
	public Color  getCouleur()    { return this.couleur; }
}