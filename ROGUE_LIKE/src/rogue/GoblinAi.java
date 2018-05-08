package rogue;

public class GoblinAi extends CreatureAi {
	CreatureFactory factory;
	public int nbrChildren;
	public int childLevel;
    public GoblinAi(Creature creature, CreatureFactory factory, int childLevel) {
		super(creature);
		this.factory = factory;
		this.childLevel = childLevel;
    }


 
}