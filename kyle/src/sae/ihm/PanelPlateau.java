package sae.ihm;

import sae.Controleur;

import javax.swing.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;

public class PanelPlateau extends JPanel
{
	private final int TAILLE_CASE = 50;

	private int nbLig;
	private int nbCol;
	private int nbZoneMax;
	private int nbZonePlacer;
	private int cptBalise = 0 ;

	private Color[][] tabColors;
	private Image[][] tabBalisePlateau;
	
	private boolean interactif;
	private boolean enCoursDessin;
	private boolean toutEstPosee ;

	private PanelSelectionBiome panelBiome;
	private Controleur          ctrl;

	private final int tailleCase = 50;
	private final int nbCasesLargeur = 8;
	private final int nbCasesHauteur = 8 ;
	private final int nbBalise = 5;

	public PanelPlateau( Controleur ctrl)
	{		
		this.nbLig         = 0;
		this.nbCol         = 0;
		this.nbZoneMax     = 0;
		this.ctrl          = ctrl;
		this.enCoursDessin = false;
		this.interactif    = false;
		this.nbZonePlacer  = 1;
		

		this.tabBalisePlateau = new Image[nbCasesHauteur][nbCasesLargeur];
		this.setPreferredSize(new Dimension(nbCasesHauteur * tailleCase,nbCasesLargeur * tailleCase));

		GererSouris gererSouris = new GererSouris();
		this.addMouseListener       ( gererSouris );
		this.addMouseMotionListener ( gererSouris );
	}



	private class GererSouris extends MouseAdapter
	{
		int cptBalise = getcptBalises() ; 
		int nbBalise = getNbBalises();
		public void mouseClicked(MouseEvent e)
		{
			int lig = e.getY() / TAILLE_CASE;
			int col = e.getX() / TAILLE_CASE;
				
			
			if ( (lig >= 0 && lig < nbCasesHauteur && col >= 0 && col < nbCasesLargeur) && this.cptBalise < this.nbBalise)
			{
				if (PanelPlateau.this.tabBalisePlateau[lig][col] != null)
				{
					dessinerDepart(lig, col, getGraphics());
				}
			}
		}
		public void mousePressed ( MouseEvent e)
		{
			
			if ( ! PanelPlateau.this.interactif )
				return;
			
			if ( PanelPlateau.this.nbLig == 0 || PanelPlateau.this.nbCol == 0)
				return;

			if ( PanelPlateau.this.tabColors == null)
				return;

			if ( PanelPlateau.this.nbZonePlacer >= PanelPlateau.this.nbZoneMax)			
				return;

			PanelPlateau.this.enCoursDessin = true;
			PanelPlateau.this.colorierSiPossible(e.getX(), e.getY());
		}


		public void mouseDragged ( MouseEvent e)
		{
			if ( ! PanelPlateau.this.interactif )
				return;
			
			if ( PanelPlateau.this.nbLig == 0 || PanelPlateau.this.nbCol == 0)
				return;

			if ( !PanelPlateau.this.enCoursDessin)
				return;

			PanelPlateau.this.colorierSiPossible(e.getX(), e.getY());
		}


		public void mouseReleased ( MouseEvent e)
		{
			if (! PanelPlateau.this.enCoursDessin)
				return;

			PanelPlateau.this.enCoursDessin = false;
			PanelPlateau.this.nbZonePlacer ++;
			repaint();
		}
	}


	public void dessinerPlateau ( int lig, int col)
	{
		this.nbLig = lig;
		this.nbCol = col;
		this.tabColors = new Color[this.nbLig][this.nbCol];
		repaint();
	}


	public void colorierSiPossible ( int x, int y)
	{
		int lig = y / TAILLE_CASE;
		int col = x / TAILLE_CASE;

		if ( col >= 0 && col < this.nbCol && lig >= 0 && lig < this.nbLig)
		{
			if ( tabColors[lig][col] == null)
			{
				String biome = PanelPlateau.this.panelBiome.getNomBiome();
				Color coul = PanelPlateau.this.ctrl.getColorBiome( biome );
				tabColors[lig][col] = coul;
				repaint();
			}
		}
}


	public void paintComponent ( Graphics g)
	{
		super.paintComponent(g);

		if ( this.nbLig == 0 || this.nbCol == 0)
			return;


		for ( int cptLig = 0; cptLig < this.nbLig; cptLig ++)
		{
			for ( int cptCol = 0; cptCol < this.nbCol; cptCol ++)
			{

				int x = cptCol * TAILLE_CASE;
				int y = cptLig * TAILLE_CASE;

				if (tabColors[cptLig][cptCol] != null)
				{
					g.setColor(tabColors[cptLig][cptCol]);
					g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
				}

				g.setColor( Color.BLACK);
				g.drawRect( x, y, TAILLE_CASE, TAILLE_CASE);
			}
		}

		//dessine quand ont drop /*  
		for (int cptLig = 0; cptLig < nbCasesHauteur; cptLig++)
		{
			for (int cptCol = 0; cptCol < nbCasesLargeur; cptCol++)
			{
				int x = cptCol * tailleCase;
				int y = cptLig * tailleCase;

				if (this.tabBalisePlateau[cptLig][cptCol] != null)
				{
					g.drawImage(this.tabBalisePlateau[cptLig][cptCol],x,y,tailleCase,tailleCase,null); //nul car image deja chargeé
				}
			}
		}
		
	}



	public void setPanelBiome( PanelSelectionBiome panelBiome)
	{
		this.panelBiome = panelBiome;
	}

	public void setInteractif ( boolean b)
	{
		this.interactif = b;
	}

	public void setNbBiomeMax ( int i)   { this.nbZoneMax = i; }

	public boolean estLibre(int lig, int col)
	{
		return this.tabBalisePlateau[lig][col] == null;
	}

	public void ajouterBalise(int lig, int col, Image img)
	{
		this.tabBalisePlateau[lig][col] = img;
		repaint();
	}

	public int getNbLig()  { return this.nbLig;  }
	public int getNbCol()  { return this.nbCol;  }
	public int getNbBalises()  { return this.nbBalise;  }
	public int getcptBalises()  { return this.cptBalise;  }



	public Color getCaseColor ( int lig, int col)
	{
		return this.tabColors[lig][col];
	}
	public void dessinerDepart(int lig, int col, Graphics g)
		{
		int centreX = col * tailleCase + tailleCase / 2;
		int centreY = lig * tailleCase + tailleCase / 2;
		int rayon = 15;
		g.setColor(Color.RED);
		g.drawOval(centreX - rayon, centreY - rayon, rayon * 2, rayon * 2);
	}

}
