import java.util.Iterator;
import java.lang.Integer;

public class Deque<Item> implements Iterable<Item> {
    
    private Node mFirst, mLast;
    
    private class Node
    {
        Item item;
        Node next;
        Node prev;
        public Node(Item pitem)
        {
            item = pitem;
        }
    }
    
    private int msize;
    
    public Deque()                           // construct an empty deque
    {
        msize = 0;
    }
    
    public boolean isEmpty()                 // is the deque empty?
    {
        return msize == 0;
    }
    
    public int size()                        // return the number of items on the deque
    {
        return msize;
    }
    
    public void addFirst(Item item)          // insert the item at the front
    {
        if (item == null )
            throw new java.lang.NullPointerException();
        
        Node p = new Node(item);
        
        if (msize == 0)
        {
            mFirst = p;
            mLast = p;
        }
        else
        {
            p.next = mFirst; 
            mFirst.prev = p;
            mFirst = p;
        }
        
        ++msize;
    }
    
    
    public void addLast(Item item)           // insert the item at the end
    {
        if (item == null )
            throw new java.lang.NullPointerException();
        
        Node newNode = new Node(item);
        if (msize > 0)
        {
            newNode.prev = mLast;
            mLast.next = newNode;
            mLast = newNode;
            ++msize;
            return;
        }
        
        //first node;
        mLast = newNode;
        mFirst = newNode;
        ++msize;
    }
    
    public Item removeFirst()                // delete and return the item at the front
    {
        if (msize == 0)
            throw new java.util.NoSuchElementException();
        
        Item result = mFirst.item;
        if (msize == 1)
        {
            mFirst = null;
            mLast  = null;
        } 
        else 
        {
            mFirst.next.prev = null;
            mFirst = mFirst.next;
        }
        
        --msize;
        return result;
    }
    
    
    public Item removeLast()                 // delete and return the item at the end
    {
        if (msize == 0)
            throw new java.util.NoSuchElementException();
        
        Item result = mLast.item;
        if (msize == 1)
        {
            mFirst = null;
            mLast  = null;
        }
        else
        {
            mLast.prev.next = null;
            mLast = mLast.prev;
        }
        
        --msize;
        return result;
        
    }
    
    
    private class DequeIterator implements Iterator<Item> 
    {
        private Node current;
        public boolean hasNext() 
        {  
            return current != null;  
        }         
        
        public void remove()
        {  
            /* not supported */   
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next()
        {
            if (!hasNext())
            {
                throw new java.util.NoSuchElementException();
            }
            
            Item litem = current.item;
            current = current.next;
            return litem;
        }
        
        public DequeIterator(Node first)
        {
            current = first;
        }
        
    }
    
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator(mFirst);
    }
    
    
    public static void main(String[] args)   // unit testing
    {
        Deque<Integer> mydeque = new Deque<Integer>();
        
        for (int i=0; i<10; ++i)
        {
            mydeque.addLast(new Integer(i));
        }
        
        
        Iterator<Integer> liter = mydeque.iterator();
        while (liter.hasNext())
        {
            Integer t = liter.next();
            System.out.println(" item = " + t);
        }
        
        mydeque.addLast(new Integer(200));
        mydeque.addFirst(new Integer(100));
        
        System.out.println("-----------------------");
        Iterator<Integer> iter2 = mydeque.iterator();
        while (iter2.hasNext())
        {
            Integer t = iter2.next();
            System.out.println(" item = " + t);
        }
        
        while (mydeque.size() > 0)
        {
            System.out.println("-------Rem First------ size=" + mydeque.size() );
            
            Integer x =  mydeque.removeFirst();
            System.out.println("-------Removed " + x);
            
            Iterator<Integer> it = mydeque.iterator();
            System.out.println("-------Remaining ----");
            while(it.hasNext())
            {
                Integer t = it.next();
                System.out.println(" item = " + t);
            }
        }
        
        
        Deque<Integer> mydeque1 = new Deque<Integer>();
        mydeque1.addFirst(1);
        Integer x = mydeque1.removeFirst();
        System.out.println("Got x= " + x + "  size=" + mydeque1.size());
        mydeque1.addLast(2);
        x = mydeque1.removeFirst();
        System.out.println("Got x= " + x + "  size=" + mydeque1.size());
        
    }
}
