package lightbot.graphics;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import lightbot.system.Colour;
import lightbot.system.CardinalDirection;

public class RobotDisplay {
	
	private int line;
	private int column;
	private int level;
	private Colour colour;
	private CardinalDirection direction;
	
	private final float robotHeight = 40; 
	
	// The texture of the cube
	public Texture currentTexture;
	
	public RobotDisplay(int line, int column, int level, Colour colour, CardinalDirection direction){
		this.line = line;
		this.column = column;
		this.level = level;
		this.colour = colour;
		this.direction = direction;
		
		System.out.println("Line : " + line + ", column : " + column + ", level : " + level + " " + direction.toString());
		
		switch(this.direction){
			case EAST:
				currentTexture = Textures.robotEast;
				break;
			case NORTH:
				currentTexture = Textures.robotNorth;
				break;
			case SOUTH:
				currentTexture = Textures.robotSouth;
				break;
			case WEST:
				currentTexture = Textures.robotWest;
				break;
			default:
				currentTexture = Textures.robotNorth;
				break;
		}
	}
	
	/**
	 * Initialize a cube from the data given at the instantiation
	 * @return A sprite of this cube
	 */
	public Sprite createRobot(){
		Sprite robot = new Sprite(currentTexture);
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		float sizeCubeY = Textures.cubeTexture.getSize().y;
		
		float decalXRobot = (Textures.cellTexture.getSize().x - this.currentTexture.getSize().x)/2;
		
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

		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTexture.getSize()), 2);
		
		robot.scale(new Vector2f((float)0.5, (float)0.5));
		robot.setOrigin(Vector2f.sub(origin, decal));
		return robot;
	}
}