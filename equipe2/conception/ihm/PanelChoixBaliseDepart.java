package conception.ihm;

import conception.Controleur;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.Color;
import java.awt.GradientPaint;
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
	private JLabel          lblCompteur   ;
	

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
		this.lblCompteur    = new JLabel (" Balises de départ sélectionnées : 0 / 5");

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
		panelGauche.add( this.lblCompteur);
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.add( new JLabel());
		panelGauche.setOpaque(false);

		//panel this
		this.add ( panelGauche, BorderLayout.CENTER);
		this.add ( panelBtn   , BorderLayout.SOUTH );

		/* - - - - - - - - - - - - - - - - */
		/* Activation des Composants       */
		/* - - - - - - - - - - - - - - - - */

		this.btnRetour     .addActionListener(this);
		this.btnSauvegarder.addActionListener(this);

		this.panelPlateau.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				int nb = PanelChoixBaliseDepart.this.panelPlateau.getNbDepartSelectionne();
				PanelChoixBaliseDepart.this.lblCompteur.setText(" Balises de départ sélectionnées : " + nb + " / 5");
			}
		});
		
	}

	/* ========================================================= */

	public void actionPerformed ( ActionEvent e)
	{
		if ( e.getSource() == this.btnRetour)
		{
			this.panelPlateau.setModeSelectionDepart(false);
			this.panelPlateau.setAfficheLiaison(false);
			
			this.frame.switchPanel("balise");
			this.frame.revenirEtape();
		}

		if ( e.getSource() == this.btnSauvegarder)
		{
			this.panelPlateau.setTabDepart( this.panelPlateau.getTabDepart());
			this.ctrl.genererPlateau();

			if ( this.ctrl.sauvegarder() )
				JOptionPane.showMessageDialog( this, "Le plateau a été sauvegardé dans plateau.data");
			else
				JOptionPane.showMessageDialog( this, "Erreur lors de la sauvegarde du plateau", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
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