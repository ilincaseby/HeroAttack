package main;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class StatisticInfoHelper {
    public static void getCardsInHand(int playerIdx, Player playerOne, Player playerTwo, ArrayNode output) {
        Player playerToPassAsArgument;
        if (playerIdx == 1) {
            playerToPassAsArgument = playerOne;
        } else {
            playerToPassAsArgument = playerTwo;
        }
        output.add(OutputHelper.cardsInHand(playerIdx, playerToPassAsArgument));
    }

    public static void getPlayerDeck(int playerIdx, Player playerOne, Player playerTwo, ArrayNode output) {
        Player playerAsArgument;
        if (playerIdx == 1) {
            playerAsArgument = playerOne;
        } else {
            playerAsArgument = playerTwo;
        }
        output.add(OutputHelper.getPlayerDeck(playerIdx, playerAsArgument));
    }

    public static void getCardsOnTable(ArrayNode output) {
        output.add(OutputHelper.getCardsOnTable());
    }

    public static void getPlayerTurn(Player playerOne, ArrayNode output) {
        if (playerOne.turn) {
            output.add(OutputHelper.getPlayerTurn(1));
        } else {
            output.add(OutputHelper.getPlayerTurn(2));
        }
    }

    public static void getPlayerHero(int playerIdx, Player playerOne, Player playerTwo, ArrayNode output) {
        Player playerAsArgument;
        if (playerIdx == 1) {
            playerAsArgument = playerOne;
        } else {
            playerAsArgument = playerTwo;
        }
        output.add(OutputHelper.getPlayerHero(playerIdx, playerAsArgument));
    }

    public static void getCardAtPosition(int x, int y, ArrayNode output) {
        output.add(OutputHelper.getCardAtPosition(x, y));
    }

    public static void getPlayerMana(int playerIdx, Player playerOne, Player playerTwo, ArrayNode output) {
        output.add(OutputHelper.getPlayerMana(playerIdx, playerOne, playerTwo));
    }

    public static void getEnvironmentCardsInHand(int playerIdx, Player playerOne, Player playerTwo, ArrayNode output) {
        if (playerIdx == 1) {
            output.add(OutputHelper.getEnvironmentCardsInHand(playerOne, 1));
        } else {
            output.add(OutputHelper.getEnvironmentCardsInHand(playerTwo, 2));
        }
    }

    public static void getFrozenCardsOnTable(ArrayNode output) {
        output.add(OutputHelper.getFrozenTable());
    }

    public static void getTotalGamesPlayed(int noGamesPlayed, ArrayNode output) {
        output.add(OutputHelper.getTotalGamesPlayed(noGamesPlayed));
    }

    public static void getPlayerWins(int playerIdx, MyInteger wins, ArrayNode output) {
        output.add(OutputHelper.AnnounceNoVictories(playerIdx, wins.getX()));
    }
}
