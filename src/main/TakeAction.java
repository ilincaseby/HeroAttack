package main;

import fileio.ActionsInput;
import fileio.CardInput;
import fileio.GameInput;

public class TakeAction {
    public void start(PlayersDecks playerDeck, GameInput game) {
        Player playerOne = new Player();
        Player playerTwo = new Player();
        int shuffleSeed = game.getStartGame().getShuffleSeed();
        int startingPlayer = game.getStartGame().getStartingPlayer();
        int indexOne = game.getStartGame().getPlayerOneDeckIdx();
        int indexTwo = game.getStartGame().getPlayerTwoDeckIdx();
        CardInput heroOne = game.getStartGame().getPlayerOneHero();
        CardInput heroTwo = game.getStartGame().getPlayerTwoHero();
        playerOne.makeThatAHero(heroOne);
        playerTwo.makeThatAHero(heroTwo);
        // TODO Put the shuffled in the deck of each player
    }

    public void actions(Player playerOne, Player playerTwo, ActionsInput action) {

    }
}
