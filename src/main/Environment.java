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
        //super.isMinion = false;
        super.isEnv = true;
    }
}

class FireStorm extends Environment {
    public FireStorm(int mana, String description, List<String> colors, String name) {
        super(mana, description, colors, name);
    }
    public void ability(Cards[][] arr, int row) {
        for (int j = 0; j < 5; ++j) {
            if (arr[row][j].isMinion == true) {
                ((Minion) arr[row][j]).health -= 1;
                if (((Minion) arr[row][j]).health <= 0)
                    arr[row][j] = new nullCard();
            }
        }
    }
}

class Winterfell extends Environment {
    public Winterfell(int mana, String description, List<String> colors, String name) {
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
    public int ability(Cards[][] arr, int row) {
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
        if (indexHighestHealth == -1)
            return 1;
        //System.out.println(row);
        int mirrorIndex = -1;
        if (row == 0)
            mirrorIndex = 3;
        if (row == 1)
            mirrorIndex = 2;
        if (row == 2) {
            mirrorIndex = 1;
            //System.out.println(row);
        }
        if (row == 3)
            mirrorIndex = 0;
        //System.out.println(mirrorIndex);
        if (arr[mirrorIndex][indexHighestHealth].isNull == false)
            return -1;
        arr[mirrorIndex][indexHighestHealth] = arr[row][indexHighestHealth];
        arr[row][indexHighestHealth] = new nullCard();
        return 1;
    }
}