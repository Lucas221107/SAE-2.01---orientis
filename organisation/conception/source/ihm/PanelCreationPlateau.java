package source.ihm;

import source.Controleur;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelCreationPlateau extends JPanel
{
	private JPanel panelBalise;
	private PanelPlateau panelPlateau;

	private final int tailleCase = 50;
	private final int nbCasesLargeur = 8;
	private final int nbCasesHauteur = 8 ;
	private final int nbBalise = 5;

	public PanelCreationPlateau()
	{
		GereSouris gereSouris = new GereSouris();

		this.setLayout(new BorderLayout());

		this.panelBalise  = new JPanel(new GridLayout(1, this.nbBalise));
		this.panelPlateau = new PanelPlateau();
		//tableau de nos balises
		for (int cpt = 0; cpt < this.nbBalise; cpt++)
		{
			ImageIcon img = new ImageIcon("./images/balise_" + cpt + ".png"); 

			JLabel lblBalise = new JLabel(img);

			lblBalise.addMouseListener(gereSouris);
			lblBalise.addMouseMotionListener(gereSouris);
			this.panelBalise.add(lblBalise);
		}

		this.add(this.panelPlateau, BorderLayout.CENTER);
		this.add(this.panelBalise , BorderLayout.SOUTH);
	}

	/*======================================== /
	/===Paneau qui va acceuillir le plateau===*/

	private class PanelPlateau extends JPanel
	{
		private Image[][] tabBalisePlateau;

		public PanelPlateau()
		{
			this.tabBalisePlateau = new Image[nbCasesHauteur][nbCasesLargeur];
			this.setPreferredSize(new Dimension(nbCasesHauteur * tailleCase,nbCasesLargeur * tailleCase));
		}

		public boolean estLibre(int lig, int col)
		{
			return this.tabBalisePlateau[lig][col] == null;
		}

		public void ajouterBalise(int lig, int col, Image img)
		{
			this.tabBalisePlateau[lig][col] = img;
			repaint();
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			for (int cptLig = 0; cptLig < nbCasesHauteur; cptLig++)
			{
				for (int cptCol = 0; cptCol < nbCasesLargeur; cptCol++)
				{
					int x = cptCol * tailleCase;
					int y = cptLig * tailleCase;

					g.setColor(Color.GRAY);
					g.fillRect(x, y, tailleCase, tailleCase);//dessine rectangle remplis en gris

					g.setColor(Color.BLACK);
					g.drawRect(x, y, tailleCase, tailleCase); //contour du rectangles en noir 

					if (this.tabBalisePlateau[cptLig][cptCol] != null)
					{
						g.drawImage(this.tabBalisePlateau[cptLig][cptCol],x,y,tailleCase,tailleCase,null); //nul car image deja chargeé
					}
				}
			}
		}
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
				this.frameParent = (JFrame)SwingUtilities.getWindowAncestor(PanelCreationPlateau.this);//trouve frame 
				this.panelAvant  = (JPanel)this.frameParent.getGlassPane();
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