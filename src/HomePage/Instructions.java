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

public class Instructions {
	// declaration of the attributes
	private Canvas canvas;
	private static GraphicsContext gc;
	private Scene scene;
	private Stage stage;
	private StackPane pane;

	private static int pageCounter = 1;

	// customized icon and cursor
	private final Image ICON = new Image ("images/icon.png",500,500,false,false);
	private final Image CURSOR = new Image ("images/cursor.png",500,500,false,false);

	// the instructions made were just pictures
	private final static Image INS1 = new Image("images/insbg1.jpg",800,500,false,false);
	private final static Image INS2 = new Image("images/insbg2.jpg",800,500,false,false);
	private final static Image INS3 = new Image("images/insbg3.jpg",800,500,false,false);
	private final static Image INS4 = new Image("images/insbg4.jpg",800,500,false,false);
	private final static Image INS5 = new Image("images/insbg5.jpg",800,500,false,false);

	private static Image bg = Instructions.INS1; // the main background will be the first page of the scene

	// constructor
	Instructions (){
		this.pane = new StackPane();
		this.scene = new Scene(pane, SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);

		this.canvas = new Canvas(SceneController.WINDOW_WIDTH,SceneController.WINDOW_HEIGHT);
		Instructions.gc = canvas.getGraphicsContext2D();

		this.setProperties();
	}

	private void setProperties(){
		Instructions.gc.drawImage(Instructions.bg,0,0);

		// necessary buttons: home button (go back to scene controller), back, and next buttons
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

		nxtbtn.setTranslateY(140);
		bckbtn.setTranslateY(140);
		homebtn.setTranslateY(140);

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
		 public void handle(ActionEvent e)
		 {
			SceneController.mediaPlayer.setMute(true);
			SceneController scenecontroller = new SceneController();
			scenecontroller.setStage(stage);
		 }
	 };

	 // go to previous page
	 private EventHandler<ActionEvent> back1 = new EventHandler<ActionEvent>(){
		private final int MIN_PAGE = 1;

		public void handle(ActionEvent e) {
			if (Instructions.pageCounter > this.MIN_PAGE){
				setPageCounter (-1);
			}
			Instructions.switchPage();
		}
	 };

	 // go to next page
	 private EventHandler<ActionEvent> nxtbtn1 = new EventHandler<ActionEvent>(){
		private final int MAX_PAGE = 5;

		 public void handle(ActionEvent e) {
			 if (Instructions.pageCounter < this.MAX_PAGE){
				 setPageCounter (1);
			 }
			 Instructions.switchPage();
		 }
	 };

	 // navigate through the pages
	 private static void switchPage(){
		 switch(Instructions.pageCounter) {
		  case 1:
			System.out.println("Page 1");
			Instructions.gc.drawImage(Instructions.INS1,0,0);
		    break;
		  case 2:
			System.out.println("Page 2");
			Instructions.gc.drawImage(Instructions.INS2,0,0);
		    break;
		  case 3:
			System.out.println("Page 3");
			Instructions.gc.drawImage(Instructions.INS3,0,0);
		    break;
		  case 4:
			System.out.println("Page 4");
			Instructions.gc.drawImage(Instructions.INS4,0,0);
		    break;
		  case 5:
			System.out.println("Page 5");
			Instructions.gc.drawImage(Instructions.INS5,0,0);
		    break;
		}
	 }

	private void setPageCounter (int num){
		 Instructions.pageCounter += num;
	 }

}
