package rogue.screens;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import asciiPanel.AsciiPanel;
import rogue.*;
public class PlayScreen implements Screen {
    private World world;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
    private SavedGame savedGame;
    public PlayScreen(){
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
    }
    public PlayScreen(String path) {
    	SavedGame s = new SavedGame(null, 0, 0, 0, 0);
    	try {
			s = s.loadGame(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	this.world = s.world;
    	this.centerX = s.centerX;
    	this.centerY = s.centerX;
        screenWidth = 80;
        screenHeight = 21;
        savedGame = new SavedGame(s.world, 0, 0,s.world.width() , s.world.height());
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
        return Math.max(0, Math.min(centerX - screenWidth / 2, world.width() - screenWidth));
    }
    public int getScrollY() {
        return Math.max(0, Math.min(centerY - screenHeight / 2, world.height() - screenHeight));
    }

    public void displayOutput(AsciiPanel terminal) {
    	int left = getScrollX();
        int top = getScrollY();
   
        displayTiles(terminal, left, top);
        terminal.write('X', centerX - left, centerY - top);

   }
    private void scrollBy(int mx, int my){
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
    }
    public Screen respondToUserInput(KeyEvent key) {
    	savedGame.update(centerX, centerY);
        switch (key.getKeyCode()){
	        case KeyEvent.VK_LEFT:
	        case KeyEvent.VK_H: scrollBy(-1, 0); break;
	        case KeyEvent.VK_RIGHT:
	        case KeyEvent.VK_L: scrollBy( 1, 0); break;
	        case KeyEvent.VK_UP:
	        case KeyEvent.VK_K: scrollBy( 0,-1); break;
	        case KeyEvent.VK_DOWN:
	        case KeyEvent.VK_J: scrollBy( 0, 1); break;
	        case KeyEvent.VK_Y: scrollBy(-1,-1); break;
	        case KeyEvent.VK_U: scrollBy( 1,-1); break;
	        case KeyEvent.VK_B: scrollBy(-1, 1); break;
	        case KeyEvent.VK_N: scrollBy( 1, 1); break;
	        case KeyEvent.VK_ESCAPE: return new SafeguardScreen(savedGame);
	        case KeyEvent.VK_ENTER: return new WinScreen();
        }
    
        return this;
    }
}
