package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;


public class PanelImage extends JPanel
{
	private Controleur ctrl;
	private Image      img;

	public PanelImage( Controleur ctrl )
	{
		this.ctrl = ctrl;
		this.img = getToolkit().getImage( this.ctrl.getFichierImage() );
	}

	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);

	    this.img = getToolkit().getImage( this.ctrl.getFichierImage() );

		// Centrage proportionnel
        int imgW  = this.img.getWidth (this);
        int imgH  = this.img.getHeight(this);
        double ratio = Math.min((double) getWidth() / imgW, (double) getHeight() / imgH);
        int newW  = (int)(imgW * ratio);
        int newH  = (int)(imgH * ratio);
        int x     = (getWidth()  - newW) / 2;
        int y     = (getHeight() - newH) / 2;

        g.drawImage( this.img, x, y, newW, newH, this );
	}

}