import javax.swing.*;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.*;


public class PanelSelectionBiome extends JPanel implements ActionListener
{
	private FramePrincipale    framePrincipale;
	private PanelPlateau       panelPlateau;
	private JButton            btnValider;
	private JButton            btnSupprimer;
	private JButton            btnRetour;
	
	private JComboBox<String>  cbBiomes;

	private Controleur         ctrl;

	public PanelSelectionBiome ( FramePrincipale frame, PanelPlateau panelPlateau, Controleur ctrl )
	{

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int       hauteur     = (int) ( tailleEcran.getHeight() );
        int       largeur     = (int) ( tailleEcran.getWidth() * 0.375 );

		this.setLayout( new BorderLayout());
		this.setBorder( BorderFactory.createLineBorder( Color.BLACK ));
		this.setBackground( new Color (230, 218, 218));
		this.setPreferredSize( new Dimension( largeur, hauteur ));

		/*--------------------------------*/
		/*    Création des composantes    */
		/*--------------------------------*/

		this.framePrincipale = frame;
		this.ctrl            = ctrl;
		this.panelPlateau    = panelPlateau;

		this.btnValider   = new JButton("Valider"  );
		this.btnSupprimer = new JButton("Supprimer");
		this.btnRetour    = new JButton("Retour"   );

		this.cbBiomes     = new JComboBox<>( this.ctrl.initTabBiomes());
		this.panelPlateau.setPanelSelectionBiomes(this);

		JLabel lblTypeBiome = new JLabel("Type de biomes"      );

		JPanel panelGaucheInfo = new JPanel( new GridLayout( 8, 1));
		panelGaucheInfo.setOpaque( false );

		JPanel panelBtn        = new JPanel();
		panelBtn.setOpaque( false );
		
		
		/*--------------------------------------*/
		/*    Positionnement des composantes    */
		/*--------------------------------------*/

		
		//panelBtn
		panelBtn.add( this.btnRetour   );
		panelBtn.add( this.btnSupprimer);
		panelBtn.add( this.btnValider  );
		
		
		//panelGaucheInfo
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( lblTypeBiome );
		panelGaucheInfo.add( this.cbBiomes);
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( new JLabel() );
		panelGaucheInfo.add( new JLabel() );


		//panel this
		this.add( panelGaucheInfo , BorderLayout.CENTER);
		this.add( panelBtn        , BorderLayout.SOUTH );
		
		
		
		/*----------------------------------*/
		/*    activation des composantes    */
		/*----------------------------------*/

		this.btnRetour   .addActionListener(this);
		this.btnSupprimer.addActionListener(this);
		this.btnValider  .addActionListener(this);
	}



	public void actionPerformed (ActionEvent e)
	{

		if ( e.getSource() == this.btnRetour)
		{
			this.framePrincipale.switchPanel("config");
		}


		if ( e.getSource() == this.btnSupprimer)
		{

		}

		if ( e.getSource() == this.btnValider)
		{

		}
	}


	public Color getColorBiome()
	{
		String biome = (String) this.cbBiomes.getSelectedItem();

		switch ( biome )
		{
			case "Forêt dense"  : return Color.GRAY;
			case "Forêt claire" : return Color.LIGHT_GRAY;
			case "Prairie"      : return Color.GREEN;
			case "Bruyère"      : return Color.ORANGE;
			case "Marais"       : return Color.BLUE;
			case "Zone rocheuse": return Color.BLACK;
			case "Montagne"     : return Color.PINK;
			case "Dune"         : return Color.YELLOW;
			case "Urbain"       : return Color.RED;
			case "Industriel"   : return Color.MAGENTA;

			default        : return Color.WHITE;
		}
	}
}
