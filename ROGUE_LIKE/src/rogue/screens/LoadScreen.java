package rogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;

import asciiPanel.AsciiPanel;

public class LoadScreen implements Screen{
	private int selected = 0;
	String[] savedFiles = getSavedFiles();
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("Saved Games:", 2);
		for(int i=0; i<5; i++) {
			if(selected < 5) {
				if(i == selected) {
					terminal.writeCenter(savedFiles[i], 5+2*i, new Color(255, 255, 0));
				}else {
					terminal.writeCenter(savedFiles[i], 5+2*i);
				}
			} else if(savedFiles.length - selected < 5) {
				if(savedFiles.length-5+i == selected) {
					terminal.writeCenter(savedFiles[savedFiles.length-5+i], 5+2*i, new Color(255, 255, 0));
				}else {
					terminal.writeCenter(savedFiles[savedFiles.length-5+i], 5+2*i);
				}
			} else {
				if(selected-2+i == selected) {
					terminal.writeCenter(savedFiles[selected-2+i], 5+2*i, new Color(255, 255, 0));
				}else {
					terminal.writeCenter(savedFiles[selected-2+i], 5+2*i);
				}
			}
		}
	}
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_ENTER: return new PlayScreen(System.getProperty("user.home") + File.separator + ".savedRlGames"+ File.separator + savedFiles[selected]);
		case KeyEvent.VK_ESCAPE: return new StartScreen();
		case KeyEvent.VK_DOWN:
			if(this.selected < savedFiles.length-1)
				selected++;
			return this;
		case KeyEvent.VK_UP:
			if(this.selected >0)
				selected--;
			return this;
		}
		return null;
	}
	public static String[] getSavedFiles() {
		File f = new File(System.getProperty("user.home") + File.separator + ".savedRlGames");
		File[] files = f.listFiles();
		String[] fileNames = new String[files.length];
		for(int i=0; i<files.length; i++) {
			fileNames[i] = files[i].getName();
		}
		return fileNames;
		
	}
}
