package rogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import rogue.*;
public class PlayScreen implements Screen {
    private World world;
    private int screenWidth;
    private int screenHeight;
    private SavedGame savedGame;
    private Creature player;
    private Factory factory;
    private List<String> messages;
	private FieldOfView fov;
	private Screen subscreen;
	public PlayScreen(){
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
        factory = new Factory(world);
        messages = new ArrayList<String>();
        player = factory.newPlayer(messages);
		fov = new FieldOfView(world);
        populateWorld();
		createItems(factory);
    }
    public PlayScreen(String path) {
        factory = new Factory(world);
        messages = new ArrayList<String>();
    	savedGame = new SavedGame(null);
		try {
			world = savedGame.loadGame(path, messages, factory);
		} catch (IOException e) {
			e.printStackTrace();
		}
        screenWidth = 80;
        screenHeight = 21;
        player = world.getCreature().get(0);
        fov = new FieldOfView(world);
    }
    private void populateWorld(){
        for(int i=0; i<100; i++) {
        	factory.newPlant();
        }
        for(int i=0; i<50; i++) {
        	factory.newMole();
        }
    }
    private void displayMessages(AsciiPanel terminal, List<String> messages) {
    	int top = screenHeight - messages.size();
    	for(int i=0; i<messages.size(); i++) {
    		terminal.writeCenter(messages.get(i), top+i);
    	}
    	messages.clear();
    }
    private void createWorld(){
    	int width = 200;
    	int height = 200;
        world = new WorldBuilder(width, height).makeCaves().build();
        savedGame = new SavedGame(world);
    }
    private void displayTiles(AsciiPanel terminal, int left, int top) {
		fov.update(player.x, player.y, player.visionRadius());
    	for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;
				if (player.canSee(wx, wy))
					terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
				else
					terminal.write(fov.tile(wx, wy).glyph(), x, y, Color.darkGray);           }
        }
    }
	private void createItems(Factory factory) {
		for (int i = 0; i < world.width() * world.height() / 100; i++){
			factory.newRock();
		}
		factory.newVictoryItem();
	}
	
    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }
    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }

    public void displayOutput(AsciiPanel terminal) {
    	int left = getScrollX();
        int top = getScrollY();
   
        displayTiles(terminal, left, top);
        terminal.write("hp[", 0,22);
        terminal.write(']', 23, 22);
        int stopVal = (int)((double)player.getHp()*20/player.getMaxHp());
        for(int i=0; i<20; i++) {
        	if(i < stopVal)
                terminal.write((char)178,3+i,22);
        	else
        		terminal.write(' ',3+i,22);

        }
        displayMessages(terminal, messages);
		if (subscreen != null)
			subscreen.displayOutput(terminal);
}

    public Screen respondToUserInput(KeyEvent key) {
    	savedGame = new SavedGame(world);
    	if(subscreen != null) {
    		subscreen = subscreen.respondToUserInput(key);
    	} else {
	        switch (key.getKeyCode()){
		        case KeyEvent.VK_LEFT:
		        case KeyEvent.VK_H: player.moveBy(-1, 0); break;
		        case KeyEvent.VK_RIGHT:
		        case KeyEvent.VK_L: player.moveBy( 1, 0); break;
		        case KeyEvent.VK_UP:
		        case KeyEvent.VK_K: player.moveBy( 0,-1); break;
		        case KeyEvent.VK_DOWN:
		        case KeyEvent.VK_J: player.moveBy( 0, 1); break;
		        case KeyEvent.VK_Y: player.moveBy(-1,-1); break;
		        case KeyEvent.VK_U: player.moveBy( 1,-1); break;
		        case KeyEvent.VK_B: player.moveBy(-1, 1); break;
		        case KeyEvent.VK_N: player.moveBy( 1, 1); break;
		        case KeyEvent.VK_ESCAPE: return new SafeguardScreen(savedGame);
		        case KeyEvent.VK_ENTER: return new WinScreen();
				case KeyEvent.VK_D: subscreen = new DropScreen(player); break;	        case 'g':
				default : 			
					switch (key.getKeyChar()){
						case 'g':
						case ',': player.pickup(); break;
						default : return this;
					}
	        }
    	}
		if (subscreen == null)
			world.updateCreatures();
		if (player.getHp() < 1)
			return new LoseScreen();

        return this;
    }
}
