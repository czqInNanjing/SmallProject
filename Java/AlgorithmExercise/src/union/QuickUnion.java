package union;

/**
 * Another implementation of Union with Lazy implementation of union operation
 *
 * @author Qiang
 * @since 12/04/2017
 */
public class QuickUnion implements Union {

    private int[] id;
    private int count;

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public QuickUnion(int n) {

        id = new int[n];
        count = n;


    }

    @Override
    public int find(int i) {
        while (id[i] != i) {
            i = id[i];
        }
        return i;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i != j) {
            id[i] = j;
            count--;
        }


    }

    @Override
    public int count() {
        return count;
    }
}
