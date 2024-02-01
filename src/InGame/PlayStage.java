package InGame;


import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import miniprojtemplate.GameStage;
//import miniprojtemplate.GameTimer;
import javafx.util.Duration;

public class PlayStage {
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	static GraphicsContext gc;
	private GameTimer gametimer;

	private final Image ICON = new Image ("images/icon.png",500,500,false,false);
	private final Image CURSOR = new Image ("images/cursor.png",500,500,false,false);

	//the class constructor
	public PlayStage() {
		this.root = new Group();
		this.scene = new Scene(root, PlayStage.WINDOW_WIDTH,PlayStage.WINDOW_HEIGHT, Color.CADETBLUE);
		this.canvas = new Canvas(PlayStage.WINDOW_WIDTH,PlayStage.WINDOW_HEIGHT);
		PlayStage.gc = canvas.getGraphicsContext2D();

		//instantiate an animation timer
		this.gametimer = new GameTimer(PlayStage.gc,this.scene, this);
		new Thread (gametimer).start();
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;

		this.scene.setCursor(new ImageCursor(CURSOR));
		stage.getIcons().add(ICON);

		//set stage elements here
		this.root.getChildren().add(canvas);

		this.stage.setTitle("Space Sweeper");
		this.stage.setScene(this.scene);

		//invoke the start method of the animation timer
		this.gametimer.start();

		this.stage.show();
	}

	// when the game is over
	void setGameOver(){
		// there will be a slight pause before being directed to the win/lose page
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				GameOverStage gameover = new GameOverStage();
				gameover.setStage(stage);
			}
		});
	}

	public Scene getScene() {
		return this.scene;
	}

}

