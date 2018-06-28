package percolations;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int[] openCells;
    private final int gridNumber;
    private int openCount = 0;
    private final WeightedQuickUnionUF allCells;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if(n <= 0){
            throw new IllegalArgumentException("N must be greater than 0");
        }

        allCells = new WeightedQuickUnionUF(n*n+2);
        gridNumber = n;
        openCells = new int[n*n+1];

        connectBottomCells(n);
        connectTopCells(n);
    }

    public void open(int row, int col){       // open site (row, col) if it is not open already
        if(row < 1 || col < 1 || row > gridNumber || col > gridNumber){
            throw new IllegalArgumentException("invalid location");
        }
        if(openCells[getPointInGrig(row, col)] != 1) {
            openCells[getPointInGrig(row, col)] = 1;
            openCount++;
            connectToAdjacentOpenCells(row, col);
        }
    }

    private int getPointInGrig(int row, int col) {
        return (gridNumber * row) - (gridNumber - col);
    }

    public boolean isOpen(int row, int col){  // is site (row, col) open?
        if(row < 1 || col < 1 || row > gridNumber || col > gridNumber){
            throw new IllegalArgumentException("invalid location");
        }

        return openCells[getPointInGrig(row, col)] == 1;
    }
    public boolean isFull(int row, int col) {  // is site (row, col) full?
        if(row < 1 || col < 1 || row > gridNumber || col > gridNumber){
            throw new IllegalArgumentException("invalid location");
        }

        return isOpen(row, col) && allCells.connected(0, getPointInGrig(row, col));
    }
    public int numberOfOpenSites(){      // number of open sites
        return openCount;
    }
    public boolean percolates(){             // does the system percolate?
        return allCells.connected(0, (gridNumber*gridNumber+1));
    }

    private void connectTopCells(int n) {
        int startingPoint  = 0;
        for (int i = 1; i<=n; i++){
            allCells.union(0, startingPoint+i);
        }
    }

    private void connectBottomCells(int n) {
        int startingPoint  = n*(n-1);
        for (int i = 1; i<=n; i++){
            allCells.union((n*n)+1, startingPoint+i);
        }
    }

    private void connectToAdjacentOpenCells(int row, int col) {
        int openedCell = getPointInGrig(row, col);
        int right = col >= gridNumber ? 0 : getPointInGrig(row, col) + 1;
        int left = col <= 1 ? 0 :  getPointInGrig(row, col) - 1;
        int top = row <= 1 ? 0 : getPointInGrig(row-1, col);
        int bottom = row >= gridNumber ? 0 : getPointInGrig(row+1, col);

        if(positionIsValid(bottom)){
            allCells.union(openedCell, bottom);
        }

        if(positionIsValid(top)){
            allCells.union(openedCell, top);
        }

        if(positionIsValid(left)){
            allCells.union(openedCell, left);
        }

        if(positionIsValid(right)){
            allCells.union(openedCell, right);
        }
    }

    private boolean positionIsValid(int cell) {
        return cell <= gridNumber*gridNumber && cell > 0 && openCells[cell] == 1;
    }

    public static void main(String[] args) {   // test client (optional)
//        Scanner fileIn = new Scanner(new File("resources/input10.txt"));
//
//        int n = Integer.parseInt(fileIn.next());
//        percolations.Percolation perco = new percolations.Percolation(n);
//        while (fileIn.hasNext()) {
//            int row = Integer.parseInt(fileIn.next());
//            int col = Integer.parseInt(fileIn.next());
//            perco.open(row, col);
//        }
//
//        System.out.println("Number of open sites is " + perco.numberOfOpenSites());
//        System.out.println("Percolates is " + perco.percolates());
    }
}