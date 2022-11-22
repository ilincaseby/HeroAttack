package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public final class OutputHelper {

    private OutputHelper() { }

    /**
     * Method to call when an add to output in case of error
     * at placeCard command is desired.
     * **/
    public static ObjectNode toStringPlaceCard(final int handIdx, final int whichError) {
        if (whichError == 0) {
            return null;
        }
        String err = "";
        if (whichError == CommandActionHelper.One) {
            err = "Cannot place environment card on table.";
        }
        if (whichError == CommandActionHelper.Two) {
            err = "Not enough mana to place card on table.";
        }
        if (whichError == CommandActionHelper.Three) {
            err = "Cannot place card on table since row is full.";
        }

        ObjectNode newNode = (new ObjectMapper()).createObjectNode();
        newNode.put("command", "placeCard");
        newNode.put("handIdx", handIdx);
        newNode.put("error", err);
        return newNode;
    }

    /**
     * Method to call when an add to output in case of error
     * useAttackCard command is desired.
     * **/
    public static ObjectNode usesAttack(final int whichError,
                                        final Coordinates attacker, final Coordinates attacked) {
        ObjectMapper format = new ObjectMapper();
        ObjectNode node = format.createObjectNode();
        node.put("command", "cardUsesAttack");
        ObjectNode coordattacker = format.createObjectNode();
        coordattacker.put("x", attacker.getX());
        coordattacker.put("y", attacker.getY());
        ObjectNode coordattacked = format.createObjectNode();
        coordattacked.put("x", attacked.getX());
        coordattacked.put("y", attacked.getY());
        node.set("cardAttacker", coordattacker);
        node.set("cardAttacked", coordattacked);
        String error = "";
        if (whichError == CommandActionHelper.One) {
            error = "Attacked card does not belong to the enemy.";
        }
        if (whichError == CommandActionHelper.Two) {
            error = "Attacker card has already attacked this turn.";
        }
        if (whichError == CommandActionHelper.Three) {
            error = "Attacker card is frozen.";
        }
        if (whichError == CommandActionHelper.Four) {
            error = "Attacked card is not of type 'Tank'.";
        }
        node.put("error", error);
        return node;
    }

    /**
     * Method to call when an add to output in case of error
     * cardUseAbility command is desired.
     * **/
    public static ObjectNode usesAbility(final int whichError,
                                         final Coordinates attacker, final Coordinates attacked) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode toAdd = mapper.createObjectNode();
        toAdd.put("command", "cardUsesAbility");
        ObjectNode coordEr = mapper.createObjectNode();
        coordEr.put("x", attacker.getX());
        coordEr.put("y", attacker.getY());
        toAdd.set("cardAttacker", coordEr);
        ObjectNode coordEd = mapper.createObjectNode();
        coordEd.put("x", attacked.getX());
        coordEd.put("y", attacked.getY());
        toAdd.set("cardAttacked", coordEd);
        String err = "";
        if (whichError == CommandActionHelper.One) {
            err = "Attacker card is frozen.";
        }
        if (whichError == CommandActionHelper.Two) {
            err = "Attacker card has already attacked this turn.";
        }
        if (whichError == CommandActionHelper.Three) {
            err = "Attacked card does not belong to the current player.";
        }
        if (whichError == CommandActionHelper.Four) {
            err = "Attacked card does not belong to the enemy.";
        }
        if (whichError == CommandActionHelper.Five) {
            err = "Attacked card is not of type 'Tank'.";
        }
        toAdd.put("error", err);
        return toAdd;
    }

    /**
     * Method to call when an add to output in case of error
     * useAttackHero is desired.
     * **/
    public static ObjectNode useAttackHero(final int whichError, final Coordinates attacker) {
        String err = "";
        if (whichError == CommandActionHelper.One) {
            err = "Attacker card is frozen.";
        }
        if (whichError == CommandActionHelper.Two) {
            err = "Attacker card has already attacked this turn.";
        }
        if (whichError == CommandActionHelper.Three) {
            err = "Attacked card is not of type 'Tank'.";
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "useAttackHero");
        ObjectNode attNode = mapper.createObjectNode();
        attNode.put("x", attacker.getX());
        attNode.put("y", attacker.getY());
        node.set("cardAttacker", attNode);
        node.put("error", err);
        return node;
    }

    /**
     * Method to call when an add to output in case of
     * winning.
     * **/
    public static ObjectNode announceVictory(final int whichPlayer) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        if (whichPlayer == 1) {
            node.put("gameEnded", "Player one killed the enemy hero.");
            return node;
        }
        node.put("gameEnded", "Player two killed the enemy hero.");
        return node;
    }

    /**
     * Method to call when an add to output in case of error
     * useHeroAbility command is desired.
     * **/
    public static ObjectNode errorHero(final int whichCase, final int affectedRow) {
        String err = "";
        if (whichCase == CommandActionHelper.One) {
            err = "Not enough mana to use hero's ability.";
        }
        if (whichCase == CommandActionHelper.Two) {
            err = "Hero has already attacked this turn.";
        }
        if (whichCase == CommandActionHelper.Three) {
            err = "Selected row does not belong to the enemy.";
        }
        if (whichCase == CommandActionHelper.Four) {
            err = "Selected row does not belong to the current player.";
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "useHeroAbility");
        node.put("affectedRow", affectedRow);
        node.put("error", err);
        return node;
    }

    /**
     * Method to call when an add to output in case of error
     * cardUseEnivronment is desired.
     * **/
    public static ObjectNode errorEnvironment(final int whichCase,
                                              final int handIdx, final int affectedRow) {
        String err = "";
        if (whichCase == CommandActionHelper.One) {
            err = "Chosen card is not of type environment.";
        }
        if (whichCase == CommandActionHelper.Two) {
            err = "Not enough mana to use environment card.";
        }
        if (whichCase == CommandActionHelper.Three) {
            err = "Chosen row does not belong to the enemy.";
        }
        if (whichCase == CommandActionHelper.Four) {
            err = "Cannot steal enemy card since the player's row is full.";
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "useEnvironmentCard");
        node.put("handIdx", handIdx);
        node.put("affectedRow", affectedRow);
        node.put("error", err);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * cards in Hand is desired.
     * **/
    public static ObjectNode cardsInHand(final int playerIndex, final Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getCardsInHand");
        node.put("playerIdx", playerIndex);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.inHand.size(); ++i) {
            ObjectNode nodeCards = mapper.createObjectNode();
            if (player.inHand.get(i).isMinion()) {
                Minion minCard = ((Minion) player.inHand.get(i));
                duplicateErrorQuickFix(mapper, nodeCards, minCard);
            }
            if (player.inHand.get(i).isEnv()) {
                Environment envCard = ((Environment) player.inHand.get(i));
                duplicateCodeErrorQuickFix1(mapper, nodeCards, envCard);
            }
            arrayNode.add(nodeCards);
        }
        node.set("output", arrayNode);
        return node;
    }

    private static void duplicateCodeErrorQuickFix1(final ObjectMapper mapper,
                                                    final ObjectNode nodeCards,
                                                    final Environment envCard) {
        nodeCards.put("mana", envCard.getMana());
        nodeCards.put("description", envCard.getDescription());
        ArrayNode arr = mapper.createArrayNode();
        for (int k = 0; k < envCard.getColors().size(); ++k) {
            arr.add(envCard.getColors().get(k));
        }
        nodeCards.set("colors", arr);
        nodeCards.put("name", envCard.getName());
    }

    private static void duplicateErrorQuickFix(final ObjectMapper mapper,
                                               final ObjectNode nodeCards, final Minion minCard) {
        nodeCards.put("mana", minCard.mana);
        nodeCards.put("attackDamage", minCard.attackDamage);
        nodeCards.put("health", minCard.health);
        nodeCards.put("description", minCard.description);
        ArrayNode arr = mapper.createArrayNode();
        for (int k = 0; k < minCard.colors.size(); ++k) {
            arr.add(minCard.colors.get(k));
        }
        nodeCards.set("colors", arr);
        nodeCards.put("name", minCard.name);
    }

    /**
     * Add to output in case of deck of a player
     * is desired
     * **/
    public static ObjectNode getPlayerDeck(final int index, final Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerDeck");
        node.put("playerIdx", index);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.inPlayDeck.size(); ++i) {
            ObjectNode nodeCards = mapper.createObjectNode();
            if (player.inPlayDeck.get(i).isMinion()) {
                Minion minCard = ((Minion) player.inPlayDeck.get(i));
                duplicateErrorQuickFix(mapper, nodeCards, minCard);
            }
            if (player.inPlayDeck.get(i).isEnv()) {
                Environment envCard = ((Environment) player.inPlayDeck.get(i));
                duplicateCodeErrorQuickFix1(mapper, nodeCards, envCard);
            }
            arrayNode.add(nodeCards);
        }
        node.set("output", arrayNode);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * cards on the table is desired.
     * **/
    public static ObjectNode getCardsOnTable() {
        Table table = Table.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getCardsOnTable");
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < CommandActionHelper.Four; ++i) {
            ArrayNode arrayNode1 = mapper.createArrayNode();
            for (int j = 0; j < Table.sizeCols; ++j) {
                if (!table.arr[i][j].isNull()) {
                    ObjectNode nodeCard = mapper.createObjectNode();
                    Minion minCard = ((Minion) table.arr[i][j]);
                    duplicateErrorQuickFix(mapper, nodeCard, minCard);
                    arrayNode1.add(nodeCard);
                }
            }
            arrayNode.add(arrayNode1);
        }
        node.set("output", arrayNode);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * playerTurn is desired.
     * **/
    public static ObjectNode getPlayerTurn(final int whichOne) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerTurn");
        node.put("output", whichOne);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * hero of player is desired.
     * **/
    public static ObjectNode getPlayerHero(final int which, final Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerHero");
        node.put("playerIdx", which);
        ObjectNode auxNode = mapper.createObjectNode();
        auxNode.put("mana", player.hero.getMana());
        auxNode.put("description", player.hero.getDescription());
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.hero.getColors().size(); ++i) {
            arrayNode.add(player.hero.getColors().get(i));
        }
        auxNode.set("colors", arrayNode);
        auxNode.put("name", player.hero.getName());
        auxNode.put("health", player.hero.getHealth());
        node.set("output", auxNode);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * cards at a specific position is desired.
     * **/
    public static ObjectNode getCardAtPosition(final int x, final int y) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getCardAtPosition");
        node.put("x", x);
        node.put("y", y);
        if (Table.getInstance().arr[x][y].isNull()) {
            node.put("output", "No card available at that position.");
            return node;
        }
        Minion minCard = ((Minion) Table.getInstance().arr[x][y]);
        ObjectNode auxNode = mapper.createObjectNode();
        duplicateErrorQuickFix(mapper, auxNode, minCard);
        node.set("output", auxNode);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * player mana is desired.
     * **/
    public static ObjectNode getPlayerMana(final int p,
                                           final Player playerOne, final Player playerTwo) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerMana");
        node.put("playerIdx", p);
        if (p == 1) {
            node.put("output", playerOne.mana);
        } else {
            node.put("output", playerTwo.mana);
        }
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * environment cards in Hand is desired.
     * **/
    public static ObjectNode getEnvironmentCardsInHand(final Player player, final int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getEnvironmentCardsInHand");
        node.put("playerIdx", playerIdx);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.inHand.size(); ++i) {
            if (player.inHand.get(i).isEnv()) {
                Environment card = ((Environment) player.inHand.get(i));
                ObjectNode aux = mapper.createObjectNode();
                duplicateCodeErrorQuickFix1(mapper, aux, card);
                arrayNode.add(aux);
            }
        }
        node.set("output", arrayNode);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * cards frozen on the table is desired.
     * **/
    public static ObjectNode getFrozenTable() {
        Table table = Table.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getFrozenCardsOnTable");
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < CommandActionHelper.Four; ++i) {
            for (int j = 0; j < Table.sizeCols; ++j) {
                if (!table.arr[i][j].isNull()) {
                    Minion minCard = ((Minion) table.arr[i][j]);
                    if (minCard.isFrozen()) {
                        ObjectNode nodeCard = mapper.createObjectNode();
                        nodeCard.put("mana", minCard.mana);
                        nodeCard.put("attackDamage", minCard.attackDamage);
                        nodeCard.put("health", minCard.health);
                        nodeCard.put("description", minCard.description);
                        ArrayNode arr = mapper.createArrayNode();
                        for (int k = 0; k < minCard.colors.size(); ++i) {
                            arr.add(minCard.colors.get(i));
                        }
                        nodeCard.set("colors", arr);
                        nodeCard.put("name", minCard.name);
                        arrayNode.add(nodeCard);
                    }
                }
            }
        }
        node.set("output", arrayNode);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * number of games played is desired.
     * **/
    public static ObjectNode getTotalGamesPlayed(final int gamesCounter) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getTotalGamesPlayed");
        node.put("output", gamesCounter);
        return node;
    }

    /**
     * Method to call when an add to output in case of showing
     * number of victories for a player is desired.
     * **/
    public static ObjectNode announceNoVictories(final int whichCase, final int wins) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        if (whichCase == 1) {
            node.put("command", "getPlayerOneWins");
        } else {
            node.put("command", "getPlayerTwoWins");
        }
        node.put("output", wins);
        return node;
    }
}
