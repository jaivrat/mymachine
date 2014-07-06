public class Percolation {
   private int mN;
   private WeightedQuickUnionUF cont;
   private int mBotSentinelIdx;
   private int mTopSentinelIdx;

   
   private int[][] multi; 
   
   public Percolation(int N)              // create N-by-N grid, with all sites blocked
   {
       if (N <= 0)
           throw new java.lang.IllegalArgumentException();
       
       mN = N;
       cont                = new WeightedQuickUnionUF(mN*mN+2); //2 added to hold top and bottom sentinels
       mTopSentinelIdx     = mN*mN;
       mBotSentinelIdx     = mN*mN + 1;
       
       multi = new int[mN][mN];
   }
   
   private void checkRange(int i, int j)
   {
       if (i < 1 || i > mN)
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       
       if (j < 1 || j > mN)
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
   }
      
   public void open(int i, int j)         // open site (row i, column j) if it is not already
   {
       checkRange(i, j);
       //convention
       int row = i -1;
       int col = j -1;
       
       //already open
       if (multi[row][col] == 1)
           return;
       
       //open the site
       multi[row][col] = 1; 
       
       //do connections
       int idx = mN * row + col;
       //System.out.println("Opening linear idx=" + idx );
       
      //explore X+ (row, col+1) 
      if ((col+1 < mN) && isOpen(row+1, col+2))
           cont.union(idx, idx+1);
           
       //explore X-  //checking row,col-1
       if (col-1 > -1 && isOpen(row+1, col))
           cont.union(idx, idx-1);
           
       //explore Y+ (row+1,col)
       if (row == mN-1) 
       {  // bottom row sentinels to be connected
           cont.union(idx, mBotSentinelIdx);
       }
       
       if ((row+1 < mN) && isOpen(row+2, col+1)) //check i+1,j
       {
           cont.union(idx, idx + mN);
       }
       

        //explore Y-
       if (row == 0)
       {       // sentinels to be connected
           cont.union(idx, mTopSentinelIdx);
       }
       
       if (row-1 > -1 && isOpen(row, col+1))    //check i-1,j
           cont.union(idx, idx - mN);
       
   }
   
   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
   {
       checkRange(i, j);
       return multi[i-1][j-1] == 1;
   }
   
   public boolean isFull(int i, int j)    // is site (row i, column j) full?
   {
       checkRange(i, j);
       
       if (!isOpen(i, j))
           return false;
       
       //now the site is open
       int row = i - 1;
       int col = j - 1;
       int idx = mN * row + col;
       
       if (row == 0) //top row
       {
           return true;
       }
       
       return cont.connected(mTopSentinelIdx, idx);
   }
   
   public boolean percolates()            // does the system percolate?
   {
       return cont.connected(mTopSentinelIdx, mBotSentinelIdx);
   }
 
   //just for debug print
  public void debugPrint()
   {
       int X = multi.length;
       int Y = multi[0].length;
       System.out.println("Open report ----");
       
       for(int i=0; i< mN; ++i)
       {
         for(int j=0; j<mN ; ++j)
          {
              if(isOpen(i+1, j+1))
                   System.out.print("O - ");
               else
                   System.out.print("X - ");
           }
           System.out.println("");
       }
       
       System.out.println("**********\n");
   }
   
   //debug
   public void debugPercRep()
   {
        System.out.println("Fullness report ----");
    for(int i=0; i< mN; ++i)
    {
        for(int j=0; j<mN; ++j)
        {
            if(isFull(i+1,j+1))
                System.out.print("F - ");
            else
                System.out.print("X - ");
        }
        System.out.println("");
    }
    
   }
}