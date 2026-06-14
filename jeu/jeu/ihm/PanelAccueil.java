package jeu.ihm;

import javax.swing.*;
import java.awt.event.*;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelAccueil extends JPanel implements ActionListener
{
	/*------------------*/
	/*    Attributs     */
	/*------------------*/
	private JButton         btnRegle  ;
	private JButton         btnSolo   ;
	private JButton         btnDuo    ;

	private Image           imgFond   ;

	private FramePrincipale frame     ;

	/*---------------*/
	/* Constructeur  */
	/*---------------*/
	public PanelAccueil( FramePrincipale frame)
	{
		this.setLayout( new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();  // <- layout permettant de disposer ou on veut les élements

		/*--------------------------------*/
		/*    Création des composantes    */
		/*--------------------------------*/

		this.frame = frame;

		this.btnSolo  = new JButton("Jouer en Solo");
		this.btnDuo   = new JButton("Jouer en Duo" );
		this.btnRegle = new JButton("Règle"        );
		
		this.imgFond = getToolkit().getImage("../jeu/ihm/images/ecran_d_acceuil.png"); // <- image de fond d'écran

		JPanel panelBtn = new JPanel( new FlowLayout( FlowLayout.CENTER));
		panelBtn.setOpaque(false);


		/*--------------------------------------*/
		/*    Positionnement des composantes    */
		/*--------------------------------------*/

		panelBtn.add( this.btnSolo  );
		panelBtn.add( this.btnDuo   );
		panelBtn.add( this.btnRegle );

		this.add ( panelBtn, gbc );

		/*----------------------------------*/
		/*    activation des composantes    */
		/*----------------------------------*/


		this.btnSolo .addActionListener(this);
		this.btnDuo  .addActionListener(this);
		this.btnRegle.addActionListener(this);
	}


	/*------------------*/
	/*    Méthodes      */
	/*------------------*/

	/* méthode obligatoire pour dessiner une image en fond */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage ( this.imgFond, 0, 0, getWidth(), getHeight(), this );
	}


	/* méthode pour savoir si on appuie sur un bouton */
	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.btnSolo)
		{
			this.frame.switchPanel("chargement");	
		}

		if ( e.getSource() == this.btnDuo )
		{

		}

		if ( e.getSource() == this.btnRegle)
		{
			this.frame.switchPanel("regle");
		}
	}
}
