package lightcore.graphics;

import org.jsfml.graphics.Sprite;

import lightcore.simulator.Colour;
import lightcore.simulator.action.Turn;
import lightcore.world.Grid;
import lightcore.world.RelativeDirection;
import lightcore.world.Robot;

public class Display {
	
	private Grid grid;
	private int size;
	
	public GridDisplay gridDisplay;
	public RobotDisplay robotDisplay;
	public RobotDisplay cloneDisplay;
	
	private int robotTransparency = 255;
	private int cloneTransparency = 150;
	
	public Animation anim;
	
	public boolean robotIsDisplayed = false;
	
	
	/********************************************************************************************/
	/*										Constructors										*/
	/********************************************************************************************/
	
	/**
	 * Create a Display object in order to play on a grid
	 * @param grid The grid to play on
	 * @param originX
	 * @param originY
	 */
	public Display(Grid grid, int originX, int originY){
		this.grid = grid;
		this.robotIsDisplayed = true;
		
		this.size = this.grid.getSize();
		
		this.gridDisplay = new GridDisplay(grid, originX, originY);
		this.gridDisplay.initGrid();
		this.robotDisplay = new RobotDisplay(Robot.wheatley, robotTransparency, originX, originY);
		this.cloneDisplay = new RobotDisplay(Robot.wheatleyClone, cloneTransparency, originX, originY);
		
		anim = new Animation(gridDisplay.getGridSprites(), robotDisplay.robotSprite);
		anim.updateClone(cloneDisplay.robotSprite);
	}
	
	/**
	 * Create a Display object in order to use the Editor
	 * @param line The number of line of the editor
	 * @param column The number of column of the editor
	 * @param originX
	 * @param originY
	 */
	public Display(int size, int originX, int originY){
		this.robotIsDisplayed = false;
		this.size = size;
		
		gridDisplay = new GridDisplay(size, originX, originY);
		anim = new Animation(gridDisplay.getGridSprites());
	}
	
	public void reinit(Grid grid, int originX, int originY){
		this.grid = grid;
		this.size = grid.getSize();
		gridDisplay.reinit(this.grid, originX, originY);
		
		if(!robotIsDisplayed)
			robotIsDisplayed = true;
		robotTransparency = 150;
		this.robotDisplay = new RobotDisplay(Robot.wheatley, robotTransparency, originX, originY);
		
		anim = new Animation(gridDisplay.getGridSprites(), robotDisplay.robotSprite);
	}
	
	public void displayRobot(int line, int column, int originX, int originY){
		if(!robotIsDisplayed)
			robotIsDisplayed = true;
		Robot.wheatley.setPosition(line, column);
		robotTransparency = 150;
		this.robotDisplay = new RobotDisplay(Robot.wheatley, robotTransparency, originX, originY);
		anim.updateRobot(robotDisplay.robotSprite);
	}
	
	public void deleteRobot(){
		if(robotIsDisplayed){
			robotIsDisplayed = false;
			anim.updateRobot(null);
		}
	}
	
	
	/********************************************************************************************/
	/*										Accessors											*/
	/********************************************************************************************/
	
	public GridDisplay getGridDisplay(){return this.gridDisplay;}
	
	
	/********************************************************************************************/
	/*									Printing procedures										*/
	/********************************************************************************************/
	
	/**
	 * Print the grid, and if it's a game, 
	 * print the robot if it has to be displayed
	 */
	public void print(){
		for(int l=0; l<size; l++)
			for(int c=0; c<size; c++){
				gridDisplay.printPillar(l, c);
				if(robotIsDisplayed && Robot.wheatley.getLine() == l && Robot.wheatley.getColumn() == c)
					robotDisplay.print();
				if(robotIsDisplayed && Robot.wheatleyClone.getVisibility() && Robot.wheatleyClone.getLine() == l && Robot.wheatleyClone.getColumn() == c)
					cloneDisplay.print();
			}
	}
	
	/**
	 * Animation of the grid's construction
	 */
	public void createGridAnim(){		
		Sprite[][][] grid = gridDisplay.getGridSprites();
		for(int l = 0; l < grid.length; l++){
			for(int c = 0; c < grid[0].length; c++){
				for(int level = 0; level < 50; level++){
					if(l == Robot.wheatley.getLine() && c == Robot.wheatley.getColumn() && level == GridDisplay.levelMax[l][c]){
						anim.addRemoveCube(l, c, level, true, true);
						anim.displayRobot(l, c, level, true, false);
					}
					else{
						if(grid[l][c][level] != null)
							anim.addRemoveCube(l, c, level, true, true);
					}
				}
			}
		}
	}
	
	/**
	 * Animation of the grid's destruction 
	 */
	public void deleteGridAnim(){
		Sprite[][][] grid = gridDisplay.getGridSprites();
		for(int l = grid.length-1; l >= 0; l--)
			for(int c = grid[0].length-1; c >= 0; c--)
				for(int level = 49; level >= 0; level--)
					if(grid[l][c][level] != null)
						anim.addRemoveCube(l, c, level, false, true);
	}
	
	
	/********************************************************************************************/
	/*									Actions on the grid										*/
	/********************************************************************************************/
	
	/**
	 * Rotation of the grid and the robot
	 * @param way
	 */
	public void rotate(int way){
		if(way == 0){
			gridDisplay.rotateLeft();
			
			if(robotIsDisplayed){
				int previousPosX = Robot.wheatley.getLine();
				Robot.wheatley.setLine(size-Robot.wheatley.getColumn()-1);
				Robot.wheatley.setColumn(previousPosX);
				
				previousPosX = Robot.wheatleyClone.getLine();
				Robot.wheatleyClone.setLine(size-Robot.wheatleyClone.getColumn()-1);
				Robot.wheatleyClone.setColumn(previousPosX);
				
				Turn turn = new Turn(RelativeDirection.LEFT, Colour.WHITE);
				turn.execute(null, Robot.wheatley);
				robotDisplay.updateRobot(Robot.wheatley, robotTransparency);
				
				if(Robot.wheatleyClone.getVisibility()){
					turn.execute(null, Robot.wheatleyClone);
					cloneDisplay.updateRobot(Robot.wheatleyClone, cloneTransparency);
				}
			}
		}
		else{
			gridDisplay.rotateRight();
			
			if(robotIsDisplayed){
				int previousPosX = Robot.wheatley.getLine();
				Robot.wheatley.setLine(Robot.wheatley.getColumn());
				Robot.wheatley.setColumn(size-previousPosX-1);
				
				previousPosX = Robot.wheatleyClone.getLine();
				Robot.wheatleyClone.setLine(Robot.wheatleyClone.getColumn());
				Robot.wheatleyClone.setColumn(size-previousPosX-1);
				
				Turn turn = new Turn(RelativeDirection.RIGHT, Colour.WHITE);
				turn.execute(null, Robot.wheatley);
				robotDisplay.updateRobot(Robot.wheatley, robotTransparency);
				
				if(Robot.wheatleyClone.getVisibility()){
					turn.execute(null, Robot.wheatleyClone);
					cloneDisplay.updateRobot(Robot.wheatleyClone, cloneTransparency);
				}
			}
		}
		if(robotIsDisplayed){
			anim.updateSprite(gridDisplay.getGridSprites(), robotDisplay.getSprite());
			if(Robot.wheatleyClone.getVisibility())
				anim.updateClone(cloneDisplay.getSprite());
		}
		else
			anim.updateSprite(gridDisplay.getGridSprites());
	}
	
	public void rotateRobot(RelativeDirection direction){
		if(robotIsDisplayed){
			Turn turn = new Turn(direction, Colour.WHITE);
			turn.execute(null, Robot.wheatley);
			robotDisplay.updateRobot(Robot.wheatley, robotTransparency);
			anim.updateSprite(gridDisplay.getGridSprites(), robotDisplay.getSprite());
		}
	}
}
