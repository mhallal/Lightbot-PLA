package lightcore.graphics;

import java.io.IOException;
import java.nio.file.Paths;

import lightcore.LightCore;
import lightcore.world.Robot;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class RobotDisplay implements DisplayPrimitive{
	
	private Robot robot;
	
	private int line;
	private int column;
	private int level;
	
	private int originX;
	private int originY;
	
	private int transparency;
	
	private final float robotHeight = 20;
	
	// The texture of the robot
	public Texture currentTexture;
	public Sprite robotSprite;
	
	/**
	 * Constructor
	 * @param robot
	 * @param transparency
	 */
	public RobotDisplay(Robot robot, int transparency, int originX, int originY){
		this.originX = originX;
		this.originY = originY;
		updateRobot(robot, transparency);
	}
	
	/**
	 * Update the robot with a new robot and a new transparency
	 * @param robot
	 * @param transparency
	 */
	public void updateRobot(Robot robot, int transparency){
		this.robot = robot;
		
		this.line = this.robot.getLine();
		this.column = this.robot.getColumn();
		this.level = GridDisplay.levelMax[this.line][this.column];
		
		this.transparency = transparency;
		
		//System.out.println("Line : " + line + ", column : " + column + ", level : " + level + " " + direction.toString());
		
		String colour = "";
		switch(this.robot.getColour()){
			case BLUE:
				colour = "blue";
				break;
			case ORANGE:
				colour = "orange";
				break;
			case PURPLE:
				colour = "purple";
				break;
			case RED:
				colour = "red";
				break;
			default:
				colour = "white";
				break;
		}
		
		String direction = this.robot.getDirection().toString().toLowerCase();
		
		String path = "ressources/robot/" + colour + "_" + direction + ".png";
		
		currentTexture = new Texture();
		try {
			currentTexture.loadFromFile(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentTexture.setSmooth(true);
		
		this.robotSprite = create();
	}
	
	public void print(){
		LightCore.window.draw(robotSprite);
	}
	
	/**
	 * Initialize a robot from the data given at the instantiation
	 * @return A sprite of this robot
	 */
	public Sprite create(){
		Sprite robot = new Sprite(currentTexture);
		robot.setTextureRect(new IntRect(0, 0, 38, 50));
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		float sizeCubeY = Textures.cubeTextureWhite.getSize().y;
		
		float decalXRobot = (Textures.cellTexture.getSize().x - 38)/2;
		
		Vector2f decal;
		if(this.level == 0){
			if(this.line == this.column)
				decal = new Vector2f(-1+decalXRobot, this.line*2*(decalY-1)-this.robotHeight);
			else
				decal = new Vector2f((this.column-this.line)*(decalX-1)+decalXRobot, (this.line+this.column)*(decalY-1)-this.robotHeight);
		}
		else{
			if(this.line == this.column)
				decal = new Vector2f(-1+decalXRobot, (this.line+1)*2*(decalY-1)-(sizeCubeY-2)-(this.level-1)*decalY-this.robotHeight);
			else
				decal = new Vector2f((this.column-this.line)*(decalX-1)+decalXRobot, (this.line+this.column+2)*(decalY-1)-(sizeCubeY-2)-(this.level-1)*decalY-this.robotHeight);
		}

		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTextureWhite.getSize()), 2);
		
		robot.scale(new Vector2f(1, 1));
		robot.setOrigin(Vector2f.sub(origin, decal));
		robot.setColor(new Color(255, 255, 255, this.transparency));
		robot.setPosition(originX, originY);
		return robot;
	}
	
	public Sprite getSprite(){return this.robotSprite;}
}
