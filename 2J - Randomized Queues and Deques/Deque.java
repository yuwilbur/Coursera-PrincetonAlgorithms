import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private Node m_first;
  private Node m_last;
  private int m_size;
  
  private class Node {
    Item item;
    Node next;
    Node prev;
  }
  
  public Deque() {
    m_first = null;
    m_last = null;
    m_size = 0;
  }
  
  public boolean isEmpty() {
    return (m_first == null && m_last == null);
  }
  
  public int size() {
    return m_size;
  }
  
  public void addFirst(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    
    Node tempFirst = m_first;
    m_first = new Node();
    m_first.prev = null;
    m_first.item = item;
    m_first.next = tempFirst;
    if (tempFirst != null)
      tempFirst.prev = m_first;
    if (m_last == null)
      m_last = m_first;
    m_size++;
  }
  
  public void addLast(Item item) {
    if (item == null)
      throw new java.lang.NullPointerException();
    
    Node tempLast = m_last;
    m_last = new Node();
    m_last.next = null;
    m_last.item = item;
    m_last.prev = tempLast;
    if (tempLast != null)
      tempLast.next = m_last;
    if (m_first == null)
      m_first = m_last;
    m_size++;
  }
  
  public Item removeFirst() {
    if (m_first == null)
      throw new java.util.NoSuchElementException();
    Item item = m_first.item;
    m_first = m_first.next;
    if (m_first != null)
      m_first.prev = null;
    m_size--;
    if (m_first == null)
      m_last = null;
    return item;
  }
  
  public Item removeLast() {
    if (m_last == null)
      throw new java.util.NoSuchElementException();
    Item item = m_last.item;
    m_last = m_last.prev;
    if (m_last != null)
      m_last.next = null;
    m_size--;
    if (m_last == null)
      m_first = null;
    return item;
  }
  
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }
  
  private class DequeIterator implements Iterator<Item> 
  {
    private Node current = m_first;
    public boolean hasNext() { return current != null; }
    public void remove() { throw new java.lang.UnsupportedOperationException(); }
    public Item next() {
      if (current == null)
        throw new java.util.NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }
}