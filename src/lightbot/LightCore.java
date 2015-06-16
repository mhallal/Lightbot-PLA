package lightbot;

import java.util.List;

import lightbot.graphics.DisplayMode;
import lightbot.graphics.Editor;
import lightbot.graphics.Game;
import lightbot.graphics.ActionListDisplay;
import lightbot.graphics.Textures;
import lightbot.system.ParserJSON;
import lightbot.system._Executable;
import lightbot.system.generator.WorldGenerator;
import lightbot.system.world.Grid;
import lightbot.system.world.Level;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.TextEvent;

public class LightCore {

	public static RenderWindow window;
	public static DisplayMode display;

	public static VideoMode screenInformations;
	public static float scaleRatio;

	public static int originX;
	public static int originY;

	public static void main(String[] args) {
		
		boolean firstLaunch = true;

		Textures.initTextures();

		// Create the window
		window = new RenderWindow();

		screenInformations = VideoMode.getDesktopMode();
		scaleRatio = ((float) screenInformations.width) / 1920;
		System.out.println(scaleRatio);

		// base 1280 * 960 for 1920*1080
		window.create(new VideoMode(1000, 600), "LightCore");

		// Limit the framerate
		window.setFramerateLimit(60);

		// WorldGenerator newWorld = new WorldGenerator();

		// newWorld.getGrid().printGrid();
		// Grid grid = newWorld.getGrid();
		Level level = ParserJSON.deserialize("example.json");
		Grid grid = level.getGrid();
		List<_Executable> actions = level.getListOfActions();

		// display = new Editor(5);
		display = new Game(grid);
		// display.printGrid();

		// RectangleShape rect = new RectangleShape(new Vector2f(750, 460));
		RectangleShape rect = new RectangleShape(new Vector2f(710, 475));
		rect.setPosition(15, 15);
		rect.setOutlineThickness(1);
		rect.setOutlineColor(Color.BLACK);

		// Main loop
		while (window.isOpen()) {
			if(firstLaunch) {
				firstLaunch = false;
			}
			// Draw everything
			window.clear(Color.WHITE);
			window.draw(rect);
			display.display();
			ActionListDisplay.displayActionList(actions, 10, window);

			// Handle events
			for (Event event : window.pollEvents()) {
				display.eventManager(event);
				switch (event.type) {
				case CLOSED:
					System.out.println("The user pressed the close button!");
					window.close();
					break;
				/*
				 * case TEXT_ENTERED: TextEvent textEvent = event.asTextEvent();
				 * System.out.println("The user typed the following character: "
				 * + textEvent.character); break;
				 * 
				 * case MOUSE_WHEEL_MOVED:
				 * System.out.println("The user moved the mouse wheel!"); break;
				 */
				
				default:
					break;
				}
			}

			window.display();
		}
	}
}