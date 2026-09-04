package a12540834;

public abstract class MagicItem implements Tradeable, MagicEffectRealization, MagicSource{
	private String name ; 
	private int usages ; 
	private int price ; 
	private int weight ; 
	
	public MagicItem(String name, int usages, int price, int weight) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        if (usages < 0) throw new IllegalArgumentException();
        if (price < 0) throw new IllegalArgumentException();
        if (weight < 0) throw new IllegalArgumentException();
        this.name = name;
        this.usages = usages;
        this.price = price;
        this.weight = weight;
    }
	
	public int getUsages () {
	return usages;
	}
	public boolean tryUsage () {
	if (usages > 0 ) {
		usages--;
		return true;
	}
	 return false;
	}
	public String usageString () {
		if(usages == 1) return "use";
		else return "uses";
	}
	public String additionalOutputString () {
	return "";
	}
	@Override
		public String toString() {
	        String currencyString = price == 1 ? "Knut" : "Knuts";
	        return "[" + name + "; " + weight + " g; " + price + " " + currencyString + "; " + usages + " " + usageString() + additionalOutputString() + "]";
	    }
	
@Override
public int getPrice() {
    return price;
}

@Override
public int getWeight() {
    return weight;
}

@Override
public boolean provideMana(MagicLevel levelNeeded, int amount) {
    return true;
}
@Override
public void takeDamagePercent(int percentage) {
    if (percentage < 0 || percentage > 100) throw new IllegalArgumentException();
    usages = (int)(usages * (1 - percentage / 100.0));
}

}