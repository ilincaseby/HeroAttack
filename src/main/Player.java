package main;

import fileio.CardInput;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public Hero hero;
    public int mana = 0;
    public boolean turn = false;
    public int whichHero = 0;
    public List<Cards> inPlayDeck;
    public List<Cards> inHand = new ArrayList<>();

    /**
     * A custom constructor which translates the default
     * input for hero into this implementation
     * **/
    public void makeThatAHero(final CardInput hero) {
        if (hero.getName().equals("General Kocioraw")) {
            this.hero = new GeneralKocioraw(hero.getMana(),
                    hero.getDescription(), hero.getColors());
            whichHero = CommandActionHelper.TWO;
            return;
        }
        if (hero.getName().equals("Empress Thorina")) {
            this.hero = new EmpressThorina(hero.getMana(),
                    hero.getDescription(), hero.getColors());
            whichHero = CommandActionHelper.ONE;
            return;
        }
        if (hero.getName().equals("King Mudface")) {
            this.hero = new KingMudFace(hero.getMana(),
                    hero.getDescription(), hero.getColors());
            whichHero = CommandActionHelper.THREE;
            return;
        }
        if (hero.getName().equals("Lord Royce")) {
            this.hero = new LordRoyce(hero.getMana(),
                    hero.getDescription(), hero.getColors());
            whichHero = CommandActionHelper.FOUR;
        }
    }
}
