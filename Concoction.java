package a12540834;

import java.util.ArrayList;
import java.util.List;

public class Concoction extends Potion{
	private int health ;
	private int mana ;
	private List<Spell> spells;

	public Concoction(String name, int usages, int price, int weight, int health, int mana, List <Spell> spells) {
		super(name, usages, price, weight);
		if(spells == null) throw new IllegalArgumentException();
		if(mana == 0 && health == 0 && spells.isEmpty()) throw new IllegalArgumentException();
		this.mana = mana;
		this.health = health;
		this.spells = new ArrayList<>(spells); 
	}
	@Override
	public String additionalOutputString () {
		 StringBuilder sb = new StringBuilder();
		 if (health != 0) {
	            sb.append("; ")
	              .append(health > 0 ? "+" : "-")  
	              .append(Math.abs(health))
	              .append(" HP");
	        }
		 if (mana != 0) {
	            sb.append("; ")
	              .append(mana > 0 ? "+" : "-")   
	              .append(Math.abs(mana))
	              .append(" MP");
	        }
		 if (!spells.isEmpty()) {
	            sb.append("; cast ")
	              .append(spells);  
	        }
		 return sb.toString();
	}
	 @Override
	    public void useOn(MagicEffectRealization target) {
		 if (tryUsage()) {

	            if (health > 0) {
	                target.heal(health);   
	            } else if (health < 0) {
	                target.takeDamage(-health);
	            }
	 
		 if (mana > 0) {
             target.enforceMagic(mana);   
         } else if (mana < 0) {
             target.weakenMagic(-mana);   
         }
         for (Spell spell : spells) {
             spell.cast(this, target);
         }

		 }
}
}
