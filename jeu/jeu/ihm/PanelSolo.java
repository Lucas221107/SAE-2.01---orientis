package jeu.ihm;
 
import javax.swing.*;

import jeu.Controleur;

import java.awt.event.*     ;
import java.awt.BorderLayout;
import java.awt.Graphics    ;
import java.awt.GridLayout  ;
import java.awt.Image       ;
import java.awt.Font        ;
import java.awt.Color       ;
import java.awt.FlowLayout  ;


public class PanelSolo extends JPanel implements ActionListener
{
    /*- - - - - - - - - - - - -*/
	/* Constantes des couleurs */
	/*- - - - - - - - - - - - -*/
	private final Color VERT_FORET     = new Color( 40,  67,  43);
    private final Color BEIGE_BOUTON   = new Color(230, 218, 196);
    private final Color TEXTE_FONCE    = new Color( 51,  35,  21);

   
    /*- - - - - - - - - - - - -*/
	/* Attributs               */
	/*- - - - - - - - - - - - -*/
    private PanelPioche     panelPioche    ;
    private Controleur      ctrl           ;
    private StyleComposante style          ;
    private FramePrincipale frame          ; 

    private JPanel          panelOption    ;

    private boolean         optionActiver  ;

    private JButton         btnOption      ;
    private JButton         btnQuitter     ;
    private JButton         btnDemo        ;
    private JButton         btnRecommencer ;

    private Image           imgFond        ;

    /*- - - - - - - - - - - - -*/
	/* Constructeur            */
	/*- - - - - - - - - - - - -*/
    public PanelSolo(FramePrincipale frame, StyleComposante style, Controleur ctrl)
    {
        this.setLayout(new BorderLayout());


        /*- - - - - - - - - - - - -*/
	    /* Création des attributs  */
	    /*- - - - - - - - - - - - -*/

        this.ctrl = ctrl   ;
        this.frame = frame ;
        this.style = style ;

         /* Création de font pour les boutons */
        Font fontBouton = new Font("SansSerif", Font.BOLD, 13);
        
        this.imgFond = getToolkit().getImage("../jeu/ihm/images/fond_multi_connexion.png"); // <- image d'arrière plan

        this.optionActiver = false;  // <- desactive l'affichage du menu option

        /*=============================== boutons ===============================*/
        this.btnDemo  = new JButton( "Démo");
        this.style.styleBouton( this.btnDemo, this.VERT_FORET, Color.WHITE, fontBouton);

        this.btnOption = new JButton( "Option");
        this.style.styleBouton( this.btnOption, this.BEIGE_BOUTON, this.TEXTE_FONCE, fontBouton );

        this.btnRecommencer = new JButton("Recommencer la partie");
        this.style.styleBouton( this.btnRecommencer, this.VERT_FORET, Color.WHITE, fontBouton );

        this.btnQuitter     = new JButton("Quitter la partie");
        this.style.styleBouton( this.btnQuitter, this.VERT_FORET, Color.WHITE, fontBouton );

        /*=============================== panel ===============================*/
        JPanel panelHaut = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelHaut.setOpaque( false );

        this.panelPioche  = new PanelPioche(this.frame, this.ctrl, this.style);
        this.panelPioche.setOpaque(false);
        
        this.panelOption = new JPanel( new GridLayout( 9, 1, 3, 3));
        this.panelOption.setOpaque(false);
        this.panelOption.setVisible( this.optionActiver );


        /*- - - - - - - - - - - - - - - -*/
	    /* Positionnement des attributs  */
	    /*- - - - - - - - - - - - - - - -*/

        //panel haut
        panelHaut.add( this.btnOption );
        panelHaut.setOpaque(false);

        //panel Option
        this.panelOption.add ( this.btnDemo        );
        this.panelOption.add ( this.btnRecommencer );
        this.panelOption.add ( this.btnQuitter     );
        this.panelOption.add ( new JLabel() );
        this.panelOption.add ( new JLabel() );
        this.panelOption.add ( new JLabel() );
        this.panelOption.add ( new JLabel() );
        this.panelOption.add ( new JLabel() );

        // panel solo
        this.add( this.panelPioche , BorderLayout.SOUTH);
        this.add( this.panelOption , BorderLayout.WEST );
        this.add(panelHaut        , BorderLayout.NORTH);

        /*- - - - - - - - - - - - - -*/
	    /* Activation des attributs  */
	    /*- - - - - - - - - - - - - -*/
        this.btnOption     .addActionListener(this);
        this.btnQuitter    .addActionListener(this);
        this.btnRecommencer.addActionListener(this);
    }


    /*=============================== Méthodes ===============================*/
    public void actionPerformed ( ActionEvent e )
    {
        if ( e.getSource() == this.btnOption )
        {
            this.optionActiver = ! this.optionActiver;
            this.panelOption.setVisible(optionActiver);

            this.revalidate();
            this.repaint();
        }


        if ( e.getSource() == this.btnQuitter )
        {

        }

        if ( e.getSource() == this.btnRecommencer )
        {

        }
    }


    /* méthode paintComponent permettant de mettre une image de fond*/
    public void paintComponent ( Graphics g )
    {
        super.paintComponent(g);
        g.drawImage(this.imgFond, 0, 0, this.getWidth(), this.getHeight(), this );
    }


}