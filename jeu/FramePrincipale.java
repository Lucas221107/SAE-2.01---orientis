import javax.swing.*;
import java.awt.Dimension;
import java.awt.CardLayout;

public class FramePrincipale extends JFrame
{
	private PanelAccueil    panelAccueil;
	private StyleComposante style;
	private PanelRegle      panelRegle  ;
	private PanelMulti      panelMulti  ;
	private Controleur      ctrl        ;

	private int          hauteur     ;
	private int          largeur     ;

	private CardLayout   cardLayout  ;

	private JPanel       panelPrincipal;

	public FramePrincipale ( Controleur ctrl)
	{

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.hauteur = (int) ( tailleEcran.getHeight() - 45);
		this.largeur = (int) ( tailleEcran.getWidth ()     );
		
		
		this.setTitle   ("Orientis");
		this.setLocation( 0, 0);
		this.setSize    ( this.getDimensionPanel() );

		this.ctrl = ctrl;
		this.style = new StyleComposante();

		this.cardLayout = new CardLayout();
		this.panelPrincipal = new JPanel ( this.cardLayout );
		
		this.panelAccueil = new PanelAccueil( this );
		this.panelRegle   = new PanelRegle  ( this );
		this.panelMulti   = new PanelMulti  ( this , this.style);

		//ajout des panel avec le cardLayout

		this.panelPrincipal.add( this.panelAccueil, "accueil");
		this.panelPrincipal.add( this.panelRegle  , "regle"  );
		this.panelPrincipal.add( this.panelMulti  , "multi"  );

		this.cardLayout.show ( this.panelPrincipal, "accueil");


		this.add( this.panelPrincipal);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public Dimension getDimensionPanel()  { return new Dimension ( this.largeur, this.hauteur ); }

	public void switchPanel ( String nom )
	{
		this.cardLayout.show ( this.panelPrincipal, nom );
	}
}