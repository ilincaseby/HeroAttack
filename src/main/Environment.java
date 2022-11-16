package main;

import java.util.List;

public class Environment extends Cards {
    public int mana;
    public String description;
    public final List<String> colors;
    public String name;

    public Environment(int mana, String description, List<String> colors, String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
        super.isMinion = false;
    }
}

class FireStorm extends Environment {
    public FireStorm(int mana, String description, List<String> colors, String name) {
        super(mana, description, colors, name);
    }
    public void ability(Cards[][] arr, int row) {
        for (int j = 0; j < 5; ++j) {
            if (arr[row][j].isMinion == true)
                ((Minion) arr[row][j]).health -= 1;
        }
    }
}

class Winterfall extends Environment {
    public Winterfall(int mana, String description, List<String> colors, String name) {
        super(mana, description, colors, name);
    }
    public void ability(Cards[][] arr, int row) {
        for (int j = 0; j < 5; ++j) {
            if (arr[row][j].isNull == false) {
                ((Minion) arr[row][j]).freeze();
            }
        }
    }
}

class HeartHound extends Environment {
    public HeartHound(int mana, String description, List<String> colors, String name) {
        super(mana, description, colors, name);
    }
    public void ability(Cards[][] arr, int row) {
        int indexHighestHealth = -1;
        int maxHealth = 0;
        for (int j = 0; j < 5; ++j) {
            if (arr[row][j].isMinion == true) {
                if (((Minion) arr[row][j]).health > maxHealth) {
                    indexHighestHealth = j;
                    maxHealth = ((Minion) arr[row][j]).health;
                }
            }
        }
        int mirrorIndex = -1;
        if (row == 0 || row == 1)
            mirrorIndex = 3 - row;
        if (row == 2 || row == 3)
            mirrorIndex = row - 3;
        if (arr[mirrorIndex][indexHighestHealth].isNull == false)
            return;
        arr[mirrorIndex][indexHighestHealth] = arr[row][indexHighestHealth];
        arr[row][indexHighestHealth] = new nullCard();
    }
}