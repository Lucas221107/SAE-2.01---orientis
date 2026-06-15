package jeu.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un joueur (orienteur) d'Orientis. Le joueur trace un chemin à
 * partir de sa balise de départ, capture des balises et cumule un score.
 * <p>
 * Chaque joueur joue sur sa propre carte : l'état « visitée » d'une balise est
 * propre au joueur (ses balises capturées), et non porté par la balise partagée.
 * Cette classe sert aussi bien au mode solo qu'au mode multijoueur.
 *
 * @author Groupe 2
 * @version 1.0
 */
public class Joueur
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	/** Nom du joueur. */
	private String nom;

	/** Couleur attribuée au joueur. */
	private String couleur;

	/** Score total cumulé sur l'ensemble des manches. */
	private int scoreTotal;

	/** Score réalisé à chacune des manches jouées (sert au départage). */
	private ArrayList<Integer> scoresManches;

	/** Balise de départ du joueur. */
	private BaliseDepart baliseDepart;

	/** Chemin tracé par le joueur durant la manche courante. */
	private Chemin chemin;

	/** Balises capturées par le joueur durant la manche courante. */
	private ArrayList<Balise> balisesCapturees;

	private boolean aJoue;

	private Chemin cheminGlobal; 

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/**
	 * Construit un joueur avec son nom, sa couleur et sa balise de départ.
	 * Le score est initialisé à zéro, le chemin est vide et aucune balise
	 * n'est encore capturée.
	 *
	 * @param nom          nom du joueur
	 * @param couleur      couleur du joueur
	 * @param baliseDepart balise de départ du joueur
	 */
	public Joueur(String nom, String couleur, BaliseDepart baliseDepart)
	{
		this.nom              = nom                     ;
		this.couleur          = couleur                 ;
		this.scoreTotal       = 0                       ;
		this.scoresManches    = new ArrayList<Integer>();
		this.baliseDepart     = baliseDepart            ;
		this.chemin           = new Chemin()            ;
		this.balisesCapturees = new ArrayList<Balise>() ;
		this.aJoue            = true                    ;
		this.cheminGlobal     = new Chemin()            ;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return le nom du joueur */
	public String getNom()        { return this.nom       ; }

	/** @return la couleur du joueur */
	public String getCouleur()    { return this.couleur   ; }

	/** @return le score total cumulé sur l'ensemble des manches */
	public int    getScoreTotal() { return this.scoreTotal; }

	/**
	 * Retourne le meilleur score réalisé sur une seule manche.
	 * Sert au départage en cas d'égalité de score total.
	 *
	 * @return le meilleur score d'une manche, ou {@code 0} si aucune manche n'a été jouée
	 */
	public int getMeilleurScoreManche()
	{
		int meilleur = 0;

		for (int score : this.scoresManches)
			if (score > meilleur)
				meilleur = score;

		return meilleur;
	}


	public void setBaliseDepart ( BaliseDepart baliseDepart )
	{
		this.baliseDepart = baliseDepart;
	}




	public boolean peutPiocher()  { return this.aJoue ; }
	public void    setAJoue   ()  { this.aJoue = true ; }
	public void    setAPioche ()  { this.aJoue = false; }
	/* - - - - - - - - - - - - - */
	/* Methodes                  */
	/* - - - - - - - - - - - - - */

	/**
	 * Capture une balise en traçant un segment depuis une balise du chemin.
	 *
	 * @param depart  balise de départ du segment (extrémité du chemin)
	 * @param arrivee balise capturée
	 */
	public void capturer(Balise depart, Balise arrivee)
	{
		/* On trace le segment du depart vers la balise capturee. */
		this.chemin.ajouterSegment(new Segment(depart, arrivee));

		this.cheminGlobal.ajouterSegment(new Segment(depart, arrivee));

		/* La balise capturee est comptabilisee : elle devient "visitee" pour ce joueur. */
		this.balisesCapturees.add(arrivee);
	}

	/**
	 * Indique si le joueur peut jouer avec le fanion donné, c'est-à-dire s'il
	 * existe au moins une balise capturable depuis une extrémité de son chemin.
	 *
	 * @param fanion le fanion pioché
	 * @return {@code true} si le joueur peut capturer une balise valide
	 */
	public boolean peutJouer(Fanion fanion)
	{
		/* On cherche, depuis chaque extremite jouable, une balise voisine capturable. */
		for (Balise depart : this.getDepartsPossibles())
		{
			for (Balise voisin : depart.getVoisins())
			{
				if (this.captureValide(fanion, depart, voisin))
					return true;
			}
		}
		return false;
	}



	public boolean estDejaJouee ( Balise balise )
	{
		return this.aVisite(balise);
	}


	public boolean segmentSeCroise( Balise depart, Balise arrive )
	{
		return this.cheminGlobal.croise( new Segment(depart, arrive));
	}

	/**
	 * Indique si ce joueur a déjà visité (capturé) la balise donnée durant la
	 * manche courante.
	 *
	 * @param balise la balise à tester
	 * @return {@code true} si le joueur a déjà capturé cette balise
	 */
	public boolean aVisite(Balise balise)
	{
		return this.balisesCapturees.contains(balise);
	}

	/**
	 * Retourne les balises depuis lesquelles le joueur peut prolonger son chemin :
	 * les deux extrémités du chemin, ou la balise de départ si le chemin est vide.
	 *
	 * @return la liste des balises de départ possibles pour un déplacement
	 */
	private List<Balise> getDepartsPossibles()
	{
		if (this.chemin.estVide())
		{
			List<Balise> departs = new ArrayList<Balise>();
			departs.add(this.baliseDepart);
			return departs;
		}
		return this.chemin.getExtremites();
	}

	public boolean estExtremiteJouable ( Balise balise)
	{
		// si chemin vide, seule la balise de départ est jouable
		if ( this.chemin.estVide())
			return balise == this.baliseDepart;

		// sinon, la balise doit etre sur une extremité du chemin 
		List<Balise> extremites = this.chemin.getExtremites();
    	return extremites.contains(balise) || balise == this.baliseDepart && extremites.contains(this.baliseDepart);
	}

	public BaliseDepart getBaliseDepart() { return this.baliseDepart ; }

	/**
	 * Indique si capturer la balise destination depuis la balise départ constitue
	 * un coup valide pour le fanion donné. La balise destination doit :
	 * correspondre au fanion (même numéro ou joker), ne pas avoir été visitée
	 * par ce joueur, et le segment tracé ne doit croiser aucun segment déjà présent.
	 *
	 * @param fanion      fanion tiré
	 * @param depart      balise de départ (extrémité du chemin)
	 * @param destination balise que l'on souhaite capturer
	 * @return {@code true} si la capture est valide
	 */
	private boolean captureValide(Fanion fanion, Balise depart, Balise destination)
	{
		return fanion.correspond(destination.getNumero())
		    && !this.aVisite(destination)
		    && !this.chemin.croise(new Segment(depart, destination));
	}

	/**
	 * Calcule le score réalisé durant la manche courante.
	 *
	 * @return le score de la manche
	 */
	public int calculerScoreManche()
	{
		int maxDansUnBiome = 0;
		int nbBiomes       = 0;

		/* Pour chaque biome existant, on compte les balises capturees de ce biome. */
		for (TypeBiome biome : TypeBiome.values())
		{
			int nbBalisesBiome = 0;
			for (Balise balise : this.balisesCapturees)
				if (balise.getBiome() == biome)
					nbBalisesBiome++;

			/* Un biome avec au moins une balise est un biome decouvert. */
			if (nbBalisesBiome > 0)
				nbBiomes++;

			/* On retient le plus grand nombre de balises dans un seul biome. */
			if (nbBalisesBiome > maxDansUnBiome)
				maxDansUnBiome = nbBalisesBiome;
		}

		/* Score = nb max de balises dans un seul biome  x  nb de biomes decouverts. */
		return maxDansUnBiome * (nbBiomes + 1);
	}

	/**
	 * Ajoute le score de la manche courante au score total du joueur et
	 * mémorise ce score dans l'historique des manches.
	 */
	public void ajouterScoreManche()
	{
		int scoreManche = this.calculerScoreManche();

		this.scoresManches.add(scoreManche);
		this.scoreTotal += scoreManche;
	}

	/**
	 * Réinitialise l'état du joueur pour une nouvelle manche :
	 * efface le chemin tracé et vide la liste des balises capturées.
	 * (L'historique des scores et le score total sont conservés.)
	 */
	public void reinitialiserManche()
	{
		this.chemin.effacer();
		this.balisesCapturees.clear();
		this.aJoue = true;
	}
}