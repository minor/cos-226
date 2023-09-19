/* *****************************************************************************
 *  Operating system: OS X Ventura 13.5.1
 *  [ examples are "OS X Ventura 13.5", "Windows 11", and "Ubuntu 22.04" ]
 *
 *  Compiler: Temurin 11.0.20
 *  [ an example is "Temurin 11.0.20" ]
 *
 *  Text editor / IDE: IntelliJ 2023.2.1
 *  [ an example is "IntelliJ 2023.2" ]
 *
 *  Have you taken (part of) this course before: No
 *  Have you taken (part of) the Coursera course Algorithms, Part I or II: No
 *
 *  Hours to complete assignment (optional): 6
 *
 **************************************************************************** */

Programming Assignment 1: Percolation


/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.
 **************************************************************************** */

boolean[][] grid — the 2D array representing the grid of sites. Each site is a
boolean value, where true indicates open and false indicates closed.
int gridSize — a variable holding the value of n, for the row and col size
int openSites — a variable representing the number of open sites in the grid.
Updated as sites are converted to open.
int virtualBottom — a "virtual" bottom for the grid that makes calculating for
percolation quicker and easier. Set to n^2 + 1.
int virtualTop — a "virtual" top for the grid that makes calculating for
percolation quicker and easier. Set to 0.
WeightedQuickUnionUF wQF — data structure used to efficently model the
connectivity between each site in the grid. It uses Weighted Quick Union so
that the runtime is efficient and essentially each site corresponds with an
element in wQF. Once a site is opened, it's connected with other open sites in
the data structure to model connectivity.

/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement each method in
 *  the Percolation API.
 **************************************************************************** */

open(): used to open a site. It uses the union algorithm to connect the newly
opened site to the existing open sites around it (if they
exist).
isOpen(): checks whether a given site is open or not, by reading the boolean
value stored in the 2D array at that location. True indicates open and false
indicates closed.
isFull(): checks whether a given site is full or not via a find algorithm that
checks the connectivity of two sites. Also uses a helper function called
"flatten," that converts a position in a 2D array to a 1D one.
numberOfOpenSites(): returns the number of open sites that is updated via a
counter variable that adds one every time a site has been opened.
percolates(): checks if the system percolates. Uses the find algorithm to check
if the virtual top and the virtual bottom are connected. If yes, the system
percolates; if no, then it doesn't.

/* *****************************************************************************
 *  First, implement Percolation using QuickFindUF.
 *  What is the largest values of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
100                 5.595
125                 17.374
150                 27.154
175                 53.368
185                 69.685

/* *****************************************************************************
 *  Describe the strategy you used for selecting the values of n.
 **************************************************************************** */

I chose an arbitrary number n for the first trial. I happened to choose a small
number—like 100—because I knew that my system would be able to handle it in a
timely fashion. Once I had a good estimate for how much time n=100 took, I
increased n by 25, until the time became close to 60 seconds.

/* *****************************************************************************
 *  Next, implement Percolation using WeightedQuickUnionUF.
 *  What is the largest values of n that PercolationStats can handle in
 *  less than one minute on your computer when performing T = 100 trials?
 *
 *  Fill in the table below to show the values of n that you used and the
 *  corresponding running times. Use at least 5 different values of n.
 **************************************************************************** */

 T = 100

 n          time (seconds)
--------------------------
1000                 26.332
2000                 180.361
1500                 110.828
1350                 88.661
1250                 59.931


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

Backwash — I wasn't able to figure out how to get rid of backwash, so there will
be numerous open sites on the bottom for something that percolates.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I had some serious troubles with indexes—i.e. starting from 0 and ending at n-1,
or other scenarios. But, I was able to get the hang of it by the end of the
project, and I'm glad that I had to go through hours of debugging just to
realize my error was an index error. Now, hopefully, I won't make those
mistakes again.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

A video explanation on how to use Percolation Visualizer could have been
helpful. I ended up just exploring the project a little and figured it out, but
it wasn't super clear.
