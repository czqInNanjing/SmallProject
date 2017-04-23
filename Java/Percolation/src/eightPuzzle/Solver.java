package eightPuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * find a gameTree to the initial board (using the A* algorithm)
 *
 * @author Qiang
 * @since 23/04/2017
 */
public class Solver {

    private Board board;
    private Board twinBoard;


    private List<Board> solution;

    private MinPQ<MovesBoard> priorityQueue;
    private MinPQ<MovesBoard> twinPriorityQueue;

    private MovesBoard tempBoard;
    private MovesBoard twinTempBoard;

    private boolean isSolvable = false;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }

        board = initial;
        twinBoard = board.twin();

        priorityQueue = new MinPQ<>(50, Comparator.comparingInt(MovesBoard::getPriority));
        twinPriorityQueue = new MinPQ<>(50, Comparator.comparingInt(MovesBoard::getPriority));
        computeSolution();
    }

    private class MovesBoard {
        private Board board;
        private int moves;
        private MovesBoard previousBoard;

        public MovesBoard(Board board, int moves, MovesBoard previousBoard) {
            this.board = board;
            this.moves = moves;
            this.previousBoard = previousBoard;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }
    }


    private void computeSolution() {
        tempBoard = new MovesBoard(board, 0, null);
        twinTempBoard = new MovesBoard(twinBoard, 0, null);
        priorityQueue.insert(tempBoard);
        twinPriorityQueue.insert(twinTempBoard);

        while (true) {
            if (nextStep(false)) {
                isSolvable = true;
                break;
            }

            if (nextStep(true)) {
                isSolvable = false;
                break;
            }

        }

        // remember to free the memory after compute, that is very important
        // in fact, we should use local var instead of field for this var
        priorityQueue = null;
        twinPriorityQueue = null;
        twinTempBoard = null;

        if (isSolvable()) {
            solution = new ArrayList<>(100);


            MovesBoard movesBoard = tempBoard;
            solution.add(movesBoard.board);
            while (movesBoard.previousBoard != null) {
                movesBoard = movesBoard.previousBoard;
                solution.add(movesBoard.board);
            }
            Collections.reverse(solution);
        }


    }

    /**
     * Let the given sequence of boards advance one step
     *
     * @return whether the given boards has reached the goal
     */
    private boolean nextStep(boolean isTwin) {
        MinPQ<MovesBoard> minPQ;
        MovesBoard localTempBoard;

        if (!isTwin) {
            minPQ = priorityQueue;
            tempBoard = priorityQueue.delMin();
            localTempBoard = tempBoard;
        } else {
            minPQ = twinPriorityQueue;
            twinTempBoard = twinPriorityQueue.delMin();
            localTempBoard = twinTempBoard;
        }

        MovesBoard localPreviousBoard = localTempBoard.previousBoard;
        Board minBoard = localTempBoard.board;
        for (Board oneBoard : minBoard.neighbors()) {
            if (localPreviousBoard == null || !oneBoard.equals(localPreviousBoard.board)) {
                minPQ.insert(new MovesBoard(oneBoard, localTempBoard.moves + 1, localTempBoard));
            }
        }

        return minBoard.isGoal();


    }


    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     *
     * @return
     */
    public int moves() {
        if (isSolvable()) {
            return solution.size() - 1;
        } else {
            return -1;
        }
    }

    /**
     * sequence of boards in a shortest gameTree; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (isSolvable()) {
            List<Board> result = new ArrayList<>();
            result.addAll(solution);
            return result;
        } else {
            return null;
        }

    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print gameTree to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}
