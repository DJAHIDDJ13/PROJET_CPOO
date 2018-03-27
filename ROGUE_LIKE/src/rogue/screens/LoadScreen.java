package rogue.screens;

import java.awt.event.KeyEvent;
import java.io.File;

import asciiPanel.AsciiPanel;

public class LoadScreen implements Screen{
	int selected = 0;
	String[] savedFiles;
	public void displayOutput(AsciiPanel terminal) {
		// TODO affichage des tout les fichier dans ~/.savedRlGames
		terminal.writeCenter("Saved Games:", 2);
		
	}
	public Screen respondToUserInput(KeyEvent key) {
		// TODO Add scroll mechanism to see all the file names with scrolling with up and down keys + enter to select file
		
		return null;
	}
	public String[] getSavedFiles() {
		File f = new File(System.getProperty("user.home") + File.separator + ".savedRlGames");
		File[] files = f.listFiles();
		String[] fileNames = new String[files.length];
		for(int i=0; i<files.length; i++) {
			fileNames[i] = files[i].getName();
		}
		return fileNames;
		
	}
}
