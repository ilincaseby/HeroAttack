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
                    CommandActionHelper.endTurnForPlayer(playerOne, playerTwo, roundDone);
                    break;

                case "placeCard":
                    int handIdx = actions.get(i).getHandIdx();
                    int forDeb = 0;
                    if (playerOne.turn) {
                        CommandActionHelper.placeCardCommand(playerOne, handIdx, Table.firstPlayerFrontRow, Table.firstPlayerBackRow, output);
                        break;
                    }
                    if (playerTwo.turn) {
                        CommandActionHelper.placeCardCommand(playerTwo, handIdx, Table.secondPlayerFrontRow, Table.secondPlayerBackRow, output);
                        break;
                    }
                    break;

                case "cardUsesAttack":
                    if (playerOne.turn) {
                        //int errorCode = 0;
                        CommandActionHelper.usesAttackCommandHelper(actions.get(i).getCardAttacker(), actions.get(i).getCardAttacked(), Table.firstPlayerFrontRow, Table.firstPlayerBackRow, Table.secondPlayerFrontRow, output);
                        break;
                    }
                    if (playerTwo.turn) {
                        CommandActionHelper.usesAttackCommandHelper(actions.get(i).getCardAttacker(), actions.get(i).getCardAttacked(), Table.secondPlayerFrontRow, Table.secondPlayerBackRow, Table.firstPlayerFrontRow, output);
                        break;
                    }
                    break;

                case "cardUsesAbility":
                    if (playerOne.turn) {
                        CommandActionHelperModule1.cardUsesAbilityHelper(actions.get(i).getCardAttacker(), actions.get(i).getCardAttacked(), output, Table.firstPlayerFrontRow, Table.firstPlayerBackRow, Table.secondPlayerFrontRow, Table.secondPlayerBackRow);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelperModule1.cardUsesAbilityHelper(actions.get(i).getCardAttacker(), actions.get(i).getCardAttacked(), output, Table.secondPlayerFrontRow, Table.secondPlayerBackRow, Table.firstPlayerFrontRow, Table.firstPlayerBackRow);
                    }
                    break;

                case "useAttackHero":
                    if (playerOne.turn) {
                        CommandActionHelperModule1.useAttackHero(playerTwo, Table.secondPlayerFrontRow, 1, oneWins, actions.get(i).getCardAttacker(), output);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelperModule1.useAttackHero(playerOne, Table.firstPlayerFrontRow, 2, twoWins, actions.get(i).getCardAttacker(), output);
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
                            output.add(OutputHelper.errorEnvironment(3, handIndex, affectedR));
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
                            output.add(OutputHelper.errorEnvironment(3, handIndex, affectedR));
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
