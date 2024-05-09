## Exercise 1:
In this exercise we implemented the 5 different algorithms from the lecture 
for solving the MSS (maximal scoring subsequence) problem and here we will analyse
and compare their theoretical and practical runtime: 

* **MSS naive algorithm:**  
The naive algorithm uses 3 nested loops - the outer loop iterates (with `i` from 
0 to the length of the sequence) over all possible starting positions for the 
subsequence, while the middle loop (with `j`) iterates over all possible end-points of 
(starting from `i`). Those two loops determine the subsequence window of interest 
the third inner loops sums the elements in the positions between `i` and `j`.
As each of these loops can run up to `n` iterations (`n` is the length of the input
sequence), this results in a theoretical time complexity of __O__(n<sup>3</sup>). 
The practical runtime can depend on multiple factors, such as the HashSet operations,
which are not considered in the theoretical runtime. The used operations `clear()` and `add()` 
are typically efficient, but their cost could accumulate, but still usually small compared to
the cost of the triple nested loops.  
While there might be some variation due to system-specific factors, given the above considerations,
the practical runtime should be generally close to the theoretical runtime of __O__(n<sup>3</sup>).  
----- 
* **MSS naive and recursive algorithm:**  
The naive recursive algorithm modifies the naive one by replacing the inner loop (which computes the
sum of the elements in each subsequence) with a recursive function, which again iterates through each 
position in each subsequence (denoted by the given indicies `i` and `j`).  
This algorithm is also correct, because the only difference with the naive algorithm is the recursive
summation and the used recursive function has a termination condition - when the end of the sequence is
reached, the start and end indices will be equal. (`if(start == end) return sequence[start];`). Otherwise, the 
function is called with a bigger start-index, which makes the sum-window smaller until one element in it is left.
Theoretically the runtime complexity remains __O__(n<sup>3</sup>), but in this case the practical runtime differs from
the theoretical, as recursive calls in Java involve additional overhead (computational work, resources, memory etc.), which 
is absent in simple operations like summation. This is proven by the bigger runtime, compared to the naive algorithm. 
---
* **MSS dynamic programming:**  
The Dynamic Programming (DP) algorithm offers a different approach compared to the previously discussed naive and naive
recursive methods. The DP algorithm uses a 2D array (or matrix) to store sums of all possible subsequences and each new sum
is calculated by the sum at position `(i, j-1)` - the previous computed sum (of all elements before the last one at position `(i, j)`) 
and adding the last element (at position `j`)) - which is the new addition to the last computed sum. This method avoids redundant calculations.
The algorithm still uses 2 for-loops and each inner-loop computes one summation (__O__(1)) and saves the new sum in the matrix (__O__(1)), which
is the reason why the DP algorithm has runtime complexity of __O__(n<sup>2</sup>). This more efficient approach is a result of reduction of redundant calculations. 
The practical runtime is similar to the theoretical (the HashSet operations do not have a big influence the runtime, but do add to the constant 
factor). But there is increased memory usage due to the storage of an n×n matrix. 
---  
* **MSS divide-and-conquer:**  
The Divide-and-Conquer (DC) algorithm is suitable for problems like MSS, which can be broken down into smaller subproblems, which
can be solved independently and then combined.  
The recursive DC algorithm divides the sequence into two halves until the base case is reached, when the subsequence consists of 
only one element, which if positive - it is returned as the maximum subsequence, or zero. The algorithm recursively finds the
maximal scoring subsequence from the two halves and also checks the existence of such sequences across the two halves. This 
requires additional functions (`maxCrossIndexLeft` and `maxCrossIndexRight`) to find the index of the maximum sum subsequence that 
crosses the midpoint, starting from the middle to the left end and from the middle to the right end, respectively.  
The theoretical time complexity could be expressed by the recurrence relation T(n) = 2T(n/2) + __O__(n), where the __O__(n) term 
comes from the additional linear functions required to find the maximum crossing subsequence, which could iterate to both ends
of the sequence, hence why __O__(n). In this algorithm each problem is divided into 2 subproblems of half the original size, therefore 
when applying the Master theorem a = 2 and b = 2, f(n) = __Θ__(n) => c = 1. log<sup>b</sup>a = log<sup>2</sup>2 = 1 = c => we use Case 2, which 
means T(n)=Θ(nlogn).  
In this case the practical and theoretical runtimes align, as the algorithm is avoiding redundant calculations, and optimising work
distribution by dividing the problem. The recursive overhead is not playing a big role given the logarithmic depth of the recursion.  
---
* **MSS optimal algorithmus:**  
The optimal algorithm uses one loop to iterate through the array, examining each element exactly once to determine if it should 
be included in the current subarray or if a new subarray should start again. For each position in the input sequence, the algorithm performs
a constant amount of work (updating variables by performing summation once and/or updating them with new values), so only the iteration through
the array is considered - resulting in a runtime complexity of __Θ__(n).  
The practical time is also the same, as the algorithm does nit need additional memory and the performed operations are simple. 

## Exercise 2:
* **All MSS using the optimal solution, including overlaps**  
This approach uses the optimal algorithm from exercise 1 and also allows overlapping subsequences. It has the same
theoretical runtime like the optimal __O__(n), as it also uses one for-loop to iterate through the sequence and the
operations in each iteration are executed in constant time - __O__(1), but here there are additional operations with HashSet, which
explains the bigger practical time, compared to the fastest MSS algorithm from the previous exercise. 
---
* **SMSS**  
Both algorithms have a theoretical time complexity of __O__(n), where n is the length of the sequence. However, the additional
condition `(lstart != rstart)` adds a slight overhead as it checks for the uniqueness of the start index in addition
to the sum, which could slightly affect the practical runtime but not the complexity class. 
---
* **SMSS with minimal length**  
The minimalSMSS method further refines the approach to finding maximum sum subsequences by focusing not only on finding 
subsequences that achieve the maximum sum but also ensuring that only the subsequences of the minimal possible length are
captured. The theoretical complexity remains __O__(n), as it still involves a single pass through the array with 
operations that execute in constant time. However, the conditional logic is slightly more complex due to additional checks
for length, potentially making this algorithm slightly slower in practical scenarios due to these overheads.   