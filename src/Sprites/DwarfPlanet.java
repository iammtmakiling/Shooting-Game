package Sprites;

import javafx.scene.image.Image;

public class DwarfPlanet extends Sprite {
	public final static int DWARFPLANET_WIDTH = 63;
	public final static int DWARFPLANET_HEIGHT = 40;
	public final Image DWARFPLANET_IMAGE = new Image("images/dwarfplanet.png",DwarfPlanet.DWARFPLANET_WIDTH,DwarfPlanet.DWARFPLANET_HEIGHT,false,false);
	private static boolean isNotUsed; // not used
	private static boolean isPresent;


	public DwarfPlanet(int xPos, int yPos) {
		super(xPos, yPos);
		DwarfPlanet.isNotUsed = true;
		DwarfPlanet.isPresent = true;
		this.loadImage(this.DWARFPLANET_IMAGE);
	}

	// Getters -----------------------------------------------------------
	public static boolean getIsNotUsed() {
		return DwarfPlanet.isNotUsed;
	}

	public static boolean getIsPresent() {
		return DwarfPlanet.isPresent;
	}

	// Setters -----------------------------------------------------------
	public static void setIsNotUsed(boolean status) {
		DwarfPlanet.isNotUsed = status;
	}

	public static void setIsPresent(boolean status) {
		DwarfPlanet.isPresent = status;
	}

}
