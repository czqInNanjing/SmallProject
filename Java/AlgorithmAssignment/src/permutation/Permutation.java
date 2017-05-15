package permutation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public class Permutation {

    public static void main(String[] args) {

        int numOfOutput = Integer.parseInt(args[0]);

        RandomizedQueue<String> strings = new RandomizedQueue<>();

        String[] allStrings = StdIn.readAllStrings();

        for (String temp : allStrings) {
            strings.enqueue(temp);
        }

        Iterator<String> itr = strings.iterator();

        for (int i = 0; i < numOfOutput; i++) {
            StdOut.println(itr.next());
        }


    }


}
