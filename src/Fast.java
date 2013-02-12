
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michal
 */
public class Fast {

    private static void drawPoints(Point[] points) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        int N = points.length;
        for (int i = 0; i < N; i++) {
            points[i].draw();
        }
    }

    private static void outputIt(Point[] arr) {

        StringBuilder sb;
        int N = arr.length;
        sb = new StringBuilder();
        sb.append(arr[0]);
        for (int i = 1; i < N; i++) {
            sb.append(" -> ");
            sb.append(arr[i]);
        }
        StdOut.println(sb);

    }

    private static void drawLine(Point[] arr) {
        arr[0].drawTo(arr[arr.length - 1]);
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

    private static void computeIt(Point[] points) {
        int N = points.length;
        Arrays.sort(points, 0, N);
        drawPoints(points);
        Point[] tempP = new Point[N];
        for (int k = 0; k < N; k++) {
            tempP[k] = points[k];
        }
        int start;
        double slope = 0;
        for (int i = 0; i < N - 1; i++) {
            if (i % 200 == 0) StdOut.println(i + "NEXT" + N + " " + (float)i/N);
            Point origin = points[i];
            Arrays.sort(tempP, origin.SLOPE_ORDER);
            start = 1;
            slope = origin.slopeTo(tempP[start]);
            for (int j = 2; j < N; j++) {
                
                if (origin.slopeTo(tempP[j]) != slope) {
                    if (j - start >= 3) {
                        Point[] arr = new Point[j - start + 1];
                        arr[0] = tempP[0];
                        for (int k = 0; k < j - start; k++) {
                            arr[k + 1] = tempP[start + k];
                        }
                        Arrays.sort(arr);
                        if (arr[0].compareTo(origin) == 0) {
                            outputIt(arr);
                            drawLine(arr);
                        }
                    }
                    slope = origin.slopeTo(tempP[j]);
                    start = j;

                }
            }
            if (N - start >= 3) {

                Point[] arr = new Point[N - start + 1];
                arr[0] = tempP[0];
                for (int k = 0; k < N - start; k++) {
                    arr[k + 1] = tempP[start + k];
                }
                Arrays.sort(arr);
                if (arr[0].compareTo(origin) == 0) {
                    outputIt(arr);
                    drawLine(arr);
                }
            }
        }
        StdDraw.show(0);
    }

    public static void main(String[] args) {
        //String filename = args[0];
    	//"input/8puzzle/puzzle11.txt";
        String filename = "input/collinear/input10000.txt";
        Point[] p = loadData(filename);
        computeIt(p);
    }
}
