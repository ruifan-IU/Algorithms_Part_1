import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            rq.enqueue(str);
        }
        int k = Integer.parseInt(args[0]);
        while (k > 0) {
            String str = rq.dequeue();
            StdOut.println(str);
            k--;
        }
    }
}
