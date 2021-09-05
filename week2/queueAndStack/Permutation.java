import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> que = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            que.enqueue(StdIn.readString());
        }

//        for (Object q : que) {
//
//            String s = (String) q;
//            StdOut.println(s);
//            k--;
//            if (k == 0) {
//                break;
//            }
//
//        }

        while (k > 0) {
            StdOut.println(que.dequeue());
            k--;
        }
    }
}
