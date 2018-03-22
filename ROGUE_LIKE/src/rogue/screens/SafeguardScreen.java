package rogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import asciiPanel.AsciiPanel;
import rogue.SavedGame;

public class SafeguardScreen implements Screen{
	int selected = 0;
	private SavedGame savedGame;
	public SafeguardScreen(SavedGame savedGame) {
		this.savedGame = savedGame;
	}
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("Do you want to safeguard before closing?", 7);
    	if(selected == 0) {
    		terminal.writeCenter("YES", 2, new Color(255, 255, 0));
    	} else {
    		terminal.writeCenter("YES", 2, new Color(255,255,255));
    	}
    	if(selected == 1) {
    		terminal.writeCenter("NO", 5, new Color(255, 255, 0));
    	} else {
    		terminal.writeCenter("NO", 5, new Color(255,255,255));
    	}

	}

	public Screen respondToUserInput(KeyEvent key) {
    	switch(key.getKeyCode()) {
    	case KeyEvent.VK_DOWN:
    	case KeyEvent.VK_UP:
    		selected = 1 - selected;
    		return this;
    	case KeyEvent.VK_ENTER:
    		if(selected == 0) {
    			try {
    				savedGame.SaveGame(".last_saved_game.rl");    				
    			} catch (FileNotFoundException | UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}

    		}
    		return null;
    	default:
    		return this;
    	}
	}

}
