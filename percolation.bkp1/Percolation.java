public class Percolation {
   private int mgridSZ;
   private WeightedQuickUnionUF cont;
   private int mBottomSentinelIdx;
   private int mTopSentinelIdx;

   //just for performance
   private int lastIdx;
   
   private int[][] multi; 
   
   public Percolation(int N)              // create N-by-N grid, with all sites blocked
   {
       if (N<=0)
           throw new java.lang.IllegalArgumentException();
       
       mgridSZ = N;
       cont                = new WeightedQuickUnionUF(mgridSZ*mgridSZ+2); //2 added to hold top and bottom sentinels
       mBottomSentinelIdx  = mgridSZ*mgridSZ;
       mTopSentinelIdx     = mBottomSentinelIdx + 1;
       lastIdx             = mgridSZ -1; //0..N-1
       
       multi = new int[N][N];
   }
   
   private void checkRange(int i, int j)
   {
       if (i < 1 || i > mgridSZ)
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       
       if (j < 1 || j > mgridSZ)
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
       int idx = mgridSZ * row + col;
       //System.out.println("Opening linear idx=" + idx );
       
      //explore X+
      if (col+1 <= lastIdx && isOpen(row+1, col+2)) // checking i, j+1
           cont.union(idx, idx+1);
           
       //explore X-
       if (col-1 >= 0 && isOpen(row+1, col)) //checking i,j-1
           cont.union(idx, idx-1);
           
       //explore Y+
       if (row+1 <= lastIdx && isOpen(row+2, col+1)) //check i+1,j
       {
           cont.union(idx, idx + mgridSZ);
       }
       else if (row == lastIdx)
       {       // sentinels to be connected
           cont.union(idx, mTopSentinelIdx);
       }
       
        //explore Y-
       if ( row-1 >= 0 && isOpen(row,col+1))    //check i-1,j
           cont.union(idx, idx - mgridSZ);
       else if (row == 0)
       {       // sentinels to be connected
           cont.union(idx, mBottomSentinelIdx);
       }
   }
   
   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
   {
       checkRange(i,j);
       return multi[i-1][j-1] == 1;
   }
   
   public boolean isFull(int i, int j)    // is site (row i, column j) full?
   {
       checkRange(i,j);
       if(isOpen(i,j) == false)
           return false;
       
       //now the site is open
       int row = i - 1;
       int col = j - 1;
       
       if(row == lastIdx) //top row
           return true;
       
       int idx = mgridSZ * row + col;
       return cont.connected(mTopSentinelIdx,idx);
   }
   
   public boolean percolates()            // does the system percolate?
   {
       return cont.connected(mTopSentinelIdx,mBottomSentinelIdx);
   }
 
   //just for debug print
  public void debugPrint()
   {
       int X = multi.length;
       int Y = multi[0].length;
       
       for(int i=0; i< X; ++i)
       {
         for(int j=0; j<Y ; ++j)
          {
              if(isOpen(i+1,j+1))
                   System.out.print("O - ");
               else
                   System.out.print("X - ");
           }
           System.out.println("\n");
       }
       
       System.out.println("**********\n");
   }
}