package jeu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import jeu.ihm.*;
import jeu.metier.*;

public class Controleur
{

	private FramePrincipale     ihm      ;
	private Plateau         plateau;
	private String          cheminPlateau;
	private String          sFichierImage;
	private Partie          metier;

	private Partie partieJ1 ;
	private Partie partieJ2 ;
	

	public Controleur()
	{
		this.ihm    = new FramePrincipale ( this );
		this.metier = null;
	}



	public void setCheminPlateau ( String chemin )
	{
		this.cheminPlateau = chemin;
		this.ihm.getPanelChargement().chargerPlateau();
		this.ihm.getPanelChargementDuo().chargerPlateau();
	}

	public Plateau chargerPlateau ( String chemin)
	{
		this.plateau = GestionnairePlateau.charger(chemin);
		return GestionnairePlateau.charger(chemin);
	}

	public String getCheminPlateau ()
	{
		return this.cheminPlateau;
	}


	public void resetPlateau()
	{
		this.cheminPlateau = null;
		this.metier = null;
	}

	public void setFichierImage ( String fic )
	{
		this.sFichierImage = fic;
		this.ihm.maj();
	}

	public String getFichierImage ()  { return this.sFichierImage; }


	public int getNbLigne()  { return this.plateau.getNbLignes(); }
	public int getNbColonnes() { return this.plateau.getNbColonnes(); }

	public Color getColorBiome ( int lig, int col)
	{
		String nomBiome = this.plateau.getTabStringBiome() [lig][col];

		TypeBiome biome = TypeBiome.toNom(nomBiome);

		if ( biome == null )
			return Color.WHITE;

		return biome.getCouleur();
	}


	public Balise getBaliseDepartJoueur () 
	{ if (this.metier == null)
		return null;
		return this.metier.getJoueur().getBaliseDepart(); }


	public int    getLigBaliseDepartJoueur() 
	{ if (this.metier == null)
		return -1; 
		return this.getBaliseDepartJoueur().getPosition().getLigne() ; }


	public int    getColBaliseDepartJoueur() 
	{ if (this.metier == null)
		return -1;
		return this.getBaliseDepartJoueur().getPosition().getColonne() ; }

	public void creerPartie ( String nomJoueur )
	{
		List<BaliseDepart> lstBaliseDeparts = this.plateau.getBalisesDepart();
		
		if ( lstBaliseDeparts != null )
		{
			Joueur joueur = new Joueur(nomJoueur, "bleu",  lstBaliseDeparts.get(0));
			this.metier = new Partie(joueur, this.plateau);

			this.metier.demarrer();

			System.out.println("Partie créer");
		}
	}

	public void creerPartieDuo(String nomJ1, String nomJ2)
	{
		List<BaliseDepart> lstBaliseDeparts = this.plateau.getBalisesDepart();

		if (lstBaliseDeparts != null)
		{
			Joueur joueur1 = new Joueur(nomJ1, "rouge", lstBaliseDeparts.get(0));
			Joueur joueur2 = new Joueur(nomJ2, "bleu",  lstBaliseDeparts.get(0));

			this.partieJ1 = new Partie(joueur1, this.plateau);
			this.partieJ2 = new Partie(joueur2, this.plateau);

			this.partieJ1.demarrer();
			this.partieJ2.demarrer();

		
		}
	}

	public boolean estJouable ( int lig, int col, int ligDepart, int colDepart)
	{
		if ( this.metier == null || this.plateau == null)
			return false;

		Balise baliseArrive = this.plateau.getBalise(lig, col);
		Balise baliseDepart = this.plateau.getBalise(ligDepart, colDepart);

		if ( baliseDepart == null || baliseArrive == null)
			return false;

		return baliseDepart.estVoisin(baliseArrive) && !baliseArrive.estVisitee();
	}



	public List<int[]> getVoisinDisponible ( int lig, int col, int numFanion)
	{
		List<int[]> lstDispo = new ArrayList<>();

		if ( this.plateau == null)
			return lstDispo;

		Balise balise = this.plateau.getBalise(lig, col);
		
		if ( balise == null )
			return lstDispo;
		
		for ( Balise baliseVoisine : balise.getVoisins())
		{
			if ( !baliseVoisine.estVisitee() && ( numFanion == 0 || baliseVoisine.getNumero() == numFanion))
			{
				lstDispo.add( new int[] { baliseVoisine.getPosition().getLigne(), baliseVoisine.getPosition().getColonne() }) ;
			}
		}

		return lstDispo;
	}



	public void capturerBalise ( int ligDepart, int colDepart, int ligArrive, int colArrive)
	{
		if ( this.metier == null )
			return;

		Balise baliseDepart = this.plateau.getBalise(ligDepart, colDepart);
		Balise baliseArrive = this.plateau.getBalise(ligArrive, colArrive);

		if ( baliseDepart == null || baliseArrive == null)
			return;

		this.metier.getJoueur().capturer(baliseDepart, baliseArrive);
	}

	public void terminerManche()
	{
		this.metier.terminerManche();
	}

	public boolean estMancheTermine()
	{
		return this.metier.getMancheCourante().estTerminee();
	}

	public boolean estBaliseDepart( int lig, int col)
	{
		return this.plateau.getBalise(lig, col) instanceof BaliseDepart;
	}

	public boolean aUneBalise ( int lig, int col)
	{
		return this.plateau.getBalise(lig, col) != null;
	}

	public int getNumeroBalise ( int lig, int col )
	{
		Balise b = this.plateau.getBalise(lig, col);

		if ( b != null)
			return b.getNumero();

		return 0;
	}

	public Partie getPartieJ1() { return this.partieJ1; }
	public Partie getPartieJ2() { return this.partieJ2; }


	public List<int[]> getVoisins(int lig, int col)
	{
		List<int[]> coords = new ArrayList<>();

		if (this.plateau == null) 
			return coords;

		Balise b = this.plateau.getBalise(lig, col);
		
		if (b == null)
			return coords;

		for (Balise voisin : b.getVoisins())
		{
			coords.add(new int[] { voisin.getPosition().getLigne(),	voisin.getPosition().getColonne() });
		}
		
		return coords;
	}



	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	
	/** @return la partie en cours, ou null si aucune partie n'est démarrée */
	public Partie getPartie() { return this.metier; }

	/**
	 * Remplace la partie en cours.
	 *
	 * @param partie la nouvelle partie (peut être null pour réinitialiser)
	 */
	public void setPartie( Partie partie ) { this.metier = partie; }

	/* - - - - - - - - - - - - - */
	/* Méthodes                  */
	/* - - - - - - - - - - - - - */

	public boolean demarrerPartie()
	{
		if ( this.metier == null )
			return false;
	
		this.metier.demarrer();
		return true;
	}

	public boolean demarrerPartieDuo(String nomFichier, String nomJ1, String nomJ2)
	{
		Plateau plateau = GestionnairePlateau.charger(nomFichier);
		if (plateau == null) return false;

		Joueur joueur1 = new Joueur(nomJ1, "rouge", null);
		Joueur joueur2 = new Joueur(nomJ2, "bleu",  null);

		this.partieJ1 = new Partie(joueur1, plateau);
		this.partieJ2 = new Partie(joueur2, plateau);

		this.partieJ1.demarrer();
		this.partieJ2.demarrer();

		return true;
	}

	
		public Fanion piocher(Pioche pioche){return pioche.piocher();}
		public boolean estVide(Pioche pioche){return pioche.estVide();}
		public void reconstituer(Pioche pioche){ pioche.reconstituer();}
		//public void resetPanelPioehceDuo(PanelPiocheDuo panelPiocheDuo){panelPiocheDuo.reset();}


	public static void main (String[] args)
	{
		new Controleur();
	}
}

