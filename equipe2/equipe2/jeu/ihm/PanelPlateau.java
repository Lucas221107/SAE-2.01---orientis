package jeu.ihm ; 
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelPlateau extends JPanel
{
    /* - - - - - - - - - - - - - */
    /* Déclaration des attributs */
    /* - - - - - - - - - - - - - */
    private int       nbLig            ;
    private int       nbCol            ;
    private Color[][] tabColors        ;
    private int[][]   tabBalisePlateau ;

    /* - - - - - - - - - - */
    /* Constructeur        */
    /* - - - - - - - - - - */
    public PanelPlateau()
    {
        this.nbLig            = 0   ;
        this.nbCol            = 0   ;
        this.tabColors        = null;
        this.tabBalisePlateau = null;
    }

    public void dessinerPlateau(int lig, int col)
    {
        if (lig != this.nbLig || col != this.nbCol)
        {
            this.nbLig            = lig                              ;
            this.nbCol            = col                              ;
            this.tabColors        = new Color[this.nbLig][this.nbCol];
            for (int l = 0; l < this.nbLig; l++)
                for (int c = 0; c < this.nbCol; c++)
                    this.tabColors[l][c] = new Color(34, 139, 34)   ;
            this.tabBalisePlateau = new int[this.nbLig][this.nbCol]  ;
        }
        repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (this.tabColors == null) return;

        int marge = 20;

        int largCell = (this.getWidth()  - 2 * marge) / this.nbCol;
        int hautCell = (this.getHeight() - 2 * marge) / this.nbLig;

        for (int l = 0; l < this.nbLig; l++)
        {
            for (int c = 0; c < this.nbCol; c++)
            {
                g.setColor(this.tabColors[l][c]);

                g.fillRect(
                    marge + c * largCell,
                    marge + l * hautCell,
                    largCell,
                    hautCell
                );

                g.setColor(Color.BLACK);

                g.drawRect(
                    marge + c * largCell,
                    marge + l * hautCell,
                    largCell,
                    hautCell
                );
            }
        }
    }
}