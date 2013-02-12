
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michal
 */
public class Solver {

    private Stack<Board> solList;
    private Board initialB;
    private int minMoves;
    
    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;
        private int moves;

        public Node(Node prev, Board board) {
            this.board = board;
            this.prev = prev;
            if (prev != null)
                moves = prev.moves + 1;
            else
                moves =  0;
        } 

        @Override
        public int compareTo(Node o) {
            int pr1 = moves + board.manhattan();
            int pr2 = o.moves + o.board.manhattan();
            if (pr1 < pr2)
                return -1;
            else if (pr1 > pr2)
                return 1;
            else
                return 0;
        }
    }
    
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        initialB = initial;
        solList = new Stack<>();
        Board twin = initial.twin();
        MinPQ<Node> mpq = new MinPQ<Node>();
        mpq.insert(new Node(null, initial));
        mpq.insert(new Node(null, twin));
        Node current;
//        StdOut.println("Begin solving");
//        StdOut.println("initial: " + initial + " h:  " + initial.hamming() 
//                +  " manh:  " + initial.manhattan());
//        
//        StdOut.println("evil twin: " + twin + " h:  " + initial.hamming() 
//                +  " manh:  " + initial.manhattan());
//        int i = 0;
//        int numOfInserts = 2;
        while (true) {
            current = mpq.delMin();
//            StdOut.println(++i + " iteration, moves:" + current.moves +
//                    " manhattan(): "+ current.board.manhattan() + 
//                    " hamming(): "+ current.board.hamming() + "\n" 
//                    + current.board );
            if (current.board.isGoal()) {
                minMoves = current.moves;
                while (current != null) {
                    solList.push(current.board);
                    current = current.prev;
                }
                break;
            }
            for (Board bb : current.board.neighbors()) {
                if (current.prev == null || !bb.equals(current.prev.board)) {
                    mpq.insert(new Node(current, bb));
//                    numOfInserts++;
                }
            }
        }
//        StdOut.println("Num of inserts: " + numOfInserts);
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return solList.peek() == initialB;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        if (!isSolvable())
            return -1;
        return minMoves;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if no solution
        if (!isSolvable())
            return null;
        return solList;
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
    	StdOut.println(System.getProperty("user.dir"));
        //In in = new In(args[0]);
                String filename = "input/8puzzle/puzzle11.txt";
        In in = new In(filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
