package fr.icom.info.m1.balleauprisonnier_mvn.Joueur;

import javafx.scene.canvas.GraphicsContext;

/**
 * Classe qui générera plus aisément différents types de joueurs
 */
public class PlayerFactory implements PlayerInterface{
	@Override public Player creerPlayer(GraphicsContext gc, Player.typeJoueur couleurJoueur, int xInit, int yInit, Player.orientation position, Player.strategie strat, int width , double v){

		Player player = null;

		switch(couleurJoueur){
			case RED:
			case BLUE:
				player = new Player(gc, couleurJoueur, xInit, yInit, position , width, v);
				break;
			case ORC:
			case SKELETON:
				player = new Bot(gc, couleurJoueur, xInit, yInit, position, strat , width, v);
				break;
			default:
				System.out.println("Type inconnu");
		}
		return player;
	}
}
