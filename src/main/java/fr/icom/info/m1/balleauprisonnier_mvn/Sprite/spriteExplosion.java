package fr.icom.info.m1.balleauprisonnier_mvn.Sprite;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.util.Duration;

public class spriteExplosion extends Sprite{
	private final Rectangle2D[] walkClips;
	private final Rectangle2D[] shootClips;
	//private int numCells;
	protected int numCellsWalk;
	protected int numCellsShoot;
	private final Timeline walkTimeline;
	private final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
	protected final Timeline shootTimeline;
	private Timeline timeline;
	public boolean isRunning;
	private int hauteurCellule = 146;
	private int largeurCellule = 112;

	public spriteExplosion(){
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

	public void playShoot(){
		frameCounter.set(0);
		timeline.stop();
		timeline = shootTimeline;
		timeline.setCycleCount(numCellsShoot);
		//timeline.setOnFinished(e -> playContinuously());
		timeline.playFromStart();
	}

	public void stop() {
		frameCounter.set(0);
		setViewport(walkClips[frameCounter.get()]);
		walkTimeline.stop();
	}
}
