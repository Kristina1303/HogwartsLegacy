package a12540834;

public class HealingSpell extends Spell{
	private boolean type ;
	private boolean percentage ;
	private int amount ;
	
	public HealingSpell(String name, int manaCost, MagicLevel levelNeeded, boolean type, boolean percentage, int amount) {
		super(name, manaCost, levelNeeded);
		if(amount < 0) throw new IllegalArgumentException();
		if(percentage && amount > 100) throw new IllegalArgumentException();
		this.type = type;
		this.percentage = percentage;
		this.amount = amount;
	}
	@Override
	public void doEffect ( MagicEffectRealization target ) {
		 if (type && !percentage) {
	            target.heal(amount);   // absolute HP restore
		 }
		 else if (type && percentage) {
	            target.healPercent(amount);         // percentage HP restore
	        } else if (!type && !percentage) {
	            target.enforceMagic(amount);        // absolute MP restore
	        } else {
	            target.enforceMagicPercent(amount); // percentage MP restore
	        } 
		 
	}
		 
	 @Override
	    public String additionalOutputString() {
	        return "; +" + amount + (percentage ? "% " : " ") + (type ? "HP" : "MP");
	 }

}
