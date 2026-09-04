package a12540834;

public enum MagicLevel {
	    NOOB(50),
	    ADEPT(100),
	    STUDENT(200),
	    EXPERT(500),
	    MASTER(1000);

	    private final int mp;

	    MagicLevel(int mp) {
	        this.mp = mp;
	    }

	    public int toMana() {
	        return mp;
	    }

	    @Override
	    public String toString() {
	        return "*".repeat(ordinal() + 1);
	    }
	}

