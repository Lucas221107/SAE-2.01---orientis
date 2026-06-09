import javax.swing.*;

import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanelMulti extends JPanel implements ActionListener
{
	/*- - - - - - - - - - - - -*/
	/* Constantes des couleurs */
	/*- - - - - - - - - - - - -*/
	private final Color VERT_FORET     = new Color( 40,  67,  43);
    private final Color ORANGE_BALISE  = new Color(242, 100,  25);
    private final Color BEIGE_CARTE    = new Color(245, 238, 220);
    private final Color BEIGE_BOUTON   = new Color(230, 218, 196);
    private final Color TEXTE_FONCE    = new Color( 51,  35,  21);
	
	/*- - - - - - - - - - - - -*/
	/*        Attributs        */
	/*- - - - - - - - - - - - -*/
	private FramePrincipale frame;
	private StyleComposante style;

	private JButton         btnHote             ;
	private JButton         btnRejoindre        ;
	private JButton         btnEffacer          ;
	private JButton         btnRejoindreServeur ;
	private JButton         btnRetour           ; 

	private JTextField      txtAdresseIP        ;
	private JTextField      txtPort             ; 

	private Image           imgFond             ;

	private JPanel          panelCache          ;


	/*- - - - - - - - - - - - -*/
	/*      Constructeur       */
	/*- - - - - - - - - - - - -*/
	public PanelMulti ( FramePrincipale frame, StyleComposante style)
	{
		 
		this.frame = frame;
		this.style = style;

		this.setLayout( new BorderLayout());
		this.setBackground(BEIGE_CARTE);

		
		/* Création de font pour les labels ainsi que les boutons */
		Font fontLabel  = new Font("SansSerif", Font.BOLD, 14);
        Font fontBouton = new Font("SansSerif", Font.BOLD, 13);


		/*- - - - - - - - - - - - - */
		/* Création des composantes */
		/*- - - - - - - - - - - - - */
		this.imgFond = getToolkit().getImage("./images/fond_multi_connexion.png");



		/*===================================== JButton =====================================*/

		// style Bouton Retour
		this.btnRetour = new JButton("Retour");
		this.style.styleBouton( this.btnRetour, this.BEIGE_BOUTON, this.TEXTE_FONCE, fontBouton );
		
		// style bouton btnRejoindre + btnHote
		this.btnHote = new JButton("Heberger une Partie" );
		this.style.styleBouton( this.btnHote, this.VERT_FORET, Color.WHITE, fontBouton );
		
		this.btnRejoindre = new JButton("Rejoindre une Partie");
		this.style.styleBouton( this.btnRejoindre, this.VERT_FORET, Color.WHITE, fontBouton );
		
		// style bouton btnEffacer + btnRejoindreServeur
		this.btnRejoindreServeur = new JButton ("Se Connecter");
		this.style.styleBouton( this.btnRejoindreServeur, this.BEIGE_BOUTON, this.TEXTE_FONCE, fontBouton );
		
		this.btnEffacer    = new JButton("Effacer");
		this.style.styleBouton( this.btnEffacer, this.ORANGE_BALISE, Color.WHITE, fontBouton );



		/*===================================== JTextField =====================================*/

		// style des JTextField
		this.txtAdresseIP = new JTextField(15);
		this.style.styleJTextField( this.txtAdresseIP, Color.WHITE, this.TEXTE_FONCE );

		this.txtPort      = new JTextField(5);
		this.style.styleJTextField( this.txtPort, Color.WHITE, this.TEXTE_FONCE );



		/*===================================== JLabel =====================================*/

		// style des JLabel
		JLabel lblAdresse = new JLabel("Indiquer l'ADRESSE IP de la personne que vous voulez rejoindre : ");
		lblAdresse.setForeground( this.TEXTE_FONCE );
		lblAdresse.setFont(fontLabel);

		JLabel lblPort    = new JLabel("Indiquer le PORT du serveur que vous voulez rejoindre :                 ");
		lblPort.setForeground( this.TEXTE_FONCE );
		lblPort.setFont(fontLabel);

		JLabel lblConnection = new JLabel("Information de Connection");
		lblConnection.setForeground( this.TEXTE_FONCE );
		lblConnection.setFont(fontLabel);



		/*===================================== JPanel =====================================*/
		JPanel panelAdresseIP     = new JPanel( new FlowLayout( FlowLayout.LEFT));
		panelAdresseIP.setOpaque(false);

		JPanel panelLblInfo       = new JPanel( new FlowLayout( FlowLayout.LEFT));
		panelLblInfo.setOpaque(false);

		JPanel panelPort          = new JPanel( new FlowLayout( FlowLayout.LEFT));
		panelPort.setOpaque(false);

		JPanel panelBtn           = new JPanel( new BorderLayout());
		panelBtn.setOpaque(false);

		JPanel panelBtnTopInterne = new JPanel();
		panelBtnTopInterne.setOpaque(false);

		JPanel panelBtnCache      = new JPanel();
		panelBtnCache.setOpaque(false);


		this.panelCache = new JPanel( new GridLayout( 8, 1));
		this.panelCache.setOpaque( false);
		this.panelCache.setVisible(false);


		/*- - - - - - - - - - - - - - - - */
		/* Positionnement des composantes */
		/*- - - - - - - - - - - - - - - - */

		// panel lblInfo
		panelLblInfo.add ( lblConnection );

		//panel Btn top Interne
		panelBtnTopInterne.add ( this.btnHote      );
		panelBtnTopInterne.add ( this.btnRejoindre );

		//panelBtn
		panelBtn.add ( this.btnRetour    , BorderLayout.WEST   );
		panelBtn.add ( panelBtnTopInterne, BorderLayout.CENTER );

		//panel Btn Cache
		panelBtnCache.add( this.btnEffacer         );
		panelBtnCache.add( this.btnRejoindreServeur );

		//panelPort
		panelPort.add( lblPort      );
		panelPort.add( this.txtPort );
		
		//panelAdresseIP
		panelAdresseIP.add( lblAdresse        );
		panelAdresseIP.add( this.txtAdresseIP );

		//panelCache
		this.panelCache.add( new JLabel()   );
		this.panelCache.add( panelLblInfo   );
		this.panelCache.add( new JLabel()   );
		this.panelCache.add( panelPort      );
		this.panelCache.add( panelAdresseIP );
		this.panelCache.add( new JLabel()   );
		this.panelCache.add( new JLabel()   );
		this.panelCache.add( panelBtnCache  );

		//panel this
		this.add( panelBtn       , BorderLayout.NORTH  );
		this.add( this.panelCache, BorderLayout.CENTER );


		/*- - - - - - - - - - - - - - */
		/* Activation des composantes */
		/*- - - - - - - - - - - - - - */
		this.btnEffacer         .addActionListener(this);
		this.btnHote            .addActionListener(this);
		this.btnRejoindre       .addActionListener(this);
		this.btnRejoindreServeur.addActionListener(this);
		this.btnRetour          .addActionListener(this);
	}

    /*===================================== Méthodes  =====================================*/

	public void actionPerformed ( ActionEvent e )
	{
		if (e.getSource() == this.btnRejoindre)
		{
			this.panelCache.setVisible(true);
		}


		if ( e.getSource() == this.btnHote )
		{

		}

		if ( e.getSource() == this.btnEffacer)
		{
			this.txtAdresseIP.setText("");
			this.txtPort     .setText("");
		}

		if ( e.getSource() == this.btnRejoindreServeur )
		{
			
		}

		if ( e.getSource() == this.btnRetour )
		{
			this.frame.switchPanel("accueil");
			this.panelCache.setVisible(false);
		}
	}

	/* méthode paintComponent pour mettre une image en arrière plan */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g.drawImage ( this.imgFond, 0, 0, this.getWidth(), this.getHeight(), this );
	}

}