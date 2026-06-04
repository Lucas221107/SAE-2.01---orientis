import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class PanelConfigJeux extends JPanel implements ActionListener, DocumentListener
{
   
    // ===============================
    // DECLARATIONS DES ATTRIBUTS
    // ===============================
    
    private JTextField txtHauteurPlateau ;
    private JTextField txtLargeurPlateau ;   

    private JTextField txtNbBiome ; 
    private JTextField txtNbBalise ;
    
    private JButton btnAnnuler ; 
    private JButton btnValider ; 
    private JButton btnRetourAccueil;
    private JButton btnFacile   ;
    private JButton btnMoyen    ;
    private JButton btnDifficile;
    private JButton btnExpert   ; 
    
    private PanelPlateau panelPlateau;

    private FramePrincipale frame;

    

    public PanelConfigJeux( FramePrincipale frame, PanelPlateau panelPlateau)
    {
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int       hauteur     = (int) ( tailleEcran.getHeight() );
        int       largeur     = (int) ( tailleEcran.getWidth() * 0.375 );
       
       
        this.setLayout( new BorderLayout());
        // ===============================
        // Création des composants
        // ===============================

        this.frame        = frame;
        this.panelPlateau = panelPlateau;

        JLabel lblHauteurPlateau = new JLabel("Hauteur du plateau( en case ) : " );
        JLabel lblLargeurPlateau = new JLabel("Largeur du plateau ( en case ) : "); 
        JLabel lblNbBiome        = new JLabel("Nombre de biomes : "              ); 
        JLabel lblNbBalise       = new JLabel("Nombre de balises : "             ); 

        this.txtHauteurPlateau = new JTextField(8); 
        this.txtLargeurPlateau = new JTextField(8); 
        this.txtNbBiome        = new JTextField(8); 
        this.txtNbBalise       = new JTextField(8); 


        JPanel panelInfoGauche      = new JPanel( new GridLayout( 8, 1));
        panelInfoGauche.setBackground( new Color (230, 218, 218));
        panelInfoGauche.setPreferredSize( new Dimension( largeur, hauteur));
        panelInfoGauche.setOpaque( false );

        JPanel panelInfoHauteur     = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelInfoHauteur.setOpaque(false);

        JPanel panelInfoLargeur     = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelInfoLargeur.setOpaque(false);

        JPanel panelInfoBiomes      = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelInfoBiomes.setOpaque(false);

        JPanel panelInfoBalises     = new JPanel( new FlowLayout( FlowLayout.LEFT));
        panelInfoBalises.setOpaque(false);

        JPanel panelBtnDifficulte   = new JPanel();
        panelBtnDifficulte.setOpaque(false);

        JPanel panelBtnValide       = new JPanel();
        panelBtnValide.setOpaque(false);

        
        this.btnValider       = new JButton("Valider");
        this.btnAnnuler       = new JButton("Annuler") ; 
        this.btnRetourAccueil = new JButton("Retour aux régles");
        
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

        //panel Biomes 
        panelInfoBiomes.add( lblNbBiome      );
        panelInfoBiomes.add( this.txtNbBiome );

        //panel Balise
        panelInfoBalises.add( lblNbBalise );
        panelInfoBalises.add( this.txtNbBalise );

        //panel difficulté
        panelBtnDifficulte.add( this.btnFacile    );
        panelBtnDifficulte.add( this.btnMoyen     );
        panelBtnDifficulte.add( this.btnDifficile );
        panelBtnDifficulte.add( this.btnExpert    );

        //panel btn valide
        panelBtnValide.add( this.btnAnnuler       );
        panelBtnValide.add( this.btnValider       );
        panelBtnValide.add( this.btnRetourAccueil );

        //panel info gauche
        panelInfoGauche.add( new JLabel()                             );
        panelInfoGauche.add( panelInfoHauteur                         );
        panelInfoGauche.add( panelInfoLargeur                         );
        panelInfoGauche.add( panelInfoBiomes                          );
        panelInfoGauche.add( panelInfoBalises                         );
        panelInfoGauche.add( new JLabel( "Prédéfinir un tableau"));
        panelInfoGauche.add( panelBtnDifficulte                       );
        panelInfoGauche.add( new JLabel()                             );


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
        this.btnRetourAccueil.addActionListener(this);


        this.txtHauteurPlateau.getDocument().addDocumentListener(this);
        this.txtLargeurPlateau.getDocument().addDocumentListener(this);

    }


        public void actionPerformed(ActionEvent e)
        {
            String HauteurPlateau = this.txtHauteurPlateau.getText()  ;
            String LargeurPlateau = this.txtLargeurPlateau.getText(); 
            String nbBalise = this.txtNbBalise.getText();
            String nbBiome = this.txtNbBiome.getText(); 

            if(e.getSource() == this.btnValider)
            {
                this.frame.switchPanel("biomes");
                this.frame.passerEtape();
            }

            if(e.getSource() == this.btnAnnuler) 
            {
                this.supprimerSaisie();
            }

            if(e.getSource() == this.btnRetourAccueil)
            {
                this.frame.switchPanel("Accueil");
            }

             if ( e.getSource() == this.btnFacile)
            {
                this.txtHauteurPlateau.setText("8" );
                this.txtLargeurPlateau.setText("8" );
                this.txtNbBalise      .setText("42");
                this.txtNbBiome       .setText("12" );
            }

            if ( e.getSource() == this.btnMoyen)
            {
                this.txtHauteurPlateau.setText("7" );
                this.txtLargeurPlateau.setText("7" );
                this.txtNbBalise      .setText("30");
                this.txtNbBiome       .setText("9" );
            }


            if ( e.getSource() == this.btnDifficile)
            {
                this.txtHauteurPlateau.setText("7" );
                this.txtLargeurPlateau.setText("9" );
                this.txtNbBalise      .setText("32");
                this.txtNbBiome       .setText("7" );
            }


            if ( e.getSource() == this.btnExpert)
            {
                this.txtHauteurPlateau.setText("9" );
                this.txtLargeurPlateau.setText("13");
                this.txtNbBalise      .setText("37");
                this.txtNbBiome       .setText("7" );
            }

 
        }


        public void insertUpdate ( DocumentEvent e ) { this.mettreAJour(); }
        public void removeUpdate ( DocumentEvent e ) { this.mettreAJour(); }
        public void changedUpdate( DocumentEvent e ) { this.mettreAJour(); }
        
        
        private void supprimerSaisie()
        {
            this.txtHauteurPlateau.setText("");
            this.txtLargeurPlateau.setText("");
            this.txtNbBiome       .setText("");
            this.txtNbBalise      .setText("");
        }


        private void mettreAJour()
        {
            try
            {
                int hauteurPlateau = 0;
                int largeurPlateau = 0;

                if ( this.txtHauteurPlateau.getText().trim().matches("[0-9]+"))
                    hauteurPlateau = Integer.parseInt( this.txtHauteurPlateau.getText().trim());

                if ( this.txtLargeurPlateau.getText().trim().matches("[0-9]+"))
                    largeurPlateau = Integer.parseInt( this.txtLargeurPlateau.getText().trim());


                this.panelPlateau.dessinerPlateau( hauteurPlateau, largeurPlateau);


            }
            catch( Exception e ) {}
        }



}