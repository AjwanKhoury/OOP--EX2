# OOP--Ex2
## This project has been developed as an assignment in OOP course.

## First Part
This part is to build a directional weighted graph.
Graph: a set of vertices and edges .(for more info https://en.wikipedia.org/wiki/Graph)
This part is all about the node data , such as the id node, location of the node, edge's weight, node's info and tag alse we have a hashmap that has two elemnents <Integer, edge_data> 
We bulid 3 classses:
1. `node_data` :represents the set of operations applicable on a node (vertex) in a (directional) weighted graph.
2. `directed_weighted_graph`: represents a directional weighted graph ,has a road-system or communication network in mind ,it support a large number of nodes (over 100,000).it based on an efficient compact representation.
3. `dw_graph_algorithms`:  represents a Directed Weighted Graph Theory Algorithms contains copy, init, isConnected, shortestPathDist, List<node_data> shortestPath(int src, int dest), Save(file)-JSON file, Load(file)-JSON file. (this link explanning the algorthims that we used https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)



## Secound Part
This part is to build the game.
It has many classes that every one of them complete the other classes to run the game,\
The classes are :
1. `Ex2`: which build to start the game.
2. `Ex2_Client`: its has all the functions to build the game{ run, moveAgants, nextNode, distCal....) , and have the perpose operations to run the game, which it divide the action beetween other classes.
3. `Arena` : it's an helper class which save the the list of agent and pokemon and it locat them on the game map(in the Jframe).
4. `MyFrame`: it is the Jframe classs , its mission to Draw the game on our screen to see what going on the game.
	it also has a bar to help as play the game.
	in this bar we can choose which game scenarios we want to play with, olso we can save the graph, also it give the player option to run the game or stop it , also adding new agents.
	start Game option will be dependent on number of agent(which need to fill specefic num of agent in every game scenarios).
5. `Cl_Agent`: this class represent one agent player , it hold basic data on the agent , like its id , location , direction and speed.
6. `Cl_Pokemon`:this class represent one Pokemon player , it hold basic data on it, like its id , location and the edge that have it.


# Welcome to the Pokémon-Game!
## What is Pokémon-Game?
It is a computer game which has developed as an assignment in OOP course.
In this game you have a 24 scenarios each one has a different starting point, conditions , duration , number of egents , number of pokemons and graph representing game arena.

## What the purpose of this game?
The purpose of the game is to play a Pokémon-game to collect scores as much as we can, in that we built a game that achieve two missions to have fun and to think in cleverly way!

## How to get more Score?
We get more scores by collecting more Pokémon's which depends in our first agent locate that we choose.

## How the agents move?
The agents move by using Dijkstra-Algorithm (is an algorithm for finding the shortest paths between nodes in a graph).

## How to play the game?
1.open the game.\
2.choose the game scenarios[0-23].\
3.add the locate for every agent then the game will start.\
in the end you will get your score!.
