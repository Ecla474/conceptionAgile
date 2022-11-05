package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Projectile {
    private double x;
    private double y;
    private double vitesse;
    private double direction;
    private Image representation;
    private GraphicsContext gc;
    

    public Projectile(GraphicsContext gcParent, double xInitial, double yInitial, double directionInitiale){
        gc = gcParent;
        vitesse = 1;
        representation = new Image("assets/ball.png");
        x = xInitial;
        y = yInitial;
        direction = directionInitiale;
    }

    /**
     * Gére les modifications du modèle
     */
    public void controlleur(){
        x = x + vitesse * Math.cos(direction);
        y = y + vitesse * Math.sin(direction);
        vue();
    }

    private void vue(){
        gc.drawImage(representation, x, y);
    }
}
