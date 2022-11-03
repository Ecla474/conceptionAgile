package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.animation.AnimationTimer;

/**
 * 
 * Classe gerant un joueur
 *
 */
public class Player{
	  double x;       // position horizontale du joueur
	  final double y; 	  // position verticale du joueur
	  double angle = 90; // rotation du joueur, devrait toujour être en 0 et 180
	  double step;    // pas d'un joueur
	  String playerColor;
	  boolean haut; // !!!!!
	  
	  // On une image globale du joueur 
	  Image directionArrow;
	  Sprite sprite;
	  ImageView PlayerDirectionArrow;
	  
	  GraphicsContext graphicsContext;
	  ArrayList<String> input = new ArrayList<String>();
	  /**
	   * Constructeur du Joueur
	   * 
	   * @param gc ContextGraphic dans lequel on va afficher le joueur
	   * @param color couleur du joueur
	   * @param yInit position verticale
	   */
	  Player(GraphicsContext gc, String color, int xInit, int yInit, String side, Scene scene, boolean equipe){
		// Tous les joueurs commencent au centre du canvas, 
	    x = xInit;               
	    y = yInit;
	    graphicsContext = gc;
	    playerColor=color;
		haut = equipe;
	    
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
        //directionArrow = sprite.getClip().;

	    // Tous les joueurs ont une vitesse aleatoire entre 0.0 et 1.0
        // Random randomGenerator = new Random();
        // step = randomGenerator.nextFloat();

        // Pour commencer les joueurs ont une vitesse / un pas fixe
        step = 1;
	    
	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    scene.setOnKeyPressed(
			new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e){
					String code = e.getCode().toString();
					// only add once... prevent duplicates
					if(!input.contains(code)){
						input.add(code);
					}
				}
			}
		);

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    scene.setOnKeyReleased(
			new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e){
					String code = e.getCode().toString();
					input.remove(code);
				}
			}
		);
		new AnimationTimer(){
	        public void handle(long currentNanoTime){
				controlleur();
			}
		}.start(); // On lance la boucle de rafraichissement
	}

	/**
	 *  Affichage du joueur
	 */
	void vue(){
		graphicsContext.save(); // saves the current state on stack, including the current transform
		rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
		graphicsContext.drawImage(directionArrow, x, y);
		graphicsContext.restore(); // back to original state (before rotation)
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}
	
	/**
	 *  Deplacement du joueur vers la gauche, on cantonne le joueur sur le plateau de jeu
	 */
	void moveLeft(){	    
		if(x > 10 /*&& x < 520*/){
			spriteAnimate();
			x -= step;
		}
	}

	/**
	 *  Deplacement du joueur vers la droite
	 */
	void moveRight(){
		if(/*x > 10 &&*/ x < 520){ // VAR GLOBALE OU PARAM
			spriteAnimate();
			x += step;
		}
	}

	/**
	 *  Rotation du joueur vers la gauche
	 */
	void turnLeft(){
		if(angle > 0 && angle < 180){
			angle += 1;
		}else{
			angle += 1;
		}
	}

	/**
	 *  Rotation du joueur vers la droite
	 */
	void turnRight(){
		if (angle > 0 && angle < 180){
			angle -=1;
		}else{
			angle -= 1;
		}
	}

	void shoot(){
		sprite.playShoot();
	}
	
	/**
	 *  Déplacement en mode boost
	 */
	void boost(){
		x += step*2;
		spriteAnimate();
	}

	void spriteAnimate(){
		//System.out.println("Animating sprite");
		if(!sprite.isRunning){
			sprite.playContinuously();
		}
		sprite.setX(x);
		sprite.setY(y);
	}

	/**
	 *  Gére les modifications du modèle à partir des infos de la vue.
	 */
	void controlleur(){
		if(haut){
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
		}else{
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
		}	
		if(input.contains("SPACE")){
			this.shoot();
		}
		this.vue();
	}
}
