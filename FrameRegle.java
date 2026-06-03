import javax.swing.*;
import java.awt.Dimension;

public class FrameRegle extends JFrame
{
	
	private PanelRegle panelRegle;

	public FrameRegle()
	{
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setTitle("Orientis");
		this.setLocation(0,0);
		this.setSize( (int) tailleEcran.getWidth(), (int) (tailleEcran.getHeight() - 45) );

		this.panelRegle = new PanelRegle();

		this.add( this.panelRegle );

		this.setVisible(true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
