import java.util.Iterator;

public class Subset {
   public static void main(String[] args)
   {
       if (args.length < 1)
       {
           throw new java.lang.IllegalArgumentException();
       }
       
       int subsetSz = Integer.parseInt(args[0]);
           
      // System.out.println("Got i= " + subsetSz );
       String s;
       RandomizedQueue<String> mydeque = new RandomizedQueue<String>();
       while (!StdIn.isEmpty())
       {
           s =  StdIn.readString();
           //System.out.println("String input >" + s + "<");
           mydeque.enqueue(new String(s));
       }
       
       int i=1;
       Iterator<String> liter = mydeque.iterator();
       while (liter.hasNext() && (i++ <= subsetSz) )
       {
           System.out.println(liter.next());
       }
       
       
   }
}