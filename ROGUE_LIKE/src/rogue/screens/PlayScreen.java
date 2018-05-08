package rogue.screens;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
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
    private CreatureFactory creatureFactory;
    private List<String> messages;
    public PlayScreen(){
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
        creatureFactory = new CreatureFactory(world);
        messages = new ArrayList<String>();
        player = creatureFactory.newPlayer(messages);
        populateWorld();
    }
    public PlayScreen(String path) {
    	SavedGame s = new SavedGame(null, 0, 0, 0, 0);
    	try {
			s = s.loadGame(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	world = s.world;
        screenWidth = 80;
        screenHeight = 21;
        savedGame = new SavedGame(s.world, 0, 0, 200 ,200);
        creatureFactory = new CreatureFactory(world);
        messages = new ArrayList<String>();
        player = creatureFactory.newPlayer(s.centerX, s.centerY, messages);
        populateWorld();
    }
    private void populateWorld(){
        for(int i=0; i<100; i++) {
        	creatureFactory.newPlant();
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
        savedGame = new SavedGame(world, 0, 0, width, height);
    }
    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;
                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy), world.bgColor(wx, wy));
            }
        }
        List<Creature> creatures = world.getCreature();
        for(Creature c : creatures) {
        	if(c.x >= left && c.x < left+screenWidth && c.y >= top  && c.y < top+screenHeight) {
        		terminal.write(c.glyph(), c.x-left, c.y-top, c.color(), c.bgColor());
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
        terminal.write("hp", 0,22);
        terminal.write('|', 2, 22);
        terminal.write('|', 23, 22);
        for(int i=0; i<20; i++) {
        	if(i/20 < player.getHp()/player.getMaxHp())
        		terminal.write((char)178, i+3, 22);
        }
        displayMessages(terminal, messages);
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
