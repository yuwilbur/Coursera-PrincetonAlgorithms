import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] m_queue;
  private int m_size;
  
  public RandomizedQueue() {
    m_queue = (Item[]) new Object[1];
    m_size = 0;
  }
  
  public boolean isEmpty() {
    return (m_size == 0);
  }
  
  public int size() {
    return m_size;
  }
  
  public void enqueue(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    if (m_size >= m_queue.length) {
      resize(m_queue.length * 2);
    }
    m_queue[m_size] = item;
    m_size++;
  }
  
  public Item dequeue() {
    if (m_size == 0)
      throw new java.util.NoSuchElementException();
    int index = StdRandom.uniform(m_size);
    Item item = m_queue[index];
    m_queue[index] = m_queue[m_size - 1];
    m_queue[m_size - 1] = null;
    m_size--;
    if (m_size < m_queue.length / 2)
      resize(m_queue.length / 2);
    return item;
  }
  
  public Item sample() {
    if (m_size == 0)
      throw new java.util.NoSuchElementException();
    return m_queue[StdRandom.uniform(m_size)];
  }
  
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }
  
  private void resize(int capacity) {
    Item[] queue = (Item[]) new Object[capacity];
      for(int i = 0; i < m_queue.length; i++) {
        queue[i] = m_queue[i];
      }
      m_queue = queue;
  }
  
  private class RandomizedQueueIterator implements Iterator<Item> 
  {
    private int currentIndex;
    private Object[] subqueue;
    public RandomizedQueueIterator() {
      currentIndex = 0;
      subqueue = new Object[m_size];
      System.arraycopy(m_queue, 0, subqueue, 0, m_size);
      StdRandom.shuffle(subqueue);
      
    }
    public boolean hasNext() { 
      return (currentIndex < subqueue.length); 
    }
    public void remove() { throw new java.lang.UnsupportedOperationException(); }
    public Item next() {
      if (currentIndex >= subqueue.length)
        throw new java.util.NoSuchElementException();
      return (Item)subqueue[currentIndex++];
    }
  }
}