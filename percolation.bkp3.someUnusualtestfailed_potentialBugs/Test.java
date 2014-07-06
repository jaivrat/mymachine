import java.util.Random;

public class Test
{
   public static void main(String[] args) { 
   Random rand = new Random(1);
   for(int i=0; i <100; ++i)
   {
       int r = rand.nextInt(3);
       System.out.print("r=" + r + "   ");
   }
 }
}