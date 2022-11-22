package main;

import java.util.List;

public class Environment extends Cards {
    private int mana;
    private String description;
    private final List<String> colors;
    private String name;
    private boolean isWinterfell = false;
    private boolean isFireStorm = false;
    private boolean isHeartHound = false;

    /**
     * Getter
     * **/
    public int getMana() {
        return mana;
    }

    /**
     * Setter
     * **/
    public void setMana(int mana) {
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
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter
     * **/
    public List<String> getColors() {
        return colors;
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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter
     * **/
    public boolean isWinterfell() {
        return isWinterfell;
    }

    /**
     * Setter
     * **/
    public void setWinterfell(boolean winterfell) {
        isWinterfell = winterfell;
    }

    /**
     * Getter
     * **/
    public boolean isFireStorm() {
        return isFireStorm;
    }

    /**
     * Setter
     * **/
    public void setFireStorm(boolean fireStorm) {
        isFireStorm = fireStorm;
    }

    /**
     * Getter
     * **/
    public boolean isHeartHound() {
        return isHeartHound;
    }

    /**
     * Setter
     * **/
    public void setHeartHound(boolean heartHound) {
        isHeartHound = heartHound;
    }

    public Environment(final int mana, final String description,
                       final List<String> colors, final String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
        //super.isMinion = false;
        super.setEnv(true);
        super.setNull(false);
    }
}

class FireStorm extends Environment {
    public FireStorm(final int mana, final String description,
                     final List<String> colors, final String name) {
        super(mana, description, colors, name);
        super.setFireStorm(true);
    }
    public void ability(final Cards[][] arr, final int row) {
        for (int j = 0; j < Table.sizeCols; ++j) {
            if (arr[row][j].isMinion()) {
                ((Minion) arr[row][j]).health -= 1;
                if (((Minion) arr[row][j]).health <= 0) {
                    arr[row][j] = new NullCard();
                }
            }
        }
    }
}

class Winterfell extends Environment {
    public Winterfell(final int mana, final String description,
                      final List<String> colors, final String name) {
        super(mana, description, colors, name);
        super.setWinterfell(true);
    }
    public void ability(final Cards[][] arr, final int row) {
        for (int j = 0; j < Table.sizeCols; ++j) {
            if (!arr[row][j].isNull()) {
                ((Minion) arr[row][j]).freeze();
            }
        }
    }
}

class HeartHound extends Environment {
    public HeartHound(final int mana, final String description,
                      final List<String> colors, final String name) {
        super(mana, description, colors, name);
        super.setHeartHound(true);
    }
    public int ability(final Cards[][] arr, final int row) {
        int indexHighestHealth = -1;
        int maxHealth = 0;
        for (int j = 0; j < Table.sizeCols; ++j) {
            if (arr[row][j].isMinion()) {
                if (((Minion) arr[row][j]).health > maxHealth) {
                    indexHighestHealth = j;
                    maxHealth = ((Minion) arr[row][j]).health;
                }
            }
        }
        if (indexHighestHealth == CommandActionHelper.negOne) {
            return 1;
        }
        int mirrorIndex = CommandActionHelper.negOne;
        if (row == CommandActionHelper.Zero) {
            mirrorIndex = CommandActionHelper.Three;
        }
        if (row == CommandActionHelper.One) {
            mirrorIndex = CommandActionHelper.Two;
        }
        if (row == CommandActionHelper.Two) {
            mirrorIndex = CommandActionHelper.One;
        }
        if (row == CommandActionHelper.Three) {
            mirrorIndex = CommandActionHelper.Zero;
        }
        if (!arr[mirrorIndex][indexHighestHealth].isNull()) {
            return -1;
        }
        arr[mirrorIndex][indexHighestHealth] = arr[row][indexHighestHealth];
        arr[row][indexHighestHealth] = new NullCard();
        return 1;
    }
}
