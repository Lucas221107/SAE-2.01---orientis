package conception.source.ihm;

import conception.source.Controleur;

import javax.swing.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelPlateau extends JPanel
{
	private final int TAILLE_CASE = 50;

	private int largeur;
	private int hauteur;

	private Color[][] tabColors;
	
	private boolean interactif;
	private PanelSelectionBiome panelBiome;
	private Controleur          ctrl;

	public PanelPlateau( Controleur ctrl)
	{		
		this.largeur    = 0;
		this.hauteur    = 0;
		this.ctrl       = ctrl;
		this.interactif = false;

		this.addMouseListener( new GererSouris());
	}



	private class GererSouris extends MouseAdapter
	{

		public void mouseClicked ( MouseEvent e)
		{
	
			if ( ! PanelPlateau.this.interactif )
				return;
			
			if ( largeur == 0 || hauteur == 0)
				return;

			if (tabColors == null)
				return;

			int lig = e.getX() / TAILLE_CASE;
			int col = e.getY() / TAILLE_CASE;

			if ( col >= 0 && col < largeur && lig >= 0 && lig < hauteur)
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
	}


	public void setPanelSelectionBiomes ( PanelSelectionBiome panel)
	{
		this.panelBiome = panel;
	}


	public void dessinerPlateau ( int hauteur, int largeur)
	{
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.tabColors = new Color[largeur][hauteur];
		repaint();
	}


	public void paintComponent ( Graphics g)
	{
		super.paintComponent(g);

		if ( this.largeur == 0 || this.hauteur == 0)
			return;


		for ( int cptLargeur = 0; cptLargeur < this.largeur; cptLargeur ++)
		{
			for ( int cptHauteur = 0; cptHauteur < this.hauteur; cptHauteur ++)
			{

				int x = cptLargeur * TAILLE_CASE;				int y = cptHauteur * TAILLE_CASE;

				if (tabColors[cptLargeur][cptHauteur] != null)
				{
					g.setColor(tabColors[cptLargeur][cptHauteur]);
					g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
				}

				g.setColor( Color.BLACK);
				g.drawRect( x, y, TAILLE_CASE, TAILLE_CASE);
			}
		}
	}



	public void colorierCase(int col, int lig, Color couleur)
	{
		if (tabColors == null) return;

		if (col >= 0 && col < largeur && lig >= 0 && lig < hauteur)
		{
			tabColors[col][lig] = couleur;
			repaint();
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


}
