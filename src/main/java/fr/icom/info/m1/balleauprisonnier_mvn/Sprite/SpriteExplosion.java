package fr.icom.info.m1.balleauprisonnier_mvn.Sprite;

import javafx.beans.property.*;
import javafx.util.Duration;
import javafx.scene.image.*;
import javafx.animation.*;
import javafx.geometry.*;


/**
 * Classe implémentant le sprite d'une explosion (lorsqu'un joueur est touché par un Projectile).
 */
public class SpriteExplosion extends Sprite{
	/**** DONNÉES MEMBRES ****/
	private final Rectangle2D[] walkClips;
	private final Rectangle2D[] shootClips;
	private int numCellsWalk;
	private int numCellsShoot;
	private final Timeline walkTimeline;
	private final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
	private final Timeline shootTimeline;
	private Timeline timeline;
	/* Dimensions d'une cellule */
	private int hauteurCellule = 146;
	private int largeurCellule = 112;

	/**
	 * Génére le sprite de l'explosion.
	 */
	public SpriteExplosion(){
		numCellsWalk = 6;
		int lineNumber = 0;

		walkClips = new Rectangle2D[numCellsWalk];
		for(int i = 0; i < numCellsWalk; i++){
			walkClips[i] = new Rectangle2D(
					i * largeurCellule, hauteurCellule*lineNumber,
					largeurCellule, hauteurCellule
			);
		}
		Image animationImage = new Image("assets/explosions.png");
		setImage(animationImage);
		setViewport(walkClips[0]);

		Duration frameTime = Duration.seconds(.50);
		walkTimeline = new Timeline(
			new KeyFrame(frameTime, event -> {
				frameCounter.set((frameCounter.get() + 1) % numCellsWalk);
				setViewport(walkClips[frameCounter.get()]);
			})
		);

		numCellsShoot = 6;
		lineNumber += 0;

		shootClips = new Rectangle2D[numCellsShoot];
		for(int i = 0; i < numCellsShoot; i++){
			shootClips[i] = new Rectangle2D(
					i * largeurCellule, hauteurCellule*lineNumber,
					largeurCellule, hauteurCellule
			);
		}

		shootTimeline = new Timeline(
			new KeyFrame(Duration.seconds(.15), event -> {
				frameCounter.set((frameCounter.get() + 1) % numCellsShoot);
				setViewport(shootClips[frameCounter.get()]);
			})
		);

		timeline = walkTimeline;
		isRunning = false;
	}

	/**
	 * Joue le sprite de l'explosion.
	 */
	public void playShoot(){
		frameCounter.set(0);
		timeline.stop();
		timeline = shootTimeline;
		timeline.setCycleCount(numCellsShoot);
		//timeline.setOnFinished(e -> playContinuously());
		timeline.playFromStart();
	}
}
