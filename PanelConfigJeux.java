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

    

    public PanelConfigJeux( FramePrincipale frame)
    {
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int       hauteur     = (int) ( tailleEcran.getHeight() );
        int       largeur     = (int) ( tailleEcran.getWidth() * 0.375 );
       
       
        this.setLayout( new BorderLayout());
        // ===============================
        // Création des composants
        // ===============================

        this.frame = frame;

        JLabel lblConfig         = new JLabel("CONFIGURATION", JLabel.CENTER);
        lblConfig.setPreferredSize( new Dimension( largeur, 45));
        lblConfig.setBorder( BorderFactory.createLineBorder( Color.BLACK));

        JLabel lblAppercu        = new JLabel("APPERCU" , JLabel.CENTER);
        lblAppercu.setPreferredSize( new Dimension(largeur, 45));
        lblAppercu.setBorder( BorderFactory.createLineBorder( Color.BLACK));

        JLabel lblHauteurPlateau = new JLabel("Hauteur du plateau( en case ) : " );
        JLabel lblLargeurPlateau = new JLabel("Largeur du plateau ( en case ) : "); 
        JLabel lblNbBiome        = new JLabel("Nombre de biomes : "              ); 
        JLabel lblNbBalise       = new JLabel("Nombre de balises : "             ); 

        this.txtHauteurPlateau = new JTextField(8); 
        this.txtLargeurPlateau = new JTextField(8); 
        this.txtNbBiome        = new JTextField(8); 
        this.txtNbBalise       = new JTextField(8); 

        //panel partie gauche
        JPanel panelConfiguration   = new JPanel( new BorderLayout());
        panelConfiguration.setBackground   ( new Color(230, 218,216));
        panelConfiguration.setPreferredSize( new Dimension(largeur, hauteur));

        JPanel panelInfoGauche      = new JPanel( new GridLayout( 8, 1));
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

        //panel partieDroite
        JPanel panelAppercu       = new JPanel( new BorderLayout());
        this.panelPlateau         = new PanelPlateau();
    
        
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
        panelInfoGauche.add( panelBtnValide                           );

        // panelConfiguration
        panelConfiguration.add( lblConfig      , BorderLayout.NORTH  );
        panelConfiguration.add( panelInfoGauche, BorderLayout.CENTER );

        // panel appercu
        panelAppercu.add( lblAppercu ,BorderLayout.NORTH );
        panelAppercu.add( panelPlateau                   ); 


        // panel this
        this.add( panelConfiguration ,BorderLayout.WEST  );
        this.add( panelAppercu       ,BorderLayout.CENTER);


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
                if(HauteurPlateau.isEmpty() || LargeurPlateau.isEmpty() || nbBalise.isEmpty() || nbBiome.isEmpty())
                {
                    System.out.println("Veuillez remplir toute les informations : ");
                    return ; 
                }

                if(!(nbBalise.matches("[0-9]+") ))// verification que c'est un int 
                {
                   System.out.println("Veuillez entrez un int pour les balises "); 
                   return ; 
                }

                if(!(nbBiome.matches("[0-9]+")))
                {
                    System.out.println("Veuillez entrez un int pour les biomes");
                    return ; 
                }

                if(!(HauteurPlateau.matches("[0-9]+")))
                {
                    System.out.println("Veuillez entrez un int pour la hauteur du plateau");
                    return ; 
                }
                
                if(!(LargeurPlateau.matches("[0-9]+")))
                {
                    System.out.println("Veuillez entrez un int pour la hauteur du plateau");
                    return ; 
                }
            }

            if(e.getSource() == this.btnAnnuler) 
            {
                this.supprimerSaisie();
            }

            if(e.getSource() == this.btnRetourAccueil)
            {
                this.frame.switchPanel("Accueil");
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

                System.out.println( hauteurPlateau + " " + largeurPlateau);

                panelPlateau.dessinerPlateau( hauteurPlateau, largeurPlateau);


            }
            catch( Exception e ) {}
        }



}