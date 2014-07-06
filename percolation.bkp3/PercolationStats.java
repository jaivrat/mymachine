import java.util.Random;

public class PercolationStats
{  
   private double [] results;

   //to avoid repeated calculations
   private boolean meanflag;
   private boolean stdevflag;
   private double  mmean;
   private double  mstddev;
      
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   {
       if (N <= 0 || T <= 0)
           throw new java.lang.IllegalArgumentException();
       
       results = new double[T];      
       meanflag = false;
       stdevflag = false;
       

       Random rand = new Random(1);
       
       //Do experiment
       for (int exptIdx = 0; exptIdx < T; ++exptIdx)
       {
           //for each experiment    
           int mNumOpen = 0;
           int gridSize = N;
           Percolation perc = new Percolation(N); //grid which holds
        
           int i = 0;
           int j = 0;
        
           //Till it starts percolating
           while (!perc.percolates()) 
           {   
                //generate a random (i,j) till one found with closed (To open)
               do 
               {
                    i = rand.nextInt(N);
                    j = rand.nextInt(N);
               } while (perc.isOpen(i+1, j+1));
               
               //Found a closed i,j - Open it
               perc.open(i+1, j+1);
               ++mNumOpen;
               
               perc.debugPrint();
               perc.debugPercRep();
           }
           
           results[exptIdx] = (double) mNumOpen/(N*N);
       }
   }   
   
   public double mean()                     // sample mean of percolation threshold
   {
       double avg = 0.0;
       int sz = results.length;
       for (int i = 0; i < sz; ++i)
       {
           avg = avg*(i/(i+1.0)) + results[i]/(i+1.0);
           //System.out.println("for mean i:=" + i + " avg=" + avg);
       }
       
       mmean = avg;
       meanflag = true;
       return mmean;
   }
   
   public double stddev()                   // sample standard deviation of percolation threshold
   {
       double mu;
       double var = 0.0;
       
       int sz = results.length;
       if (sz == 1)
           return 0.0;
       
       if (meanflag)
           mu = mmean;
       else
           mu = mean();
       

       for (int i = 0; i < sz; ++i)
       {
           var = var*(i/(i+1.0)) + java.lang.Math.pow(results[i]-mu, 2.0)/(i+1.0); 
       }
       
       var = var * (sz/(sz - 1.0));
       
       mstddev = java.lang.Math.pow(var, 0.5);
       stdevflag = true;
       
       return mstddev;
   }
   
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   {
       double sigma;
       if (stdevflag)
           sigma = mstddev;
       else
           sigma = stddev();
       
       double mu;
       if (meanflag)
           mu = mmean;
       else
           mu = mean();
       
       return mu - 1.96 * sigma * java.lang.Math.pow((double) results.length, -0.5);
   }
   
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   {
       double sigma;
       if (stdevflag)
           sigma = mstddev;
       else
           sigma = stddev();
       
       double mu;
       if (meanflag)
           mu = mmean;
       else
           mu = mean();
       
       return mu + 1.96 * sigma * java.lang.Math.pow(results.length, -0.5);
   }
   
//   public void printRes()
//   {
//       for(int i=0; i< results.length; ++i)
//           System.out.print("," + results[i] + " ");
//       System.out.println("\n");
//       System.out.println("Total # = " + results.length ); 
//   }
            
public static void main(String[] args) { 
    //takes two command-line arguments N and T
    if (args.length < 2)
    {
        System.out.println("Two arguments expected <GridSize> <NumberOfSimulations>");
        System.exit(-1);
    }
    
    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    
    PercolationStats percStats = new PercolationStats(N, T);
    //percStats.printRes();
    System.out.println("mean                    = " + percStats.mean());
    System.out.println("stddev                  = " + percStats.stddev());
    System.out.println("95% confidence interval = " + percStats.confidenceLo() +", "+ percStats.confidenceHi());
}
}