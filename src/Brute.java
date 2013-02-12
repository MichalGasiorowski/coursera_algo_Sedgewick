
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michal
 */
public class Brute {

    private static void drawPoints(Point[] points) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        int N = points.length;
        for (int i = 0; i < N; i++) {
            points[i].draw();
        }
    }

    private static void outputIt(Point[] points, int[] arr) {

        StringBuilder sb;
        int N = arr.length;
        sb = new StringBuilder();
        sb.append(points[arr[0]]);
        for (int i = 1; i < N; i++) {
            sb.append(" -> ");
            sb.append(points[arr[i]]);
        }
        StdOut.println(sb);

    }

    private static void drawLine(Point[] points, int[] arr) {
        points[arr[0]].drawTo(points[arr[3]]);
    }

    private static Point[] loadData(String filename) {
        In in = new In(filename);
        int N = in.readInt();

        Point[] ret = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            ret[i] = p;
        }
        return ret;
    }

    private static void bruteForceIt(Point[] points) {
        int N = points.length;
        Arrays.sort(points, 0, N);
        drawPoints(points);

        for (int i1 = 0; i1 < N; i1++) {
            for (int i2 = i1 + 1; i2 < N; i2++) {
                for (int i3 = i2 + 1; i3 < N; i3++) {
                    for (int i4 = i3 + 1; i4 < N; i4++) {
                        double sl1 = points[i1].slopeTo(points[i2]);
                        double sl2 = points[i1].slopeTo(points[i3]);
                        double sl3 = points[i1].slopeTo(points[i4]);
                        if (sl1 == sl2 && sl1 == sl3) {
                            int[] arr = new int[4];
                            arr[0] = i1;
                            arr[1] = i2;
                            arr[2] = i3;
                            arr[3] = i4;
                            drawLine(points, arr);
                            outputIt(points, arr);
                        }
                    }
                }
            }
        }
        StdDraw.show(0);
    }

    public static void main(String[] args) {
        String filename = args[0];
        //String filename = "c:/Users/Michal/Dropbox/Programming/"
        //        +"NetBeansProjects/input/collinear/input8.txt";
        Point[] p = loadData(filename);
        bruteForceIt(p);
        //outputIt(p, dq);
    }
}
