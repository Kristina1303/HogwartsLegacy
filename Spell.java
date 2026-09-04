package a12540834;

public abstract class Spell {
	private String name ;
	private int manaCost ;
	private MagicLevel levelNeeded ;
	
	public Spell(String name, int manaCost, MagicLevel levelNeeded) {
		if(name == null || name.isEmpty()) throw new IllegalArgumentException();
		if(manaCost < 0) throw new IllegalArgumentException();
		if(levelNeeded == null) throw new IllegalArgumentException();
		this.name = name;
		this.manaCost = manaCost;
		this.levelNeeded = levelNeeded;
	}
	public void cast(MagicSource source, MagicEffectRealization target) {
		if (source.provideMana(levelNeeded, manaCost)) {
            doEffect(target);
        }
	}
	public abstract void doEffect(MagicEffectRealization target);
	public String additionalOutputString () {
		return "";
	}
	@Override
    public String toString() {
		return "[" + name + "("+ levelNeeded + "): " + manaCost + " mana" + additionalOutputString() + "]";
	}
}
