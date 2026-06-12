package sae.metier;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/** Plateau de l'application de conception d'Orientis.
  * Construit un plateau predefini (biomes, balises, balises de depart),
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
	private int          nbLignes       ;
	private int          nbColonnes     ;
	private int 		 nbBalise       ; 
	private int 		 nbBiome        ; 
	private int    [][]  tabNumBalise   ;
	private String [][]  tabStringBiome ;
	private boolean[][]  tabDepart      ;

	// Attribut traduisant l'association vers les balises (grille)
	private Balise [][] balises         ;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public Plateau ( int nbLignes, int nbColonnes,int nbBiome ,int nbBalise )
	{
		this.nbLignes       = nbLignes                        ;
		this.nbColonnes     = nbColonnes                      ;
		this.nbBiome        = nbBiome                         ; 
		this.nbBalise       = nbBalise                        ; 
		this.balises        = new Balise[nbLignes][nbColonnes];
		this.tabNumBalise   = null                            ;
		this.tabStringBiome = null                            ;
		this.tabDepart      = null                            ;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public int    getNbLignes   ()	{ return this.nbLignes  ; }
	public int    getNbColonnes () { return this.nbColonnes; }
	public int    getNbBiomes   () { return this.nbBiome   ; }
	public int    getNbBalises  () { return this.nbBalise  ; }

	public Balise getBalise ( int ligne, int colonne )
	{
		/* vérification nécessaire pour éviter une erreur */
		if ( this.balises == null || this.balises.length == 0) 
			return null;

		/* vérification si les coordonnées sont dans le tableau this.balise */
		if (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes)
			return this.balises[ligne][colonne];

		return null;
	}


	/* - - - - - - - - - - - - - */
	/* Modificateurs             */
	/* - - - - - - - - - - - - - */
	public void setLigne          ( int         ligne   )  { this.nbLignes       = ligne   ; }
	public void setColonne        ( int         colonne )  { this.nbColonnes     = colonne ; }
	public void setTabNumBalise   ( int    [][] tab     )  { this.tabNumBalise   = tab     ; }
	public void setTabStringBiome ( String [][] tab     )  { this.tabStringBiome = tab     ; }
	public void setTabDepart      ( boolean[][] tab     )  { this.tabDepart      = tab     ; }

	public void setBalise ( int ligne, int colonne, Balise balise )
	{
		/* vérification pour éviter de mettre une balise hors du tableau */
		if (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes)
		{
			this.balises[ligne][colonne] = balise;
		}
	}


	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */
	public void genererPlateau ()
	{
		TypeBiome biome     = null;
		int       numBalise = 0   ;
		
		this.balises    = new Balise[this.nbLignes][this.nbColonnes];

		for ( int lig = 0; lig < this.nbLignes; lig ++)
		{
			for ( int col = 0; col < this.nbColonnes; col ++)
			{
				biome     = TypeBiome.toNom( this.tabStringBiome[lig][col]);
				numBalise = this.tabNumBalise[lig][col]                    ;

				/* positionne les balises dans le plateau */
				if ( numBalise != 0 )
				{
					this.setBalise(lig, col, new Balise(numBalise, biome, new Position(lig, col)));
				}

				/* positionne les balises de départ dans le plateau */
				if (this.tabDepart != null && this.tabDepart[lig][col])
				{
					this.setBalise(lig, col, new BaliseDepart(numBalise, biome, new Position(lig, col), "Rouge"));
				}

			}
		}

		/* A retirer avant l'envoi final */
		System.out.println(this.toString());

		// on calcul les liaisons entre balises
		this.calculerLiaisons();
	}

	/** Calcule, pour chaque balise, la liste de ses balises voisines selon
	  * les huit directions de la rose des vents.
	  */
	public void calculerLiaisons ()
	{
		int[][] directions = 
		{
			{ -1, 0}, // Nord
			{ -1, 1}, // Nord Est
			{ 0, 1 }, // Est
			{ 1, 1 }, // Sud Est
			{ 1, 0 }, // Sud
			{ 1, -1}, // Sud Ouest
			{ 0, -1}, // Ouest
			{ -1, -1} // Nord Ouest
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
						int ligVoisin = lig + dir[0];
						int colVoisin = col + dir[1];
						boolean trouveVoisin = false;

						while (!trouveVoisin && ligVoisin >= 0 && ligVoisin < this.nbLignes && colVoisin >= 0 && colVoisin < this.nbColonnes)
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


	/** Ecrit le plateau dans un fichier .data : le bloc grille puis le bloc
	  * des liaisons entre balises.
	  * @param nomFichier le nom du fichier à écrire
	  * @return true si l'ecriture a reussi, false sinon
	  */
	public boolean sauvegarder ( String nomFichier )
	{
		try
		{
			PrintWriter pw = new PrintWriter ( new OutputStreamWriter ( new FileOutputStream ( nomFichier ), "UTF8" ) );
			/* Bloc grille */
			// En tete du .data : dimensions de la grille
			pw.println ( "GRILLE " + this.nbLignes + " " + this.nbColonnes );
			
			// Une ligne par balise : ligne colonne numero biome type [couleur si depart]
			for (int lig = 0; lig < this.nbLignes; lig++)
			{
				for (int col = 0; col < this.nbColonnes; col++)
				{
					Balise b = this.balises[lig][col];
					
					if (b != null)
					{
						String ligneFichier = lig + " " + col + " " + b.getNumero() + " " + b.getBiome();
						if (b instanceof BaliseDepart)
						{
							ligneFichier += " DEPART " + ((BaliseDepart) b).getCouleur();
						}
						else
						{
							ligneFichier += " NORMALE";
						}

						pw.println ( ligneFichier );
					}
					
				}
			}

			/* Bloc liaisons */
			pw.println ( "LIAISONS" );
			
			// une ligne par balise : ligne,colonne : voisin,voisin ...
			for (int lig = 0; lig < this.nbLignes; lig++ )
			{
				for (int col = 0; col < this.nbColonnes; col++)
				{
					Balise b = this.balises[lig][col];
					
					if  (b != null )
					{
						String ligneFichier = lig + "," + col + " :";
						for (Balise voisin : b.getVoisins())
						{
							ligneFichier += " " + voisin.getPosition().getLigne() + "," + voisin.getPosition().getColonne();
						}
						pw.println ( ligneFichier );
					}
					
				}
			}

			pw.close();
			return true;

		}
		catch (IOException e)
		{
			System.err.println ( "Erreur lors de la sauvegarde du plateau : " + e.getMessage());
			return false;
		}
	}
	

	public String toString ()
	{
		// Calcul de la largeur maximale parmi toutes les cellules non vides
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
				Balise  b       = this.balises[lig][col];
				String  contenu = (b != null) ? b.toString() : "";
				s += String.format ( "| %-" + largeur + "s ", contenu );
			}
			s += "|\n" + sep;
		}

		return s;
	}
}