package main;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Cards {
    public int mana;
    public int health;
    public int attackDamage;
    public String description;
    public String name;
    public final List<String> colors;
    public boolean isTank = false;
    private boolean validAttack = true;
    private boolean isFrozen = false;

    public void freeze() {
        isFrozen = true;
    }

    public void resetIce() {
        isFrozen = false;
    }

    public boolean isValidAttack() {
        return validAttack;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void resetAttack() {
        validAttack = true;
    }

    public void hasAttacked() {
        validAttack = false;
    }

    public Minion(int mana, int health, int attackDamage, String description, String name, List<String> colors) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = attackDamage;
        this.description = description;
        this.name = name;
        this.colors = new ArrayList<String>(colors);
        if (name.equals("Goliath") == true || name.equals("Warden"))
            isTank = true;
        super.isMinion = true;
    }
}

interface Actions{
    void action(Minion unluckyGuy);
}

class Miraj extends Minion implements Actions {
    public Miraj(int mana, int health, int attackDamage, String description, String name, List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }
    public void action(Minion unluckyGuy) {
        int aux = unluckyGuy.health;
        unluckyGuy.health = this.health;
        this.health = aux;
    }
}

class TheRipper extends Minion implements Actions {
    public TheRipper(int mana, int health, int attackDamage, String description, String name, List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }
    public void action(Minion unluckyGuy) {
        unluckyGuy.attackDamage -= 2;
    }
}

class TheCursedOne extends Minion implements Actions {
    public TheCursedOne(int mana, int health, int attackDamage, String description, String name, List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }

    @Override
    public void action(Minion unluckyGuy) {
        int aux = unluckyGuy.health;
        unluckyGuy.health = unluckyGuy.attackDamage;
        unluckyGuy.attackDamage = aux;
    }
}

class Disciple extends Minion implements Actions {
    public Disciple(int mana, int health, int attackDamage, String description, String name, List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }

    @Override
    public void action(Minion luckyGuy) {
        luckyGuy.health += 2;
    }
}