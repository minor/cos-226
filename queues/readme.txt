Programming Assignment 2: Deques and Randomized Queues


/* *****************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 **************************************************************************** */

For the deque, I used a linked list because I thought that if all the nodes
were "connected," it would be easier to add elements to the front or the
end. For the randomized queue, I used a resizable array because I thought that
using the random value as an index would be the easiest way to build the class.

/* *****************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 **************************************************************************** */

Randomized Queue:   ~  40n  bytes (16 bytes from object overhead, 8 bytes from
non-static nested class extra overhead, 8 bytes from reference to Item, 8
bytes from reference to Node)

Deque:              ~  32n  bytes (8*n from the reference to Item and array
length becomes 4n at worst, but at best is 8n)

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

None.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I had a problem with figuring out how to do the random processes, but I later
realized that there was the StdRandom package in `algs4`.


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
 Linked lists were really cool — I definitely see myself using them in the
 future on Leet Code problems.
