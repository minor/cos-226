import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class KdTreeST<Value> {
    private Node root;   // root of KdTree
    private int n;       // # of nodes in KdTree

    private class Node {
        private Point2D p; // the point
        private Value val; // the symbol table maps the point to this value
        private RectHV rect; // axis-aligned rectangle corresponding to node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private boolean xy; // determine if point is x-coord or y-coord


        // Node constructor
        public Node(Point2D p, Value val, RectHV rect, Node lb, Node rt,
                    boolean xy) {
            this.p = p;
            this.val = val;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.xy = xy;
        }
    }


    // construct an empty symbol table of points
    public KdTreeST() {
        root = null;
        n = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // number of points
    public int size() {
        return n;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        root = helperPut(root, p, val, true, Double.NEGATIVE_INFINITY,
                         Double.NEGATIVE_INFINITY, 1, 1);
    }

    // helper method for put
    private Node helperPut(Node node, Point2D p, Value val, boolean xy, double
            xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            n++;
            // ensure xmin and ymin are leq to xmax and ymax
            RectHV rect = new RectHV(Math.min(xmin, xmax), Math.min(ymin, ymax),
                                     Math.max(xmin, xmax),
                                     Math.max(ymin, ymax));
            return new Node(p, val, rect, null, null, xy);
        }

        double cmp;

        // if the point already exists, update the value (fixes size issue)
        if (node.p.equals(p)) {
            node.val = val;
        }
        else if (node.xy) {
            cmp = Double.compare(p.x(), node.p.x());
            if (cmp < 0) {
                // update xmax when inserting to the left subtree
                node.lb = helperPut(node.lb, p, val, false, xmin, ymin, node.p.x(), ymax);
            }
            else {
                // update xmin when inserting to the right subtree
                node.rt = helperPut(node.rt, p, val, false, node.p.x(), ymin, xmax, ymax);
            }
        }
        else {
            cmp = Double.compare(p.y(), node.p.y());
            if (cmp < 0) {
                // update ymax when inserting to the left subtree
                node.lb = helperPut(node.lb, p, val, true, xmin, ymin, xmax, node.p.y());
            }
            else {
                // update ymin when inserting to the right subtree
                node.rt = helperPut(node.rt, p, val, true, xmin, node.p.y(), xmax, ymax);
            }
        }
        return node;
    }


    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }

        Node current = root;

        while (current != null) {
            Point2D currentPoint = current.p;

            // debugging
            // System.out.println("p: " + p);
            // System.out.println("currentPoint: " + currentPoint);

            if (p.equals(currentPoint)) {
                // debugging
                // System.out.println("found matching point: " + currentPoint);
                // System.out.println("returning: " + current.val);
                return current.val; // return val
            }

            int cmp;
            if (current.xy) {
                cmp = Double.compare(p.x(), currentPoint.x());
            }
            else {
                cmp = Double.compare(p.y(), currentPoint.y());
            }

            // debugging
            // System.out.println("cmp: " + cmp);

            // decide where to go
            if (cmp < 0) {
                // debugging
                // System.out.println("going left");
                current = current.lb; // go left for smaller values
            }
            else {
                // debugging
                // System.out.println("going right right");
                current = current.rt; // go right for equal or greater values
            }

            if (current != null) {
                current.xy = !current.xy;
            }

            // if (p.equals(currentPoint)) {
            //     System.out.println("Finally breaking");
            //     break;
            // }
        }

        // debugging
        // System.out.println("Point not found.");
        return null; // point not found.
    }


    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        return get(p) != null;
    }

    // all points in the symbol table (level-order)
    public Iterable<Point2D> points() {
        if (isEmpty()) {
            return new Queue<Point2D>();
        }

        Queue<Node> queue = new Queue<Node>();
        Queue<Point2D> queuePoints = new Queue<Point2D>();
        queue.enqueue(root); // first the root, then lb, then rt
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x != null) {
                queuePoints.enqueue(x.p);
                queue.enqueue(x.lb);
                queue.enqueue(x.rt);
            }
        }
        return queuePoints;
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        // start at root & recursively search for points in subtrees
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null.");
        }
        Queue<Point2D> pointsRect = new Queue<Point2D>();
        helperPrune(root, rect, pointsRect);
        return pointsRect;
    }

    // helper function for pruning (where if input rect doesn't intersect at
    // a node, no need to explore node/subtrees)
    private void helperPrune(Node node, RectHV rect, Queue<Point2D> q) {
        // base case
        if (node == null) {
            return;
        }

        if (rect.intersects(node.rect)) {
            if (rect.contains(node.p)) q.enqueue(node.p);
            helperPrune(node.lb, rect, q);
            helperPrune(node.rt, rect, q);
        }
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (isEmpty()) {
            return null;
        }
        return helperNearest(root, p, root.p, true);
    }

    // helper function for finding the nearest point
    private Point2D helperNearest(Node node, Point2D p, Point2D n, boolean xy) {
        if (node == null) {
            return n;
        }

        Point2D champion = n; // create champion point
        double nodePtoP = node.p.distanceSquaredTo(p);
        double championToP = champion.distanceSquaredTo(p);

        if (nodePtoP < championToP) {
            champion = node.p;
        }

        // if current point is closer than next distance between next point
        // and node's rectangle, then no need to look at node/subtrees
        if (node.rect.distanceSquaredTo(p) < n.distanceSquaredTo(p)) {
            double cmp;
            if (node.xy) {
                cmp = Double.compare(p.x(), node.p.x());
            }
            else {
                cmp = Double.compare(p.y(), node.p.y());
            }

            if (cmp < 0) {
                // explore left first
                n = helperNearest(node.lb, p, n, xy);
                n = helperNearest(node.rt, p, n, xy);
            }
            else {
                // explore right first
                n = helperNearest(node.rt, p, n, xy);
                n = helperNearest(node.lb, p, n, xy);
            }
        }
        return n;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // create new kdtree
        KdTreeST<Double> kdtree = new KdTreeST<Double>();

        // read in file and assign its arguments to variables
        In in = new In(args[0]);
        double x, y;
        Point2D p;
        double val = 0.0;
        while (!in.isEmpty()) {
            x = in.readDouble();
            y = in.readDouble();
            p = new Point2D(x, y);
            kdtree.put(p, val);
        }

        // testing for readme (start a stopwatch, go through 1M nearest
        // neighbors, then stop the stopwatch)
        Stopwatch stopwatch = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            kdtree.nearest(new Point2D(StdRandom.uniformDouble(),
                                       StdRandom.uniformDouble()));
        }
        double time = stopwatch.elapsedTime();
        System.out.println(time);

        // // // debugging/test cases
        // //
        // // // Add some points to the tree
        // // Point2D p1 = new Point2D(0.5, 0.5);
        // // Point2D p2 = new Point2D(0.2, 0.2);
        // // Point2D p3 = new Point2D(0.8, 0.8);
        // // kdtree.put(p1, 1.0);
        // // kdtree.put(p2, 2.0);
        // // kdtree.put(p3, 3.0);
        // //
        // // // Test the get() function to retrieve values associated with points
        // // Point2D searchP1 = new Point2D(0.5, 0.5);
        // // Point2D searchP2 = new Point2D(0.2, 0.2);
        // // Point2D searchP3 = new Point2D(0.8, 0.8);
        // //
        // // // case 1: retrieve val at p1
        // // System.out.println("val associated with p1: "
        // //                            + kdtree.get(searchP1)); // expected: 1.0
        // //
        // // // case 2: retrieve val at p2
        // // System.out.println("val associated with p2: "
        // //                            + kdtree.get(searchP2)); // expected: 2.0
        // //
        // // // case 3: retrieve val at p3
        // // System.out.println("val associated with p3: "
        // //                            + kdtree.get(searchP3)); // expected: 3.0
        // //
        // // // case 4: retrieve val for point not in tree
        // // System.out.println(
        // //         "Value associated with non-existent point: " +
        // //                 kdtree.get(new Point2D(0.3, 0.3))); // expected: null

        // // testing for contains and duplicates b/c that was broken for me:
        // // create a new KdTree
        // KdTreeST<Double> kdtree = new KdTreeST<Double>();
        //
        // System.out.println(kdtree.size());
        // System.out.println(kdtree.isEmpty());
        // // System.out.println(kdtree.points());
        // // System.out.println(kdtree.contains(new Point2D(1.0, 0.875)));
        // // System.out.println(kdtree.get(new Point2D(2.0, 0.975)));
        // // System.out.println(kdtree.nearest(new Point2D(2.0, 0.975)));
        //
        // Point2D pointA = new Point2D(1.0, 0.875);
        // Point2D pointB = new Point2D(0.75, 0.375);
        // Point2D pointC = new Point2D(0.5, 1.0);
        // Point2D pointD = new Point2D(0.125, 0.75);
        // Point2D pointE = new Point2D(0.875, 0.125);
        // Point2D pointF = new Point2D(0.75, 0.375);
        //
        // kdtree.put(pointA, 0.0);
        // kdtree.put(pointB, 0.0);
        // kdtree.put(pointC, 0.0);
        // kdtree.put(pointD, 0.0);
        // kdtree.put(pointE, 0.0);
        // System.out.println("\nPoints: " + kdtree.points());
        // System.out.println("Size: " + kdtree.size());
        // System.out.println("Get value of B: " + kdtree.get(pointB));
        // kdtree.put(pointF, 1.0);
        // System.out.println("\nPoints: " + kdtree.points());
        // System.out.println("Size: " + kdtree.size());
        // System.out.println("Get value of F: " + kdtree.get(pointF));
        //
        //
        // Point2D queryPoint = new Point2D(1.0, 0.875);
        //
        //
        // System.out.println(kdtree.contains(queryPoint));
    }
}
