package main;

public class Cards {
    private boolean isMinion = false;
    private boolean isNull = true;
    private boolean isEnv = false;

    /**
     * Get minion value
     * **/
    public boolean isMinion() {
        return isMinion;
    }

    /**
     * Set minion value
     * **/
    public void setMinion(final boolean minion) {
        isMinion = minion;
    }

    /**
     * Get null value
     * **/
    public boolean isNull() {
        return isNull;
    }

    /**
     * Set null value
     * **/
    public void setNull(final boolean aNull) {
        isNull = aNull;
    }

    /**
     * Get Env value
     * **/
    public boolean isEnv() {
        return isEnv;
    }

    /**
     * Set env value
     * **/
    public void setEnv(final boolean env) {
        isEnv = env;
    }


}

class NullCard extends Cards {
}
