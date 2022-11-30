package fr.icom.info.m1.balleauprisonnier_mvn.Joueur;

import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Player;
import javafx.scene.canvas.GraphicsContext;

public interface PlayerInterface {
    Player creerPlayer(GraphicsContext gc, Player.typeJoueur couleurJoueur, int xInit, int yInit, Player.orientation position, int width, double v);
}
