package InGame;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.ImageCursor;
import HomePage.SceneController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameOverStage {

	// attributes
	private Canvas canvas;
	private static GraphicsContext gc;
	private Scene scene;
	private Stage stage;
	private StackPane pane;

	public final Image icon = new Image ("images/icon.png",500,500,false,false);
	public final Image cursor = new Image ("images/cursor.png",500,500,false,false);

	public final Image gameOverBg = new Image("images/gameoverbg.jpg",800,500,false,false);
	public final Image congratsBg = new Image("images/congratsbg.jpg",800,500,false,false);

	// constructor
	public GameOverStage (){
		this.pane = new StackPane();
		this.scene = new Scene(pane, SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);

		this.canvas = new Canvas(SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);
		GameOverStage.gc = canvas.getGraphicsContext2D();

		this.setProperties();
	}

	private void setProperties(){
		if (GameTimer.getGameStatus() == true){ // player wins
			GameOverStage.gc.drawImage(this.congratsBg,0,0);
		} else { // player loses
			GameOverStage.gc.drawImage(this.gameOverBg,0,0);
		}

		// exit the game
		Button exitbtn = new Button("Exit");

		exitbtn.setPrefWidth(90);
		exitbtn.setPrefHeight(50);
		exitbtn.setTranslateY(140);

		//set font type, style, size, and color
		Font theFont = Font.font("Times New Roman",FontWeight.BOLD,45);
		GameOverStage.gc.setFont(theFont);
		GameOverStage.gc.setFill(Color.BLACK);
		GameOverStage.gc.fillText("Score: ", PlayStage.WINDOW_WIDTH*0.35, PlayStage.WINDOW_HEIGHT*0.60);
		GameOverStage.gc.fillText(String.valueOf(GameTimer.getScore()), PlayStage.WINDOW_WIDTH*0.53,PlayStage.WINDOW_HEIGHT*0.60);

		this.addEventHandler(exitbtn);
		this.pane.getChildren().add(canvas);
		this.pane.getChildren().add(exitbtn);
	}

	public void setStage(Stage stage) {
		this.stage = stage;

		this.scene.setCursor(new ImageCursor(cursor));
		stage.getIcons().add(icon);

		this.stage.setTitle("Mini Ship Shooting Game");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	// exit the game
	private void addEventHandler(Button exitbtn) {
		exitbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent arg0) {
				System.exit(0);
			}
		});

	}



}
