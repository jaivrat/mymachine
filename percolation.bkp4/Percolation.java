import java.util.ArrayList;

public class Percolation {
   private int mN;
   private WeightedQuickUnionUF cont;
   private WeightedQuickUnionUF backtrack;
   private int[][] multi; 
   private int mTopSentIdx;
   private int mBotSentIdx;
   
   public Percolation(int N)              // create N-by-N grid, with all sites blocked
   {
       if (N <= 0)
           throw new java.lang.IllegalArgumentException();
       
       mN          = N;
       
       //last ones to store top sentinel and bottom sentinel
       cont          = new WeightedQuickUnionUF(mN*mN + 2); 
       backtrack     = new WeightedQuickUnionUF(mN*mN + 1);
       multi         = new int[mN][mN];
       mTopSentIdx   = mN*mN;
       mBotSentIdx   = mTopSentIdx + 1;
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
      if ((col+1 < mN) && multi[row][col+1] == 1)
      {
           cont.union(idx+1,idx);
           backtrack.union(idx+1,idx);
      }
           
       //explore X-  //checking row,col-1
       if (col > 0 && multi[row][col-1] == 1)
       {
           cont.union(idx-1,idx);
           backtrack.union(idx-1,idx);
       }
           
       //explore Y+ (row+1,col) 
       if ((row+1 < mN) && multi[row+1][col] == 1) //check i+1,j
       {
           cont.union(idx + mN, idx);
           backtrack.union(idx + mN, idx);
       }
       
        //explore Y-
       if (row > 0 && multi[row-1][col] == 1)    //check i-1,j
       {
           cont.union(idx - mN, idx );
           backtrack.union(idx - mN, idx );
       }
       else if (row == 0) //speacial case for top row
       {
           //connect to topsential
           cont.union(mTopSentIdx, idx);
           backtrack.union(mTopSentIdx, idx);
       }
       
       //if last(bottom) row - it might be connected to top
       if (row == mN -1) 
       {
           //connect to bottom
           cont.union(mBotSentIdx, idx);
       }
       
   }
   
   public boolean isOpen(int i, int j)    // is site (row i, column j) open?
   {
       checkRange(i, j);
       return multi[i-1][j-1] == 1;
   }
   
//   private boolean traverseAndFind(int i,int j)
//   {
//       if (i > mN || i < 1 || j > mN || j < 1)
//           return false;
//       
//       int row = i - 1;
//       int col = j - 1;
//       
//       //if reach a closed cell return false
//       if(multi[row][col] == 0)
//           return false;
//       
//       if(row == 0)
//           return true;
//     
//       boolean nextlevel =   traverseAndFind(i-1, j) 
//                               || traverseAndFind(i+1, j) 
//                               || traverseAndFind(i, j-1)
//                               || traverseAndFind(i, j+1);
//       return nextlevel;
//   }
   
   
   public boolean isFull(int i, int j)    // is site (row i, column j) full?
   {
       checkRange(i, j);
       return (multi[i-1][j-1] == 1 && backtrack.connected(mN*(i-1)+j-1,mTopSentIdx));
       //return traverseAndFind(i, j);
   }
   
   public boolean percolates()            // does the system percolate?
   {
       return cont.connected(mTopSentIdx,mBotSentIdx);
   }
 
   //just for debug print
//  public void debugPrint()
//   {
//       int X = multi.length;
//       int Y = multi[0].length;
//       System.out.println("Open report ----");
//       
//       for(int i=0; i< mN; ++i)
//       {
//         for(int j=0; j<mN ; ++j)
//          {
//              if(isOpen(i+1, j+1))
//                   System.out.print("O - ");
//               else
//                   System.out.print("X - ");
//           }
//           System.out.println("");
//       }
//       
//       System.out.println("**********\n");
//   }
   
   //debug
//   public void debugPercRep()
//   {
//        System.out.println("Fullness report ----");
//    for(int i=0; i< mN; ++i)
//    {
//        for(int j=0; j<mN; ++j)
//        {
//            if(isFull(i+1,j+1))
//                System.out.print("F - ");
//            else
//                System.out.print("X - ");
//        }
//        System.out.println("");
//    }
//    
//  }
}