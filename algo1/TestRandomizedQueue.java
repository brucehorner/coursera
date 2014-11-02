import java.util.Iterator;


public class TestRandomizedQueue
{
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> dq = new RandomizedQueue<Integer>();
        for ( int j=1; j<=50; j++)
            dq.enqueue(j);

        print(dq);
        /*
        int sz = dq.size();
        for (int i=0; i<sz; i++)
        {
            int k = dq.dequeue();
            System.out.format ("just extracted %d%n", k);
        }
        print(dq);
        */
    }

    public static void print (RandomizedQueue<Integer> dq)
    {
        int size = dq.size();
        System.out.format("Reported size = %d%n", size);

        Iterator<Integer> it1 = dq.iterator();
        Iterator<Integer> it2 = dq.iterator();
        while (it1.hasNext())
        {
            int i1 = it1.next();
            System.out.format ("Value for it1 is %d%n", i1);
            
            if (it2.hasNext())
            {
            	int i2 = it2.next();
                System.out.format ("Value for it2 is %d%n", i2);
            }
            
        }
    }

}
