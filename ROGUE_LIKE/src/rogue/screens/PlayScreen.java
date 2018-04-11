package rogue.screens;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import asciiPanel.AsciiPanel;
import rogue.*;
public class PlayScreen implements Screen {
    private World world;
    private int screenWidth;
    private int screenHeight;
    private SavedGame savedGame;
    private Creature player;
    public PlayScreen(){
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        player = creatureFactory.newPlayer();
    }
    public PlayScreen(String path) {
    	SavedGame s = new SavedGame(null, 0, 0, 0, 0);
    	try {
			s = s.loadGame(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	this.world = s.world;
    	this.player.x = s.centerX;
    	this.player.y = s.centerX;
        screenWidth = 80;
        screenHeight = 21;
        savedGame = new SavedGame(s.world, 0, 0, 200 , 200);
    }
    private void createWorld(){
    	int width = 200;
    	int height = 200;
        world = new WorldBuilder(width, height).makeCaves().build();
        savedGame = new SavedGame(world, 0, 0, width, height);
    }
    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;
                
                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
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
		terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
        terminal.write(player.x+" "+player.y, 2, 22);

   }

    public Screen respondToUserInput(KeyEvent key) {
    	savedGame.update(player.x, player.y);
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
        }
    
        return this;
    }
}
