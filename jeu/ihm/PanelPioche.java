package jeu.ihm ; 
import javax.swing.*;
import jeu.metier.Pioche;
import jeu.metier.Fanion;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.AlphaComposite;

import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class PanelPioche extends JPanel implements ActionListener
{
    /* - - - - - - - - - - - - - */
	/* Déclaration des attributs */
	/* - - - - - - - - - - - - - */

    private Map<Integer, Image> imageMap;
    private final int TAILLE_CARTE = 80;
    private final int NB_CARTES    = 6 ;
    private JLabel lblPiocher          ;
    private JPanel panelGauche     ; 
    private Pioche pioche              ;  
    private JPanel panelDroit         ; 
    private JPanel panelInstructions   ; 
    private JLabel lblInstruction      ;
    private JLabel lblPioche           ;
    private JLabel lblFanionPiochee    ;
    private JButton btnRetourAcceuil   ; 
    private FramePrincipale frame      ; 

    /* - - - - - - - - - - */
	/* Constructeur        */
	/* - - - - - - - - - - */

    public PanelPioche(FramePrincipale frame)
    {
        this.frame = frame ;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 150));
        /* - - - - - - - - - - - - - - - */
        /* Initialisation des attributs  */
        /* - - - - - - - - - - - - - - - */	
        this.panelGauche = new JPanel(new GridLayout(2,1));
        this.panelDroit = new JPanel(new GridLayout(1,2)) ; 
        this.pioche = new Pioche(); 

        this.imageMap = new HashMap<>();
        this.lblPiocher = new JLabel("Appuyer sur la pioche pour piocher ") ; 
        this.lblPioche = new JLabel()                         ;
        this.lblFanionPiochee = new JLabel() ; 

        //On met un bouton retour accueil transparent gras 
        this.btnRetourAcceuil = new JButton(" Accueil :");
        this.btnRetourAcceuil.setForeground(Color.WHITE);
        this.btnRetourAcceuil.setFont(this.btnRetourAcceuil.getFont().deriveFont(Font.BOLD, 16f));
        this.btnRetourAcceuil.setBackground(new Color(0, 0, 0, 0));
        this.btnRetourAcceuil.setOpaque(false);
        this.btnRetourAcceuil.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        ///////////////////////////////////////////////////


        this.lblPiocher.setForeground(Color.WHITE);
        this.lblPiocher.setFont(this.lblPiocher.getFont().deriveFont(Font.BOLD, 18f));


        this.panelGauche.setOpaque(false);// on les mets transparent pour voir le fond 
        this.panelDroit.setOpaque(false);

        this.lblPioche.setIcon(new ImageIcon("images/pioche.png"));
        this.pioche.reconstituer(); 


        /* - - - - - - - - - - - - - - -   */
        /*      /Ajout des attributs/      */
        /* - - - - - - - - - - - - - - -  */
        this.panelGauche.add(this.btnRetourAcceuil) ;	
        this.panelGauche.add(this.lblPiocher);
        this.panelDroit.add(this.lblPioche);
        this.panelDroit.add(this.lblFanionPiochee);
       


        this.add(this.panelGauche,BorderLayout.WEST);
        this.add(this.panelDroit,BorderLayout.EAST); 



         /* - - - - - - - - - - - - - - -   */
        /*      /Mise sur ecoute    /      */
        /* - - - - - - - - - - - - - - -  */
        this.btnRetourAcceuil.addActionListener(this);	
        GererSouris gererSouris = new GererSouris();
		this.lblPioche.addMouseListener       ( gererSouris );

        
    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnRetourAcceuil);
        {
           this.frame.switchPanel("accueil");
        }
      
    }


    private class GererSouris extends MouseAdapter
    {
        String fanionPiochee ; 
        int numFanion ; 
        String typeFanion ; 
        Boolean estFoncee  = false ; 
        int cptFonce = 0 ; 

        public void mouseClicked(MouseEvent e)
        {
           if (e.getSource() == PanelPioche.this.lblPioche) 
            {
                if(cptFonce <= 5)
                {
                    if(PanelPioche.this.pioche.estVide() )
                        return ; 

                    this.fanionPiochee = (PanelPioche.this.pioche.piocher()).toString() ;

                    String stringNumFanion = this.fanionPiochee.substring(6);
                    this.numFanion = Integer.parseInt(stringNumFanion);
                    this.typeFanion = this.fanionPiochee.substring(0, 5);

                    System.out.println(this.typeFanion);
                    System.out.print(this.numFanion);

                    if(this.typeFanion .equals("Clair"))
                        this.estFoncee = false ; 
                    if(this.typeFanion.equals("Fonce"))
                        this.estFoncee = false ; 

                    Fanion fanionPiochee  = new Fanion(this.numFanion,this.estFoncee);

                    PanelPioche.this.lblFanionPiochee.setIcon(new ImageIcon("images/Fanion" + "_" + this.typeFanion + "_" + this.numFanion + ".png"));

                    if(this.typeFanion.equals("Fonce"))
                        this.cptFonce ++ ; 
                }
            }
        }
    }



    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image fond = new ImageIcon("images/image_fond_pioche.png").getImage();
        g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);

        // Overlay sombre sur tout le panel
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); 
    }






        
}