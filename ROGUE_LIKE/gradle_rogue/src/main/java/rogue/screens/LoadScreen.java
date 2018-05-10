package rogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import asciiPanel.AsciiPanel;

public class LoadScreen implements Screen{
	private int selected = 0;
	private int showStart = 0;
	String[] savedFiles = getSavedFiles();
	public void displayOutput(AsciiPanel terminal) {
		if(savedFiles==null) {
			terminal.writeCenter("No safeguards available", 5);
			return;
		}
		Arrays.sort(savedFiles);
		terminal.writeCenter("Saved Games:", 2);
		for(int i=showStart; i<savedFiles.length; i++) {
			if(5+i*3<22) {
				terminal.writeCenter(savedFiles[i], 5+i*3, new Color(255,255,i==selected?0:255));
			}
		}
		
	}
	public Screen respondToUserInput(KeyEvent key) {
		
		switch(key.getKeyCode()) {
		case KeyEvent.VK_ENTER: 
			if(savedFiles != null)
				return new PlayScreen(System.getProperty("user.home") + File.separator + ".savedRlGames"+ File.separator + savedFiles[selected]);
			else
				return new LoadScreen();
		case KeyEvent.VK_ESCAPE: return new StartScreen();
		case KeyEvent.VK_DOWN:
			selected = Math.floorMod(selected+1, savedFiles.length);
			if(selected == 0) {
				showStart = 0;
				break;
			}
			if(Math.abs(selected - showStart) > 5)
				showStart++;
			break;
		case KeyEvent.VK_UP: 
			selected = Math.floorMod(selected-1, savedFiles.length);
			if(selected == savedFiles.length-1) {
				showStart = savedFiles.length - 6;
				break;
			}
			if(selected < showStart)
				showStart--;
			break;
		}
		return this;
	}
	public static String[] getSavedFiles() {
		File f = new File(System.getProperty("user.home") + File.separator + ".savedRlGames");
		File[] files = f.listFiles();
		String[] fileNames;
		if(files != null) {
			fileNames = new String[files.length];
			for(int i=0; i<files.length; i++) {
				fileNames[i] = files[i].getName();
			}
		} else {
			fileNames = null;
		}
		return fileNames;
		
	}
}
