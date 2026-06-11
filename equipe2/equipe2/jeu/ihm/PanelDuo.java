package jeu.ihm ;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelDuo extends JPanel
{
    private PanelPlateau  panelPlateauJ1;
    private PanelPlateau panelPlateauJ2 ; 
    private PanelPioche   panelPioche;
    private JPanel        panelDeuxPlateau ; 
    private FramePrincipale  frame         ; 

    public PanelDuo(FramePrincipale frame)
    {
        this.frame = frame ; 
        this.setLayout(new BorderLayout());

        this.panelPioche  = new PanelPioche(this.frame);
        this.panelDeuxPlateau = new JPanel(new GridLayout(1, 2)); 
        this.panelPlateauJ1 = new PanelPlateau() ; 
        this.panelPlateauJ2 = new PanelPlateau() ; 


        this.panelPlateauJ1.dessinerPlateau(7, 7);
        this.panelPlateauJ2.dessinerPlateau(7, 7);

        this.panelDeuxPlateau.add(this.panelPlateauJ1);
        this.panelDeuxPlateau.add(this.panelPlateauJ2);

        this.add                 (this.panelDeuxPlateau,BorderLayout.CENTER) ; 
        this.add                 (this.panelPioche , BorderLayout.SOUTH);
    }


}