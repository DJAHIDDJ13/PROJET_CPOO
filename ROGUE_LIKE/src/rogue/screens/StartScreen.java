package rogue.screens;
import java.awt.Color;
import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {
	int selected = 0;
    public void displayOutput(AsciiPanel terminal) {
    	if(selected == 0) {
    		terminal.writeCenter("PLAY", 2, new Color(255, 255, 0));
    	} else {
    		terminal.writeCenter("PLAY", 2, new Color(255,255,255));
    	}
    	if(selected == 1) {
    		terminal.writeCenter("HELP", 5, new Color(255, 255, 0));
    	} else {
    		terminal.writeCenter("HELP", 5, new Color(255,255,255));
    	}
    	if(selected == 2) {
    		terminal.writeCenter("LOAD", 8, new Color(255, 255, 0));
    	} else {
    		terminal.writeCenter("LOAD", 8, new Color(255,255,255));
    	}
        terminal.writeCenter("-- press [enter] to start --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
    	switch(key.getKeyCode()) {
    	case KeyEvent.VK_DOWN:
    		selected = Math.floorMod(selected+1, 3);
    		return this;    		
    	case KeyEvent.VK_UP:
    		selected = Math.floorMod(selected-1, 3);
    		return this;
    	case KeyEvent.VK_ENTER:
    		switch(selected) {
    		case 0: return new PlayScreen();
    		case 1: return new HelpScreen();
    		case 2: return new LoadScreen();
    		}
    	default:
    		return this;
    	}
    }
}