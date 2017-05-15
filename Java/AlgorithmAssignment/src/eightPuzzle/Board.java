package eightPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Qiang
 * @since 23/04/2017
 */
public class Board {

    private int[][] blocks;
    private int dimension;

    /**
     * construct a board from an n-by-n array of blocks where blocks[i][j] = block in row i, column j
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }

        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }

    }

    /**
     * board dimension n
     */
    public int dimension() {
        return dimension;
    }

    /**
     * number of blocks out of place
     *
     * @return number of blocks out of place
     */
    public int hamming() {
        int result = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != i * dimension + j + 1 && blocks[i][j] != 0) {
                    result++;
                }
            }
        }

        return result;

    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0) {
                    int goalI = (blocks[i][j] - 1) / dimension;
                    int goalJ = (blocks[i][j] - 1) % dimension;
                    result += Math.abs(goalI - i) + Math.abs(goalJ - j);
                }

            }
        }

        return result;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     *
     * @return
     */
    public Board twin() {
        int zeroI = -1;
        int zeroJ = -1;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break;
                }
            }
        }

        int swapLine = zeroI == 0 ? 1 : 0;
        return getSwapBoard(swapLine, 0, swapLine, 1);
    }

    private Board getSwapBoard(int x1, int y1, int x2, int y2) {
        int temp = blocks[x1][y1];
        blocks[x1][y1] = blocks[x2][y2];
        blocks[x2][y2] = temp;

        Board newBoard = new Board(blocks);

        temp = blocks[x1][y1];
        blocks[x1][y1] = blocks[x2][y2];
        blocks[x2][y2] = temp;

        return newBoard;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(blocks, board.blocks);
    }


    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        int zeroI = -1;
        int zeroJ = -1;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break;
                }
            }
        }

        List<Board> boards = new ArrayList<>();
        if (zeroI != 0)
            boards.add(getSwapBoard(zeroI, zeroJ, zeroI - 1, zeroJ));
        if (zeroI != dimension - 1)
            boards.add(getSwapBoard(zeroI, zeroJ, zeroI + 1, zeroJ));
        if (zeroJ != 0)
            boards.add(getSwapBoard(zeroI, zeroJ, zeroI, zeroJ - 1));
        if (zeroJ != dimension - 1)
            boards.add(getSwapBoard(zeroI, zeroJ, zeroI, zeroJ + 1));

        return boards;

    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(dimension * dimension * 5);
        buffer.append(dimension).append('\n');
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                buffer.append(blocks[i][j]).append('\t');
            }
            buffer.append('\n');
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    public static void main(String[] args) {
    }
}