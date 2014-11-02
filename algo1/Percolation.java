public class Percolation
{
    private boolean[] data;
    private int dataLength; 
    private int sideLength;    
    private WeightedQuickUnionUF unionFinder;
    private WeightedQuickUnionUF unionFinderNoBottom;		// lot of work to avoid backwash
    private int theTOPoffset;
    private int theBOTTOMoffset;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        sideLength = N;
        dataLength = N*N;
        data = new boolean[dataLength];
        unionFinder = new WeightedQuickUnionUF(dataLength + 2);        	// add two for the virtual top/bottom entries
        unionFinderNoBottom = new WeightedQuickUnionUF(dataLength + 1); // add one for the virtual top entry
        theTOPoffset = dataLength;
        theBOTTOMoffset = dataLength + 1;
    }
    
    private int offset(int i, int j)
    {
        return (i-1)*sideLength + (j-1);
    }
    
    private void validate(int i, int j)
    {
        if (i < 1 || i > sideLength || j < 1 || j > sideLength) throw new IndexOutOfBoundsException();    	
    }
    
    private void unionBoth (int i, int j)
    {
        unionFinder.union(i, j);
        unionFinderNoBottom.union(i, j);
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        validate(i, j);
        
        int thisOffset = offset(i, j);
        if (data[thisOffset] == false)
        {
            data[thisOffset] = true;
                        
            // join to any open neighbours
            if (i > 1)            	// ABOVE
                if (isOpen(i-1, j))
                    unionBoth(thisOffset, offset(i-1, j));

            if (i < sideLength)    	// BELOW
                if (isOpen(i+1, j))
                    unionBoth(thisOffset, offset(i+1, j));

            if (j > 1)            	// LEFT
                if (isOpen(i, j-1))
                    unionBoth(thisOffset, offset(i, j-1));
            
            if (j < sideLength)    	// RIGHT
                if (isOpen(i, j+1))
                    unionBoth(thisOffset, offset(i, j+1));
            
            if (i == 1)
                unionBoth(offset(i, j), theTOPoffset);                  // join top row to TOP
            
            if (i == sideLength)
                unionFinder.union(offset(i, j), theBOTTOMoffset);       // join bottom row to BOTTOM 
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        validate(i, j);
        return data[offset(i, j)];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        //return isOpen(i, j) && unionFinder.connected(offset(i, j), theTOPoffset);
        return isOpen(i, j) && unionFinderNoBottom.connected(offset(i, j), theTOPoffset);
    }
    
    // does the system percolate?
    public boolean percolates()
    {
        return unionFinder.connected(theTOPoffset, theBOTTOMoffset);
    }
    
    private void print(String s)
    {
        System.out.format("%s%n", s);
        for (int row = 1; row <= sideLength; row++)
        {
            for (int col = 1; col <= sideLength; col++)
                System.out.format(" %c%s", data[offset(row, col)] ? 'O' : '.', (isFull(row, col) ? "*" : " "));
            System.out.format("%n");
        }        
    }
    
    public static void main(String[] args)
    {
        Percolation p = new Percolation(7);        
        //p.print("Initialized:");
        System.out.format("Percolates = %s%n", p.percolates());
        p.open(3, 2);
        p.open(5, 4);
        p.print("With 3,2 and 5,4 open:");
        
        p.open(4, 2);
        p.open(5, 3);
        p.open(4, 3);
        p.open(1, 1);
        p.open(3, 5);
        p.open(2, 7);
        p.open(3, 7);
        p.open(4, 7);
        p.print("With more cells open:");
        System.out.format("Percolates = %s%n", p.percolates());

        p.open(1, 4);        
        p.open(2, 4);
        p.open(7, 2);
        p.open(7, 4);
        p.open(7, 6);
        p.open(7, 7);
        p.print("With more cells open:");
        System.out.format("Percolates = %s%n", p.percolates());

        p.open(2, 2);
        p.open(2, 3);
        p.open(6, 4);
        p.print("Should be done now:");
        System.out.format("Percolates = %s%n", p.percolates());
        
    }
}

