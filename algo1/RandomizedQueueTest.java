import org.junit.Test;
import org.junit.*;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueueTest {

    private RandomizedQueue<String> rqs;
    private RandomizedQueue<Integer> rqi;


    @Before
    public void init() {
        rqs = new RandomizedQueue<String>();
        rqi = new RandomizedQueue<Integer>();
    }

    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(rqs.isEmpty());
        rqs.enqueue("Test");
        Assert.assertFalse(rqs.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        Assert.assertTrue(rqs.size() == 0);
        rqs.enqueue("1");
        Assert.assertTrue(rqs.size() == 1);
        rqs.enqueue("2");
        Assert.assertTrue(rqs.size() == 2);
    }

    @Test(expected = NoSuchElementException.class)
    public void testSampleException() throws Exception {
        rqs.sample();
    }

    @Test
    public void testSample() throws Exception {
        rqs.enqueue("1");
        rqs.enqueue("2");
        rqs.enqueue("3");
        for (int i = 0; i < 100000; ++i)
            if (rqs.sample() == "1")
                return;
        Assert.fail("sample() must return random values.");
    }


    @Test
    public void testEnqueue() throws Exception {
        rqs.enqueue("1");
        rqs.enqueue("2");
        rqs.enqueue("3");
        Assert.assertTrue(rqs.size() == 3);
    }

    @Test
    public void testDequeue() throws Exception {
        rqs.enqueue("1");
        rqs.enqueue("2");
        rqs.enqueue("3");
        rqs.enqueue("4");
        rqs.enqueue("5");
        rqs.enqueue("6");
        for (int i = rqs.size(); --i >= 0; )
            rqs.dequeue();
        Assert.assertTrue(rqs.isEmpty());
    }

    @Test
    public void testIterator() throws Exception {
        rqs.enqueue("1");
        rqs.enqueue("2");
        rqs.enqueue("3");
        rqs.enqueue("4");
        rqs.enqueue("5");
        rqs.enqueue("6");
        StringBuilder sb = new StringBuilder(10);
        int sum = 0;
        for (String s : rqs) {
            sb.append(s);
            sum += Integer.parseInt(s);
        }
        Assert.assertTrue(sb.toString().length() == 6);
        Assert.assertTrue(sum == 1 + 2 + 3 + 4 + 5 + 6);
    }

    @Test
    public void testIteratorEmpty() throws Exception {
        StringBuilder sb = new StringBuilder(10);
        for (String s : rqs)
            sb.append(s);
        Assert.assertTrue(sb.toString().length() == 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextException() throws Exception {
        rqs.enqueue("1");
        Iterator<String> i = rqs.iterator();
        i.next();
        i.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveException() throws Exception {
        rqs.enqueue("1");
        Iterator<String> i = rqs.iterator();
        i.remove();
    }
    
    @Test
    public void largeUpAndDown()
    {
    	int limit = 4142;
    	for (int i=0; i<limit; i++)
    		rqi.enqueue(i);
    	for (int i=0; i<limit-1; i++)
    		rqi.dequeue();
    	Assert.assertTrue(rqi.size()==1);    	
    }
    
}