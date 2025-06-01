# My Java Mini Monopoly Game

This game is run from the command line.

In this version of the game, a number of rules are removed. There are no houses or hotels. They are no groups of properties. There are no mortgages. There are no Chance or Community Chest cards. There is no jail. Purchase and rent are simplified. The notion of a Court is introduced where players have to do Jury Service. The notion of a Congestion Zone is introduced.

Players and the Board

The game is played by 2 to 10 players. The game is played on a board of between 10 and 50 spaces. The board is a loop – that is, when the player reaches the last space, they then restart from the first space. 
All players start on the first square known as the “Home” space. All players start with a balance of £1000.
The other spaces are a mix of roads, properties and courts. Here is the same 16-space board containing roads (grey - 2,3,6,8,10,14,16), properties (orange - 4,5,9,13,15) and a court (blue - 7). A congestion zone (yellow - 10-12) is shown. Congestion zones can only be a set of road spaces. The Home space is shown in green and should be considered to be a “Road” space.

Moving

The players take it in turns to roll a pair of 6-sided dice. After rolling, they move forward by the sum of the faces of the dice. For example, a player on space 1 that rolls 4 & 4 will move to space 9. A player on space 15 of a 16-space board that rolls 4 & 5 will move to space 8.

Type of space landed on	Action

Road	Take no action
Property (not owned)	The player may choose to purchase the property. They may choose not to purchase the property and play passes to the next player.
Property (owned)	The player must pay rent to the owner of the property. If the property is owned by the current player, no action is taken.
Congestion Zone	If a player lands on a space in the congestion zone, then the player must pay a fine price based on the journey undertaken.  The fine is equal to £10 * the value of the roll. If the player crosses a congestion zone without landing in it, they are considered to have gone around the zone and they don’t have to pay.
Court	If the player lands on a court space, they are considered to be attending for Jury Service. They will be paid £100 for their time, but they will miss two turns.

Transactions

All properties have a purchase price and a rental value.  These are both fixed at the start of the game.  For example, in traditional London Monopoly, Mayfair has a purchase price of £400 and a basic rental value of £50. In our version of the game, the rental value is never more than 10% of the purchase price. No property may be priced over £500.

If a player wishes to purchase a property, then they must pay the purchase price. This debits their balance. A player can’t purchase a property if they don’t have the funds for it.

If a player is obliged to pay rent, then they must give the rental value to the owner of the property. If a player is obliged to pay rent and the rent is more than their balance, then they must sell properties in order to service the debt (see Bankruptcy).

If a player is still unable to pay rent, then they must pay all of their balance to the property owner (see Bankruptcy).

When a player lands on or passes the Home space, they receive their wages of £200 plus 1% of the rental price of all of their owned properties.


Bankruptcy

If a player’s cash balance falls below zero and they own no properties, then they are declared bankrupt and are out of the game. 

If a player’s cash balance falls below zero and they own properties, then the property (or properties) with the lowest purchase price is sold for 50% of the stated purchase price. This process continues until the player is back in credit or all properties are sold. If, after all properties are sold, the player’s cash balance is still less than zero, the player is declared bankrupt and is out of the game.


End of the game

A round is considered complete when all players have rolled the dice and completed the actions. The game is played over a number of rounds or until there is only one player left in credit. The winner is the player with the highest assets. The assets are calculated as the sum of the player’s cash balance and the sum of their owned property purchase values.

