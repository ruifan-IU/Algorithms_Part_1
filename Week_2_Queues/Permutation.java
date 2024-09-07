import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        int n = 0;
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            n++;
            if (n <= k) {
                rq.enqueue(str);
            }
            else {
                double p = (double) k / n;
                boolean include = StdRandom.bernoulli(p);
                if (include) {
                    rq.dequeue();
                    rq.enqueue(str);
                }
            }
        }
        for (String s : rq) {
            StdOut.println(s);
        }
    }
}