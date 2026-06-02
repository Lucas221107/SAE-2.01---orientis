import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class FrameConfigJeux extends JFrame
{
    PanelConfigJeux panelConfig ; 
    public FrameConfigJeux()
    {
        this.panelConfig = new PanelConfigJeux(this);
        this.add(this.panelConfig);

        this.setTitle("Configuration du jeu");
        this.setSize(800, 600); // ou pack()
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
        
    }
    public static void main(String[] a)
    {
        new FrameConfigJeux();
    }
}