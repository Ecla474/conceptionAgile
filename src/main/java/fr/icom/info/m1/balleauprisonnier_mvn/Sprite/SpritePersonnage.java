package fr.icom.info.m1.balleauprisonnier_mvn.Sprite;

import fr.icom.info.m1.balleauprisonnier_mvn.Joueur.Player;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.util.Duration;

public class SpritePersonnage extends Sprite{
	private final Rectangle2D[] walkClips;
	private final Rectangle2D[] shootClips;
	protected int numCellsWalk;
	protected int numCellsShoot;
	private final Timeline walkTimeline;
	private final IntegerProperty frameCounter;
	protected final Timeline shootTimeline;



	public SpritePersonnage(Image animationImage, int numCells, int numRows, Duration frameTime, Player.orientation orientationInitiale){
		frameCounter = new SimpleIntegerProperty(0);
		hauteurCellule = 64;
		largeurCellule = 64;
		numCellsWalk = 9;

		int lineNumber = 8;
		if(orientationInitiale == Player.orientation.HAUT){
			lineNumber += 2;
		}

		walkClips = new Rectangle2D[numCellsWalk];
		for(int i = 0; i < numCellsWalk; i++){
			walkClips[i] = new Rectangle2D(
					i * largeurCellule, hauteurCellule*lineNumber,
					largeurCellule, hauteurCellule
			);
		}

		setImage(animationImage);
		setViewport(walkClips[0]);

		walkTimeline = new Timeline(
			new KeyFrame(frameTime, event -> {
				frameCounter.set((frameCounter.get() + 1) % numCellsWalk);
				setViewport(walkClips[frameCounter.get()]);
			})
		);

		numCellsShoot = 13;
		lineNumber += 8;

		shootClips = new Rectangle2D[numCellsShoot];
		for(int i = 0; i < numCellsShoot; i++){
			shootClips[i] = new Rectangle2D(
					i * largeurCellule, hauteurCellule*lineNumber,
					largeurCellule, hauteurCellule
			);
		}

		shootTimeline = new Timeline(
			new KeyFrame(frameTime, event -> {
				frameCounter.set((frameCounter.get() + 1) % numCellsShoot);
				setViewport(shootClips[frameCounter.get()]);
			})
		);

		timeline = walkTimeline;
		isRunning = false;
	}

	public void playContinuously() {
		isRunning = true;
		frameCounter.set(0);
		timeline = walkTimeline;
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.stop();
		timeline.playFromStart();
	}

	public void playShoot(){
		frameCounter.set(0);
		timeline.stop();
		timeline = shootTimeline;
		timeline.setCycleCount(numCellsShoot);
		timeline.setOnFinished(e -> playContinuously());
		timeline.playFromStart();
	}

	public void stop() {
		frameCounter.set(0);
		setViewport(walkClips[frameCounter.get()]);
		walkTimeline.stop();
	}


}
