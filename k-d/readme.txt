Programming Assignment 5: K-d Trees


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */

I drew from the textbook when I designed my Node data type, but I optimized it
for KdTrees. The data type as implemented contains a Point, a corresponding
Value, an axis-aligned rectangle corresponding to node, another two nodes for
the left/right subtrees (respectively), and a boolean that determines whether
the node is vertical or not.

/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */

For range search, I started at the root, and recursively searched for points in
subtrees that the recantangle contains. To make this more efficient, I created
a helper function for pruning that the need to explore nodes/subtrees that
don't intersect the input rectangle.


/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */

For the nearest neighbor, I use a rcursive helper function that traverses the
tree efficiently, updating the nearest neighbor as it goes. It also prunes
the search space based on distance comparisons, which allows it to optimally
find the nearest neighbor.

/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, rounding each value to use one digit after
 *  the decimal point. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST: 1,000 / 48.159 = ~20.76 calls per second

KdTreeST: 1,000,000 / 2.494 = ~400,962.31 calls per second

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
NA

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
NA

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
NA
