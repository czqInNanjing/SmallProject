package union;

/**
 * @author Qiang
 * @since 12/04/2017
 */
public interface Union {
    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one object
     * @return the component identifier for the component containing site {@code p}
     * @throws IndexOutOfBoundsException unless {@code 0 <= p < n}
     */
    int find(int p);

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IndexOutOfBoundsException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    boolean connected(int p, int q);
    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IndexOutOfBoundsException unless
     *         both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    void union(int p, int q);
    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    int count();


}
