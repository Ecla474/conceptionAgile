package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Classe gérant un joueur
 */
public class Player{
	// Largeur du terrain :
	private final int largeurTerrain;
	// Position horizontale du joueur :
	private double x;
	// Position verticale du joueur :
	private final double y;
	// Rotation du joueur (devrait toujours être en 0 et 180) :
	private double angle = 90;
	// Pas d'un joueur :
	private double step;
	// Couleur du joueur :
	private String playerColor;
	// Équipes :
	public enum equipes {UNE, DEUX};
	private equipes equipe;
	  
	// On une image globale du joueur 
	Image directionArrow;
	Sprite sprite;
	ImageView PlayerDirectionArrow;
	  
	GraphicsContext graphicsContext;

	/**
	 * Constructeur du Joueur
	 * 
	 * @param gc ContextGraphic dans lequel on va afficher le joueur
	 * @param color couleur du joueur
	 * @param yInit position verticale
	 */
	Player(GraphicsContext gc, String color, int xInit, int yInit, String side, equipes equipeDAppartenance, int largeurPlateau){
		largeurTerrain = largeurPlateau;
		// Tous les joueurs commencent au centre du canvas, 
		x = xInit;
		y = yInit;
		graphicsContext = gc;
		playerColor=color;
		equipe = equipeDAppartenance;
	    
	    angle = 0;

	    // On charge la representation du joueur
        if(side=="top"){
        	directionArrow = new Image("assets/PlayerArrowDown.png");
		}
		else{
			directionArrow = new Image("assets/PlayerArrowUp.png");
		}
        
        PlayerDirectionArrow = new ImageView();
        PlayerDirectionArrow.setImage(directionArrow);
        PlayerDirectionArrow.setFitWidth(10);
        PlayerDirectionArrow.setPreserveRatio(true);
        PlayerDirectionArrow.setSmooth(true);
        PlayerDirectionArrow.setCache(true);

        Image tilesheetImage = new Image("assets/orc.png");
        sprite = new Sprite(tilesheetImage, 0,0, Duration.seconds(.2), side);
        sprite.setX(x);
        sprite.setY(y);
        //directionArrow = sprite.getClip().; (Commenté dans le dépôt initial)

	    // Tous les joueurs ont une vitesse aleatoire entre 0.0 et 1.0
        // Random randomGenerator = new Random();
        // step = randomGenerator.nextFloat();

        // Pour commencer les joueurs ont une vitesse / un pas fixe
        step = 1;
	}

	//  Affichage du joueur
	private void vue(){
		graphicsContext.save(); // saves the current state on stack, including the current transform
		rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
		graphicsContext.drawImage(directionArrow, x, y);
		graphicsContext.restore(); // back to original state (before rotation)
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}
	
	//  Déplacement du joueur vers la gauche, on cantonne le joueur sur le plateau de jeu
	private void moveLeft(){	    
		if(x > 10){
			spriteAnimate();
			x -= step;
		}
	}

	// Déplacement du joueur vers la droite
	private void moveRight(){
		if(x < largeurTerrain-80){
			spriteAnimate();
			x += step;
		}
	}

	// Rotation du joueur vers la gauche
	private void turnLeft(){
		if(angle > 0 && angle < 180){
			angle += 1;
		}else{
			angle += 1;
		}
	}

	// Rotation du joueur vers la droite
	private void turnRight(){
		if(angle > 0 && angle < 180){
			angle -=1;
		}else{
			angle -= 1;
		}
	}

	private void shoot(){
		sprite.playShoot();
	}
	
	// Déplacement en mode boost
	private void boost(){
		x += step*2;
		spriteAnimate();
	}

	private void spriteAnimate(){
		if(!sprite.isRunning){
			sprite.playContinuously();
		}
		sprite.setX(x);
		sprite.setY(y);
	}

	//  Gére les modifications du modèle à partir des infos de la vue.
	public void controlleur(ArrayList<String> input){
		switch(equipe){
			case UNE:
				if(input.contains("Q")){
					this.moveLeft();
				} 
				if(input.contains("D")){
					this.moveRight();
				}
				if(input.contains("Z")){
					this.turnLeft();
				} 
				if(input.contains("S")){
					this.turnRight();
				}
				break;
			case DEUX:
				if(input.contains("LEFT")){
					this.moveLeft();
				} 
				if(input.contains("RIGHT")){
					this.moveRight();
				}
				if(input.contains("UP")){
					this.turnLeft();
				} 
				if(input.contains("DOWN")){
					this.turnRight();
				}
				break;
		}
		if(input.contains("SPACE")){
			this.shoot();
		}
		this.vue();
	}
}
