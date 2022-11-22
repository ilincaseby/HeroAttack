package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.GameInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TakeAction {

    /**
     * Method to begin the actual game.
     * **/
    public void start(final PlayersDecks playerDeck, final GameInput game,
                      final ArrayNode output, final int noGamesPlayed, final Player playerOne,
                      final Player playerTwo, final MyInteger oneWins, final MyInteger twoWins) {
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
        deepCopyCards(playerDeck.getPlayerOneDecks(), indexOne, playerOne, shuffleSeed);
        deepCopyCards(playerDeck.getPlayerTwoDecks(), indexTwo, playerTwo, shuffleSeed);
        if (startingPlayer == 1) {
            playerOne.turn = true;
        } else {
            playerTwo.turn = true;
        }
        ArrayList<ActionsInput> actions = game.getActions();
        if (!playerOne.inPlayDeck.isEmpty()) {
            playerOne.inHand.add(playerOne.inPlayDeck.remove(0));
        }
        if (!playerTwo.inPlayDeck.isEmpty()) {
            playerTwo.inHand.add(playerTwo.inPlayDeck.remove(0));
        }
        int roundDone = 0;
        int round = 1;
        playerOne.mana = 1;
        playerTwo.mana = 1;
        for (ActionsInput action : actions) {
            String command = action.getCommand();
            if (roundDone == 2) {
                roundDone %= 2;
                if (round < CommandActionHelper.NINE) {
                    playerOne.mana = playerOne.mana + round + 1;
                    playerTwo.mana += round + 1;
                }
                if (round >= CommandActionHelper.NINE) {
                    playerOne.mana += CommandActionHelper.TEN;
                    playerTwo.mana += CommandActionHelper.TEN;
                }
                round++;

            }

            switch (command) {
                case "endPlayerTurn" -> {
                    roundDone++;
                    CommandActionHelper.endTurnForPlayer(playerOne, playerTwo, roundDone);
                }
                case "placeCard" -> {
                    if (playerOne.turn) {
                        CommandActionHelper.placeCardCommand(playerOne, action.getHandIdx(),
                                Table.FIRSTPLAYERFRONTROW, Table.FIRSTPLAYERBACKROW, output);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelper.placeCardCommand(playerTwo, action.getHandIdx(),
                                Table.SECONDPLAYERFRONTROW, Table.SECONDPLAYERBACKROW, output);
                    }
                }
                case "cardUsesAttack" -> {
                    if (playerOne.turn) {
                        //int errorCode = 0;
                        CommandActionHelper.usesAttackCommandHelper(action.getCardAttacker(),
                                action.getCardAttacked(), Table.FIRSTPLAYERFRONTROW,
                                Table.FIRSTPLAYERBACKROW, Table.SECONDPLAYERFRONTROW, output);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelper.usesAttackCommandHelper(action.getCardAttacker(),
                                action.getCardAttacked(), Table.SECONDPLAYERFRONTROW,
                                Table.SECONDPLAYERBACKROW, Table.FIRSTPLAYERFRONTROW, output);
                    }
                }
                case "cardUsesAbility" -> {
                    if (playerOne.turn) {
                        CommandActionHelperModule1.cardUsesAbilityHelper(action.getCardAttacker(),
                                action.getCardAttacked(), output, Table.FIRSTPLAYERFRONTROW,
                                Table.FIRSTPLAYERBACKROW, Table.SECONDPLAYERFRONTROW,
                                Table.SECONDPLAYERBACKROW);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelperModule1.cardUsesAbilityHelper(action.getCardAttacker(),
                                action.getCardAttacked(), output, Table.SECONDPLAYERFRONTROW,
                                Table.SECONDPLAYERBACKROW, Table.FIRSTPLAYERFRONTROW,
                                Table.FIRSTPLAYERBACKROW);
                    }
                }
                case "useAttackHero" -> {
                    if (playerOne.turn) {
                        CommandActionHelperModule1.useAttackHero(playerTwo,
                                Table.SECONDPLAYERFRONTROW, 1, oneWins, action.getCardAttacker(),
                                output);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelperModule1.useAttackHero(playerOne,
                                Table.FIRSTPLAYERFRONTROW, 2, twoWins, action.getCardAttacker(),
                                output);
                    }
                }
                case "useHeroAbility" -> {
                    if (playerOne.turn) {
                        CommandActionHelperModule2.abilityHeroUse(action.getAffectedRow(),
                                playerOne, Table.FIRSTPLAYERFRONTROW, Table.FIRSTPLAYERBACKROW,
                                Table.SECONDPLAYERFRONTROW, Table.SECONDPLAYERBACKROW, output);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelperModule2.abilityHeroUse(action.getAffectedRow(),
                                playerTwo, Table.SECONDPLAYERFRONTROW, Table.SECONDPLAYERBACKROW,
                                Table.FIRSTPLAYERFRONTROW, Table.FIRSTPLAYERBACKROW, output);
                    }
                }
                case "useEnvironmentCard" -> {
                    if (playerOne.turn) {
                        CommandActionHelperModule2.useEnvironmentCard(playerOne,
                                action.getHandIdx(), action.getAffectedRow(),
                                Table.SECONDPLAYERFRONTROW, Table.SECONDPLAYERBACKROW, output);
                    }
                    if (playerTwo.turn) {
                        CommandActionHelperModule2.useEnvironmentCard(playerTwo,
                                action.getHandIdx(), action.getAffectedRow(),
                                Table.FIRSTPLAYERFRONTROW, Table.FIRSTPLAYERBACKROW, output);
                    }
                }
                case "getCardsInHand" -> StatisticInfoHelper.
                        getCardsInHand(action.getPlayerIdx(), playerOne, playerTwo, output);
                case "getPlayerDeck" -> StatisticInfoHelper.
                        getPlayerDeck(action.getPlayerIdx(), playerOne, playerTwo, output);
                case "getCardsOnTable" -> StatisticInfoHelper.
                        getCardsOnTable(output);
                case "getPlayerTurn" -> StatisticInfoHelper.
                        getPlayerTurn(playerOne, output);
                case "getPlayerHero" -> StatisticInfoHelper.
                        getPlayerHero(action.getPlayerIdx(), playerOne, playerTwo, output);
                case "getCardAtPosition" -> StatisticInfoHelper.
                        getCardAtPosition(action.getX(), action.getY(), output);
                case "getPlayerMana" -> StatisticInfoHelper.
                        getPlayerMana(action.getPlayerIdx(), playerOne, playerTwo, output);
                case "getEnvironmentCardsInHand" -> StatisticInfoHelper.
                        getEnvironmentCardsInHand(action.getPlayerIdx(), playerOne, playerTwo,
                                output);
                case "getFrozenCardsOnTable" -> StatisticInfoHelper.
                        getFrozenCardsOnTable(output);
                case "getTotalGamesPlayed" -> StatisticInfoHelper.
                        getTotalGamesPlayed(noGamesPlayed, output);
                case "getPlayerOneWins" -> StatisticInfoHelper.getPlayerWins(1, oneWins, output);
                case "getPlayerTwoWins" -> StatisticInfoHelper.getPlayerWins(2, twoWins, output);
            }
        }
    }

    /**
     * Method to copy an entire chosen by the player in his playDeck.
     * **/
    public void deepCopyCards(final Decks choosingDeck, final int indexDeck,
                              final Player player, final int shuffleSeed) {
        List<Cards> auxDeck = choosingDeck.getDecks().get(indexDeck);
        player.inPlayDeck = new ArrayList<>();
        for (Cards card : auxDeck) {
            if (!card.isNull()) {
                if (card.isMinion()) {
                    Minion minionCard = (Minion) card;
                    if (minionCard.getName().equals("The Cursed One")) {
                        TheCursedOne env = new TheCursedOne(minionCard.getMana(),
                                minionCard.getHealth(),
                                minionCard.getAttackDamage(), minionCard.getDescription(),
                                minionCard.getName(),
                                minionCard.getColors());
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    if (minionCard.getName().equals("Disciple")) {
                        Disciple env = new Disciple(minionCard.getMana(), minionCard.getHealth(),
                                minionCard.getAttackDamage(), minionCard.getDescription(),
                                minionCard.getName(),
                                minionCard.getColors());
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    if (minionCard.getName().equals("Miraj")) {
                        Miraj env = new Miraj(minionCard.getMana(), minionCard.getHealth(),
                                minionCard.getAttackDamage(), minionCard.getDescription(),
                                minionCard.getName(),
                                minionCard.getColors());
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    if (minionCard.getName().equals("The Ripper")) {
                        TheRipper env = new TheRipper(minionCard.getMana(), minionCard.getHealth(),
                                minionCard.getAttackDamage(), minionCard.getDescription(),
                                minionCard.getName(),
                                minionCard.getColors());
                        player.inPlayDeck.add(env);
                        continue;
                    }
                    Minion newMini = new Minion(minionCard.getMana(), minionCard.getHealth(),
                            minionCard.getAttackDamage(), minionCard.getDescription(),
                            minionCard.getName(),
                            minionCard.getColors());
                    player.inPlayDeck.add(newMini);
                }
                if (card.isEnv()) {
                    assert card instanceof Environment;
                    Environment envCard = (Environment) card;
                    if (envCard.getName().equals("Winterfell")) {
                        //System.out.println("is one here");
                        Winterfell aux = new Winterfell(envCard.getMana(),
                                envCard.getDescription(), envCard.getColors(), envCard.getName());
                        player.inPlayDeck.add(aux);
                        continue;
                    }
                    if (envCard.getName().equals("Firestorm")) {
                        FireStorm aux = new FireStorm(envCard.getMana(),
                                envCard.getDescription(), envCard.getColors(), envCard.getName());
                        player.inPlayDeck.add(aux);
                        continue;
                    }
                    if (envCard.getName().equals("Heart Hound")) {
                        HeartHound aux = new HeartHound(envCard.getMana(),
                                envCard.getDescription(), envCard.getColors(), envCard.getName());
                        player.inPlayDeck.add(aux);
                    }
                }
            }
        }
        Random rand = new Random(shuffleSeed);
        Collections.shuffle(player.inPlayDeck, rand);
    }
}
