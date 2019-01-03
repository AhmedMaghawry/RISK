# RISK
## Overview :
- Risk is a complex board game produced by Hasbro that involves both luck and skill. The goal is simple: take over the world. Despite this simple goal, the game is very complicated and dynamic. Players attempt to take over the world by eliminating all other players. Players are eliminated when they lose all of their troops on the game board. Players must be skilled in troop deployment and must be aware of the underlying probabilities present in the game. This paper will discuss the game board, rules, probabilities, and general strategies of the game.
- In this project implement a simple environment simulator that generates instances of a search problem, runs agent programs, and evaluates their performance according to a simple performance measure. The search problem we will use is a simplified and abstract
version of the board game RISK.

## Agents Implemented :

### Completely Passive Agent :
- Attack : There is no attack for this Agent
- Place : Choose the Country with the Least armies.

### Nearly Passive Agent :
- Attack : Choose the Country with the minimum number of armies to attack and the attack happen from the adjacent with the minimum index.
- Place : Choose the Country with the Least armies.

### Aggressive Agent :
- Attack : Attacks on the most country which give him least damage or best benefit
- Place : Place the armies at the least country has armies

### Greedy Agent :
The agent which aim to get the fastest reward it can get in the soon future.

### A* Agent :
An agent that uses A* search, an inteligent agent that plays against a completely passive agent, and attempts to win in as few turns as possible.
Here the attack and the place based on the best move to achieve the goal early .
The best move choose using the heuristic function as follow :
f = h – g
h = (1 / number of enemy countries in the attacked continent) * the continent bounce + the gain of armies result from the attack
g = number of turns done till now

### RTA* Agent :
An agent that uses Real time A* search, an inteligent agent that plays against a completely passive agent, and attempts to win in as few turns as possible.
The same heuristic as the A * Agent and also algorithm except that the limit of the search space it can search in (Here is 3).

## Input read :
At the game start the user have to pick a input file that looks like the following :
``` V 4 (number of vertices)
E 4 (number of edges)
(1 2)
(2 3)
(3 4)
(1 3)
A1 1 4 (Player 1 vertices numbers)
A2 2 3 (Player 2 vertices numbers)
c 1 1 (vertex number 1 has 1 army)
c 2 1
c 3 1
c 4 2
P 2 (number of partition cells)
5 1 2 ( value of the 1st partition cell, and its members)
3 3 4 ( value of the 2nd partition cell, and its members)
```
## User Guide :
1. At the game start the game the user have to :
a. choose the type of player 1 and player2 which are Human (assumption only player 1 can be human) , Aggressive , Completely_Passive , Nearly_Pacifist  , A_Search,  A_Real , Greedy
b. pick the input file that describes the game environment
2. After the user enters a valid input and clicks play the play screen will appear.
The play screen has four buttons at the right which are
- Attack : in which the current playing agent can perform attack on an opponent’s country.
- Place : in which the current playing agent can perform placement  of bonus armies at the beginning of each turn.
- Next : in which the current playing agent end his turn and advances to the other agent.
- Quit : in which the user can quit the game at any time.
- The play screen has nodes which represents the countries in each node there are two numbers like “1/3" which means that country with number 3 has 1 army. The nodes in purple color belongs to player1 and the nodes in blue color belongs to player2.
- The player screen has six colored continents and the (nodes) countries that are in the same partitions will be in the same colored region.
- The play screen has log text which tells the user what action is performed in a text form.
- The play screen has a ( full / not full )screen button, For all non- human agents the user must click on the following the buttons in this order : Place , Attack , Next without choosing which country is attacking or which country being armies placed on.
- For the human player which will be player 1 only which will click on the following the buttons in this order :
1.	Place : then player1 will click on a country which belongs to him to place the bonus armies to the selected country.
2.	Attack :then player1 will first click on a country which he belongs to him and then click on an opponent country to be attacked.
3.	Next : to end his turn.
