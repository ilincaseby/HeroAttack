package main;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;
import java.util.List;

public class PlayersDecks {
    public Decks playerOneDecks = new Decks();
    public Decks playerTwoDecks = new Decks();
    public void putThemRightBaby(DecksInput one, DecksInput two) {
        playerOneDecks.nrDecks = one.getNrDecks();
        playerOneDecks.nrCardsInDeck = one.getNrCardsInDeck();
        playerTwoDecks.nrDecks = two.getNrDecks();
        playerTwoDecks.nrCardsInDeck = two.getNrCardsInDeck();
        putCardsinDecks(one.getDecks(), playerOneDecks);
        putCardsinDecks(two.getDecks(), playerTwoDecks);
    }

    public void putCardsinDecks(ArrayList<ArrayList<CardInput>> deck, Decks p) {
        for (int i = 0; i < deck.size(); ++i) {
            //System.out.println("Am bagat boss");
            List<CardInput> cardInput = deck.get(i);
            List<Cards> newCards = new ArrayList<>();
            for (int j = 0; j < cardInput.size(); ++j) {
                CardInput card = cardInput.get(j);
                if (card.getName().equals("Winterfell")) {
                    Winterfall env = new Winterfall(card.getMana(), card.getDescription(), card.getColors(), card.getName());
                    newCards.add(env);
                    continue;
                }
                if (card.getName().equals("Firestorm")) {
                    FireStorm env = new FireStorm(card.getMana(), card.getDescription(), card.getColors(), card.getName());
                    newCards.add(env);
                    continue;
                }
                if (card.getName().equals("Heart Hound")) {
                    HeartHound env = new HeartHound(card.getMana(), card.getDescription(), card.getColors(), card.getName());
                    newCards.add(env);
                    continue;
                }
                if (card.getName().equals("The Cursed One")) {
                    TheCursedOne env = new TheCursedOne(card.getMana(), card.getHealth(), card.getAttackDamage(), card.getDescription(), card.getName(), card.getColors());
                    newCards.add(env);
                    continue;
                }
                if (card.getName().equals("Disciple")) {
                    Disciple env = new Disciple(card.getMana(), card.getHealth(), card.getAttackDamage(), card.getDescription(), card.getName(), card.getColors());
                    newCards.add(env);
                    continue;
                }
                if (card.getName().equals("Miraj")) {
                    Miraj env = new Miraj(card.getMana(), card.getHealth(), card.getAttackDamage(), card.getDescription(), card.getName(), card.getColors());
                    newCards.add(env);
                    continue;
                }
                if (card.getName().equals("The Ripper")) {
                    TheRipper env = new TheRipper(card.getMana(), card.getHealth(), card.getAttackDamage(), card.getDescription(), card.getName(), card.getColors());
                    newCards.add(env);
                    continue;
                }
                Minion mini = new Minion(card.getMana(), card.getHealth(), card.getAttackDamage(), card.getDescription(), card.getName(), card.getColors());
                newCards.add(mini);
            }
            p.Decks.add(newCards);
        }
    }
}
