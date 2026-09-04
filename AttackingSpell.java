package a12540834;

import java.util.HashSet;
import java.util.Set;

public class AttackingSpell extends Spell {
	private boolean type;
	private boolean percentage ;
	private int amount;
	
	public AttackingSpell(String name, int manaCost, MagicLevel levelNeeded, boolean type, boolean percentage, int amount) {
		super(name, manaCost, levelNeeded);
		if(amount < 0) throw new IllegalArgumentException();
		if(percentage && amount > 100) throw new IllegalArgumentException();
		this.type = type;
		this.percentage = percentage;
		this.amount = amount;
	}
	@Override
	public void doEffect ( MagicEffectRealization target ) {
		if (target.isProtected(this)) {
			target.removeProtection(new HashSet<>(Set.of(this)));
		}
		else {
            if (type && !percentage) {
                target.takeDamage(amount);         // absolute HP damage
            } else if (type && percentage) {
                target.takeDamagePercent(amount);  // percentage HP damage
            } else if (!type && !percentage) {
                target.weakenMagic(amount);        // absolute MP damage
            } else {
                target.weakenMagicPercent(amount); // percentage MP damage
            }
        }
	}
	 @Override
	    public String additionalOutputString() {
		 return "; -" + amount + (percentage ? "% " : " ") + (type ? "HP" : "MP");
	 }
}

