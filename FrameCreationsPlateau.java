import javax.swing.*;
import java.awt.BorderLayout;

public class FrameCreationsPlateau extends JFrame
{
    private PanelCreationPlateau panelCreation ; 

    public FrameCreationsPlateau()
    {

        // --- Mise en page ---
        this.setTitle(" Création du plateau ");
        this.setSize(600, 500);
        this.setLayout(new BorderLayout());

        // --- Création et ajout des composants ---
        this.panelCreation = new PanelCreationPlateau();
        this.add(this.panelCreation);

        this.setVisible(true);
        this.pack();

    }
    public static void main(String[] a)
    {
        new FrameCreationsPlateau();
    }
}