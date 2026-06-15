package jeu.ihm ; 


import jeu.Controleur;

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
    private StyleComposante  style            ;
    private JPanel           panelGauche      ;   
    private JPanel           panelDroit       ; 
    private JLabel           lblPioche        ;
    private JLabel           lblFanionPiochee ;
    private FramePrincipale  frame            ;
    private Controleur       ctrl             ;
    private JButton          btnPasserSonTour ;

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
        this.panelGauche.setOpaque(false);

        this.panelDroit  = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        this.panelDroit.setOpaque(false);


        JLabel lblPiocher     = new JLabel("Appuyer sur la pioche pour piocher ");
        lblPiocher.setForeground(Color.BLACK);
        lblPiocher.setFont( lblPiocher.getFont().deriveFont(Font.BOLD, 18f));

        this.lblPioche        = new JLabel();
        this.lblFanionPiochee = new JLabel();


        // Bouton passer son tour
        this.btnPasserSonTour = new JButton(" Passer son tour ");
        this.style.styleBouton(this.btnPasserSonTour, VERT_FORET, Color.WHITE, fontBouton);
        
        /* création d'une image et on la redimensionne avec le getScaleInstance */
        Image imgPioche = new ImageIcon("../jeu/ihm/images/pioche.png").getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);
        this.lblPioche.setIcon(new ImageIcon(imgPioche));

        /* - - - - - - - - - - - - - - - */
        /* Ajout des composants          */
        /* - - - - - - - - - - - - - - - */

        // panel gauche 
        this.panelGauche.add(lblPiocher);
        this.panelGauche.add(new JLabel()   );
        this.panelGauche.add(this.btnPasserSonTour);

        // panel droit
        this.panelDroit.add(this.lblPioche);
        this.panelDroit.add(this.lblFanionPiochee);

        // panel this
        this.add(this.panelGauche, BorderLayout.WEST);
        this.add(this.panelDroit,  BorderLayout.CENTER);


        /* - - - - - - - - - - - - - - - - */
        /*  Activation des composantes     */
        /* - - - - - - - - - - - - - - - - */
        this.btnPasserSonTour.addActionListener(this);

        GererSouris gererSouris = new GererSouris();
        this.lblPioche.addMouseListener(gererSouris);
     
    }

    /*==========================================================================================*/

    /* - - - - - - - - -*/
    /*     méthodes     */
    /* - - - - - - - - -*/
    
    /* méthode qui reinitialise l'affichage de la pioche */
    public void reinitialiserAffichage()
    {
        this.lblFanionPiochee.setIcon(null);
    }
  
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnPasserSonTour)
        {
            if (! this.ctrl.peutPiocher()) // <- est ce que le joueue peut piocher
            {
                 this.ctrl.passerSonTour(); 

                /* on vérifie si la manche est terminé */
                if ( this.ctrl.estMancheTermine())
                {
                    int numManche = this.ctrl.getNumeroManche();
                    int score     = this.ctrl.getScoreJoueurCourant();
                    
                    this.ctrl.terminerManche(); // <- on termine la manche
                    this.reinitialiserAffichage(); // <- on reinitialise l'affichage de la pioche

                    this.frame.getPanelSolo().ajouterScoreManche(numManche, score); // <- ajoute le score que le joueur a fait pendant la manche
                    this.frame.getPanelPlateau().nouvelleManche(); // <- on recommence une nouvelle manche
                }
            }
               
        }
    }

    private class GererSouris extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            /* on vérifie que le click est bien sur le label de la pioche */
            if (e.getSource() != PanelPioche.this.lblPioche)
                return;
            
            /* on verifie que la manche 'est pas terminer */
            if ( PanelPioche.this.ctrl.estMancheTermine())
                return;

            /* on vérifie que le joueur à le droit de piocher */
            if (! PanelPioche.this.ctrl.peutPiocher())
                return;


            PanelPioche.this.ctrl.piocher();

            int    numFanion  = PanelPioche.this.ctrl.getNumeroFanionCourant(); // <- on recupere le numero de la carte pioché
            String typeFanion = PanelPioche.this.ctrl.getTypeFanionCourant  (); // <- on recupere le type de la carte pioché ( clair ou foncé );

            String chemin = "../jeu/ihm/images/Fanion_" + typeFanion + "_" + numFanion + ".png";

            /* on redimensionne les images pour pouvoir les affiché sinon elle ne s'affiche pas */
            Image imgFanion = new ImageIcon(chemin).getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);

            PanelPioche.this.lblFanionPiochee.setIcon( new ImageIcon (imgFanion));

            /* on redessine le panel plateau */
            PanelPioche.this.frame.getPanelPlateau().repaint();
        }    
    }
}
