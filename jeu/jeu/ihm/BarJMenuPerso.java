package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;

public class BarJMenuPerso extends JMenuBar implements ActionListener
{
	
	private JMenuItem     menuiFichierNouveau;
	private JMenuItem     menuiFichierOuvrir;
	private JMenuItem     menuiFichierFermer;
	private JMenuItem     menuiFichierQuitter;

	private Controleur    ctrl;


	public BarJMenuPerso( Controleur ctrl)
	{

		this.ctrl = ctrl;

		JMenu menuFichier   = new JMenu("Fichier"  );
		menuFichier.setMnemonic('f');

		// les items du menu fichier
		this.menuiFichierNouveau         = new JMenuItem ("Nouveau");
		menuiFichierNouveau.setMnemonic(KeyEvent.VK_N);

		this.menuiFichierOuvrir          = new JMenuItem ("Ouvrir" );
		menuiFichierOuvrir.setMnemonic(KeyEvent.VK_O);

		this.menuiFichierFermer          = new JMenuItem ("Fermer" );
		menuiFichierFermer.setMnemonic(KeyEvent.VK_F);

		this.menuiFichierQuitter         = new JMenuItem ("Quitter");
		menuiFichierQuitter.setMnemonic(KeyEvent.VK_Q);



		menuFichier.add( this.menuiFichierNouveau );
		menuFichier.add( this.menuiFichierOuvrir );
		menuFichier.add( this.menuiFichierFermer );
		menuFichier.addSeparator();
		menuFichier.add( this.menuiFichierQuitter );


		// ajout du menu 'Fichier' a la barre de menu
		this.add( menuFichier   );


		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiFichierNouveau        .addActionListener(this);
		this.menuiFichierOuvrir         .addActionListener(this);
		this.menuiFichierFermer         .addActionListener(this);
		this.menuiFichierQuitter        .addActionListener(this);
		
	}


	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.menuiFichierOuvrir )
		{
			JFileChooser chooser = new JFileChooser();

			int returnVal = chooser.showOpenDialog(this);

			if ( returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = chooser.getSelectedFile();
				this.ctrl.setCheminPlateau( file.getAbsolutePath());
			}
		}

	}

}
