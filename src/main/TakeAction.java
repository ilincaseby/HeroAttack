package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.Coordinates;
import fileio.GameInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TakeAction {

    public void start(PlayersDecks playerDeck, GameInput game, ArrayNode output, int noGamesPlayed, Player playerOne, Player playerTwo, MyInteger oneWins, MyInteger twoWins) {
        Table table = Table.getInstance();
        table.clearTable();
        int shuffleSeed = game.getStartGame().getShuffleSeed();
        int startingPlayer = game.getStartGame().getStartingPlayer();
        int indexOne = game.getStartGame().getPlayerOneDeckIdx();
        int indexTwo = game.getStartGame().getPlayerTwoDeckIdx();
        CardInput heroOne = game.getStartGame().getPlayerOneHero();
        CardInput heroTwo = game.getStartGame().getPlayerTwoHero();
        playerOne.makeThatAHero(heroOne);
        playerTwo.makeThatAHero(heroTwo);
        // TODO Put the shuffled in the deck of each player
        deepCopyCards(playerDeck.playerOneDecks, indexOne, playerOne, shuffleSeed);
        deepCopyCards(playerDeck.playerTwoDecks, indexTwo, playerTwo, shuffleSeed);
        if (startingPlayer == 1)
            playerOne.turn = true;
        else
            playerTwo.turn = true;
        ArrayList<ActionsInput> actions = game.getActions();
        if (!playerOne.inPlayDeck.isEmpty())
            playerOne.inHand.add(playerOne.inPlayDeck.remove(0));
        if (!playerTwo.inPlayDeck.isEmpty())
            playerTwo.inHand.add(playerTwo.inPlayDeck.remove(0));
        int roundDone = 0;
        int round = 1;
        playerOne.mana = 1;
        playerTwo.mana = 1;
        boolean endgame = false;
        for (int i = 0; i < actions.size(); ++i) {
            if (endgame) {
                break;
            }
            String command = actions.get(i).getCommand();
            if (roundDone == 2) {
                roundDone %= 2;
                if (round < 9) {
                    //System.out.println(playerOne.mana);
                    playerOne.mana = playerOne.mana + round + 1;
                    playerTwo.mana += round + 1;
                }
                if (round >= 9) {
                    playerOne.mana += 10;
                    playerTwo.mana += 10;
                }
                round++;

            }

            switch (command) {
                case "endPlayerTurn":
                    roundDone++;
                    if (roundDone == 2){
                        if (!playerOne.inPlayDeck.isEmpty())
                            playerOne.inHand.add(playerOne.inPlayDeck.remove(0));
                        if (!playerTwo.inPlayDeck.isEmpty())
                        playerTwo.inHand.add(playerTwo.inPlayDeck.remove(0));
                    }
                    boolean check = playerOne.turn;
                    /* It s done for the first player */
                    if (check == true) {
                        playerOne.turn = false;
                        playerTwo.turn = true;
                        unfroze(table, Table.firstPlayerBackRow, Table.firstPlayerFrontRow);
                        playerOne.hero.resetAttack();
                    }
                    /* It s done the turn for second player */
                    if (check == false) {
                        playerOne.turn = true;
                        playerTwo.turn = false;
                        unfroze(table, Table.secondPlayerBackRow, Table.secondPlayerFrontRow);
                        playerTwo.hero.resetAttack();
                    }
                    break;

                case "placeCard":
                    int handIdx = actions.get(i).getHandIdx();
                    int forDeb = 0;
                    if (playerOne.turn) {
                        //Cards card = playerOne.inPlayDeck.get(handIdx);
                        if (playerOne.inHand.size() <= handIdx) {
                            System.out.println("Something is wrong");
                        }
                        if (playerOne.inHand.get(handIdx).isEnv) {
                            //System.out.println(((Environment)playerOne.inPlayDeck.get(handIdx)).name);
                            output.add(OutputHelper.toStringPlaceCard(handIdx, 1));
                            break;
                        }
                        if (((Minion) playerOne.inHand.get(handIdx)).mana > playerOne.mana) {
                            //System.out.println("auch" + " " + ((Minion) playerOne.inHand.get(handIdx)).name + " " + ((Minion) playerOne.inHand.get(handIdx)).mana);
                            //System.out.println(playerOne.mana);

                            output.add(OutputHelper.toStringPlaceCard(handIdx, 2));
                            break;
                        }
                        boolean foundASpace = false;
                        for (int k = 0; k < 5; ++k) {
                            if (table.arr[Table.firstPlayerFrontRow][k].isNull) {
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("The Ripper")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerFrontRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("Miraj")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerFrontRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("Goliath")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerFrontRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("Warden")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerFrontRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                            }
                        }
                        if (foundASpace) {
                            break;
                        }
                        for (int k = 0; k < 5; ++k) {
                            if (table.arr[Table.firstPlayerBackRow][k].isNull) {
                                //Cards cardTest = playerOne.inPlayDeck.remove(handIdx);
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("Sentinel")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerBackRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("Berserker")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerBackRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("The Cursed One")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerBackRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerOne.inHand.get(handIdx)).name.equals("Disciple")) {
                                    playerOne.mana -= ((Minion) playerOne.inHand.get(handIdx)).mana;
                                    table.arr[Table.firstPlayerBackRow][k] = playerOne.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                            }
                        }
                        if (foundASpace)
                            break;
                        output.add(OutputHelper.toStringPlaceCard(handIdx, 3));
                        //System.out.println("nasol");
                        break;
                    }
                    if (playerTwo.turn) {
                        if (playerTwo.inHand.get(handIdx).isEnv) {
                            //System.out.println(((Environment)playerTwo.inPlayDeck.get(handIdx)).name);
                            output.add(OutputHelper.toStringPlaceCard(handIdx, 1));
                            break;
                        }
                        if (((Minion) playerTwo.inHand.get(handIdx)).mana > playerTwo.mana) {
                            output.add(OutputHelper.toStringPlaceCard(handIdx, 2));
                            break;
                        }
                        boolean foundASpace = false;
                        for (int k = 0; k < 5; ++k) {
                            if (table.arr[Table.secondPlayerFrontRow][k].isNull) {
                                if (((Minion) playerTwo.inHand.get(handIdx)).name.equals("The Ripper")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerFrontRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerTwo.inHand.get(handIdx)).name.equals("Miraj")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerFrontRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerTwo.inHand.get(handIdx)).name.equals("Goliath")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerFrontRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion) playerTwo.inHand.get(handIdx)).name.equals("Warden")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerFrontRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                            }
                        }
                        if (foundASpace)
                            break;
                        for (int k = 0; k < 5; ++k) {
                            if (table.arr[Table.secondPlayerBackRow][k].isNull) {
                                if (((Minion)  playerTwo.inHand.get(handIdx)).name.equals("Sentinel")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerBackRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion)  playerTwo.inHand.get(handIdx)).name.equals("Berserker")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerBackRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion)  playerTwo.inHand.get(handIdx)).name.equals("The Cursed One")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerBackRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                                if (((Minion)  playerTwo.inHand.get(handIdx)).name.equals("Disciple")) {
                                    playerTwo.mana -= ((Minion) playerTwo.inHand.get(handIdx)).mana;
                                    table.arr[Table.secondPlayerBackRow][k] = playerTwo.inHand.remove(handIdx);
                                    foundASpace = true;
                                    break;
                                }
                            }
                        }
                        if (foundASpace)
                            break;
                        output.add(OutputHelper.toStringPlaceCard(handIdx, 3));
                        break;
                    }
                    break;

                case "cardUsesAttack":
                    Coordinates attacker = actions.get(i).getCardAttacker();
                    Coordinates attacked = actions.get(i).getCardAttacked();
                    if (playerOne.turn) {
                        //int errorCode = 0;
                        int xAttacked = attacked.getX();
                        if (xAttacked == Table.firstPlayerFrontRow || Table.firstPlayerBackRow == xAttacked) {
                            output.add(OutputHelper.usesAttack(1, attacker, attacked));
                            break;
                        }
                        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn) {
                            output.add(OutputHelper.usesAttack(2, attacker, attacked));
                            break;
                        }
                        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
                            output.add(OutputHelper.usesAttack(3, attacker, attacked));
                            break;
                        }
                        boolean isAttackedLeastOneTank = false;
                        boolean areThereAnyTanks = false;
                        for (int k = 0; k < 5; ++k) {
                            if (!table.arr[Table.secondPlayerFrontRow][k].isNull) {
                                if (((Minion) table.arr[Table.secondPlayerFrontRow][k]).isTank) {
                                    areThereAnyTanks = true;
                                    if (Table.secondPlayerFrontRow == attacked.getX() && k == attacked.getY())
                                        isAttackedLeastOneTank = true;
                                }
                            }
                        }
                        if (!isAttackedLeastOneTank && areThereAnyTanks) {
                            output.add(OutputHelper.usesAttack(4, attacker, attacked));
                            break;
                        }
                        int damage = ((Minion) table.arr[attacker.getX()][attacker.getY()]).attackDamage;
                        ((Minion) table.arr[attacked.getX()][attacked.getY()]).health -= damage;
                        if (((Minion) table.arr[attacked.getX()][attacked.getY()]).health <= 0) {
                            //System.out.println("bye bye friend");
                            table.arr[attacked.getX()][attacked.getY()] = new nullCard();
                            table.shiftCards(attacked.getX(), attacked.getY());
                        }
                        ((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn = false;
                        break;
                    }
                    if (playerTwo.turn) {
                        int xAttacked = attacked.getX();
                        if (xAttacked == Table.secondPlayerFrontRow || Table.secondPlayerBackRow == xAttacked) {
                            output.add(OutputHelper.usesAttack(1, attacker, attacked));
                            break;
                        }
                        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn) {
                            output.add(OutputHelper.usesAttack(2, attacker, attacked));
                            break;
                        }
                        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
                            output.add(OutputHelper.usesAttack(3, attacker, attacked));
                            break;
                        }
                        boolean isAttackedLeastOneTank = false;
                        boolean areThereAnyTanks = false;
                        for (int k = 0; k < 5; ++k) {
                            if (!table.arr[Table.firstPlayerFrontRow][k].isNull) {
                                if (((Minion) table.arr[Table.firstPlayerFrontRow][k]).isTank) {
                                    areThereAnyTanks = true;
                                    if (Table.firstPlayerFrontRow == attacked.getX() && k == attacked.getY())
                                        isAttackedLeastOneTank = true;
                                }
                            }
                        }
                        if (!isAttackedLeastOneTank && areThereAnyTanks) {
                            output.add(OutputHelper.usesAttack(4, attacker, attacked));
                            break;
                        }
                        ((Minion) table.arr[attacked.getX()][attacked.getY()]).health -= ((Minion) table.arr[attacker.getX()][attacker.getY()]).attackDamage;
                        if (((Minion) table.arr[attacked.getX()][attacked.getY()]).health <= 0) {
                            table.arr[attacked.getX()][attacked.getY()] = new nullCard();
                            table.shiftCards(attacked.getX(), attacked.getY());
                        }
                        ((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn = false;
                        break;
                    }
                    break;

                case "cardUsesAbility":
                    Coordinates Cattacker = actions.get(i).getCardAttacker();
                    Coordinates Cattacked = actions.get(i).getCardAttacked();
                    if (((Minion)(table.arr[Cattacker.getX()][Cattacker.getY()])).isFrozen()) {
                        output.add(OutputHelper.usesAbility(1, Cattacker, Cattacked));
                        break;
                    }
                    if (!((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).isValidTurn) {
                        output.add(OutputHelper.usesAbility(2, Cattacker, Cattacked));
                        break;
                    }
                    if (playerOne.turn) {
                        String name = ((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name;
                        if (name.equals("Disciple")) {
                            if (Table.firstPlayerFrontRow != Cattacked.getX() && Table.firstPlayerBackRow != Cattacked.getX()) {
                                output.add(OutputHelper.usesAbility(3, Cattacker, Cattacked));
                                break;
                            }
                        }
                        if (name.equals("The Cursed One") || name.equals("Miraj") || name.equals("The Ripper")) {
                            if (Table.secondPlayerFrontRow != Cattacked.getX() && Table.secondPlayerBackRow != Cattacked.getX()) {
                                output.add(OutputHelper.usesAbility(4, Cattacker, Cattacked));
                                break;
                            }
                            boolean tankOneLeast = false;
                            boolean isAttackedTank = false;
                            for (int k = 0; k < 5; ++k) {
                                if (!table.arr[Table.secondPlayerFrontRow][k].isNull){
                                    if (((Minion) table.arr[Table.secondPlayerFrontRow][k]).isTank) {
                                        tankOneLeast = true;
                                        if (Cattacked.getX() == Table.secondPlayerFrontRow && Cattacked.getY() == k)
                                            isAttackedTank = true;
                                    }
                                }
                            }
                            if (tankOneLeast && !isAttackedTank) {
                                output.add(OutputHelper.usesAbility(5, Cattacker, Cattacked));
                                break;
                            }
                        }

                        //Actions toDo;
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("Disciple")) {
                            ((Disciple) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("Miraj")) {
                            ((Miraj) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("The Cursed One")) {
                            ((TheCursedOne) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("The Ripper")) {
                            ((TheRipper) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]).health <= 0) {
                            table.arr[Cattacked.getX()][Cattacked.getY()] = new nullCard();
                            table.shiftCards(Cattacked.getX(), Cattacked.getY());
                            break;
                        }
                    }
                    if (playerTwo.turn) {
                        String name = ((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name;
                        if (name.equals("Disciple")) {
                            if (Table.secondPlayerFrontRow != Cattacked.getX() && Table.secondPlayerBackRow != Cattacked.getX()) {
                                output.add(OutputHelper.usesAbility(3, Cattacker, Cattacked));
                                break;
                            }
                        }
                        if (name.equals("The Cursed One") || name.equals("Miraj") || name.equals("The Ripper")) {
                            if (Table.firstPlayerFrontRow != Cattacked.getX() && Table.firstPlayerBackRow != Cattacked.getX()) {
                                output.add(OutputHelper.usesAbility(4, Cattacker, Cattacked));
                                break;
                            }
                            boolean tankOneLeast = false;
                            boolean isAttackedTank = false;
                            for (int k = 0; k < 5; ++k) {
                                if (!table.arr[Table.firstPlayerFrontRow][k].isNull) {
                                    if (((Minion) table.arr[Table.firstPlayerFrontRow][k]).isTank) {
                                        tankOneLeast = true;
                                        if (Cattacked.getX() == Table.firstPlayerFrontRow && Cattacked.getY() == k)
                                            isAttackedTank = true;
                                    }
                                }
                            }
                            if (tankOneLeast && !isAttackedTank) {
                                output.add(OutputHelper.usesAbility(5, Cattacker, Cattacked));
                                break;
                            }
                        }

                        //Actions toDo;
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("Disciple")) {
                            ((Disciple) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("Miraj")) {
                            ((Miraj) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("The Cursed One")) {
                            ((TheCursedOne) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacker.getX()][Cattacker.getY()]).name.equals("The Ripper")) {
                            ((TheRipper) table.arr[Cattacker.getX()][Cattacker.getY()]).action(((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]));
                        }
                        if (((Minion) table.arr[Cattacked.getX()][Cattacked.getY()]).health <= 0) {
                            table.arr[Cattacked.getX()][Cattacked.getY()] = new nullCard();
                            table.shiftCards(Cattacked.getX(), Cattacked.getY());
                            break;
                        }
                    }
                    break;
                    //TODO: Shift if u take out a card

                case "useAttackHero":
                    Coordinates badForYou = actions.get(i).getCardAttacker();
                    if (((Minion) table.arr[badForYou.getX()][badForYou.getY()]).isFrozen()) {
                        output.add(OutputHelper.useAttackHero(1, badForYou));
                        break;
                    }
                    if (!((Minion) table.arr[badForYou.getX()][badForYou.getY()]).isValidTurn) {
                        output.add(OutputHelper.useAttackHero(2, badForYou));
                        break;
                    }
                    if (playerOne.turn) {
                        boolean areThereAnyTanks = false;
                        for (int k = 0; k < 5; ++k) {
                            if (!table.arr[Table.secondPlayerFrontRow][k].isNull) {
                                if (((Minion) table.arr[Table.secondPlayerFrontRow][k]).isTank)
                                    areThereAnyTanks = true;
                            }
                        }
                        if (areThereAnyTanks) {
                            output.add(OutputHelper.useAttackHero(3, badForYou));
                            break;
                        }
                        playerTwo.hero.health -= ((Minion) table.arr[badForYou.getX()][badForYou.getY()]).attackDamage;
                        ((Minion) table.arr[badForYou.getX()][badForYou.getY()]).isValidTurn = false;
                        if (playerTwo.hero.health <= 0) {
                            output.add(OutputHelper.announceVictory(1));
                            playerOne.wins++;
                            oneWins.setX(oneWins.getX() + 1);
                            break;
                        }
                    }
                    if (playerTwo.turn) {
                        boolean areThereAnyTanks = false;
                        for (int k = 0; k < 5; ++k) {
                            if (!table.arr[Table.firstPlayerFrontRow][k].isNull) {
                                if (((Minion) table.arr[Table.firstPlayerFrontRow][k]).isTank)
                                    areThereAnyTanks = true;
                            }
                        }
                        if (areThereAnyTanks) {
                            output.add(OutputHelper.useAttackHero(3, badForYou));
                            break;
                        }
                        playerOne.hero.health -= ((Minion) table.arr[badForYou.getX()][badForYou.getY()]).attackDamage;
                        ((Minion) table.arr[badForYou.getX()][badForYou.getY()]).isValidTurn = false;
                        if (playerOne.hero.health <= 0) {
                            output.add(OutputHelper.announceVictory(2));
                            playerTwo.wins++;
                            twoWins.setX(twoWins.getX() + 1);
                            break;
                        }
                    }
                    break;

                case "useHeroAbility":
                    int affectedRow = actions.get(i).getAffectedRow();
                    if (playerOne.turn) {
                        if (playerOne.mana < playerOne.hero.mana) {
                            output.add(OutputHelper.errorHero(1, affectedRow));
                            break;
                        }
                        if (!playerOne.hero.isValidAttack()) {
                            output.add(OutputHelper.errorHero(2, affectedRow));
                            break;
                        }
                        if (playerOne.whichHero == 1 || playerOne.whichHero == 4) {
                            if (affectedRow != Table.secondPlayerFrontRow && affectedRow != Table.secondPlayerBackRow) {
                                output.add(OutputHelper.errorHero(3, affectedRow));
                                break;
                            }
                        }
                        if (playerOne.whichHero == 2 || playerOne.whichHero == 3) {
                            if (affectedRow != Table.firstPlayerBackRow && affectedRow != Table.firstPlayerFrontRow) {
                                output.add(OutputHelper.errorHero(4, affectedRow));
                                break;
                            }
                        }
                        if (playerOne.whichHero == 1) {
                            ((EmpressThorina) playerOne.hero).lowBlow(table.arr, affectedRow);
                        }
                        if (playerOne.whichHero == 2) {
                            ((GeneralKocioraw) playerOne.hero).bloodThrist(table.arr, affectedRow);
                        }
                        if (playerOne.whichHero == 3) {
                            ((KingMudFace) playerOne.hero).earthBorn(table.arr, affectedRow);
                        }
                        if (playerOne.whichHero == 4) {
                            ((LordRoyce) playerOne.hero).subZero(table.arr, affectedRow);
                        }
                        playerOne.hero.hasAttacked();
                        playerOne.mana -= playerOne.hero.mana;
                    }
                    if (playerTwo.turn) {
                        if (playerTwo.mana < playerTwo.hero.mana) {
                            output.add(OutputHelper.errorHero(1, affectedRow));
                            break;
                        }
                        if (!playerTwo.hero.isValidAttack()) {
                            output.add(OutputHelper.errorHero(2, affectedRow));
                            break;
                        }
                        if (playerTwo.whichHero == 1 || playerTwo.whichHero == 4) {
                            if (affectedRow != Table.firstPlayerFrontRow && affectedRow != Table.firstPlayerBackRow) {
                                output.add(OutputHelper.errorHero(3, affectedRow));
                                break;
                            }
                        }
                        if (playerTwo.whichHero == 2 || playerTwo.whichHero == 3) {
                            if (affectedRow != Table.secondPlayerBackRow && affectedRow != Table.secondPlayerFrontRow) {
                                output.add(OutputHelper.errorHero(4, affectedRow));
                                break;
                            }
                        }
                        if (playerTwo.whichHero == 1) {
                            ((EmpressThorina) playerTwo.hero).lowBlow(table.arr, affectedRow);
                        }
                        if (playerTwo.whichHero == 2) {
                            ((GeneralKocioraw) playerTwo.hero).bloodThrist(table.arr, affectedRow);
                        }
                        if (playerTwo.whichHero == 3) {
                            ((KingMudFace) playerTwo.hero).earthBorn(table.arr, affectedRow);
                        }
                        if (playerTwo.whichHero == 4) {
                            ((LordRoyce) playerTwo.hero).subZero(table.arr, affectedRow);
                        }
                        playerTwo.hero.hasAttacked();
                        playerTwo.mana -= playerTwo.hero.mana;
                    }
                    break;

                case "useEnvironmentCard":
                    int handIndex = actions.get(i).getHandIdx();
                    int affectedR = actions.get(i).getAffectedRow();
                    if (playerOne.turn) {
                        if (!playerOne.inHand.get(handIndex).isEnv) {
                            output.add(OutputHelper.errorEnvironment(1, handIndex, affectedR));
                            break;
                        }
                        Environment envCard = ((Environment) playerOne.inHand.get(handIndex));
                        if (playerOne.mana < envCard.mana) {
                            output.add(OutputHelper.errorEnvironment(2, handIndex, affectedR));
                            break;
                        }
                        if (affectedR != Table.secondPlayerBackRow && affectedR != Table.secondPlayerFrontRow) {
                            output.add(OutputHelper.errorEnvironment(3, handIndex,affectedR));
                            break;
                        }
                        if (envCard.name.equals("Heart Hound")) {
                            int done = ((HeartHound) envCard).ability(table.arr, affectedR);
                            if (done == -1) {
                                output.add(OutputHelper.errorEnvironment(4, handIndex, affectedR));
                                break;
                            }
                        }
                        if (envCard.name.equals("Winterfell"))
                            ((Winterfell) envCard).ability(table.arr, affectedR);
                        if (envCard.name.equals("Firestorm"))
                            ((FireStorm) envCard).ability(table.arr, affectedR);
                        playerOne.mana -= envCard.mana;
                        playerOne.inHand.remove(handIndex);
                    }
                    if (playerTwo.turn) {
                        if (!playerTwo.inHand.get(handIndex).isEnv) {
                            output.add(OutputHelper.errorEnvironment(1, handIndex, affectedR));
                            break;
                        }
                        Environment envCard = ((Environment) playerTwo.inHand.get(handIndex));
                        if (playerTwo.mana < envCard.mana) {
                            output.add(OutputHelper.errorEnvironment(2, handIndex, affectedR));
                            break;
                        }
                        if (affectedR != Table.firstPlayerBackRow && affectedR != Table.firstPlayerFrontRow) {
                            output.add(OutputHelper.errorEnvironment(3, handIndex,affectedR));
                            break;
                        }
                        if (envCard.name.equals("Heart Hound")) {
                            //System.out.println(affectedR);
                            int done = ((HeartHound) envCard).ability(table.arr, affectedR);
                            if (done == -1) {
                                output.add(OutputHelper.errorEnvironment(4, handIndex, affectedR));
                                break;
                            }
                        }
                        if (envCard.name.equals("Winterfell"))
                            ((Winterfell) envCard).ability(table.arr, affectedR);
                        if (envCard.name.equals("Firestorm"))
                            ((FireStorm) envCard).ability(table.arr, affectedR);
                        playerTwo.mana -= envCard.mana;
                        playerTwo.inHand.remove(handIndex);
                        //playerOne
                    }
                    break;

                case "getCardsInHand":
                    int playerIdx = actions.get(i).getPlayerIdx();
                    Player playerToPassAsArgument;
                    if (playerIdx == 1) {
                        playerToPassAsArgument = playerOne;
                    } else {
                        playerToPassAsArgument = playerTwo;
                    }
                    output.add(OutputHelper.cardsInHand(playerIdx, playerToPassAsArgument));
                    break;

                case "getPlayerDeck":
                    int playerIndex = actions.get(i).getPlayerIdx();
                    Player playerForDebug;
                    if (playerIndex == 1) {
                        playerForDebug = playerOne;
                    } else {
                        playerForDebug = playerTwo;
                    }
                    output.add(OutputHelper.getPlayerDeck(playerIndex, playerForDebug));
                    break;

                case "getCardsOnTable":
                    output.add(OutputHelper.getCardsOnTable());
                    break;

                case "getPlayerTurn":
                    if (playerOne.turn) {
                        output.add(OutputHelper.getPlayerTurn(1));
                    } else {
                        output.add(OutputHelper.getPlayerTurn(2));
                    }
                    break;

                case "getPlayerHero":
                    int which = actions.get(i).getPlayerIdx();
                    Player chosenToShowGreatness;
                    if (which == 1) {
                        chosenToShowGreatness = playerOne;
                    } else {
                        chosenToShowGreatness = playerTwo;
                    }
                    output.add(OutputHelper.getPlayerHero(which, chosenToShowGreatness));
                    break;

                case "getCardAtPosition":
                    output.add(OutputHelper.getCardAtPosition(actions.get(i).getX(), actions.get(i).getY()));
                    break;
                case "getPlayerMana":
                    output.add(OutputHelper.getPlayerMana(actions.get(i).getPlayerIdx(), playerOne, playerTwo));
                    break;
                case "getEnvironmentCardsInHand":
                    if (actions.get(i).getPlayerIdx() == 1) {
                        output.add(OutputHelper.getEnvironmentCardsInHand(playerOne, 1));
                    } else {
                        output.add(OutputHelper.getEnvironmentCardsInHand(playerTwo, 2));
                    }
                    break;
                case "getFrozenCardsOnTable":
                    output.add(OutputHelper.getFrozenTable());
                    break;

                case "getTotalGamesPlayed":
                    output.add(OutputHelper.getTotalGamesPlayed(noGamesPlayed));
                    break;

                case "getPlayerOneWins":
                    output.add(OutputHelper.AnnounceNoVictories(1, oneWins.getX()));
                    break;

                case "getPlayerTwoWins":
                    output.add(OutputHelper.AnnounceNoVictories(2, twoWins.getX()));
                    break;
            }
        }
    }

    public void unfroze(Table table, int stRow, int ndRow) {
        for (int i = 0; i < 5; ++i) {
            Cards card = table.arr[stRow][i];
            if (!card.isNull && card.isMinion) {
                Minion minCard = (Minion) card;
                minCard.resetIce();
                minCard.isValidTurn = true;
            }
        }
        for (int i = 0; i < 5; ++i) {
            Cards card = table.arr[ndRow][i];
            if (!card.isNull && card.isMinion) {
                Minion minCard = (Minion) card;
                minCard.resetIce();
                minCard.isValidTurn = true;
            }
        }
    }

    public void actions(Player playerOne, Player playerTwo, ActionsInput action) {

    }
    public void deepCopyCards(Decks choosingDeck, int indexDeck, Player player, int shuffleSeed) {
        List<Cards> auxDeck = choosingDeck.decks.get(indexDeck);
        player.inPlayDeck = new ArrayList<>();
        for (int i = 0; i < auxDeck.size(); ++i) {
            Cards card = auxDeck.get(i);
            if (!card.isNull) {
                if (card.isMinion) {
                    Minion minionCard = (Minion) card;
                    if (minionCard.name.equals("The Cursed One")) {
                        TheCursedOne env = new TheCursedOne(minionCard.mana, minionCard.health, minionCard.attackDamage, minionCard.description, minionCard.name, minionCard.colors);
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    if (minionCard.name.equals("Disciple")) {
                        Disciple env = new Disciple(minionCard.mana, minionCard.health, minionCard.attackDamage, minionCard.description, minionCard.name, minionCard.colors);
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    if (minionCard.name.equals("Miraj")) {
                        Miraj env = new Miraj(minionCard.mana, minionCard.health, minionCard.attackDamage, minionCard.description, minionCard.name, minionCard.colors);
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    if (minionCard.name.equals("The Ripper")) {
                        TheRipper env = new TheRipper(minionCard.mana, minionCard.health, minionCard.attackDamage, minionCard.description, minionCard.name, minionCard.colors);
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    Minion newMini = new Minion(minionCard.mana, minionCard.health, minionCard.attackDamage, minionCard.description, minionCard.name, minionCard.colors);
                    player.inPlayDeck.add(newMini);
                }
                if (card.isEnv) {
                    Environment envCard = (Environment) card;
                    if (envCard.name.equals("Winterfell")) {
                        //System.out.println("is one here");
                        Winterfell aux = new Winterfell(envCard.mana, envCard.description, envCard.colors, envCard.name);
                        player.inPlayDeck.add(aux);
                        continue;
                    }
                    if (envCard.name.equals("Firestorm")) {
                        FireStorm aux = new FireStorm(envCard.mana, envCard.description, envCard.colors, envCard.name);
                        player.inPlayDeck.add(aux);
                        continue;
                    }
                    if (envCard.name.equals("Heart Hound")) {
                        HeartHound aux = new HeartHound(envCard.mana, envCard.description, envCard.colors, envCard.name);
                        player.inPlayDeck.add(aux);
                    }
                }
            }
        }
        Random rand = new Random(shuffleSeed);
        Collections.shuffle(player.inPlayDeck, rand);
    }
}
