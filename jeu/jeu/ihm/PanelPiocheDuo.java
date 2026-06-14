package jeu.ihm ; 
import javax.swing.*;
import jeu.metier.Pioche;
import jeu.metier.Fanion;
import jeu.Controleur ; 

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.AlphaComposite;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
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
    private JButton             btnRetourAcceuil ;
    private JButton             btnPasserSonTour ;
    private FramePrincipale     frame         ;
    private Controleur          ctrl          ;
    private Pioche              pioche        ;
    private GererSouris         gererSouris   ;

    private PanelPlateau        panelPlateauJ1 ;
    private PanelPlateau        panelPlateauJ2 ;
    private JLabel              lblPointJ1     ;
    private JLabel              lblPointJ2     ;

    // 1 = tour J1, 2 = tour J2
    private int                 joueurCourant ;
    private int                 nbPointJ1     ;
    private int                 nbPointJ2     ; 
    private boolean aJoueJ1 = false;
    private boolean aJoueJ2 = false;

    /* - - - - - - - - - - */
    /* Constructeur        */
    /* - - - - - - - - - - */
    public PanelPiocheDuo(FramePrincipale frame, Controleur ctrl, PanelPlateau j1, PanelPlateau j2)
    {
        this.ctrl           = ctrl  ;
        this.frame          = frame ;
        this.panelPlateauJ1 = j1    ;
        this.panelPlateauJ2 = j2    ;
        this.joueurCourant  = 1     ; // J1 commence

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 150));

        this.panelGauche      = new JPanel(new GridLayout(3, 2));
        this.panelDroit       = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        this.pioche           = new Pioche();
        this.gererSouris      = new GererSouris();
        this.lblPiocher       = new JLabel("Appuyer sur la pioche pour piocher ");
        this.lblPioche        = new JLabel();
        this.lblFanionPiochee = new JLabel();
        this.lblPointJ1       = new JLabel() ;
        this.lblPointJ2       = new JLabel() ; 


        //on commence la partie dans le init 
        //this.ctrl.creerPartieDuo((this.ctrl.getPartieJ1().getJoueur().getNom()), (this.ctrl.getPartieJ2().getJoueur()).getNom());
        

        this.btnRetourAcceuil = new JButton(" Retour à l'accueil ");
        this.btnRetourAcceuil.setForeground(Color.WHITE);
        this.btnRetourAcceuil.setFont(this.btnRetourAcceuil.getFont().deriveFont(Font.BOLD, 16f));
        this.btnRetourAcceuil.setBackground(new Color(0, 0, 0, 0));
        this.btnRetourAcceuil.setOpaque(false);
        this.btnRetourAcceuil.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        this.btnPasserSonTour = new JButton(" Passer son tour ");
        this.btnPasserSonTour.setForeground(Color.WHITE);
        this.btnPasserSonTour.setFont(this.btnPasserSonTour.getFont().deriveFont(Font.BOLD, 16f));
        this.btnPasserSonTour.setBackground(new Color(0, 0, 0, 0));
        this.btnPasserSonTour.setOpaque(false);
        this.btnPasserSonTour.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        this.lblTourJoueur = new JLabel(" Tour du joueur : ");
        this.lblTourJoueur.setForeground(Color.WHITE);
        this.lblTourJoueur.setFont(this.lblTourJoueur.getFont().deriveFont(Font.BOLD, 16f));
        this.lblTourJoueur.setOpaque(false);

        this.lblPiocher.setForeground(Color.WHITE);
        this.lblPiocher.setFont(this.lblPiocher.getFont().deriveFont(Font.BOLD, 18f));

        this.lblPointJ1.setForeground(Color.WHITE);
        this.lblPointJ1.setFont(this.lblPointJ1.getFont().deriveFont(Font.BOLD, 16f));
        this.lblPointJ2.setForeground(Color.WHITE);
        this.lblPointJ2.setFont(this.lblPointJ2.getFont().deriveFont(Font.BOLD, 16f));

        this.panelGauche.setOpaque(false);
        this.panelDroit.setOpaque(false);

        Image imgPioche = new ImageIcon("../jeu/ihm/images/pioche.png").getImage().getScaledInstance(101, 130, Image.SCALE_SMOOTH);
        this.lblPioche.setIcon(new ImageIcon(imgPioche));

        this.ctrl.reconstituer(this.pioche);

        this.panelGauche.add(this.btnRetourAcceuil);
        this.panelGauche.add(this.lblPiocher);
        this.panelGauche.add(this.btnPasserSonTour);
        this.panelGauche.add(this.lblTourJoueur);
        this.panelGauche.add(this.lblPointJ1) ; 
        this.panelGauche.add(this.lblPointJ2) ; 

        this.panelDroit.add(this.lblPioche);
        this.panelDroit.add(this.lblFanionPiochee);

        this.add(this.panelGauche, BorderLayout.WEST);
        this.add(this.panelDroit,  BorderLayout.CENTER);

        this.btnRetourAcceuil.addActionListener(this);
        this.btnPasserSonTour.addActionListener(this);
        this.lblPioche.addMouseListener(this.gererSouris);

       
        
        this.majLabelTour();
    }

    //methode qu'on va appeler dans le pour commencer une partie et recup les info des joueur creer au plateau chargement
    public void init()
    {
        if (this.ctrl.getPartieJ1() == null || this.ctrl.getPartieJ2() == null)
            return;

        this.ctrl.setPartie(this.ctrl.getPartieJ1());
        this.aJoueJ1 = true;
        this.aJoueJ2 = true;
        this.joueurCourant = 1;
        this.lblPointJ1.setText(this.ctrl.getPartieJ1().getJoueur().getNom() + " : " + this.nbPointJ1);
        this.lblPointJ2.setText(this.ctrl.getPartieJ2().getJoueur().getNom() + " : " + this.nbPointJ2);
        this.majLabelTour();
        this.gererSouris.reset();
        this.lblFanionPiochee.setIcon(null);
        this.panelPlateauJ1.setActif(false);
        this.panelPlateauJ2.setActif(false);
        this.ctrl.reconstituer(this.pioche);
    }
    /* Met à jour le label avec le nom du joueur courant */
    private void majLabelTour()
    {
        if (this.joueurCourant == 1 && this.ctrl.getPartieJ1() != null)
            this.lblTourJoueur.setText(" Tour de : " + this.ctrl.getPartieJ1().getJoueur().getNom());
        else if (this.joueurCourant == 2 && this.ctrl.getPartieJ2() != null)
            this.lblTourJoueur.setText(" Tour de : " + this.ctrl.getPartieJ2().getJoueur().getNom());
    }


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnRetourAcceuil)
        {
            this.frame.switchPanel("accueil");
            this.ctrl.reconstituer(this.pioche);
            this.gererSouris.reset();
            this.lblFanionPiochee.setIcon(null);
            this.joueurCourant = 1;
            this.majLabelTour();
        }

        
        if (e.getSource() == this.btnPasserSonTour)
        {
            if (this.joueurCourant == 1)
            {
                this.aJoueJ1 = true;
                this.panelPlateauJ1.setActif(false);
                this.panelPlateauJ2.setActif(true);
                this.ctrl.setPartie(this.ctrl.getPartieJ2());
                this.joueurCourant = 2;
            }
            else
            {
                this.aJoueJ2 = true;
                this.panelPlateauJ2.setActif(false);
                this.joueurCourant = 1;
                this.ctrl.setPartie(this.ctrl.getPartieJ1()); // ← manquait
            }
                this.majLabelTour();
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
            if (e.getSource() != PanelPiocheDuo.this.lblPioche) return;

            if (!PanelPiocheDuo.this.aJoueJ1 || !PanelPiocheDuo.this.aJoueJ2) return;

            PanelPiocheDuo.this.aJoueJ1 = false;
            PanelPiocheDuo.this.aJoueJ2 = false;

            if (cptFoncee < 6)
            {
                if (PanelPiocheDuo.this.ctrl.estVide(PanelPiocheDuo.this.pioche))
                    return;

                Fanion fanion = PanelPiocheDuo.this.ctrl.piocher(PanelPiocheDuo.this.pioche);
                this.fanionPiochee = fanion.toString();

                String stringNumFanion = this.fanionPiochee.substring(6);
                this.numFanion  = Integer.parseInt(stringNumFanion);
                this.typeFanion = this.fanionPiochee.substring(0, 5);

                if (this.typeFanion.equals("Clair")) this.estFoncee = false;
                if (this.typeFanion.equals("Fonce")) this.estFoncee = true;

                PanelPiocheDuo.this.lblFanionPiochee.setIcon(
                    new ImageIcon("../jeu/ihm/images/Fanion_" + this.typeFanion + "_" + this.numFanion + ".png"));

                // Notifier les deux plateaux du fanion pioché
                PanelPiocheDuo.this.panelPlateauJ1.setFanionCourant(this.numFanion);
                PanelPiocheDuo.this.panelPlateauJ2.setFanionCourant(this.numFanion);

                // Mettre à jour la partie active selon le joueur courant
                // Activer seulement le plateau du joueur courant 
                if (PanelPiocheDuo.this.joueurCourant == 1)
                {
                    PanelPiocheDuo.this.panelPlateauJ1.setActif(true);
                    PanelPiocheDuo.this.panelPlateauJ2.setActif(false);
                }
                else
                {
                    PanelPiocheDuo.this.panelPlateauJ1.setActif(false);
                    PanelPiocheDuo.this.panelPlateauJ2.setActif(true);
                }

                if (this.estFoncee) this.cptFoncee++;
            }
        }

        public void reset()
        {
            this.cptFoncee = 0;
            this.estFoncee = false;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image fond = new ImageIcon("../jeu/ihm/images/image_fond_pioche.png").getImage();
        g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public void joueurAJoue()
    {
        if (this.joueurCourant == 1)
        {
            this.aJoueJ1 = true;
            this.panelPlateauJ1.setActif(false);
            this.panelPlateauJ2.setActif(true);
            this.ctrl.setPartie(this.ctrl.getPartieJ2());
            this.joueurCourant = 2;
        }
        else
        {
            this.aJoueJ2 = true;
            this.panelPlateauJ2.setActif(false);
            this.ctrl.setPartie(this.ctrl.getPartieJ1());
            this.joueurCourant = 1;
        }
        System.out.println("joueurAJoue appelé, joueurCourant = " + this.joueurCourant);
        this.majLabelTour();
    }
}