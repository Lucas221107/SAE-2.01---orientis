import javax.swing.*;
import java.awt.event.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelAccueil extends JPanel implements ActionListener
{
	
	private JButton btnRegle;
	private JButton btnSolo;
	private JButton btnMulti;

	private Image   imgFond;

	public PanelAccueil()
	{
		this.setLayout( new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/*--------------------------------*/
		/*    Création des composantes    */
		/*--------------------------------*/

		Dimension dimBtn = new Dimension( 90, 30 );

		this.btnSolo = new JButton("Jouer en solo");
		this.btnSolo.setPreferredSize(dimBtn);

		this.btnMulti = new JButton("Jouer en Multi");
		this.btnMulti.setPreferredSize(dimBtn);

		this.btnRegle = new JButton("Règle");
		this.btnRegle.setPreferredSize(dimBtn);
		
		this.imgFond = getToolkit().getImage("./images/ecran_d_acceuil.png");

		JPanel panelBtn = new JPanel( new FlowLayout( FlowLayout.CENTER));
		panelBtn.setOpaque(false);


		/*--------------------------------------*/
		/*    Positionnement des composantes    */
		/*--------------------------------------*/

		panelBtn.add( this.btnSolo  );
		panelBtn.add( this.btnMulti );
		panelBtn.add( this.btnRegle );

		this.add ( panelBtn, gbc );

		/*----------------------------------*/
		/*    activation des composantes    */
		/*----------------------------------*/


		this.btnSolo .addActionListener(this);
		this.btnMulti.addActionListener(this);
		this.btnRegle.addActionListener(this);
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g.drawImage ( this.imgFond, 0, 0, getWidth(), getHeight(), this );
	}


	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.btnSolo)
		{
			// ouvertur de la frame config
			new FrameConfigJeux();

			//récupération de la fenetre pour la fermer 
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

			if ( frame != null )
				frame.dispose();
		}

		if ( e.getSource() == this.btnRegle)
		{
			// Ouverture de la frame Regle
			new FrameRegle();

			//récupération de la fenetre pour la fermer
			JFrame frame = ( JFrame ) SwingUtilities.getWindowAncestor(this);

			if ( frame != null )
				frame.dispose();
		}

		if ( e.getSource() == this.btnMulti)
		{
			System.out.println("ouverture de la page multi");
		}
	}
}
