package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;

public class OutputHelper {
    public static ObjectNode toStringPlaceCard(int handIdx, int whichError) {
        if (whichError == 0)
            return null;
        String err = "";
        if (whichError == 1)
            err = "Cannot place environment card on table.";
        if (whichError == 2)
            err = "Not enough mana to place card on table.";
        if (whichError == 3)
            err = "Cannot place card on table since row is full.";

        ObjectNode newNode = (new ObjectMapper()).createObjectNode();
        newNode.put("command", "placeCard");
        newNode.put("handIdx", handIdx);
        newNode.put("error", err);
        return newNode;
    }

    public static ObjectNode usesAttack(int whichError, Coordinates attacker, Coordinates attacked) {
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
        if (whichError == 1)
            error = "Attacked card does not belong to the enemy.";
        if (whichError == 2)
            error = "Attacker card has already attacked this turn.";
        if (whichError == 3)
            error = "Attacker card is frozen.";
        if (whichError == 4)
            error = "Attacked card is not of type 'Tank'.";
        node.put("error", error);
        return node;
    }

    public static ObjectNode usesAbility(int whichError, Coordinates attacker, Coordinates attacked) {
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
        if (whichError == 1)
            err = "Attacker card is frozen.";
        if (whichError == 2)
            err = "Attacker card has already attacked this turn.";
        if (whichError == 3)
            err = "Attacked card does not belong to the current player.";
        if (whichError == 4)
            err = "Attacked card does not belong to the enemy.";
        if (whichError == 5)
            err = "Attacked card is not of type 'Tank'.";
        toAdd.put("error", err);
        return toAdd;
    }

    public static ObjectNode useAttackHero(int whichError, Coordinates attacker) {
        String err = "";
        if (whichError == 1)
            err = "Attacker card is frozen.";
        if (whichError == 2)
            err = "Attacker card has already attacked this turn.";
        if (whichError == 3)
            err = "Attacked card is not of type 'Tank'.";
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

    public static ObjectNode announceVictory(int whichPlayer) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        if (whichPlayer == 1) {
            node.put("gameEnded", "Player one killed the enemy hero.");
            return node;
        }
        node.put("gameEnded", "Player two killed the enemy hero.");
        return node;
    }

    public static ObjectNode errorHero(int whichCase, int affectedRow) {
        String err = "";
        if (whichCase == 1)
            err = "Not enough mana to use hero's ability.";
        if (whichCase == 2)
            err = "Hero has already attacked this turn.";
        if (whichCase == 3)
            err = "Selected row does not belong to the enemy.";
        if (whichCase == 4)
            err = "Selected row does not belong to the current player.";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "useHeroAbility");
        node.put("affectedRow", affectedRow);
        node.put("error", err);
        return node;
    }

    public static ObjectNode errorEnvironment(int whichCase, int handIdx, int affectedRow) {
        String err = "";
        if (whichCase == 1)
            err = "Chosen card is not of type environment.";
        if (whichCase == 2)
            err = "Not enough mana to use environment card.";
        if (whichCase == 3)
            err = "Chosen row does not belong to the enemy.";
        if (whichCase == 4)
            err = "Cannot steal enemy card since the player's row is full.";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "useEnvironmentCard");
        node.put("handIdx", handIdx);
        node.put("affectedRow", affectedRow);
        node.put("error", err);
        return node;
    }

    public static ObjectNode cardsInHand(int playerIndex, Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getCardsInHand");
        node.put("playerIdx", playerIndex);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.inHand.size(); ++i) {
            ObjectNode nodeCards = mapper.createObjectNode();
            if (player.inHand.get(i).isMinion) {
                Minion minCard = ((Minion) player.inHand.get(i));
                duplicateErrorQuickFix(mapper, nodeCards, minCard);
            }
            if (player.inHand.get(i).isEnv) {
                Environment envCard = ((Environment) player.inHand.get(i));
                duplicateCodeErrorQuickFix1(mapper, nodeCards, envCard);
            }
            arrayNode.add(nodeCards);
        }
        node.set("output", arrayNode);
        return node;
    }

    private static void duplicateCodeErrorQuickFix1(ObjectMapper mapper, ObjectNode nodeCards, Environment envCard) {
        nodeCards.put("mana", envCard.mana);
        nodeCards.put("description", envCard.description);
        ArrayNode arr = mapper.createArrayNode();
        for (int k = 0; k < envCard.colors.size(); ++k) {
            arr.add(envCard.colors.get(k));
        }
        nodeCards.set("colors", arr);
        nodeCards.put("name", envCard.name);
    }

    private static void duplicateErrorQuickFix(ObjectMapper mapper, ObjectNode nodeCards, Minion minCard) {
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

    public static ObjectNode getPlayerDeck(int index, Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerDeck");
        node.put("playerIdx", index);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.inPlayDeck.size(); ++i) {
            ObjectNode nodeCards = mapper.createObjectNode();
            if (player.inPlayDeck.get(i).isMinion) {
                Minion minCard = ((Minion) player.inPlayDeck.get(i));
                duplicateErrorQuickFix(mapper, nodeCards, minCard);
            }
            if (player.inPlayDeck.get(i).isEnv) {
                Environment envCard = ((Environment) player.inPlayDeck.get(i));
                duplicateCodeErrorQuickFix1(mapper, nodeCards, envCard);
            }
            arrayNode.add(nodeCards);
        }
        node.set("output", arrayNode);
        return node;
    }

    public static ObjectNode getCardsOnTable() {
        Table table = Table.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getCardsOnTable");
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < 4; ++i) {
            ArrayNode arrayNode1 = mapper.createArrayNode();
            for (int j = 0; j < 5; ++j) {
//                if (table.arr[i][j].isEnv) {
//                    System.out.println(i + " " + j + " " + ((Environment) table.arr[i][j]).isEnv);
//                }
                if (!table.arr[i][j].isNull) {
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

    public static ObjectNode getPlayerTurn(int whichOne) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerTurn");
        node.put("output", whichOne);
        return node;
    }

    public static ObjectNode getPlayerHero(int which, Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getPlayerHero");
        node.put("playerIdx", which);
        ObjectNode auxNode = mapper.createObjectNode();
        auxNode.put("mana", player.hero.mana);
        auxNode.put("description", player.hero.description);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.hero.colors.size(); ++i) {
            arrayNode.add(player.hero.colors.get(i));
        }
        auxNode.set("colors", arrayNode);
        auxNode.put("name", player.hero.name);
        auxNode.put("health", player.hero.health);
        node.set("output", auxNode);
        return node;
    }

    public static ObjectNode getCardAtPosition(int x, int y) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getCardAtPosition");
        node.put("x", x);
        node.put("y", y);
        if (Table.getInstance().arr[x][y].isNull) {
            node.put("output", "No card available at that position.");
            return node;
        }
        Minion minCard = ((Minion) Table.getInstance().arr[x][y]);
        ObjectNode auxNode = mapper.createObjectNode();
        duplicateErrorQuickFix(mapper, auxNode, minCard);
        node.set("output", auxNode);
        return node;
    }
    public static ObjectNode getPlayerMana(int p, Player playerOne, Player playerTwo) {
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

    public static ObjectNode getEnvironmentCardsInHand(Player player, int playerIdx) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getEnvironmentCardsInHand");
        node.put("playerIdx", playerIdx);
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < player.inHand.size(); ++i) {
            if (player.inHand.get(i).isEnv) {
                Environment card = ((Environment) player.inHand.get(i));
                ObjectNode aux = mapper.createObjectNode();
                duplicateCodeErrorQuickFix1(mapper, aux, card);
                arrayNode.add(aux);
            }
        }
        node.set("output", arrayNode);
        return node;
    }
    public static ObjectNode getFrozenTable() {
        Table table = Table.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getFrozenCardsOnTable");
        ArrayNode arrayNode = mapper.createArrayNode();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (!table.arr[i][j].isNull) {
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

    public static ObjectNode getTotalGamesPlayed(int gamesCounter) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "getTotalGamesPlayed");
        node.put("output", gamesCounter);
        return node;
    }

    public static ObjectNode AnnounceNoVictories(int whichCase, int wins) {
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
