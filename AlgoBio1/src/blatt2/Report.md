## Praxisblatt 2
#### Thanh Truc Bui – Antoaneta Nikolova

### Exercise 1:
Parse operation:
* `f` or `file` - the user can give a file path to a TSV file to be used as input.
The option is not obligatory and if not used, we work on the default one.

<div style="text-align: justify">

For this exercise we implemented a data structure, which represents a graph with cities (Class `City`) as nodes and edges, which connect the cities (Class `Edge`). The class `Aufgabe 1` reads the given TSV file, using the default or provided file path, takes the cities and creates the edges between them.
The function <code>readTSV(String filename)</code> is called with the file pathway.
It iterates through the file row for row and each row is split by `/t` to get the information. Then using indexes we save each split string
in local variables, which at the end of the current iteration are used to define the new added element (id, city) to the HashMap `map`. This algorithm iterates once
through each row in the file and for each one is called the <code>.split()</code> function, which checks each character in the file, hence the runtime complexity
at the end is O(m) with m being the number of characters in the file.
The theoretical runtime and the practical are the same, as the overhead by initialising and updating HashMap is ignorable and each operation is in O(1). It
also terminates when the whole file is iterated through (the file cannot be endless). When the filepath is not correct - an exception is thrown.

The function <code>createEdges()</code> uses 2 for-loops to iterate through each possible combinations of cities (builds sets of 2) and
computes the distance between them according to the exercise description. The algorithm also checks if the city IDs in each pair are the same, as this means that the cities are the same, and we should not compute the distance in this case.
Because of the 2 for-loops through the HashMap, each iteration takes O(1) (even the function <code>distance()</code> takes O(1) time), this algorithm is in O(n<sup>2</sup>) in worst and best case (as there are no `return` or `break`, which could stop the iterations and lower runtime).
The practical time should also be similar.

</div>

---
### Exercise 2:

Two available parse operations:
* `f` or `file` - the user can give a filepath to a TSV file to be used as input.
* `o` or `output` - the user can give a filepath to a TSV file, on which the output will be written.  
Both are not obligatory and if not used, we work on the default ones.

<div style="text-align: justify">

In this exercise we considered only the edges with weight < 20.24 and implemented the Depth-First-Search algorithm to go through the modified graph structure.
The constructor <code>public Aufgabe2(Aufgabe1 graph, boolean filter)</code> initialises some variables and iterates through each
city in the graph. Then with <code>sortEdges(city.edges)</code> it sorts the weights of the edges of each city. The `ArrayList.sort` method uses the
TimSort algorithm (CITATIONS), which is a hybrid sorting algorithm derived from merge sort and insertion sort with worst and average runtime complexity
of O(nlogn). After that it removes all edges, smaller than the given threshold (per default 20.24).
Then we add each city id to a HashSet to keep track of all possible cities (as ids are unique) and check if the lowest ID is found, to determine the starting node.

The method `public void depthFirstSearch(int currentID)` implements the Depth-First-Search algorithm. When called with a city ID, this id is removed from the HashSet `toBeVisited`, as this node has been visited. Then it increments the number of steps and checks if there are any more cities to visit.
After that it iterates through each edge of the current city (starting with the one with the smallest weight => the smallest index, since the edges have been sorted in the constructor `public Aufgabe2(Aufgabe1 graph, boolean filter)`).
Firstly, we check if there are any more cities to visit, which offers early termination. If the HashSet is empty, there is no need to iterate through the other edges of the current node.
Otherwise, it gets the ID of the next end-city (of the edge) and checks if it is contained in the HashSet, which stores all not yet visited cities. If yes, then it adds it to the StringBuilder and recursively calls the same method with the new city. This process is repeated until all cities in the HashSet have been visited (removed from the HashMap => it becomes empty) or when all edges are iterated - of the current node and all parent nodes before it.
Let n be the number of cities and m all edges. In best case, each city with the smallest edge will be in the HashSet `toBeVisited`, hence for each recursive call we take the first edge until we go through the n cities and after that the HashSet becomes empty, which means all recursive calls terminate one after the other until the whole algorithm stops - O(n).
In worst case, there is a city (or multiple), which cannot be reached, but still are in `toBeVisited`. This means the algorithm will go through each edge of each reachable node and at the end it will terminate again, but this time in O(n*m) or O(n<sup>3</sup>).

</div>

---

### Exercise 3:
Two available parse operations:
* `f` or `file` - the user can give a filepath to a TSV file to be used as input.
* `o` or `output` - the user can give a filepath to a TSV file, on which the output will be written.  
  Both options are not obligatory and if not given, we use the default provided ones.

<div style="text-align: justify">

In the last exercise we implemented the Kruskal algorithm.  
The constructor `public Aufgabe3(Aufgabe1 graph)` iterates through the nodes in the graph, saves them in a HashMap and initialises the data structures for the Union-Find algorithm used later.  
We implemented 2 methods to check for cycles. 
The first method `public boolean isCycle(Edge e)` (written in a comment) takes edge `e` and jumps into the method `resetToBeVisited();` to clear the HashSet (this method takes O(nmlogm) n for the for-loop through the nodes and at each iteration the edges are being sorted (worst case for a complete graph: (n-1) edges must be sorted)).
The next method `depthFirstSearch(e.getStartID());` uses the algorithm from Exercise 2 to list all connected nodes. Lastly, the `isCycle(Edge e)` method iterates through each city in the result to compare all the result-IDs with the current end-ID and check for circles in the graph structure.
The bad runtime complexity motivated us to search for other algorithms. We found Union-Find algorithm. We found Union-Find algorithm. When deciding whether to
add an edge, we need to check if the start- and end-cities are connected. If they are, then they have the same root and adding the edge between them would create a cycle. Otherwise, we allow th edge and combine them.
This algorithm uses 2 arrays `parent` and `rank`. In the first one each index is associated with a city (determined by the Maps `cityIDToIndex` and `indexToCityID`) and keeps track of the parent of each node. Initially, each node is its own parent. By allowing edges between them, depending on the rang, one of the 2 cities at each edge becomes the parent (new root) and the other one connect to them. We also increment the rang of the current node, as it becomes a part of a bigger set or structure.
The algorithm Union-Find is implemented in the method `public boolean isCycle(Edge e)` and uses the private methods:  `int find(int x)` to search recursively for the root of the start- and edge cities using ` int[] parent`; `void union(int x, int y)` connects the 2 disjoint structures, if they do not have the same root. The order is decided using `int[] rank`, which stores the depth of each tree (subset) and connects the smaller to the bigger. Its runtime is theoretically O(nm), but in practice it achieves much better performance due to path compression and union by rank, resulting in amortized time complexity closer to O(mα(n)), where α(n) is the inverse Ackermann function, which grows very slowly. [1]

The algorithm `public void kruskal()` firstly iterates through the nodes and collects all edges and sort them. After that it iterates through each edge and at each step the function `public boolean isCycle(Edge e)` is called. When there are no cycles, then we add the cities at the two ends of the current edge, add them to the StringBuilder `backtracking` and also update the sum.
The algorithm iterates through all edges or until the number of edges in the MST is equal to the number of cities - 1. 
</div>