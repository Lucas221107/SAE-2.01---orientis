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

			/* Si le panel n'est pas actif, on ne fait rien*/
			if (!PanelPlateau.this.actif)
				return;

			/* si le plateau est nul ou si la partie est nul */
			if (PanelPlateau.this.plateau == null || PanelPlateau.this.ctrl.getPartie() == null)
				return;

			int tailleCase = PanelPlateau.this.tailleCasePlateau;
			int decallageX = (PanelPlateau.this.getWidth()  - PanelPlateau.this.plateau.getNbColonnes() * tailleCase) / 2;
			int decallageY = (PanelPlateau.this.getHeight() - PanelPlateau.this.plateau.getNbLignes()   * tailleCase) / 2;

			int lig = (y - decallageY) / tailleCase;
			int col = (x - decallageX) / tailleCase;

			/* Si le click est hors du plateau on ne fait rien */
			if (lig < 0 || lig > PanelPlateau.this.plateau.getNbLignes() ||
				col < 0 || col > PanelPlateau.this.plateau.getNbColonnes())
				return;
			
			/* Si il n'y a pas de balise sur le click on ne fait rien */
			if (!PanelPlateau.this.ctrl.aUneBalise(lig, col))
				return;

			if (PanelPlateau.this.tabBaliseSelectionnee == null)
			{
				if (PanelPlateau.this.ctrl.getNumeroFanionCourant() == -1)
					return;

				PanelPlateau.this.tabBaliseSelectionnee = new int[]{ lig, col }; // <- on place les coordonnées du point cliqué


				if (PanelPlateau.this.ctrl.estDuo())
					PanelPlateau.this.lstBaliseDisponible = PanelPlateau.this.ctrl.getVoisinDisponibleJoueur(lig, col, PanelPlateau.this.indexJoueur);
				else
					PanelPlateau.this.lstBaliseDisponible = PanelPlateau.this.ctrl.getVoisinDisponible(lig, col);

				PanelPlateau.this.repaint();
				return;
			}

			/* la partie capture de balise */
			for (int[] baliseDispo : PanelPlateau.this.lstBaliseDisponible)
			{
				if (baliseDispo[0] == lig && baliseDispo[1] == col)
				{
					int ligSelectionne = PanelPlateau.this.tabBaliseSelectionnee[0];
					int colSelectionne = PanelPlateau.this.tabBaliseSelectionnee[1];


					if (PanelPlateau.this.ctrl.estDuo())
						PanelPlateau.this.ctrl.capturerBaliseJoueur(ligSelectionne, colSelectionne, lig, col, PanelPlateau.this.indexJoueur);
					else
						PanelPlateau.this.ctrl.capturerBalise(ligSelectionne, colSelectionne, lig, col);

					/* Ajout du segment tracer entre les 2 balises */
					PanelPlateau.this.lstSegmentDessines.add(new int[]{ ligSelectionne, colSelectionne, lig, col, PanelPlateau.this.ctrl.getNumeroManche() });


					PanelPlateau.this.tabBaliseSelectionnee = null; // <- reinitialise le tableau de balise selectionnée
					PanelPlateau.this.lstBaliseDisponible.clear(); // <- reinitialise la liste de balise Dipsonible 


					if (PanelPlateau.this.ctrl.estDuo())
					{
						if (PanelPlateau.this.panelPiocheDuo != null)
							PanelPlateau.this.panelPiocheDuo.joueurAJoue(PanelPlateau.this.indexJoueur); // <- indique le joueur courant a joué
					}
					else
					{
						/* On vérifie si la manche est terminée */
						if (PanelPlateau.this.ctrl.estMancheTermine())
						{
							int numManche = PanelPlateau.this.ctrl.getNumeroManche();
							int score     = PanelPlateau.this.ctrl.getScoreJoueurCourant();

							PanelPlateau.this.ctrl.terminerManche(); // <- termine la manche
							PanelPlateau.this.frame.getPanelSolo().ajouterScoreManche(numManche, score); // <- ajoute le score de la manche a la table de score du mode solo
							PanelPlateau.this.nouvelleManche(); // <- lance une nouvelle manche
							return;
						}
					}

					PanelPlateau.this.repaint(); // <- redessine le plateau
					return;
				}
			}

			PanelPlateau.this.tabBaliseSelectionnee = null; // <- reinitialise le tableau de balise selectionnée
			PanelPlateau.this.lstBaliseDisponible.clear(); // <- reinitialise la liste de balise Dipsonible 
			PanelPlateau.this.repaint();
		}
	}

	/* méthode qui permet de charger le plateau en fonction du chemin aboslue */
	public void charger ( String chemin )
	{
		this.plateau = this.ctrl.chargerPlateau(chemin);
		this.repaint();
	}

	
	/* méthode qui modifie la pioche duo avec la piocheDuo mis en paramètre */
	public void setPanelPiocheDuo(PanelPiocheDuo panelPiocheDuo ) { this.panelPiocheDuo = panelPiocheDuo ; }


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
		
		/* Que pour le mode solo */
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
		this.lstSegmentDessines .clear();

		/* que en mode solo */
		if ( !this.ctrl.estDuo() )
			this.frame.getPanelPioche().reinitialiserAffichage();
		
		this.repaint();
	}


	/* méthode qui modifie si le est actif ou non */
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

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3)); // épaisseur en pixels
			g2.setColor(Color.RED);
			g2.drawRect(x, y, this.tailleCasePlateau, this.tailleCasePlateau);
			g2.setStroke(new BasicStroke(1)); // reset pour ne pas affecter le reste du dessin
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
