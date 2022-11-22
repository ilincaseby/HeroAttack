package main;

public class Table {

    public Cards[][] arr;
    public static int firstPlayerFrontRow = 2;
    public static int firstPlayerBackRow = 3;
    public static int secondPlayerFrontRow = 1;
    public static int secondPlayerBackRow = 0;
    private static Table instance = null;
    private Table() {
        arr = new Cards[4][5];
    }
    public static Table getInstance() {
        if (instance == null)
            instance = new Table();
        return instance;
    }
    public void clearTable() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 5; ++j) {
                arr[i][j] = new NullCard();
            }
        }
    }
    public void clearPosition(int i, int j) {
        arr[i][j] = new NullCard();
    }
    public void shiftCards(int i, int j) {
        for (;j< 4; ++j) {
            arr[i][j] = arr[i][j + 1];
        }
        this.clearPosition(i, 4);
    }
}