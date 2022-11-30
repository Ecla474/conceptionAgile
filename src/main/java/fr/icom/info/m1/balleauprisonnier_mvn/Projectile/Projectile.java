package fr.icom.info.m1.balleauprisonnier_mvn.Projectile;
import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe implémentant un Projectile.
 */
public class Projectile{
	/***** DONNÉES MEMBRES *****/
	/* Propriétés physiques */
	// Abscisse
	private double x;
	// Ordonnée
	private double y;
	// Vitesse de déplacement
	private double vitesse;
	// Direction en degrés
	private double direction;

	/* Environnement */
	// Profondeur du terrain
	private double profondeurTerrain;
	// Bord gauche du terrain
	private int bordGauche;
	// Bord droit du terrain
	private int bordDroit;
	// Camps qui a lancé le projectile (et qui est immunnisé contre ses dégâts)
	private Player.orientation orientation;

	/* Données graphiques */
	// Représentation visuelle de l'objet
	private Image representation;
	// Contexte graphique
	private GraphicsContext gc;


	/**** MÉTHODES PUBLIQUE ****/
	/**
	 * Destructeur explicite
	 */
	public void finalize(){}

	private static Projectile instance = null;

	/**
	* @param gcParent contexte graphique
	* @param xInitial abscisse initiale
	* @param yInitial ordonnée initiale
	* @param directionInitiale direction initiale (en degré)
	* @param fondDuTerrain profondeur du terrain
	* @param orientationTir camps qui a lancé le Projectile
	* @param coteGauche Bord gauche du terrain
	* @param coteDroit Bord droit du terrain
	*/
	public Projectile getInstance(GraphicsContext gcParent, double xInitial, double yInitial, double directionInitiale, double fondDuTerrain, Player.orientation orientationTir, int coteGauche, int coteDroit){
		gc = gcParent;
		vitesse = 1;
		representation = new Image("assets/ball.png");
		x = xInitial;
		y = yInitial;
		direction = directionInitiale;

		profondeurTerrain = fondDuTerrain;
		orientation = orientationTir;

		bordGauche = coteGauche;
		bordDroit = coteDroit;

		return instance;
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
		// Gestion des collisions sur les côtés du terrain.
		if(x>bordDroit){
			collisionBordDroit();
		}else if(x<bordGauche){
			collisionBordGauche();
		}
		return true;
	}

	/**
	 * Gestion de la Vue du Prohectile.
	 */
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


	/**** MÉTHODES PRIVÉES ****/
	// Mise en œuvre du rebond sur un côté droit.
	private void collisionBordDroit(){
		while(x>bordDroit){
			x--;
		}
		if(direction<=0 && direction>=-180){
			direction = -180-Math.abs(direction) + 2*Math.abs(direction);
		}else{
			direction = 180-direction-2*direction;
		}
	}
	
	// Mise en œuvre du rebond sur un côté gauche.
	private void collisionBordGauche(){
		while(x<bordGauche){
			x++;
		}
		if(direction<=0 && direction>=-180){
				direction = direction+2*(Math.abs(direction)+90);
		}else{
			direction = 180-direction-2*direction;
		}
	}
}
