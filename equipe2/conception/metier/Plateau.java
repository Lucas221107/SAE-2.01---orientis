package conception.metier;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Plateau de l'application de conception d'Orientis.
 * Construit un plateau prédéfini (biomes, balises, balises de départ),
 * calcule les liaisons entre balises (rose des vents) et l'exporte dans un
 * fichier .data.
 *
 * @author Groupe 2
 * @version 1.0
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

	/* - - - - - - - - - - - - - */
	/* Modificateurs             */
	/* - - - - - - - - - - - - - */

	/** @param ligne nouveau nombre de lignes */
	public void setLigne(int ligne)             { this.nbLignes       = ligne  ; }

	/** @param colonne nouveau nombre de colonnes */
	public void setColonne(int colonne)         { this.nbColonnes     = colonne; }

	/** @param tab tableau des numéros de balise par case */
	public void setTabNumBalise(int[][] tab)    { this.tabNumBalise   = tab    ; }

	/** @param tab tableau des noms de biome par case */
	public void setTabStringBiome(String[][] tab) { this.tabStringBiome = tab  ; }

	/** @param tab tableau booléen indiquant les cases de départ */
	public void setTabDepart(boolean[][] tab)   { this.tabDepart      = tab    ; }

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

				/* Instancie une balise normale si la case n'est pas vide */
				if (numBalise != 0)
				{
					this.setBalise(lig, col, new Balise(numBalise, biome, new Position(lig, col)));
				}

				/* Remplace par une balise de départ si la case est marquée comme telle */
				if (this.tabDepart != null && this.tabDepart[lig][col])
				{
					this.setBalise(lig, col, new BaliseDepart(numBalise, biome, new Position(lig, col), "Rouge"));
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
		int[][] directions =
		{
			{ -1,  0 }, // Nord
			{ -1,  1 }, // Nord-Est
			{  0,  1 }, // Est
			{  1,  1 }, // Sud-Est
			{  1,  0 }, // Sud
			{  1, -1 }, // Sud-Ouest
			{  0, -1 }, // Ouest
			{ -1, -1 }  // Nord-Ouest
		};

		for (int lig = 0; lig < this.nbLignes; lig++)
		{
			for (int col = 0; col < this.nbColonnes; col++)
			{
				Balise courante = this.balises[lig][col];
				if (courante != null)
				{
					for (int[] dir : directions)
					{
						int     ligVoisin    = lig + dir[0];
						int     colVoisin    = col + dir[1];
						boolean trouveVoisin = false       ;

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
								ligVoisin += dir[0];
								colVoisin += dir[1];
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Sauvegarde le plateau dans un fichier {@code .data} structuré en quatre blocs :
	 * <ul>
	 *   <li>{@code GRILLE} – dimensions et compteurs</li>
	 *   <li>{@code BIOMES} – biome de chaque case</li>
	 *   <li>{@code BALISES} – position, numéro, biome et type de chaque balise</li>
	 *   <li>{@code LIAISONS} – liste des voisins de chaque balise</li>
	 * </ul>
	 *
	 * @param nomFichier chemin du fichier de destination
	 * @return {@code true} si l'écriture a réussi, {@code false} en cas d'erreur d'E/S
	 */
	public boolean sauvegarder(String nomFichier)
	{
		try
		{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), "UTF8"));

			/* En-tête : dimensions et nombres de la grille */
			pw.println("GRILLE " + this.nbLignes + " " + this.nbColonnes + " " + this.nbBiome + " " + this.nbBalise);

			/* Bloc biomes : le biome de chaque case (même les cases sans balise) */
			pw.println("BIOMES");
			if (this.tabStringBiome != null)
			{
				for (int lig = 0; lig < this.nbLignes; lig++)
				{
					for (int col = 0; col < this.nbColonnes; col++)
					{
						String nomBiome = this.tabStringBiome[lig][col];
						if (nomBiome != null)
						{
							TypeBiome biome = TypeBiome.toNom(nomBiome);
							if (biome != null)
								pw.println(lig + " " + col + " " + biome.name());
						}
					}
				}
			}

			/* Bloc balises : ligne colonne numero biome type [couleur si départ] */
			pw.println("BALISES");
			for (int lig = 0; lig < this.nbLignes; lig++)
			{
				for (int col = 0; col < this.nbColonnes; col++)
				{
					Balise b = this.balises[lig][col];
					if (b != null)
					{
						String ligneFichier = lig + " " + col + " " + b.getNumero() + " " + b.getBiome().name();

						if (b instanceof BaliseDepart)
							ligneFichier += " DEPART " + ((BaliseDepart) b).getCouleur();
						else
							ligneFichier += " NORMALE";

						pw.println(ligneFichier);
					}
				}
			}

			/* Bloc liaisons : ligne,colonne : voisin1,voisin2 ... */
			pw.println("LIAISONS");
			for (int lig = 0; lig < this.nbLignes; lig++)
			{
				for (int col = 0; col < this.nbColonnes; col++)
				{
					Balise b = this.balises[lig][col];
					if (b != null)
					{
						String ligneFichier = lig + "," + col + " :";
						for (Balise voisin : b.getVoisins())
							ligneFichier += " " + voisin.getPosition().getLigne() + "," + voisin.getPosition().getColonne();

						pw.println(ligneFichier);
					}
				}
			}

			pw.close();
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Erreur lors de la sauvegarde du plateau : " + e.getMessage());
			return false;
		}
	}

	/**
	 * Retourne une représentation textuelle du plateau sous forme de grille ASCII.
	 * Chaque cellule affiche le résultat de {@link Balise#toString()} ou est vide.
	 * La largeur des cellules s'adapte à la représentation la plus longue.
	 *
	 * @return la grille formatée en chaîne de caractères
	 */
	public String toString()
	{
		/* Largeur maximale parmi toutes les représentations de balises */
		int largeur = 1;
		for (int lig = 0; lig < this.nbLignes; lig++)
			for (int col = 0; col < this.nbColonnes; col++)
			{
				Balise b = this.balises[lig][col];
				if (b != null && b.toString().length() > largeur)
					largeur = b.toString().length();
			}

		String sep = ("+-" + "-".repeat(largeur) + "-").repeat(this.nbColonnes) + "+\n";
		String s   = sep;

		for (int lig = 0; lig < this.nbLignes; lig++)
		{
			for (int col = 0; col < this.nbColonnes; col++)
			{
				Balise b       = this.balises[lig][col]        ;
				String contenu = (b != null) ? b.toString() : "";
				s += String.format("| %-" + largeur + "s ", contenu);
			}
			s += "|\n" + sep;
		}

		return s;
	}
}