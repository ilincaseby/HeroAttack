package main;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class CommandActionHelperModule2 {

    /**
     * Method to use the ability of the hero
     * **/
    public static void abilityHeroUse(final int affectedRow, final Player player,
                                      final int fRowMe, final int bRowMe, final int fRowDujman,
                                      final int bRowDujman, final ArrayNode output) {
        if (player.hero.mana > player.mana) {
            output.add(OutputHelper.errorHero(CommandActionHelper.One, affectedRow));
            return;
        }
        if (!player.hero.isValidAttack()) {
            output.add(OutputHelper.errorHero(CommandActionHelper.Two, affectedRow));
            return;
        }
        int heroNo = player.whichHero;
        if (heroNo == CommandActionHelper.One || heroNo == CommandActionHelper.Four) {
            if (affectedRow != fRowDujman && affectedRow != bRowDujman) {
                output.add(OutputHelper.errorHero(CommandActionHelper.Three, affectedRow));
                return;
            }
        }
        if (heroNo == CommandActionHelper.Two || heroNo == CommandActionHelper.Three) {
            if (affectedRow != fRowMe && affectedRow != bRowMe) {
                output.add(OutputHelper.errorHero(CommandActionHelper.Four, affectedRow));
                return;
            }
        }
        if (heroNo == CommandActionHelper.One) {
            ((EmpressThorina) player.hero).lowBlow(Table.getInstance().arr, affectedRow);
        }
        if (heroNo == CommandActionHelper.Two) {
            ((GeneralKocioraw) player.hero).bloodThrist(Table.getInstance().arr, affectedRow);
        }
        if (heroNo == CommandActionHelper.Three) {
            ((KingMudFace) player.hero).earthBorn(Table.getInstance().arr, affectedRow);
        }
        if (heroNo == CommandActionHelper.Four) {
            ((LordRoyce) player.hero).subZero(Table.getInstance().arr, affectedRow);
        }
        player.hero.hasAttacked();
        player.mana -= player.hero.mana;
    }

    /**
     * Method to use an environment card
     * **/
    public static void useEnvironmentCard(final Player player, final int handIdx,
                                          final int affectedRow, final int fRowEnemy,
                                          final int bRowEnemy, final ArrayNode output) {
        if (!player.inHand.get(handIdx).isEnv()) {
            output.add(OutputHelper.errorEnvironment(CommandActionHelper.One,
                    handIdx, affectedRow));
            return;
        }
        Environment envCard = ((Environment) player.inHand.get(handIdx));
        if (player.mana < envCard.getMana()) {
            output.add(OutputHelper.errorEnvironment(CommandActionHelper.Two,
                    handIdx, affectedRow));
            return;
        }
        if (affectedRow != fRowEnemy && affectedRow != bRowEnemy) {
            output.add(OutputHelper.
                    errorEnvironment(CommandActionHelper.Three, handIdx, affectedRow));
            return;
        }
        if (envCard.isHeartHound()) {
            int success = ((HeartHound) envCard).ability(Table.getInstance().arr, affectedRow);
            if (success == -1) {
                output.add(OutputHelper.errorEnvironment(CommandActionHelper.Four,
                        handIdx, affectedRow));
                return;
            }
        }
        if (envCard.isWinterfell()) {
            assert envCard instanceof Winterfell;
            ((Winterfell) envCard).ability(Table.getInstance().arr, affectedRow);
        }
        if (envCard.isFireStorm()) {
            assert envCard instanceof FireStorm;
            ((FireStorm) envCard).ability(Table.getInstance().arr, affectedRow);
        }
        player.mana -= envCard.getMana();
        player.inHand.remove(handIdx);
    }
}
