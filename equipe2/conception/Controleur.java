package conception;

import conception.ihm.FramePrincipale;
import conception.metier.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;

/**
 * Représente le Controleur de la partie Conception de Orientis.
 * Il permet de relier la partie Metier et IHM.
 * Il suffit de lancer ce fichier pour faire fonctionner l'application.
 *
 * @author Groupe 2
 * @version 1.0
 */

public class Controleur 
{

	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private FramePrincipale ihm    ;
	private Plateau         plateau;


	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public Controleur()
	{
		this.plateau = new Plateau(0,0,0,0);
		this.ihm     = new FramePrincipale(this);
	}


	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public Balise    getBalise(int lig , int col) { return this.plateau.getBalise    (lig, col) ; }
	public int       getNbLig ()                  { return this.plateau.getNbLignes  ()         ; }
	public int       getNbCol ()                  { return this.plateau.getNbColonnes()         ; }
	public TypeBiome toColor  (Color c )          {	return TypeBiome.toColors( c )              ; }

	public String[] getTabBiome()
	{
		TypeBiome[] tabNom    = TypeBiome.values()                    ;
		String   [] tabBiomes = new String[ TypeBiome.values().length];

		for ( int cpt = 0; cpt < tabBiomes.length; cpt ++)
			tabBiomes[cpt] = tabNom[cpt].getNom();

		return tabBiomes;
	}


	/* - - - - - - - - - - - - - */
	/* Modificateurs             */
	/* - - - - - - - - - - - - - */
	public void  setTabNumBalise   ( int    [][] tab )  { this.plateau.setTabNumBalise  ( tab ) ; }
	public void  setTabDepart      ( boolean[][] tab )  { this.plateau.setTabDepart     ( tab ) ; }
	public void  setTabStringBiome ( String [][] tab )  { this.plateau.setTabStringBiome( tab ) ; }
	public void  setNbLigne        ( int         lig )  { this.plateau.setLigne         ( lig ) ; }
	public void  setNbCol          ( int         col )  { this.plateau.setColonne       ( col ) ; }

	public Color getColorBiome     ( String      s   )
	{
		for ( TypeBiome biome : TypeBiome.values())
		{
			if ( biome.getNom().equals(s))
				return biome.getCouleur();
		}

		return null;
	}


	/* - - - - - - - - - - - - - */
	/* Méthode                   */
	/* - - - - - - - - - - - - - */

	public void genererPlateau(int nbColonnes, int nbLignes, int nbBiomes, int nbBalise)
	{
		this.plateau = new Plateau(nbLignes, nbColonnes,nbBiomes,nbBalise);	
	}


	public void genererPlateau()
	{
		this.plateau.genererPlateau();
	}

	public boolean sauvegarder()
	{
		if ( this.plateau == null)
		{
			return false;
		}

		return this.plateau.sauvegarder ( "plateau.data" );
	}

	
	public boolean chargerPlateau ( String nomFichier )
	{
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( nomFichier ), "UTF8" );

			/* --- En tete GRILLE --- */
			if ( !sc.hasNextLine() ) { sc.close(); return false; }

			String entete = sc.nextLine();
			if ( !entete.startsWith ( "GRILLE" ) ) { sc.close(); return false; }

			String[] partiesEntete = entete.split ( " " );
			int nbLignes   = Integer.parseInt ( partiesEntete[1] );
			int nbColonnes = Integer.parseInt ( partiesEntete[2] );
			int nbBiomes   = Integer.parseInt ( partiesEntete[3] );
			int nbBalises  = Integer.parseInt ( partiesEntete[4] );
			this.plateau   = new Plateau ( nbLignes, nbColonnes, nbBiomes, nbBalises );

			// tableaux reconstruits a partir du fichier
			String [][] tabBiome  = new String [nbLignes][nbColonnes];
			int    [][] tabNum    = new int    [nbLignes][nbColonnes];
			boolean[][] tabDepart = new boolean[nbLignes][nbColonnes];

			String section = "";

			while ( sc.hasNextLine() )
			{
				String ligne = sc.nextLine();

				// changement de bloc
				if ( ligne.equals ( "BIOMES" ) || ligne.equals ( "BALISES" ) || ligne.equals ( "LIAISONS" ) )
				{
					section = ligne;
					continue;
				}

				if ( ligne.isEmpty() )
					continue;

				if ( section.equals ( "BIOMES" ) )
				{
					// format : lig col BIOME
					String[]  p     = ligne.split ( " " );
					int       lig   = Integer.parseInt ( p[0] );
					int       col   = Integer.parseInt ( p[1] );
					TypeBiome biome = TypeBiome.valueOf  ( p[2] );
					tabBiome[lig][col] = biome.getNom();
				}
				else if ( section.equals ( "BALISES" ) )
				{
					// format : lig col numero biome NORMALE
					//       ou : lig col numero biome DEPART couleur
					String[]  p      = ligne.split ( " " );
					int       lig    = Integer.parseInt ( p[0] );
					int       col    = Integer.parseInt ( p[1] );
					int       numero = Integer.parseInt ( p[2] );
					TypeBiome biome  = TypeBiome.valueOf  ( p[3] );
					String    type   = p[4];

					Balise balise;
					if ( type.equals ( "DEPART" ) )
					{
						balise = new BaliseDepart ( numero, biome, new Position ( lig, col ), p[5] );
						tabDepart[lig][col] = true;
					}
					else
					{
						balise = new Balise ( numero, biome, new Position ( lig, col ) );
					}

					tabNum[lig][col] = numero;
					this.plateau.setBalise ( lig, col, balise );
				}
				else if ( section.equals ( "LIAISONS" ) )
				{
					// format : lig,col : lig1,col1 lig2,col2 ...
					String[] parties = ligne.split ( " : " );
					String[] coords  = parties[0].split ( "," );
					int    lig    = Integer.parseInt ( coords[0] );
					int    col    = Integer.parseInt ( coords[1] );
					Balise balise = this.plateau.getBalise ( lig, col );

					if ( balise != null && parties.length > 1 )
					{
						String[] voisinsStr = parties[1].split ( " " );
						for ( String voisinStr : voisinsStr )
						{
							if ( !voisinStr.isEmpty() )
							{
								String[] cv     = voisinStr.split ( "," );
								int      ligV   = Integer.parseInt ( cv[0] );
								int      colV   = Integer.parseInt ( cv[1] );
								Balise   voisin = this.plateau.getBalise ( ligV, colV );
								if ( voisin != null )
									balise.ajouterVoisin ( voisin );
							}
						}
					}
				}
			}

			// on remet les tableaux reconstruits dans le plateau
			this.plateau.setTabStringBiome ( tabBiome  );
			this.plateau.setTabNumBalise   ( tabNum    );
			this.plateau.setTabDepart      ( tabDepart );

			sc.close();
			return true;
		}
		catch ( IOException e )
		{
			System.err.println ( "Erreur lors du chargement du plateau : " + e.getMessage() );
			return false;
		}
	}

	

	public static void main ( String[] args)
	{
		new Controleur();
	}
	
}