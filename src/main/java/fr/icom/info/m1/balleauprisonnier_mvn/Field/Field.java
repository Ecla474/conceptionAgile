package fr.icom.info.m1.balleauprisonnier_mvn.Field;

import java.util.ArrayList;
import java.util.Vector;

import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Bot;
import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Player;
import fr.icom.info.m1.balleauprisonnier_mvn.Projectile.Projectile;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Classe gérant le terrain de jeu.
 */
public class Field extends Canvas{
	// Équipes :
	public enum equipes {UNE, DEUX};
	private int nbrJoueursEquipe1 = 3;
	private int nbrJoueursEquipe2 = 3;
	private Player[] equipe1 = new Player[nbrJoueursEquipe1];
	private Player[] equipe2 = new Player[nbrJoueursEquipe2];

	// Projectiles
	private Vector<Projectile> projectiles = new Vector<Projectile>();

	// Tableau traçant les evenements
	private ArrayList<String> input = new ArrayList<String>();
	
	public final GraphicsContext gc;
	private final int width;
	private final int height;
	
	/**
	 * Canvas dans lequel on va dessiner le jeu.
	 * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
	 * @param w Largeur du canvas
	 * @param h Hauteur du canvas
	 */
	public Field(Scene scene, int w, int h){
		super(w, h); 
		width = w;
		height = h;

		// Permet de capturer le focus et donc les événements clavier et souris.
		this.setFocusTraversable(true);
		gc = this.getGraphicsContext2D();
		// On initialise le terrain de jeu
		equipe1[0] = new Player(gc, Player.typeJoueur.BLUE,     w/4-26,   h-140, Player.orientation.BAS,     width, 0.5);
		equipe1[1] = new Bot(gc, Player.typeJoueur.SKELETON, w/2-32,   h-140, Player.orientation.BAS,     width, 0.5);
		equipe1[2] = new Bot(gc, Player.typeJoueur.SKELETON, 3*w/4-35, h-140, Player.orientation.BAS,     width, 0.5);
		equipe2[0] = new Player(gc, Player.typeJoueur.RED,      w/4-26,   20, Player.orientation.HAUT, width, 1);
		equipe2[1] = new Bot(gc, Player.typeJoueur.ORC,      w/2-32,   20, Player.orientation.HAUT, width, 1);
		equipe2[2] = new Bot(gc, Player.typeJoueur.ORC,      3*w/4-35, 20, Player.orientation.HAUT, width, 1);

		// 𝘌𝘷𝘦𝘯𝘵 𝘓𝘪𝘴𝘵𝘦𝘯𝘦𝘳 du clavier : quand une touche est pressée, on la rajoute a la liste d'input.
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

		// 𝘌𝘷𝘦𝘯𝘵 𝘓𝘪𝘴𝘵𝘦𝘯𝘦𝘳 du clavier : quand une touche est relachée, on l'enleve de la liste d'input.
		this.setOnKeyReleased(
			new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e){
					String code = e.getCode().toString();
					input.remove(code);
				}
			}
		);
		// Boucle principale du jeu : handle() est appelee à chaque rafraîchissement de frame (soit environ 60 fois par seconde).
		new AnimationTimer(){


			public void handle(long currentNanoTime){	 
				// On nettoie le canvas à chaque frame
				gc.clearRect(0, 0, getWidth(), getHeight());
				for(int i=0; i<nbrJoueursEquipe1; i++){
					if(equipe1[i].controlleur(input, Field.equipes.UNE)){
						addProjectile(equipe1[i]);
					}
				}
				for(int i=0; i<nbrJoueursEquipe2; i++){
					if(equipe2[i].controlleur(input, Field.equipes.DEUX)){
						addProjectile(equipe2[i]);
					}
				}
				for(int i=0; i<projectiles.size(); i++){
					projectiles.get(i).controlleur();
				}
				gc.setFill(Color.WHITE);
				gc.setFont(new Font("", 60));
				gc.fillText("0", width/2, (height/2)-45);
				gc.fillText("0", width/2, (height/2)+65);
			}


		}.start(); // On lance la boucle de rafraîchissement.
		
	}


	/**
	 * @return Tableau des joueurs des 2 équipes.
	 */
	public Player[] getJoueurs() {
		Player[] retour = new Player[nbrJoueursEquipe1+nbrJoueursEquipe2];
		System.arraycopy(equipe1, 0, retour, 0, nbrJoueursEquipe1);
		System.arraycopy(equipe2, 0, retour, nbrJoueursEquipe1, nbrJoueursEquipe2);
		return retour;
	}

	/**
	 * @return le nombre de joueurs
	 */
	public int getNbrJoueurs(){
		return nbrJoueursEquipe1+nbrJoueursEquipe2;
	}

	/*/**
	 * Ajoute un nouveau projectile.
	 * @param xInitial
	 * @param yInitial
	 * @param directionInitiale
	 */
	public void addProjectile(Player joueur){
		double angle = joueur.getAngle();
		switch(joueur.getOrientation()){
			case HAUT:
				angle+=90;
				break;
			case BAS:
				angle-=90;
				break;
		}
		projectiles.add(new Projectile());
		projectiles.lastElement().getInstance(gc, joueur.getX()+10, joueur.getY()+10, angle);
		
	}

}