public class PercolationStats
{    
    private double data[];
    
    /**
     * Also, include a main() method that takes two command-line arguments N and T, performs T independent
     * computational experiments on an N-by-N grid, and prints out the mean, standard
     * deviation, and the 95% confidence interval for the percolation threshold. Use standard random from
     * our standard libraries to generate random numbers; use standard statistics to compute the sample
     * mean and standard deviation.
     */
    public static void main(String[] args)
    {
        if (args.length == 2)
        {            
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            
            PercolationStats stats = new PercolationStats(N,T);
            System.out.format ("mean                    = %f%n", stats.mean());
            System.out.format ("stddev                  = %f%n", stats.stddev());
            System.out.format ("95%% confidence interval = %f, %f%n", stats.confidenceLo(), stats.confidenceHi());
        }
        else
            System.out.println ("Please provide two arguments for number of iterations and size of grid");

    }

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        
        data = new double[T];
        for (int outer_loop=0; outer_loop<T; outer_loop++)
        {
            Percolation p = new Percolation(N);
            int counter = 0;
            while (!p.percolates())
            {
                int row =  StdRandom.uniform(1, N+1);
                int col = StdRandom.uniform(1, N+1);
                if (!p.isOpen(row, col))
                {
                    p.open(row, col);
                    counter++;
                }
            }
            
            //System.out.format("Percolated with counter = %d for N = %d%n", counter, N);
            data[outer_loop] = (double)counter / (N*N);
        }
        
    }
    
    
    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(data);
    }                    
    
    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(data);
    }    
    
    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {        
        int T = data.length; 
        double lo = mean() - ((1.96 * stddev()) / Math.sqrt(T));
        return lo;
    }
    
    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        int T = data.length; 
        double lo = mean() + ((1.96 * stddev()) / Math.sqrt(T));
        return lo;
    }             

}

