package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

/**
 * Classe gerant le terrain de jeu.
 */
public class Field extends Canvas{
	// Joueurs
	private int nbrJoueurs = 2;
	private Player[] joueurs = new Player[nbrJoueurs];

	// Couleurs possibles
	private String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	// Tableau traçant les evenements
	private ArrayList<String> input = new ArrayList<String>();
	
	private final GraphicsContext gc;
	private final int width;
	private final int height;
	
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
		
		// permet de capturer le focus et donc les evenements clavier et souris.
		this.setFocusTraversable(true);
		
		gc = this.getGraphicsContext2D();
		
		// On initialise le terrain de jeu
		joueurs[0] = new Player(gc, colorMap[0], w/2, h-50, "bottom", Player.equipes.UNE, width);
		joueurs[1] = new Player(gc, colorMap[1], w/2, 20, "top", Player.equipes.DEUX, width);

		// Event Listener du clavier : quand une touche est pressée, on la rajoute a la liste d'input.
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

		// Event Listener du clavier : quand une touche est relachée, on l'enleve de la liste d'input.
		this.setOnKeyReleased(
			new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e){
					String code = e.getCode().toString();
					input.remove(code);
				}
			}
		);

		// Boucle principale du jeu : handle() est appelee à chaque rafraichissement de frame (soit environ 60 fois par seconde)
		new AnimationTimer(){
			public void handle(long currentNanoTime){	 
				// On nettoie le canvas a chaque frame
				gc.setFill( Color.LIGHTGRAY);
				gc.fillRect(0, 0, width, height);
				for(int i=0; i<nbrJoueurs; i++){
					joueurs[i].controlleur(input);
				}
			}
		}.start(); // On lance la boucle de rafraichissement 
		
	}

	/**
	 * @return Tableau des joueurs
	 */
	public Player[] getJoueurs() {
		return joueurs;
	}

	/**
	 * @return le nombre de joueurs
	 */
	public int getNbrJoueurs(){
		return nbrJoueurs;
	}

	/**
	 * @return la largeur du terrain
	 */
	public int getLargeurTerrain(){
		return width;
	}
}