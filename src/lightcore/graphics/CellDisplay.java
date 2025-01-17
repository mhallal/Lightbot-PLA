package lightcore.graphics;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

public class CellDisplay {
	
	/**
	 * Create a sprite of a cell at the given coordinates
	 * @param line The cell's line
	 * @param column The cell's column
	 * @return A sprite of the cell
	 */
	public static Sprite createCell(int line, int column){
		Sprite toAdd = new Sprite(Textures.cellTexture);
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		
		Vector2f decal;
		if(line == column)
			decal = new Vector2f(0, 
					line*2*(decalY) 
						+ (Math.round(Textures.cubeTextureWhite.getSize().y) 
						- (Math.round(Textures.cellTexture.getSize().y))));
		else
			decal = new Vector2f((column-line)*(decalX),
					(line+column)*(decalY)
					+ (Math.round(Textures.cubeTextureWhite.getSize().y) 
					- (Math.round(Textures.cellTexture.getSize().y))));
		
		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTextureWhite.getSize()), 2);
		
		toAdd.scale(new Vector2f(1, 1));
		toAdd.setOrigin(Vector2f.sub(origin, decal));
		return toAdd;
	}
	
}
