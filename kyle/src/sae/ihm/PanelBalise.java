package sae.ihm;

import sae.Controleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelBalise extends JPanel
{

	private JPanel panelBalise ; 
	private final int nbBalise = 5;
	private PanelPlateau panelPlateau;

	private final int tailleCase = 50;
	private final int nbCasesLargeur = 8;
	private final int nbCasesHauteur = 8 ;

	public PanelBalise(FramePrincipale frame , PanelPlateau panelPlateau , Controleur ctrl)
	{
		GereSouris gereSouris = new GereSouris();
		this.setLayout(new BorderLayout() );
		this.panelPlateau = panelPlateau;
		this.panelBalise = new JPanel(new GridLayout(10,1));
		//tableau de nos balises
		for (int cpt = 0; cpt < this.nbBalise; cpt++)
		{
			ImageIcon img = new ImageIcon("../sae/ihm/images/balise_" + cpt + ".png");
			JLabel lblBalise = new JLabel(img);
			
			lblBalise.addMouseListener(gereSouris);
			lblBalise.addMouseMotionListener(gereSouris);
			this.panelBalise.add(lblBalise);
		}
		this.add(this.panelBalise) ; 

	}

	/*========================================*/

	private class GereSouris extends MouseAdapter
	{
		private JLabel lblBaliseActive;
		private JFrame frameParent;
		private JPanel panelAvant;
		private int posX;
		private int posY;
		private int indexOrigine;

		public void mousePressed(MouseEvent e)
		{
			if (this.frameParent == null)
			{
				this.frameParent = (JFrame)SwingUtilities.getWindowAncestor(PanelBalise.this);//trouve frame
				JPanel panelOpaque = new JPanel();  
				panelOpaque.setOpaque(false);//on met un panel devant qui sera pas opaque donc transparent 
				this.frameParent.setGlassPane(panelOpaque);
				this.panelAvant = (JPanel)this.frameParent.getGlassPane();

			}

			if (e.getSource() instanceof JLabel)
			{
				this.lblBaliseActive = (JLabel)e.getSource();
				this.posX = e.getX();
				this.posY = e.getY();
				this.indexOrigine = panelBalise.getComponentZOrder(this.lblBaliseActive);//on prend le point d'origine du clic
			}
		}

		public void mouseDragged(MouseEvent e)
		{
			if (this.lblBaliseActive != null)
			{
				if (!this.panelAvant.isVisible())
				{
					this.panelAvant.setLayout(null);
					Point p = SwingUtilities.convertPoint(this.lblBaliseActive,0,0,this.panelAvant);

					this.lblBaliseActive.setLocation(p);

					this.panelAvant.add(this.lblBaliseActive);
					this.panelAvant.setVisible(true);
				}

				int dx = e.getX() - this.posX;
				int dy = e.getY() - this.posY;

				this.lblBaliseActive.setLocation(this.lblBaliseActive.getX() + dx , this.lblBaliseActive.getY() + dy);

				this.panelAvant.repaint();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			if (this.lblBaliseActive == null) return;

			Point p = SwingUtilities.convertPoint(this.lblBaliseActive,e.getPoint(),panelPlateau);

			int cptCol = p.x / tailleCase; // determine pos dans le tableau 
			int cptLig = p.y / tailleCase;

			this.panelAvant.remove(this.lblBaliseActive);
			this.panelAvant.setVisible(false);

			if(cptLig >= 0 &&
				cptLig < nbCasesHauteur &&
				cptCol >= 0 &&
				cptCol < nbCasesLargeur
			)
			{
				if (panelPlateau.estLibre(cptLig, cptCol))
				{
					Image img =((ImageIcon)this.lblBaliseActive.getIcon()).getImage();

					panelPlateau.ajouterBalise(cptLig,cptCol,img);

					panelBalise.remove(this.lblBaliseActive);
					panelBalise.repaint();
				}
				else
				{
					panelBalise.add(this.lblBaliseActive,this.indexOrigine);
					panelBalise.repaint();
				}
			}
			else
			{
				panelBalise.add(this.lblBaliseActive,this.indexOrigine);
				panelBalise.repaint();
			}

			this.lblBaliseActive = null;
		}
	}

	


}