package a12540834;

public interface Trader {
	 boolean possesses(Tradeable item);
	    // if item is null throw IllegalArgumentException

	    boolean canAfford(int amount);
	    // if amount is negative throw IllegalArgumentException

	    boolean hasCapacity(int weight);
	    // if weight is negative throw IllegalArgumentException

	    boolean pay(int amount);
	    // if amount is negative throw IllegalArgumentException

	    boolean earn(int amount);
	    // if amount is negative throw IllegalArgumentException

	    boolean addToInventory(Tradeable item);
	    // if item is null throw IllegalArgumentException

	    boolean removeFromInventory(Tradeable item);
	    // if item is null throw IllegalArgumentException

	    default boolean canSteal() {
	        return false;
	    }

	    boolean steal(Trader thief);
	    // if thief is null throw IllegalArgumentException

	    default boolean isLootable() {
	        return false;
	    }

	    default boolean canLoot() {
	        return false;
	    }

	    boolean loot(Trader looter);
	    // if looter is null throw IllegalArgumentException
	}

