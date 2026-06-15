package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;

public class BarJMenuPerso extends JMenuBar implements ActionListener
{
	/*======================*/
	/*      Attributs       */
	/*======================*/
	private JMenuItem     menuiFichierOuvrir;

	private Controleur    ctrl;

	/*======================*/
	/*    Constructeur      */
	/*======================*/
	public BarJMenuPerso( Controleur ctrl)
	{

		/*-------------------------------*/
		/*   Création des composants     */
		/*-------------------------------*/
		this.ctrl = ctrl;

		JMenu menuFichier   = new JMenu("Fichier"  );
		menuFichier.setMnemonic('f');

		// les items du menu fichier
		this.menuiFichierOuvrir          = new JMenuItem ("Ouvrir" );
		menuiFichierOuvrir.setMnemonic(KeyEvent.VK_O);


		/*-----------------------------------*/
		/* Positionnement des composants     */
		/*-----------------------------------*/

		menuFichier.add( this.menuiFichierOuvrir );

		// ajout du menu Fichier a la barre de menu
		this.add( menuFichier );


		/*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiFichierOuvrir         .addActionListener(this);
		
	}


	public void actionPerformed ( ActionEvent e )
	{
		if ( e.getSource() == this.menuiFichierOuvrir )
		{
			JFileChooser chooser = new JFileChooser(new File(".."));

			int returnVal = chooser.showOpenDialog(this);

			if ( returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = chooser.getSelectedFile();
				this.ctrl.setCheminPlateau( file.getAbsolutePath()); // <- récupere le chmin absolu du fichier et l'envoie au controleur
			}
		}
	}
}
