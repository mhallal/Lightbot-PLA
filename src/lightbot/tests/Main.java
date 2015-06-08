package lightbot.tests;

import lightbot.graphics.Display;
import lightbot.graphics.Editor;
import lightbot.graphics.Game;
import lightbot.graphics.Textures;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.TextEvent;

public class Main {
	
	public static RenderWindow window;
	public static Display display;

	public static void main(String[] args) {
		
		/***
		 * 
		 * TODO
		 * 
		 * use Editor button
		 */
		Textures.initTextures();		
		
		int[][] mat = {
			{3, 3, 1, 1},
			{1, 0, 1, 2},
			{1, 1, 0, 1},
			{1, 2, 4, 4}
		};
		
		//Create the window
		window = new RenderWindow();
		window.create(new VideoMode(640, 480), "Hello JSFML!");

		//Limit the framerate
		window.setFramerateLimit(30);

		//display = new Editor(9, 9, 320, 100);
		display = new Game(mat, 320, 200);
		display.printGrid();

		//Main loop
		while (window.isOpen()) {
		    //Draw everything
		    window.clear(Color.WHITE);
		    
		    display.display();
		    window.display();

		  //Handle events
		    for(Event event : window.pollEvents()) {
		    	display.eventManager(event);
		    	 switch(event.type) {
			         case CLOSED:
			             System.out.println("The user pressed the close button!");
			             window.close();
			             break;
			        	    
			         case TEXT_ENTERED:
			        	    TextEvent textEvent = event.asTextEvent();
			        	    System.out.println("The user typed the following character: " + textEvent.character);
			        	    break;
	
			         case MOUSE_WHEEL_MOVED:
			             System.out.println("The user moved the mouse wheel!");
			             break;
					default:
						break;
			     }
		    }
		}
	}
}