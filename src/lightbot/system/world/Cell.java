package lightbot.system.world;

import lightbot.system.Colour;


public class Cell {
	
	private int height;
	private Colour colour;
	
	/**
	 * Default constructor for a Cell
	 */
	public Cell(){
		this.height = 1;
		this.colour = Colour.WHITE;
	}
	
	/**
	 * Constructor for a Cell with level and colour
	 * @param level
	 * @param col
	 */
	public Cell(int level, Colour colour){
		this.height = level;
		this.colour = colour;
	}
	
	public void setHeight(int level){
		this.height = level;
	}
	
	public void setColour(Colour colour){
		this.colour = colour;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public Colour getColour(){
		return this.colour;
	}
	
}


