# Knapsack-Algorithm-with-Dynamic-Programming-and-Greedy-Approach

The program simulates a value game using customized chess pieces. In our case, there will be many available pieces for each type of piece, each having a historical name, along with an arbitrary attack point. Each piece type will have a level. Select your pieces depending on maximum allowed level (see Table 1). For instance, if we set the maximum allowed level to 3, you could only have one pawn, one rook and one archer. If you fall short of gold, you may also prefer not to buy a piece from the allowed level (i.e., leaving that level empty) to have a higher valued piece. For example, instead of buying one pawn, one rook and one archer, you could skip rook (in level 2) and get a better archer, if that makes you more advantageous in total attack point. In other words, you don’t need to fill the piece for each allowed level.

![image](https://github.com/kaans4nli/Knapsack-Algorithm-with-Dynamic-Programming-and-Greedy-Approach/assets/107371841/ec75e732-9779-4583-a832-d983a69ea882)

When the game starts, it must take the necessary arguments given in Code 1. from the user.

![image](https://github.com/kaans4nli/Knapsack-Algorithm-with-Dynamic-Programming-and-Greedy-Approach/assets/107371841/db76a928-02f0-4a04-8212-6278f9e89c65)

Now, let us consider individual types. For example, you may have the option of selecting following knights (or not selecting any of them, leaving level 4 blank) when number of allowed pieces per level is set to 3 and max level allowed is greater than or equal to 4. You will select at most one of them.
* Knight #1: El Cid, 120 attack points, 80 gold
* Knight #2: Richard the Lionheart, 150 attack points, 105 gold
* Knight #3: Attila, 175 attack points, 120 gold

