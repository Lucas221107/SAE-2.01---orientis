package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.BorderLayout;

public class PanelChargementPlateau extends JPanel implements ActionListener
{
	
	/*- - - - - - - - - - - - -*/
	/* Constantes des couleurs */
	/*- - - - - - - - - - - - -*/
	private final Color VERT_FORET     = new Color( 40,  67,  43);
    private final Color ORANGE_BALISE  = new Color(242, 100,  25);
    private final Color BEIGE_CARTE    = new Color(245, 238, 220);
    private final Color BEIGE_BOUTON   = new Color(230, 218, 196);
    private final Color TEXTE_FONCE    = new Color( 51,  35,  21);

	
	/*- - - - - - - - - - - - -*/
	/* Attributs               */
	/*- - - - - - - - - - - - -*/
	private FramePrincipale frame        ;
	private StyleComposante style        ;
	private PanelPlateau    panelPlateau ;
	private Controleur      ctrl         ;

	private BarJMenuPerso   maBarre      ;
	
	private JButton         btnCommencer ;
	private JButton         btnRetour    ;
	private JButton         btnAnnuler   ;

	private Image           imgFond      ;

	private JTextField      txtNomJoueur ;


	/*- - - - - - - - - - - - -*/
	/* Constructeur            */
	/*- - - - - - - - - - - - -*/
	public PanelChargementPlateau ( FramePrincipale frame, Controleur ctrl, StyleComposante style, PanelPlateau panelPlateau)
	{
		this.setLayout( new BorderLayout());

		/*- - - - - - - - - - - - - */
		/* Création des composantes */
		/*- - - - - - - - - - - - - */

		/* Création de font pour les labels ainsi que les boutons */
        Font fontBouton = new Font("SansSerif", Font.BOLD, 13);
		
		this.ctrl         = ctrl         ;
		this.frame        = frame        ;
		this.style        = style        ;
		this.panelPlateau = panelPlateau ;

		this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond_multi_connexion.png");

		this.maBarre = new BarJMenuPerso( this.ctrl );

		JLabel lblNomJoueur = new JLabel("Entrez un pseudo");
		this.txtNomJoueur = new JTextField(10);


		/*============================ boutons ===========================*/
		this.btnAnnuler   = new JButton("Annuler");
		this.style.styleBouton( this.btnAnnuler, this.VERT_FORET, Color.WHITE, fontBouton );

		this.btnRetour    = new JButton("Retour");
		this.style.styleBouton( this.btnRetour, this.BEIGE_BOUTON, this.TEXTE_FONCE, fontBouton );

		this.btnCommencer = new JButton("Commencer la partie");
		this.style.styleBouton( this.btnCommencer, this.VERT_FORET, Color.WHITE, fontBouton );


		/*============================ panel ===========================*/
		JPanel panelBtn   = new JPanel();
		panelBtn.setOpaque( false );

		JPanel panelGauche = new JPanel( new FlowLayout( FlowLayout.LEFT));
		panelGauche.setOpaque(false);

		this.panelPlateau.setOpaque(false);



		/*- - - - - - - - - - - - - */
		/* Ajout des composantes    */
		/*- - - - - - - - - - - - - */
		
		//panel Gauche
		panelGauche.add ( lblNomJoueur      );
		panelGauche.add ( this.txtNomJoueur );


		//panel btn
		panelBtn.add( this.btnRetour    );
		panelBtn.add( this.btnAnnuler   );
		panelBtn.add( this.btnCommencer );

		// panel this
		this.add( panelBtn           , BorderLayout.SOUTH );
		this.add( this.panelPlateau  , BorderLayout.CENTER);
		this.add( panelGauche        , BorderLayout.WEST  );


		/*- - - - - - - - - - - - - - - -*/
		/* Activation des composantes    */
		/*- - - - - - - - - - - - - - - -*/
		this.btnAnnuler  .addActionListener(this);
		this.btnCommencer.addActionListener(this);
		this.btnRetour   .addActionListener(this);
	}

	/*=========================== Méthodes ============================*/

	public void actionPerformed (ActionEvent e)
	{
		if ( e.getSource() == this.btnRetour)
		{
			this.panelPlateau.reset();   // <- réinitialise le plateau
			this.ctrl.resetPlateau();    // <- réinitialise le plateau dans le controleur
			this.frame.switchPanel("accueil"); // <- va au panel suivant
		}

		if ( e.getSource() == this.btnCommencer)
		{
			if ( this.ctrl.getCheminPlateau() == null )
			{
				JOptionPane.showMessageDialog(this, "Veuillez d'abord sélectionner un fichier"); // <- affiche un popup pour une sécurité
				return;
			}

			if ( this.txtNomJoueur.getText().isEmpty())
			{
				JOptionPane.showConfirmDialog(this, "Veuillez saisir votre pseudo");
				return;
			}

			this.ctrl.creerPartie( this.txtNomJoueur.getText());
			this.frame.switchPanel("solo");
		}

		if ( e.getSource() == this.btnAnnuler )
		{
			
		}
	}

	/* méthode permettant de charger le plateau pour pouvoir l'afficher grâce au Controleur */
	public void chargerPlateau()
	{
		this.panelPlateau.charger( this.ctrl.getCheminPlateau());
	}


	/* méthode paintComponent permettant de mettre une image de fond*/
	public void paintComponent ( Graphics g )
	{
		super.paintComponent( g );
		g.drawImage( this.imgFond, 0, 0,  this.getWidth(), this.getHeight(), this);
	}

	/*- - - - - - - - - - - - - */
	/* Accesseurs               */
	/*- - - - - - - - - - - - - */
	public BarJMenuPerso getMaBarre()   { return this.maBarre; }
}
