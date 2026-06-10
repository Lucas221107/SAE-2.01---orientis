package jeu.metier;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Scanner;

/**
 * Gère la persistance d'un {@link Plateau} dans un fichier {@code .data}.
 * Cette classe isole la logique de lecture/écriture du format de fichier afin
 * que {@link Plateau} reste centré sur l'état et la logique de jeu (séparation
 * des responsabilités, respect du MVC : c'est le contrôleur qui décide quand
 * sauvegarder/charger et avec quel chemin).
 *
 * <p>Le fichier est structuré en quatre blocs :</p>
 * <ul>
 *   <li>{@code GRILLE} – dimensions et compteurs</li>
 *   <li>{@code BIOMES} – biome de chaque case</li>
 *   <li>{@code BALISES} – position, numéro, biome et type de chaque balise</li>
 *   <li>{@code LIAISONS} – liste des voisins de chaque balise</li>
 * </ul>
 *
 * @author Groupe 2
 * @version 1.0
 */
public class GestionnairePlateau
{
	/* - - - - - - - - - - - - - */
	/* Constructeur              */
	/* - - - - - - - - - - - - - */

	/** Classe utilitaire : aucune instance ne doit être créée. */
	private GestionnairePlateau() { }

	/* - - - - - - - - - - - - - */
	/* Sauvegarde                */
	/* - - - - - - - - - - - - - */

	/**
	 * Sauvegarde un plateau dans un fichier {@code .data}.
	 *
	 * @param plateau    plateau à sauvegarder
	 * @param nomFichier chemin du fichier de destination
	 * @return {@code true} si l'écriture a réussi, {@code false} en cas d'erreur d'E/S
	 */
	public static boolean sauvegarder(Plateau plateau, String nomFichier)
	{
		try
		{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(nomFichier), "UTF8"));

			int nbLignes   = plateau.getNbLignes()  ;
			int nbColonnes = plateau.getNbColonnes();

			/* En-tête : dimensions et nombres de la grille */
			pw.println("GRILLE " + nbLignes + " " + nbColonnes + " " + plateau.getNbBiomes() + " " + plateau.getNbBalises());

			ecrireBiomes  (pw, plateau, nbLignes, nbColonnes);
			ecrireBalises (pw, plateau, nbLignes, nbColonnes);
			ecrireLiaisons(pw, plateau, nbLignes, nbColonnes);

			pw.close();
			return true;
		}
		catch (IOException e)
		{
			System.err.println("Erreur lors de la sauvegarde du plateau : " + e.getMessage());
			return false;
		}
	}

	/**
	 * Écrit le bloc {@code BIOMES} : le biome de chaque case (même sans balise).
	 */
	private static void ecrireBiomes(PrintWriter pw, Plateau plateau, int nbLignes, int nbColonnes)
	{
		pw.println("BIOMES");

		String[][] tabBiome = plateau.getTabStringBiome();
		if (tabBiome == null)
			return;

		for (int lig = 0; lig < nbLignes; lig++)
		{
			for (int col = 0; col < nbColonnes; col++)
			{
				String nomBiome = tabBiome[lig][col];
				if (nomBiome != null)
				{
					TypeBiome biome = TypeBiome.toNom(nomBiome);
					if (biome != null)
						pw.println(lig + " " + col + " " + biome.name());
				}
			}
		}
	}

	/**
	 * Écrit le bloc {@code BALISES} : ligne colonne numero biome type [couleur si départ].
	 */
	private static void ecrireBalises(PrintWriter pw, Plateau plateau, int nbLignes, int nbColonnes)
	{
		pw.println("BALISES");

		for (int lig = 0; lig < nbLignes; lig++)
		{
			for (int col = 0; col < nbColonnes; col++)
			{
				Balise b = plateau.getBalise(lig, col);
				if (b != null)
				{
					String ligneFichier = lig + " " + col + " " + b.getNumero() + " " + b.getBiome().name();

					if (b instanceof BaliseDepart)
						ligneFichier += " DEPART " + ((BaliseDepart) b).getCouleur();
					else
						ligneFichier += " NORMALE";

					pw.println(ligneFichier);
				}
			}
		}
	}

	/**
	 * Écrit le bloc {@code LIAISONS} : ligne,colonne : voisin1 voisin2 ...
	 */
	private static void ecrireLiaisons(PrintWriter pw, Plateau plateau, int nbLignes, int nbColonnes)
	{
		pw.println("LIAISONS");

		for (int lig = 0; lig < nbLignes; lig++)
		{
			for (int col = 0; col < nbColonnes; col++)
			{
				Balise b = plateau.getBalise(lig, col);
				if (b != null)
				{
					String ligneFichier = lig + "," + col + " :";
					for (Balise voisin : b.getVoisins())
						ligneFichier += " " + voisin.getPosition().getLigne() + "," + voisin.getPosition().getColonne();

					pw.println(ligneFichier);
				}
			}
		}
	}

	/* - - - - - - - - - - - - - */
	/* Chargement                */
	/* - - - - - - - - - - - - - */

	/**
	 * Charge un plateau depuis un fichier {@code .data} précédemment sauvegardé.
	 * Les balises et leurs liaisons sont reconstruites directement à partir du
	 * fichier (sans régénérer le plateau).
	 *
	 * @param nomFichier chemin du fichier à lire
	 * @return le {@link Plateau} reconstruit, ou {@code null} en cas d'erreur d'E/S
	 */
	public static Plateau charger(String nomFichier)
	{
		Plateau plateau = null;

		try
		{
			Scanner scanner = new Scanner(new FileInputStream(nomFichier), "UTF8");

			String section = "";

			while (scanner.hasNextLine())
			{
				String ligne = scanner.nextLine().trim();
				if (ligne.isEmpty())
					continue;

				if (ligne.startsWith("GRILLE"))
				{
					plateau = creerPlateauDepuisEntete(ligne);
					continue;
				}

				if (ligne.equals("BIOMES") || ligne.equals("BALISES") || ligne.equals("LIAISONS"))
				{
					section = ligne;
					continue;
				}

				/* Les lignes de données sont traitées selon le bloc courant. */
				if (plateau != null)
					lireLigneDonnees(plateau, section, ligne);
			}

			scanner.close();
		}
		catch (IOException e)
		{
			System.err.println("Erreur lors du chargement du plateau : " + e.getMessage());
			return null;
		}

		return plateau;
	}

	/**
	 * Aiguille une ligne de données vers le traitement du bloc courant.
	 */
	private static void lireLigneDonnees(Plateau plateau, String section, String ligne)
	{
		if (section.equals("BIOMES"))   lireBiome  (plateau, ligne);
		if (section.equals("BALISES"))  lireBalise (plateau, ligne);
		if (section.equals("LIAISONS")) lireLiaison(plateau, ligne);
	}

	/**
	 * Crée un plateau vide à partir de la ligne d'en-tête
	 * {@code "GRILLE nbLignes nbColonnes nbBiome nbBalise"}.
	 */
	private static Plateau creerPlateauDepuisEntete(String ligne)
	{
		Scanner scanner = new Scanner(ligne);
		scanner.next(); // ignore le mot-cle "GRILLE"

		int nbLignes   = scanner.nextInt();
		int nbColonnes = scanner.nextInt();
		int nbBiome    = scanner.nextInt();
		int nbBalise   = scanner.nextInt();

		scanner.close();

		Plateau plateau = new Plateau(nbLignes, nbColonnes, nbBiome, nbBalise);

		/* Tableau des biomes preparé pour permettre une future re-sauvegarde fidèle. */
		plateau.setTabStringBiome(new String[nbLignes][nbColonnes]);

		return plateau;
	}

	/**
	 * Lit une ligne du bloc {@code BIOMES} : {@code "lig col NOM_ENUM"}.
	 */
	private static void lireBiome(Plateau plateau, String ligne)
	{
		Scanner scanner = new Scanner(ligne);

		int       lig   = scanner.nextInt();
		int       col   = scanner.nextInt();
		TypeBiome biome = TypeBiome.valueOf(scanner.next());

		scanner.close();

		/* On stocke le nom d'affichage : coherent avec TypeBiome.toNom utilise a la sauvegarde. */
		plateau.getTabStringBiome()[lig][col] = biome.getNom();
	}

	/**
	 * Lit une ligne du bloc {@code BALISES} :
	 * {@code "lig col numero NOM_ENUM TYPE [couleur]"}.
	 */
	private static void lireBalise(Plateau plateau, String ligne)
	{
		Scanner scanner = new Scanner(ligne);

		int       lig    = scanner.nextInt();
		int       col    = scanner.nextInt();
		int       numero = scanner.nextInt();
		TypeBiome biome  = TypeBiome.valueOf(scanner.next());
		String    type   = scanner.next();

		Balise balise;
		if (type.equals("DEPART"))
			balise = new BaliseDepart(numero, biome, new Position(lig, col), scanner.next());
		else
			balise = new Balise(numero, biome, new Position(lig, col));

		scanner.close();

		plateau.setBalise(lig, col, balise);
	}

	/**
	 * Lit une ligne du bloc {@code LIAISONS} : {@code "lig,col : l1,c1 l2,c2 ..."}.
	 */
	private static void lireLiaison(Plateau plateau, String ligne)
	{
		String[] parties = ligne.split(":");

		String[] origine  = parties[0].trim().split(",");
		Balise   courante = plateau.getBalise(Integer.parseInt(origine[0]), Integer.parseInt(origine[1]));

		if (courante == null || parties.length < 2)
			return;

		for (String voisinTexte : parties[1].trim().split("\\s+"))
		{
			if (voisinTexte.isEmpty())
				continue;

			String[] coord  = voisinTexte.split(",");
			Balise   voisin = plateau.getBalise(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));

			if (voisin != null)
				courante.ajouterVoisin(voisin);
		}
	}
}
