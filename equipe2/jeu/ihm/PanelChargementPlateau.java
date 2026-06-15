package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;

public class PanelChargementPlateau extends JPanel implements ActionListener
{
	
	/*- - - - - - - - - - - - -*/
	/* Constantes des couleurs */
	/*- - - - - - - - - - - - -*/
	private final Color VERT_FORET     = new Color( 40,  67,  43);
    private final Color BEIGE_BOUTON   = new Color(230, 218, 196);
    private final Color TEXTE_FONCE    = new Color( 51,  35,  21);

	/*- - - - - - - - - - - - -*/
	/* Constantes de la Fonts  */
	/*- - - - - - - - - - - - -*/
    private final Font FONT_LABEL  = new Font("SansSerif", Font.BOLD, 12);
	private final Font FONT_BOUTON = new Font("SansSerif", Font.BOLD, 13);

	
	/*- - - - - - - - - - - - -*/
	/* Attributs               */
	/*- - - - - - - - - - - - -*/
	private FramePrincipale   frame        ;
	private StyleComposante   style        ;
	private PanelPlateau      panelPlateau ;
	private Controleur        ctrl         ;

	private BarJMenuPerso     maBarre      ;
	
	private JButton           btnCommencer ;
	private JButton           btnRetour    ;
	private JButton           btnAnnuler   ;

	private Image             imgFond      ;

	private JTextField        txtNomJoueur ;

	private JComboBox<String> cbTailleCase ;


	/*- - - - - - - - - - - - -*/
	/* Constructeur            */
	/*- - - - - - - - - - - - -*/
	public PanelChargementPlateau ( FramePrincipale frame, Controleur ctrl, StyleComposante style, PanelPlateau panelPlateau)
	{
		this.setLayout( new BorderLayout());

		/*- - - - - - - - - - - - - */
		/* Création des composantes */
		/*- - - - - - - - - - - - - */
	
		this.ctrl         = ctrl         ;
		this.frame        = frame        ;
		this.style        = style        ;
		this.panelPlateau = panelPlateau ;

		this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond_multi_connexion.png");

		this.maBarre = new BarJMenuPerso( this.ctrl );

		String[] tabTaille = { "Petite (30 px)", "normale (50 px)", "Grande (70 px)"};
		this.cbTailleCase = new JComboBox<>( tabTaille );
		this.cbTailleCase.setSelectedIndex(1);


		/*============================ Labels ===========================*/
		JLabel lblNomJoueur = new JLabel("Entrez votre pseudo");
		this.txtNomJoueur = new JTextField(10);

		JLabel lblInfoFichier = new JLabel("Veuillez choisir un fichier plateau dans votre répertoire");
		this.style.styleJLabel(lblInfoFichier, FONT_LABEL, TEXTE_FONCE);

		JLabel lblInfoCase = new JLabel("Choisissez la taille des cases de votre plateau :");
		this.style.styleJLabel(lblInfoCase, FONT_LABEL, TEXTE_FONCE);


		/*============================ boutons ===========================*/
		this.btnAnnuler   = new JButton("Annuler");
		this.style.styleBouton( this.btnAnnuler, VERT_FORET, Color.WHITE, FONT_BOUTON );

		this.btnRetour    = new JButton("Retour");
		this.style.styleBouton( this.btnRetour, BEIGE_BOUTON, TEXTE_FONCE, FONT_BOUTON );

		this.btnCommencer = new JButton("Commencer la partie");
		this.style.styleBouton( this.btnCommencer, VERT_FORET, Color.WHITE, FONT_BOUTON );


		/*============================ panel ===========================*/
		JPanel panelBtn   = new JPanel();
		panelBtn.setOpaque( false );

		JPanel panelPseudo = new JPanel( new FlowLayout( FlowLayout.LEFT));
		panelPseudo.setOpaque(false);

		JPanel panelGauche = new JPanel( new GridLayout( 9, 1));
		panelGauche.setOpaque(false );

		this.panelPlateau.setOpaque(false);



		/*- - - - - - - - - - - - - */
		/* Ajout des composantes    */
		/*- - - - - - - - - - - - - */
		
		//panel Pseudo
		panelPseudo.add ( lblNomJoueur      );
		panelPseudo.add ( this.txtNomJoueur ); 
		
		//panel Gauche
		panelGauche.add ( lblInfoFichier    );
		panelGauche.add ( new JLabel()      );
		panelGauche.add ( panelPseudo       );
		panelGauche.add ( new JLabel()      );
		panelGauche.add ( lblInfoCase       );
		panelGauche.add ( this.cbTailleCase );
		panelGauche.add ( new JLabel()      );
		panelGauche.add ( new JLabel()      );
		panelGauche.add ( new JLabel()      );

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
				JOptionPane.showMessageDialog(this, "Veuillez saisir votre pseudo"); // <- affiche un popup indiquant qu'il faut écrire un pseudo
				return;
			}

			this.ctrl.creerPartie( this.txtNomJoueur.getText());

			int taille;

			switch ( this.cbTailleCase.getSelectedIndex())
			{
				case 0  : taille = 30; break;
				case 2  : taille = 70; break;
				default : taille = 50; break;
			}

			this.panelPlateau.setTailleCasePlateau(taille);
			this.frame.switchPanel("solo");
		}

		if ( e.getSource() == this.btnAnnuler )
		{
			this.panelPlateau.reset();
			this.ctrl.resetPlateau();
			this.txtNomJoueur.setText("");
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
