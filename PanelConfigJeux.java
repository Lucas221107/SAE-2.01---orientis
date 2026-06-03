import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class PanelConfigJeux extends JPanel implements ActionListener
{
   
    // ===============================
    // DECLARATIONS DES ATTRIBUTS
    // ===============================
    private FrameConfigJeux parent ; 
    private JLabel lblHauteurPlateau ; //nombre case hauteur 
    private JTextField txtHauteurPlateau ; 
    private JLabel lblLargeurPlateau ;  //nombre case largeur
    private JTextField txtLargeurPlateau ;   

    private JLabel lblNbBiome ;   //nombre biome 
    private JTextField txtNbBiome ; 
    private JLabel lblNbBalise ;  // nombre balise 
    private JTextField txtNbBalise ;
    
    private JPanel panelConfiguration ; 

    private JPanel panelValiderAnnulerRegle ;  //panel avec 3 boutons , annuler/valider/voir Accueil
    private JButton btnAnnuler ; 
    private JButton btnValider ; 
    private JButton btnRetourAccueil ; 

    

    public PanelConfigJeux()
    {
        this.setLayout(new BorderLayout());
        // ===============================
        // Création des composants
        // ===============================
        this.lblHauteurPlateau = new JLabel("Hauteur du plateau( en case ) :") ; 
        this.txtHauteurPlateau = new JTextField() ; 
        this.lblLargeurPlateau = new JLabel("Largeur du plateau ( en case ) :") ; 
        this.txtLargeurPlateau = new JTextField() ; 

        this.lblNbBiome = new JLabel("Nombre de biome :") ; 
        this.txtNbBiome = new JTextField() ; 

        this.lblNbBalise = new JLabel("Nombre de balise :") ; 
        this.txtNbBalise = new JTextField() ; 

        this.panelConfiguration = new JPanel(new GridLayout(4,2,5,5)); ;

        this.panelValiderAnnulerRegle = new JPanel() ; //panel avec nos deux bouttons 
        this.btnValider = new JButton("Valider");
        this.btnAnnuler = new JButton("Annuler") ; 
        this.btnRetourAccueil = new JButton("Retour au régles");

        this.panelConfiguration.add(this.lblHauteurPlateau) ;
        this.panelConfiguration.add(this.txtHauteurPlateau);
        this.panelConfiguration.add(this.lblLargeurPlateau) ;
        this.panelConfiguration.add(this.txtLargeurPlateau);
        this.panelConfiguration.add(this.lblNbBiome) ;
        this.panelConfiguration.add(this.txtNbBiome);
        this.panelConfiguration.add(this.lblNbBalise) ;
        this.panelConfiguration.add(this.txtNbBalise);

        this.panelValiderAnnulerRegle.add(this.btnValider); 
        this.panelValiderAnnulerRegle.add(this.btnAnnuler);
        this.panelValiderAnnulerRegle.add(this.btnRetourAccueil); 


        //on ajoute nos deux panel au panel principal 
        this.add(this.panelConfiguration, BorderLayout.CENTER);
        this.add(this.panelValiderAnnulerRegle, BorderLayout.SOUTH);

        this.btnAnnuler.addActionListener(this);
        this.btnValider.addActionListener(this);
        this.btnRetourAccueil.addActionListener(this);

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
               
                System.out.println("Tout est carré ");
                
            }

            if(e.getSource() == this.btnAnnuler) 
            {
                this.supprimerSaisie();
            }

            if(e.getSource() == this.btnRetourAccueil)
            {
                System.out.println("Retour au régles");
                
                new FrameAccueil() ;//ouverture de la frame de l'acceuil   
                
                JFrame frame = ( JFrame ) SwingUtilities.getWindowAncestor(this);

			    if ( frame != null )
				    frame.dispose();
                

            }
 
        }

        private void supprimerSaisie()
        {
            this.txtHauteurPlateau.setText("");
            this.txtLargeurPlateau.setText("");
            this.txtNbBiome.setText("");
            this.txtNbBalise.setText("");
        }
}