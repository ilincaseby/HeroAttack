package main;

public final class Table {

    public Cards[][] arr;
    public static final int firstPlayerFrontRow = 2;
    public static final int firstPlayerBackRow = 3;
    public static final int secondPlayerFrontRow = 1;
    public static final int secondPlayerBackRow = 0;
    public static final int sizeCols = 5;
    private static Table instance = null;
    private Table() {
        arr = new Cards[CommandActionHelper.Four][sizeCols];
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
        for (int i = 0; i < CommandActionHelper.Four; ++i) {
            for (int j = 0; j < sizeCols; ++j) {
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
        for (int k = j; k < CommandActionHelper.Four; ++k) {
            arr[i][k] = arr[i][k + 1];
        }
        this.clearPosition(i, CommandActionHelper.Four);
    }
}
