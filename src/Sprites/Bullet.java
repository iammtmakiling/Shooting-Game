package Sprites;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	public final int BULLET_SPEED = 20;
	public final Image BULLET_IMAGE = new Image("images/bullet.png",this.BULLET_WIDTH,this.BULLET_HEIGHT,false,false);
	public final int BULLET_WIDTH = 17;
	public final int BULLET_HEIGHT = 10;

	public Bullet(int x, int y){
		super(x,y);
		this.loadImage(this.BULLET_IMAGE);
	}


	//method that will move/change the x position of the bullet
	public void move(){
		x += this.BULLET_SPEED; // change the direction of the bullet (from left to right)
	}
}