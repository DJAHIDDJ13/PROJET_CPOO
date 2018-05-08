package rogue;

public class FungusAi extends CreatureAi{
	CreatureFactory factory;
	public int nbrChildren;
	public int childLevel;
	  public FungusAi(Creature creature, CreatureFactory factory, int childLevel) {
			super(creature);
			this.factory = factory;
			this.childLevel = childLevel;
	    }
	  public void onUpdate() {
			if(Math.random()<0.005)
				spread();
		}
		private void spread() {
			if(nbrChildren<3-childLevel && childLevel<2) {
				int x = creature.x + (Math.random()>0.5?1:-1)*((int)(Math.random()*2));
				int y = creature.y + (Math.random()>0.5?1:-1)*((int)(Math.random()*2));			
				if(creature.canEnter(x, y)) {
					nbrChildren++;
					Creature c = factory.newPlant();
					new PlantAi(c, factory, childLevel+1);
					c.x = x;
					c.y = y;
				}
			}
		}
		

}
