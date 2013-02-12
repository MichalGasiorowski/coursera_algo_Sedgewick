/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michal
 */
public class Board {

    private final byte[][] blocks;
    private final int N;
    private int blankI, blankJ;
    private int cachedManhattan = -1;
    private int cachedHamming = -1;

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks.length;
        this.blocks = new byte[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = (byte) blocks[i][j];
                if (blocks[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                }
            }
        }
    }

    private static void swap(byte[][] bl, int i, int j, int dx, int dy) {
        byte temp = bl[i][j];
        bl[i][j] = bl[i + dx][j + dy];
        bl[i + dx][j + dy] = temp;
    }

    private Board copyBoard(byte[][] bBlocks) {

        int[][] tempB = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tempB[i][j] = (int) bBlocks[i][j];
            }
        }
        return new Board(tempB);
    }

    private int hamming(int num, int placeI, int placeJ) {
        if (num == 0) {
            return 0;
        }
        if (num == placeI * N + 1 + placeJ) {
            return 0;
        } else {
            return 1;
        }
    }

    private int manhattan(int num, int placeI, int placeJ) {
        if (num == 0) {
            return 0;
        }
        int rightI = (num - 1) / N;
        int rightJ = (num - 1) % N;
        return Math.abs(placeI - rightI) + Math.abs(placeJ - rightJ);
    }

    public int dimension() {
        // board dimension N
        return N;
    }

    public int hamming() {
        // number of blocks out of place
        if (cachedHamming == -1) {
            cachedHamming = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    cachedHamming += hamming(blocks[i][j], i, j);
                }
            }
        }
        return cachedHamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        if (cachedManhattan == -1) {
            cachedManhattan = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    cachedManhattan += manhattan(blocks[i][j], i, j);
                }
            }
        }
        return cachedManhattan;
    }

    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
        Board twinBoard = copyBoard(blocks);
        byte temp;
        if (blankI != 0) {
            temp = twinBoard.blocks[0][0];
            twinBoard.blocks[0][0] = twinBoard.blocks[0][1];
            twinBoard.blocks[0][1] = temp;
        } else {
            temp = twinBoard.blocks[1][0];
            twinBoard.blocks[1][0] = twinBoard.blocks[1][1];
            twinBoard.blocks[1][1] = temp;
        }
        return twinBoard;
    }

    @Override
    public boolean equals(Object y) {
        // does this board equal y?
        if (y == null) {
            return false;
        }

        if (y == this) {
            return true;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board b = (Board) y;
        if (b.N != this.N) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (b.blocks[i][j] != this.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Stack<Board> st = new Stack<>();
        byte[][] temp = new byte[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                temp[i][j] = blocks[i][j];
            }
        }
        if (blankI != 0) {
            swap(temp, blankI, blankJ, -1, 0);
            st.push(copyBoard(temp));
            swap(temp, blankI - 1, blankJ, 1, 0);
        }
        if (blankI != N - 1) {
            swap(temp, blankI, blankJ, 1, 0);
            st.push(copyBoard(temp));
            swap(temp, blankI + 1, blankJ, -1, 0);
        }
        if (blankJ != 0) {
            swap(temp, blankI, blankJ, 0, -1);
            st.push(copyBoard(temp));
            swap(temp, blankI, blankJ - 1, 0, 1);
        }
        if (blankJ != N - 1) {
            swap(temp, blankI, blankJ, 0, 1);
            st.push(copyBoard(temp));
            swap(temp, blankI, blankJ + 1, 0, -1);
        }
        return st;
    }

    @Override
    public String toString() {
        // string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
