package main;

import java.util.List;

public class Hero {
    int mana;
    String description;
    List<String> colors;
    int health = CommandActionHelper.thirty;
    private boolean validAttack = true;
    String name;

    public Hero(final int mana, final String description,
                final List<String> colors, final String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    /**
    Method to access validAttack parameter.
    * */
    public boolean isValidAttack() {
        return validAttack;
    }

    /**
     Method to call when a turn is done.
    * */
    public void resetAttack() {
        validAttack = true;
    }

    /**
     Method to call when the Hero took his
     chance to attack during this turn.
    * */
    public void hasAttacked() {
        validAttack = false;
    }
}

class LordRoyce extends Hero {
    public LordRoyce(final int mana, final String description, final List<String> colors) {
        super(mana, description, colors, "Lord Royce");
    }
    public void subZero(final Cards[][] arr, final int row) {
        int indexHighestAttackLevel = -1;
        int highestAttackDamage = -1;
        for (int j = 0; j < Table.sizeCols; ++j) {
            if (!arr[row][j].isNull()) {
                if (((Minion) arr[row][j]).attackDamage > highestAttackDamage) {
                    highestAttackDamage = ((Minion) arr[row][j]).attackDamage;
                    indexHighestAttackLevel = j;
                }
            }
        }
        if (indexHighestAttackLevel == -1) {
            return;
        }
        ((Minion) arr[row][indexHighestAttackLevel]).freeze();
    }
}

class EmpressThorina extends Hero {
    public EmpressThorina(final int mana, final String description, final List<String> colors) {
        super(mana, description, colors, "Empress Thorina");
    }
    public void lowBlow(final Cards[][] arr, final int row) {
        int indexHighestHealth = -1;
        int highestHealth = -1;
        for (int j = 0; j < Table.sizeCols; ++j) {
            if (!arr[row][j].isNull()) {
                if (((Minion) arr[row][j]).health > highestHealth) {
                    highestHealth = ((Minion) arr[row][j]).health;
                    indexHighestHealth = j;
                }
            }
        }
        if (indexHighestHealth == -1) {
            return;
        }
        arr[row][indexHighestHealth] = new NullCard();
    }
}

class KingMudFace extends Hero {
    public KingMudFace(final int mana, final String description, final List<String> colors) {
        super(mana, description, colors, "King Mudface");
    }
    public void earthBorn(final Cards[][] arr, final int row) {
        for (int j = 0; j < Table.sizeCols; ++j) {
            if (!arr[row][j].isNull()) {
                ((Minion) arr[row][j]).health++;
            }
        }
    }
}

class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final int mana, final String description, final List<String> colors) {
        super(mana, description, colors, "General Kocioraw");
    }
    public void bloodThrist(final Cards[][] arr, final int row) {
        for (int j = 0; j < Table.sizeCols; ++j) {
            if (!arr[row][j].isNull()) {
                ((Minion) arr[row][j]).attackDamage++;
            }
        }
    }
}



