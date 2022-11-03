package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas{
	/** Joueurs */
	Player[] joueurs = new Player[2];
	/** Couleurs possibles */
	String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();
    

    final GraphicsContext gc;
    final int width;
    final int height;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	public Field(Scene scene, int w, int h){
		super(w, h); 
		width = w;
		height = h;
		
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		
        gc = this.getGraphicsContext2D();
        
        /** On initialise le terrain de jeu */
    	joueurs[0] = new Player(gc, colorMap[0], w/2, h-50, "bottom", scene, true);
    	joueurs[1] = new Player(gc, colorMap[1], w/2, 20, "top", scene, false);

	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
	    new AnimationTimer(){
	        public void handle(long currentNanoTime){	 
	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);
	    	}
	    }.start(); // On lance la boucle de rafraichissement 
	     
	}

	public Player[] getJoueurs() {
		return joueurs;
	}
}
