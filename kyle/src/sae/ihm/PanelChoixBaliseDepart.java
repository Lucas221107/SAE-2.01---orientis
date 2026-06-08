package sae.ihm;

import sae.Controleur;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.*;


public class PanelChoixBaliseDepart extends JPanel implements ActionListener
{

	/* - - - - - - - - - - - - - */
	/* Déclaration des attributs */
	/* - - - - - - - - - - - - - */
	private Controleur      ctrl          ;
	private FramePrincipale frame         ;
	private PanelPlateau    panelPlateau  ;

	private JButton         btnRetour     ;
	private JButton         btnSauvegarder;
	

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public PanelChoixBaliseDepart( FramePrincipale frame, PanelPlateau panelPlateau, Controleur ctrl)
	{
		
		this.frame        = frame;
		this.ctrl         = ctrl;
		this.panelPlateau = panelPlateau;
		
		this.setLayout( new BorderLayout());
		this.setPreferredSize( this.frame.getDimensionPanel());

		/* - - - - - - - - - - - - - - - - */
		/* Création des Composants         */
		/* - - - - - - - - - - - - - - - - */
		this.btnRetour      = new JButton("Retour"                );
		this.btnSauvegarder = new JButton("Sauvegarder le plateau");

		JPanel panelBtn    = new JPanel    ();
		JPanel panelGauche = new JPanel(new GridLayout( 8,1));

		/* - - - - - - - - - - - - - - - - */
		/* Positionnement des Composants   */
		/* - - - - - - - - - - - - - - - - */

		// panelBtn
		panelBtn.add ( this.btnRetour);
		panelBtn.add ( this.btnSauvegarder);

		//panelGauche
		panelGauche.add( new JLabel(" Veuillez selectionnez 5 balises de départ"));
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());

		//panel this
		this.add ( panelGauche, BorderLayout.CENTER);
		this.add ( panelBtn   , BorderLayout.SOUTH );

		/* - - - - - - - - - - - - - - - - */
		/* Activation des Composants       */
		/* - - - - - - - - - - - - - - - - */

		this.btnRetour     .addActionListener(this);
		this.btnSauvegarder.addActionListener(this);
		
	}

	/* ========================================================= */

	public void actionPerformed ( ActionEvent e)
	{
		if ( e.getSource() == this.btnRetour)
		{
			this.frame.switchPanel("balise");
		}

		if ( e.getSource() == this.btnSauvegarder)
		{
			this.panelPlateau.setTabDepart( this.panelPlateau.getTabDepart());
			this.ctrl.genererPlateau(); // <- méthode permettant de générer le plateau final dans la partie métier
		}
	}
}