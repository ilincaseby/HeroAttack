package main;

public class Cards {
    public boolean isMinion;
    public boolean isNull = false;
}

class nullCard extends Cards{
    public int specific;
    public nullCard() {
        specific = 0;
        super.isMinion = false;
        super.isNull = true;
    }

}
