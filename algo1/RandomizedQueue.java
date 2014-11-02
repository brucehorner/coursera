import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] data;
    private int size;
    public RandomizedQueue()           // construct an empty randomized queue
    {
       data = (Item[]) new Object[1];
       size = 0;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = data[i];
        data = copy;
    }

    public boolean isEmpty()           // is the queue empty?
    {
        return size == 0;
    }

    public int size()                  // return the number of items on the queue
    {
        return size;
    }

    public void enqueue(Item item)     // add the item
    {
        if (item == null) throw new NullPointerException("cannot pass null to enqueue()");
        if (size == data.length) resize(2 * data.length);
        data[size++] = item;
    }

    public Item dequeue()              // delete and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        int pos = StdRandom.uniform(size);
        Item ret = data[pos];
        for (int i = pos; i < size-1; i++)
            data[i] = data[i+1];
        // decrement size counter and mark the end element null to avoid loitering
        data[--size] = null;

        if (size <= data.length/4 && size > 0)
            resize(size);

        return ret;
    }

    public Item sample()               // return (but do not delete) a random item
    {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        int pos = StdRandom.uniform(size);
        return data[pos];
    }

    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new PrivateIterator();
    }

    private class PrivateIterator implements Iterator<Item>
    {
        Item[] itData;
        int itSize = 0;

        public PrivateIterator()
        {
            itSize = size;
            itData = (Item[]) new Object[itSize];
            for (int i = 0; i < itSize; i++)
                itData[i] = data[i];
        }

        public boolean hasNext()
        {
            return itSize != 0;
        }

        public void remove() { throw new UnsupportedOperationException("remove() is not implemented."); }

        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException("no more elements");

            int pos = StdRandom.uniform(itSize);
            Item ret = itData[pos];
            for (int i = pos; i < itSize-1; i++)
                itData[i] = itData[i+1];
            itSize--;
            return ret;
        }
    }
}