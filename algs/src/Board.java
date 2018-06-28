import java.util.ArrayList;
import java.util.Arrays;


public class Board {

    private final int[][] innerBlocks;
    private int hamming;
    private int manhattan;
    private int dimension;
    private int[] zeroLocation = new int[2];

    public Board(int[][] blocks){
        innerBlocks = Arrays.copyOf(blocks,blocks.length);
        dimension = blocks.length;
        hamming = calcHamming();
        manhattan = calcManhattan();
    }

    private Board(int[][] copy, int dimension, int[] newZeroLocation, int h, int m) {
        innerBlocks = copy;
        this.dimension = dimension;
        this.zeroLocation = newZeroLocation;
        this.hamming = h;
        this.manhattan = m;
    }

    private int calcManhattan() {
        int manhattan = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int rightLocation = getRightLocation(i,j);
                if (innerBlocks[i][j] != 0 && innerBlocks[i][j] != rightLocation)
                    manhattan=manhattan+getDistance(i,j,innerBlocks[i][j]);
            }
        }

        return manhattan;
    }

    private int getDistance(int x, int y, int k) {
        int realx = (k/dimension) - 1;
        int realy = dimension - (k%dimension) - 1;
        return Math.abs(realx-x)+Math.abs(realy-y);
    }

    private int calcHamming() {
        int hamming = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(0 == innerBlocks[i][j] )
                {
                    zeroLocation[0] = i;
                    zeroLocation[1] = j;
                }
                int rightLocation = getRightLocation(i,j);
                if (innerBlocks[i][j] != 0 && innerBlocks[i][j] != rightLocation)
                    hamming++;
            }
        }

        return hamming;
    }

    private int getRightLocation(int i, int j) {
        return (i * dimension + j) + 1;
    }

    public int dimension(){
        return dimension;
    }
    public int hamming(){
        return hamming;
    }
    public int manhattan(){
        return manhattan;
    }
    public boolean isGoal(){
        return hamming==0;
    }

    public Board twin(){
        int[][] newArray = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newArray[i][j] = innerBlocks[i][j];
            }
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension-1; j++) {
                if(newArray[i][j] != 0 && newArray[i][j+1] != 0){
                    return getTwin(newArray, i, j);
                }
            }
        }
        return null;
    }

    private Board getTwin(int[][] copy, int i, int j) {
        int temp = copy[i][j];
        copy[i][j] = copy[i][j+1];
        copy[i][j+1] = temp;

        int h = hamming;
        int m = manhattan;

        m += (calCopyManhattan(copy, i, j+1) - calCopyManhattan(innerBlocks, i, j)) + (calCopyManhattan(copy, i, j) - calCopyManhattan(innerBlocks, i, j+1));
        h += (calCopyHamming(copy, i, j+1) - calCopyHamming(innerBlocks, i, j)) + (calCopyHamming(copy, i, j) - calCopyHamming(innerBlocks, i, j+1));

        return new Board(copy);
        //return new Board(copy, dimension, zeroLocation, h, m);
    }

    public boolean equals(Object y){
        if (null == y || this.getClass() != y.getClass()) {
            return false;
        }
        return this.toString().equals(y.toString());
    }
    public Iterable<Board> neighbors(){
        ArrayList<Board> neighbours = new ArrayList<>();
        if(zeroLocation[0] > 0){
            neighbours.add(neighbour(zeroLocation[0]-1, zeroLocation[1]));
        }
        if(zeroLocation[0] < (dimension-1)){
            neighbours.add(neighbour(zeroLocation[0]+1, zeroLocation[1]));
        }
        if(zeroLocation[1] > 0){
            neighbours.add(neighbour(zeroLocation[0], zeroLocation[1]-1));
        }
        if(zeroLocation[1] < (dimension-1)){
            neighbours.add(neighbour(zeroLocation[0], zeroLocation[1]+1));
        }
        return neighbours;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension).append(" \n ");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%d ", (int) innerBlocks[i][j]));
            }
            s.append("\n ");
        }
        return s.toString();
    }

    private Board neighbour(int i, int j) {
        int[][] copy = new int[dimension][dimension];

        for (int ii = 0; ii < dimension; ii++) {
            for (int jj = 0; jj < dimension; jj++) {
                copy[ii][jj] = innerBlocks[ii][jj];
            }
        }

        exch(copy, zeroLocation, i, j);
        int h = hamming;
        int m = manhattan;
        h -= calCopyHamming(copy, zeroLocation[0], zeroLocation[1]) - calCopyHamming(copy, i, j);
        m -= calCopyManhattan(copy, zeroLocation[0], zeroLocation[1]) - calCopyManhattan(copy, i, j);
        int[] newZeroLocation = new int[2];
        newZeroLocation[0] = i;
        newZeroLocation[1] = j;
        return new Board(copy);
        //return new Board(copy, dimension, newZeroLocation, h, m);
    }

    private int calCopyManhattan(int[][] copy, int i, int j) {
        int rightLocation = getRightLocation(i,j);
        if (copy[i][j] != 0 && copy[i][j] != rightLocation){
            return getDistance(i,j,copy[i][j]);
        }
        return 0;
    }

    private int calCopyHamming(int[][] copy, int i, int j) {
        int rightLocation = getRightLocation(i,j);
        if (copy[i][j] != 0 && copy[i][j] != rightLocation)
            return 1;
        return 0;
    }

    private void exch(int[][] copy, int[] zeroLocation, int i, int j) {
        int temp = copy[zeroLocation[0]][zeroLocation[1]];
        copy[zeroLocation[0]][zeroLocation[1]] = copy[i][j];
        copy[i][j] = temp;
    }

    public static void main(String[] args){

    }
}
