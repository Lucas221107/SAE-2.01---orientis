package jeu.ihm ; 
import javax.swing.*;
import java.awt.BorderLayout;

public class PanelSolo extends JPanel
{
    private PanelPlateau panelPlateau;
    private PanelPioche  panelPioche;
    private FramePrincipale frame ; 

    public PanelSolo(FramePrincipale frame)
    {
        this.frame = frame ; 
        this.setLayout(new BorderLayout());

        this.panelPlateau = new PanelPlateau();
        this.panelPioche  = new PanelPioche(this.frame);

        this.panelPlateau.dessinerPlateau(7, 7);

        this.add(this.panelPlateau, BorderLayout.CENTER);
        this.add(this.panelPioche , BorderLayout.SOUTH);
    }

}