import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

public class PanelPlateau extends JPanel
{
	private final int TAILLE_CASE = 50;

	private int largeur;
	private int hauteur;

	public PanelPlateau()
	{
		this.largeur = 0;
		this.hauteur = 0;
	}


	public void dessinerPlateau ( int hauteur, int largeur)
	{
		this.largeur = largeur;
		this.hauteur = hauteur;
		repaint();
	}


	public void paintComponent ( Graphics g)
	{
		super.paintComponent(g);

		if ( this.largeur == 0 || this.hauteur == 0)
			return;

		g.setColor( Color.LIGHT_GRAY);

		for ( int cptLargeur = 0; cptLargeur < this.largeur; cptLargeur ++)
		{
			for ( int cptHauteur = 0; cptHauteur < this.hauteur; cptHauteur ++)
			{
				g.drawRect( cptLargeur * TAILLE_CASE, cptHauteur * TAILLE_CASE, TAILLE_CASE, TAILLE_CASE);
			}
		}
	}
}
