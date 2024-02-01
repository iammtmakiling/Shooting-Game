package main;
import HomePage.SceneController;
/*
 * 12/7/21
 */
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// starts the game
	public void start(Stage stage) throws Exception {
		// instantiate a new instance of the scene controller
		// scene controller contains the following pages: play, instruction, and about
		SceneController scenecontroller = new SceneController();
		scenecontroller.setStage(stage);
		stage.setScene(scenecontroller.getScene());
	}
}
