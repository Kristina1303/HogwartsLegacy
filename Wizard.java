package a12540834;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Wizard implements MagicSource , Trader , MagicEffectRealization{
	private String name ;
	private MagicLevel level ;
	private int basicHP ;
	private int HP ;
	private int basicMP;
	private int MP ;
	private int money ;
	private Set < Spell > knownSpells ;
	private Set < AttackingSpell > protectedFrom ;
	private int carryingCapacity ;
	private Set < Tradeable > inventory ;
	
	public Wizard(String name, MagicLevel level, int basicHP, int HP, int basicMP, int MP,
            int money, Set<Spell> knownSpells, Set<AttackingSpell> protectedFrom,
            int carryingCapacity, Set<Tradeable> inventory) {
  if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException();
  }
  if (level == null) {
      throw new IllegalArgumentException();
  }
  if (basicHP < 0) {
      throw new IllegalArgumentException();
  }
  if (basicMP < level.toMana()) {
      throw new IllegalArgumentException();
  }
  if (money < 0) {
      throw new IllegalArgumentException();
  }
  if (carryingCapacity < 0) {
      throw new IllegalArgumentException();
  }
  if (knownSpells == null) {
      throw new IllegalArgumentException();
  }
  if (protectedFrom == null) {
      throw new IllegalArgumentException();
  }
  if (inventory == null) {
      throw new IllegalArgumentException();
  }
  this.name = name;
  this.level = level;
  this.basicHP = basicHP;
  this.HP = HP;      
  this.basicMP = basicMP;
  this.MP = MP;      
  this.money = money;
  this.knownSpells = new HashSet<>(knownSpells);
  this.protectedFrom = new HashSet<>(protectedFrom);
  this.carryingCapacity = carryingCapacity;
  this.inventory = new HashSet<>(inventory);
}
	 public boolean isDead() {
	        return HP == 0;
	    }
	 private int inventoryTotalWeight() {
	        int total = 0;
	        for (Tradeable item : inventory) {
	            total += item.getWeight();
	        }
	        return total;
	    }
	  public boolean learn(Spell s) {
	        if (s == null) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        return knownSpells.add(s);
	    }
	  public boolean forget ( Spell s ) {
		  if (s == null) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        return knownSpells.remove(s);
	  }
	  public boolean castSpell(Spell s, MagicEffectRealization target) {
	        if (s == null || target == null) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        if (!knownSpells.contains(s)) return false;
	        s.cast(this, target);
	        return true;
	    }

	    public boolean castRandomSpell(MagicEffectRealization target) {
	        if (knownSpells.isEmpty()) return false;
	        int index = new Random().nextInt(knownSpells.size());
	        Spell randomSpell = knownSpells.stream().skip(index).findFirst().get();
	        return castSpell(randomSpell, target);
	    }
	 
	    public boolean useItem(Tradeable item, MagicEffectRealization target) {
	        if (item == null || target == null) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        if (!possesses(item)) return false;
	        item.useOn(target);
	        return true;
	    }

	    public boolean useRandomItem(MagicEffectRealization target) {
	        if (inventory.isEmpty()) return false;
	        int index = new Random().nextInt(inventory.size());
	        Tradeable randomItem = inventory.stream().skip(index).findFirst().get();
	        return useItem(randomItem, target);
	    }

	    public boolean sellItem(Tradeable item, Trader target) {
	        if (item == null || target == null) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        return item.purchase(this, target);
	    }
	    public boolean sellRandomItem(Trader target) {
	        if (inventory.isEmpty()) return false;
	        int index = new Random().nextInt(inventory.size());
	        Tradeable randomItem = inventory.stream().skip(index).findFirst().get();
	        return sellItem(randomItem, target);
	    }
	    @Override
	    public String toString() {
	        return "[" + name + "(" + level + "): "
	                + HP + "/" + basicHP + " "
	                + MP + "/" + basicMP + "; "
	                + money + (money == 1 ? " Knut" : " Knuts") + "; "
	                + "knows " + knownSpells + "; "
	                + "carries " + inventory + "]";
	    }
	 // --- MagicSource Interface ---
	    
	    @Override
	    public boolean provideMana(MagicLevel levelNeeded, int manaAmount) {
	        if (levelNeeded == null || manaAmount < 0) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        if (level.ordinal() < levelNeeded.ordinal()) return false;
	        if (MP < manaAmount) return false;
	        MP -= manaAmount;
	        return true;
	    }
	 
	 // --- Trader Interface ---

	    @Override
	    public boolean possesses(Tradeable item) {
	        if (item == null) throw new IllegalArgumentException();
	        return inventory.contains(item);
	    }

	    @Override
	    public boolean canAfford(int amount) {
	        if (amount < 0) throw new IllegalArgumentException();
	        return money >= amount;
	    }

	    @Override
	    public boolean hasCapacity(int weight) {
	        if (weight < 0) throw new IllegalArgumentException();
	        return inventoryTotalWeight() + weight <= carryingCapacity;
	    }
	    @Override
	    public boolean pay(int amount) {
	        if (amount < 0) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        if (!canAfford(amount)) return false;
	        money -= amount;
	        return true;
	    }

	    @Override
	    public boolean earn(int amount) {
	        if (amount < 0) throw new IllegalArgumentException();
	        if (isDead()) return false;
	        money += amount;
	        return true;
	    }
	    @Override
	    public boolean addToInventory(Tradeable item) {
	        if (item == null) throw new IllegalArgumentException();
	        if (!hasCapacity(item.getWeight())) return false;
	        return inventory.add(item);
	    }

	    @Override
	    public boolean removeFromInventory(Tradeable item) {
	        if (item == null) throw new IllegalArgumentException();
	        return inventory.remove(item);
	    }
	    @Override
	    public boolean canSteal() {
	        return !isDead();
	    }

	    @Override
	    public boolean steal(Trader thief) {
	        if (thief == null) throw new IllegalArgumentException();
	        if (!thief.canSteal()) return false;
	        if (inventory.isEmpty()) return false;
	        int index = new Random().nextInt(inventory.size());
	        Tradeable randomItem = inventory.stream().skip(index).findFirst().get();
	        removeFromInventory(randomItem);
	        return thief.addToInventory(randomItem);
	    }
	    @Override
	    public boolean isLootable() {
	        return isDead();
	    }

	    @Override
	    public boolean canLoot() {
	        return !isDead();
	    }
	        @Override
	        public boolean loot(Trader looter) {
	            if (looter == null) throw new IllegalArgumentException();
	            if (!looter.canLoot()) return false;
	            if (!isLootable()) return false;
	            boolean success = false;
	            Set<Tradeable> copy = new HashSet<>(inventory); // copy to avoid modifying while iterating
	            for (Tradeable item : copy) {
	                removeFromInventory(item);
	                if (looter.addToInventory(item)) {
	                    success = true;
	                }
	            }
	            return success;
	        }
	     // --- MagicEffectRealization Interface ---

	        @Override
	        public void takeDamage(int amount) {
	            if (amount < 0) throw new IllegalArgumentException();
	            HP = Math.max(0, HP - amount); // HP cannot go below 0
	        }

	        @Override
	        public void takeDamagePercent(int percentage) {
	            if (percentage < 0 || percentage > 100) throw new IllegalArgumentException();
	            int damage = (int)(basicHP * percentage / 100.0);
	            HP = Math.max(0, HP - damage);
	        }

	        @Override
	        public void weakenMagic(int amount) {
	            if (amount < 0) throw new IllegalArgumentException();
	            MP = Math.max(0, MP - amount);
	        }
	        @Override
	        public void weakenMagicPercent(int percentage) {
	            if (percentage < 0 || percentage > 100) throw new IllegalArgumentException();
	            int damage = (int)(basicMP * percentage / 100.0);
	            MP = Math.max(0, MP - damage);
	        }

	        @Override
	        public void heal(int amount) {
	            if (amount < 0) throw new IllegalArgumentException();
	            HP += amount;
	        }
	        @Override
	        public void healPercent(int percentage) {
	            if (percentage < 0 || percentage > 100) throw new IllegalArgumentException();
	            HP += (int)(basicHP * percentage / 100.0);
	        }

	        @Override
	        public void enforceMagic(int amount) {
	            if (amount < 0) throw new IllegalArgumentException();
	            MP += amount;
	        }

	        @Override
	        public void enforceMagicPercent(int percentage) {
	            if (percentage < 0 || percentage > 100) throw new IllegalArgumentException();
	            MP += (int)(basicMP * percentage / 100.0);
	        }
	        @Override
	        public boolean isProtected(Spell s) {
	            if (s == null) throw new IllegalArgumentException();
	            return protectedFrom.contains(s);
	        }

	        @Override
	        public void setProtection(Set<AttackingSpell> attacks) {
	            if (attacks == null) throw new IllegalArgumentException();
	            protectedFrom.addAll(attacks);
	        }

	        @Override
	        public void removeProtection(Set<AttackingSpell> attacks) {
	            if (attacks == null) throw new IllegalArgumentException();
	            protectedFrom.removeAll(attacks);
	        }
	    }
	       

