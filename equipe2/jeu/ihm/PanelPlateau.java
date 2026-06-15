package jeu.ihm;

import jeu.Controleur;
import jeu.metier.Plateau;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Image;

public class PanelPlateau extends JPanel
{
	/*- - - - - - - - - - - - - - - - - -*/
	/* Constantes des couleurs de manche */
	/*- - - - - - - - - - - - - - - - - -*/
	private static final Color[] COULEURS_MANCHES = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.PINK, Color.MAGENTA };

	/*- - - - - - - - - - - - -*/
	/* Attributs               */
	/*- - - - - - - - - - - - -*/
	private FramePrincipale frame                ;
	private Controleur      ctrl                 ;

	private Plateau         plateau              ;

	private int[]           tabBaliseSelectionnee;
	private List<int[]>     lstBaliseDisponible  ;
	private List<int[]>     lstSegmentDessines   ;

	private int             tailleCasePlateau    ;
	private int             indexJoueur          ;

	private PanelPiocheDuo  panelPiocheDuo       ;
	private boolean         actif                ;

	
	/*- - - - - - - - - - - - -*/
	/* Constructeur            */
	/*- - - - - - - - - - - - -*/
	public PanelPlateau ( FramePrincipale frame, Controleur ctrl)
	{

		/*- - - - - - - - - - - - - */
		/* Création des composantes */
		/*- - - - - - - - - - - - - */

		this.ctrl  = ctrl ;
		this.frame = frame;

		this.tailleCasePlateau     = 50;

		this.actif                 = true;
		this.indexJoueur           = -1  ;
		this.tabBaliseSelectionnee = null;
		this.lstBaliseDisponible   = new ArrayList<>();
		this.lstSegmentDessines    = new ArrayList<>(); 
		
		/*- - - - - - - - - - - - - - */
		/* Activation des composantes */
		/*- - - - - - - - - - - - - - */

		GererSouris gererSouris = new GererSouris();
		this.addMouseListener       ( gererSouris );
		this.addMouseMotionListener ( gererSouris );
	}

	/*===================================================================== */

	private class GererSouris extends MouseAdapter
	{

		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();

			if (!PanelPlateau.this.actif)
				return;

			if (PanelPlateau.this.plateau == null || PanelPlateau.this.ctrl.getPartie() == null)
				return;

			int tailleCase = PanelPlateau.this.tailleCasePlateau;
			int decallageX = (PanelPlateau.this.getWidth()  - PanelPlateau.this.plateau.getNbColonnes() * tailleCase) / 2;
			int decallageY = (PanelPlateau.this.getHeight() - PanelPlateau.this.plateau.getNbLignes()   * tailleCase) / 2;

			int lig = (y - decallageY) / tailleCase;
			int col = (x - decallageX) / tailleCase;

			if (lig < 0 || lig > PanelPlateau.this.plateau.getNbLignes() ||
				col < 0 || col > PanelPlateau.this.plateau.getNbColonnes())
				return;

			if (!PanelPlateau.this.ctrl.aUneBalise(lig, col))
				return;

			if (PanelPlateau.this.tabBaliseSelectionnee == null)
			{
				if (PanelPlateau.this.ctrl.getNumeroFanionCourant() == -1)
					return;

				PanelPlateau.this.tabBaliseSelectionnee = new int[]{ lig, col };

				// En duo : utiliser le joueur de CE plateau
				if (PanelPlateau.this.ctrl.estDuo())
					PanelPlateau.this.lstBaliseDisponible = PanelPlateau.this.ctrl.getVoisinDisponibleJoueur(lig, col, PanelPlateau.this.indexJoueur);
				else
					PanelPlateau.this.lstBaliseDisponible = PanelPlateau.this.ctrl.getVoisinDisponible(lig, col);

				PanelPlateau.this.repaint();
				return;
			}

			for (int[] baliseDispo : PanelPlateau.this.lstBaliseDisponible)
			{
				if (baliseDispo[0] == lig && baliseDispo[1] == col)
				{
					int ligSel = PanelPlateau.this.tabBaliseSelectionnee[0];
					int colSel = PanelPlateau.this.tabBaliseSelectionnee[1];

					// En duo : capturer pour le joueur de CE plateau
					if (PanelPlateau.this.ctrl.estDuo())
						PanelPlateau.this.ctrl.capturerBaliseJoueur(ligSel, colSel, lig, col, PanelPlateau.this.indexJoueur);
					else
						PanelPlateau.this.ctrl.capturerBalise(ligSel, colSel, lig, col);

					PanelPlateau.this.lstSegmentDessines.add(new int[]{ ligSel, colSel, lig, col, PanelPlateau.this.ctrl.getNumeroManche() });

					PanelPlateau.this.tabBaliseSelectionnee = null;
					PanelPlateau.this.lstBaliseDisponible.clear();

					if (PanelPlateau.this.ctrl.estMancheTermine())
					{
						int numManche = PanelPlateau.this.ctrl.getNumeroManche();
						int score = PanelPlateau.this.ctrl.estDuo()
								? PanelPlateau.this.ctrl.getScoreJoueur(PanelPlateau.this.indexJoueur)
								: PanelPlateau.this.ctrl.getScoreJoueurCourant();

						if (PanelPlateau.this.ctrl.estDuo())
						{
							if (PanelPlateau.this.panelPiocheDuo != null)
								PanelPlateau.this.panelPiocheDuo.mancheTermineeDuo(numManche, score);
						}
						else
						{
							PanelPlateau.this.ctrl.terminerManche();
							PanelPlateau.this.frame.getPanelSolo().ajouterScoreManche(numManche, score);
						}

						PanelPlateau.this.nouvelleManche();
						return;
					}

					if (PanelPlateau.this.panelPiocheDuo != null)
						PanelPlateau.this.panelPiocheDuo.joueurAJoue(PanelPlateau.this.indexJoueur);

					PanelPlateau.this.repaint();
					return;
				}
			}

			PanelPlateau.this.tabBaliseSelectionnee = null;
			PanelPlateau.this.lstBaliseDisponible.clear();
			PanelPlateau.this.repaint();
		}
	}

	/* méthode qui permet de charger le plateau en fonction du chemin aboslue */
	public void charger ( String chemin )
	{
		this.plateau = this.ctrl.chargerPlateau(chemin);
		this.repaint();
	}

	public void setPanelPiocheDuo(PanelPiocheDuo p) { this.panelPiocheDuo = p; }

	/* méthode qui réinitialise le plateau actuel */
	public void reset()
	{
		this.plateau           = null;
		this.tailleCasePlateau = 50  ;
		repaint();
	}

	/* méthode qui réinitialise le tableau de balise sélectionnée ainsi que la liste de voisin disponible et démarre une nouvelle manche */
	public void nouvelleManche()
	{
		this.tabBaliseSelectionnee = null;
		this.lstBaliseDisponible.clear();
		
		if ( ! this.ctrl.estDuo() )
		{
			this.frame.getPanelPioche().reinitialiserAffichage();
			this.frame.getPanelSolo  ().majManche();
		}

		this.repaint();
	}

	/* méthode qui recommence la partie */
	public void reinitialiserPartie()
	{
		this.tabBaliseSelectionnee = null ;
		this.lstBaliseDisponible.clear();
		this.lstSegmentDessines.clear();

		if ( !this.ctrl.estDuo() )
			this.frame.getPanelPioche().reinitialiserAffichage();
		
		this.repaint();
	}


	public void setActif ( boolean actif )   { this.actif = actif ; }


	/* méthode qui change la taille des cases du plateau */
	public void setTailleCasePlateau( int taille)
	{
		this.tailleCasePlateau = taille;
		this.repaint();
	}

	public void setPlateau(Plateau plateau)
	{
		this.plateau = plateau;
		this.repaint();
	}


	/* méthode qui permet de dessiner le plateau */
	public void paintComponent ( Graphics g)
	{
		super.paintComponent(g);

		/* si le plateau est null on ne dessine rien */
		if ( this.plateau == null)
			return;

		Graphics2D g2d = ( Graphics2D ) g ;

		int nbLig = this.plateau.getNbLignes  ();
		int nbCol = this.plateau.getNbColonnes();

		//Calcul du decallage pour centrer le plateau
		int decallageX = ( this.getWidth()  - nbCol * this.tailleCasePlateau) / 2;
		int decallageY = ( this.getHeight() - nbLig * this.tailleCasePlateau) / 2;

		for (int lig = 0; lig < nbLig; lig ++)
		{
			for ( int col = 0; col < nbCol; col ++)
			{
				int x = decallageX + col * this.tailleCasePlateau ;
				int y = decallageY + lig * this.tailleCasePlateau ;

				// colorier les cases de la couleur du biome
				g.setColor( this.ctrl.getColorBiome(lig, col));
				g.fillRect(x, y, this.tailleCasePlateau, this.tailleCasePlateau);
			}
		}

		/* colorie la case des balises disponibles en orange */
		g.setColor(Color.ORANGE );
		for ( int[] baliseDispo : this.lstBaliseDisponible)
		{
			int x = decallageX + baliseDispo[1] * this.tailleCasePlateau;
			int y = decallageY + baliseDispo[0] * this.tailleCasePlateau;

			g.fillRect(x, y, this.tailleCasePlateau, this.tailleCasePlateau);
		}

		/* appelle de la méthode qui dessine les liaisons entre les balises */
		this.dessinerLiaison(g2d, decallageX, decallageY);

		
		g2d.setStroke( new BasicStroke(4)); // <- change l'éppaisseur des traits
		
		/* dessine le chemin emprunté pour capturer une balise de la couleur de la manche */
		for ( int[] segment : this.lstSegmentDessines)
		{
			int x1 = decallageX + segment[1] * this.tailleCasePlateau + this.tailleCasePlateau / 2;
			int y1 = decallageY + segment[0] * this.tailleCasePlateau + this.tailleCasePlateau / 2;
			int x2 = decallageX + segment[3] * this.tailleCasePlateau + this.tailleCasePlateau / 2;
			int y2 = decallageY + segment[2] * this.tailleCasePlateau + this.tailleCasePlateau / 2;


			/* sécurité qui boucle sur le tableau de couleur */
			/* si le nombre de manche est superieur au nombre de couleur du tableau, on réutilise une couleur deja utilisé */
			int indexCouleur = ( segment[4] - 1 ) % PanelPlateau.COULEURS_MANCHES.length;

			g2d.setColor( PanelPlateau.COULEURS_MANCHES[ indexCouleur ] );
			g2d.drawLine(x1, y1, x2, y2);
		}

		g2d.setStroke( new BasicStroke( 1 )); // <- remet l'epaisseur du trait normal


		/* ajout des images des balises sans hashMap */
		for (int lig = 0; lig < nbLig; lig ++)
		{
			for ( int col = 0; col < nbCol; col ++)
			{

				int x = decallageX + col * this.tailleCasePlateau ;
				int y = decallageY + lig * this.tailleCasePlateau ;

				// ajout des balises
				if ( this.ctrl.aUneBalise(lig, col) )
				{
					int    numeroBalise = this.ctrl.getNumeroBalise(lig, col);
					String couleur      = this.ctrl.getCouleurBaliseDepart(lig, col);
					String chemin;

					if ( this.ctrl.estBaliseDepart(lig, col))
						chemin = "../jeu/ihm/images/balise_depart_" + couleur + "_" + numeroBalise + ".png";
					else
						chemin = "../jeu/ihm/images/balise_" + numeroBalise + ".png";

					Image img = new ImageIcon( chemin ).getImage();

					g.drawImage( img, x, y, this.tailleCasePlateau, this.tailleCasePlateau, this);

				}
			}
		}

		/* dessine un carré autour de la balise de départ du joueur */	
		if ( this.ctrl.getPartie() != null)
		{

			 int ligDepart, colDepart;

			if (this.indexJoueur >= 0) 
			{
				// duo : afficher la balise de départ du bon joueur
				jeu.metier.Joueur j = this.ctrl.getPartie().getJoueurs().get(this.indexJoueur);
				ligDepart = j.getBaliseDepart().getPosition().getLigne();
				colDepart = j.getBaliseDepart().getPosition().getColonne();
			} 
			else 
			{
				// solo
				ligDepart = this.ctrl.getLigBaliseDepartJoueur();
				colDepart = this.ctrl.getColBaliseDepartJoueur();
			}


			int x = decallageX + colDepart * this.tailleCasePlateau;
			int y = decallageY + ligDepart * this.tailleCasePlateau;

			g.setColor( Color.RED );
			g.drawRect( x, y, this.tailleCasePlateau, this.tailleCasePlateau);
		}


		/* dessine un carré jaune autour de la balise clické */
		if ( this.tabBaliseSelectionnee != null )
		{
			int x = decallageX + this.tabBaliseSelectionnee[1] * this.tailleCasePlateau;
			int y = decallageY + this.tabBaliseSelectionnee[0] * this.tailleCasePlateau;
			
			g.setColor( Color.YELLOW );
			g.drawRect( x, y, this.tailleCasePlateau, this.tailleCasePlateau);
		}
		
	}

	public void setIndexJoueur(int index) { this.indexJoueur = index; }

	/* méthode qui dessine les liaisons entre les balises */
	public void dessinerLiaison ( Graphics g, int decallageX, int decallageY )
	{
		g.setColor( Color.BLACK );

		for (int lig = 0; lig < this.ctrl.getNbLigne(); lig++)
		{
			for (int col = 0; col < this.ctrl.getNbColonnes(); col++)
			{
				if (! this.ctrl.aUneBalise(lig, col)) 
					continue;

				int xCourant = decallageX + col * this.tailleCasePlateau + this.tailleCasePlateau / 2;
				int yCourant = decallageY + lig * this.tailleCasePlateau + this.tailleCasePlateau / 2;

				for (int[] voisin : this.ctrl.getVoisins(lig, col))
				{
					int xVoisin = decallageX + voisin[1] * this.tailleCasePlateau + this.tailleCasePlateau / 2;
					int yVoisin = decallageY + voisin[0] * this.tailleCasePlateau + this.tailleCasePlateau / 2;

					g.drawLine(xCourant, yCourant, xVoisin, yVoisin);
				}
			}
		}
	}
}
