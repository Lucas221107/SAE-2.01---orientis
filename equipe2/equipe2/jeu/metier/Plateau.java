package jeu.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Plateau de l'application de conception d'Orientis.
 * Construit un plateau prédéfini (biomes, balises, balises de départ),
 * calcule les liaisons entre balises (rose des vents).
 *
 * @author Groupe 2
 * @version 1.1
 */
public class Plateau
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Nombre de lignes de la grille. */
	private int nbLignes;

	/** Nombre de colonnes de la grille. */
	private int nbColonnes;

	/** Nombre total de balises sur le plateau. */
	private int nbBalise;

	/** Nombre de biomes distincts présents sur le plateau. */
	private int nbBiome;

	/** Tableau des numéros de balise pour chaque case de la grille (0 = case vide). */
	private int[][] tabNumBalise;

	/** Tableau des noms de biome pour chaque case de la grille. */
	private String[][] tabStringBiome;

	/** Tableau indiquant si une case est une balise de départ. */
	private boolean[][] tabDepart;

	/** Grille principale contenant les objets {@link Balise} positionnés sur le plateau. */
	private Balise[][] balises;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit un plateau vide avec les dimensions et compteurs donnés.
	 * Les tableaux de configuration (biomes, numéros, départs) doivent être
	 * fournis ensuite via les modificateurs avant d'appeler {@link #genererPlateau()}.
	 *
	 * @param nbLignes   nombre de lignes de la grille
	 * @param nbColonnes nombre de colonnes de la grille
	 * @param nbBiome    nombre de biomes distincts
	 * @param nbBalise   nombre total de balises
	 */
	public Plateau(int nbLignes, int nbColonnes, int nbBiome, int nbBalise)
	{
		this.nbLignes       = nbLignes                         ;
		this.nbColonnes     = nbColonnes                       ;
		this.nbBiome        = nbBiome                          ;
		this.nbBalise       = nbBalise                         ;
		this.balises        = new Balise[nbLignes][nbColonnes] ;
		this.tabNumBalise   = null                             ;
		this.tabStringBiome = null                             ;
		this.tabDepart      = null                             ;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return le nombre de lignes de la grille */
	public int getNbLignes()   { return this.nbLignes  ; }

	/** @return le nombre de colonnes de la grille */
	public int getNbColonnes() { return this.nbColonnes; }

	/** @return le nombre de biomes distincts */
	public int getNbBiomes()   { return this.nbBiome   ; }

	/** @return le nombre total de balises */
	public int getNbBalises()  { return this.nbBalise  ; }

	/**
	 * Retourne la balise à la position donnée, ou {@code null} si la case est vide
	 * ou si les coordonnées sont hors limites.
	 *
	 * @param ligne   indice de ligne (0-indexé)
	 * @param colonne indice de colonne (0-indexé)
	 * @return la {@link Balise} à cette position, ou {@code null}
	 */
	public Balise getBalise(int ligne, int colonne)
	{
		if (this.balises == null || this.balises.length == 0)
			return null;

		if (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes)
			return this.balises[ligne][colonne];

		return null;
	}

	/**
	 * Retourne la liste de toutes les balises de départ présentes sur le plateau.
	 *
	 * @return liste des {@link BaliseDepart}, vide si aucune n'existe
	 */
	public List<BaliseDepart> getBalisesDepart()
	{
		List<BaliseDepart> resultat = new ArrayList<>();

		for (int lig = 0; lig < this.nbLignes; lig++)
		{
			for (int col = 0; col < this.nbColonnes; col++)
			{
				if (this.balises[lig][col] instanceof BaliseDepart)
				{
					resultat.add((BaliseDepart) this.balises[lig][col]);
				}
			}
		}

		return resultat;
	}

	/* - - - - - - - - - - - - - */
	/* Modificateurs             */
	/* - - - - - - - - - - - - - */

	/** @param ligne nouveau nombre de lignes */
	public void setLigne(int ligne)               { this.nbLignes       = ligne  ; }

	/** @param colonne nouveau nombre de colonnes */
	public void setColonne(int colonne)           { this.nbColonnes     = colonne; }

	/** @param tab tableau des numéros de balise par case */
	public void setTabNumBalise(int[][] tab)      { this.tabNumBalise   = tab    ; }

	/** @param tab tableau des noms de biome par case */
	public void setTabStringBiome(String[][] tab) { this.tabStringBiome = tab    ; }

	/** @param tab tableau booléen indiquant les cases de départ */
	public void setTabDepart(boolean[][] tab)     { this.tabDepart      = tab    ; }

	/**
	 * Place une balise à la position donnée dans la grille.
	 * Ignore la demande si les coordonnées sont hors limites.
	 *
	 * @param ligne   indice de ligne
	 * @param colonne indice de colonne
	 * @param balise  balise à placer
	 */
	public void setBalise(int ligne, int colonne, Balise balise)
	{
		if (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes)
		{
			this.balises[ligne][colonne] = balise;
		}
	}

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Génère le plateau à partir des tableaux de configuration :
	 * instancie les {@link Balise} et {@link BaliseDepart} à leurs positions,
	 * puis calcule les liaisons entre voisins.
	 */
	public void genererPlateau()
	{
		TypeBiome biome     = null;
		int       numBalise = 0   ;

		this.balises = new Balise[this.nbLignes][this.nbColonnes];

		for (int lig = 0; lig < this.nbLignes; lig++)
		{
			for (int col = 0; col < this.nbColonnes; col++)
			{
				biome     = TypeBiome.toNom(this.tabStringBiome[lig][col]);
				numBalise = this.tabNumBalise[lig][col]                   ;

				/* Instancie une balise (normale ou de départ) si la case n'est pas vide */
				if (numBalise != 0)
				{
					if (this.tabDepart != null && this.tabDepart[lig][col])
					{
						/* Balise de départ */
						this.setBalise(lig, col, new BaliseDepart(numBalise, biome, new Position(lig, col), "Rouge"));
					}
					else
					{
						/* Balise normale */
						this.setBalise(lig, col, new Balise(numBalise, biome, new Position(lig, col)));
					}
				}
			}
		}

		this.calculerLiaisons();
	}

	/**
	 * Calcule, pour chaque balise, la liste de ses voisines selon les huit
	 * directions de la rose des vents. Pour chaque direction, on avance case
	 * par case jusqu'à trouver une balise ou sortir de la grille.
	 */
	public void calculerLiaisons()
	{
		for (int lig = 0; lig < this.nbLignes; lig++)
		{
			for (int col = 0; col < this.nbColonnes; col++)
			{
				Balise courante = this.balises[lig][col];
				if (courante != null)
				{
					for (Direction dir : Direction.values())
					{
						int     ligVoisin    = lig + dir.getDLigne()  ;
						int     colVoisin    = col + dir.getDColonne() ;
						boolean trouveVoisin = false                   ;

						/* Avance dans la direction jusqu'à trouver une balise ou sortir */
						while (!trouveVoisin
								&& ligVoisin >= 0 && ligVoisin < this.nbLignes
								&& colVoisin >= 0 && colVoisin < this.nbColonnes)
						{
							Balise voisin = this.balises[ligVoisin][colVoisin];
							if (voisin != null)
							{
								courante.ajouterVoisin(voisin);
								voisin.ajouterVoisin(courante);
								trouveVoisin = true;
							}
							else
							{
								ligVoisin += dir.getDLigne()  ;
								colVoisin += dir.getDColonne() ;
							}
						}
					}
				}
			}
		}
	}
}