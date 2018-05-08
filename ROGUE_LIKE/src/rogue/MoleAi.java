package rogue;

import java.util.Random;

public class MoleAi extends CreatureAi {
	CreatureFactory factory;
	public int nbrChildren;
	public int childLevel;
    public MoleAi(Creature creature, CreatureFactory factory, int childLevel) {
		super(creature);
		this.factory = factory;
		this.childLevel = childLevel;
    }
public void onUpdate(){
    	
    	// moving
    	Random rn = new Random();
		int x = rn.nextInt(2);
		int y = rn.nextInt(2);

        if (x == 0) {
        	x--;
        }
        if (y == 0) {
        	y--;
        }
        creature.moveBy(x,y);
    }

}
