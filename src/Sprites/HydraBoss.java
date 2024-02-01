package Sprites;

import java.util.Random;

import InGame.PlayStage;
import javafx.scene.image.Image;


public class HydraBoss extends Sprite {
	public final int MAX_HYDRA_SPEED = 5;
	public final int MIN_HYDRA_SPEED = 1;
	public final static int HYDRA_WIDTH=150;
	public final Image HYDRA_IMAGE = new Image("images/bossFish.png",HydraBoss.HYDRA_WIDTH,HydraBoss.HYDRA_WIDTH,false,false);
	private boolean alive;
	//attribute that will determine if a fish will initially move to the right
	private boolean moveRight;
	private int speed;
	private int health = 3000;


	public HydraBoss(int x, int y) {
		super(x,y);
		this.alive = true;
		this.loadImage(this.HYDRA_IMAGE);

		Random r = new Random();
		this.speed = r.nextInt(this.MAX_HYDRA_SPEED-this.MIN_HYDRA_SPEED+1) + this.MIN_HYDRA_SPEED;

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
		if (this.moveRight == true && this.x !=	 PlayStage.WINDOW_WIDTH-TidBit.TIDBIT_WIDTH)
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

	//setter
	public boolean isAlive() {
		return this.alive;
	}

	public void setAlive (boolean status){
		this.alive = status;
	}

	public void setBossHealth(int num)
	{
		this.health += num;
		System.out.print("Boss Health: ");
		System.out.println(this.health);
	}

	//getters
	public int getBossHealth()
	{
		return this.health;
	}

}

