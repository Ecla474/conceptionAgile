package fr.icom.info.m1.balleauprisonnier_mvn.Joueur;

import javafx.scene.canvas.GraphicsContext;
import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.*;
import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.PlayerInterface;

import static fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Player.typeJoueur.*;


public class PlayerFactory implements PlayerInterface{


    @Override
    public Player creerPlayer(GraphicsContext gc, Player.typeJoueur couleurJoueur, int xInit, int yInit, Player.orientation position, int width, double v){

        Player player = null;

        switch (couleurJoueur) {
            case RED:
            case BLUE:
                player = new Player(gc, couleurJoueur, xInit, yInit, position, width, v);
                break;
            case ORC:
            case SKELETON:
                player = new Bot(gc, couleurJoueur, xInit, yInit, position, width, v);
                break;
            default:
                System.out.println("Type inconnu");
        }
        return player;
    }
}
