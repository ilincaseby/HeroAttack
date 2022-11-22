package main;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Cards {
    private int mana;
    private int health;
    private int attackDamage;
    private String description;
    private String name;
    private final List<String> colors;
    private boolean isTank = false;
    private boolean isFrozen = false;
    private boolean isValidTurn = true;

    /**
     * Getter
     * **/
    public boolean isTank() {
        return isTank;
    }

    /**
     * Setter
     * **/
    public void setTank(final boolean tank) {
        isTank = tank;
    }

    /**
     * Getter
     * **/
    public int getMana() {
        return mana;
    }

    /**
     * Setter
     * **/
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Getter
     * **/
    public int getHealth() {
        return health;
    }

    /**
     * Setter
     * **/
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Getter
     * **/
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Setter
     * **/
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Getter
     * **/
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     * **/
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter
     * **/
    public String getName() {
        return name;
    }

    /**
     * Setter
     * **/
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter
     * **/
    public boolean isValidTurn() {
        return isValidTurn;
    }

    public void setValidTurn(final boolean validTurn) {
        isValidTurn = validTurn;
    }

    /**
     * Getter
     * **/
    public List<String> getColors() {
        return colors;
    }

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
        int aux = unluckyGuy.getHealth();
        unluckyGuy.setHealth(this.getHealth());
        this.setHealth(aux);
        this.setValidTurn(false);
    }
}

class TheRipper extends Minion implements Actions {
    public TheRipper(final int mana, final int health, final int attackDamage,
                     final String description, final String name, final List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }
    public void action(final Minion unluckyGuy) {
        unluckyGuy.setAttackDamage(unluckyGuy.getAttackDamage() - 2);
        if (unluckyGuy.getAttackDamage() < 0) {
            unluckyGuy.setAttackDamage(0);
        }
        this.setValidTurn(false);
    }
}

class TheCursedOne extends Minion implements Actions {
    public TheCursedOne(final int mana, final int health, final int attackDamage,
                        final String description, final String name, final List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }

    @Override
    public void action(final Minion unluckyGuy) {
        int aux = unluckyGuy.getHealth();
        unluckyGuy.setHealth(unluckyGuy.getAttackDamage());
        unluckyGuy.setAttackDamage(aux);
        this.setValidTurn(false);
    }
}

class Disciple extends Minion implements Actions {
    public Disciple(final int mana, final int health, final int attackDamage,
                    final String description, final String name, final List<String> colors) {
        super(mana, health, attackDamage, description, name, colors);
    }

    @Override
    public void action(final Minion luckyGuy) {
        luckyGuy.setHealth(luckyGuy.getHealth() + 2);
        this.setValidTurn(false);
    }
}
