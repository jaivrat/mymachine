import java.util.ArrayList;
import java.util.Random;
import java.util.*;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private ArrayList<Item>   mDataList;
    private Random  mRandom;
    public RandomizedQueue()
    {// construct an empty randomized queue
        mDataList = new  ArrayList<Item>();
        mRandom = new Random();
    }
    
    private RandomizedQueue( RandomizedQueue obj)
    {
        if(null == obj)
            throw new java.lang.NullPointerException();
        
        //make a copy - not deepcopy avilable
        int sz = obj.mDataList.size();
        mDataList = new ArrayList<Item>(sz); //inital capcity
        for (int i=0; i < sz; ++i)
        {
            Item t = (Item)obj.mDataList.get(i);
            mDataList.add((Item)t);
        }
        mRandom = new Random();
    }
    
    public boolean isEmpty()                 // is the queue empty?
    {
        return (mDataList.size() == 0);
    }
    
    public int size()                        // return the number of items on the queue
    {
        return mDataList.size();
    }
    
    public void enqueue(Item item)           // add the item
    {
        if (item == null )
            throw new java.lang.NullPointerException();
      
        mDataList.add(item);
    }
    
    private Item sampleRemovable(boolean removeAsWell)
    {
        if (size() == 0)
            throw new java.util.NoSuchElementException();
        
        //make sure each element comes with equal probability
        Item result;
        int sz     = mDataList.size();
        int rndindex = mRandom.nextInt(sz); //0..sz-1
        result       = mDataList.get(rndindex);
        if (removeAsWell)
        {
            mDataList.set(rndindex,mDataList.get(sz-1));
            mDataList.remove(sz-1);
        }       
        return result;
    }
    
    public Item dequeue()                    // delete and return a random item
    {
        return sampleRemovable(true);
    }
    
    public Item sample()                     // return (but do not delete) a random item
    {
        return sampleRemovable(false);
    }
    
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator(this);
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> 
    {
        RandomizedQueue<Item> mQCopy;
        
        public boolean hasNext() 
        {  
            return (!mQCopy.isEmpty());  
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
            Item t = mQCopy.dequeue();
            return t;
        }
        
        public RandomizedQueueIterator(RandomizedQueue<Item> data)
        {
            if (data.size() == 0)
                mQCopy = new RandomizedQueue<Item>();
            else
                mQCopy = new RandomizedQueue<Item>(data);
        }
        
    }
    
    
    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<Integer> mydeque = new RandomizedQueue<Integer>();
        
        
        for (int i=0; i<10; ++i)
        {
            mydeque.enqueue(new Integer(i));
        }
        
        
        Iterator<Integer> liter = mydeque.iterator();
        while (liter.hasNext())
        {
            Integer t = liter.next();
            System.out.println(" item = " + t);
        }
        
        mydeque.enqueue(new Integer(200));
        mydeque.enqueue(new Integer(100));
        
        System.out.println("-----------------------");
        Iterator<Integer> iter2 = mydeque.iterator();
        while (iter2.hasNext())
        {
            Integer t = iter2.next();
            System.out.println(" item = " + t);
        }
        
        while (mydeque.size()>0)
        {
            System.out.println("-------Rem First------ size=" + mydeque.size() );
            
            Integer x =  mydeque.dequeue();
            System.out.println("-------Removed " + x);
            
            Iterator<Integer> it = mydeque.iterator();
            System.out.println("-------Remaining ----");
            while (it.hasNext())
            {
                Integer t = it.next();
                System.out.println(" item = " + t);
            }
        }
        
        
        RandomizedQueue<Integer> mydeque1 = new RandomizedQueue<Integer>();
        mydeque1.enqueue(1);
        Integer x = mydeque1.dequeue();
        System.out.println("Got x= " + x + "  size=" + mydeque1.size());
        mydeque1.enqueue(2);
        x = mydeque1.dequeue();
        System.out.println("Got x= " + x + "  size=" + mydeque1.size());
    }
}