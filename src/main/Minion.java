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
    private boolean isFrozen = false;
    public boolean isValidTurn = true;

    /**
     Method to call when the card used his attack.
     **/
    public void freeze() {
        isFrozen = true;
    }

    /**
     * Method to call when player ends his turn.
     * **/
    public void resetIce() {
        isFrozen = false;
    }

    /**
     * Method to check if a card has already attacked this turn.
     * **/
    public boolean isFrozen() {
        return isFrozen;
    }

    public Minion(final int mana, final int health, final int attackDamage,
                  final String description, final String name, final List<String> colors) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = attackDamage;
        this.description = description;
        this.name = name;
        this.colors = new ArrayList<>(colors);
        if (name.equals("Goliath") || name.equals("Warden")) {
            isTank = true;
        }
        super.setMinion(true);
        super.setNull(false);
    }
}

interface Actions {
    void action(Minion unluckyGuy);
}

class Miraj extends Minion implements Actions {
    public Miraj(final int mana, final int health, final int attackDamage,
                 final String description, final String name, final List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }
    public void action(final Minion unluckyGuy) {
        int aux = unluckyGuy.health;
        unluckyGuy.health = this.health;
        this.health = aux;
        this.isValidTurn = false;
    }
}

class TheRipper extends Minion implements Actions {
    public TheRipper(final int mana, final int health, final int attackDamage,
                     final String description, final String name, final List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }
    public void action(final Minion unluckyGuy) {
        unluckyGuy.attackDamage -= 2;
        if (unluckyGuy.attackDamage < 0) {
            unluckyGuy.attackDamage = 0;
        }
        this.isValidTurn = false;
    }
}

class TheCursedOne extends Minion implements Actions {
    public TheCursedOne(final int mana, final int health, final int attackDamage,
                        final String description, final String name, final List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }

    @Override
    public void action(final Minion unluckyGuy) {
        int aux = unluckyGuy.health;
        unluckyGuy.health = unluckyGuy.attackDamage;
        unluckyGuy.attackDamage = aux;
        this.isValidTurn = false;
    }
}

class Disciple extends Minion implements Actions {
    public Disciple(final int mana, final int health, final int attackDamage,
                    final String description, final String name, final List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }

    @Override
    public void action(final Minion luckyGuy) {
        luckyGuy.health += 2;
        this.isValidTurn = false;
    }
}
