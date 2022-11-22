package main;

import java.util.List;

public class Hero {
    private int mana;
    private String description;
    private List<String> colors;
    private int health = CommandActionHelper.thirty;
    private boolean validAttack = true;
    private String name;

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
    public List<String> getColors() {
        return colors;
    }

    /**
     * Setter
     * **/
    public void setColors(final List<String> colors) {
        this.colors = colors;
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
    public String getName() {
        return name;
    }

    /**
     * Setter
     * **/
    public void setName(final String name) {
        this.name = name;
    }

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
                if (((Minion) arr[row][j]).getAttackDamage() > highestAttackDamage) {
                    highestAttackDamage = ((Minion) arr[row][j]).getAttackDamage();
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
                if (((Minion) arr[row][j]).getHealth() > highestHealth) {
                    highestHealth = ((Minion) arr[row][j]).getHealth();
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
                ((Minion) arr[row][j]).setHealth(((Minion) arr[row][j]).getHealth() + 1);
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
                ((Minion) arr[row][j]).
                        setAttackDamage(((Minion) arr[row][j]).getAttackDamage() + 1);
            }
        }
    }
}



