package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;

public class CommandActionHelperModule1 extends CommandActionHelper{
    public static void cardUsesAbilityHelper(Coordinates attacker, Coordinates attacked, ArrayNode output, int fRowMe, int bRowMe, int fRowDujman, int bRowDujman) {
        Table table = Table.getInstance();
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
            output.add(OutputHelper.usesAbility(1, attacker, attacked));
            return;
        }
        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn) {
            output.add(OutputHelper.usesAbility(2, attacker, attacked));
            return;
        }
        String nameOfCard = ((Minion) table.arr[attacker.getX()][attacker.getY()]).name;
        if (nameOfCard.equals("Disciple")) {
            if (fRowMe != attacked.getX() && bRowMe != attacked.getX()) {
                output.add(OutputHelper.usesAbility(3, attacker, attacked));
                return;
            }
        }
        if (nameOfCard.equals("The Cursed One") || nameOfCard.equals("Miraj") || nameOfCard.equals("The Ripper")) {
            if (fRowDujman != attacked.getX() && bRowDujman != attacked.getX()) {
                output.add(OutputHelper.usesAbility(4, attacker, attacked));
                return;
            }
            if (noTanksAttackedTanksExist(fRowDujman, attacked.getX(), attacked.getY())) {
                output.add(OutputHelper.usesAbility(5, attacker, attacked));
                return;
            }
        }

        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).name.equals("Disciple")) {
            ((Disciple) table.arr[attacker.getX()][attacker.getY()]).action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).name.equals("Miraj")) {
            ((Miraj) table.arr[attacker.getX()][attacker.getY()]).action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).name.equals("The Cursed One")) {
            ((TheCursedOne) table.arr[attacker.getX()][attacker.getY()]).action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).name.equals("The Ripper")) {
            ((TheRipper) table.arr[attacker.getX()][attacker.getY()]).action(((Minion) table.arr[attacked.getX()][attacked.getY()]));
        }
        if (((Minion) table.arr[attacked.getX()][attacked.getY()]).health <= 0) {
            table.arr[attacked.getX()][attacked.getY()] = new NullCard();
            table.shiftCards(attacked.getX(), attacked.getY());
        }
    }

    public static void useAttackHero(Player enemy, int fRowDujman, int whichPlayer, MyInteger winsToInc, Coordinates attacker, ArrayNode output) {
        Table table = Table.getInstance();
        if (((Minion) table.arr[attacker.getX()][attacker.getY()]).isFrozen()) {
            output.add(OutputHelper.useAttackHero(1, attacker));
            return;
        }
        if (!((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn) {
            output.add((OutputHelper.useAttackHero(2, attacker)));
            return;
        }
        for (int k = 0; k < 5; ++k) {
            if (!table.arr[fRowDujman][k].isNull) {
                if (((Minion) table.arr[fRowDujman][k]).isTank) {
                    output.add(OutputHelper.useAttackHero(3, attacker));
                    return;
                }
            }
        }
        enemy.hero.health -= ((Minion) table.arr[attacker.getX()][attacker.getY()]).attackDamage;
        ((Minion) table.arr[attacker.getX()][attacker.getY()]).isValidTurn = false;
        if (enemy.hero.health <= 0) {
            output.add(OutputHelper.announceVictory(whichPlayer));
            winsToInc.setX(winsToInc.getX() + 1);
        }
    }
}
