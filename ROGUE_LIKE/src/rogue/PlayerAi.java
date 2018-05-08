package rogue;

public class PlayerAi extends CreatureAi {
	 public static int maxHp;
		public static int hp;
		public static int maxMana;
		public static int mana;
		public static int stamina;
		public static int xp;
		public static int reqXp;
		public static int level = 1;
		
		//Passive Attributes
		public static int attack;
		public static int defense;
		public static double critical;
		public static double dodge;
		public static int insight;
	public PlayerAi(Creature creature) {
		super(creature);
	}

	public void onEnter(int x, int y, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
		}
	}
}
