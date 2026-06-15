package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class FramePrincipale extends JFrame
{
	/*----------------*/
	/* Attributs      */
	/*----------------*/
	private PanelAccueil           panelAccueil    ;
	private PanelChargementPlateau panelChargement ;
	private PanelChargementDuo     panelChargementDuo ;
	private PanelPlateau           panelPlateau    ;
	private PanelSolo              panelSolo       ;
	private PanelDuo               panelDuo        ;
	private StyleComposante        style           ;
	private PanelRegle             panelRegle      ;
	private Controleur             ctrl            ;

	private int                    hauteur         ;
	private int                    largeur         ;

	private CardLayout             cardLayout      ;

	private JPanel                 panelPrincipal  ;

	/*----------------------*/
	/*    Constructeur      */
	/*----------------------*/
	public FramePrincipale ( Controleur ctrl)
	{

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.hauteur = (int) ( tailleEcran.getHeight() - 45);
		this.largeur = (int) ( tailleEcran.getWidth ()     );
		
		
		this.setTitle   ("Orientis");
		this.setLocation( 0, 0);
		this.setSize    ( this.getDimensionPanel() );

		/*--------------------------------*/
		/*    Création des composantes    */
		/*--------------------------------*/

		this.ctrl = ctrl;

		this.style          = new StyleComposante();  // <- Classe qui permet de donner du style au composantes

		this.cardLayout     = new CardLayout(); // <- layout permettant de ne posseder qu'une seule frame comme un paquet de carte
		this.panelPrincipal = new JPanel ( this.cardLayout );


		this.panelPlateau = new PanelPlateau( this,             this.ctrl );
		this.panelSolo    = new PanelSolo   ( this, this.style, this.ctrl );
		this.panelDuo     = new PanelDuo    ( this, this.style, this.ctrl );
		
		this.panelAccueil    = new PanelAccueil          ( this                                           );
		this.panelRegle      = new PanelRegle            ( this                                           );
		this.panelChargement = new PanelChargementPlateau( this, this.ctrl, this.style, this.panelPlateau );
		this.panelChargementDuo = new PanelChargementDuo( this, this.ctrl, this.style);


		/*------------------------------------*/
		/*  Positionnement des composantes    */
		/*------------------------------------*/

		/* ajout des panels pour le cardLayout */

		this.panelPrincipal.add( this.panelAccueil   , "accueil"    );
		this.panelPrincipal.add( this.panelRegle     , "regle"      );
		this.panelPrincipal.add( this.panelChargement, "chargement" );
		this.panelPrincipal.add( this.panelChargementDuo, "chargementDuo");
		this.panelPrincipal.add( this.panelSolo      , "solo"       );
		this.panelPrincipal.add( this.panelDuo       , "duo");


		this.cardLayout.show ( this.panelPrincipal, "accueil"); // <- le premier panel qu'on veut afficher

		this.add( this.panelPrincipal);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/*-------------------*/
	/*    Méthodes       */
	/*-------------------*/

	public Dimension              getDimensionPanel ()  { return new Dimension ( this.largeur, this.hauteur ) ; } // <- return la dimension de l'ecran
	public PanelPlateau           getPanelPlateau   ()  { return this.panelPlateau                            ; } // <- return le plateau de jeu
	public PanelPioche            getPanelPioche    ()  { return this.panelSolo.getPanelPioche()              ; } // <- return le panelPioche
	public PanelChargementPlateau getPanelChargement()  { return this.panelChargement                         ; } // <- return le PanelDeChargement
	public PanelSolo              getPanelSolo      ()  { return this.panelSolo                               ;	}
	public PanelChargementDuo     getPanelChargementDuo() { return this.panelChargementDuo ; }
	public PanelDuo               getPanelDuo          () { return this.panelDuo ; }

	/* méthode qui permet de changer de panel en appuyant sur le bouton */
	public void switchPanel ( String nom )
	{

		if ( nom.equals("solo"))
		{
			this.panelChargement.remove(this.panelPlateau); // <- efface le plateau de panelChargement
			this.panelChargement.revalidate(); // <- redispose le panel
			this.panelSolo      .add( this.panelPlateau, BorderLayout.CENTER); // <- ajoute le plateau dans le panel Solo
			this.panelPlateau   .setOpaque(false ); // <- rend le panelPlateau non opaque
			this.panelSolo      .revalidate(); // <- redispose le panel Solo
			this.panelPlateau   .repaint();    // <- redessine le PanelPlateau
		}

		if (nom.equals("duo"))
		{
			// Ne pas rappeler charger() — le plateau est déjà dans this.ctrl.plateau
			this.panelDuo.getPanelPlateauJ1().setOpaque(false);
			this.panelDuo.getPanelPlateauJ2().setOpaque(false);
			
			// On assigne le plateau déjà chargé directement
			this.panelDuo.getPanelPlateauJ1().setPlateau(this.ctrl.getPlateau());
			this.panelDuo.getPanelPlateauJ2().setPlateau(this.ctrl.getPlateau());
			
			this.panelDuo.revalidate();
			this.panelDuo.repaint();
			this.panelDuo.ajouterPlateaux(this.panelDuo.getPanelPlateauJ1(), this.panelDuo.getPanelPlateauJ2());
		}

		if ( nom.equals("chargement"))
			this.setJMenuBar( this.panelChargement.getMaBarre());
		else if ( nom.equals("chargementDuo"))
			this.setJMenuBar( this.panelChargementDuo.getMaBarre());
		else
			this.setJMenuBar(null);

		this.cardLayout.show ( this.panelPrincipal, nom ); // <- affiche le panel avec le nom rentrer en paramètre
		this.revalidate(); // <- redispose la frame
		this.repaint();  // <- redessine la frame 
	}
}