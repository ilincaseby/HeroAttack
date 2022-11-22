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
    public static void endTurnForPlayer(Player playerOne, Player playerTwo, int roundDone) {
        if (roundDone == 2) {
            if (!playerOne.inPlayDeck.isEmpty())
                playerOne.inHand.add(playerOne.inPlayDeck.remove(0));
            if (!playerTwo.inPlayDeck.isEmpty())
                playerTwo.inHand.add(playerTwo.inPlayDeck.remove(0));
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

    public static void unfroze(Table table, int stRow, int ndRow) {
        for (int i = 0; i < 5; ++i) {
            Cards cardStRow = table.arr[stRow][i];
            Cards cardNdRow = table.arr[ndRow][i];
            if (!cardStRow.isNull && cardStRow.isMinion) {
                Minion minCard = (Minion) cardStRow;
                minCard.resetIce();
                minCard.isValidTurn = true;
            }
            if (!cardNdRow.isNull && cardNdRow.isMinion) {
                Minion minCard = (Minion) cardNdRow;
                minCard.resetIce();
                minCard.isValidTurn = true;
            }
        }
    }

    public static void placeCardCommand(Player player, int handIdx, int frontRow, int backRow, ArrayNode output) {
        if (player.inHand.get(handIdx).isEnv) {
            //System.out.println(((Environment)playerOne.inPlayDeck.get(handIdx)).name);
            output.add(OutputHelper.toStringPlaceCard(handIdx, 1));
            return;
        }
        if (((Minion) player.inHand.get(handIdx)).mana > player.mana) {
            output.add(OutputHelper.toStringPlaceCard(handIdx, 2));
            return;
        }
        Table table = Table.getInstance();
        boolean foundASpace = false;
        for (int k = 0; k < 5; ++k) {
            if (table.arr[frontRow][k].isNull) {
                if (((Minion) player.inHand.get(handIdx)).name.equals("The Ripper")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).name.equals("Miraj")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).name.equals("Goliath")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).name.equals("Warden")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[frontRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
            }
        }
        if (foundASpace) {
            return;
        }
        for (int k = 0; k < 5; ++k) {
            if (table.arr[backRow][k].isNull) {
                //Cards cardTest = playerOne.inPlayDeck.remove(handIdx);
                if (((Minion) player.inHand.get(handIdx)).name.equals("Sentinel")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).name.equals("Berserker")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).name.equals("The Cursed One")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
                if (((Minion) player.inHand.get(handIdx)).name.equals("Disciple")) {
                    player.mana -= ((Minion) player.inHand.get(handIdx)).mana;
                    table.arr[backRow][k] = player.inHand.remove(handIdx);
                    foundASpace = true;
                    break;
                }
            }
        }
        if (foundASpace)
            return;
        output.add(OutputHelper.toStringPlaceCard(handIdx, 3));
    }

    public static void usesAttackCommandHelper(Coordinates attacker, Coordinates attacked, int frontRowMe, int backRowMe, int frontRowDujman, ArrayNode output) {
        if (attacked.getX() == frontRowMe || attacked.getX() == backRowMe) {
            output.add(OutputHelper.usesAttack(1, attacker, attacked));
            return;
        }
        Table table = Table.getInstance();
        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn) {
            output.add(OutputHelper.usesAttack(2, attacker, attacked));
            return;
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
            output.add(OutputHelper.usesAttack(3, attacker, attacked));
            return;
        }
        if (noTanksAttackedTanksExist(frontRowDujman, attacked.getX(), attacked.getY())) {
            output.add(OutputHelper.usesAttack(4, attacker, attacked));
            return;
        }
        int damage = ((Minion) table.arr[attacker.getX()][attacker.getY()]).attackDamage;
        ((Minion) table.arr[attacked.getX()][attacked.getY()]).health -= damage;
        if (((Minion) table.arr[attacked.getX()][attacked.getY()]).health <= 0) {
            table.arr[attacked.getX()][attacked.getY()] = new NullCard();
            table.shiftCards(attacked.getX(), attacked.getY());
        }
        ((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn = false;
    }

    public static boolean noTanksAttackedTanksExist(int auxRow, int attackedX, int attackedY) {
        Table table = Table.getInstance();
        boolean isAttackedLeastOneTank = false;
        boolean areThereAnyTanks = false;
        for (int k = 0; k < 5; ++k) {
            if (!table.arr[auxRow][k].isNull) {
                if (((Minion) table.arr[auxRow][k]).isTank) {
                    areThereAnyTanks = true;
                    if (auxRow == attackedX && k == attackedY)
                        isAttackedLeastOneTank = true;
                }
            }
        }
        return !isAttackedLeastOneTank && areThereAnyTanks;
    }
}
