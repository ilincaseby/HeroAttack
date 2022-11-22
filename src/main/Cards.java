package main;

public class Cards {
    public boolean isMinion = false;
    public boolean isNull = false;
    public boolean isEnv = false;
}

class NullCard extends Cards{
    public int specific;
    public NullCard() {
        specific = 0;
        //super.isMinion = false;
        super.isNull = true;
    }

}
