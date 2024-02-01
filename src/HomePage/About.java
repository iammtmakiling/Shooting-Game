package HomePage;

import javafx.scene.image.Image;
import javafx.scene.ImageCursor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class About {
	// attributes
	private Canvas canvas;
	private static GraphicsContext gc;
	private Scene scene;
	private Stage stage;
	private StackPane pane;
	private static int pageCounter = 1;

	private final Image ICON = new Image ("images/icon.png",500,500,false,false);
	private final Image CURSOR = new Image ("images/cursor.png",500,500,false,false);

	private final static Image ABT1 = new Image("images/abtbg1.jpg",800,500,false,false);
	private final static Image ABT2 = new Image("images/abtbg2.jpg",800,500,false,false);
	private final static Image ABT3 = new Image("images/abtbg3.jpg",800,500,false,false);
	private final static Image ABT4 = new Image("images/abtbg4.jpg",800,500,false,false);

	About (){
		this.pane = new StackPane();
		this.scene = new Scene(pane, SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);

		this.canvas = new Canvas(SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);
		About.gc = canvas.getGraphicsContext2D();

		this.setProperties();
	}

	private void setProperties(){
		About.gc.drawImage(About.ABT1, 0, 0);

		// buttons to navigate pages as well as go to the main page
		Button homebtn = new Button("Home");
		Button bckbtn = new Button("Back");
		Button nxtbtn = new Button("Next");

		homebtn.setOnAction(homebtn1);
		bckbtn.setOnAction(back1);
		nxtbtn.setOnAction(nxtbtn1);

		nxtbtn.setTranslateX(100);
		bckbtn.setTranslateX(-100);
		nxtbtn.setPrefWidth(90);
		homebtn.setPrefWidth(90);
		homebtn.setPrefHeight(50);
		bckbtn.setPrefWidth(90);

		nxtbtn.setTranslateY(120);
		bckbtn.setTranslateY(120);
		homebtn.setTranslateY(120);

		this.pane.getChildren().add(canvas);
		this.pane.getChildren().add(homebtn);
		this.pane.getChildren().add(bckbtn);
		this.pane.getChildren().add(nxtbtn);
	}

	void setStage(Stage stage) {
		this.stage = stage;

		this.scene.setCursor(new ImageCursor(CURSOR));
		stage.getIcons().add(ICON);

		this.stage.setTitle("Mini Ship Shooting Game");
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	// home button
	private EventHandler<ActionEvent> homebtn1 = new EventHandler<ActionEvent>(){
		 public void handle(ActionEvent e) {
			SceneController.mediaPlayer.setMute(true);
			SceneController scenecontroller = new SceneController();
			scenecontroller.setStage(stage);
		 }
	 };

	 // previous page
	 private EventHandler<ActionEvent> back1 = new EventHandler<ActionEvent>(){
		private final int MIN_PAGE = 1;

		 public void handle(ActionEvent e) {
			 if (About.pageCounter > this.MIN_PAGE){
				 setPageCounter (-1);
			 }
			 About.switchPage();
		 }
	 };

	 // next page
	 private EventHandler<ActionEvent> nxtbtn1 = new EventHandler<ActionEvent>(){
		private final int MAX_PAGE = 4;
		 public void handle(ActionEvent e) {
			 if (About.pageCounter < this.MAX_PAGE){
				 setPageCounter(1);
			 }
			 About.switchPage();
		 }
	 };

	 // navigate through the pages
	 private static void switchPage(){
		 switch(About.pageCounter) {
		  case 1:
			System.out.println("Page 1");
			About.gc.drawImage(About.ABT1,0,0);
		    break;
		  case 2:
			System.out.println("Page 2");
			About.gc.drawImage(About.ABT2,0,0);
		    break;
		  case 3:
			System.out.println("Page 3");
			About.gc.drawImage(About.ABT3,0,0);
		    break;
		  case 4:
			System.out.println("Page 4");
			About.gc.drawImage(About.ABT4,0,0);
		    break;
		}
	 }

	 private void setPageCounter (int num){
		 About.pageCounter += num;
	 }
}