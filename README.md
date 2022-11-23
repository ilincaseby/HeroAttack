# GwentStone Gameplay README
Ilinca Sebastian-Ionut 321CA 2022

## Short Description
GwentStone is a game based on magic players that appears on cards.
It is very similar to DuelMaster. Both players has a hero from the beginning. The purpose is to kill enemy's hero. That's the point where the cards have a meaning.
A table is available where players can place the magic cards on their rows when is their turn to action. Hero can be attacked by just cards on the table. To protect the king, players can place Tank cards so the enemy must take down Tanks first. Also, for a more entertaining gamification, in the hand of the player there will be environment cards which have some impact on a specific row.

## Tank Cards
* Goliath
* Warden

## Simple Minion Cards
_**These Cards do not have a special ability or tank attribute, they simply attack**_
* Sentinel
* Berserker

## Minion Card with Special Abilities
* Miraj
* The Ripper
* The Cursed One
* Disciple

## Environment Cards
* Firestorm
* Winterfell
* Heart Hound

## Heroes
* General Kocioraw
* King Mudface
* Empress Thorina
* Lord Royce

## Implementations and Used Design Patterns Explained
  1. Used SingleTon Design Pattern for simplicity of parsing Table argument for helper functions. Also I find it a good choice in the combination with the clearTable method which has the purpose to fill the entire table with NullCard Cards at the beginning of a new game. The allocation is the Lazy one because I find it more useful than Eager.
  1. Has static final components for the use of magic numbers(e.g. Table.FIRSTPLAYERFRONTROW).
  1. Minion Cards Class has some features to detect if a card is tank or not.
  1. Minion class is used as a class to instantiate normal Minion cards but also to use extensions on that for special Minions which have abilities. These Classes from Minion main class also implements an interface.
  1. Hero class is used _**ONLY**_ for extension. Every class extended from Hero class has his own action.
  1.  Player Class is used to store data about a player which has impact in the game(e.g. mana, hero, inHand, inPlay).
  1. inPlay and inHand are lists which have the following meanings: inPlay is reffered to a  deck that has been taken out from the list of decks and inHand represents the cards available for place/use.
  1. With every round player gets a card from the inPLay deck and put it in his own inHand.
  1. Environment class is used _**ONLY**_ for extension. Each of the child has a special ability but all three cards have attributes in common.
  1. **CommandActionHelper Modules**: Used three classes for different functionalities because of the lizibility of the code.
  1. These classes implements actions like 'placeCard', 'useAttackHero'. Also in the functionalities are treated edge cases and redirect them to output.
  1. _**StatisticInfoHelper**_: Found it more intuitive to treat statistic command in a separate module(again for the lizibility).
  1. Make use of the OutputHelper to redirect the output in a specific ArrayNode.
  1. _**OutputHelper**_:Last very important class(the rest are not as important as these 8).
  1. _**OutputHelper**_: Except of adding the ObjectNode object in the ArrayNode object, everything for output happens there. Most of the methods make use of the error cases to determine the string returned for error section, because most command error outputs share the same message, but not error message.
  1. A **VERY** challenging thing was to show the cards on the table or anything that needed an ArrayNode to be put inside an ObjectNode. Said very challenging because it was difficult to make the right researches to find out how it's done(e.g. objectNode.putArray vs objectNode.set).

## Difficulties
1. For CheckStyle, needed to reset all the parameters to private and have public modifiers(getter and setter).
1. Because of the little changes scenario, used copy paste then modify, but not everything worked at a glance from first try.
1. First time using ObjectMapper, so it took some hours spent on research with the purpose of understanding how it works.
1. A difficulty that could be avoided was to not intercalate in code parameter 'inPlayDeck' with 'inHand'.

## Things learned with this project
1. Most of the time, you need to look at other peoples' code and understand how does that work.(e.g. ObjectMapper).
1. A good program takes around 20% implementation, 30% coding style and making comments so that other people can look over and understand what has been done there, 20% research and 30% debugging.


## Block of code as final
```
System.out.println("Hope this README makes everything clear about the project and if there is something unclear contact me at the following gmail address:");
```
<ilincasebastian1406@gmail.com>








