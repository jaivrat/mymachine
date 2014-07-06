import java.util.ArrayList;
import java.util.Random;
import java.util.*;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private ArrayList<ArrayList<Item>>   mDataList;
    
    private int mN;
    private Random  mRandom;
    public RandomizedQueue()
    {// construct an empty randomized queue
        mDataList = new  ArrayList<ArrayList<Item>>();
        mRandom = new Random();
        mN = 16;
    }
    
    private RandomizedQueue( RandomizedQueue obj)
    {
        if(null == obj)
            throw new java.lang.NullPointerException();
        
        //make a copy - not deepcopy avilable
        int outsz = obj.mDataList.size();
        mDataList = new ArrayList<ArrayList<Item>>(outsz); //inital capcity
        for (int i=0; i < outsz; ++i)
        {
            ArrayList<Item> srcbucket = (ArrayList<Item>) obj.mDataList.get(i);
            int insz                  = srcbucket.size();
            ArrayList<Item> newbucket = new ArrayList<Item>(insz);
            
            for (int j = 0; j < insz; ++j)
            {
                Item t = srcbucket.get(j);
                newbucket.add((Item)t);
            }
            
            mDataList.add(newbucket);
        }
        
        mRandom = new Random();
        mN = 16;
    }
    
    public boolean isEmpty()                 // is the queue empty?
    {
        return (mDataList.size() == 0);
    }
    
    private int lastElemSize()
    {
        if (mDataList.size() == 0)
            return (0);
        else
            return (mDataList.get(mDataList.size() - 1).size());
    }
    
    public int size()                        // return the number of items on the queue
    {
        int numFull = Math.max(0, mDataList.size() - 1) ;
        return Math.max(0, numFull * mN + lastElemSize());
    }
    
    public void enqueue(Item item)           // add the item
    {
        if (item == null )
            throw new java.lang.NullPointerException();
        
        if (isEmpty())
        {
            ArrayList<Item> newElemList = new ArrayList<Item>();
            newElemList.add(item);
            mDataList.add(newElemList);
            return;
        }
        
        //not empty
        //1. Get last element
        ArrayList<Item> lastEl = mDataList.get(mDataList.size()-1);
        if (lastEl.size() == mN) //full
        {
            ArrayList<Item> newElemList = new ArrayList<Item>();
            newElemList.add(item);
            mDataList.add(newElemList);
        }
        else
        {
            lastEl.add(item);
        }
    }
    
    private Item sampleRemovable(boolean removeAsWell)
    {
        if (size() == 0)
            throw new java.util.NoSuchElementException();
        
        //make sure each element comes with equal probability
        Item result;
        int k      =  mDataList.size();
        int sz     = lastElemSize();
        double p1  = sz/(sz + mN*(k-1));
        double rnd = mRandom.nextDouble();
        if (rnd <= p1)
        {
            //deque from last bucket
            ArrayList<Item> lastList = mDataList.get(k-1);
            int rndindex = mRandom.nextInt(sz); //0..sz-1
            result       = lastList.get(rndindex);
            if (removeAsWell)
            {
                lastList.remove(rndindex);
                if (lastList.isEmpty())
                    mDataList.remove(k-1);
            }
        }
        else
        {
            //remove from the bucket (0..second last)
            int rndBucketindex         = mRandom.nextInt(k-1); //0..k-2
            ArrayList<Item>  rndBucket = mDataList.get(rndBucketindex);
           
           // System.out.println("Expected = " + mN + "  Got=" + rndBucket.size());
            int rndindex = mRandom.nextInt(rndBucket.size()); //must be N only
           
            result       = rndBucket.get(rndindex);
            
            if (removeAsWell)
            {
                //fill in th the vacant place by last element in last bucket
                rndBucket.set(rndindex,mDataList.get(k-1).get(sz-1));
                mDataList.get(k-1).remove(sz-1);
                
                //clear last bucket if empty
                if (mDataList.get(k-1).isEmpty())
                    mDataList.remove(k-1);
            }
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