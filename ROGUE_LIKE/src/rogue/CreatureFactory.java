package rogue;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(){
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow);
		world.addAtEmptyLocation(player);
		new PlayerAi(player);
		return player;
	}

	public Creature newPlayer(int centerX, int centerY) {
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow);
		world.addAtLocation(player, centerX, centerY);
		new PlayerAi(player);
		return player;
	}
	public Creature newFungus(){
	    Creature fungus = new Creature(world, 'f', AsciiPanel.green, AsciiPanel.yellow);
	    world.addAtEmptyLocation(fungus);
	    new FungusAi(fungus);
	    return fungus;
	}

}
