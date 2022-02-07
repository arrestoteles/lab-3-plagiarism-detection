/**
 *  The {@code Stopwatch} data type is for measuring the time that elapses
 *  between the start and end of a programming task (wall-clock time).
 *
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/14analysis">Section 1.4</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Stopwatch {

    private long start;

    public Stopwatch() {
        reset();
    }

    public void reset() {
        start = System.currentTimeMillis();
    }

    // returns elapsed time in seconds after last reset.
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

   public void finished(String task) {
       System.out.printf("%s took %.2f seconds.%n", task, elapsedTime());
       reset();
   }

}
