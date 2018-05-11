package rogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		terminal.writeCenter("press [Enter] to load and [Escape] to go back and [Del] to delete", 22);
		
	}
	public Screen respondToUserInput(KeyEvent key) {
		
		switch(key.getKeyCode()) {
		case KeyEvent.VK_ENTER: 
			if(savedFiles != null)
				if(savedFiles.length >= 1)
					return new PlayScreen(System.getProperty("user.home") + File.separator + ".savedRlGames"+ File.separator + savedFiles[selected]);
			else
				return new LoadScreen();
		case KeyEvent.VK_ESCAPE: return new StartScreen();
		case KeyEvent.VK_DOWN:
			if(savedFiles == null)
				break;
			if(savedFiles.length < 1)
				break;
			selected = Math.floorMod(selected+1, savedFiles.length);
			if(selected == 0) {
				showStart = 0;
				break;
			}
			if(Math.abs(selected - showStart) > 5)
				showStart++;
			break;
		case KeyEvent.VK_UP: 
			if(savedFiles == null)
				break;
			if(savedFiles.length < 1)
				break;
			selected = Math.floorMod(selected-1, savedFiles.length);
			if(selected == savedFiles.length-1) {
				showStart = Math.max(0, savedFiles.length - 6);
				break;
			}
			if(selected < showStart)
				showStart--;
			break;
		case KeyEvent.VK_DELETE:
			try {
				if(savedFiles == null)
					break;
				if(savedFiles.length < 1)
					break;
				Files.deleteIfExists(Paths.get(System.getProperty("user.home") + File.separator + ".savedRlGames"+ File.separator + savedFiles[selected]));
				savedFiles = getSavedFiles();
				if(selected >= savedFiles.length)
					selected = Math.max(0, savedFiles.length-1);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
