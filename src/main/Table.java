package main;

public final class Table {

    public Cards[][] arr;
    public static final int FIRSTPLAYERFRONTROW = 2;
    public static final int FIRSTPLAYERBACKROW = 3;
    public static final int SECONDPLAYERFRONTROW = 1;
    public static final int SECONDPLAYERBACKROW = 0;
    public static final int SIZECOLS = 5;
    private static Table instance = null;
    private Table() {
        arr = new Cards[CommandActionHelper.FOUR][SIZECOLS];
    }

    /**
     * SingleTon pattern constructor.
     * **/
    public static Table getInstance() {
        if (instance == null) {
            instance = new Table();
        }
        return instance;
    }

    /**
     * Method to clear the table after a game is finished.
     * We need this method because of the SingleTon design pattern.
     * **/
    public void clearTable() {
        for (int i = 0; i < CommandActionHelper.FOUR; ++i) {
            for (int j = 0; j < SIZECOLS; ++j) {
                arr[i][j] = new NullCard();
            }
        }
    }

    /**
     * Helper for shiftCards Method.
     * **/
    private void clearPosition(final int i, final int j) {
        arr[i][j] = new NullCard();
    }
    /**
     * Method to call when a card is eliminated.
     * **/
    public void shiftCards(final int i, final int j) {
        for (int k = j; k < CommandActionHelper.FOUR; ++k) {
            arr[i][k] = arr[i][k + 1];
        }
        this.clearPosition(i, CommandActionHelper.FOUR);
    }
}
