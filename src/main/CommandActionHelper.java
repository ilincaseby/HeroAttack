package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;

/*
* Static function to reset the freeze
* cards and give them the possibility to attack again.
* Also it switches the turn of players and if a round
* is complete take a card from deck.
* */
public class CommandActionHelper {

    public static final int negOne = -1;
    public static final int Zero = 0;
    public static final int One = 1;
    public static final int Two = 2;
    public static final int Three = 3;
    public static final int Four = 4;
    public static final int Five = 5;
    public static final int thirty = 30;
    public static final int Nine = 9;
    public static final int Ten = 10;

    //private CommandActionHelper() { }

    /**
     * Helper for ending a turn command.
     * **/
    public static void endTurnForPlayer(final Player playerOne, final Player playerTwo,
                                        final int roundDone) {
        if (roundDone == 2) {
            if (!playerOne.inPlayDeck.isEmpty()) {
                playerOne.inHand.add(playerOne.inPlayDeck.remove(0));
            }
            if (!playerTwo.inPlayDeck.isEmpty()) {
                playerTwo.inHand.add(playerTwo.inPlayDeck.remove(0));
            }
        }
        Table table = Table.getInstance();
        boolean check = playerOne.turn;
        /* It s done for the first player */
        if (check) {
            playerOne.turn = false;
            playerTwo.turn = true;
            unfroze(table, Table.firstPlayerBackRow, Table.firstPlayerFrontRow);
            playerOne.hero.resetAttack();
        }
        /* It s done the turn for second player */
        if (!check) {
            playerOne.turn = true;
            playerTwo.turn = false;
            unfroze(table, Table.secondPlayerBackRow, Table.secondPlayerFrontRow);
            playerTwo.hero.resetAttack();
        }
    }

    /**
     * Method to call when a round is done and all
     * the cards reset their valid attack.
     * **/
    public static void unfroze(final Table table, final int stRow, final int ndRow) {
        for (int i = 0; i < Table.sizeCols; ++i) {
            Cards cardStRow = table.arr[stRow][i];
            Cards cardNdRow = table.arr[ndRow][i];
            if (!cardStRow.isNull() && cardStRow.isMinion()) {
                Minion minCard = (Minion) cardStRow;
                minCard.resetIce();
                minCard.setValidTurn(true);
            }
            if (!cardNdRow.isNull() && cardNdRow.isMinion()) {
                Minion minCard = (Minion) cardNdRow;
                minCard.resetIce();
                minCard.setValidTurn(true);
            }
        }
    }

    /**
     * Method to place a card
     * **/
    public static void placeCardCommand(final Player player, final int handIdx, final int frontRow,
                                        final int backRow, final ArrayNode output) {
        if (player.inHand.get(handIdx).isEnv()) {
            //System.out.println(((Environment)playerOne.inPlayDeck.get(handIdx)).name);
            output.add(OutputHelper.toStringPlaceCard(handIdx, One));
            return;
        }
        if (((Minion) player.inHand.get(handIdx)).getMana() > player.mana) {
            output.add(OutputHelper.toStringPlaceCard(handIdx, Two));
            return;
        }
        Table table = Table.getInstance();
        boolean foundASpace = false;
        for (int k = 0; k < Table.sizeCols; ++k) {
            if (table.arr[frontRow][k].isNull()) {
                if (((Minion) player.inHand.get(handIdx)).getName().equals("The Ripper")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).getName().equals("Miraj")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).getName().equals("Goliath")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).getName().equals("Warden")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
            }
        }
        if (foundASpace) {
            return;
        }
        for (int k = 0; k < Table.sizeCols; ++k) {
            if (table.arr[backRow][k].isNull()) {
                //Cards cardTest = playerOne.inPlayDeck.remove(handIdx);
                if (((Minion) player.inHand.get(handIdx)).getName().equals("Sentinel")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).getName().equals("Berserker")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).getName().equals("The Cursed One")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).getName().equals("Disciple")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).getMana();
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
            }
        }
        if (foundASpace) {
            return;
        }
        output.add(OutputHelper.toStringPlaceCard(handIdx, Three));
    }

    /**
     * Method to attack a card
     * **/
    public static void usesAttackCommandHelper(final Coordinates attacker,
                                               final Coordinates attacked,
                                               final int frontRowMe, final int backRowMe,
                                               final int frontRowDujman, final ArrayNode output) {
        if (attacked.getX() == frontRowMe || attacked.getX() == backRowMe) {
            output.add(OutputHelper.usesAttack(One, attacker, attacked));
            return;
        }
        Table table = Table.getInstance();
        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn()) {
            output.add(OutputHelper.usesAttack(Two, attacker, attacked));
            return;
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
            output.add(OutputHelper.usesAttack(Three, attacker, attacked));
            return;
        }
        if (noTanksAttackedTanksExist(frontRowDujman, attacked.getX(), attacked.getY())) {
            output.add(OutputHelper.usesAttack(Four, attacker, attacked));
            return;
        }
        int damage = ((Minion) table.arr[attacker.getX()][attacker.getY()]).getAttackDamage();
        ((Minion) table.arr[attacked.getX()][attacked.getY()]).setHealth(((Minion) table.arr[attacked.getX()][attacked.getY()]).getHealth() - damage);
        if (((Minion) table.arr[attacked.getX()][attacked.getY()]).getHealth() <= 0) {
            table.arr[attacked.getX()][attacked.getY()] = new NullCard();
            table.shiftCards(attacked.getX(), attacked.getY());
        }
        ((Minion) table.arr[attacker.getX()][attacker.getY()]).setValidTurn(false);
    }

    /**
     * Method to see if tanks exists and they are attacked
     * **/
    public static boolean noTanksAttackedTanksExist(final int auxRow, final int attackedX,
                                                    final int attackedY) {
        Table table = Table.getInstance();
        boolean isAttackedLeastOneTank = false;
        boolean areThereAnyTanks = false;
        for (int k = 0; k < Table.sizeCols; ++k) {
            if (!table.arr[auxRow][k].isNull()) {
                if (((Minion) table.arr[auxRow][k]).isTank()) {
                    areThereAnyTanks = true;
                    if (auxRow == attackedX && k == attackedY) {
                        isAttackedLeastOneTank = true;
                    }
                }
            }
        }
        return !isAttackedLeastOneTank && areThereAnyTanks;
    }
}
