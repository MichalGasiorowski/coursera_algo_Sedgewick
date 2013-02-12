
public class KdTree {
	
	private static final boolean VERTICAL = true;
	private static final boolean HORIZONTAL = false;
	private static final double SMIN = 0;
	private static final double SMAX = 1;
	
	private Node root;
	private int treeSize = 0;
	
	public KdTree() {
	   // construct an empty set of points
		root = null;
	}
	
	private static class Node {
		   private Point2D p;      // the point
		   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		   private Node lb;        // the left/bottom subtree
		   private Node rt;        // the right/top subtree
	}
	
	private boolean checkInLTFirst(Point2D query, boolean vert, Point2D node) {
		//StdOut.println("query: " + query + "vert: " + vert + "node: " + node);
		int comp;
		if (vert) {
			comp = Point2D.X_ORDER.compare(node, query);
		}
		else {
			comp = Point2D.Y_ORDER.compare(node, query);
		}
		if (comp == 1 || comp == 0)
			return true;
		return false;
	}
	
	private void draw(Node curr, boolean vert) {
		if (curr == null)
			return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		//StdOut.print(curr.p.toString());
		StdDraw.point(curr.p.x(), curr.p.y());
		StdDraw.setPenRadius();
		if (vert) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(curr.p.x(), curr.rect.ymin(), curr.p.x(), curr.rect.ymax());
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(curr.rect.xmin(), curr.p.y(), curr.rect.xmax(), curr.p.y());
		}
		draw(curr.lb, !vert);
		draw(curr.rt, !vert);
	}
	
	private void range(Node curr, RectHV rect, Stack<Point2D> ret) {
		// all points in the set that are inside the rectangle
		if (curr == null)
			return;
		if (!curr.rect.intersects(rect))
			return;
		if (rect.contains(curr.p)) {
			ret.push(curr.p);
			//StdOut.print(curr.p.toString());
		}
		range(curr.lb, rect, ret);
		range(curr.rt, rect, ret);
	}
	
	private Point2D nearest(Node curr, Point2D p, double[] bSoFar, boolean vert) {
		//StdOut.println(bSoFar[0]);
		if (curr == null)
			return null;
		if (curr.rect.distanceSquaredTo(p) > bSoFar[0])
			return null;
		//StdOut.println(curr.p.toString());
		Point2D res = null;
		double bRes = p.distanceSquaredTo(curr.p);
		double distToLIne = 0;
		if (vert)
			distToLIne = curr.p.x() - p.x();
		else
			distToLIne = curr.p.y() - p.y();
		distToLIne = distToLIne * distToLIne;
//		StdOut.println(" currentPoint: " + curr.p + "vert: " + vert + " Querypoint: " + p);
//		//StdOut.println(" lt: " + curr.lb.p + " rt: "  + curr.rt.p);
//		StdOut.println(" bSoFar: " + bSoFar[0] + " distance: " + bRes + " distToLine: " + distToLIne);
		if (bRes < bSoFar[0]) {
			res = curr.p;
			bSoFar[0] = bRes;
		}
		Point2D bLeft, bRight;
		if (checkInLTFirst(p, vert, curr.p)) {
			//StdOut.println("LEFT/DOWN");
			if (curr.lb != null) {
				//StdOut.println("  Entering left 1");
				bLeft = nearest(curr.lb, p, bSoFar, !vert);
				if (bLeft != null) {
					res = bLeft;
				}
			}
			if (distToLIne > bSoFar[0]) {
				//StdOut.println("Cut1!" + distToLIne + " " + bSoFar[0] + res);
				return res;
			}
			if (curr.rt != null) {
				//StdOut.println("  Entering right 2");
				bRight = nearest(curr.rt, p, bSoFar, !vert);
				if (bRight != null) {
					res = bRight;	
				}
			}
		}
		else {
			//StdOut.println("RIGHT/UP");
			if (curr.rt != null) {
				//StdOut.println("  Entering right 1");
				bRight = nearest(curr.rt, p, bSoFar, !vert);
				if (bRight != null) {
					res = bRight;
				}
			}
			if (-distToLIne > bSoFar[0]) {
				//StdOut.println("Cut2!" + distToLIne + " " + bSoFar[0] + res);
				return res;
			}
			if (curr.lb != null) {
				//StdOut.println("  Entering left 2");
				bLeft = nearest(curr.lb, p, bSoFar, !vert);
				if (bLeft != null) {
					res = bLeft;
				}
			}
		}
		//StdOut.println("End return " + res);
		return res;
	}
	
	public boolean isEmpty() {
	   // is the set empty?
		return treeSize == 0;
	}
   
	public int size() {
	   // number of points in the set
		return treeSize;
	}
   
	public void insert(Point2D p) {
	   // add the point p to the set (if it is not already in the set)
		boolean vert = VERTICAL;
		double xmin = SMIN, ymin = SMIN;
		double xmax = SMAX, ymax = SMAX;
		
		if (isEmpty()) {
			root = new Node();
			root.lb = null;
			root.rt = null;
			root.p = p;
			root.rect = new RectHV(xmin, ymin, xmax, ymax);
			treeSize++;
			return;
		}
		
		Node current = root;
		Node parent = root;
		int comp;
		boolean left = true;
		while (current != null) {
			parent = current;
			if (vert) {
				comp = Point2D.X_ORDER.compare(p, current.p);
			}
			else {
				comp = Point2D.Y_ORDER.compare(p, current.p);
			}
			if (comp == 1) {
				if (vert) 
					xmin = current.p.x();
				else
					ymin = current.p.y();
				left = false;
				current = current.rt;
			}
			else if (comp == -1) {
				if (vert) 
					xmax = current.p.x();
				else
					ymax = current.p.y();
				left = true;
				current = current.lb;
			}
			else { 
				if (current.p.equals(p))
					return;
				left = false;
				current = current.rt;
			}
			vert = !vert;
		}
		current = new Node();
		current.p = p;
		current.lb = null;
		current.rt = null;
		current.rect = new RectHV(xmin, ymin, xmax, ymax);
		if (left)
			parent.lb = current;
		else
			parent.rt = current;
		treeSize++;
		//StdOut.println(size());
	}
   
	public boolean contains(Point2D p) {
	   // does the set contain the point p?
		boolean vert = VERTICAL;
		
		Node current = root;
		int comp;
		while (current != null) {
			if (vert) {
				comp = Point2D.X_ORDER.compare(p, current.p);
			}
			else {
				comp = Point2D.Y_ORDER.compare(p, current.p);
			}
			if (comp == 1) {
				current = current.rt;
			}
			else if (comp == -1) {
				current = current.lb;
			}
			else {
				if (current.p.equals(p))
					return true;
				current = current.rt;
			}
			vert = !vert;
		}
		return false;
	}
   
	public void draw() {
	   // draw all of the points to standard draw
        draw(root, VERTICAL);
	}
   
	public Iterable<Point2D> range(RectHV rect) {
	   // all points in the set that are inside the rectangle
		Stack<Point2D> st = new Stack<>();
		range(root, rect, st);
		return st;
	}
   
	public Point2D nearest(Point2D p) {
	   // a nearest neighbor in the set to p; null if set is empty
		double[] bSoFar = new double[1];
		bSoFar[0] = 1000;
		return nearest(root, p, bSoFar, VERTICAL); 
	}
}
