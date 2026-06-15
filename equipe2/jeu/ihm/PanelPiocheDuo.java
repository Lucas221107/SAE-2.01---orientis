package jeu.ihm ; 


import javax.swing.*;

import jeu.Controleur ; 

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;


public class PanelPiocheDuo extends JPanel implements ActionListener
{
    /* - - - - - - - - - - - - - */
    /* Déclaration des attributs */
    /* - - - - - - - - - - - - - */

    private JLabel              lblPiocher    ;
    private JPanel              panelGauche   ;
    private JPanel              panelDroit    ;
    private JLabel              lblPioche     ;
    private JLabel              lblFanionPiochee ;
    private JLabel              lblTourJoueur ;
    private JButton             btnPasserSonTour ;
    private FramePrincipale     frame         ;
    private Controleur          ctrl          ;
    private GererSouris         gererSouris   ;

    private PanelPlateau        panelPlateauJ1 ;
    private PanelPlateau        panelPlateauJ2 ;
    private JLabel              lblPointJ1     ;
    private JLabel              lblPointJ2     ;

    // 1 = tour J1, 2 = tour J2
    private int                 nbPointJ1     ;
    private int                 nbPointJ2     ;
    private int                 indiceJoueurActif; 


    /* - - - - - - - - - - */
    /* Constructeur        */
    /* - - - - - - - - - - */
    public PanelPiocheDuo(FramePrincipale frame, Controleur ctrl, PanelPlateau j1, PanelPlateau j2)
    {
        this.ctrl           = ctrl  ;
        this.frame          = frame ;
        this.panelPlateauJ1 = j1    ;
        this.panelPlateauJ2 = j2    ;
    

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 150));

        this.panelGauche      = new JPanel(new GridLayout(3, 2));
        this.panelDroit       = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        this.gererSouris      = new GererSouris();
        this.lblPiocher       = new JLabel("Appuyer sur la pioche pour piocher ");
        this.lblPioche        = new JLabel();
        this.lblFanionPiochee = new JLabel();
        this.lblPointJ1       = new JLabel() ;
        this.lblPointJ2       = new JLabel() ; 


        this.indiceJoueurActif = 0;

        //on commence la partie dans le init 
        //this.ctrl.creerPartieDuo((this.ctrl.getPartieJ1().getJoueur().getNom()), (this.ctrl.getPartieJ2().getJoueur()).getNom());

        this.btnPasserSonTour = new JButton(" Passer son tour ");


        this.lblTourJoueur = new JLabel(" Tour du joueur : ");


        this.lblPiocher.setForeground(Color.WHITE);
  

        this.lblPointJ1.setForeground(Color.WHITE);

        this.lblPointJ2.setForeground(Color.WHITE);


        this.panelGauche.setOpaque(false);
        this.panelDroit.setOpaque(false);

        Image imgPioche = new ImageIcon("../jeu/ihm/images/pioche.png").getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);
        this.lblPioche.setIcon(new ImageIcon(imgPioche));


        this.panelGauche.add(this.lblPiocher);
        this.panelGauche.add(this.btnPasserSonTour);
        this.panelGauche.add(this.lblTourJoueur);
        this.panelGauche.add(this.lblPointJ1) ; 
        this.panelGauche.add(this.lblPointJ2) ; 

        this.panelDroit.add(this.lblPioche);
        this.panelDroit.add(this.lblFanionPiochee);

        this.add(this.panelGauche, BorderLayout.WEST);
        this.add(this.panelDroit,  BorderLayout.CENTER);

        this.btnPasserSonTour.addActionListener(this);
        this.lblPioche.addMouseListener(this.gererSouris);

       
        
        this.majLabelTour();
    }

    //methode qu'on va appeler dans le pour commencer une partie et recup les info des joueur creer au plateau chargement
    public void init()
    {
        if (this.ctrl.getPartie() == null )
            return;

        this.indiceJoueurActif = 0;
        this.lblPointJ1.setText(this.ctrl.getPartie().getJoueurs().get(0).getNom() + " : " + this.nbPointJ1);
        this.lblPointJ2.setText(this.ctrl.getPartie().getJoueurs().get(1).getNom() + " : " + this.nbPointJ2);
        this.majLabelTour();
        this.gererSouris.reset();
        this.lblFanionPiochee.setIcon(null);
        this.panelPlateauJ1.setActif(false);
        this.panelPlateauJ2.setActif(false);
    }


    public void joueurAJoue(int indexJoueurQuiVientDeJouer)
    {
        this.majLabelTour();

        if (indexJoueurQuiVientDeJouer == 0)
        {
            // J1 vient de jouer → activer J2
            this.indiceJoueurActif = 1;
            this.panelPlateauJ1.setActif(false);
            this.panelPlateauJ2.setActif(true);
        }
        else
        {
            // J2 vient de jouer → les deux ont joué, réinitialiser
            this.indiceJoueurActif = 0;
            this.ctrl.reinitialiserFanionDuo();
            this.gererSouris.reset();

            this.panelPlateauJ1.setActif(false);
            this.panelPlateauJ2.setActif(false);
        }

        this.panelPlateauJ1.repaint();
        this.panelPlateauJ2.repaint();
    }


    public void mancheTermineeDuo(int numManche, int score) 
    {
        // à compléter selon ta logique de fin de manche duo
        this.reinitialiserAffichage();
        this.ctrl.terminerManche();
        this.majLabelTour();
        this.panelPlateauJ1.repaint();
        this.panelPlateauJ2.repaint();
    }


    /* Met à jour le label avec le nom du joueur courant */
    private void majLabelTour()
    {
        this.lblTourJoueur.setText("Tour de : " + this.ctrl.getNomJoueurCourant());
    }


    public void actionPerformed(ActionEvent e)
    {
           
        if (e.getSource() == this.btnPasserSonTour)
        {
            if (this.indiceJoueurActif == 0 && this.ctrl.peutPiocher())
                return;
            
            this.ctrl.passerSonTour();;
            this.joueurAJoue( this.indiceJoueurActif );
        }
    }


    private class GererSouris extends MouseAdapter
    {
        int     numFanion     ;
        String  typeFanion    ;
        boolean estFoncee = false ;

        public void mouseClicked(MouseEvent e)
        {
            if (e.getSource() != PanelPiocheDuo.this.lblPioche)
                return;


            if (!PanelPiocheDuo.this.ctrl.peutPiocher()) 
                return;

            if (PanelPiocheDuo.this.indiceJoueurActif != 0)
                return;

            if (PanelPiocheDuo.this.ctrl.estMancheTermine())
            {
                PanelPiocheDuo.this.reinitialiserAffichage();
                return;
            }

            PanelPiocheDuo.this.ctrl.piocher();

            this.numFanion  = PanelPiocheDuo.this.ctrl.getNumeroFanionCourant();
            this.typeFanion = PanelPiocheDuo.this.ctrl.getTypeFanionCourant();

            String chemin = "../jeu/ihm/images/Fanion_" + this.typeFanion + "_" + this.numFanion + ".png";
            Image imgFanion = new ImageIcon(chemin).getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);
            PanelPiocheDuo.this.lblFanionPiochee.setIcon(new ImageIcon(imgFanion));

            // Après pioche : toujours activer J1 d'abord
            PanelPiocheDuo.this.panelPlateauJ1.setActif(true);
            PanelPiocheDuo.this.panelPlateauJ2.setActif(false);

            PanelPiocheDuo.this.panelPlateauJ1.repaint();
            PanelPiocheDuo.this.panelPlateauJ2.repaint();
        }

        public void reset()
        {
            this.estFoncee = false;
        }
    }


    public void reinitialiserAffichage()
    {
        this.lblFanionPiochee.setIcon(null);
    }
}