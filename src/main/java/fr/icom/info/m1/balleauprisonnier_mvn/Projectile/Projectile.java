package fr.icom.info.m1.balleauprisonnier_mvn.Projectile;

import javafx.scene.image.Image;
import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Player;
import javafx.scene.canvas.GraphicsContext;

public class Projectile{
	private double x;
	private double y;
	private double profondeurTerrain;
	private double vitesse;
	private double direction;
	private Image representation;
	private GraphicsContext gc;
	private Player.orientation orientation;
	
	public void finalize(){}

	public Projectile(GraphicsContext gcParent, double xInitial, double yInitial, double directionInitiale, double fondDuTerrain, Player.orientation orientationTir){
		gc = gcParent;
		vitesse = 1;
		representation = new Image("assets/ball.png");
		x = xInitial;
		y = yInitial;
		direction = directionInitiale;

		profondeurTerrain = fondDuTerrain;
		orientation = orientationTir;
	}

	/**
	 * @return la nécessité d'être supprimé du terrain.
	 */
	public boolean controlleur(){
		x += vitesse * Math.cos(Math.toRadians(direction));
		y += vitesse * Math.sin(Math.toRadians(direction));

		switch(orientation){
			case HAUT:
				if(y>profondeurTerrain){
					return false;
				}
				break;
			case BAS:
				if(y<profondeurTerrain){
					return false;
				}
				break;
		}
		return true;
	}

	public void vue(){
		gc.drawImage(representation, x, y);
	}

	/**
	 * @return abscisse
	 */
	public double getX(){
		return x;
	}

	/**
	 * @return ordonnée
	 */
	public double getY(){
		return y;
	}

	/**
	 * @return orientation
	 */
	public Player.orientation getOrientation(){
		return orientation;
	}
}
