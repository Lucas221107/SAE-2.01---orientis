package jeu.ihm;


import javax.swing.*;

import java.awt.Color;
import java.awt.Font;

/*-------------------------------------------------------------------*/
/*  Classe dans l'IHM permettant de donner un style aux composantes
/*  évite la répétition inutile de code
/*-------------------------------------------------------------------*/

public class StyleComposante 
{

	/*---------------------------------------*/
	/*   Méthode pour styliser les boutons   */
	/*---------------------------------------*/
	public void styleBouton ( JButton btn, Color fond, Color texte, Font font )
	{
		btn.setBackground(fond);  // <- change la couleur du bouton
		btn.setForeground(texte); // <- change la couleur de la police d'écriture
		btn.setFont(font); // <- change la font de la police
		btn.setFocusPainted(false); // <- retire le rectangle quand on clique sur le bouton
		btn.setBorder( BorderFactory.createCompoundBorder( 
			BorderFactory.createLineBorder(fond.darker(), 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15) ));  // <- création de la bordure du bouton
	}


	/*---------------------------------------*/
	/*   Méthode pour styliser les JLabel    */
	/*---------------------------------------*/
	public void styleJLabel ( JLabel lbl, Font font, Color texte )
	{
		lbl.setForeground( texte ); // <- change la couleur de la police
		lbl.setFont      ( font  ); // <- change la police d'écriture
		lbl.setBorder    ( BorderFactory.createEmptyBorder( 8, 0, 8, 0)); // <- créer une bordure invisible au label
	}


	/*---------------------------------------*/
	/*   Méthode pour styliser les Panels    */
	/*---------------------------------------*/
	public void stylePanelScore ( JPanel panel)
	{
		panel.setOpaque( true );  // <- rend le fons du panel transparent
		panel.setBackground( new Color( 0,0,0, 120)); // <- opacifie la couleur du background
		panel.setBorder(BorderFactory.createCompoundBorder(
   					    BorderFactory.createLineBorder (new Color(255, 200, 50), 1, true),
                        BorderFactory.createEmptyBorder(10, 15, 10, 15)));  // <- création de bordure autour du panel
	}
}
