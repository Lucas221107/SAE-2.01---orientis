# SAE-2.01---orientis
# SAÉ 2.01, 2.02 et 2.05 — Jeu de plateau Orientis

SAÉ avec du Java organisé en **MVC** : un `Controleur`, une couche
**métier** (logique du jeu) et une couche **ihm** (interface graphique).

## Arborescence du projet

```
equipe2/
├── README.md
├── compile.list                 # Liste des fichiers .java à compiler
│
├── conception/
│   ├── Controleur.java          # Point d'entrée (main) — lance l'application
│   │
│   ├── ihm/                     # Interface graphique (Swing)
│   │   ├── FramePrincipale.java
│   │   ├── PanelConfigJeux.java
│   │   ├── PanelPlateau.java
│   │   ├── PanelSelectionBiome.java
│   │   ├── PanelBalise.java
│   │   ├── PanelChoixBaliseDepart.java
│   │   └── images/              # Ressources graphiques (.png / .jpg)
│   │       ├── arriere_plan.png
│   │       ├── ecran_d_acceuil.png
│   │       ├── page1.jpg … page5.jpg
│   │       ├── balise_1.png … balise_5.png
│   │       └── baliseDepart_1.png … baliseDepart_5.png
│   │
│   └── metier/                  # Logique métier du jeu
│       ├── Balise.java
│       ├── BaliseDepart.java
│       ├── Plateau.java
│       ├── Position.java
│       └── TypeBiome.java
│
└── class/                       # Fichiers compilés (.class) — généré
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
java conception.Controleur
```

### Prérequis

- **JDK** (`javac` et `java` disponibles dans le PATH)
```bash
java -version
javac -version
```
