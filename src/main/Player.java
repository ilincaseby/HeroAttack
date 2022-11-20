package main;

import fileio.CardInput;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public Decks deck;
    public Hero hero;
    public int mana = 0;
    public boolean turn = false;
    public int whichHero = 0;
    public List<Cards> inPlayDeck;
    public List<Cards> inHand = new ArrayList<>();
    int wins = 0;

    public void setInPlayDeck(List<Cards> playingDeck) {
        inPlayDeck = playingDeck;
    }

    public void makeThatAHero(CardInput hero) {
        if (hero.getName().equals("General Kocioraw")) {
            this.hero = new GeneralKocioraw(hero.getMana(), hero.getDescription(), hero.getColors());
            whichHero = 2;
            return;
        }
        if (hero.getName().equals("Empress Thorina")) {
            this.hero = new EmpressThorina(hero.getMana(), hero.getDescription(), hero.getColors());
            whichHero = 1;
            return;
        }
        if (hero.getName().equals("King Mudface")) {
            this.hero = new KingMudFace(hero.getMana(), hero.getDescription(), hero.getColors());
            whichHero = 3;
            return;
        }
        if (hero.getName().equals("Lord Royce")) {
            this.hero = new LordRoyce(hero.getMana(), hero.getDescription(), hero.getColors());
            whichHero = 4;
        }
    }
}
