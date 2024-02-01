package Sprites;

import java.util.ArrayList;
import java.util.Random;

import InGame.GameTimer;
import javafx.scene.image.Image;



public class Ship extends Sprite{
	// attributes
	private String name;
	private static int strength;
	private static boolean alive;
	private static boolean immortal = false;

	private Sprite enemySprite;
	private Sprite powerUpSprite;

	private ArrayList<Bullet> bullets;
	public final static Image SHIP_IMAGE = new Image("images/ship.png",Ship.SHIP_WIDTH,Ship.SHIP_WIDTH,false,false);
	public final static Image SHIP_IMAGE2 = new Image("images/ship2.png",Ship.SHIP_WIDTH,Ship.SHIP_WIDTH2,false,false);
	private final static int SHIP_WIDTH = 50;
	private final static int SHIP_WIDTH2 = 70;

	// constructor
	public Ship(String name, int x, int y) {
		super(x,y);
		this.name = name;
		Random r = new Random();
		Ship.strength = 100 + r.nextInt(51);
		Ship.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Ship.SHIP_IMAGE);
	}

	//method called if spacebar is pressed
    public void shoot(){
        //compute for the x and y initial position of the bullet
        int x = (int) (this.x + this.width+20);
        int y = (int) (this.y + this.height/2);
        /*
         * TODO: Instantiate a new bullet and add it to the bullets arraylist of ship
         */
        Bullet newBullet = new Bullet(x, y);
        bullets.add(newBullet);
    }


	//Method called if up/down/left/right arrow key is pressed.
	public void move() {
		this.x += this.dx;
		this.y += this.dy;

		//If Ship collides with fish
		//Strength will decrease by ___
		//Set the status of fish being alive as false
		for (Sprite s : GameTimer.getEnemiesArrayList()){
			int shipStrengthDecrement = 0;
			if (s instanceof TidBit) { // if s is of TidBit type
				this.enemySprite = (TidBit) s; // typecast to tidbit
				shipStrengthDecrement = 30;
			}
			else {
				this.enemySprite = (HydraBoss) s; // typecast to hydra
				shipStrengthDecrement = 50;
			}

			if (this.enemySprite.collidesWith(this)) {
				if (Ship.immortal == false){
					Ship.setStrength(-shipStrengthDecrement);
				}
				this.enemySprite.setAlive(false);
				System.out.println("Strength updates: ");
				System.out.println(strength);
			}
		}

		for (Sprite p: GameTimer.getPowerUpsArrayList()){
			if (p instanceof Comet)
				this.powerUpSprite = (Comet) p;
			else
				this.powerUpSprite = (DwarfPlanet) p;

			//If Ship collides with Comet
			//Immortality for 3 seconds
			if (this.powerUpSprite instanceof Comet && this.powerUpSprite.collidesWith(this)) {
				this.loadImage(Ship.SHIP_IMAGE2);
				Ship.setImmortality(true);
				Comet.setIsNotUsed(false);
			}

			//If Ship collides with dwarfPlanet
			//Strength Increases by 50
			if (this.powerUpSprite instanceof DwarfPlanet && this.powerUpSprite.collidesWith(this)){
				Ship.setStrength(50);
				DwarfPlanet.setIsPresent(false);
				DwarfPlanet.setIsNotUsed(false);
			}
		}
	}

	// Setters -----------------------------------------------------------
	private static void setStrength(int num){
		Ship.strength += num;
		System.out.println(strength);
	}

	public static void setImmortality(boolean status){
		Ship.immortal = status;
		System.out.print("immortality status: ");
		System.out.println(immortal);
	}

	// Getters -----------------------------------------------------------
	public boolean getAlive(){
		return Ship.alive;
	}

	public String getName(){
		return this.name;
	}

	public static Boolean getImmortality(){
		return Ship.immortal;
	}

	public static int getStrength(){
		return Ship.strength;
	}

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

}
