import java.util.Iterator;


public class TestDeque
{
    public static void main(String[] args)
    {
        Deque<Integer> dq = new Deque<Integer>();
        for ( int j=1; j<=5; j++)
            dq.addFirst(j);

        for ( int j=100; j<=500; j+=100)
            dq.addLast(j);

        print(dq);
        int sz = dq.size();
        for (int i=0; i<sz; i++)
            dq.removeLast();
        print(dq);
    }

    public static void print (Deque<Integer> dq)
    {
        int size = dq.size();
        System.out.format("Reported size = %d%n", size);

        Iterator<Integer> it = dq.iterator();
        while ( it.hasNext())
        {
            int i = it.next();
            System.out.format ("Value is %d%n", i);
        }
    }

}
