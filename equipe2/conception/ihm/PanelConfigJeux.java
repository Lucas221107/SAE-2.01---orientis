package conception.ihm;

import conception.Controleur;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class PanelConfigJeux extends JPanel implements ActionListener, DocumentListener
{
    /* - - - - - - - - - - - - - */
	/* Déclaration des attributs */
	/* - - - - - - - - - - - - - */
    
    private JTextField      txtHauteurPlateau ;
    private JTextField      txtLargeurPlateau ;   

    private JTextField      txtNbBalise       ;
    
    private JButton         btnAnnuler        ; 
    private JButton         btnValider        ; 
    private JButton         btnFacile         ;
    private JButton         btnMoyen          ;
    private JButton         btnDifficile      ;
    private JButton         btnExpert         ; 
    
    private PanelPlateau    panelPlateau      ;
    private FramePrincipale frame             ;
    private Controleur      ctrl              ;
    private PanelBalise     panelBalise       ;

    
    /* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
    public PanelConfigJeux( FramePrincipale frame, PanelPlateau panelPlateau, Controleur ctrl, PanelBalise panelBalise)
    {

        this.setLayout( new BorderLayout());
        this.setBorder( BorderFactory.createLineBorder( Color.BLACK ));
		this.setBackground( new Color (230, 218, 218));
        
        // ===============================
        // Création des composants
        // ===============================

        this.frame        = frame       ;
        this.panelPlateau = panelPlateau;
        this.ctrl         = ctrl        ;
        this.panelBalise  = panelBalise ;

        JLabel lblHauteurPlateau = new JLabel("Nombre de lignes   : "    );
        JLabel lblLargeurPlateau = new JLabel("Nombre de colonnes : "    ); 
        JLabel lblNbBalise       = new JLabel("Nombre de balises  : "    ); 
        JLabel lblDifficute      = new JLabel("Choissiez une difficulté ");
        JLabel lblOption2        = new JLabel("ou choisissez vous même"  );

        this.txtHauteurPlateau = new JTextField(3); 
        this.txtLargeurPlateau = new JTextField(3); 
        this.txtNbBalise       = new JTextField(3); 


        JPanel panelInfoGauche      = new JPanel( new GridLayout( 8, 1));
        panelInfoGauche.setBackground( new Color (230, 218, 218));
        panelInfoGauche.setPreferredSize( this.frame.getDimensionPanel());
        panelInfoGauche.setOpaque( false );

        JPanel panelInfoHauteur     = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelInfoHauteur.setOpaque(false);

        JPanel panelInfoLargeur     = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelInfoLargeur.setOpaque(false);

        JPanel panelInfoBalises     = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelInfoBalises.setOpaque(false);

        JPanel panelBtnDifficulte   = new JPanel();
        panelBtnDifficulte.setOpaque(false);

        JPanel panelLblHaut = new JPanel();
        panelLblHaut.setOpaque(false);

        JPanel panelTxtCentre = new JPanel();
        panelTxtCentre.setOpaque(false);

        JPanel panelBtnValide       = new JPanel();
        panelBtnValide.setOpaque(false);

        JPanel panelVide = new JPanel();
        panelVide.setOpaque(false);

        
        this.btnValider       = new JButton("Suivant");
        this.btnValider.setEnabled(false);

        this.btnAnnuler   = new JButton("Annuler"  ); 
        this.btnFacile    = new JButton("Facile"   );
        this.btnMoyen     = new JButton("Moyen"    );
        this.btnDifficile = new JButton("Difficile");
        this.btnExpert    = new JButton("Expert"   );


        /* ======================================= */
        /*      positionnement des composantes     */
        /* ======================================= */

        // panel Hauteur
        panelInfoHauteur.add( lblHauteurPlateau      );
        panelInfoHauteur.add( this.txtHauteurPlateau );

        // panel Largeur
        panelInfoLargeur.add( lblLargeurPlateau      );
        panelInfoLargeur.add( this.txtLargeurPlateau );


        //panel Balise
        panelInfoBalises.add( lblNbBalise      );
        panelInfoBalises.add( this.txtNbBalise );

        //panelTxtCentre
        panelTxtCentre.add( lblOption2 );

        //panelLblHaut
        panelLblHaut.add( lblDifficute );

        //panel difficulté
        panelBtnDifficulte.add( this.btnFacile    );
        panelBtnDifficulte.add( this.btnMoyen     );
        panelBtnDifficulte.add( this.btnDifficile );
        panelBtnDifficulte.add( this.btnExpert    );

        //panel btn valide
        panelBtnValide.add( this.btnAnnuler       );
        panelBtnValide.add( this.btnValider       );

        //panel info gauche
        panelInfoGauche.add( panelLblHaut         );
        panelInfoGauche.add( panelBtnDifficulte   );     
        panelInfoGauche.add( panelTxtCentre       );
        panelInfoGauche.add( panelInfoHauteur     );
        panelInfoGauche.add( panelInfoLargeur     );
        panelInfoGauche.add( panelInfoBalises     );      
        panelInfoGauche.add( panelVide            );


        // panel this
        this.add( panelInfoGauche ,BorderLayout.CENTER);
        this.add( panelBtnValide  ,BorderLayout.SOUTH);


        /* =================================== */
        /*      Activation des composantes     */
        /* =================================== */

        this.btnFacile       .addActionListener(this);
        this.btnMoyen        .addActionListener(this);
        this.btnDifficile    .addActionListener(this);
        this.btnExpert       .addActionListener(this);
        this.btnAnnuler      .addActionListener(this);
        this.btnValider      .addActionListener(this);

        /* event pour lire en direct ce que l'utilisateur écrit dans les JTextFields */
        this.txtHauteurPlateau.getDocument().addDocumentListener(this);
        this.txtLargeurPlateau.getDocument().addDocumentListener(this);
        this.txtNbBalise      .getDocument().addDocumentListener(this);
    }

    /*===============================================================================*/
    /* - - - - - - - - - - - - - */
	/* Méthodes                  */
	/* - - - - - - - - - - - - - */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.btnAnnuler) 
        {
            this.supprimerSaisie();
            return; // <- évite d'entrer dans le catch et de provoquer un erreur
        }

        // pré-remplissage du plateau
        if ( e.getSource() == this.btnFacile)
        {
            this.txtHauteurPlateau.setText("7" );
            this.txtLargeurPlateau.setText("7" );
            this.txtNbBalise      .setText("14");
        }

        if ( e.getSource() == this.btnMoyen)
        {
            this.txtHauteurPlateau.setText("8" );
            this.txtLargeurPlateau.setText("8" );
            this.txtNbBalise      .setText("20");
        }


        if ( e.getSource() == this.btnDifficile)
        {
            this.txtHauteurPlateau.setText("7" );
            this.txtLargeurPlateau.setText("9" );
            this.txtNbBalise      .setText("22");
        }


        if ( e.getSource() == this.btnExpert)
        {
            this.txtHauteurPlateau.setText("9" );
            this.txtLargeurPlateau.setText("13");
            this.txtNbBalise      .setText("26");
        }

        try
        {
            int hauteurPlateau = Integer.parseInt( this.txtHauteurPlateau.getText() );
            int largeurPlateau = Integer.parseInt( this.txtLargeurPlateau.getText() );
            int nbBalise       = Integer.parseInt( this.txtNbBalise      .getText() );


            if(e.getSource() == this.btnValider)
            {
                this.frame       .switchPanel  ("biomes"   );  // <- passe au panel suivant
                this.frame       .passerEtape  (                );  
                this.ctrl        .setNbLigne   ( hauteurPlateau );
                this.ctrl        .setNbCol     ( largeurPlateau );
                this.panelPlateau.setInteractif(true          ); // <- Permet de pouvoir selectionner les zones pour l'étape d'après
                this.panelBalise .setNbBalise  ( nbBalise       );
            }
        }
        catch(Exception ex) {}        
    }


    public void insertUpdate ( DocumentEvent e ) { this.mettreAJour(); }  // <- S'active quand l'utilisateur écrit un caractère
    public void removeUpdate ( DocumentEvent e ) { this.mettreAJour(); }  // <- S'active quand l'utilisateur efface un caractère
    public void changedUpdate( DocumentEvent e ) { this.mettreAJour(); }  // <- Rare mais obligatoire pour que ca compile
    
    
    /* méthode permettant d'effacer le contenu des JTextFields après avoir appuyer sur le bouton effacer */
    private void supprimerSaisie()
    {
        this.txtHauteurPlateau.setText("");
        this.txtLargeurPlateau.setText("");
        this.txtNbBalise      .setText("");
    }


    private void mettreAJour()
    {
        try
        {
            String hauteurPlateau = this.txtHauteurPlateau.getText().trim();
            String largeurPlateau = this.txtLargeurPlateau.getText().trim();
            String sNbBalise       = this.txtNbBalise     .getText().trim();

            /* vérification obligatoire pour savoir si les JTextFields contiennent uniquement des numéros */
            if ( !hauteurPlateau.matches("[0-9]+") || !largeurPlateau.matches("[0-9]+") || !sNbBalise.matches("[0-9]+"))
            {
                this.btnValider.setEnabled(false);
                return;
            }

            int nbLig    = Integer.parseInt(hauteurPlateau);
            int nbCol    = Integer.parseInt(largeurPlateau);
            int nbBalise = Integer.parseInt(sNbBalise     );

            this.panelPlateau.dessinerPlateau( nbLig, nbCol);
            
            this.btnValider.setEnabled(  nbBalise <= ( nbLig * nbCol) / 2 );
        }
        catch( Exception e ) {}
    }

    protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gp = new GradientPaint(
			0, 0,new Color( 144, 238, 144),              // couleur de départ
			0, getHeight(), Color.WHITE   // couleur d'arrivée
		);

		g2.setPaint(gp);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}


}