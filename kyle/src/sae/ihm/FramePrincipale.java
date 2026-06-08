package sae.ihm;

import sae.Controleur;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class FramePrincipale extends JFrame
{
	
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private PanelConfigJeux        panelConfigJeux ;
	private PanelSelectionBiome    panelBiome      ;
	private PanelPlateau           panelPlateau    ;
	private PanelBalise            panelBalise     ; 
	private PanelChoixBaliseDepart panelChoixDepart;

	private JPanel                 panel           ;
	private JLabel                 lblNord         ;
	private int                    cpt             ;
	private int                    largeur         ;
	private int                    hauteur         ;

	private CardLayout             cardLayout      ;

	private Controleur             ctrl            ;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public FramePrincipale( Controleur ctrl)
	{
		
		this.setTitle ("Orientis");
		this.setLocation(0,0);
		this.setSize( largeur, hauteur);

		this.setLayout (new BorderLayout());
		
		
		/* Calcul de la taille de l'écran */
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		this.largeur = (int)  tailleEcran.getWidth()       ;
		this.hauteur = (int) (tailleEcran.getHeight() - 45);

		this.cpt  = 0   ;
		this.ctrl = ctrl;

		/* ============================ */
		/*     gérer la partie nord     */
		/* ============================ */

		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize( new Dimension(largeur, 50));
		panelNorth.setBorder( BorderFactory.createLineBorder( Color.BLACK));
		
		this.lblNord = new JLabel();
		this.lblNord.setFont( new Font ( "Arial", Font.BOLD, 25));

		panelNorth.add ( this.lblNord );
		this.passerEtape();

		/* ============================ */
		/*     gérer la partie Est      */
		/* ============================ */

		this.panelPlateau = new PanelPlateau( ctrl );


		/* ============================ */
		/*     gérer la partie Ouest    */
		/* ============================ */

		this.cardLayout = new CardLayout();
		this.panel      = new JPanel( this.cardLayout);

		//instanciation des panel
		this.panelBalise      = new PanelBalise           ( this, this.panelPlateau, ctrl                  );
		this.panelConfigJeux  = new PanelConfigJeux       ( this, this.panelPlateau, ctrl, this.panelBalise);
		this.panelBiome       = new PanelSelectionBiome   ( this, this.panelPlateau, ctrl                  );
		this.panelChoixDepart = new PanelChoixBaliseDepart( this, this.panelPlateau, ctrl                  );
		
		this.panelPlateau.setPanelBiome(panelBiome);


		//ajout du cardLayout
		this.panel.add ( this.panelConfigJeux , "config" );
		this.panel.add ( this.panelBiome      , "biomes" );
		this.panel.add ( this.panelBalise     , "balise" );
		this.panel.add ( this.panelChoixDepart, "depart" );
		
		cardLayout.show(this.panel, "config");
		

		this.add ( panel            , BorderLayout.WEST );
		this.add ( panelNorth       , BorderLayout.NORTH);
		this.add ( this.panelPlateau, BorderLayout.CENTER);

		this.setVisible(true);
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
	}

	/* méthode permettant de changer le panel de gauche en fonction d'une action */
	public void switchPanel ( String nom )
	{
		cardLayout.show( this.panel, nom);
	}

	/* méthode permettant de changer le panel du haut */
	public void passerEtape()
	{
		switch ( this.cpt )
		{
			case 0 : this.lblNord.setText( "Configurez le plateau "             ); break;
			case 1 : this.lblNord.setText( "Placez les biomes"                  ); break;
			case 2 : this.lblNord.setText( "Placez les balises"                 ); break;
			case 3 : this.lblNord.setText( "Selectionnez 5 balises de départs"  ); break;
		}

		this.cpt ++;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */
	public Dimension getDimensionPanel()    { return new Dimension( (int) (this.largeur * 0.375), this.hauteur); }
}
