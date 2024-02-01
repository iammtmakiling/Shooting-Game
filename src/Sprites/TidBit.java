package Sprites;

import java.util.Random;

import InGame.PlayStage;
import javafx.scene.image.Image;


public class TidBit extends Sprite {
	public final int MAX_TIDBIT_SPEED = 5;
	public final int MIN_TIDBIT_SPEED = 1;
	public final Image TIDBIT_IMAGE = new Image("images/fish.png",TidBit.TIDBIT_WIDTH,TidBit.TIDBIT_WIDTH,false,false);
	public final static int TIDBIT_WIDTH=40;
	private boolean alive;
	//attribute that will determine if a fish will initially move to the right
	private boolean moveRight;
	private int speed;


	public TidBit(int x, int y){
		super(x,y);
		this.alive = true;
		this.loadImage(this.TIDBIT_IMAGE);

		Random r = new Random();
		this.speed = this.MIN_TIDBIT_SPEED + r.nextInt(this.MAX_TIDBIT_SPEED);

		// random boolean value
		this.moveRight = r.nextBoolean();
	}

	//method that changes the x position of the fish
	public void move(){
		/*
		 * TODO: 				If moveRight is true and if the fish hasn't reached the right boundary yet,
		 *    						move the fish to the right by changing the x position of the fish depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the fish hasn't reached the left boundary yet,
		 * 	 						move the fish to the left by changing the x position of the fish depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */
		if (this.moveRight == true && this.x != PlayStage.WINDOW_WIDTH-TidBit.TIDBIT_WIDTH)
		{
			this.x += this.speed;
			if (this.x >= PlayStage.WINDOW_WIDTH-TidBit.TIDBIT_WIDTH){
				this.moveRight = false;
			}
		}
		if(this.moveRight == false && this.x > 0)
		{
			this.x -= this.speed;
			if (this.x <= 0){
				this.moveRight = true;
			}
		}
	}

	//getter
	public boolean isAlive() {
		return this.alive;
	}

	// setter
	public void setAlive (boolean status){
		this.alive = status;
	}
}
