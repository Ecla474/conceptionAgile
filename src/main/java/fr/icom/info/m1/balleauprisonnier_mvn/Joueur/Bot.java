package fr.icom.info.m1.balleauprisonnier_mvn.Joueur;

import java.util.Calendar;
import java.util.Date;

import java.util.Vector;
import fr.icom.info.m1.balleauprisonnier_mvn.Projectile.Projectile;
import javafx.scene.canvas.GraphicsContext;

public class Bot extends Player{
	/**
	 * Constructeur du joueur bot
	 * @param gc Contexte graphique
	 * @param type Type du joueur
	 * @param xInit Position initiale du joueur en largeur du terrain
	 * @param yInit Position initiale du joueur en longueur du terrain
	 * @param orientationInitiale Orientation du joueur
	 * @param largeurPlateau Largeur du terrain
	 * @param vitesse Vitesse du joueur
	 */
	public Bot(GraphicsContext gc, typeJoueur color, int xInit, int yInit, Player.orientation orientationInitiale, int largeurPlateau, strategie strategieAdoptee, double vitesse){
		super(gc, color, xInit, yInit, orientationInitiale, largeurPlateau, vitesse);
		strategieEnCours = strategieAdoptee; 
	}
	
	/**
	 * Constructeur du joueur bot avec une vitesse aléatoire
	 * @param gc Contexte graphique
	 * @param type Type du joueur
	 * @param xInit Position initiale du joueur en largeur du terrain
	 * @param yInit Position initiale du joueur en longueur du terrain
	 * @param orientationInitiale Orientation du joueur
	 * @param largeurPlateau Largeur du terrain
	 */
	public Bot(GraphicsContext gc, typeJoueur color, int xInit, int yInit, Player.orientation orientationInitiale, int largeurPlateau, strategie strategieAdoptee){
		super(gc, color, xInit, yInit, orientationInitiale, largeurPlateau);
		strategieEnCours = strategieAdoptee; 
	}

	/**
	 * Surcharge du contrôleur destinée aux Bots se déplaçant au hazard ou restant statiques.
	 */
	public void controleur(){
		if(strategieEnCours==strategie.HASARD){
			Calendar c1 = Calendar.getInstance();
			Date dateOne = c1.getTime();
			if(dateOne.getTime()%2==0){
				moveLeft();
			}else{
				moveRight();
			}
		}else if(strategieEnCours==strategie.STATIQUE){
		}else{
			System.out.println("Erreur d'appel de constructeur");
		}
	}

	/**
	 * Surcharge du contrôleur destinée aux Bots cherchant à se rapprocher des autres membres de son équipe.
	 * @param coordonneesEquipe Tableau des membres d'une équipe.
	 */
	public void controleur(Vector<Player> coordonneesEquipe, int positionCourante){
		double xMoyen = 0;
		for(int i=0; i<coordonneesEquipe.size(); i++){
			if(i != positionCourante){
				xMoyen += coordonneesEquipe.get(i).getX();
			}
		}
		
		xMoyen=xMoyen/((double)coordonneesEquipe.size()-1);
		if(xMoyen<x && xMoyen+30<x){
			moveLeft();
		}else if(xMoyen>x && xMoyen>x+30){
			moveRight();
		}
	}

	/**
	 * @param projectiles Tableau des projectiles
	 */
	public void controleur(Vector<Projectile> projectiles){
		double xMoyen = 0;
		for(int i=0; i<projectiles.size(); i++){
			if(projectiles.get(i).getOrientation()!=orientationActuelle){
				xMoyen += projectiles.get(i).getX();
			}
		}
		
		xMoyen=xMoyen/projectiles.size();
		if(xMoyen>x && xMoyen!=0){
			moveLeft();
		}else if(xMoyen<x && xMoyen!=0){
			moveRight();
		}
	}
}
