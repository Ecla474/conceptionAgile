package fr.icom.info.m1.balleauprisonnier_mvn.Joueur;

public class Rectangle{
	/**** DONNÉES MEMBRES */
	protected double y;
	protected double x;
	protected double largeur;
	protected double hauteur;


	/**** MÉTHODES À HÉRITER ****/
    /**
	 * @return l'abscisse du joueur.
	 */
	public double getX(){
		return x;
	}

	/**
	 * @return l'ordonée du joueur.
	 */
	public double getY(){
		return y;
	}

	/**
	 * @return la largeur.
	 */
	public double getLargeur(){
		return largeur;
	}

	/**
	 * @return la hauteur.
	 */
	public double getHauteur(){
		return hauteur;
	}

	/**
	 * @param nouveauX nouvelle valeur de X.
	 */
	public void setX(double nouveauX){
		x = nouveauX;
	}

	/**
	 * @param nouveauY nouvelle valeur de Y.
	 */
	public void setY(double nouveauY){
		y = nouveauY;
	}

	/**
	 * @param nouvelleLargeur nouvelle largeur.
	 */
	public void setLargeur(double nouvelleLargeur){
		largeur = nouvelleLargeur;
	}

	/**
	 * @param nouvelleHauteur nouvelle hauteur.
	 */
	public void setHauteur(double nouvelleHauteur){
		hauteur = nouvelleHauteur;
	}
}
