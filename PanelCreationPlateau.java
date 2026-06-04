import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.Image;
import java.awt.Color; 
import java.awt.Dimension;



public class PanelCreationPlateau extends JPanel
{
    private JPanel panelPlateau  ; 
    private JPanel panelBalise   ; 
    private JPanel[][] tabCases ;
    private int largeurPlateau ; 
    private int longeurPlateau ; 
    private int[] tabBalise ; 
    private int nbBalise ; 
	private JLabel pieceSelectionnee = null; 

    public PanelCreationPlateau()
    {
		GereSouris gereSouris = new GereSouris();
        this.setLayout(new BorderLayout()) ; 
        //---Création des composants ---//
        this.panelBalise = new JPanel(new GridLayout(1, 5)); 
        this.panelPlateau = new JPanel(new GridLayout(8,8)) ;
        this.tabCases =  new JPanel[8][8];
        this.tabBalise = new int[this.nbBalise = 10] ; 
       

        this.tabBalise[0]  = 1 ; 
        this.tabBalise[1] = 2 ; 
        this.tabBalise[2] = 3 ; 
        this.tabBalise[3] = 4 ; 
        this.tabBalise[4] = 5 ; 

        for(int cpt = 0 ; cpt < this.tabBalise.length ; cpt++)
        {
            ImageIcon img = new ImageIcon("./images/balise_" + cpt + ".png");
            JLabel lblBalise = new JLabel(img);
			lblBalise.addMouseListener(gereSouris);
			lblBalise.addMouseMotionListener(gereSouris);
			lblBalise.setPreferredSize(new Dimension(50,50));
            this.panelBalise.add(lblBalise);
        }

        for (int cptlig = 0; cptlig < 8; cptlig++) 
        {
            for (int cptcol = 0; cptcol < 8; cptcol++) 
            {
                JPanel casePlateau = new JPanel();
                casePlateau.setBackground(Color.GRAY);
                casePlateau.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                casePlateau.setPreferredSize(new Dimension(60, 60));
                this.tabCases[cptlig][cptcol] = casePlateau;
                this.panelPlateau.add(casePlateau);
                
            }
        }



        this.add(this.panelPlateau, BorderLayout.NORTH);
        this.add(this.panelBalise, BorderLayout.CENTER); 
        
        

        
    }

	private class GereSouris extends MouseAdapter
	{
		private JLabel pieceActive = null;
		private int posX, posY;

		public void mousePressed(MouseEvent e)
		{
			if (e.getSource() instanceof JLabel)
			{
				this.pieceActive = (JLabel) e.getSource();
				this.posX = e.getX();
				this.posY = e.getY();
			}
		}

		public void mouseDragged(MouseEvent e)
		{
			if (this.pieceActive != null)
			{
				// déplace du delta comme dans le cours
				int dx = e.getX() - this.posX;
				int dy = e.getY() - this.posY;
				this.pieceActive.setLocation(
					this.pieceActive.getX() + dx,
					this.pieceActive.getY() + dy
				);
				this.posX = e.getX();
				this.posY = e.getY();
				PanelCreationPlateau.this.repaint();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			if (this.pieceActive != null)
			{
				java.awt.Point p = javax.swing.SwingUtilities.convertPoint(
					this.pieceActive, e.getPoint(), panelPlateau
				);
				java.awt.Component cible = panelPlateau.getComponentAt(p);
				if (cible instanceof JPanel)
				{
					((JPanel) cible).add(this.pieceActive);
					((JPanel) cible).revalidate();
					((JPanel) cible).repaint();
				}
				this.pieceActive = null;
			}
		}
	}





}