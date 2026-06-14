package jeu.ihm ; 


import jeu.metier.Pioche;
import jeu.Controleur;
import jeu.metier.Fanion;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;

public class PanelPioche extends JPanel implements ActionListener
{
    private final Color VERT_FORET   = new Color( 40,  67,  43) ;
    
    /* - - - - - - - - - - - - - */
	/* Déclaration des attributs */
	/* - - - - - - - - - - - - - */   
    private StyleComposante  style;
    private JPanel panelGauche     ; 
    private Pioche pioche              ;  
    private JPanel panelDroit         ; 
    private JLabel lblPioche           ;
    private JLabel lblFanionPiochee    ;
    private FramePrincipale frame      ; 
    private Controleur ctrl;
    private JButton btnPasserSonTour   ;

    /* - - - - - - - - - - */
	/* Constructeur        */
	/* - - - - - - - - - - */

    public PanelPioche(FramePrincipale frame, Controleur ctrl, StyleComposante style)
    {
        this.ctrl  = ctrl ;
        this.frame = frame ;
        this.style = style;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 150));
        /* - - - - - - - - - - - - - - - */
        /* Initialisation des attributs  */
        /* - - - - - - - - - - - - - - - */	

        /* Création de font pour les labels ainsi que les boutons */
        Font fontBouton = new Font("SansSerif", Font.BOLD, 13);

        this.panelGauche = new JPanel(new GridLayout(4,1));
        this.panelDroit  = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        this.pioche      = new Pioche();


        JLabel lblPiocher     = new JLabel("Appuyer sur la pioche pour piocher ");
        this.lblPioche        = new JLabel();
        this.lblFanionPiochee = new JLabel();

        ///////////////////////////////////
        // Bouton passer son tour
        this.btnPasserSonTour = new JButton(" Passer son tour ");
        this.style.styleBouton(this.btnPasserSonTour, VERT_FORET, Color.WHITE, fontBouton);
        

        lblPiocher.setForeground(Color.BLACK);
        lblPiocher.setFont( lblPiocher.getFont().deriveFont(Font.BOLD, 18f));

        this.panelGauche.setOpaque(false);
        this.panelDroit.setOpaque(false);

        Image imgPioche = new ImageIcon("../jeu/ihm/images/pioche.png").getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);
        this.lblPioche.setIcon(new ImageIcon(imgPioche));

        // MVC : reconstituer via le contrôleur
        this.ctrl.reconstituer(this.pioche);

        /* - - - - - - - - - - - - - - - */
        /* Ajout des composants          */
        /* - - - - - - - - - - - - - - - */
        this.panelGauche.add(lblPiocher);
        this.panelGauche.add(new JLabel()   );
        this.panelGauche.add(this.btnPasserSonTour);

        this.panelDroit.add(this.lblPioche);
        this.panelDroit.add(this.lblFanionPiochee);

        this.add(this.panelGauche, BorderLayout.WEST);
        this.add(this.panelDroit,  BorderLayout.CENTER);



         /* - - - - - - - - - - - - - - -   */
        /*      /Mise sur ecoute    /      */
        /* - - - - - - - - - - - - - - -  */
        this.btnPasserSonTour.addActionListener(this);
        GererSouris gererSouris = new GererSouris();
        this.lblPioche.addMouseListener(gererSouris);

        
    }
  
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnPasserSonTour)
        {

        }
    }

    private class GererSouris extends MouseAdapter
    {
        String  fanionPiochee ;
        int     numFanion     ;
        String  typeFanion    ;
        boolean estFoncee = false ;
        int     cptFoncee = 0     ;

        public void mouseClicked(MouseEvent e)
        {
            if (e.getSource() == PanelPioche.this.lblPioche)
            {
                if ( PanelPioche.this.ctrl.estMancheTermine())
                {
                    // MVC : estVide via le contrôleur
                    if (PanelPioche.this.ctrl.estVide(PanelPioche.this.pioche))
                        return ;

                    // MVC : piocher via le contrôleur
                    Fanion fanion = PanelPioche.this.ctrl.piocher(PanelPioche.this.pioche);
                    this.fanionPiochee = fanion.toString();

                    String stringNumFanion = this.fanionPiochee.substring(6);
                    this.numFanion  = Integer.parseInt(stringNumFanion);
                    this.typeFanion = this.fanionPiochee.substring(0, 5);

                    PanelPioche.this.frame.getPanelPlateau().setFanionCourant( this.numFanion );

                    if (this.typeFanion.equals("Clair"))
                        this.estFoncee = false ;

                    if (this.typeFanion.equals("Fonce"))
                        this.estFoncee = true ;

                    PanelPioche.this.lblFanionPiochee.setIcon( new ImageIcon("../jeu/ihm/images/Fanion_" + this.typeFanion + "_" + this.numFanion + ".png"));

                    if (this.estFoncee)
                        this.cptFoncee++;
                }

                
                PanelPioche.this.ctrl.terminerManche();


            }
        }
    }
}
