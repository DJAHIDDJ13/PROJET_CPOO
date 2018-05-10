package rogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import asciiPanel.AsciiPanel;
import rogue.SavedGame;

public class SafeguardScreen implements Screen {
	private int selected = 0;
	private static String fileName = "";
	private static boolean inputToggled = false;
	private static boolean badNameError = false;
	private SavedGame savedGame;
	public SafeguardScreen(SavedGame savedGame) {
		this.savedGame = savedGame;
	}
	public SafeguardScreen() {
		
	}
	public void displayOutput(AsciiPanel terminal) {
		if(badNameError) {
			terminal.writeCenter("Please choose a different file name", 5);
		}
		if(inputToggled) {
			terminal.writeCenter("Enter a valid new file name: ", 8);
			terminal.writeCenter(fileName, 9);
		} else {
			terminal.writeCenter("Do you want to safeguard before closing?", 7);
			if(selected == 0) {
				terminal.writeCenter("YES", 2, new Color(255, 255, 0));
				terminal.writeCenter("NO", 5, new Color(255,255,255));
			} else {
				terminal.writeCenter("YES", 2, new Color(255,255,255));
				terminal.writeCenter("NO", 5, new Color(255, 255, 0));
			}
		}
	}

	public Screen respondToUserInput(KeyEvent key) {
		if(inputToggled) {
			switch(key.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				try {
					savedGame.SaveGame(fileName);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch(FileNotFoundException e) {
					badNameError = true;
					fileName = "";
					return this;
				}
				return null;
			case KeyEvent.VK_BACK_SPACE:
				fileName = fileName.substring(0, fileName.length()==0?0:fileName.length()-1);
				return this;
			}
			char c = key.getKeyChar();
			String s = (Character.isLetterOrDigit(c) || c == '.' || c == '_' || c=='-')?""+c:"";
			fileName = fileName + s;
			return this;
		} else {
			switch(key.getKeyCode()) {
    		case KeyEvent.VK_DOWN:
    		case KeyEvent.VK_UP:
    			selected = 1 - selected;
    			return this;
    		case KeyEvent.VK_ENTER:
    			if(selected == 0) {
    				inputToggled = true;
    				return this;
    			}
    			return null;
    		}
		}
		return this;
	}
}
