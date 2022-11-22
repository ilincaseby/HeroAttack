package main;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class StatisticInfoHelper {
    /**
     * Add to output cards of a player in hand.
     * **/
    public static void getCardsInHand(final int playerIdx, final Player playerOne,
                                      final Player playerTwo, final ArrayNode output) {
        Player playerToPassAsArgument;
        if (playerIdx == 1) {
            playerToPassAsArgument = playerOne;
        } else {
            playerToPassAsArgument = playerTwo;
        }
        output.add(OutputHelper.cardsInHand(playerIdx, playerToPassAsArgument));
    }

    /**
     * Add to output deck of a player.
     * **/
    public static void getPlayerDeck(final int playerIdx, final Player playerOne,
                                     final Player playerTwo, final ArrayNode output) {
        Player playerAsArgument;
        if (playerIdx == 1) {
            playerAsArgument = playerOne;
        } else {
            playerAsArgument = playerTwo;
        }
        output.add(OutputHelper.getPlayerDeck(playerIdx, playerAsArgument));
    }

    /**
     * Add to output cards that are on the table.
     * **/
    public static void getCardsOnTable(final ArrayNode output) {
        output.add(OutputHelper.getCardsOnTable());
    }

    /**
     * Add to output which player is in turn.
     * **/
    public static void getPlayerTurn(final Player playerOne, final ArrayNode output) {
        if (playerOne.turn) {
            output.add(OutputHelper.getPlayerTurn(1));
        } else {
            output.add(OutputHelper.getPlayerTurn(2));
        }
    }

    /**
     * Add to output hero of a player.
     * **/
    public static void getPlayerHero(final int playerIdx, final Player playerOne,
                                     final Player playerTwo, final ArrayNode output) {
        Player playerAsArgument;
        if (playerIdx == 1) {
            playerAsArgument = playerOne;
        } else {
            playerAsArgument = playerTwo;
        }
        output.add(OutputHelper.getPlayerHero(playerIdx, playerAsArgument));
    }

    /**
     * Add to output card at a certain position.
     * **/
    public static void getCardAtPosition(final int x, final int y, final ArrayNode output) {
        output.add(OutputHelper.getCardAtPosition(x, y));
    }

    /**
     * Add to output mana value of a player.
     * **/
    public static void getPlayerMana(final int playerIdx, final Player playerOne,
                                     final Player playerTwo, final ArrayNode output) {
        output.add(OutputHelper.getPlayerMana(playerIdx, playerOne, playerTwo));
    }

    /**
     * Add to output number of environments cards in hand.
     * **/
    public static void getEnvironmentCardsInHand(final int playerIdx, final Player playerOne,
                                                 final Player playerTwo, final ArrayNode output) {
        if (playerIdx == 1) {
            output.add(OutputHelper.getEnvironmentCardsInHand(playerOne, 1));
        } else {
            output.add(OutputHelper.getEnvironmentCardsInHand(playerTwo, 2));
        }
    }

    /**
     * Add to output number of frozen cards.
     * **/
    public static void getFrozenCardsOnTable(final ArrayNode output) {
        output.add(OutputHelper.getFrozenTable());
    }

    /**
     * Add to output number of games played.
     * **/
    public static void getTotalGamesPlayed(final int noGamesPlayed, final ArrayNode output) {
        output.add(OutputHelper.getTotalGamesPlayed(noGamesPlayed));
    }

    /**
     * Add to output number of wins of a player.
     * **/
    public static void getPlayerWins(final int playerIdx, final MyInteger wins,
                                     final ArrayNode output) {
        output.add(OutputHelper.announceNoVictories(playerIdx, wins.getX()));
    }
}
