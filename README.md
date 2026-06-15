# SAÉ 2.01, 2.02 et 2.05 — Jeu de plateau Orientis (partie jeu)

Application Java organisée en **MVC** : un `Controleur`, une couche
**métier** (logique du jeu) et une couche **ihm** (interface graphique Swing).

## Arborescence du projet

```
equipe2/
├── compile.list                 # Liste des fichiers .java à compiler
│
└── jeu/
    ├── README.md
    ├── Controleur.java          # Point d'entrée (main) — lance l'application
    │
    ├── ihm/                     # Interface graphique (Swing)
    │   ├── FramePrincipale.java
    │   ├── StyleComposante.java
    │   ├── BarJMenuPerso.java
    │   ├── PanelAccueil.java
    │   ├── PanelRegle.java
    │   ├── PanelChargementPlateau.java
    │   ├── PanelChargementDuo.java
    │   ├── PanelPlateau.java
    │   ├── PanelPioche.java
    │   ├── PanelPiocheDuo.java
    │   ├── PanelSolo.java
    │   ├── PanelDuo.java
    │   └── images/              # Ressources graphiques (.png)
    │       ├── fond.png
    │       ├── pioche.png
    │       ├── balise_1.png … balise_5.png
    │       ├── balise_depart_rouge_1.png …
    │       ├── Fanion_clair_1.png … Fanion_fonce_5.png
    │       └── etc...
    │
    └── metier/                  # Logique métier du jeu
        ├── Balise.java
        ├── BaliseDepart.java
        ├── Chemin.java
        ├── Direction.java
        ├── Fanion.java
        ├── GestionnairePlateau.java
        ├── Joueur.java
        ├── Manche.java
        ├── Partie.java
        ├── Pioche.java
        ├── Plateau.java
        ├── Position.java
        ├── Segment.java
        └── TypeBiome.java
```

## Récupérer le projet depuis GitHub

```bash
# Cloner le dépôt (branche main)
git clone https://github.com/Lucas221107/SAE-2.01---orientis.git

# Se placer dans le projet
cd SAE-2.01---orientis

# S'assurer d'être sur la branche main
git checkout main

# Récupérer les dernières modifications
git pull origin main
```

## Compiler et exécuter

Depuis le dossier `equipe2/` (celui qui contient `compile.list`) :

```bash
# 1. Compiler tous les fichiers listés dans compile.list vers le dossier class/
javac @compile.list -d class

# 2. Se placer dans le dossier des classes compilées
cd class

# 3. Lancer l'application
java jeu.Controleur
```

### Prérequis

- **JDK** (`javac` et `java` disponibles dans le PATH)
```bash
java -version
javac -version
```

## Générer la Javadoc

Depuis le dossier `equipe2/` :

```bash
# Javadoc publique (méthodes et classes publiques uniquement)
javadoc -encoding UTF-8 -charset UTF-8 -d doc jeu\metier\*.java

# Javadoc complète (inclut les membres privés, protégés et par défaut)
javadoc -private -encoding UTF-8 -charset UTF-8 -d doc jeu\metier\*.java
```

La documentation générée se trouve dans le dossier `equipe2/doc/`. Ouvrir `doc/index.html` dans un navigateur pour la consulter.
