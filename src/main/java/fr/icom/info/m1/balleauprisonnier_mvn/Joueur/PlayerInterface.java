package fr.icom.info.m1.balleauprisonnier_mvn.Joueur;

import javafx.scene.canvas.GraphicsContext;

/**
 * Description de l'interface utilisée par la classe PlayerFactory.
 */
public interface PlayerInterface{
    Player creerPlayer(GraphicsContext gc, Player.typeJoueur couleurJoueur, int xInit, int yInit, Player.orientation position, Player.strategie strat, int width, double v);
}
