# Catan from the ADP group

## Starting the game

To run the program, open the MainGame.java file with the IDE of your choice. Then execute the main () method. To start the program from the command line, first compile with "javac MainGame.java" and then enter "java MainGame"

First you have to choose the number of players from 2-4

## Founding phase

1. Each player, in turn, can place one of their settlements and put a street
to the created settlement

2. Now all players again, one after the other, place a settlement and street, but this time in reverse order. So the player who last has placed the settlement and road may start first.

3. Each player receives their first resource after setting their second settlement. For each land space adjacent to this second settlement, he receives one resource from the bank's stock.

##  Game phase
The starting player (who last, as described above, set his second settlement) comes first to turn. Then the others follow in a fixed order.

If it's your turn the terminal ask you what you want to do. The selection you have:

1. `SHOW:` Displays the board with all the built buildings and roads

2. `TRADE:` trade your resources with the bank with a ratio 4:1

3. `BUILD:` you can build `1: SETTLEMENT`, `2: CITY`. `3: ROAD` 

4. `STRUCTURE_COST:` Displays the cost from all the structure.
 
5. `BANK_STOCK:` Displays the remaining stock of the bank.

6. `MY_STOCK:` Displays your remaining stock.

7. `END:` Ends your turn.
