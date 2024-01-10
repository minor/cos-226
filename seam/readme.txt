Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */

The algorithm essentially creates a grid to store the energy values for each
pixel, and uses Dijkstra's algorithm (with a priority queue) to iterate through
the pixels row-wise. As it does this, it relaxes the edges until reaching the
right-most column to find the shortest path.

/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */

Images that seem to work well with seam capturing are those that have gradual
changes and not images that have common occurences of high energy deltas. So
while this algorithm works well with images like the ocean or surfing or
natural landscapes, it may not work well for, say, a chess board. This is
because there is repetition but also many important features.

/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 2000
 multiplicative factor (for H) = 2

 H           time (seconds)      ratio       log ratio
------------------------------------------------------
250             0.269            n/a            n/a
500             0.471            1.75           0.56
1000            0.923            1.96           0.67
2000            1.827            1.98           0.68
4000            2.915            1.60           0.52
8000            5.652            1.94           0.66


(keep H constant)
 H = 2000
 multiplicative factor (for W) = 2

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
250               0.27            n/a          n/a
500               0.429           1.59         0.46
1000              0.777           1.81         0.59
2000              1.23            1.58         0.46
4000              2.635           2.14         0.76
8000              6.974           2.65         0.97



/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */


Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:


    ~ 5.3*10^-8 * W^1.5 * H^2
       _______________________________________

The general strategy that I was going for here was to find the pattern in the
data, and see how to correlate that to the tilde notations. But, I'm not sure
if my exponential values are accurate.

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

N/A


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I wasn't able to figure out how to place together the tilde notation for the
running time.


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
