package InGame;
import java.util.ArrayList;
import java.util.Random;

import HomePage.SceneController;
import Sprites.Bullet;
import Sprites.Comet;
import Sprites.DwarfPlanet;
import Sprites.TidBit;
import Sprites.HydraBoss;
import Sprites.Ship;
import Sprites.Sprite;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer implements Runnable{
	// attributes
	private GraphicsContext gc;
	private Scene theScene;
	private Ship myShip;
	private static int GameCounter = 60; // countdown from 60 seconds (if 0, then the player won the game)

	// arraylists for the sprites:
		// enemy arraylist
		// power up array list
	private static ArrayList<Sprite> powerUpsList;
	private static ArrayList<Sprite> enemyList;
	private static ArrayList<Bullet> bList;

	protected PlayStage playstage;

	private static int shipImmortalityCount = 3; // 3-second immortality when ship collided with comet
	private static int dwarfPlanetDespawnCount = 5; // despawning the dwarf planet after 5 seconds

	private static int score = 0; // represents the number of fish killed
	private static boolean gameStatus = true;

	private final Image bg = new Image("images/background2.png",800,500,false,false);

	// constructor
	GameTimer(GraphicsContext gc, Scene theScene, PlayStage playstage){
		this.playstage = playstage;
		this.gc = gc;
		this.theScene = theScene;
		this.myShip = new Ship("Spaceship", 150, 250);

		GameTimer.powerUpsList = new ArrayList<Sprite>();
		GameTimer.enemyList =  new ArrayList<Sprite>();
		GameTimer.bList = this.myShip.getBullets();

		//Spawn the first Enemies
		GameTimer.spawnSprites("tidbit", 7);

		//Call method to handle mouse click event
		this.handleKeyPressEvent();
	}

	private void statusBar() {
		// appears above the screen

		//set font type, style, size, and color
		Font theFont = Font.font("Times New Roman",FontWeight.BOLD,20);
		this.gc.setFont(theFont);
		this.gc.setFill(Color.BLACK);

		//Score Checker
		this.gc.fillText("Score: ", PlayStage.WINDOW_WIDTH*0.80, PlayStage.WINDOW_HEIGHT*0.10);
		this.gc.fillText(String.valueOf(GameTimer.score), PlayStage.WINDOW_WIDTH*0.87,PlayStage.WINDOW_HEIGHT*0.10);

		//Invinsibility Status Checker
		this.gc.fillText("Invinsibility Status: ", PlayStage.WINDOW_WIDTH*0.45, PlayStage.WINDOW_HEIGHT*0.10);
		if (Ship.getImmortality() == true){
			this.gc.fillText("Active", PlayStage.WINDOW_WIDTH*0.67,PlayStage.WINDOW_HEIGHT*0.10);
		}else{
			this.gc.fillText("Inactive", PlayStage.WINDOW_WIDTH*0.67,PlayStage.WINDOW_HEIGHT*0.10);
		}

		//Strength Status Checker
		this.gc.fillText("Ship Strength: ", PlayStage.WINDOW_WIDTH*0.05, PlayStage.WINDOW_HEIGHT*0.10);
		this.gc.fillText(String.valueOf(Ship.getStrength()), PlayStage.WINDOW_WIDTH*0.22,PlayStage.WINDOW_HEIGHT*0.10);

		//Time
		this.gc.fillText("Time: ", PlayStage.WINDOW_WIDTH*0.30, PlayStage.WINDOW_HEIGHT*0.10);
		this.gc.fillText(String.valueOf(GameTimer.GameCounter), PlayStage.WINDOW_WIDTH*0.37,PlayStage.WINDOW_HEIGHT*0.10);
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, PlayStage.WINDOW_WIDTH,PlayStage.WINDOW_HEIGHT);

		this.moveSprite();

		//Constant Checker if powerUp is Used
		GameTimer.despawnDwarfPlanet();

		PlayStage.gc.drawImage(this.bg,0,0);
		this.renderSprites();
		this.statusBar();

		this.isShipAlive();

		if (GameTimer.GameCounter == 0 && GameTimer.gameStatus == true){ // finished the game
			this.stop();
			SceneController.mediaPlayer.setMute(true);
			System.out.println("Win"); // player wins
			playstage.setGameOver();
		}

		if (GameTimer.gameStatus == false){ // player loses
			this.stop();
			System.out.println("Lose");
			playstage.setGameOver();
		}
	}

	@Override
	public void run() {
		while (GameTimer.GameCounter != 0 && GameTimer.gameStatus != false) {
			try{
				//Constant Spawning of the Sprites
				GameTimer.spawning();

				//Updates Immortality Status to False after 3 seconds of Collision with Comet
				if (GameTimer.shipImmortalityCount == 0){
					Ship.setImmortality(false);
					GameTimer.setShipImmortalityCounter(3); // resets immortality counter
					this.myShip.loadImage(Ship.SHIP_IMAGE); // change ship image back to its original image
				}

				if (Ship.getImmortality()){ // if the ship is still immortal
					GameTimer.setShipImmortalityCounter(-1); // decrement the immortality
				}

				//Despawn unused dwarfplanet after 5 seconds from spawning
				if (GameTimer.dwarfPlanetDespawnCount == 0){
					DwarfPlanet.setIsPresent(false);
					DwarfPlanet.setIsNotUsed(false);
					GameTimer.despawnDwarfPlanet();
					GameTimer.setDwarfDespawnCount(5); // resets despawn counter
				}

				if (DwarfPlanet.getIsNotUsed() && DwarfPlanet.getIsPresent()){
					GameTimer.setDwarfDespawnCount(-1);
				}


				Thread.sleep(1000); // sleeps for 1 second
				GameTimer.setGameCounter(-1); // decrements game counter after 1 second
			} catch (InterruptedException e){
				e.printStackTrace();
			}

		}
	}

	//Methods that will Render the Sprites ----------------------------
	private void renderSprites() {
		// render all enemies (tidbits and hydra)
		for (Sprite s : GameTimer.enemyList){
			s.render(this.gc);
		}

		// render all powerups (comet and dwarfplanet)
		for (Sprite p : GameTimer.powerUpsList){
			p.render(this.gc);
		}

		// render bullets
		for (Bullet b : this.myShip.getBullets()){
			b.render(this.gc);
		}

		this.myShip.render(this.gc);
	}


	private static void spawnSprites(String sprite, int num){
		Random r = new Random();
		int upperBound = 50;
		int lowerBound = PlayStage.WINDOW_HEIGHT;
		switch (sprite){
			case "tidbit":
		        for(int i=0;i<num;i++){
		        	int x = PlayStage.WINDOW_WIDTH;
		        	int y = (int) (Math.random() * (upperBound - (lowerBound-TidBit.TIDBIT_WIDTH-10)) + (lowerBound-TidBit.TIDBIT_WIDTH-10));
		            Sprite newSprite = new TidBit(x, y);
		            GameTimer.enemyList.add(newSprite);
		        }
		        break;

			case "hydra":
				int x =  PlayStage.WINDOW_WIDTH-HydraBoss.HYDRA_WIDTH;
				int y = (int) (Math.random() * (upperBound - (lowerBound-HydraBoss.HYDRA_WIDTH)) + (lowerBound-HydraBoss.HYDRA_WIDTH));

				// there will no be spawning of hydraboss until the current one is killed
				int hydraCount = 0; // will count the number of hydra boss in the arraylist
				for (Sprite s : enemyList) {
					if (s instanceof HydraBoss) { // loops through the arraylist and check if there is a hydra
						hydraCount++; // if so, update the counter
						break;
					}
				}

				if (hydraCount == 0) { // there are no other instances of hydra
					Sprite newSprite = new HydraBoss(x, y);
					GameTimer.enemyList.add(newSprite);
				}
				break;

			case "comet":
		        for(int i=0;i<num;i++){
		            int x1 = PlayStage.WINDOW_WIDTH;
		            int y1 = (int) (Math.random() * (upperBound - (lowerBound-Comet.COMET_HEIGHT-10)) + (lowerBound-Comet.COMET_HEIGHT-10));
		            Sprite newPowerUp = new Comet(x1, y1);
		            powerUpsList.add(newPowerUp);
		        }
		        break;

			case "dwarfplanet":
		        for(int i=0;i<num;i++){
		            int x1 =  r.nextInt(PlayStage.WINDOW_WIDTH-DwarfPlanet.DWARFPLANET_WIDTH);
		            int y1 = (int) (Math.random() * (upperBound - (lowerBound-DwarfPlanet.DWARFPLANET_WIDTH)) + (lowerBound-DwarfPlanet.DWARFPLANET_WIDTH));
		            Sprite newPowerUp = new DwarfPlanet(x1, y1);
		            powerUpsList.add(newPowerUp);
		        }
		        break;
		}
	}

	//-----------------------------------------------------------------------
	//Method that will spawn/instantiate SPRITES at a random x,y location
	private static void spawning(){
		if (GameTimer.getGameCounter() < 60){
			if ((GameTimer.getGameCounter() % 30) == 0){ // Every 30 Seconds
				GameTimer.spawnSprites("hydra", 1);
			}
			if ((GameTimer.getGameCounter() % 5) == 0){ // Every 5 Seconds
				GameTimer.spawnSprites("tidbit", 3);
			}
			if ((GameTimer.getGameCounter() % 10) == 0){ // Every 10 Seconds
				GameTimer.spawnSprites("comet", 1);
				GameTimer.spawnSprites("dwarfplanet", 1);
			}
		}
	}

	//--------------------------------------------------------------------------------------


	//Method that will move the SPRITES -----------------------------------------------------
	private void moveSprite(){

		// MOVEMENT:
		// Ship
		this.myShip.move();

		// TidBits and Hydra
		for(int i = 0; i < GameTimer.enemyList.size(); i++) {
			Sprite s = GameTimer.enemyList.get(i);
			if (s.isAlive())
				s.move();
			else
				enemyList.remove(s);
		}

		// COLLISION
		// bullet
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			if (b.getVisible() && b.getX() != PlayStage.WINDOW_WIDTH) // move if visible
				b.move();
			else
				bList.remove(b); // else, remove

			// check bullet collision with the enemies
			for (Sprite s : enemyList){
				// TIDBIT
				if (s instanceof TidBit) {
					TidBit t = (TidBit) s;

					if (b.collidesWith(t)){ // if bullet collides with the tidbit
						t.setAlive(false); // tidbit dies
						b.setVisible(false); // tidbit will not be visible on the screen
						setScore(1); // we set tidbit kill to 1
					}
				}

				// HYDRA
				if (s instanceof HydraBoss) {
					HydraBoss hB = (HydraBoss) s;

					if (b.collidesWith(hB)){ // if bullet collides with hydra
						hB.setBossHealth(-Ship.getStrength()); // decrement by the strength of the ship
						if (hB.getBossHealth() <= 0){ // if health of hydra is less than 0
							hB.setAlive(false); // hydra dies
							setScore(5); // we set hydra kill to 5
						}
						b.setVisible(false); // tidbit will not be visible on the screen
					}
				}
			}

		}


		// Check if Comet is Used
		for(int i = 0; i < GameTimer.powerUpsList.size(); i++){
			Sprite p = GameTimer.powerUpsList.get(i);
			if (p instanceof Comet) {
				Comet c = (Comet) p;
				if (Comet.getIsNotUsed()) // if comet is not used
					c.move(); // move comet
				else
					powerUpsList.remove(c);

				// if comet touched the leftmost part of the screen
				if (c.getX() < 0){
					powerUpsList.remove(c);
				}
			}
		}

	}

	//------------------------------------------------------------------------------------------

	//Removes Dwarf Planet from the Canvas
	public static void despawnDwarfPlanet(){
		for(int i = 0; i < GameTimer.powerUpsList.size(); i++){
			Sprite p = GameTimer.powerUpsList.get(i);
			if (p instanceof DwarfPlanet) {
				DwarfPlanet d = (DwarfPlanet) p;
				if (!DwarfPlanet.getIsNotUsed()) {
					powerUpsList.remove(d);
				}
			}
		}
	}


	//Method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyShip(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                stopMyShip(code);
            }
        });
    }

	//method that will move the ship depending on the key pressed
	private void moveMyShip(KeyCode ke) {
		if(ke==KeyCode.W) this.myShip.setDY(-10);

		if(ke==KeyCode.A) this.myShip.setDX(-10);

		if(ke==KeyCode.S) this.myShip.setDY(10);

		if(ke==KeyCode.D) this.myShip.setDX(10);

		if(ke==KeyCode.SPACE) this.myShip.shoot();

		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyShip(KeyCode ke){
		this.myShip.setDX(0);
		this.myShip.setDY(0);
	}

	private void isShipAlive(){
		if (Ship.getStrength() <= 0){
			GameTimer.gameStatus = false;
		}
	}

	//Setters ------------------------------------------------------------------
	private void setScore (int score){
		GameTimer.score += score;
	}

	public static void setGameCounter (int num){
		GameTimer.GameCounter += num;
	}

	private static void setDwarfDespawnCount(int num) {
		GameTimer.dwarfPlanetDespawnCount += num;
	}

	private static void setShipImmortalityCounter(int num) {
		GameTimer.shipImmortalityCount += num;
	}

	//--------------------------------------------------------------------------

	//Getters ------------------------------------------------------------------
	public static int getScore (){
		return GameTimer.score;
	}

	public static int getGameCounter(){
		return GameTimer.GameCounter;
	}

	static boolean getGameStatus() {
		return GameTimer.gameStatus;
	}

	public static ArrayList<Sprite> getEnemiesArrayList() {
		return GameTimer.enemyList;
	}

	public static ArrayList<Sprite> getPowerUpsArrayList() {
		return GameTimer.powerUpsList;
	}

}