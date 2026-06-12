package sae.ihm;

import sae.Controleur;
import sae.metier.Balise;

import javax.swing.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class PanelPlateau extends JPanel
{
	/* - - - - - - - - - - - - - */
	/* Déclaration des attributs */
	/* - - - - - - - - - - - - - */
	private final int TAILLE_CASE = 50;

	private int                 nbLig              ;
	private int                 nbCol              ;
	private int                 nbBalise           ;
	private int                 cptBalise          ;
	private int                 nbDepartSelectionne;
	private int                 nbZonePlacer       ;

	private Color  [][]         tabColors          ;
	private int    [][]         tabBalisePlateau   ;
	private boolean[][]         tabDepartBalise    ;
	
	private boolean             interactif         ;
	private boolean             enCoursDessin      ;
	private boolean             enCoursGomme       ;
	private boolean             modeGomme          ;
	private boolean             modeSelectionDepart;
	private boolean             afficherLiaison    ;

	private PanelSelectionBiome panelBiome         ;
	private Controleur          ctrl               ;
	private Map<Integer, Image> imageMap           ;

	

	/* - - - - - - - - - - */
	/* Constructeur        */
	/* - - - - - - - - - - */
	public PanelPlateau( Controleur ctrl)
	{		
		this.ctrl          = ctrl;

	/* - - - - - - - - - - - - - - - */
	/* Initialisation des attributs  */
	/* - - - - - - - - - - - - - - - */	
		this.nbLig               = 0     ;
		this.nbCol               = 0     ;
		this.cptBalise           = 0     ;
		this.nbZonePlacer        = 0     ;
		
		this.enCoursDessin       = false ;
		this.enCoursGomme        = false ;
		this.interactif          = false ;
		this.modeGomme           = false ;
		this.modeSelectionDepart = false ;
		this.afficherLiaison     = false ;
		
		// création d'une HashMap
		this.imageMap = new HashMap<>();

		//On ajoute dans la HashMap toutes les 5 images de balise
		for ( int cpt = 1; cpt <= 5; cpt++)
			this.imageMap.put ( cpt, new ImageIcon("../sae/ihm/images/balise_" + cpt + ".png").getImage());

		this.setPreferredSize(new Dimension(this.ctrl.getNbLig() * TAILLE_CASE, this.ctrl.getNbCol() * TAILLE_CASE));

		GererSouris gererSouris = new GererSouris();
		this.addMouseListener       ( gererSouris );
		this.addMouseMotionListener ( gererSouris );
	}

	/* =================================================================== */

	private class GererSouris extends MouseAdapter
	{
		
		// on initialise ces 2 positions à des valeurs impossible
		private int ligBaliseSelectionnee = -1;
    	private int colBaliseSelectionnee = -1;
		

		public void mousePressed ( MouseEvent e)
		{
			int lig = e.getY() / TAILLE_CASE; // <- recupere la ligne du tablau
			int col = e.getX() / TAILLE_CASE; // <- recupere la colonne du tableau

			// si le tableau a 0 ligne et 0 colonne alors on quitte
			if ( PanelPlateau.this.nbLig == 0 || PanelPlateau.this.nbCol == 0)
				return;

			/* - - - - - - - - - - - - - - - - */
			/* Mode selection de balise départ */
			/* - - - - - - - - - - - - - - - - */
			if ( PanelPlateau.this.modeSelectionDepart)
			{
				// on vérifie que les coordonnées sont bien dans le tableau sinon on quitte 
				if (lig < 0 || lig >= PanelPlateau.this.nbLig || col < 0 || col >= PanelPlateau.this.nbCol) 
					return;

				//On vérifie qu'il y est bien une balise dans la case cliqué sinon on quitte
				if (PanelPlateau.this.tabBalisePlateau[lig][col] == 0) 
					return;

				// Si la case est déjà séléctionner on la désélectionne
				if (PanelPlateau.this.tabDepartBalise[lig][col] == true) 
				{ 
					PanelPlateau.this.tabDepartBalise[lig][col] = false; 
					PanelPlateau.this.nbDepartSelectionne--; 
				}
				else if ( PanelPlateau.this.nbDepartSelectionne < 5)  // <- on vérifie que le nombre de balise sélectionner soit bien inférieur à 5
				{ 
					PanelPlateau.this.tabDepartBalise[lig][col] = true;
					PanelPlateau.this.nbDepartSelectionne++; 
				}

				repaint();
				return;
			}


			/* - - - - - - - - - - - - - - - - */
			/* Mode selection de Biome         */
			/* - - - - - - - - - - - - - - - - */
			if ( PanelPlateau.this.interactif)
			{
				if ( PanelPlateau.this.tabColors == null)
					return;

				/* On vérifie si nous sommes en mode Gomme */
				if ( PanelPlateau.this.modeGomme)
				{
					PanelPlateau.this.enCoursGomme = true;

					if ( lig >= 0 && lig < PanelPlateau.this.nbLig && col >= 0 && col < PanelPlateau.this.nbCol)
						PanelPlateau.this.gommerCase(lig, col);

					return;
				}

				PanelPlateau.this.enCoursDessin = true;
				PanelPlateau.this.colorierSiPossible(e.getX(), e.getY());
				return;
			}

			
			/* - - - - - - - - - - - - - - - - */
			/* Mode selection de Balise        */
			/* - - - - - - - - - - - - - - - - */

			if (tabBalisePlateau[lig][col] != 0)
			{
				this.ligBaliseSelectionnee = lig;
				this.colBaliseSelectionnee = col;
			}
			else if (cptBalise < nbBalise)
			{
				PanelPlateau.this.tabBalisePlateau[lig][col] = cptBalise + 1;
				PanelPlateau.this.cptBalise++;
				repaint();
			}
		}


		public void mouseDragged ( MouseEvent e)
		{
			if ( PanelPlateau.this.interactif )
			{
				int lig = e.getY() / TAILLE_CASE;
				int col = e.getX() / TAILLE_CASE;

				if ( PanelPlateau.this.nbLig == 0 || PanelPlateau.this.nbCol == 0)
				return;
				
				if ( PanelPlateau.this.enCoursGomme)
				{
					if ( lig >= 0 && lig < PanelPlateau.this.nbLig && col >= 0 && col < PanelPlateau.this.nbCol)
						PanelPlateau.this.gommerCase( lig, col);
					
					return;
				}

				if ( !PanelPlateau.this.enCoursDessin)
					return;

				PanelPlateau.this.colorierSiPossible(e.getX(), e.getY());
				return;
			}

			if (ligBaliseSelectionnee == -1) return;

   			repaint();	
		}


		public void mouseReleased ( MouseEvent e)
		{
			
			int     lig            = e.getY() / TAILLE_CASE;
       		int     col            = e.getX() / TAILLE_CASE;
			boolean caseDifferente = (lig != ligBaliseSelectionnee || col != colBaliseSelectionnee);

			if (PanelPlateau.this.enCoursGomme)
			{
				PanelPlateau.this.enCoursGomme = false;
				return;
			}

			//  Si nous sommes en mode biome
			if ( PanelPlateau.this.enCoursDessin)
			{
				PanelPlateau.this.enCoursDessin = false;
				PanelPlateau.this.nbZonePlacer ++;
				repaint();
				return;
			}
				
			// Si nous sommes en mode balise
			if ( this.ligBaliseSelectionnee == -1) 
				return;
			
			// On repositionne une balise déjà placé si l'utilisateur veut la changer de place
			if ( caseDifferente && PanelPlateau.this.tabBalisePlateau[lig][col] == 0)
			{
				tabBalisePlateau[lig][col] = tabBalisePlateau[ligBaliseSelectionnee][colBaliseSelectionnee];
				tabBalisePlateau[ligBaliseSelectionnee][colBaliseSelectionnee] = 0;
			}

			ligBaliseSelectionnee = -1;
			colBaliseSelectionnee = -1;
			repaint();
		}
	}

	
	/* ==================================================================== */

	/* - - - - - - - - - */
	/* Accesseurs        */
	/* - - - - - - - - - */
	
	public int         getNbLig              ()                  { return this.nbLig                           ; }
	public int         getNbCol              ()                  { return this.nbCol                           ; }
	public int         getNbBalises          ()                  { return this.nbBalise                        ; }
	public int         getcptBalises         ()                  { return this.cptBalise                       ; }
	public int         getNbDepartSelectionne()                  { return this.nbDepartSelectionne             ; }
	public int    [][] getTabBalise          ()                  { return this.tabBalisePlateau                ; }
	public boolean[][] getTabDepart          ()                  { return this.tabDepartBalise                 ; }
	public boolean     estLibre              (int lig, int col)  { return this.tabBalisePlateau[lig][col] == 0 ; }
	public Color       getCaseColor          (int lig, int col)  { return this.tabColors[lig][col]             ; }

	
	/* - - - - - - - - - */
	/* Modificateurs     */
	/* - - - - - - - - - */

	public void setPanelBiome ( PanelSelectionBiome panelBiome )  { this.panelBiome = panelBiome            ; }
	public void setTabDepart  ( boolean[][]         tab        )  { this.ctrl.setTabDepart(tabDepartBalise) ; }
	public void setModeGomme  ( boolean             b          )  { this.modeGomme  = b                     ; }
	public void setInteractif ( boolean             b          )  { this.interactif = b                     ; }
	public void setNbBalise   ( int                 val        )  { this.nbBalise   = val                   ; }
	
	public void setModeSelectionDepart ( boolean b)
	{
		this.modeSelectionDepart = b;
		this.nbDepartSelectionne = 0;
		this.tabDepartBalise     = new boolean[this.nbLig][this.nbCol];
		repaint();
	}

	public void setAfficheLiaison ( boolean b)
	{
		this.afficherLiaison = b;
		repaint();
	}

	
	/* - - - - - - - - - */
	/* Méthodes          */
	/* - - - - - - - - - */
	
	public void dessinerPlateau ( int lig, int col)
	{
		if (lig != this.nbLig || col != this.nbCol)
		{
			this.nbLig            = lig                              ;
			this.nbCol            = col                              ;
			this.tabColors        = new Color[this.nbLig][this.nbCol];
			for (int l = 0; l < this.nbLig; l++)
				for (int c = 0; c < this.nbCol; c++)
					this.tabColors[l][c] = new Color(34, 139, 34);
			this.tabBalisePlateau = new int  [this.nbLig][this.nbCol];
		}

		repaint();
	}


	public void ajouterBalise (int lig, int col, int numBalise)
	{
		this.tabBalisePlateau[lig][col] = numBalise;
		repaint();
	}

	/* méthode permettant de colorier la case si elle est vide */
	public void colorierSiPossible ( int x, int y)
	{
		int lig = y / TAILLE_CASE; // <- récupere la ligne de la case cliqué
		int col = x / TAILLE_CASE; // <- récupere la colonne de la case cliqué

		if ( col >= 0 && col < this.nbCol && lig >= 0 && lig < this.nbLig)
		{
		
				String biome        = PanelPlateau.this.panelBiome.getNomBiome();
				Color coul          = PanelPlateau.this.ctrl.getColorBiome( biome );
				tabColors[lig][col] = coul;

				repaint();
			
		}
	}

	/* méthode qui permet de gommer la couleur de la case séléctionné*/
	public void gommerCase( int lig, int col)
	{
		this.tabColors[lig][col] = new Color(34, 139, 34);
		repaint();
	}

	/* méthode qui permet de voir qu'elle balise à été sélectionner comme balise de départ */
	
	public void dessinerDepart(int lig, int col, Graphics g)
	{
		int centreX = col * TAILLE_CASE + TAILLE_CASE / 2;
		int centreY = lig * TAILLE_CASE + TAILLE_CASE / 2;
		int rayon   = 15;

		g.setColor(Color.RED);
		g.drawOval(centreX - rayon, centreY - rayon, rayon * 2, rayon * 2);
	}

	/* méthode permettant de déssiner les liaisons entre les balises grâce à une methode de la classe Plateau dans métier */
	private void dessinerLiaison ( Graphics g)
	{
		g.setColor( Color.GRAY);

		for (int lig = 0; lig < this.nbLig; lig++)
		{
			for (int col = 0; col < this.nbCol; col++)
			{
				Balise balise = this.ctrl.getBalise(lig, col);
				
				if (balise == null) 
					continue; // <- si la case est null aors on ne dessine aucune liaison car pas de balise

				int x1 = col * TAILLE_CASE + TAILLE_CASE / 2;
				int y1 = lig * TAILLE_CASE + TAILLE_CASE / 2;

				for (Balise voisin : balise.getVoisins())
				{
					int x2 = voisin.getPosition().getColonne() * TAILLE_CASE + TAILLE_CASE / 2;
					int y2 = voisin.getPosition().getLigne()   * TAILLE_CASE + TAILLE_CASE / 2;
					g.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}


	/* méthode principal permettant de tout dessiner */
	public void paintComponent ( Graphics g)
	{
		super.paintComponent(g);

		if ( this.nbLig == 0 || this.nbCol == 0)
			return;


		for ( int cptLig = 0; cptLig < this.nbLig; cptLig ++)
		{
			for ( int cptCol = 0; cptCol < this.nbCol; cptCol ++)
			{

				int x = cptCol * TAILLE_CASE;
				int y = cptLig * TAILLE_CASE;

				if (tabColors[cptLig][cptCol] != null)
				{
					g.setColor(tabColors[cptLig][cptCol]); // récupere la couleur de tabColor
					g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE); // <- colorie la case
				}

				g.setColor( Color.BLACK);
				g.drawRect( x, y, TAILLE_CASE, TAILLE_CASE); // <- on dessine une case de couleur noir
			}
		}

		// On dessine les liaisons si la variable est à true
		if ( this.afficherLiaison )
			this.dessinerLiaison(g);


		for (int cptLig = 0; cptLig < this.ctrl.getNbLig(); cptLig++)
		{
			for (int cptCol = 0; cptCol < this.ctrl.getNbCol(); cptCol++)
			{
				int x = cptCol * TAILLE_CASE;
				int y = cptLig * TAILLE_CASE;

				if (this.tabBalisePlateau[cptLig][cptCol] != 0)
				{
					// On dessine l'image de la balise, le numéro de l'image est récupérer grâce à la HashMap
					g.drawImage(this.imageMap.get(this.tabBalisePlateau[cptLig][cptCol]),x,y,TAILLE_CASE, TAILLE_CASE,null); //nul car image deja chargée
				}
			}
		}	
	}
}
