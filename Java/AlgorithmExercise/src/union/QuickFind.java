package union;

/**
 * The normal implementation of union
 * @author Qiang
 * @since 11/04/2017
 */
public class QuickFind implements Union{

    private int[] id;
    private int count;

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public QuickFind(int n) {
        id = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    @Override
    public int find(int i) {
        validate(i);
        return id[i];
    }

    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    @Override
    public void union(int p, int q) {
        int newRoot = id[q];
        if (id[p] != newRoot) {
            for (int i = 0; i < id.length; i++) {
                if (id[i] == id[q] && i != q) {
                    id[i] = id[q];
                }
            }
            count--;
        }


    }

    @Override
    public int count() {
        return count;
    }
}
