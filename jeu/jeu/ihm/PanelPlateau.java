package jeu.ihm;

import jeu.Controleur;
import jeu.metier.Plateau;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.Color;
import java.awt.Image;

public class PanelPlateau extends JPanel
{
	private final int NB_BALISE_DIFFERENTE = 5;

	private FramePrincipale frame;
	private Controleur      ctrl ;

	private Plateau         plateau;

	private int[]           tabBaliseSelectionnee;
	private int             numFanionCourant     ;
	private List<int[]>     lstBaliseDisponible  ;
	private List<int[]>     lstSegmentDessines   ;
	private boolean         estActif             ;
	private PanelPiocheDuo panelPiocheDuo        ;

	private HashMap<Integer, Image> imageMap;
	
	public PanelPlateau ( FramePrincipale frame, Controleur ctrl)
	{

		this.ctrl  = ctrl ;
		this.frame = frame;


		this.numFanionCourant = - 1;
		this.estActif = true ; //obligé pour la partie solo 
		this.tabBaliseSelectionnee = null;
		this.lstBaliseDisponible   = new ArrayList<>();
		this.lstSegmentDessines    = new ArrayList<>(); 


		this.imageMap = new HashMap<>();

		for ( int cpt = 1; cpt <= this.NB_BALISE_DIFFERENTE; cpt ++ )
			this.imageMap.put( cpt, new ImageIcon("../jeu/ihm/images/balise_" + cpt + ".png").getImage());

		for ( int cpt = 6; cpt <= this.NB_BALISE_DIFFERENTE * 2; cpt ++ )
			this.imageMap.put( cpt, new ImageIcon("../jeu/ihm/images/balise_depart_bleu_" + ( cpt - this.NB_BALISE_DIFFERENTE )+ ".png").getImage());
	

		GererSouris gererSouris = new GererSouris();
		this.addMouseListener       ( gererSouris );
		this.addMouseMotionListener ( gererSouris );
	}

	/*===================================================================== */

	private class GererSouris extends MouseAdapter
	{

		public void mouseClicked ( MouseEvent e )
		{
			if (!PanelPlateau.this.estActif) return; //partie duo 

			int x = e.getX();
			int y = e.getY();

			if ( PanelPlateau.this.plateau == null || PanelPlateau.this.ctrl.getPartie() == null )
				return;

			int tailleCase = 50;
			int decallageX = ( PanelPlateau.this.getWidth()  - PanelPlateau.this.plateau.getNbColonnes() * tailleCase ) / 2;
			int decallageY = ( PanelPlateau.this.getHeight() - PanelPlateau.this.plateau.getNbLignes  () * tailleCase ) / 2;

			int lig = ( y - decallageY ) / tailleCase;
			int col = ( x - decallageX ) / tailleCase;

			if ( lig < 0 || lig > PanelPlateau.this.plateau.getNbLignes() || 
				 col < 0 || col > PanelPlateau.this.plateau.getNbColonnes() )
				 return;

			if ( ! PanelPlateau.this.ctrl.aUneBalise(lig, col))
				return;

			if ( PanelPlateau.this.tabBaliseSelectionnee == null )
			{
				if ( PanelPlateau.this.numFanionCourant == -1 )
					return;

				PanelPlateau.this.tabBaliseSelectionnee = new int[] { lig, col};
				PanelPlateau.this.lstBaliseDisponible   = PanelPlateau.this.ctrl.getVoisinDisponible(lig, col, PanelPlateau.this.numFanionCourant);
				PanelPlateau.this.repaint();
				return;
			}

			for ( int[] baliseDispo : PanelPlateau.this.lstBaliseDisponible)
			{
				if ( baliseDispo[0] == lig && baliseDispo[1] == col )
				{
					int ligBaliseSelectionnee = PanelPlateau.this.tabBaliseSelectionnee[0];
					int colBaliseSelectionnee = PanelPlateau.this.tabBaliseSelectionnee[1];

					PanelPlateau.this.ctrl.capturerBalise( ligBaliseSelectionnee, colBaliseSelectionnee, lig, col);


					PanelPlateau.this.lstSegmentDessines.add ( new int[]  { ligBaliseSelectionnee, colBaliseSelectionnee, lig, col});
					if (PanelPlateau.this.panelPiocheDuo != null)
    					PanelPlateau.this.panelPiocheDuo.joueurAJoue();
					PanelPlateau.this.tabBaliseSelectionnee = null;
					PanelPlateau.this.lstBaliseDisponible.clear();
					PanelPlateau.this.repaint();
					return;
				}
			}

			PanelPlateau.this.tabBaliseSelectionnee = null;
			PanelPlateau.this.lstBaliseDisponible.clear();
			PanelPlateau.this.repaint();

		}
	}

	public void charger ( String chemin )
	{
		this.plateau = this.ctrl.chargerPlateau(chemin);
		this.repaint();
	}
	public void setActif(boolean actif) { this.estActif = actif; } //utile pour la partie duo uniquement 


	public void paintComponent ( Graphics g)
	{

		super.paintComponent(g);

		if ( this.plateau == null)
			return;

		Graphics2D g2d = ( Graphics2D ) g ;

		int tailleCase = 50;

		int nbLig = this.plateau.getNbLignes  ();
		int nbCol = this.plateau.getNbColonnes();

		//Calcul du decallage pour centrer le plateau
		int decallageX = ( this.getWidth()  - nbCol * tailleCase) / 2;
		int decallageY = ( this.getHeight() - nbLig * tailleCase) / 2;

		for (int lig = 0; lig < nbLig; lig ++)
		{
			for ( int col = 0; col < nbCol; col ++)
			{
				int x = decallageX + col * tailleCase ;
				int y = decallageY + lig * tailleCase ;

				// colorier les cases de la couleur du biome
				g.setColor( this.ctrl.getColorBiome(lig, col));
				g.fillRect(x, y, tailleCase, tailleCase);
			}
		}

		this.dessinerLiaison(g2d, decallageX, decallageY);

		for (int lig = 0; lig < nbLig; lig ++)
		{
			for ( int col = 0; col < nbCol; col ++)
			{

				int x = decallageX + col * tailleCase ;
				int y = decallageY + lig * tailleCase ;

				// ajout des balises
				if ( this.ctrl.aUneBalise(lig, col) )
				{

					g.drawImage( this.imageMap.get( this.ctrl.getNumeroBalise( lig, col)), x, y, tailleCase, tailleCase, this);

				}
			}
		}

				
		if ( this.ctrl.getPartie() != null)
		{

			int x = decallageX + this.ctrl.getColBaliseDepartJoueur() * tailleCase;
			int y = decallageY + this.ctrl.getLigBaliseDepartJoueur() * tailleCase;

			g.setColor( Color.RED );
			g.drawRect( x, y, tailleCase, tailleCase);
		}


		if ( this.tabBaliseSelectionnee != null )
		{
			int x = decallageX + this.tabBaliseSelectionnee[1] * tailleCase;
			int y = decallageY + this.tabBaliseSelectionnee[0] * tailleCase;
			
			g.setColor( Color.YELLOW );
			g.drawRect( x, y, tailleCase, tailleCase);
		}

		g.setColor(Color.ORANGE );
		for ( int[] baliseDispo : this.lstBaliseDisponible)
		{
			int x = decallageX + baliseDispo[1] * tailleCase;
			int y = decallageY + baliseDispo[0] * tailleCase;

			g.fillRect(x, y, tailleCase, tailleCase);
		}


		g.setColor( Color.BLUE);
		for ( int[] segment : this.lstSegmentDessines)
		{
			int x1 = decallageX + segment[1] * tailleCase + tailleCase / 2;
			int y1 = decallageY + segment[0] * tailleCase + tailleCase / 2;
			int x2 = decallageX + segment[3] * tailleCase + tailleCase / 2;
			int y2 = decallageY + segment[2] * tailleCase + tailleCase / 2;

			g.drawLine(x1, y1, x2, y2);
		}
		
	}


	public void dessinerLiaison ( Graphics g, int decallageX, int decallageY )
	{
		g.setColor( Color.BLACK );

		int tailleCase = 50;

		for (int lig = 0; lig < this.ctrl.getNbLigne(); lig++)
		{
			for (int col = 0; col < this.ctrl.getNbColonnes(); col++)
			{
				if (! this.ctrl.aUneBalise(lig, col)) 
					continue;

				int xCourant = decallageX + col * tailleCase + tailleCase / 2;
				int yCourant = decallageY + lig * tailleCase + tailleCase / 2;

				for (int[] voisin : this.ctrl.getVoisins(lig, col))
				{
					int xVoisin = decallageX + voisin[1] * tailleCase + tailleCase / 2;
					int yVoisin = decallageY + voisin[0] * tailleCase + tailleCase / 2;

					g.drawLine(xCourant, yCourant, xVoisin, yVoisin);
				}
			}
		}

	}


	public void setFanionCourant ( int numFanion )
	{
		this.numFanionCourant      = numFanion ;
		this.tabBaliseSelectionnee = null      ;
		this.lstBaliseDisponible.clear();
		this.repaint();
	}


	public void reset()
	{
		this.plateau = null;
		repaint();
	}

	

	public void setPanelPiocheDuo(PanelPiocheDuo p) { this.panelPiocheDuo = p; }
}
