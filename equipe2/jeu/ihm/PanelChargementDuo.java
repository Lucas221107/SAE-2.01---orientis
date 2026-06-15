package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelChargementDuo extends JPanel implements ActionListener
{
	/*- - - - - - - - - - - - -*/
	/* Constantes des couleurs */
	/*- - - - - - - - - - - - -*/
	private final Color VERT_FORET   = new Color( 40,  67,  43);
	private final Color BEIGE_BOUTON = new Color(230, 218, 196);
	private final Color TEXTE_FONCE  = new Color( 51,  35,  21);

	/*- - - - - - - - - - - - -*/
	/* Attributs               */
	/*- - - - - - - - - - - - -*/
	private FramePrincipale frame          ;
	private StyleComposante style          ;
	private Controleur      ctrl           ;

	// Plateaux d'aperçu affichés côte à côte sur l'écran de chargement
	// Ce sont des copies indépendantes, pas celles utilisées en jeu dans PanelDuo
	private PanelPlateau    panelApercuJ1  ;
	private PanelPlateau    panelApercuJ2  ;
	private JPanel          panelDeuxApercu;

	private BarJMenuPerso   maBarre        ;

	private JButton         btnCommencer   ;
	private JButton         btnRetour      ;
	private JButton         btnAnnuler     ;

	private Image           imgFond        ;

	// Champs de saisie des pseudos des deux joueurs
	private JTextField      txtNomJ1       ;
	private JTextField      txtNomJ2       ;

	/*- - - - - - - - - - - - -*/
	/* Constructeur            */
	/*- - - - - - - - - - - - -*/
	public PanelChargementDuo(FramePrincipale frame, Controleur ctrl, StyleComposante style)
	{
		this.setLayout(new BorderLayout());

		Font fontBouton = new Font("SansSerif", Font.BOLD, 13);

		this.ctrl  = ctrl  ;
		this.frame = frame ;
		this.style = style ;

		// Image de fond spécifique au mode multijoueur
		this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond_multi_connexion.png");
		this.maBarre = new BarJMenuPerso(this.ctrl);

		// Création des deux aperçus de plateau (lecture seule, juste pour visualiser le fichier chargé)
		this.panelApercuJ1   = new PanelPlateau(this.frame, this.ctrl);
		this.panelApercuJ2   = new PanelPlateau(this.frame, this.ctrl);

		// Panel contenant les deux aperçus côte à côte
		this.panelDeuxApercu = new JPanel(new GridLayout(1, 2));
		this.panelDeuxApercu.setOpaque(false);

		this.panelApercuJ1.setOpaque(false);
		this.panelApercuJ2.setOpaque(false);

		this.panelDeuxApercu.add(this.panelApercuJ1);
		this.panelDeuxApercu.add(this.panelApercuJ2);

		/* Labels et champs texte pour saisir les pseudos */
		JLabel lblNomJ1 = new JLabel("Pseudo Joueur 1");
		JLabel lblNomJ2 = new JLabel("Pseudo Joueur 2");
		this.txtNomJ1   = new JTextField(10);
		this.txtNomJ2   = new JTextField(10);

		/*============================ Boutons ===========================*/
		// Annuler : vide les champs et réinitialise le plateau chargé
		this.btnAnnuler   = new JButton("Annuler");
		this.style.styleBouton(this.btnAnnuler, this.VERT_FORET, Color.WHITE, fontBouton);

		// Retour : revient à l'écran d'accueil sans lancer de partie
		this.btnRetour    = new JButton("Retour");
		this.style.styleBouton(this.btnRetour, this.BEIGE_BOUTON, this.TEXTE_FONCE, fontBouton);

		// Commencer : valide les saisies et lance la partie duo
		this.btnCommencer = new JButton("Commencer la partie");
		this.style.styleBouton(this.btnCommencer, this.VERT_FORET, Color.WHITE, fontBouton);

		/*============================ Panels ===========================*/
		JPanel panelBtn = new JPanel();
		panelBtn.setOpaque(false);

		// Colonne gauche : espaces vides + labels + champs de saisie des pseudos
		JPanel panelGauche = new JPanel(new GridLayout(12, 1, 5, 5));
		panelGauche.setOpaque(false);

		/*- - - - - - - - - - - - - */
		/* Ajout des composantes    */
		/*- - - - - - - - - - - - - */
		// JLabel vides utilisés comme espaceurs dans la grille
		panelGauche.add(new JLabel()   );
		panelGauche.add(new JLabel()   );
		panelGauche.add(new JLabel()   );
		panelGauche.add(lblNomJ1       );
		panelGauche.add(this.txtNomJ1  );
		panelGauche.add(new JLabel()   );
		panelGauche.add(new JLabel()   );
		panelGauche.add(lblNomJ2       );
		panelGauche.add(this.txtNomJ2  );
		panelGauche.add(new JLabel()   );
		panelGauche.add(new JLabel()   );
		panelGauche.add(new JLabel()   );

		panelBtn.add(this.btnRetour    );
		panelBtn.add(this.btnAnnuler   );
		panelBtn.add(this.btnCommencer );

		this.add(panelBtn             , BorderLayout.SOUTH );
		this.add(this.panelDeuxApercu , BorderLayout.CENTER);
		this.add(panelGauche          , BorderLayout.WEST  );

		/*- - - - - - - - - - - - - - - -*/
		/* Activation des composantes    */
		/*- - - - - - - - - - - - - - - -*/
		this.btnAnnuler  .addActionListener(this);
		this.btnCommencer.addActionListener(this);
		this.btnRetour   .addActionListener(this);
	}

	/*=========================== Méthodes ============================*/

	/**
	 * Gère les clics sur les boutons Retour, Annuler et Commencer.
	 */
	public void actionPerformed(ActionEvent e)
	{
		// Retour à l'accueil : réinitialise les aperçus et le plateau en mémoire
		if (e.getSource() == this.btnRetour)
		{
			this.panelApercuJ1.reset();
			this.panelApercuJ2.reset();
			this.ctrl.resetPlateau();
			this.frame.switchPanel("accueil");
		}

		if (e.getSource() == this.btnCommencer)
		{
			// Vérifications avant de lancer la partie
			if (this.ctrl.getCheminPlateau() == null)
			{
				JOptionPane.showMessageDialog(this, "Veuillez d'abord sélectionner un fichier");
				return;
			}
			if (this.txtNomJ1.getText().isEmpty())
			{
				JOptionPane.showConfirmDialog(this, "Veuillez saisir le pseudo du Joueur 1");
				return;
			}
			if (this.txtNomJ2.getText().isEmpty())
			{
				JOptionPane.showConfirmDialog(this, "Veuillez saisir le pseudo du Joueur 2");
				return;
			}

			// Crée la partie avec les pseudos saisis, initialise l'interface duo et bascule vers elle
			// Note : le plateau est déjà chargé via la barre de menu (chargerPlateau())
			this.ctrl.creerPartieDuo(this.txtNomJ1.getText(), this.txtNomJ2.getText());
			this.frame.getPanelDuo().getPanelPiocheDuo().init();
			this.frame.switchPanel("duo");
		}

		// Annuler : remet l'écran à zéro sans changer de panel
		if (e.getSource() == this.btnAnnuler) 
		{ 
			this.panelApercuJ1.reset();
			this.panelApercuJ2.reset();
			this.ctrl.resetPlateau();
			this.txtNomJ1.setText("");
			this.txtNomJ2.setText("");
		}
	}

	/**
	 * Charge et affiche le même plateau dans les deux aperçus.
	 * Appelé par la barre de menu après sélection d'un fichier.
	 */
	public void chargerPlateau()
	{
		this.panelApercuJ1.charger(this.ctrl.getCheminPlateau());
		this.panelApercuJ2.charger(this.ctrl.getCheminPlateau());
	}

	/**
	 * Dessine l'image de fond en plein écran derrière tous les composants.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(this.imgFond, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	public BarJMenuPerso getMaBarre() { return this.maBarre; }
}