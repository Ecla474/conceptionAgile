package fr.icom.info.m1.balleauprisonnier_mvn;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	
	/**
	 * En javafx start() lance l'application
	 *
	 * On cree le SceneGraph de l'application ici
	 * @see http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception{
		// Nom de la fenetre
        stage.setTitle("BalleAuPrisonnier");

        Group root = new Group();
        Scene scene = new Scene( root );

        // On cree le terrain de jeu et on l'ajoute a la racine de la scene
        Field gameField = new Field(scene, 600, 600 );
        root.getChildren().add(gameField);
		// AFFICHAGE DES JOUEURS
		for(int i=0; i<gameField.getJoueurs().length; i++){
			root.getChildren().add(gameField.getJoueurs()[i].sprite);
		}

        // On ajoute la scene a la fenetre et on affiche
        stage.setScene( scene );
        stage.show();
	}
	
    public static void main(String[] args){
    	Application.launch(args);
    }
}
