package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;

public class CommandActionHelperModule1 extends CommandActionHelper {

    //private CommandActionHelperModule1() { }

    /**
     * Method to use an ability of a card
     * **/
    public static void cardUsesAbilityHelper(final Coordinates attacker,
                                             final Coordinates attacked, final ArrayNode output,
                                             final int fRowMe, final int bRowMe,
                                             final int fRowDujman, final int bRowDujman) {
        Table table = Table.getInstance();
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
            output.add(OutputHelper.usesAbility(1, attacker, attacked));
            return;
        }
        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn()) {
            output.add(OutputHelper.usesAbility(2, attacker, attacked));
            return;
        }
        String nameOfCard = ((Minion) table.arr[attacker.getX()][attacker.getY()]).getName();
        if (nameOfCard.equals("Disciple")) {
            if (fRowMe != attacked.getX() && bRowMe != attacked.getX()) {
                output.add(OutputHelper.usesAbility(THREE, attacker, attacked));
                return;
            }
        }
        if (nameOfCard.equals("The Cursed One") || nameOfCard.equals("Miraj")
                                        || nameOfCard.equals("The Ripper")) {
            if (fRowDujman != attacked.getX() && bRowDujman != attacked.getX()) {
                output.add(OutputHelper.usesAbility(FOUR, attacker, attacked));
                return;
            }
            if (noTanksAttackedTanksExist(fRowDujman, attacked.getX(), attacked.getY())) {
                output.add(OutputHelper.usesAbility(FIVE, attacker, attacked));
                return;
            }
        }

        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).getName().equals("Disciple")) {
            ((Disciple) table.arr[attacker.getX()][attacker.getY()]).
                    action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).getName().equals("Miraj")) {
            ((Miraj) table.arr[attacker.getX()][attacker.getY()])
                    .action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).getName().
                equals("The Cursed One")) {
            ((TheCursedOne) table.arr[attacker.getX()][attacker.getY()])
                    .action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).getName().equals("The Ripper")) {
            ((TheRipper) table.arr[attacker.getX()][attacker.getY()])
                    .action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacked.getX()][attacked.getY()]).getHealth() <= 0) {
            table.arr[attacked.getX()][attacked.getY()] = new NullCard();
            table.shiftCards(attacked.getX(), attacked.getY());
        }
    }

    /**
     * Method to call to attack a hero
     * **/
    public static void useAttackHero(final Player enemy, final int fRowDujman,
                                     final int whichPlayer, final MyInteger winsToInc,
                                     final Coordinates attacker, final ArrayNode output) {
        Table table = Table.getInstance();
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
            output.add(OutputHelper.useAttackHero(1, attacker));
            return;
        }
        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn()) {
            output.add((OutputHelper.useAttackHero(2, attacker)));
            return;
        }
        for (int k = 0; k < Table.SIZECOLS; ++k) {
            if (!table.arr[fRowDujman][k].isNull()) {
                if (((Minion) table.arr[fRowDujman][k]).isTank()) {
                    output.add(OutputHelper.useAttackHero(THREE, attacker));
                    return;
                }
            }
        }
        enemy.hero.setHealth(enemy.hero.getHealth()
                - ((Minion) table.arr[attacker.getX()][attacker.getY()]).getAttackDamage());
        ((Minion) table.arr[attacker.getX()][attacker.getY()]).setValidTurn(false);
        if (enemy.hero.getHealth() <= 0) {
            output.add(OutputHelper.announceVictory(whichPlayer));
            winsToInc.setX(winsToInc.getX() + 1);
        }
    }
}
