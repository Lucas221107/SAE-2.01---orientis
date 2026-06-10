package jeu.metier;

import java.awt.Color;

/**
 * Représente les différents types de biomes du plateau d'Orientis,
 * ainsi que leur nom affiché et leur couleur associée.
 *
 * @author Groupe 2
 * @version 1.0
 */
public enum TypeBiome
{
	/** Forêt claire, teinte verte soutenue. */
	FORET     ("Forêt claire", new Color(  34, 139,  34) ),

	/** Vallée, teinte grise neutre. */
	VALLEE    ("Vallée"      , new Color(  85,  85,  85) ),

	/** Clairière, teinte vert pâle. */
	CLAIRIERE ("Clairière"   , new Color( 144, 238, 144) ),

	/** Lande, teinte brun rosé. */
	LANDE     ("Lande"       , new Color( 188, 143, 143) ),

	/** Montagne, teinte gris clair. */
	MONTAGNE  ("Montagne"    , new Color( 169, 169, 169) ),

	/** Marais, teinte vert foncé. */
	MARAIS    ("Marais"      , new Color(  47,  79,  47) ),

	/** Lac, teinte bleu ciel. */
	LAC       ("Lac"         , new Color(  64, 164, 223) ),

	/** Zone urbaine, teinte rouge brique. */
	URBAIN    ("Urbain"      , new Color( 169,  68,  66) ),

	/** Zone industrielle, teinte bleu acier. */
	INDUSTRIEL("Industriel"  , new Color( 100, 181, 246) ),

	/** Dune, teinte beige sableux. */
	DUNE      ("Dune"        , new Color( 210, 180, 140) );

	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Nom lisible du biome, affiché dans l'interface. */
	private String nom;

	/** Couleur AWT associée à ce biome, utilisée pour le rendu graphique. */
	private Color couleur;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit une constante de biome avec son nom affiché et sa couleur.
	 *
	 * @param nom  le nom lisible du biome
	 * @param coul la couleur AWT associée
	 */
	private TypeBiome ( String nom, Color coul )
	{
		this.nom     = nom ;
		this.couleur = coul;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/**
	 * Retourne le nom lisible de ce biome.
	 *
	 * @return le nom du biome (ex. {@code "Forêt claire"})
	 */
	public String getNom    () { return this.nom    ; }

	/**
	 * Retourne la couleur AWT associée à ce biome.
	 *
	 * @return la couleur du biome
	 */
	public Color  getCouleur() { return this.couleur; }

	/**
	 * Recherche le biome correspondant à une couleur donnée.
	 * Compare la couleur passée en paramètre avec celle de chaque constante.
	 *
	 * @param c la couleur à rechercher
	 * @return le {@code TypeBiome} dont la couleur correspond à {@code c},
	 *         ou {@code null} si aucune correspondance n'est trouvée
	 */
	public static TypeBiome toColors ( Color c )
	{
		// Parcourt toutes les constantes de l'enum
		for ( TypeBiome biome : TypeBiome.values() )
		{
			// Retourne le biome dès qu'une couleur identique est trouvée
			if ( biome.getCouleur().equals( c ) )
				return biome;
		}
		// Aucun biome ne correspond à cette couleur
		return null;
	}

	/**
	 * Recherche le biome correspondant à un nom donné.
	 * Compare le nom passé en paramètre avec celui de chaque constante.
	 *
	 * @param nom le nom à rechercher (ex. {@code "Vallée"})
	 * @return le {@code TypeBiome} dont le nom correspond à {@code nom},
	 *         ou {@code null} si aucune correspondance n'est trouvée
	 */
	public static TypeBiome toNom ( String nom )
	{
		// Parcourt toutes les constantes de l'enum
		for ( TypeBiome biome : TypeBiome.values() )
		{
			// Retourne le biome dès qu'un nom identique est trouvé
			if ( biome.getNom().equals( nom ) )
				return biome;
		}
		// Aucun biome ne correspond à ce nom
		return null;
	}
}