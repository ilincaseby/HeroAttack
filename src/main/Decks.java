package main;

import java.util.ArrayList;
import java.util.List;

public class Decks {
    private int nrCardsInDeck;
    private int nrDecks;
    private List<List<Cards>> decks = new ArrayList<>();

    /**
     * Getter
     * **/
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     * Setter
     * **/
    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    /**
     * Getter
     **/
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     * Setter
     * **/
    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    /**
     * Getter
     * **/
    public List<List<Cards>> getDecks() {
        return decks;
    }

    /**
     * Setter
     * **/
    public void setDecks(final List<List<Cards>> decks) {
        this.decks = decks;
    }
}
