import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item>
{
    private int localSize;
    private Node first, last;

    private class Node                    // a double Linked List of Items
    {
        Item item;
        Node next;
        Node prior;
    }

    public Deque()                         // construct an empty deque
    {
        localSize = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty()           // is the deque empty?
    {
        return first == null;
    }

    public int size()                  // return the number of items on the deque
    {
        return localSize;
    }

    public void addFirst(Item item)    // insert the item at the front
    {
        if (item == null) throw new NullPointerException("cannot pass null to addFirst()");

        Node oldfirst = first;    // note current head, which could be null
        first = new Node();        // create a node for the new head
        first.item = item;
        first.next = oldfirst;    // connect to the prior head
        first.prior = null;
        if (oldfirst != null) oldfirst.prior = first;
        if (last == null)    last = first;
        localSize++;
    }

    public void addLast(Item item)     // insert the item at the end
    {
        if (item == null) throw new NullPointerException("cannot pass null to addLast()");

        Node oldlast = last;        // note current end of queue, which could be null
        last = new Node();        // create a node as the new end
        last.item = item;
        last.next = null;
        last.prior = oldlast;
        if (first == null)
            first = last;
        if (oldlast != null)
            oldlast.next = last;        // connect to the prior end of the queue

        localSize++;
    }

    public Item removeFirst()          // delete and return the item at the front
    {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        Item item = first.item;
        first = first.next;
        if (first != null)    first.prior = null;        // this is now the new head with no prior
        if (isEmpty()) last = null;
        localSize--;
        return item;
    }

    public Item removeLast()           // delete and return the item at the end
    {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");

        Node oldlast = last;
        last = oldlast.prior;
        if (last != null)
            last.next = null;
        else
            first = last;
        localSize--;

        return oldlast.item;
    }
    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new PrivateIterator();
    }

    private class PrivateIterator implements Iterator<Item>
    {
        private Node pos = first;
        public boolean hasNext()
        {
            return pos != null;
        }

        public void remove() { throw new UnsupportedOperationException("remove() is not implemented."); }

        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException("no more elements");

            Item ret = pos.item;
            pos = pos.next;

            return ret;
        }
    }
}