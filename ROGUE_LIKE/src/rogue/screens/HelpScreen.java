package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import rogue.screens.*;
public class HelpScreen implements Screen{

	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("NOTHING", 2);
		terminal.writeCenter("-- press [ escape ] to return to start", 22);
	}

	public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ESCAPE ? new StartScreen() : this;
	}

}
