package rogue;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(){
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow, 0, 0, 0, 0, 0, 0, 0, 0);
		player.setMaxHP(PlayerAi.maxHp);
        player.setHP(PlayerAi.hp);
        player.setMaxMana(PlayerAi.maxMana);
        player.setMana(PlayerAi.mana);
        player.setAtt(PlayerAi.attack);
        player.setDef(PlayerAi.defense);
        player.setCrit(PlayerAi.critical);
        player.setDodge(PlayerAi.dodge);
        player.setInsight(PlayerAi.insight);
		world.addAtEmptyLocation(player);
		new PlayerAi(player);
		return player;
	}

	public Creature newPlayer(int centerX, int centerY) {
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow, 0, 0, 0, 0, 0, 0, 0, 0);
		player.setMaxHP(PlayerAi.maxHp);
        player.setHP(PlayerAi.hp);
        player.setMaxMana(PlayerAi.maxMana);
        player.setMana(PlayerAi.mana);
        player.setAtt(PlayerAi.attack);
        player.setDef(PlayerAi.defense);
        player.setCrit(PlayerAi.critical);
        player.setDodge(PlayerAi.dodge);
        player.setInsight(PlayerAi.insight);
		world.addAtLocation(player, centerX, centerY);
		new PlayerAi(player);
		return player;
	}
	 public Creature newFungus(){
	        Creature fungus = new Creature(world, 'f', new Color(0, 255, 0), new Color(20,200,20),  20, 2, 1, 5, 3, 0, 0, 1);
	        world.addAtEmptyLocation(fungus);
	        new FungusAi(fungus, this, 0);
	        return fungus;
	    }
	 
	public Creature newPlant() {
		Creature plant = new Creature(world, 'p', new Color(0, 255, 0), new Color(20,200,20), 30, 3, 1, 3, 3, 0, 0, 1);
		//world.addAtEmptyLocation(plant);
		new PlantAi(plant, this, 0);
		return plant;
	}

    
   
    
   
    public Creature newGoblin(){
    	Creature goblin = new Creature(world, 'G', new Color(0, 255, 100), new Color(20,200,20), 0, 0, 0, 0, 0, 0, 0, 0);
        world.addAtEmptyLocation(goblin);
        new GoblinAi(goblin, this, 0);
        return goblin;
    }
    
    
    public Creature newMole(){
        Creature mole = new Creature(world, 'M', new Color(0, 255, 0), new Color(20,200,20), 0, 0, 0, 0, 0, 0, 0, 0);
        world.addAtEmptyLocation(mole);
        new MoleAi(mole, this, 0);
        return mole;
    }

}
