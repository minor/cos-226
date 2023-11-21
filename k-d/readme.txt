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

For range search, I designed a method that begins the search starts at the root
of the tree and continues recursively through a recursive helper function,
efficiently pruning branches that don't intersect with the specified rectangle.
When a node's rectangular region intersects with the input rectangle, the
algorithm checks if the node's point falls within the input region, and adds it
to the result. Then, the search continues in both the left and right subtrees
*if they may* contain points within the specified region.

/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */

For the nearest neighbor, I use a recursive helper function that helps traverse
the tree efficiently. The search begins at the root, then employs the recursive
algorithm to traverse the tree. It selects the closest point found during the
traversal as the current "champion" and updates it whenever a closer point is
found. The search process alternates between comparing points along the x-coords
and y-coords to efficiently prune branches and find the nearest neighbor.

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

KdTreeST: 1,000,000 / 2.184 = ~457,875.46 calls per second

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
NA

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
I had a problem with the following error, and it took me 3 days to debug it.
- performs slightly incorrect traversal of k-d tree during call to nearest()
- performs the update-the-champion update before the pruning test
- it should perform the pruning test first

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
NA
