package conception.source.metier;

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
	private int        nbLignes;
	private int        nbColonnes;

	// Attribut traduisant l'association vers les balises (grille)
	private Balise[][] balises;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public Plateau ( int nbLignes, int nbColonnes )
	{
		this.nbLignes   = nbLignes;
		this.nbColonnes = nbColonnes;
		this.balises    = new Balise[nbLignes][nbColonnes];
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public int getNbLignes ()
	{
		return this.nbLignes;
	}

	public int getNbColonnes ()
	{
		return this.nbColonnes;
	}

	public void setLigne(int ligne)
	{
		this.nbLignes = ligne;
	}

	public void setColonne(int colonne)
	{
		this.nbColonnes = colonne;
	}

	public Balise getBalise ( int ligne, int colonne )
	{
		if (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes)
		{
			return this.balises[ligne][colonne];
		}
		else
		{
			return null;
		}
	}

	/* - - - - - - - - - - - - - */
	/* Modificateur              */
	/* - - - - - - - - - - - - - */
	public void setBalise ( int ligne, int colonne, Balise balise )
	{
		if (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes)
		{
			this.balises[ligne][colonne] = balise;
		}
	}

	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/** Genere le plateau en dur (7 biomes, 26 balises, 5 balises de depart). */
	public void genererPlateau ()
	{
		this.nbLignes   = 7;
		this.nbColonnes = 7;
		this.balises    = new Balise[this.nbLignes][this.nbColonnes];

		// creation des balises normales
		this.setBalise ( 0, 2, new Balise ( 3, TypeBiome.VALLEE,    new Position ( 0, 2 ) ) );
		this.setBalise ( 0, 4, new Balise ( 2, TypeBiome.VALLEE,    new Position ( 0, 4 ) ) );
		
		this.setBalise ( 1, 1, new Balise ( 4, TypeBiome.MONTAGNE,  new Position ( 1, 1 ) ) );
		this.setBalise ( 1, 3, new Balise ( 1, TypeBiome.VALLEE,    new Position ( 1, 3 ) ) );
		this.setBalise ( 1, 5, new Balise ( 3, TypeBiome.LAC,       new Position ( 1, 5 ) ) );
		this.setBalise ( 1, 6, new Balise ( 2, TypeBiome.LAC,       new Position ( 1, 6 ) ) );

		this.setBalise ( 2, 0, new Balise ( 2, TypeBiome.LANDE,     new Position ( 2, 0 ) ) );
		this.setBalise ( 2, 2, new Balise ( 5, TypeBiome.CLAIRIERE, new Position ( 2, 2 ) ) );
		this.setBalise ( 2, 4, new Balise ( 4, TypeBiome.VALLEE,    new Position ( 2, 4 ) ) );

		this.setBalise ( 3, 1, new Balise ( 3, TypeBiome.LANDE,     new Position ( 3, 1 ) ) );
		this.setBalise ( 3, 5, new Balise ( 1, TypeBiome.FORET,     new Position ( 3, 5 ) ) );
		
		this.setBalise ( 4, 0, new Balise ( 5, TypeBiome.LANDE,     new Position ( 4, 0 ) ) );
		this.setBalise ( 4, 2, new Balise ( 1, TypeBiome.CLAIRIERE, new Position ( 4, 2 ) ) );
		this.setBalise ( 4, 4, new Balise ( 3, TypeBiome.FORET,     new Position ( 4, 4 ) ) );
 
		this.setBalise ( 5, 1, new Balise ( 2, TypeBiome.MARAIS,    new Position ( 5, 1 ) ) );
		this.setBalise ( 5, 3, new Balise ( 4, TypeBiome.MARAIS,    new Position ( 5, 3 ) ) );
		this.setBalise ( 5, 5, new Balise ( 5, TypeBiome.FORET,     new Position ( 5, 5 ) ) );
		this.setBalise ( 5, 6, new Balise ( 1, TypeBiome.FORET,     new Position ( 5, 6 ) ) );
 
		this.setBalise ( 6, 0, new Balise ( 3, TypeBiome.MARAIS,    new Position ( 6, 0 ) ) );
		this.setBalise ( 6, 4, new Balise ( 1, TypeBiome.VALLEE,    new Position ( 6, 4 ) ) );
		this.setBalise ( 6, 6, new Balise ( 4, TypeBiome.FORET,     new Position ( 6, 6 ) ) );


		// creation des balises de depart
		this.setBalise ( 0, 0, new BaliseDepart ( 1, TypeBiome.FORET,     new Position ( 0, 0 ), "Rouge"  ));
		this.setBalise ( 0, 0, new BaliseDepart ( 1, TypeBiome.MONTAGNE,  new Position ( 0, 0 ), "ROUGE"  ) );
		this.setBalise ( 0, 5, new BaliseDepart ( 5, TypeBiome.LAC,       new Position ( 0, 5 ), "JAUNE"  ) );
		this.setBalise ( 3, 3, new BaliseDepart ( 2, TypeBiome.CLAIRIERE, new Position ( 3, 3 ), "VIOLET" ) );
		this.setBalise ( 3, 6, new BaliseDepart ( 4, TypeBiome.LAC,       new Position ( 3, 6 ), "VERT"   ) );
		this.setBalise ( 6, 2, new BaliseDepart ( 2, TypeBiome.MARAIS,    new Position ( 6, 2 ), "BLEU"   ) );

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
	  * @param nomFichier le nom du fichier a ecrire
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