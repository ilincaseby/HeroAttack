package main;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class CommandActionHelperModule2 {
    public static void abilityHeroUse(int affectedRow, Player player, int fRowMe, int bRowMe, int fRowDujman, int bRowDujman, ArrayNode output) {
        if (player.hero.mana > player.mana) {
            output.add(OutputHelper.errorHero(1, affectedRow));
            return;
        }
        if (!player.hero.isValidAttack()) {
            output.add(OutputHelper.errorHero(2, affectedRow));
            return;
        }
        int heroNo = player.whichHero;
        if (heroNo == 1 || heroNo == 4) {
            if (affectedRow != fRowDujman && affectedRow != bRowDujman) {
                output.add(OutputHelper.errorHero(3, affectedRow));
                return;
            }
        }
        if (heroNo == 2 || heroNo == 3) {
            if (affectedRow != fRowMe && affectedRow != bRowMe) {
                output.add(OutputHelper.errorHero(4, affectedRow));
                return;
            }
        }
        if (heroNo == 1) {
            ((EmpressThorina) player.hero).lowBlow(Table.getInstance().arr, affectedRow);
        }
        if (heroNo == 2) {
            ((GeneralKocioraw) player.hero).bloodThrist(Table.getInstance().arr, affectedRow);
        }
        if (heroNo == 3) {
            ((KingMudFace) player.hero).earthBorn(Table.getInstance().arr, affectedRow);
        }
        if (heroNo == 4) {
            ((LordRoyce) player.hero).subZero(Table.getInstance().arr, affectedRow);
        }
        player.hero.hasAttacked();
        player.mana -= player.hero.mana;
    }

    public static void useEnvironmentCard(Player player, int handIdx, int affectedRow, int fRowEnemy, int bRowEnemy, ArrayNode output) {
        if (!player.inHand.get(handIdx).isEnv) {
            output.add(OutputHelper.errorEnvironment(1, handIdx, affectedRow));
            return;
        }
        Environment envCard = ((Environment) player.inHand.get(handIdx));
        if (player.mana < envCard.mana) {
            output.add(OutputHelper.errorEnvironment(2, handIdx, affectedRow));
            return;
        }
        if (affectedRow != fRowEnemy && affectedRow != bRowEnemy) {
            output.add(OutputHelper.errorEnvironment(3, handIdx, affectedRow));
            return;
        }
        if (envCard.isHeartHound) {
            int success = ((HeartHound) envCard).ability(Table.getInstance().arr, affectedRow);
            if (success == -1) {
                output.add(OutputHelper.errorEnvironment(4, handIdx, affectedRow));
                return;
            }
        }
        if (envCard.isWinterfell) {
            assert envCard instanceof Winterfell;
            ((Winterfell) envCard).ability(Table.getInstance().arr, affectedRow);
        }
        if (envCard.isFireStorm) {
            assert envCard instanceof FireStorm;
            ((FireStorm) envCard).ability(Table.getInstance().arr, affectedRow);
        }
        player.mana -= envCard.mana;
        player.inHand.remove(handIdx);
    }
}
