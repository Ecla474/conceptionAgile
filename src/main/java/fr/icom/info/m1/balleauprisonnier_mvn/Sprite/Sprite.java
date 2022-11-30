package fr.icom.info.m1.balleauprisonnier_mvn.Sprite;

import javafx.scene.image.*;
import javafx.animation.*;

public class Sprite extends ImageView{
    protected Timeline timeline;
	protected boolean isRunning;
	protected int hauteurCellule;
	protected int largeurCellule;

    /**
     * @return l'état (en cours ou pas) du sprite
     */
    public boolean isItRunning(){
        return isRunning;
    }
}
