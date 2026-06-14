package jeu.ihm;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.*;

public class PanelRegle extends JPanel implements ActionListener
{
	/*--------------------------------*/
	/*           Attributs            */
	/*--------------------------------*/
	private FramePrincipale frame     ; 
	
	private JLabel          lblPage   ;
	private ImageIcon       image     ;
	private JButton         btnSuivant;
	private JButton         btnPrec   ;
	private JButton         btnAccueil;
	private JScrollPane     scrollPane;

	private String[]        tabPages  ;
	private int             cpt       ;


	/*--------------------------------*/
	/*          Constructeur          */
	/*--------------------------------*/
	public PanelRegle( FramePrincipale frame)
	{
	
		this.setLayout( new BorderLayout());

		/*--------------------------------*/
		/*    Création des composantes    */
		/*--------------------------------*/
		this.cpt = 0;

		this.frame = frame;

		this.tabPages = new String[] { "page1.jpg", "page2.jpg", "page3.jpg", "page4.jpg", "page5.jpg"}; // <- tableau qui stock les pages de règle
		this.image    = new ImageIcon( "../jeu/ihm/images/" + tabPages[cpt]); // <- met l'image dans une Icon
		this.lblPage  = new JLabel(this.image);  // <- ajout de l'image dans un JLabel

		this.scrollPane = new JScrollPane( this.lblPage);  // <- scrollpane permettant e voir toute la page de regle et de pouvoir scroller en plus
		this.scrollPane.getVerticalScrollBar().setUnitIncrement(16); // <- changement de l'unité d'incrementation de la scrollBar
		this.scrollPane.setPreferredSize( this.frame.getDimensionPanel()); // <- indique que le scrollpane doit faire toute l'écran
		
		JPanel panelBtn = new JPanel( new FlowLayout());
		panelBtn.setOpaque(false);
		
		this.btnSuivant = new JButton("page suivante"     );
		this.btnPrec    = new JButton("page précédante"   );
		this.btnAccueil = new JButton("Retour à l'accueil");

		/*--------------------------------------*/
		/*    Positionnement des composantes    */
		/*--------------------------------------*/

		panelBtn.add ( this.btnAccueil);
		panelBtn.add ( this.btnPrec   );
		panelBtn.add ( this.btnSuivant);
		
		this.add( scrollPane, BorderLayout.CENTER);
		this.add( panelBtn  , BorderLayout.SOUTH );
			
		
		/*----------------------------------*/
		/*    activation des composantes    */
		/*----------------------------------*/

		this.btnAccueil.addActionListener(this);
		this.btnPrec   .addActionListener(this);
		this.btnSuivant.addActionListener(this);
	}

	/*=======================================================================================*/

	/*--------------------------------*/
	/*           Méthodes             */
	/*--------------------------------*/

	public void actionPerformed ( ActionEvent e)
	{
		
		if (e.getSource() == this.btnPrec)
		{
			if ( this.cpt != 0 )
			{ 
				this.cpt --; // <- décrémentation du cpt pour afficher la page précédante
			
				ImageIcon img = new ImageIcon( "../jeu/ihm/images/" + this.tabPages[cpt]);
				this.lblPage.setIcon(img);

				this.scrollPane.getVerticalScrollBar().setValue(0); // <- remet la scrollbar au minimum
			}
		}


		if ( e.getSource() == this.btnSuivant)
		{

			if ( this.cpt < this.tabPages.length - 1)
			{
				this.cpt ++; // <- incrémentation du cpt pour afficher la page suivante

				ImageIcon img = new ImageIcon( "../jeu/ihm/images/" + this.tabPages[cpt]);
				this.lblPage.setIcon(img);

				this.scrollPane.getVerticalScrollBar().setValue(0); // remet la scrollBar au minimum
			}
		}

		if ( e.getSource() == this.btnAccueil)
		{
			this.frame.switchPanel ("accueil"); // <- affiche le panel Accueil
		}
	}
}
