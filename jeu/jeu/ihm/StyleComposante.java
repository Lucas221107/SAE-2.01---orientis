package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;

public class StyleComposante 
{
	
	public StyleComposante()
	{

	}


	public void styleBouton ( JButton btn, Color fond, Color texte, Font font )
	{
		btn.setBackground(fond);
		btn.setForeground(texte);
		btn.setFont(font);
		btn.setFocusPainted(false);
		btn.setBorder( BorderFactory.createCompoundBorder( 
			BorderFactory.createLineBorder(fond.darker(), 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
		 ));
	}

	public void styleJTextField ( JTextField txt, Color fond, Color texte)
	{
		txt.setBackground(fond);
		txt.setForeground(texte);

		txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder( new Color( 40,  67,  43), 1, true),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
	}
}
