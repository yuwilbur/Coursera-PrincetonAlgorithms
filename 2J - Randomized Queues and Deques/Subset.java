import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
  public static void main (String[] args) {
    if (args.length == 0)
      return;
    int k = Integer.parseInt(args[0]);
    
    String[] inputs = StdIn.readAllStrings();
    RandomizedQueue<String> queue = new RandomizedQueue<String>();
    for(int i = 0; i < inputs.length; i++) {
      queue.enqueue(inputs[i]);
    }
    
    for(int i = 0; i < k; i++) {
      StdOut.print(queue.dequeue());
    }
  }
}