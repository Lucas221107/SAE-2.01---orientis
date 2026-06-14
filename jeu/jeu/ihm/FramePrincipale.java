package jeu.ihm;
import jeu.Controleur;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class FramePrincipale extends JFrame
{
    private PanelAccueil           panelAccueil       ;
    private PanelChargementPlateau panelChargement    ;
    private PanelChargementDuo     panelChargementDuo ;
    private PanelImage             panelImage         ;
    private PanelPlateau           panelPlateau       ;
    private PanelSolo              panelSolo          ;
    private StyleComposante        style              ;
    private PanelRegle             panelRegle         ;
    private PanelMulti             panelMulti         ;
    private PanelDuo               panelDuo           ;
    private Controleur             ctrl               ;

    private int        hauteur        ;
    private int        largeur        ;
    private CardLayout cardLayout     ;
    private JPanel     panelPrincipal ;

    public FramePrincipale(Controleur ctrl)
    {
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.hauteur = (int)(tailleEcran.getHeight() - 45);
        this.largeur = (int)(tailleEcran.getWidth()      );

        this.setTitle   ("Orientis");
        this.setLocation(0, 0);
        this.setSize    (this.getDimensionPanel());

        this.ctrl  = ctrl;
        this.style = new StyleComposante();

        this.panelImage = new PanelImage(this.ctrl);

        this.cardLayout     = new CardLayout();
        this.panelPrincipal = new JPanel(this.cardLayout);

        // Plateau solo uniquement ici
        this.panelPlateau = new PanelPlateau(this, this.ctrl);

        this.panelSolo = new PanelSolo(this, this.style, this.ctrl);
        this.panelDuo  = new PanelDuo (this, this.style, this.ctrl);

        this.panelAccueil = new PanelAccueil(this);
        this.panelRegle   = new PanelRegle  (this);
        this.panelMulti   = new PanelMulti  (this, this.style);

        this.panelChargement    = new PanelChargementPlateau(this, this.ctrl, this.style, this.panelPlateau);
        this.panelChargementDuo = new PanelChargementDuo(this, this.ctrl, this.style);

        this.panelPrincipal.add(this.panelAccueil       , "accueil"       );
        this.panelPrincipal.add(this.panelRegle         , "regle"         );
        this.panelPrincipal.add(this.panelMulti         , "multi"         );
        this.panelPrincipal.add(this.panelChargement    , "chargement"    );
        this.panelPrincipal.add(this.panelChargementDuo , "chargementDuo" );
        this.panelPrincipal.add(this.panelSolo          , "solo"          );
        this.panelPrincipal.add(this.panelDuo           , "duo"           );

        this.cardLayout.show(this.panelPrincipal, "accueil");
        this.add(this.panelPrincipal);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Dimension getDimensionPanel() { return new Dimension(this.largeur, this.hauteur); }

    public PanelPlateau           getPanelPlateau()       { return this.panelPlateau   ; }
    public PanelChargementPlateau getPanelChargement()    { return this.panelChargement; }
    public PanelChargementDuo     getPanelChargementDuo() { return this.panelChargementDuo; }
	public PanelDuo getPanelDuo() { return this.panelDuo; }

    public void maj() { this.panelImage.repaint(); }

    public void switchPanel(String nom)
    {
        if (nom.equals("solo"))
        {
            this.panelChargement.remove(this.panelPlateau);
            this.panelChargement.revalidate();
            this.panelSolo.add(this.panelPlateau, BorderLayout.CENTER);
            this.panelPlateau.setOpaque(false);
            this.panelSolo.revalidate();
            this.panelPlateau.repaint();
        }

        if (nom.equals("duo"))
        {
            this.panelDuo.getPanelPlateauJ1().setOpaque(false);
            this.panelDuo.getPanelPlateauJ2().setOpaque(false);
            this.panelDuo.getPanelPlateauJ1().charger(this.ctrl.getCheminPlateau());
            this.panelDuo.getPanelPlateauJ2().charger(this.ctrl.getCheminPlateau());
            this.panelDuo.revalidate();
            this.panelDuo.repaint();

			this.panelDuo.ajouterPlateaux(this.panelDuo.getPanelPlateauJ1(),this.panelDuo.getPanelPlateauJ2());
        }

        if (nom.equals("chargement"))
            this.setJMenuBar(this.panelChargement.getMaBarre());
        else if (nom.equals("chargementDuo"))
            this.setJMenuBar(this.panelChargementDuo.getMaBarre());
        else
            this.setJMenuBar(null);

        this.cardLayout.show(this.panelPrincipal, nom);
        this.revalidate();
        this.repaint();
    }
}