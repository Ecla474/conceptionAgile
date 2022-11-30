package fr.icom.info.m1.balleauprisonnier_mvn.App;

import fr.icom.info.m1.balleauprisonnier_mvn.Field.Field;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application{
	
	/**
	 * En javafx start() lance l'application
	 *
	 * On crée le SceneGraph de l'application ici
	 * @see http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
	 */
	@Override public void start(Stage stage) throws Exception{
		// Nom de la fenêtre
		stage.setTitle("Balle au prisonnier");

		// Création du conteneur et de la racine de la scène
		Group root = new Group();
		Scene scene = new Scene(root);

		// Création du fond de la fenêtre et ajout à la racine de la scène
		Image fond = new Image("assets/fond.png");
		ImageView fondFenetre = new ImageView(fond);
		root.getChildren().add(fondFenetre);

		// Création du terrain de jeu et ajout à la racine de la scène
		Field gameField = new Field(scene, 720, 858, root);
		root.getChildren().add(gameField);

		// On ajoute la scène a la fenêtre et on affiche
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}
}
