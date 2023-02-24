# SCC-Projects
PIG Game Logic using 2 dice:

A player throws 2 six-sided dice and scores as many points as the total shown on the dice providing a die doesn’t roll a 1.
The player may continue rolling and accumulating points (but risk rolling a 1) or end his turn.

if a 1 is rolled on one dice, the turn score goes to 0 and the turn is given to the other player
if a 1 is rolled on both dice, the total score goes to 0 and the turn is given to the other player
All other rolls are added together and added to the turn score
Once the "Hold" button is pressed, the turn score is added to the total score and the turn goes to the other player.
Play passes from player to player until a winner is determined.  A winner is the first one to reach 100 points.

We are playing against the computer (Android Device) that will roll up to 3 times during the computer's turn. The same rules about throwing a 1 apply to the computer.
If all 3 rolls are thrown without a 1, then the 3 turns totals are added to computer's grand total.
