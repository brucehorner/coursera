import java.util.NoSuchElementException;


public class Subset
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length>0)
        {
            int k = Integer.parseInt(args[0]);
            RandomizedQueue<String> queue = new RandomizedQueue<String>();
            /*
            while (StdIn.hasNextChar())
            {
                try
                {
                    String s = StdIn.readString();
                    queue.enqueue(s);
                }
                catch (NoSuchElementException e)
                {
                    break;
                }
            }
            */

            String[] strs = StdIn.readStrings();
            for (String s: strs)
                queue.enqueue(s);

            for (int i = 0; i < k; i++)
                System.out.println (queue.dequeue());
        }
        else
            System.err.printf ("please supply subset size");

    }

}
