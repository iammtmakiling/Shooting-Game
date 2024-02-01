package HomePage;

// when we run the program in Main, this class will be instantiated and run

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.ImageCursor;
import java.io.File;
import InGame.PlayStage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneController {

// declaration of attributes
private StackPane pane;
private Scene scene;
private Stage stage;

private Group root;
private Canvas canvas;
private static GraphicsContext gc;

// dimensions of the windows
public final static int WINDOW_WIDTH = 800;
public final static int WINDOW_HEIGHT = 500;

// necessary images and background music throughout the game
private final Image BG = new Image("images/background.jpg",800,500,false,false);
private final Image ICON = new Image ("images/icon.png",500,500,false,false);
private final Image CURSOR = new Image ("images/cursor.png",500,500,false,false);
public static MediaPlayer mediaPlayer;

// constructor
public SceneController(){
	this.music();

	Image bg = new Image(new File("images/background.gif").toURI().toString());
	ImageView imageView = new ImageView(bg);

	this.root = new Group(imageView);
	this.scene = new Scene(root, SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);

	this.pane = new StackPane();
	this.scene = new Scene(pane, SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);

	this.canvas = new Canvas(SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);
	SceneController.gc = canvas.getGraphicsContext2D();

	this.setProperties();
 }

	// for the background music
	private void music(){
		Media sound = new Media(getClass().getResource("/sfx/bgmusic.mp3").toExternalForm());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play(); // plays the music
		mediaPlayer.setVolume(0.05);
	}

	private void setProperties(){
		// set the background image
		SceneController.gc.drawImage(this.BG,0,0);

		// declaration of the buttons to be used
		Button playbtn = new Button("Play");
		Button insbtn = new Button("Instruction");
		Button abtbtn = new Button("About");

		// necessary buttons: play, instructions, and about buttons
		playbtn.setOnAction(start1);
		insbtn.setOnAction(instruction1);
		abtbtn.setOnAction(about1);

		abtbtn.setTranslateX(-100);
		insbtn.setTranslateX(100);
		abtbtn.setPrefWidth(90);
		playbtn.setPrefWidth(90);
		playbtn.setPrefHeight(50);

		abtbtn.setTranslateY(80);
		insbtn.setTranslateY(80);
		playbtn.setTranslateY(80);

		this.pane.getChildren().add(canvas);
		this.pane.getChildren().add(playbtn);
		this.pane.getChildren().add(insbtn);
		this.pane.getChildren().add(abtbtn);
	}

	 public void setStage(Stage stage) {
		this.stage = stage;

		this.scene.setCursor(new ImageCursor(CURSOR));
		stage.getIcons().add(ICON);

		this.stage.setTitle("Mini Ship Shooting Game");
		this.stage.setScene(this.scene);
		this.stage.show();
	 }

	 // when buttons are clicked, the user will proceed to the respective scene
	 private EventHandler<ActionEvent> start1 = new EventHandler<ActionEvent>(){
		 public void handle(ActionEvent e) {
			PlayStage playStage = new PlayStage();
			playStage.setStage(stage);
			stage.setScene(playStage.getScene());
		 }
	 };

	 private EventHandler<ActionEvent> instruction1 = new EventHandler<ActionEvent>(){
		 public void handle(ActionEvent e) {
			//mediaPlayer.setMute(true);
			Instructions instructions = new Instructions();
			instructions.setStage(stage);
		 }
	 };

	 private EventHandler<ActionEvent> about1 = new EventHandler<ActionEvent>(){
		 public void handle(ActionEvent e) {
			//mediaPlayer.setMute(true);
			About about = new About();
			about.setStage(stage);
		 }
	 };

	 // getter
	public Scene getScene(){
		return this.scene;
	}
}