package rogue.screens;

import rogue.Creature;
import rogue.Item;

public class EatScreen extends InventoryBasedScreen {

	public EatScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "eat";
	}


	@Override
	protected Screen use(Item item) {
		player.eat(item);
		return null;
	}
}