package main;

import java.util.List;

public class Hero {
    int mana;
    String description;
    List<String> colors;
    int health = 30;
    private boolean validAttack = true;

    public Hero(int mana, String description, List<String> colors) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
    }

    public boolean isValidAttack() {
        return validAttack;
    }

    public void resetAttack() {
        validAttack = true;
    }

    public void hasAttacked() {
        validAttack = false;
    }
}

class LordRoyce extends Hero {
    public LordRoyce(int mana, String description, List<String> colors) {
        super(mana, description, colors);
    }
    public void subZero(Cards[][] arr, int row) {
        int indexHighestAttackLevel = -1;
        int highestAttackDamage = -1;
        for (int j = 0; j < 5; ++j) {
            if (!arr[row][j].isNull) {
                if (((Minion) arr[row][j]).attackDamage > highestAttackDamage) {
                    highestAttackDamage = ((Minion) arr[row][j]).attackDamage;
                    indexHighestAttackLevel = j;
                }
            }
        }
        if (indexHighestAttackLevel == -1)
            return;
        ((Minion) arr[row][indexHighestAttackLevel]).freeze();
    }
}

class EmpressThorina extends Hero {
    public EmpressThorina(int mana, String description, List<String> colors) {
        super(mana, description, colors);
    }
    public void lowBlow(Cards[][] arr, int row) {
        int indexHighestHealth = -1;
        int highestHealth = -1;
        for (int j = 0; j < 5; ++j) {
            if (!arr[row][j].isNull) {
                if (((Minion) arr[row][j]).health > highestHealth) {
                    highestHealth = ((Minion) arr[row][j]).health;
                    indexHighestHealth = j;
                }
            }
        }
        if (indexHighestHealth == -1)
            return;
        arr[row][indexHighestHealth] = new nullCard();
    }
}

class KingMudFace extends Hero {
    public KingMudFace(int mana, String description, List<String> colors) {
        super(mana, description, colors);
    }
    public void earthBorn(Cards[][] arr, int row) {
        for (int j = 0; j < 5; ++j) {
            if (!arr[row][j].isNull) {
                ((Minion) arr[row][j]).health++;
            }
        }
    }
}

class GeneralKocioraw extends Hero {
    public GeneralKocioraw(int mana, String description, List<String> colors) {
        super(mana, description, colors);
    }
    public void bloodThrist(Cards[][] arr, int row) {
        for (int j = 0; j < 5; ++j) {
            if (!arr[row][j].isNull) {
                ((Minion) arr[row][j]).attackDamage++;
            }
        }
    }
}



