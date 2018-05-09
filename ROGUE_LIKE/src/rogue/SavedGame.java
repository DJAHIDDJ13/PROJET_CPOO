package rogue;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import asciiPanel.AsciiPanel;


public class SavedGame {
	private int[] player;
	private int[][] moles;
	private int[][] plants;
	private char[][] world;
	public SavedGame(World world) {
		List<Creature> creatures;
		try {
			this.world = new char[world.width()][world.height()];
			for(int i=0; i<world.width(); i++) {
				this.world[i] = new char[world.height()];
				for(int j=0; j<world.height(); j++) {
					this.world[i][j] = world.glyph(i, j);
				}
			}
			creatures = world.getCreature();
		} catch(NullPointerException e) {
			this.world = null;
			creatures = null;
		}
		try {
			int moleC = 0;
			int plantC = 0;
			for(Creature c: creatures) {
				if(c.getCreatureAi() instanceof PlantAi) {
					plantC++;
				} else if(c.getCreatureAi() instanceof MoleAi) {
					moleC++;
				}
			}
			player = new int[6];
			plants = new int[plantC][5];
			moles = new int[moleC][4];
			moleC = 0;
			plantC = 0;
			for(Creature c: creatures) {
				if(c.getCreatureAi() instanceof PlayerAi) {
					this.player[0] = c.x;
					this.player[1] = c.y;
					this.player[2] = c.getHp();
					this.player[3] = c.getMaxHp();
					this.player[4] = c.attackValue();
					this.player[5] = c.defenseValue();
				} else if(c.getCreatureAi() instanceof PlantAi) {
					plants[plantC] = new int[5];
					this.plants[plantC][0] = c.x;
					this.plants[plantC][1] = c.y;
					this.plants[plantC][2] = c.getHp();
					this.plants[plantC][3] = c.getMaxHp();
					this.plants[plantC][4] = ((PlantAi)c.getCreatureAi()).childLevel;
					plantC++;
				} else if(c.getCreatureAi() instanceof MoleAi) {
					moles[moleC] = new int[4];
					this.moles[moleC][0] = c.x;
					this.moles[moleC][1] = c.y;
					this.moles[moleC][2] = c.getHp();
					this.moles[moleC][3] = c.getMaxHp();
					moleC++;
				}
			}
		} catch(NullPointerException e) {
			this.player = null;
			this.plants = null;
			this.moles = null;
		}
	}
	public void SaveGame(String name) throws IOException{
		String path = System.getProperty("user.home") + File.separator + ".savedRlGames" + File.separator + name;
		System.out.println("saved to: " + path);
		File f = new File(path);
		if(!f.getParentFile().isDirectory())
			f.getParentFile().mkdirs();
		if(f.exists()) {
			throw new FileNotFoundException();
		}
		Gson save = new Gson();
		FileWriter f1 = new FileWriter(path);
		save.toJson(this, f1);
		f1.close();
	}
	public void update(List<Creature> creatures) {
		int moleC = 0;
		int plantC = 0;
		for(Creature c: creatures) {
			if(c.getCreatureAi() instanceof PlantAi) {
				plantC++;
			} else if(c.getCreatureAi() instanceof MoleAi) {
				moleC++;
			}
		}
		player = new int[6];
		plants = new int[plantC][5];
		moles = new int[moleC][4];
		moleC = 0;
		plantC = 0;
		for(Creature c: creatures) {
			if(c.getCreatureAi() instanceof PlayerAi) {
				this.player[0] = c.x;
				this.player[1] = c.y;
				this.player[2] = c.getHp();
				this.player[3] = c.getMaxHp();
				this.player[4] = c.attackValue();
				this.player[5] = c.defenseValue();
			} else if(c.getCreatureAi() instanceof PlantAi) {
				plants[plantC] = new int[5];
				this.plants[plantC][0] = c.x;
				this.plants[plantC][1] = c.y;
				this.plants[plantC][2] = c.getHp();
				this.plants[plantC][3] = c.getMaxHp();
				this.plants[plantC][4] = ((PlantAi)c.getCreatureAi()).childLevel;
				plantC++;
			} else if(c.getCreatureAi() instanceof MoleAi) {
				moles[moleC] = new int[6];
				this.moles[moleC][0] = c.x;
				this.moles[moleC][1] = c.y;
				this.moles[moleC][2] = c.getHp();
				this.moles[moleC][3] = c.getMaxHp();
				this.moles[moleC][4] = c.attackValue();
				this.moles[moleC][5] = c.defenseValue();
				moleC++;
			}
		}
	}

	public World loadGame(String path, List<String> messages, CreatureFactory factory) throws IOException {
		Gson load = new Gson();
		FileReader reader = new FileReader(path);
		SavedGame s = load.fromJson(reader, SavedGame.class);
		Tile[][] tiles = new Tile[s.world.length][s.world[0].length];
		for(int i=0; i<s.world.length; i++) {
			for(int j=0; j<s.world[0].length; j++) {
				tiles[i][j] = s.world[i][j] == '@'? Tile.WALL:Tile.FLOOR;
			}
		}
		World w = new World(tiles);
		factory = new CreatureFactory(w);
		List<Creature> c = new ArrayList<Creature>();
		this.world = s.world;
		this.player = s.player;
		Creature d;
		d = new Creature(w, '$', AsciiPanel.brightWhite, AsciiPanel.yellow, s.player[3], s.player[4], s.player[5]);
		new PlayerAi(d, messages);
		d.takeDamage(s.player[2]-s.player[3]);
		d.x = s.player[0];
		d.y = s.player[1];
		c.add(d);
		for(int i=0; i<s.plants.length; i++) {
			Creature plant = new Creature(w, 'p', new Color(0, 255, 0), new Color(250,0,20), s.plants[i][3], 0, 0);
			plant.takeDamage(s.plants[i][2]-s.plants[i][3]);
			new PlantAi(plant, factory, s.plants[i][4]);
			plant.x = s.plants[i][0];
			plant.y = s.plants[i][1];
			c.add(plant);
		}
		for(int i=0; i<s.moles.length; i++) {
			Creature mole = new Creature(w, 'm', new Color(255, 0, 0), new Color(20,100,20), s.moles[i][3], s.moles[i][4], s.moles[i][5]);
			mole.takeDamage(s.moles[i][2]-s.moles[i][3]);
			new MoleAi(mole);
			mole.x = s.moles[i][0];
			mole.y = s.moles[i][1];
			c.add(mole);
		}
		w.setCreatures(c);
		this.plants = s.plants;
		this.moles = s.moles;
		reader.close();
		return w;
	}
}

