package jeu.ihm ; 


import javax.swing.*;

import jeu.Controleur ;
import jeu.metier.Joueur;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.*;
import java.util.List;


public class PanelPiocheDuo extends JPanel implements ActionListener
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
	
	/* - - - - - - - - - - - - - */
	/* Déclaration des attributs */
	/* - - - - - - - - - - - - - */
	private JLabel              lblPioche     ;
	private JLabel              lblFanionPiochee ;
	private JButton             btnPasserSonTour ;
	private StyleComposante     style;
	private FramePrincipale     frame         ;
	private Controleur          ctrl          ;
	private GererSouris         gererSouris   ;

	private PanelPlateau        panelPlateauJ1 ;
	private PanelPlateau        panelPlateauJ2 ;

	private JLabel              lblPointJ1    ;
	private JLabel              lblPointJ2    ;
	private JLabel              lblTourJoueur ;
	private JLabel              lblGagnant    ; 

	// 1 = tour J1, 2 = tour J2
	private int                 nbPointJ1     ;
	private int                 nbPointJ2     ;
	private int                 indiceJoueurActif; 


	/* - - - - - - - - - - */
	/* Constructeur        */
	/* - - - - - - - - - - */
	public PanelPiocheDuo(FramePrincipale frame, Controleur ctrl, PanelPlateau j1, PanelPlateau j2, StyleComposante style)
	{
		this.ctrl           = ctrl  ;
		this.frame          = frame ;
		this.style          = style ;
		this.panelPlateauJ1 = j1    ;
		this.panelPlateauJ2 = j2    ;
	

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 150));

		JPanel panelGauche      = new JPanel(new GridLayout(5, 2));
		panelGauche.setOpaque(false);

		JPanel panelDroit       = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		panelDroit.setOpaque (false);

		JPanel panelMillieu     = new JPanel() ; 
		panelMillieu.setOpaque(false);

		
		JLabel lblPiocher       = new JLabel("Appuyer sur la pioche pour piocher ");
		this.style.styleJLabel( lblPiocher, FONT_LABEL, TEXTE_FONCE);

		this.lblPioche        = new JLabel();
		this.style.styleJLabel(lblPioche, FONT_LABEL, TEXTE_FONCE);

		this.lblFanionPiochee = new JLabel();
		this.style.styleJLabel(lblFanionPiochee, FONT_LABEL, TEXTE_FONCE);

		this.lblPointJ1       = new JLabel() ;
		this.style.styleJLabel(lblPointJ1, FONT_LABEL, TEXTE_FONCE);

		this.lblPointJ2       = new JLabel() ; 
		this.style.styleJLabel(lblPointJ2, FONT_LABEL, TEXTE_FONCE);

		this.lblTourJoueur = new JLabel(" Tour du joueur : ");
		this.style.styleJLabel(this.lblTourJoueur, FONT_LABEL, TEXTE_FONCE);

		this.lblGagnant = new JLabel() ; 
		this.style.styleJLabel(this.lblTourJoueur, FONT_LABEL, TEXTE_FONCE);


		this.indiceJoueurActif = 0;

		//on commence la partie dans le init 
		this.btnPasserSonTour = new JButton(" Passer son tour ");
		this.style.styleBouton( this.btnPasserSonTour, VERT_FORET, Color.WHITE, FONT_BOUTON );


		Image imgPioche = new ImageIcon("../jeu/ihm/images/pioche.png").getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);
		this.lblPioche.setIcon(new ImageIcon(imgPioche));


		panelGauche.add(lblPiocher);
		panelGauche.add(this.btnPasserSonTour);
		panelGauche.add(lblTourJoueur);
		panelGauche.add(this.lblPointJ1) ; 
		panelGauche.add(this.lblPointJ2) ; 

		panelDroit.add(this.lblPioche);
		panelDroit.add(this.lblFanionPiochee);

		panelMillieu.add(this.lblGagnant)    ; 

		this.add(panelGauche, BorderLayout.WEST);
		this.add(panelMillieu, BorderLayout.CENTER) ; 
		this.add(panelDroit,  BorderLayout.EAST);


		this.gererSouris      = new GererSouris();
		this.btnPasserSonTour.addActionListener(this);
		this.lblPioche.addMouseListener(this.gererSouris);

	
		
		this.majLabelTour();
	}

	//methode qu'on va appeler dans le pour commencer une partie et recup les info des joueur creer au plateau chargement
	public void init()
	{
		if (this.ctrl.getPartie() == null )
			return;

		this.indiceJoueurActif = 0; // joueur 1 commence
		this.lblPointJ1.setText(this.ctrl.getPartie().getJoueurs().get(0).getNom() + " : " + this.nbPointJ1);
		this.lblPointJ2.setText(this.ctrl.getPartie().getJoueurs().get(1).getNom() + " : " + this.nbPointJ2);
		this.majPoints();
		this.majLabelTour();
		this.gererSouris.reset();
		this.lblFanionPiochee.setIcon(null);
		this.panelPlateauJ1.setActif(false);
		this.panelPlateauJ2.setActif(false);
	}

	// Appelé après qu'un joueur a joué pour passer au joueur suivant
	public void joueurAJoue(int indexJoueurQuiVientDeJouer)
	{
		

		if (indexJoueurQuiVientDeJouer == 0)
		{
			// J1 vient de jouer  activer J2
			this.indiceJoueurActif = 1;
			this.panelPlateauJ1.setActif(false);
			this.panelPlateauJ2.setActif(true);
		}
		else
		{
			// J2 vient de jouer  on repart sur J1 pour le prochain tour
			this.indiceJoueurActif = 0;

			if (this.ctrl.estMancheTermine())
			{
				 // Fin de manche : on termine et on remet à zéro l'affichage
				this.ctrl.terminerManche();
				this.reinitialiserAffichage();
				this.frame.getPanelDuo().majManche();
				this.panelPlateauJ1.nouvelleManche();
				this.panelPlateauJ2.nouvelleManche();
				
			}
			else
			{
				// Manche pas finie : on réinitialise juste le fanion pour que les deux puissent rejouer 
				this.ctrl.reinitialiserFanionDuo();
			}

			this.gererSouris.reset();
			this.panelPlateauJ1.setActif(false);
			this.panelPlateauJ2.setActif(false);
		}
		this.majLabelTour();//on maj ici sinon le tour va etre inversée
		this.majPoints();
	}

	 // Appelé quand une manche se termine depuis PanelPlateau
	public void mancheTermineeDuo(int numManche, int score) 
	{
		// à compléter selon ta logique de fin de manche duo
		this.reinitialiserAffichage();
		this.ctrl.terminerManche();
		this.majPoints();
		this.majLabelTour();
		this.panelPlateauJ1.repaint();
		this.panelPlateauJ2.repaint();
	}


	/* Met à jour le label avec le nom du joueur courant */
	private void majLabelTour()
	{
		if (this.ctrl.getPartie() == null)
			return;

		String nom = this.ctrl.getPartie().getJoueurs().get(this.indiceJoueurActif).getNom();
		this.lblTourJoueur.setText("Tour de : " + nom);
	}


	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource() == this.btnPasserSonTour)
		{
			if (this.indiceJoueurActif == 0 && this.ctrl.peutPiocher())
				return;
			
			this.ctrl.passerSonTour();;
			this.joueurAJoue( this.indiceJoueurActif );
		}
	}


	private class GererSouris extends MouseAdapter
	{
		int     numFanion     ;
		String  typeFanion    ;
		boolean estFoncee = false ;

		public void mouseClicked(MouseEvent e)
		{
			if (e.getSource() != PanelPiocheDuo.this.lblPioche)
				return;

			if (!PanelPiocheDuo.this.ctrl.peutPiocher())  // si on peut pas piocher on return 
				return;

			if (PanelPiocheDuo.this.indiceJoueurActif != 0)
				return;

			if (PanelPiocheDuo.this.ctrl.estMancheTermine())
			{
				PanelPiocheDuo.this.reinitialiserAffichage();
				return;
			}

			PanelPiocheDuo.this.ctrl.piocher();

			this.numFanion  = PanelPiocheDuo.this.ctrl.getNumeroFanionCourant();
			this.typeFanion = PanelPiocheDuo.this.ctrl.getTypeFanionCourant();

			String chemin = "../jeu/ihm/images/Fanion_" + this.typeFanion + "_" + this.numFanion + ".png";
			Image imgFanion = new ImageIcon(chemin).getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);// ajoute les images de la bonne taille 
			PanelPiocheDuo.this.lblFanionPiochee.setIcon(new ImageIcon(imgFanion)); // on met en image la carte piochée

			// Après pioche : toujours activer J1 d'abord (premier a jouer ) 
			PanelPiocheDuo.this.panelPlateauJ1.setActif(true);
			PanelPiocheDuo.this.panelPlateauJ2.setActif(false);

			PanelPiocheDuo.this.panelPlateauJ1.repaint();
			PanelPiocheDuo.this.panelPlateauJ2.repaint();// on met a jour les changement 
		}

		public void reset()
		{
			this.estFoncee = false; // on reset la pioche donc on peut repiocher 
		}
	}

	public void majPoints()
	{
		String nomJ1 = this.ctrl.getPartie().getJoueurs().get(0).getNom() ; 
		String nomJ2 = this.ctrl.getPartie().getJoueurs().get(1).getNom() ;//NOM DES JOUEUR 
		if (this.ctrl.getPartie() == null)
			return;

		this.nbPointJ1 = this.ctrl.getScoreJoueurTotal(0); //recupere les points dans le controleur 
		this.nbPointJ2 = this.ctrl.getScoreJoueurTotal(1);

		this.lblPointJ1.setText(nomJ1 + " : " + this.nbPointJ1 + " pts");
		this.lblPointJ2.setText(nomJ2 + " : " + this.nbPointJ2 + " pts"); //on affiche les points 


		if (this.ctrl.getPartie().estTerminee())// fin de partie on affiche le gagnant etc ...
		{
			List<Joueur> gagnants = (this.ctrl.getPartie()).getGagnants(); // on recupere qui gagne grace a une methode de partie

			if (this.ctrl.getPartie().estEgalite())
				this.lblGagnant.setText("Égalité !");
			else
				this.lblGagnant.setText("BRAVO " + gagnants.get(0).getNom() + "à  gagné !");
		}
		else   // <- si partie pas fini alors on dit juste qui méne 
		{
			if (this.nbPointJ1 > this.nbPointJ2)
				this.lblGagnant.setText(nomJ1 + " mène");
			else if (this.nbPointJ2 > this.nbPointJ1)
				this.lblGagnant.setText(nomJ2 + " mène");
			else
				this.lblGagnant.setText("Égalité");
		}
		
	}


	public void reinitialiserAffichage()//on enelve juste l'image du fanion piochee
	{
		this.lblFanionPiochee.setIcon(null);
	}
}