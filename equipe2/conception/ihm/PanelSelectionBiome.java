package conception.ihm;

import conception.Controleur;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;


public class PanelSelectionBiome extends JPanel implements ActionListener
{
	
	/* - - - - - - - - - - - - - - - - */
	/* Déclaration des variables       */
	/* - - - - - - - - - - - - - - - - */
	private FramePrincipale       framePrincipale;
	private PanelPlateau          panelPlateau   ;
	private JButton               btnValider     ;
	private JButton               btnGommer      ;
	private JButton               btnDessiner    ;
	private JButton               btnRetour      ;
	
	private JComboBox<String>     cbBiomes       ;

	private Controleur            ctrl           ;

	/* - - - - - - - - - */
	/* Constructeur      */
	/* - - - - - - - - - */
	public PanelSelectionBiome ( FramePrincipale frame, PanelPlateau panelPlateau, Controleur ctrl )
	{

		this.framePrincipale = frame;
		this.ctrl            = ctrl;
		this.panelPlateau    = panelPlateau;
		
		this.setLayout( new BorderLayout());
		this.setBorder( BorderFactory.createLineBorder( Color.BLACK ));
		this.setBackground( new Color (230, 218, 218));
		this.setPreferredSize( this.framePrincipale.getDimensionPanel());

		/*--------------------------------*/
		/*    Création des composantes    */
		/*--------------------------------*/

		this.btnValider   = new JButton("Suivant"  );
		this.btnGommer    = new JButton("Gommer"   );
		this.btnRetour    = new JButton("Retour"   );
		this.btnDessiner  = new JButton("Dessiner" );

		this.cbBiomes     = new JComboBox<>( this.ctrl.getTabBiome());
		this.panelPlateau.setPanelBiome(this);

		JLabel lblTypeBiome = new JLabel("Choissisez votre biome et dessiner sur le plateau");

		JPanel panelGaucheInfo = new JPanel( new GridLayout( 8, 1));
		panelGaucheInfo.setOpaque( false );

		JPanel panelMode = new JPanel();
		panelMode.setOpaque(false);

		JPanel panelBtn  = new JPanel();
		panelBtn.setOpaque( false );
		
		
		/*--------------------------------------*/
		/*    Positionnement des composantes    */
		/*--------------------------------------*/

		
		//panelBtn
		panelBtn.add( this.btnRetour   );
		panelBtn.add( this.btnValider  );

		//panelMode
		panelMode.add( this.btnGommer   );
		panelMode.add( this.btnDessiner );
		
		
		//panelGaucheInfo
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( lblTypeBiome );
		panelGaucheInfo.add( this.cbBiomes);
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( panelMode    );
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( new JLabel() );


		//panel this
		this.add( panelGaucheInfo , BorderLayout.CENTER);
		this.add( panelBtn        , BorderLayout.SOUTH );
		
		
		
		/*----------------------------------*/
		/*    activation des composantes    */
		/*----------------------------------*/

		this.btnRetour   .addActionListener(this);
		this.btnGommer   .addActionListener(this);
		this.btnDessiner .addActionListener(this);
		this.btnValider  .addActionListener(this);
	}

	/*======================================================== */

	public void actionPerformed (ActionEvent e)
	{

		if ( e.getSource() == this.btnRetour)
		{
			this.framePrincipale.switchPanel("config"); // <- switch sur le panel Précédant
			this.framePrincipale.revenirEtape();
		}


		if ( e.getSource() == this.btnGommer)
		{
			this.panelPlateau.setModeGomme( true ); // <- active le mode Gomme de PanelPlateau
		}


		if ( e.getSource() == this.btnDessiner)
		{
			this.panelPlateau.setModeGomme( false ); // <- désactive le mode gomme de PanelPlateau
		}


		if ( e.getSource() == this.btnValider)
		{
			this.framePrincipale.switchPanel("balise"); // <- passe au Panel suivant
			this.framePrincipale.passerEtape();
			this.panelPlateau.setInteractif(false);
			this.ctrl.setTabStringBiome ( this.creerTabBiome()); // <- créer le tableau avec le nom des biomes pour chaque case colorier 
			                                                     //    sur le PanelPlateau et l'envoie à la classe Plateau de metier via le Controleur
		}
	}

	/* - - - - - - - - */
	/* Accesseurs      */
	/* - - - - - - - - */
	public String getNomBiome() { return (String) this.cbBiomes.getSelectedItem(); }

	/* - - - - - - - */
	/* Méthode       */
	/* - - - - - - - */

	public String[][] creerTabBiome()
	{
		int nbLigPlateau = this.panelPlateau.getNbLig();
		int nbColPlateau = this.panelPlateau.getNbCol();

		String[][] tabNomBiome = new String[ nbLigPlateau ][ nbColPlateau ];	

		for ( int lig = 0; lig < nbLigPlateau; lig++ )
		{				
			for ( int col = 0; col < this.panelPlateau.getNbCol(); col ++)
			{
				Color coul = this.panelPlateau.getCaseColor ( lig, col ); // <- récupere la couleur de la case du Panelplateau

				if ( coul != null)
					tabNomBiome[lig][col] = this.ctrl.toColor ( coul ).getNom(); // <- On retrouve le nom du biome à partie de la couleur
				else
					tabNomBiome[lig][col] = "Forêt claire";		// <- sinon au met par defaut le biome forêt			
			}
		}

		return tabNomBiome;
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gp = new GradientPaint(
			0, 0,new Color( 144, 238, 144),              // couleur de départ
			0, getHeight(), Color.WHITE   // couleur d'arrivée
		);

		g2.setPaint(gp);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

}
