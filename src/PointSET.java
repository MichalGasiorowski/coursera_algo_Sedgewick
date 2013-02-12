public class PointSET {
   
	private SET<Point2D> pointBST;
	
	public PointSET() {
	   // construct an empty set of points
		pointBST = new SET<Point2D>();
	}
   
	public boolean isEmpty() {
	   // is the set empty?
		return size() == 0;
	}
   
	public int size() {
	   // number of points in the set
		return pointBST.size();
	}
   
	public void insert(Point2D p) {
	   // add the point p to the set (if it is not already in the set)
		pointBST.add(p);	
	}
   
	public boolean contains(Point2D p) {
	   // does the set contain the point p?
		return pointBST.contains(p);
	}
   
	public void draw() {
	   // draw all of the points to standard draw
		//StdDraw.clear();
        //StdDraw.setPenColor(StdDraw.BLACK);
        //StdDraw.setPenRadius(.01);
        //StdDraw.setXscale(0, 1);
        //StdDraw.setYscale(0, 1);
        for (Point2D item: pointBST) {
        	StdDraw.point(item.x(), item.y());
        }
        //StdDraw.show();
	}
   
	public Iterable<Point2D> range(RectHV rect) {
	   // all points in the set that are inside the rectangle
		Stack<Point2D> st = new Stack<>();
		for (Point2D item: pointBST) {
			if (rect.contains(item)) {
				st.push(item);
			}
		}
		return st;
	}
   
	public Point2D nearest(Point2D p) {
	   // a nearest neighbor in the set to p; null if set is empty
		double minDist = 10000;
		double currDist;
		Point2D near2D = new Point2D(0, 0);
		
		for (Point2D item: pointBST) {
			currDist = p.distanceSquaredTo(item);
			if (currDist < minDist) {
				near2D = item;
				minDist = currDist;
			}
		}
		return near2D;
		
	}
}
