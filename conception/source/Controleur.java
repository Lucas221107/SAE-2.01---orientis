package conception.source;

import conception.source.ihm.FramePrincipale;
import conception.source.metier.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;


public class Controleur 
{
	private FramePrincipale ihm;
	private Plateau         plateau;

	public Controleur()
	{
		this.plateau = null;
		this.ihm     = new FramePrincipale(this);
	}

	public void genererPlateau(int nbColonnes, int nbLignes, int nbBiomes, int nbBalise)
	{
		this.plateau = new Plateau(nbLignes, nbColonnes);
		
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

			/* --- Bloc GRILLE --- */
			if ( !sc.hasNextLine() ) { sc.close(); return false; }

			String entete = sc.nextLine();
			if ( !entete.startsWith ( "GRILLE" ) ) { sc.close(); return false; }

			String[] partiesEntete = entete.split ( " " );
			int nbLignes   = Integer.parseInt ( partiesEntete[1] );
			int nbColonnes = Integer.parseInt ( partiesEntete[2] );
			this.plateau   = new Plateau ( nbLignes, nbColonnes );

			boolean dansGrille = true;

			while ( sc.hasNextLine() && dansGrille )
			{
				String ligne = sc.nextLine();

				if ( ligne.equals ( "LIAISONS" ) )
				{
					dansGrille = false;
				}
				else
				{
					// format : lig col numero biome NORMALE
					//       ou : lig col numero biome DEPART couleur
					String[]  p      = ligne.split ( " " );
					int       lig    = Integer.parseInt ( p[0] );
					int       col    = Integer.parseInt ( p[1] );
					int       numero = Integer.parseInt ( p[2] );
					TypeBiome biome  = TypeBiome.valueOf ( p[3] );
					String    type   = p[4];

					Balise balise;
					if ( type.equals ( "DEPART" ) )
					{
						balise = new BaliseDepart ( numero, biome, new Position ( lig, col ), p[5] );
					}
					else
					{
						balise = new Balise ( numero, biome, new Position ( lig, col ) );
					}

					this.plateau.setBalise ( lig, col, balise );
				}
			}

			/* --- Bloc LIAISONS --- */
			while ( sc.hasNextLine() )
			{
				String ligne = sc.nextLine();

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

			sc.close();
			return true;
		}
		catch ( IOException e )
		{
			System.err.println ( "Erreur lors du chargement du plateau : " + e.getMessage() );
			return false;
		}
	}



	public String[] getTabBiome()
	{
		TypeBiome[] tabNom    = TypeBiome.values();
		String   [] tabBiomes = new String[ TypeBiome.values().length];

		for ( int cpt = 0; cpt < tabBiomes.length; cpt ++)
			tabBiomes[cpt] = tabNom[cpt].getNom();

		return tabBiomes;
	}


	public Color getColorBiome ( String s)
	{
		for ( TypeBiome biome : TypeBiome.values())
		{
			if ( biome.getNom().equals(s))
				return biome.getCouleur();
		}

		return null;
	}


	public static void main ( String[] args)
	{
		new Controleur();
	}
	
}