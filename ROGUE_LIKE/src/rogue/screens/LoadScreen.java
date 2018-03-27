package rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoadScreen implements Screen{

	@Override
	public void displayOutput(AsciiPanel terminal) {
		// TODO affichage des tout les fichier dans ~/.savedRlGames
		
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Add scroll mechanism to see all the file names with scrolling with up and down keys + enter to select file
		
		return null;
	}
}
