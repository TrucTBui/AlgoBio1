## Theoretische vs praktische Laufzeit
#### Thanh Truc Bui – Antoaneta Nikolova
### Exercise 1:

<div style="text-align: justify">

The function <code>readTSV(String filename)</code> is called with the file pathway.
This algorithm iterates once
through each row in the file and for each one is called the <code>.split()</code> function, which checks each character in the file, hence the runtime complexity
at the end is O(m) with m being the number of characters in the file.
The theoretical runtime and the practical are the same, as the overhead by initialising and updating HashMap is ignorable and each operation is in O(1). It
also terminates when the whole file is iterated through (the file cannot be endless). When the filepath is not correct - an exception is thrown.

The function <code>createEdges()</code> uses 2 for-loops to iterate through each possible combinations of cities and each iteration takes O(1) (even the function <code>distance()</code> takes O(1) time), this algorithm is in O(n<sup>2</sup>) in worst and best case (as there are no `return` or `break`, which could stop the iterations and lower runtime).
The practical time should also be similar.

</div>

---
### Exercise 2:

<div style="text-align: justify">

In this exercise we considered only the edges with weight < 20.24 and implemented the Depth-First-Search algorithm to go through the modified graph structure.
The constructor <code>public Aufgabe2(Aufgabe1 graph, boolean filter)</code> initialises some variables (O(1)) and iterates through each
city in the graph (O(n)). Then with <code>sortEdges(city.edges)</code> it sorts the weights of the edges of each city using the
TimSort algorithm, which is in O(nlogn). After that it removes all edges, smaller than the given threshold (per default 20.24), which takes O(n-1) or O(n).
So at the end, the constructor iterates n times and each iteration takes O(1 + logn + n + 1) has a theoretical worst time complexity of O(n<sup>2</sup>logn). And the practical runtime should also be the same, since there are no early `return` or `break` statements.
The constructor terminates and gives correct results for correct inputs, since it iterates through a number of nodes (not endless) and they cannot have endless edges.

The method `public void depthFirstSearch(int currentID)` implements the Depth-First-Search algorithm. It removes the current visited city from the HashSet, increments `steps` and iterates through each edge of the current city and if the ID of the end-city is contained in the HashSet `toBeVisited`, the same algorithm is recursively called with this city and does the same until all cities are visited and the HashSet is empty, or all edges are considered. 
Let n be the number of cities and m all edges. In best case the algorithm is in O(n).
In worst case - O(n*m). m is in O(n<sup>2</sup>), when the graph is complete (n-1 (1 city is not reachable => no edges from and to it) multiplied by n-2 (edges to all other nodes, except itself and the unreachable one)). So the worst complexity time is in O(n<sup>3</sup>).
The practical runtime depends heavily on the structure of the graph.

</div>

---

### Exercise 3:

<div style="text-align: justify">

In the last exercise we implemented the Kruskal algorithm.  
The constructor `public Aufgabe3(Aufgabe1 graph)` iterates through the nodes in the graph, saves them in a HashMap and initialises the data structures for the Union-Find algorithm used later (O(1)) which takes total time of O(n) - practical and theoretical, with n being the number of nodes. 
The method `public boolean isCycle(Edge e)` takes edge `e` and jumps into the method `resetToBeVisited();` to clear the HashSet (this method takes O(nmlogm) n for the for-loop through the nodes and at each iteration the edges are being sorted (worst case for a complete graph: (n-1) edges must be sorted)). 
The next method `depthFirstSearch(e.getStartID());` uses the algorithm from Exercise 2 with runtime of O(n<sup>3</sup>).
The result string from it is split by ` `, which takes linear time, but depends on the number of characters in the result. Lastly, the `isCycle(Edge e)` method iterates through each city in the result and each iteration takes O(1) for the if-statement - so combined O(k) with k being the number of cities in the split result string.
Combining everything together, the runtime complexity of `public boolean isCycle(Edge e)` is O(n<sup>3</sup>). 

The bad runtime complexity motivated us to search for other algorithms. We found Union-Find algorithm. When deciding whether to
add an edge, we need to check if the start- and end-cities are connected. The algorithm Union-Find is implemented in the method `public boolean isCycle(Edge e)` and uses the private methods:  `int find(int x)` to search recursively for the root of the start- and edge cities; `void union(int x, int y)` connects the 2 disjoint structures and takes O(1) for each call.
The runtime of this algorithm depends heavily on the `find` function => on the depth of the trees. In worst case, all are like linear chains and we have 2 different linear chains and each edge makes the chain longer, so at most n/2. This operation is done for both start- and edge- cities, so at most O(n/2) + O(n/2) = O(n/2) = O(n) per iteration => O(nm) for all edges. In practice, the Union-Find algorithm typically achieves much better performance due to path compression and union by rank, resulting in amortized time complexity closer to O(mα(n)), where α(n) is the inverse Ackermann function, which grows very slowly.

The algorithm `public void kruskal()` firstly iterates through the nodes and collects all edges - O(m). Then edges are sorted - O(mlogm). After that it iterates through each edge (O(m)) and at each step the function `public boolean isCycle(Edge e)` is called in an if-condition, when true the following operations are done on the HashMap or incrementation and are in O(1).
So as a conclusion, the `public void kruskal()` algorithm takes O(m + mlogm + mn) => O(mn). The practical runtime should be even faster or closer to O(mlogm), considering the amortized time complexity. 
</div>