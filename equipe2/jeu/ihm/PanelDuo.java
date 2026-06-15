package jeu.ihm ;

import javax.swing.*;
import jeu.Controleur ;

import java.awt.event.*     ;
import java.awt.BorderLayout;
import java.awt.Graphics    ;
import java.awt.GridLayout  ;
import java.awt.Image       ;
import java.awt.Font        ;
import java.awt.Color       ;
import java.awt.FlowLayout  ;

public class PanelDuo extends JPanel implements ActionListener
{
    /*- - - - - - - - - - - - -*/
    /* Constantes des couleurs */
    /*- - - - - - - - - - - - -*/
    private final Color VERT_FORET   = new Color( 40,  67,  43);
    private final Color BEIGE_BOUTON = new Color(230, 218, 196);
    private final Color TEXTE_FONCE  = new Color( 51,  35,  21);

    /*- - - - - - - - - - - - -*/
    /* Attributs               */
    /*- - - - - - - - - - - - -*/
    private PanelPiocheDuo  panelPiocheDuo   ;
    private PanelPlateau    panelPlateauJ1   ;
    private PanelPlateau    panelPlateauJ2   ;
    private PanelChargementDuo panelChargementDuo  ;
    private Controleur      ctrl             ;
    private StyleComposante style            ;
    private FramePrincipale frame            ;

    private JPanel          panelDeuxPlateau ;
    private JPanel          panelOption      ;

    private boolean         optionActiver    ;

    private JButton         btnOption        ;
    private JButton         btnQuitter       ;
    private JButton         btnDemo          ;
    private JButton         btnRecommencer   ;

    private Image           imgFond          ;

    /*- - - - - - - - - - - - -*/
    /* Constructeur            */
    /*- - - - - - - - - - - - -*/
    public PanelDuo(FramePrincipale frame, StyleComposante style, Controleur ctrl)
    {
        this.setLayout(new BorderLayout());

        /*- - - - - - - - - - - - -*/
        /* Création des attributs  */
        /*- - - - - - - - - - - - -*/
        this.ctrl  = ctrl  ;
        this.frame = frame ;
        this.style = style ;

        Font fontBouton = new Font("SansSerif", Font.BOLD, 13);

        this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond.png");

        this.optionActiver = false;

        /*=============================== boutons ===============================*/
        this.btnDemo  = new JButton("Démo");
        this.style.styleBouton(this.btnDemo, this.VERT_FORET, Color.WHITE, fontBouton);

        this.btnOption = new JButton("Option");
        this.style.styleBouton(this.btnOption, this.BEIGE_BOUTON, this.TEXTE_FONCE, fontBouton);

        this.btnRecommencer = new JButton("Recommencer la partie");
        this.style.styleBouton(this.btnRecommencer, this.VERT_FORET, Color.WHITE, fontBouton);

        this.btnQuitter = new JButton("Quitter la partie");
        this.style.styleBouton(this.btnQuitter, this.VERT_FORET, Color.WHITE, fontBouton);

        /*=============================== panels ===============================*/
        JPanel panelHaut = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHaut.setOpaque(false);

        this.panelOption = new JPanel(new GridLayout(9, 1, 3, 3));
        this.panelOption.setOpaque(false);
        this.panelOption.setVisible(this.optionActiver);

        /* Les deux plateaux côte à côte au centre */
        this.panelDeuxPlateau = new JPanel(new GridLayout(1, 2));
        this.panelDeuxPlateau.setOpaque(false);
        this.panelPlateauJ1 = new PanelPlateau(this.frame, this.ctrl);
        this.panelPlateauJ2 = new PanelPlateau(this.frame, this.ctrl);
        this.panelPlateauJ1.setIndexJoueur(0);
        this.panelPlateauJ2.setIndexJoueur(1);
        this.panelDeuxPlateau.add(this.panelPlateauJ1);
        this.panelDeuxPlateau.add(this.panelPlateauJ2);

       
       
        this.panelPiocheDuo = new PanelPiocheDuo(this.frame, this.ctrl, this.panelPlateauJ1, this.panelPlateauJ2);
        this.panelPiocheDuo.setOpaque(false );

        this.panelPlateauJ1.setPanelPiocheDuo(this.panelPiocheDuo);
        this.panelPlateauJ2.setPanelPiocheDuo(this.panelPiocheDuo);
        /*- - - - - - - - - - - - - - - -*/
        /* Positionnement des attributs  */
        /*- - - - - - - - - - - - - - - -*/

        // panel haut
        panelHaut.add(this.btnOption);

        // panel option
        this.panelOption.add(this.btnDemo       );
        this.panelOption.add(this.btnRecommencer);
        this.panelOption.add(this.btnQuitter    );
        this.panelOption.add(new JLabel()       );
        this.panelOption.add(new JLabel()       );
        this.panelOption.add(new JLabel()       );
        this.panelOption.add(new JLabel()       );
        this.panelOption.add(new JLabel()       );

        // panel this
        this.add(this.panelPiocheDuo  , BorderLayout.SOUTH );
        this.add(this.panelOption     , BorderLayout.WEST  );
        
        this.add(panelHaut            , BorderLayout.NORTH );
        this.add(this.panelDeuxPlateau, BorderLayout.CENTER);

        /*- - - - - - - - - - - - - -*/
        /* Activation des attributs  */
        /*- - - - - - - - - - - - - -*/
        this.btnOption     .addActionListener(this);
        this.btnQuitter    .addActionListener(this);
        this.btnRecommencer.addActionListener(this);
    }

    /*=============================== Méthodes ===============================*/
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnOption)
        {
            this.optionActiver = !this.optionActiver;
            this.panelOption.setVisible(this.optionActiver);
            this.revalidate();
            this.repaint();
        }

        if (e.getSource() == this.btnQuitter)
        {

        }

        if (e.getSource() == this.btnRecommencer)
        {

        }
    }

    /* méthode paintComponent permettant de mettre une image de fond */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(this.imgFond, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public PanelPlateau getPanelPlateauJ1() { return this.panelPlateauJ1; }
    public PanelPlateau getPanelPlateauJ2() { return this.panelPlateauJ2; }
    public PanelPiocheDuo getPanelPiocheDuo() { return this.panelPiocheDuo; }

    public void ajouterPlateaux(PanelPlateau j1, PanelPlateau j2)
    {
        this.panelDeuxPlateau.removeAll();
        this.panelDeuxPlateau.add(j1);
        this.panelDeuxPlateau.add(j2);
        this.panelDeuxPlateau.revalidate();
        this.panelDeuxPlateau.repaint();
    }

}