import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @Author: xiaojiang
 * @DATE: Created in 8:52 2020/9/30
 */
public class Percolation
{
    private final int dimension;
    private final boolean[] grids;
    private int openSites = 0;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        //Throw an IllegalArgumentException in the constructor if n ≤ 0.
        if (n <= 0) throw new IllegalArgumentException("error range");

        dimension = n;
        uf = new WeightedQuickUnionUF(dimension*dimension + 2);
        grids = new boolean[dimension*dimension + 1]; // array elements are false

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 || row > dimension || col < 1 || col > dimension)
            throw new IllegalArgumentException("error range");

        if (isOpen(row, col)) return;
        else
        {
            grids[(row - 1) * dimension + col] = true;
            openSites++;
        }
        // up
        if (row - 1 > 0)
        {
            if(isOpen(row - 1, col)) uf.union((row - 1)*dimension + col, (row -2 )*dimension + col);
        }
        else uf.union((row -1)*dimension + col, 0);
        // down
        if (row + 1 <= dimension)
        {
            if (isOpen(row + 1, col)) uf.union((row - 1) * dimension + col, row * dimension + col);
        }
        else uf.union((row -1)*dimension + col, dimension * dimension + 1);
        // left
        if (col - 1 > 0)
        {
            if (isOpen(row, col - 1)) uf.union((row - 1)*dimension + col, (row - 1)*dimension + col - 1);
        }
        // right
        if (col + 1 <= dimension)
        {
            if (isOpen(row, col + 1)) uf.union((row - 1)*dimension + col, (row - 1)*dimension + col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 1 || row > dimension || col < 1 || col > dimension)
            throw new IllegalArgumentException("error range");

        return grids[(row - 1) * dimension + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 1 || row > dimension || col < 1 || col > dimension)
            throw new IllegalArgumentException("error range");

        if (uf.find((row-1)*dimension + col) == uf.find(0)) return true;
        else return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    { return openSites; }

    // does the system percolate?
    public boolean percolates()
    { return uf.find(0) == uf.find(dimension*dimension + 1); }
}

