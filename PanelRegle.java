import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.*;

public class PanelRegle extends JPanel implements ActionListener
{
	private JLabel      lblPage;
	private ImageIcon   image;
	private JButton     btnSuivant;
	private JButton     btnPrec;
	private JButton     btnAccueil;
	private JScrollPane scrollPane;

	private String[]   tabPages;
	private int        cpt;

	public PanelRegle()
	{
		
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setLayout( new BorderLayout());

		/*--------------------------------*/
		/*    Création des composantes    */
		/*--------------------------------*/
		cpt = 0;

		this.tabPages = new String[] { "page1.jpg", "page2.jpg", "page3.jpg", "page4.jpg", "page5.jpg"};
		this.image    = new ImageIcon( "./images/" + tabPages[cpt]);
		this.lblPage  = new JLabel(this.image);

		this.scrollPane = new JScrollPane( this.lblPage);
		this.scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		this.scrollPane.setPreferredSize(tailleEcran);
		
		JPanel panelBtn = new JPanel( new FlowLayout());
		panelBtn.setOpaque(false);
		
		this.btnSuivant = new JButton("page suivante");
		this.btnPrec    = new JButton("page précédante");
		this.btnAccueil = new JButton("Retour à l'accueil");

		/*--------------------------------------*/
		/*    Positionnement des composantes    */
		/*--------------------------------------*/

		panelBtn.add ( this.btnAccueil);
		panelBtn.add ( this.btnPrec   );
		panelBtn.add ( this.btnSuivant);
		
		this.add( scrollPane, BorderLayout.CENTER);
		this.add( panelBtn  , BorderLayout.SOUTH );
			
		
		/*----------------------------------*/
		/*    activation des composantes    */
		/*----------------------------------*/

		this.btnAccueil.addActionListener(this);
		this.btnPrec   .addActionListener(this);
		this.btnSuivant.addActionListener(this);
	}



	public void actionPerformed ( ActionEvent e)
	{
		
		if (e.getSource() == this.btnPrec)
		{
			if ( cpt != 0 )
			{ 
				cpt --;
			
				ImageIcon img = new ImageIcon( "./images/" + this.tabPages[cpt]);
				this.lblPage.setIcon(img);

				this.scrollPane.getVerticalScrollBar().setValue(0);
			}
		}


		if ( e.getSource() == this.btnSuivant)
		{

			if ( cpt < this.tabPages.length - 1)
			{
				cpt ++;

				ImageIcon img = new ImageIcon( "./images/" + this.tabPages[cpt]);
				this.lblPage.setIcon(img);

				this.scrollPane.getVerticalScrollBar().setValue(0);
			}
		}

		
		if ( e.getSource() == this.btnAccueil)
		{
			new FrameAccueil();
			
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

			if ( frame != null )
				frame.dispose();
		}
	}
}
