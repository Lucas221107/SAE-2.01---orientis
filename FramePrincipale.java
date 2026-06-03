import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Dimension;

public class FramePrincipale extends JFrame
{
	private PanelRegle      panelRegle;
	private PanelAccueil    panelAccueil;
	private PanelConfigJeux panelConfigJeux;

	private JPanel          panel;

	private CardLayout      cardLayout;

	public FramePrincipale()
	{

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		int largeur = (int)  tailleEcran.getWidth();
		int hauteur = (int) (tailleEcran.getHeight() - 45);

		this.setTitle ("Orientis");
		this.setLocation(0,0);
		this.setSize( largeur, hauteur);

		// création du cardLayout
		this.cardLayout = new CardLayout();
		this.panel      = new JPanel( this.cardLayout);

		//instanciation des panels
		this.panelAccueil    = new PanelAccueil   ( this );
		this.panelRegle      = new PanelRegle     ( this );
		this.panelConfigJeux = new PanelConfigJeux( this );


		cardLayout.show( this.panel, "Accueil");

		//ajout du cardLayout
		this.panel.add ( this.panelAccueil   , "Accueil");
		this.panel.add ( this.panelRegle     , "Regle"  );
		this.panel.add ( this.panelConfigJeux, "config" );

		this.add ( this.panel );

		this.setVisible(true);
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
	}


	public void switchPanel ( String nom )
	{
		cardLayout.show( this.panel, nom);
	}

}
