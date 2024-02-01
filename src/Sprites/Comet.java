package Sprites;

import InGame.PlayStage;

//import java.util.Random;

import javafx.scene.image.Image;

public class Comet extends Sprite {

	public final static int COMET_WIDTH = 120;
	public final static int COMET_HEIGHT = 40;
	public final Image COMET_IMAGE = new Image("images/comet.png",Comet.COMET_WIDTH,Comet.COMET_HEIGHT,false,false);
	private static boolean isNotUsed;
	private final double COMET_SPEED = 2.5;

	public Comet(int xPos, int yPos) {
		super(xPos, yPos);
		Comet.isNotUsed = true;
		this.dx = PlayStage.WINDOW_WIDTH;
		this.loadImage(this.COMET_IMAGE);
	}


	public void move(){
		x -= this.COMET_SPEED; // change the direction of the comet (from right to left)
	}


	// Getters -----------------------------------------------------------
	public static boolean getIsNotUsed() {
		return Comet.isNotUsed;
	}

	// Setters -----------------------------------------------------------
	public static void setIsNotUsed(boolean status){
		Comet.isNotUsed = status;
	}



}
