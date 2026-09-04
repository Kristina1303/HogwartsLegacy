package a12540834;

public interface Tradeable {

	    int getPrice();

	    int getWeight();

	    private boolean transfer(Trader from, Trader to) {
	        return from.removeFromInventory(this) && to.addToInventory(this);
	    }

	    default boolean give(Trader giver, Trader taker) {
	        if (giver == null || taker == null || giver == taker)
	            throw new IllegalArgumentException();
	        if (!giver.possesses(this) || !taker.hasCapacity(getWeight()))
	            return false;
	        return transfer(giver, taker);
	    }

	    default boolean purchase(Trader seller, Trader buyer) {
	        if (seller == null || buyer == null || seller == buyer)
	            throw new IllegalArgumentException();
	        if (!seller.possesses(this) || !buyer.canAfford(getPrice()) || !buyer.hasCapacity(getWeight()))
	            return false;
	        buyer.pay(getPrice());
	        seller.earn(getPrice());
	        return transfer(seller, buyer);
	    }

	    void useOn(MagicEffectRealization target);
	    // if target is null throw IllegalArgumentException!!!
	}
