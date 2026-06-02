import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
 
public class FrameAccueil extends JFrame
{
	private PanelAccueil panelAccueil;
 
	public FrameAccueil ()
	{
 
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
 
		this.setTitle ("Orientis");
		this.setLocation( 20, 20);
		this.setSize( tailleEcran );
 
		//this.ctrl = ctrl;
		this.panelAccueil = new PanelAccueil();
 
		this.add( this.panelAccueil);
 
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
 
 
	public static void main( String[] args)
	{
		new FrameAccueil();
	}
}