import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelloWorld {
    public static void main (String[] args) throws FileNotFoundException {
        Scanner fileIn = new Scanner(new File("resources/puzzle2x2-05.txt"));
        int n = Integer.parseInt(fileIn.next());

        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = fileIn.nextInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
