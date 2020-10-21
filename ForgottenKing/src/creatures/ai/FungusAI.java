package creatures.ai;

import assembly.CreatureFactory;
import creatures.Creature;

public class FungusAI extends CreatureAI {
	private CreatureFactory factory;
	private int spreadcount;
	 
    public FungusAI(Creature creature, CreatureFactory factory, int spreadcount) {
        super(creature);
        this.factory = factory;
        this.spreadcount = spreadcount;
    }
    
    public void onUpdate(){
        if (spreadcount < 5 && Math.random() < 0.02)
            spread();
    }
    
    private void spread(){
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;
  
        if (!creature.canEnter(x, y, creature.z))
            return;
  
        Creature child = factory.newFungus(creature.z, spreadcount);
        child.x = x;
        child.y = y;
        spreadcount++;
        creature.doAction("spawn a child");
    }
}
