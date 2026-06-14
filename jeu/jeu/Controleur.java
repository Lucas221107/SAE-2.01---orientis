package jeu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import jeu.ihm.FramePrincipale;
import jeu.metier.*;

public class Controleur
{

	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */
	private FramePrincipale  ihm           ;
	private Plateau          plateau       ;
	private String           cheminPlateau ;
	private Partie           metier        ;
	

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */
	public Controleur()
	{
		this.ihm    = new FramePrincipale ( this );
		this.metier = null;
	}


	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	public String getCheminPlateau ()  { return this.cheminPlateau          ; }  // <- return le chemin absolue pour trouver le fichier du plateau
	public int    getNbLigne       ()  { return this.plateau.getNbLignes  (); }  // <- return le nombre de lignes du plateau 
	public int    getNbColonnes    ()  { return this.plateau.getNbColonnes(); }  // <- return le nombre de colonnes du plateau
	public Partie getPartie        ()  { return this.metier                 ; }  // <- return la partie actuelle


	/* methode qui renvoie la couleur du biome en fonction de la case et de la ligne du plateau */
	public Color getColorBiome ( int lig, int col)
	{
		String nomBiome = this.plateau.getTabStringBiome() [lig][col]; // <- recupere le string du biome dans le plateau actuelle
		TypeBiome biome = TypeBiome.toNom(nomBiome);  // <- converti ce nom en biome

		if ( biome == null )
			return Color.WHITE;	// <- couleur par defaut

		return biome.getCouleur();  // <- return la couleur du biome
	}

	/* méthode qui renvoie le nombre de manche d'une partie */
	public int getNbManche()
	{
		if ( this.metier == null )
			return 5; // <- return 5 par defaut
		
		return this.metier.getNbManche();
	}

	/* méthode qui renvoie le numéro d'une balise en fonction de sa ligne et sa colonne dans le plateau */
	public int getNumeroBalise ( int lig, int col )
	{
		Balise b = this.plateau.getBalise(lig, col);

		if ( b != null )
			return b.getNumero();

		return 0;  // <- return 0 par defaut
	}

	/* méthode qui renvoie le score de la manche du joueur courant */
	public int getScoreJoueurCourant()
	{
		if ( this.metier == null )
			return 0; // <- return 0 par defaut

		return this.metier.getJoueurCourant().calculerScoreManche();
	}

	/* méthode qui renvoie la ligne de la balise de départ du joueur */
	public int getLigBaliseDepartJoueur()
	{
		if ( this.metier == null )
			return -1; // <- return -1 par default

		return this.metier.getJoueurCourant().getBaliseDepart().getPosition().getLigne();
	}

	/* méthode qui renvoie la colonne de la balise de depart du joueur */
	public int getColBaliseDepartJoueur()
	{
		if ( this.metier == null )
			return -1; // <- renvoie -1 par default

		return this.metier.getJoueurCourant().getBaliseDepart().getPosition().getColonne();
	}

	/* méthode qui renvoie le numéro de la carte qu'on vien de piocher */
	public int getNumeroFanionCourant()
	{
		if ( this.metier == null || this.metier.getMancheCourante() == null )
			return -1; // <- return -1 si la partie n'existe pas ou que la manche courante n'existe pas

		Fanion fanion = this.metier.getMancheCourante().getFanionCourant();

		if ( fanion == null )
			return - 1; // <- renvoie -1 si la carte pioché n'existe pas

		return fanion.getNumero(); // <- renvoie le numéro du fanion
	}

	/* méthode qui renvoie le type de la carte que l'on vien de pioché ( clair ou foncé ) */
	public String getTypeFanionCourant()
	{
		if ( this.metier == null || this.metier.getMancheCourante() == null )
			return ""; // <- renvoie une chaine vide si la partie n'existe pas ou si la manche courante n'existe pas

		Fanion fanion = this.metier.getMancheCourante().getFanionCourant();

		if ( fanion == null )
			return ""; // <- return une chaine vide si la carte que l'on vien de piocher est null

		if ( fanion.estFonce())
			return "fonce"; // <- return le type "foncé" si le booleen est true
		else
			return "clair";
	}

	/* méthode qui renvoie le numéro de la manche courante */
	public int getNumeroManche()
	{
		if ( this.metier == null )
			return -1;  // <- return -1 si la partie n'existe pas

		return this.metier.getNumeroManche();
	}

	/* méthode qui revoie la liste de coordonnées ( lig et col ) de toutes les balises voisines
	 * de la balise se situant sur la case lig et col mis en paramètre */
	public List<int[]> getVoisins (int lig, int col)
	{
		List<int[]> lstCoordVoisin = new ArrayList<>();

		if ( this.plateau == null )
			return lstCoordVoisin; // <- return une liste vide si la partie n'existe pas

		Balise b = this.plateau.getBalise(lig, col);

		if ( b == null )
			return lstCoordVoisin;     // <- renvoie une liste vide si il y a pas de balise sur la case lig et col

		for ( Balise baliseVoisine : b.getVoisins())
		{
			/* ajoute les coordonnées ( lig et col ) des balises voisines dans la list */
			lstCoordVoisin.add( new int[] { baliseVoisine.getPosition().getLigne(), baliseVoisine.getPosition().getColonne() });
		}

		return lstCoordVoisin;
	}

	/* méthode qui renvoie la list de coordonnées des balises disponible a la capture */
	public List<int[]> getVoisinDisponible ( int lig, int col)
	{
		List<int[]> lstVoisinDispo = new ArrayList<>();

		if ( this.plateau == null || this.metier == null )
			return lstVoisinDispo;  // <- renvoie une liste vide si la partie ou la manche n'existe pas

		Fanion fanion = this.metier.getMancheCourante().getFanionCourant();

		if ( fanion == null )
			return lstVoisinDispo; // <- renvoie une liste vide si il n'y a pas de carte tirée

		Balise balise = this.plateau.getBalise(lig, col);

		if ( balise == null )
			return lstVoisinDispo; // <- renvoie une liste vide si la balise lig et col n'existe pas

		if ( !this.metier.getJoueurCourant().estExtremiteJouable(balise))
			return lstVoisinDispo; // <- renvoie une liste vide si la balise choisie se situe pas sur une extremité de son chemin

		for ( Balise baliseVoisine : balise.getVoisins())
		{
			if ( ! this.metier.getJoueurCourant().aVisite( baliseVoisine )        &&
			     fanion.correspond( baliseVoisine.getNumero())                    &&
				 ! this.metier.getJoueurCourant().segmentSeCroise ( balise, baliseVoisine ))
			{
				/* remplie la list avec les coordonées d'une balise qui peut etre capturer si cette derniere n'a pas été visité pendant la manche,
				 * si la valeur de la carte tirée est égale au numéro de la balise de destination et si les segments ne se croisent pas */
				
				lstVoisinDispo.add ( new int[] { baliseVoisine.getPosition().getLigne(), baliseVoisine.getPosition().getColonne()});
			}	
		}

		return lstVoisinDispo;
	}



	/* - - - - - - - - - - - - - */
	/* Modificateurs             */
	/* - - - - - - - - - - - - - */

	public void setCheminPlateau ( String chemin )
	{
		this.cheminPlateau = chemin;  // <- instancie un chemin absolue de fichier de plateau.data dans la variable
		this.ihm.getPanelChargement().chargerPlateau(); // <- charge une prévisualisation du plateau dans le panelChargement
	}

	public Plateau chargerPlateau ( String chemin)
	{
		this.plateau = GestionnairePlateau.charger(chemin);  // <- instancie la variable plateau avec le plateau charger dans le panelChargement
		return this.plateau;
	}


	/* - - - - - - - - - - - - - */
	/* Méthodes                  */
	/* - - - - - - - - - - - - - */

	/* méthode qui efface la partie et la le chemin absolue du plateau précedant */
	public void resetPlateau()
	{
		this.cheminPlateau = null ;
		this.metier        = null ;
		this.plateau       = null ;
	}

	/* méthode qui indique si la balise est une balise de départ ou non */
	public boolean estBaliseDepart( int lig, int col )
	{
		return this.plateau.getBalise(lig, col) instanceof BaliseDepart;
	}

	/* méthode qui indique si il y a une balise sur la lig et la col du plateau */
	public boolean aUneBalise (int lig, int col)
	{
		return this.plateau.getBalise(lig, col) != null;
	}

	/* méthode qui créer une nouvelle partie */
	public void creerPartie ( String nomJoueur)
	{
		List<BaliseDepart> lstDeparts = this.plateau.getBalisesDepart(); // <- liste contenant toutes les balises de départs du plateau

		if ( lstDeparts == null || lstDeparts.isEmpty() )
			return; // <- abandon de la méthode si la liste est null ou si elle est vide

		List<Joueur> lstJoueurs = new ArrayList<>(); // <- liste contenant l'ensemble des noms des joueurs de la partie
		lstJoueurs.add ( new Joueur ( nomJoueur, lstDeparts.get(0).getCouleur(), lstDeparts.get(0)));

		this.metier = new Partie(lstJoueurs, this.plateau); // <- création de la nouvelle partie
		this.metier.demarrer(); // <- on démarre la nouvelle partie
	}


	/* méthode qui termine la manche courante */
	public void terminerManche()  
	{ 
		if ( this.metier == null )
			return;

		this.metier.terminerManche() ; // <- on termine la manche

		if ( ! this.metier.estTerminee()) // <- si la partie n'est pas terminé
		{
			List<BaliseDepart> lstBaliseDeparts = this.plateau.getBalisesDepart(); // créer une liste contenant toutes les balises de départ
			int                index            = ( this.metier.getNumeroManche() - 1 ); // indice de la prochaine balise de départ

			if ( index < lstBaliseDeparts.size() )
				this.metier.getJoueurCourant().setBaliseDepart(lstBaliseDeparts.get(index)); // positionne le joueur sur sa nouvelle balise de départ car c'est une nouvelle manche 
		}
	}

	/* méthode qui indique si la manche est terminé ou non */
	public boolean estMancheTermine()
	{
		if ( this.metier == null || this.metier.getMancheCourante() == null )
			return false;

		return this.metier.getMancheCourante().estTerminee();
	}

	/* méthode permettant de passer son tour si le joueur n'a pas la possibilité de jouer */
	public void passerSonTour()
	{
		if ( this.metier == null || this.plateau == null )
			return;

		this.metier.getJoueurCourant().setAJoue();
	}

	/* méthode permettant de capturer une balise */
	public void capturerBalise ( int ligDepart, int colDepart, int ligArrive, int colArrive )
	{
		if ( this.metier == null )
			return;

		Balise baliseDepart = this.plateau.getBalise(ligDepart, colDepart);
		Balise baliseArrive = this.plateau.getBalise(ligArrive, colArrive);

		if ( baliseDepart == null || baliseArrive == null )
			return;

		this.metier.getJoueurCourant().capturer(baliseDepart, baliseArrive); // capture de la balise 
		this.metier.getJoueurCourant().setAJoue(); // indique au metier que le joueur a jouer et donc ne peux plus capturer de balise tant qu'il n'a pas pioché
		this.metier.getMancheCourante().reinitialiserFanionCourant(); // reinitialise le fanion courant pour pas que le joueur puisse joué 2 fois sur le même fanion
	}


	/* méthode permettant au joueur de piocher un fanion */
	public void piocher()
	{
		if ( this.metier == null || this.metier.getMancheCourante() == null)
			return;

		if (! this.metier.getJoueurCourant().peutPiocher())
			return; // on verifie si le joueur a fait une action avant de pioché

		this.metier.getMancheCourante().piocherFanion(); // <- pioche une nouvelle carte fanion
		this.metier.getJoueurCourant ().setAPioche   (); // <- indique au metier que le joueur vien de piocher et donc par conséquant doit faire une action
	}

	/* méthode qui indique si le joueur peut piocher ou si il doit faire une action avant */
	public boolean peutPiocher()
	{
		if ( this.metier == null || this.plateau == null )
			return false;

		return this.metier.getJoueurCourant().peutPiocher();
	}

	/*méthode qui retourne la couleur de la balise en String */
	public String getCouleurBaliseDepart( int lig, int col )
	{
		Balise balise = this.plateau.getBalise(lig, col);

		if ( balise instanceof BaliseDepart )
			return ( (BaliseDepart) balise ).getCouleur().toLowerCase();

		return "";
	}

	

	/* - - - - - - - - - - - - - */
	/* lancement du Controleur   */
	/* - - - - - - - - - - - - - */
	public static void main (String[] args)
	{
		new Controleur();
	}
}