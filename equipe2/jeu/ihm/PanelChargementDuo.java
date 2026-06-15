package jeu.ihm;

import jeu.Controleur;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelChargementDuo extends JPanel implements ActionListener
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
    private FramePrincipale frame          ;
    private StyleComposante style          ;
    private Controleur      ctrl           ;

    // Plateaux d'APERÇU uniquement — pas ceux de PanelDuo
    private PanelPlateau    panelApercuJ1  ;
    private PanelPlateau    panelApercuJ2  ;
    private JPanel          panelDeuxApercu;

    private BarJMenuPerso   maBarre        ;

    private JButton         btnCommencer   ;
    private JButton         btnRetour      ;
    private JButton         btnAnnuler     ;

    private Image           imgFond        ;

    private JTextField      txtNomJ1       ;
    private JTextField      txtNomJ2       ;

    /*- - - - - - - - - - - - -*/
    /* Constructeur            */
    /*- - - - - - - - - - - - -*/
    public PanelChargementDuo(FramePrincipale frame, Controleur ctrl, StyleComposante style)
    {
        this.setLayout(new BorderLayout());

        Font fontBouton = new Font("SansSerif", Font.BOLD, 13);

        this.ctrl  = ctrl  ;
        this.frame = frame ;
        this.style = style ;

        this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond_multi_connexion.png");
        this.maBarre = new BarJMenuPerso(this.ctrl);

        // Plateaux d'aperçu indépendants
        this.panelApercuJ1   = new PanelPlateau(this.frame, this.ctrl);
        this.panelApercuJ2   = new PanelPlateau(this.frame, this.ctrl);

        this.panelDeuxApercu = new JPanel(new GridLayout(1, 2));
        this.panelDeuxApercu.setOpaque(false);

        this.panelApercuJ1.setOpaque(false);
        this.panelApercuJ2.setOpaque(false);

        this.panelDeuxApercu.add(this.panelApercuJ1);
        this.panelDeuxApercu.add(this.panelApercuJ2);

        /* Labels et champs texte */
        JLabel lblNomJ1 = new JLabel("Pseudo Joueur 1");
        JLabel lblNomJ2 = new JLabel("Pseudo Joueur 2");
        this.txtNomJ1   = new JTextField(10);
        this.txtNomJ2   = new JTextField(10);

        /*============================ boutons ===========================*/
        this.btnAnnuler   = new JButton("Annuler");
        this.style.styleBouton(this.btnAnnuler, this.VERT_FORET, Color.WHITE, fontBouton);

        this.btnRetour    = new JButton("Retour");
        this.style.styleBouton(this.btnRetour, this.BEIGE_BOUTON, this.TEXTE_FONCE, fontBouton);

        this.btnCommencer = new JButton("Commencer la partie");
        this.style.styleBouton(this.btnCommencer, this.VERT_FORET, Color.WHITE, fontBouton);

        /*============================ panels ===========================*/
        JPanel panelBtn = new JPanel();
        panelBtn.setOpaque(false);

        JPanel panelGauche = new JPanel(new GridLayout(10, 1, 5, 5));
        panelGauche.setOpaque(false);

        /*- - - - - - - - - - - - - */
        /* Ajout des composantes    */
        /*- - - - - - - - - - - - - */
        panelGauche.add(new JLabel()   );
        panelGauche.add(new JLabel()   );
        panelGauche.add(lblNomJ1       );
        panelGauche.add(this.txtNomJ1  );
        panelGauche.add(new JLabel()   );
        panelGauche.add(new JLabel()   );
        panelGauche.add(lblNomJ2       );
        panelGauche.add(this.txtNomJ2  );
        panelGauche.add(new JLabel()   );
        panelGauche.add(new JLabel()   );

        panelBtn.add(this.btnRetour    );
        panelBtn.add(this.btnAnnuler   );
        panelBtn.add(this.btnCommencer );

        this.add(panelBtn             , BorderLayout.SOUTH );
        this.add(this.panelDeuxApercu , BorderLayout.CENTER);
        this.add(panelGauche          , BorderLayout.WEST  );

        /*- - - - - - - - - - - - - - - -*/
        /* Activation des composantes    */
        /*- - - - - - - - - - - - - - - -*/
        this.btnAnnuler  .addActionListener(this);
        this.btnCommencer.addActionListener(this);
        this.btnRetour   .addActionListener(this);
    }

    /*=========================== Méthodes ============================*/
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnRetour)
        {
            this.panelApercuJ1.reset();
            this.panelApercuJ2.reset();
            this.ctrl.resetPlateau();
            this.frame.switchPanel("accueil");
        }

        if (e.getSource() == this.btnCommencer)
        {
            if (this.ctrl.getCheminPlateau() == null)
            {
                JOptionPane.showMessageDialog(this, "Veuillez d'abord sélectionner un fichier");
                return;
            }
            if (this.txtNomJ1.getText().isEmpty())
            {
                JOptionPane.showConfirmDialog(this, "Veuillez saisir le pseudo du Joueur 1");
                return;
            }
            if (this.txtNomJ2.getText().isEmpty())
            {
                JOptionPane.showConfirmDialog(this, "Veuillez saisir le pseudo du Joueur 2");
                return;
            }


            if (e.getSource() == this.btnCommencer)
{

            // Le plateau est déjà chargé via la barre de menu (chargerPlateau())
            // donc this.ctrl.plateau est déjà le bon objet
            this.ctrl.creerPartieDuo(this.txtNomJ1.getText(), this.txtNomJ2.getText());
            this.frame.getPanelDuo().getPanelPiocheDuo().init();
            this.frame.switchPanel("duo");
}
        }

        if (e.getSource() == this.btnAnnuler) 
        { 
            this.panelApercuJ1.reset();
            this.panelApercuJ2.reset();
			this.ctrl.resetPlateau();
			this.txtNomJ1.setText("");
            this.txtNomJ2.setText("");
        }
    }

    /* Charge l'aperçu des deux plateaux (appelé par la barre de menu) */
    public void chargerPlateau()
    {
        System.out.println("chargerPlateau appelé");

        this.panelApercuJ1.charger(this.ctrl.getCheminPlateau());
        this.panelApercuJ2.charger(this.ctrl.getCheminPlateau());
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(this.imgFond, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public BarJMenuPerso getMaBarre() { return this.maBarre; }
}