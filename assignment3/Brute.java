import java.io.*;
import java.util.StringTokenizer;


public class Brute
{
    
    
    public static void main(String[] args)
    {
        System.out.println("NUm pf args = " + args.length);
        if(args.length != 1)
        {
            throw new java.lang.IllegalArgumentException();
        }
        
        try
        {
            //Create object of FileReader
            String filename = args[0];
            
            FileReader inputFile = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String line;
            line = bufferReader.readLine();
            int inputsize = Integer.parseInt(line);
            
            System.out.println("input size = " + inputsize);
            Point[] points = new Point[inputsize];
            
            int i = 0;
            StringTokenizer st;
            while ((line = bufferReader.readLine()) != null && i < inputsize)
            {
                System.out.println(line);
                st = new StringTokenizer(line," ");
                
                while(st.hasMoreTokens()){
                    int x = Integer.parseInt(st.nextToken());
                    int y = Integer.parseInt(st.nextToken());
                    points[i++] = new Point(x,y);
                }
            }
            
            for(int j=0; j<points.length; ++j)
            {
                System.out.println("point ->" + points[j]);
            }
            
            for (int i1 = 0 ; i1 < points.length; ++i1)
                for (int i2 = 0 ; i2 <  points.length; ++i2)
                for (int i3 = 0 ; i3 <  points.length; ++i3)
                for (int i4 = 0 ; i4 <  points.length; ++i4)
            {
                Point ref = points[i1];
                
                //System.out.println("Comparing: " + points[i1] + points[i2] + points[i3] + points[i4]);
//                 System.out.println("Slope 1--2 " + ref.slopeTo(points[i2]));
//                System.out.println("Slope 1--3 " + ref.slopeTo(points[i3]));
//                System.out.println("Slope 1--4 " + ref.slopeTo(points[i4]));

                if(  
                   ref.slopeTo(points[i2]) == ref.slopeTo(points[i3])  
                    && ref.slopeTo(points[i2]) != Double.NEGATIVE_INFINITY
                       && ref.slopeTo(points[i3]) != Double.NEGATIVE_INFINITY
                       && points[i2].slopeTo(points[i3]) != Double.NEGATIVE_INFINITY
                       && points[i3].slopeTo(points[i4]) != Double.NEGATIVE_INFINITY
                   && 
                   ref.slopeTo(points[i2]) == ref.slopeTo(points[i4]) )
                {
                    Point[ ] collinear = new Point[4];
                    int idx = 0;
                    collinear[idx++] = points[i1];
                    collinear[idx++] = points[i2];
                    collinear[idx++] = points[i3];
                    collinear[idx++] = points[i4];
                    System.out.println("-----");
                    java.util.Arrays.sort(collinear, collinear[0].SLOPE_ORDER);
                    for (int j=0; j < collinear.length; ++j)
                    {
                        if (j==0)
                            System.out.print(collinear[j]);
                        else    
                            System.out.print(" -> " + collinear[j]);
                    }
                    
                    System.out.println("");
                    
                }
                
            }
            
            bufferReader.close();
            
            
            
        }
        catch(Exception e){
            System.err.println("Error while reading file line by line:" 
                                   + e.getMessage());   
        }
    }
    
}