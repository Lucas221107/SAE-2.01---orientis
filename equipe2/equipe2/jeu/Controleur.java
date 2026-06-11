package jeu;

import jeu.ihm.FramePrincipale;
import jeu.metier.GestionnairePlateau;
import jeu.metier.Partie;
import jeu.metier.Pioche;

public class Controleur
{
	/* - - - - - - - - - - - - - */
	/* Attributs                 */
	/* - - - - - - - - - - - - - */

	private FramePrincipale ihm   ;
	private Partie          partie;

	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	public Controleur()
	{
		this.ihm    = new FramePrincipale( this );
		this.partie = null;
	}

	/* - - - - - - - - - - - - - */
	/* Accesseurs                */
	/* - - - - - - - - - - - - - */

	/** @return la partie en cours, ou null si aucune partie n'est démarrée */
	public Partie getPartie() { return this.partie; }

	/**
	 * Remplace la partie en cours.
	 *
	 * @param partie la nouvelle partie (peut être null pour réinitialiser)
	 */
	public void setPartie( Partie partie ) { this.partie = partie; }

	/* - - - - - - - - - - - - - */
	/* Méthodes                  */
	/* - - - - - - - - - - - - - */

	public boolean demarrerPartie()
	{
		if ( this.partie == null )
			return false;
 
		this.partie.demarrer();
		return true;
	}

	public boolean sauvegarder( String nomFichier )
	{
		if ( this.partie == null )
			return false;

		return GestionnairePlateau.sauvegarder( this.partie.getPlateau(), nomFichier );
	}

	
	public boolean charger( String nomFichier )
	{
		jeu.metier.Plateau plateau = GestionnairePlateau.charger( nomFichier );

		if ( plateau == null )
			return false;

		this.partie = new Partie( new java.util.ArrayList<>(), plateau );
		return true;
	}


	public Fanion piocher(Pioche pioche){return pioche.piocher();}
	public boolean estVide(Pioche pioche){return pioche.estVide();}
	public Pioche reconstituer(Pioche pioche){return pioche.reconstituer();}

	/* - - - - - - - - - - - - - */
	/* Point d'entrée            */
	/* - - - - - - - - - - - - - */

	public static void main( String[] args )
	{
		new Controleur();
	}
}