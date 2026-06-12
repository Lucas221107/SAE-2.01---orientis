package sae.ihm;

import sae.Controleur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelBalise extends JPanel implements ActionListener
{
	/* - - - - - - - - - - - - - */
	/* Déclaration des attributs */
	/* - - - - - - - - - - - - - */
	private final int TAILLE_CASE = 50;
	

	private JPanel          panelImageBalise ; 
	private FramePrincipale framePrincipale  ;
	private PanelPlateau    panelPlateau     ;
	private Controleur      ctrl             ;

	private JButton         btnValider       ;
	private JButton         btnRetour        ;

	private int             nbBalise         ;

	
	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public PanelBalise(FramePrincipale frame , PanelPlateau panelPlateau , Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.framePrincipale = frame;
		this.panelPlateau    = panelPlateau;

		this.setLayout(new BorderLayout() );
		this.setPreferredSize( this.framePrincipale.getDimensionPanel());

		/* - - - - - - - - - - - - - */
		/* Création des composantes  */
		/* - - - - - - - - - - - - - */
	
		this.panelImageBalise = new JPanel(new GridLayout(0,3));  // <- le 0 permet au GridLayout de s'adapter en fonction du nombre de balise

		this.btnRetour  = new JButton("Retour" );
		this.btnValider = new JButton("Valider");
		this.btnValider.setEnabled(false);

		JPanel panelBtn = new JPanel();

		/* - - - - - - - - - - - - - - - - */
		/* Positionnement des composantes  */
		/* - - - - - - - - - - - - - - - - */
		
		//panemBtn
		panelBtn.add( this.btnRetour  );
		panelBtn.add( this.btnValider );
		
		// panel this
		this.add( this.panelImageBalise, BorderLayout.CENTER );
		this.add( panelBtn             , BorderLayout.SOUTH  ); 

		/* - - - - - - - - - - - - - - */
		/* Activation des composantes  */
		/* - - - - - - - - - - - - - - */
		this.btnRetour .addActionListener(this);
		this.btnValider.addActionListener(this);
	}

	/*=============================================================== */
	public void actionPerformed ( ActionEvent e)
	{
		if ( e.getSource() == this.btnRetour )
		{ 
			this.framePrincipale.switchPanel("biomes"); // <- reviens sur l'ancien panel
		}

		if ( e.getSource() == this.btnValider)
		{
			this.ctrl.setTabNumBalise( this.panelPlateau.getTabBalise()); // <- on créer le tableau avec le numéro des balises 
			this.ctrl.genererPlateau(); // <- on genere le plateau final mais sans les balises de départ
			this.panelPlateau.setModeSelectionDepart (true); // <- on active le mode pour selectionner les balises de départ
			this.panelPlateau.setAfficheLiaison(true); // <- methode permettant de creer les liaisons entre les balises
			this.framePrincipale.switchPanel( "depart"); // <- Passage au panel suivant
		}
	}


	/*========================================================================*/

	private class GereSouris extends MouseAdapter
	{
		private JLabel lblBaliseActive;
		private JFrame frameParent    ;
		private JPanel panelAvant     ;
		private int    posX           ;
		private int    posY           ;
		private int    indexOrigine   ;

		public void mousePressed(MouseEvent e)
		{
			if (this.frameParent == null)
			{
				// on récupere la Frame Parent
				this.frameParent = (JFrame) SwingUtilities.getWindowAncestor(PanelBalise.this);

				// on créer un panel transparent
				JPanel panelOpaque = new JPanel();  
				panelOpaque.setOpaque(false);

				// on pose ce panel devant toute la fenetre afin de faire un glisser Déposer fluide
				this.frameParent.setGlassPane(panelOpaque);

				// on garde une variable qui permet de stocker le panel précedant
				this.panelAvant = (JPanel) this.frameParent.getGlassPane();

			}


			// On verifie que le click se situe bien sur un JLabel
			if (e.getSource() instanceof JLabel)
			{
				this.lblBaliseActive = (JLabel) e.getSource();
				this.posX            = e.getX()              ;
				this.posY            = e.getY()              ;

				//on prend le point d'origine du clic
				this.indexOrigine    = PanelBalise.this.panelImageBalise.getComponentZOrder(this.lblBaliseActive);
			}
		}


		public void mouseDragged(MouseEvent e)
		{
			if (this.lblBaliseActive != null)
			{
				if (!this.panelAvant.isVisible())
				{
					// on desactive le layout pour pouvoir deplacer librement le JLabel
					this.panelAvant.setLayout(null);

					// on recupere les coordonnées du coins superieur gauche du JLabel
					Point p = SwingUtilities.convertPoint(this.lblBaliseActive,0,0,this.panelAvant);

					this.lblBaliseActive.setLocation(p);

					this.panelAvant.add(this.lblBaliseActive);
					this.panelAvant.setVisible(true);
				}


				int dx = e.getX() - this.posX;
				int dy = e.getY() - this.posY;

				this.lblBaliseActive.setLocation(this.lblBaliseActive.getX() + dx , this.lblBaliseActive.getY() + dy);

				this.panelAvant.repaint();
			}
		}


		public void mouseReleased(MouseEvent e)
		{
			if (this.lblBaliseActive == null) 
				return;

			// on recupere les coordonnées du coins superieur gauche du JLabel
			Point p = SwingUtilities.convertPoint(this.lblBaliseActive,e.getPoint(),panelPlateau);

			// determine pos dans le tableau 
			int cptCol = p.x / TAILLE_CASE; 
			int cptLig = p.y / TAILLE_CASE;

			// on retire le JLabel du GlassPane car le glisser Deposer est terminer
			this.panelAvant.remove(this.lblBaliseActive);
			this.panelAvant.setVisible(false);

			// on vérifie que les coordonnées soient bien dans le plateau
			if(cptLig >= 0 && cptLig < PanelBalise.this.ctrl.getNbLig() &&
			   cptCol >= 0 && cptCol < PanelBalise.this.ctrl.getNbCol()     )
			{
				
				// On vérifie que la case ne contient pas déjà une balise
				if (panelPlateau.estLibre( cptLig, cptCol))
				{
					int numBalise = Integer.parseInt( this.lblBaliseActive.getName());
					
					// remplie le tableau de int[][] tabBalise avec le numero de la balise deposer
					panelPlateau.ajouterBalise(cptLig,cptCol,numBalise);

					// On retire le JLabel du Panel de gauche
					PanelBalise.this.panelImageBalise.remove(this.lblBaliseActive);
					
					//On repeint pour raffraichir le panel avec toutes les balises
					PanelBalise.this.panelImageBalise.repaint();

					// On active le bouton valider seulement si le panel avec toutes les balises est vide
					PanelBalise.this.btnValider.setEnabled(PanelBalise.this.panelImageBalise.getComponentCount() == 0);
				}
				else
				{
					// Si la position est deja prise ou invalide, le JLabel se replace à sa position d'origine
					PanelBalise.this.panelImageBalise.add(this.lblBaliseActive,this.indexOrigine);
					PanelBalise.this.panelImageBalise.repaint();
				}
			}
			else
			{
				// Si la position est deja prise ou invalide, le JLabel se replace à sa position d'origine
				PanelBalise.this.panelImageBalise.add(this.lblBaliseActive,this.indexOrigine);
				PanelBalise.this.panelImageBalise.repaint();
			}

			this.lblBaliseActive = null;
		}
	}

	
	/*- - - - - - - - - - - */
	/* Modificateur         */
	/*- - - - - - - - - - - */
	public void setNbBalise ( int val)
	{
		this.nbBalise = val;
		this.genererBalise();
	}


	/*- - - - - - - - - - - */
	/* Méthode              */
	/*- - - - - - - - - - - */
	private void genererBalise()
	{
		int numBalise = 0;

		// méthode permettant de supprimer toutes les balises présentes sur le panel
		this.panelImageBalise.removeAll();
		this.btnValider.setEnabled(false);

		GereSouris gererSouris = new GereSouris();

		// Pour toutes les balises
		for ( int cpt = 0; cpt < this.nbBalise; cpt ++)
		{
			//On creer une balise avec un numéro au hasard entre 1 & 5
			numBalise = (int) ( Math.random() * 5 ) + 1;

			// On créer un JLabel avec l'image associé
			ImageIcon icon = new ImageIcon("../sae/ihm/images/balise_" + numBalise + ".png");
			JLabel    lblBalise = new JLabel(icon);
			lblBalise.setPreferredSize( new Dimension( this.TAILLE_CASE, this.TAILLE_CASE));
			
			//creer le nom du label en fonction du numero de la balise pour pouvoir le réutiliser plus tard
			lblBalise.setName( String.valueOf(numBalise));

			/* - - - - - - - - - - - - - - */
			/* Activation du JLabel        */
			/* - - - - - - - - - - - - - - */
			lblBalise.addMouseListener      (gererSouris);
			lblBalise.addMouseMotionListener(gererSouris);
			this.panelImageBalise.add(lblBalise);
		}

		this.panelImageBalise.revalidate(); // <- recalcule la disposition
		this.panelImageBalise.repaint();    // <- redessine

	}
}