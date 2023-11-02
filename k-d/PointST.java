import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PointST<Value> {
    // create a redblackbst
    private RedBlackBST<Point2D, Value> bst;

    // construct an empty symbol table of points
    public PointST() {
        bst = new RedBlackBST<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // number of points
    public int size() {
        return bst.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        bst.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        return bst.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        return bst.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return bst.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null");
        }
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D point : points()) {
            if (rect.contains(point)) {
                queue.enqueue(point); // if point in rect, add to queue
            }
        }
        return queue;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (isEmpty()) {
            return null;
        }

        // iterate through points() and use squared distance from recommended
        // optimizations

        Point2D closestPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        double distance;
        for (Point2D newP : points()) {
            distance = p.distanceSquaredTo(newP);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = newP;
            }
        }
        return closestPoint;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // define symbol table
        PointST<Integer> table = new PointST<>();

        // read in file and assign its arguments to variables
        In in = new In(args[0]);
        double x, y;
        Point2D p;
        int val = 0;
        while (!in.isEmpty()) {
            x = in.readDouble();
            y = in.readDouble();
            p = new Point2D(x, y);
            table.put(p, val);
        }

        // testing for readme (start a stopwatch, go through 1k nearest
        // neighbors, then stop the stopwatch)
        Stopwatch stopwatch = new Stopwatch();
        for (int i = 0; i < 1000; i++) {
            table.nearest(new Point2D(StdRandom.uniformDouble(),
                                      StdRandom.uniformDouble()));
        }
        double time = stopwatch.elapsedTime();
        System.out.println(time);

        // // debugging/test cases
        // // create points
        // Point2D p1 = new Point2D(0.2, 0.3);
        // Point2D p2 = new Point2D(0.4, 0.5);
        // Point2D p3 = new Point2D(0.3, 0.4);
        //
        // // test isEmpty() [first case]
        // System.out.println("Table is empty: " + table.isEmpty());
        //
        // // test put()
        // table.put(p1, 1);
        // table.put(p2, 2);
        // table.put(p3, 3);
        //
        // // test size()
        // System.out.println("Table's size: " + table.size());
        //
        // // test isEmpty() [second case]
        // System.out.println("Table is empty: " + table.isEmpty());
        //
        // // test contains() and get()
        // System.out.println("Contains p1: " + table.contains(p1));
        // System.out.println("Value associated with p2: " + table.get(p2));
        // System.out.println("Points in the symbol table:");
        // for (Point2D p : table.points()) {
        //     System.out.println(p);
        // }
        //
        // // check range()
        // RectHV rect = new RectHV(0.1, 0.1, 0.3, 0.4);
        // System.out.println("Points in range:");
        // for (Point2D p : table.range(rect)) {
        //     System.out.println(p);
        // }
        //
        // // check nearest()
        // Point2D p4 = new Point2D(0.7, 0.8);
        // table.put(p4, 4);
        // Point2D p5 = new Point2D(0.9, 0.8);
        // System.out.println("Nearest neighbor to p5: " + table.nearest(p5));

    }

}
