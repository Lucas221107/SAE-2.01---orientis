package jeu.ihm;

import javax.swing.*;

import jeu.Controleur;

import java.awt.event.*     ;
import java.awt.BorderLayout;
import java.awt.Graphics    ;
import java.awt.GridLayout  ;
import java.awt.Image       ;
import java.awt.Font        ;
import java.awt.Color       ;
import java.awt.FlowLayout  ;


public class PanelSolo extends JPanel implements ActionListener
{
	/*- - - - - - - - - - - - -*/
	/* Constantes des couleurs */
	/*- - - - - - - - - - - - -*/
	private final Color VERT_FORET     = new Color( 40,  67,  43);
	private final Color BEIGE_BOUTON   = new Color(230, 218, 196);
	private final Color TEXTE_FONCE    = new Color( 51,  35,  21);

	/*- - - - - - - - - - - - -*/
	/* Constantes des Fonts    */
	/*- - - - - - - - - - - - -*/
	private final Font FONT_BOUTON = new Font("SansSerif", Font.BOLD, 13);
	private final Font FONT_LABEL  = new Font("SansSerif", Font.BOLD, 16);


	/*- - - - - - - - - - - - -*/
	/* Attributs               */
	/*- - - - - - - - - - - - -*/
	private PanelPioche     panelPioche    ;
	private Controleur      ctrl           ;
	private StyleComposante style          ;
	private FramePrincipale frame          ; 

	private JPanel          panelOption    ;
	private JPanel          panelScore     ;

	private JLabel          lblManche      ;

	private boolean         optionActiver  ;

	private JButton         btnOption      ;
	private JButton         btnQuitter     ;
	private JButton         btnRecommencer ;

	private Image           imgFond        ;

	/*- - - - - - - - - - - - -*/
	/* Constructeur            */
	/*- - - - - - - - - - - - -*/
	public PanelSolo(FramePrincipale frame, StyleComposante style, Controleur ctrl)
	{
		this.setLayout(new BorderLayout());


		/*- - - - - - - - - - - - -*/
		/* Création des attributs  */
		/*- - - - - - - - - - - - -*/

		this.ctrl  = ctrl  ;
		this.frame = frame ;
		this.style = style ;

		
		
		this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond.png"); // <- image d'arrière plan

		this.optionActiver = false;  // <- desactive l'affichage du menu option

		this.lblManche = new JLabel("Manche 1/" + this.ctrl.getNbManche());
		this.style.styleJLabel( this.lblManche, FONT_LABEL, TEXTE_FONCE);

		JLabel lblTitreScore = new JLabel("Table de score");
		this.style.styleJLabel(lblTitreScore, FONT_LABEL, TEXTE_FONCE);

		/*=============================== boutons ===============================*/

		this.btnOption = new JButton( "Option");
		this.style.styleBouton( this.btnOption, this.BEIGE_BOUTON, this.TEXTE_FONCE, FONT_BOUTON );

		this.btnRecommencer = new JButton("Recommencer la partie");
		this.style.styleBouton( this.btnRecommencer, this.VERT_FORET, Color.WHITE, FONT_BOUTON );

		this.btnQuitter     = new JButton("Quitter la partie");
		this.style.styleBouton( this.btnQuitter, this.VERT_FORET, Color.WHITE, FONT_BOUTON );

		/*=============================== panel ===============================*/
		JPanel panelHaut = new JPanel( new BorderLayout());
		panelHaut.setOpaque( false );

		JPanel panelBtnOption = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
		panelBtnOption.setOpaque(false);

		JPanel panelIndicateurManche = new JPanel();
		panelIndicateurManche.setOpaque(false);

		this.panelScore = new JPanel( new GridLayout(0, 1, 0, 5));
		this.style.stylePanelScore(this.panelScore);

		this.panelPioche  = new PanelPioche(this.frame, this.ctrl, this.style);
		this.panelPioche.setOpaque(false);
		
		this.panelOption = new JPanel( new GridLayout( 9, 1, 3, 3));
		this.panelOption.setOpaque(false);
		this.panelOption.setVisible( this.optionActiver ); // <- affiche le panel en fonction de l'état du bouton option


		/*- - - - - - - - - - - - - - - -*/
		/* Positionnement des attributs  */
		/*- - - - - - - - - - - - - - - -*/

		//panel BtnOption
		panelBtnOption.add ( this.btnOption );

		// panel indicateurManche
		panelIndicateurManche.add ( this.lblManche );

		//panel haut
		panelHaut.add( panelBtnOption        , BorderLayout.WEST   );
		panelHaut.add( panelIndicateurManche , BorderLayout.CENTER );
		panelHaut.setOpaque(false);

		//panel Score
		this.panelScore.add ( lblTitreScore );

		//panel Option
		this.panelOption.add ( new JLabel() );
		this.panelOption.add ( this.btnRecommencer );
		this.panelOption.add ( this.btnQuitter     );
		this.panelOption.add ( new JLabel() );
		this.panelOption.add ( new JLabel() );
		this.panelOption.add ( new JLabel() );
		this.panelOption.add ( new JLabel() );
		this.panelOption.add ( new JLabel() );

		// panel this
		this.add( this.panelPioche , BorderLayout.SOUTH);
		this.add( this.panelOption , BorderLayout.WEST );
		this.add( panelHaut        , BorderLayout.NORTH);
		this.add( this.panelScore  , BorderLayout.EAST );

		/*- - - - - - - - - - - - - -*/
		/* Activation des attributs  */
		/*- - - - - - - - - - - - - -*/
		this.btnOption     .addActionListener(this);
		this.btnQuitter    .addActionListener(this);
		this.btnRecommencer.addActionListener(this);
	}


	/*=============================== Méthodes ===============================*/
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.btnOption )
		{
			this.optionActiver = ! this.optionActiver;
			this.panelOption.setVisible(optionActiver);

			this.revalidate();
			this.repaint();
		}


		if ( e.getSource() == this.btnQuitter )
		{
			// arrete la partie et retourne a l'accueil
			this.ctrl.resetPlateau();
			this.reinitialiserScore();
			this.frame.getPanelPlateau().reset();
			this.frame.switchPanel("accueil");
		}

		if ( e.getSource() == this.btnRecommencer )
		{
			// recommence la partie avec le même plateau
			this.ctrl.creerPartie("Joueur 1");
			this.reinitialiserScore();
			this.majManche();
			this.frame.getPanelPlateau().reinitialiserPartie();
		}
	}


	/* méthode paintComponent permettant de mettre une image de fond*/
	public void paintComponent ( Graphics g )
	{
		super.paintComponent(g);
		g.drawImage(this.imgFond, 0, 0, this.getWidth(), this.getHeight(), this );
	}


	/* méthode qui met a jour l'indicateur de manche */
	public void majManche()
	{
		this.lblManche.setText("Manche " + this.ctrl.getNumeroManche() + "/" + this.ctrl.getNbManche());
		this.style.styleJLabel(this.lblManche, FONT_LABEL, TEXTE_FONCE);
		repaint();
	}

	/* méthode qui return le panelPioche */
	public PanelPioche getPanelPioche()  { return this.panelPioche ; }

	/* méthode qui ajoute le score de la manche sur la table de score */
	public void ajouterScoreManche ( int numManche, int score )
	{
		JLabel lblScore = new JLabel("Manche " + numManche + " : " + score + " points");

		this.panelScore.add ( lblScore );
		this.panelScore.revalidate();
		this.panelScore.repaint();
	}


	/* méthode qui reinitialise la table de score et écris juste le titre */
	public void reinitialiserScore()
	{
		this.panelScore.removeAll();
		JLabel lblTitreScore = new JLabel( "Table de score");
		this.style.styleJLabel(lblTitreScore, FONT_BOUTON, TEXTE_FONCE);

		this.panelScore.add ( lblTitreScore );
		this.panelScore.revalidate();
		this.panelScore.repaint();
	}


}