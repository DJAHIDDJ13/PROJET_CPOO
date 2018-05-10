package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import rogue.screens.StartScreen;
public class HelpScreen implements Screen{

	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("HELP:", 2);
		terminal.writeCenter("press arrows or H, L, K, J to move", 5);
		terminal.writeCenter("press ESCAPE to safeguard game", 7);		
		terminal.writeCenter("-- press [escape] to return to start", 22);
	}

	public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ESCAPE ? new StartScreen() : this;
	}

}
