package fr.icom.info.m1.balleauprisonnier_mvn.Field;

import java.util.ArrayList;
import java.util.Vector;


import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Bot;
import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Rectangle;
import fr.icom.info.m1.balleauprisonnier_mvn.Projectile.Projectile;
import fr.icom.info.m1.balleauprisonnier_mvn.Sprite.spriteExplosion;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;


/**
 * Classe gérant le terrain de jeu.
 */
public class Field extends Canvas{
	/**** DONNÉES MEMBRES ****/
	/* Dimension du Canvas */
	private final int width;
	private final int height;

	/* Gestion des équipes */
	public enum equipes {UNE, DEUX};
	private Vector<Player> equipe1 = new Vector<Player>();
	private Vector<Player> equipe2 = new Vector<Player>();
	
	// Projectiles
	private Vector<Projectile> projectiles = new Vector<Projectile>();
	// Enregistrement et écriture des scores
	private int score1;
	private int score2;
	private Text text;
	
	// Tableau traçant les événements
	private ArrayList<String> input = new ArrayList<String>();

	/* Éléments graphiques */
	private Group root;
	private final GraphicsContext gc;
	/* Et gestion des explosion lorsqu'un Joueur est Touché par un projectile */
	public spriteExplosion spriteDExplosion;
	private boolean presenceExplosion = false;
	private	double xExplosion = 0;
	private double yExplosion = 0;



	/**** MÉTHODES PUBLIQUES ****/
	/**
	 * Constructeur du Canvas dans lequel on va dessiner le jeu.
	 * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
	 * @param w Largeur du canvas
	 * @param h Hauteur du canvas
	 */
	public Field(Scene scene, int w, int h, Group root){
		// Initialisation graphique
		super(w, h); 
		width = w;
		height = h;
		this.root = root;

		// Score
		score1 = 0;
		score2 = 0;
		
		// Permet de capturer le focus et donc les événements clavier et souris.
		this.setFocusTraversable(true);
		gc = this.getGraphicsContext2D();
		
		// On initialise le terrain de jeu
		equipe1.add(new Player(gc, Player.typeJoueur.BLUE,     w/4-24,   h-140, Player.orientation.BAS,     width, 0.5));
		equipe1.add(new Bot   (gc, Player.typeJoueur.SKELETON, w/2-32,   h-140, Player.orientation.BAS,     width, Bot.strategie.OPPOSE_AU_TIR, 0.5));
		equipe1.add(new Bot   (gc, Player.typeJoueur.SKELETON, 3*w/4-36, h-140, Player.orientation.BAS,     width, Bot.strategie.OPPOSE_AU_TIR, 0.5));
		equipe2.add(new Player(gc, Player.typeJoueur.RED,      w/4-26,   20, Player.orientation.HAUT, width, 1));
		equipe2.add(new Bot   (gc, Player.typeJoueur.ORC,      w/2-32,   20, Player.orientation.HAUT, width, Bot.strategie.OPPOSE_AU_TIR, 1));
		equipe2.add(new Bot   (gc, Player.typeJoueur.ORC,      3*w/4-35, 20, Player.orientation.HAUT, width, Bot.strategie.OPPOSE_AU_TIR, 1));

		spriteDExplosion = new spriteExplosion();

		// 𝘌𝘷𝘦𝘯𝘵 𝘓𝘪𝘴𝘵𝘦𝘯𝘦𝘳 du clavier : quand une touche est pressée, on la rajoute a la liste d'input.
		this.setOnKeyPressed(
			new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e){
					String code = e.getCode().toString();
					// Pas d'ajout en double
					if(!input.contains(code)){
						input.add(code);
					}
				}
			}
		);

		// 𝘌𝘷𝘦𝘯𝘵 𝘓𝘪𝘴𝘵𝘦𝘯𝘦𝘳 du clavier : quand une touche est relachée, on l'enleve de la liste d'input.
		this.setOnKeyReleased(
			new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e){
					String code = e.getCode().toString();
					input.remove(code);
				}
			}
		);

		// Boucle principale du jeu : handle() est appelée à chaque rafraîchissement de frame (soit environ 60 fois par seconde).
		new AnimationTimer(){
			public void handle(long currentNanoTime){	 
				controleur();
			}
		}.start(); // On lance la boucle de rafraîchissement.

		// Affichage des joueurs
		for(int i=0; i<getNbrJoueurs(); i++){
			root.getChildren().add(getJoueurs()[i].sprite);
		}
		root.getChildren().add(spriteDExplosion);

		// Affichage du texte
		text = new Text();  
		root.getChildren().add(text);
		 

	}

	/**
	 * @return Tableau des joueurs des 2 équipes.
	 */
	public Player[] getJoueurs(){
		Player[] retour = new Player[equipe1.size()+equipe2.size()];
		Vector<Player> joueurs = new Vector<Player>();
		joueurs.addAll(equipe1);
		joueurs.addAll(equipe2);
		joueurs.copyInto(retour);
		return retour;
	}

	/**
	 * @return le nombre de joueurs
	 */
	public int getNbrJoueurs(){
		return equipe1.size()+equipe2.size();
	}

	/**
	 * Ajoute un nouveau projectile.
	 * @param xInitial
	 * @param yInitial
	 * @param directionInitiale
	 */
	public void addProjectile(Player joueur){
		switch(joueur.getOrientation()){
			case HAUT:
				projectiles.add(new Projectile(gc, joueur.getX()+10, joueur.getY()+10, joueur.getAngle()+90, height-140, Player.orientation.HAUT, 80, width-80));
				break;
			case BAS:
				projectiles.add(new Projectile(gc, joueur.getX()+10, joueur.getY()+20, joueur.getAngle()-90, 20, Player.orientation.BAS, 80, width-80));
				break;
		}
	}


	/**** MÉTHODES PRIVÉES ****/
	// Contrôleur du Canvas.
	private void controleur(){
		// Appels aux contrôleurs des joueurs et des bots
		for(int i=0; i<equipe1.size(); i++){
			switch(equipe1.get(i).strategieEnCours){
				case STATIQUE:
				case HASARD:
					((Bot)equipe1.get(i)).controleur();
					break;
				case OPPOSE_AU_TIR:
					((Bot)equipe1.get(i)).controleur(projectiles);
					break;
				case REJOINDRE_JOUEURS:
					((Bot)equipe1.get(i)).controleur(equipe2, i);
					break;
				case CONTROLLEE:
					if(equipe1.get(i).controleur(input, Field.equipes.UNE)){
						addProjectile(equipe1.get(i));
					}
					break;
			}
		}
		for(int i=0; i<equipe2.size(); i++){
			switch(equipe2.get(i).strategieEnCours){
				case STATIQUE:
				case HASARD:
					((Bot)equipe2.get(i)).controleur();
					break;
				case OPPOSE_AU_TIR:
					((Bot)equipe2.get(i)).controleur(projectiles);
					break;
				case REJOINDRE_JOUEURS:
					((Bot)equipe2.get(i)).controleur(equipe2, i);
					break;
				case CONTROLLEE:
					if(equipe2.get(i).controleur(input, Field.equipes.DEUX)){
						addProjectile(equipe2.get(i));
					}
					break;
			}
		}

		// Gestion des projectiles
		presenceExplosion = false;
		xExplosion = 0;
		yExplosion = 0;
		for(int i=0; i<projectiles.size(); i++){
			// Appels aux contrôlleurs des projectiles
			if(!projectiles.get(i).controlleur()){
				// Suppression d'un projectile si le controlleur le prévoit (sortie de terrain)
				projectiles.get(i).finalize();
				projectiles.remove(i);
				break;
			}
			
			// Tests collisions
			for(int j=0; j<equipe1.size(); j++){
				if(CollisionBalleJoueur(projectiles.get(i), equipe1.get(j))){
					score1+=100;
					presenceExplosion = true;
					xExplosion = equipe1.get(j).getX()-32;
					yExplosion = equipe1.get(j).getY()-32;
					root.getChildren().remove(equipe1.get(j).sprite);
					equipe1.get(j).finalize();
					equipe1.remove(j);
					break;
				}
			}
			for(int j=0; j<equipe2.size(); j++){
				if(CollisionBalleJoueur(projectiles.get(i), equipe2.get(j))){
					score2+=100;
					presenceExplosion = true;
					xExplosion = equipe2.get(j).getX()-32;
					yExplosion = equipe2.get(j).getY()-32;
					root.getChildren().remove(equipe2.get(j).sprite);
					equipe2.get(j).finalize();
					equipe2.remove(j);
					break;
				}
			}
		}

		vueTerrain(presenceExplosion, xExplosion, yExplosion);
		vueScore();
	}

	// Vue du terrain de jeu
	private void vueTerrain(boolean presenceExplosion, double x, double y){
		// On nettoie le canvas à chaque frame
		gc.clearRect(0, 0, getWidth(), getHeight());

		// Appels aux vues des joueurs
		for(int i=0; i<equipe1.size(); i++){
			equipe1.get(i).vue();
		}
		for(int i=0; i<equipe2.size(); i++){
			equipe2.get(i).vue();
		}

		// Appels aux vues des projectiles
		for(int i=0; i<projectiles.size(); i++){
			projectiles.get(i).vue();
		}

		// Gestions des explosions
		if(presenceExplosion){
			spriteDExplosion.setX(x);
			spriteDExplosion.setY(y);
			spriteDExplosion.playShoot();
		}
	}

	// Vue du score du jeu
	private void vueScore(){
		gc.setFill(Color.WHITE);
		gc.setFont(new Font("", 60));
		int testScore = score1;
		int nbrChiffre = 0;
		while(testScore>0){
			testScore/=10;
			nbrChiffre++;
		}
		gc.fillText(Integer.toString(score1), width/2-15*nbrChiffre, (height/2)-45);
		testScore = score2;
		nbrChiffre = 0;
		while(testScore>0){
			testScore/=10;
			nbrChiffre++;
		}
		gc.fillText(Integer.toString(score2), width/2-15*nbrChiffre, (height/2)+65);
	}

	/* Fonctions de détection de collisions (src. : https://zestedesavoir.com/tutoriels/2835/theorie-des-collisions/) */
	private boolean CollisionPointCercle(double x, double y,Projectile C){
		double d2 = (x-C.getX())*(x-C.getX()) + (y-C.getY())*(y-C.getY());
		int rayon = 10;
		if(d2>rayon*rayon){
			return false;
		}else{
			return true;
		}
	}

	private boolean ProjectionSurSegment(double Cx,double Cy,double Ax,double Ay,double Bx,double By){
		double ACx = Cx-Ax;
		double ACy = Cy-Ay; 
		double ABx = Bx-Ax;
		double ABy = By-Ay; 
		double BCx = Cx-Bx;
		double BCy = Cy-By; 
		double s1 = (ACx*ABx) + (ACy*ABy);
		double s2 = (BCx*ABx) + (BCy*ABy); 
		if(s1*s2>0){
			return false;
		}
		return true;
	}

	private boolean CollisionRectangleRectangle(Rectangle box1, Rectangle box2){
		if((box2.getX() >= box1.getX() + box1.getLargeur())      // trop à droite
			|| (box2.getX() + box2.getLargeur() <= box1.getX()) // trop à gauche
			|| (box2.getY() >= box1.getY() + box1.getHauteur()) // trop en bas
			|| (box2.getY() + box2.getHauteur() <= box1.getY())){ // trop en haut
			return false;
		}else{
			return true; 
		}
	}

	private Rectangle getBoxAutourCercle(Projectile balle){
		Rectangle carre = new Rectangle();
		int rayon = 25;
		carre.setX(balle.getX()-rayon);
		carre.setY(balle.getY()-rayon);
		carre.setHauteur(2*rayon);
		carre.setLargeur(2*rayon);
		return carre;
	}

	private boolean collisionPointRectangle(double curseur_x, double curseur_y, Rectangle box){
		if(curseur_x >= box.getX()
			&& curseur_x < box.getX() + box.getLargeur()
			&& curseur_y >= box.getY() 
			&& curseur_y < box.getY() + box.getHauteur()){
			return true;
		}else{
			return false;
		}
	}

	private boolean CollisionBalleJoueur(Projectile projectile, Player joueur){
		if(projectile.getOrientation() == joueur.getOrientation()){
			return false; // Hinibition des contacts au lancer de la balle
		}

		int largeurJoueur=64, hauteurJoueur=64;
		Rectangle boxCercle = getBoxAutourCercle(projectile);  // retourner la bounding box de l'image porteuse, ou calculer la bounding box.
		if(!CollisionRectangleRectangle(joueur, boxCercle)){
			return false;   // premier test
		}
		if(CollisionPointCercle(joueur.getX(),joueur.getY(), projectile)
			|| CollisionPointCercle(joueur.getX(),joueur.getY()+hauteurJoueur, projectile)
			|| CollisionPointCercle(joueur.getX()+largeurJoueur,joueur.getY(), projectile)
			|| CollisionPointCercle(joueur.getX()+largeurJoueur,joueur.getY()+hauteurJoueur, projectile)){
			return true;   // deuxieme test
		}
		if(collisionPointRectangle(projectile.getX(), projectile.getY(), joueur)){
			System.out.println(projectile.getX() + " " + projectile.getY() + " " + joueur.getX() + " " + joueur.getY());
			return true;   // troisieme test
		}
		boolean projvertical = ProjectionSurSegment(projectile.getX(),projectile.getY(),joueur.getX(), joueur.getY(), joueur.getX(), joueur.getY()+hauteurJoueur);
		boolean projhorizontal = ProjectionSurSegment(projectile.getX(),projectile.getY(),joueur.getX(), joueur.getY(), joueur.getX()+largeurJoueur, joueur.getY()); 
		if(projvertical || projhorizontal){
			return true;   // cas E
		}
		return false;  // cas B   
	}
}