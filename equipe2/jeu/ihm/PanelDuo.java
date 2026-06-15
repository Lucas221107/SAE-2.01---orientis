package jeu.ihm ;

import javax.swing.*;
import jeu.Controleur ;

import java.awt.event.*     ;
import java.awt.BorderLayout;
import java.awt.Graphics    ;
import java.awt.GridLayout  ;
import java.awt.Image       ;
import java.awt.Font        ;
import java.awt.Color       ;

public class PanelDuo extends JPanel implements ActionListener
{
	/*- - - - - - - - - - - - -*/
	/* Constantes des couleurs */
	/*- - - - - - - - - - - - -*/
	private final Color VERT_FORET   = new Color( 40,  67,  43);
	private final Color BEIGE_BOUTON = new Color(230, 218, 196);
	private final Color TEXTE_FONCE  = new Color( 51,  35,  21);

	/*- - - - - - - - - - - - -*/
	/* Constantes de la Fonts  */
	/*- - - - - - - - - - - - -*/
	private final Font FONT_LABEL  = new Font("SansSerif", Font.BOLD, 12);
	private final Font FONT_BOUTON = new Font("SansSerif", Font.BOLD, 13);

	/*- - - - - - - - - - - - -*/
	/* Attributs               */
	/*- - - - - - - - - - - - -*/
	private PanelPiocheDuo     panelPiocheDuo      ;
	private PanelPlateau       panelPlateauJ1      ;
	private PanelPlateau       panelPlateauJ2      ;
	private PanelChargementDuo panelChargementDuo  ;
	private Controleur         ctrl                ;
	private StyleComposante    style               ;
	private FramePrincipale    frame               ;

	private JPanel             panelDeuxPlateau    ;
	private JPanel             panelOption         ;
	
	private JLabel             lblManche           ;

	private boolean            optionActiver       ;

	private JButton            btnOption           ;

	private Image              imgFond             ;

	/*- - - - - - - - - - - - -*/
	/* Constructeur            */
	/*- - - - - - - - - - - - -*/
	public PanelDuo(FramePrincipale frame, StyleComposante style, Controleur ctrl)
	{
		this.setLayout(new BorderLayout());

		/*- - - - - - - - - - - - -*/
		/* Création des attributs  */
		/*- - - - - - - - - - - - -*/
		this.ctrl  = ctrl  ;
		this.frame = frame ;
		this.style = style ;


		this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond.png");

		this.optionActiver = false;

		this.lblManche = new JLabel("Manche 1/" + this.ctrl.getNbManche());
		this.style.styleJLabel(this.lblManche, FONT_LABEL, TEXTE_FONCE);

	
		/*=============================== boutons ===============================*/

		this.btnOption = new JButton("Option");
		this.style.styleBouton(this.btnOption, this.BEIGE_BOUTON, this.TEXTE_FONCE, this.FONT_BOUTON);



		/*=============================== panels ===============================*/
		
		JPanel panelHaut = new JPanel(new BorderLayout());
		panelHaut.setOpaque(false);

		JPanel panelIndicateurManche = new JPanel();
		panelIndicateurManche.setOpaque(false);

		this.panelOption = new JPanel(new GridLayout(9, 1, 3, 3));
		this.panelOption.setOpaque(false);
		this.panelOption.setVisible(this.optionActiver);

		/* Les deux plateaux côte à côte au centre */
		this.panelDeuxPlateau = new JPanel(new GridLayout(1, 2));
		this.panelDeuxPlateau.setOpaque(false);

		this.panelPlateauJ1 = new PanelPlateau(this.frame, this.ctrl);
		this.panelPlateauJ1.setIndexJoueur(0);

		this.panelPlateauJ2 = new PanelPlateau(this.frame, this.ctrl);
		this.panelPlateauJ2.setIndexJoueur(1);
	
		this.panelPiocheDuo = new PanelPiocheDuo(this.frame, this.ctrl, this.panelPlateauJ1, this.panelPlateauJ2, this.style);
		this.panelPiocheDuo.setOpaque(false );

		this.panelPlateauJ1.setPanelPiocheDuo(this.panelPiocheDuo);
		this.panelPlateauJ2.setPanelPiocheDuo(this.panelPiocheDuo);


		/*- - - - - - - - - - - - - - - -*/
		/* Positionnement des attributs  */
		/*- - - - - - - - - - - - - - - -*/

		// panel DeuxPlateau
		this.panelDeuxPlateau.add(this.panelPlateauJ1);
		this.panelDeuxPlateau.add(this.panelPlateauJ2);

		// panel indicateurManche
		panelIndicateurManche.add( this.lblManche );

		// panel haut
		panelHaut.add( this.btnOption        , BorderLayout.WEST);
		panelHaut.add( panelIndicateurManche , BorderLayout.NORTH );

		// panel option
		this.panelOption.add(new JLabel()      );
		this.panelOption.add(new JLabel()      );
        this.panelOption.add(new JLabel()      );
		this.panelOption.add(new JLabel()      );
		this.panelOption.add(new JLabel()      );
		this.panelOption.add(new JLabel()      );
		this.panelOption.add(new JLabel()      );
		this.panelOption.add(new JLabel()      );

		// panel this
		this.add(this.panelPiocheDuo  , BorderLayout.SOUTH );
		this.add(this.panelOption     , BorderLayout.WEST  );
		
		this.add(panelHaut            , BorderLayout.NORTH );
		this.add(this.panelDeuxPlateau, BorderLayout.CENTER);

		/*- - - - - - - - - - - - - -*/
		/* Activation des attributs  */
		/*- - - - - - - - - - - - - -*/
		this.btnOption     .addActionListener(this);
	}

	/*=============================== Méthodes ===============================*/
	
	/*- - - - - - - - - - -*/
	/*     Accesseurs      */
	/*- - - - - - - - - - -*/
	
	public PanelPlateau   getPanelPlateauJ1() { return this.panelPlateauJ1; } // <- return le plateau du joueur 1
	public PanelPlateau   getPanelPlateauJ2() { return this.panelPlateauJ2; } // <- return le plateau du joueur 2
	public PanelPiocheDuo getPanelPiocheDuo() { return this.panelPiocheDuo; } // <- return la pioche Duo qui est commune au deux Joueurs
	
	

	/*- - - - - - - - - - -*/
	/*      Méthodes       */
	/*- - - - - - - - - - -*/
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnOption)
		{
			this.optionActiver = !this.optionActiver;
			this.panelOption.setVisible(this.optionActiver);

			this.revalidate();
			this.repaint();
		}
	}

	/* méthode paintComponent permettant de mettre une image de fond */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(this.imgFond, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	/* méthode qui modifie le panelDeuxPlateau avec les nouveaux plateaux passer en parametre */
	public void ajouterPlateaux(PanelPlateau j1, PanelPlateau j2)
	{
		this.panelDeuxPlateau.removeAll(); // <- retire tous les plateaux du panel
		this.panelDeuxPlateau.add(j1); // <- ajoute le plateau du joueur 1
		this.panelDeuxPlateau.add(j2); // <- ajoute le plateau du joueur 2

		this.panelDeuxPlateau.revalidate(); // <- recalcule le positionnement du fichier
		this.panelDeuxPlateau.repaint();
	}


	/* méthode qui met a jour l'indicateur de manche */
	public void majManche()
	{
		this.lblManche.setText("Manche " + this.ctrl.getNumeroManche() + "/" + this.ctrl.getNbManche());
		this.style.styleJLabel(this.lblManche, FONT_LABEL, TEXTE_FONCE);
		repaint();
	}
}