import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;

// This class represents a pair of file paths. If x and y are objects
// of type Path then you can create a PathPair with:
//
//   PathPair pair = new PathPair(x, y);
//
// after which pair.path1 will be x and pair.path2 will be y.
public class PathPair implements Comparable<PathPair> {
    public final Path path1, path2;

    public PathPair(Path path1, Path path2) {
        this.path1 = path1;
        this.path2 = path2;
    }

    public String toString() {
        return path1 + " and " + path2;
    }

    public boolean equals(Object obj) {
        if (!(obj != null && obj.getClass() == this.getClass()))
            return false;

        PathPair pair = (PathPair) obj;
        return this.path1.equals(pair.path1) && this.path2.equals(pair.path2);
    }

    public int hashCode() {
        return Objects.hash(path1, path2);
    }

    public int compareTo(PathPair other) {
        return Comparator.comparing((PathPair pair) -> pair.path1)
            .thenComparing(pair -> pair.path2)
            .compare(this, other);
    }

    // Return the same pair of paths but sorted so that path1 <= path2.
    // This is useful when we want to compare two PathPairs ignoring
    // the order of the paths.
    public PathPair canonicalise() {
        if (path1.compareTo(path2) <= 0)
            return new PathPair(path1, path2);
        else
            return new PathPair(path2, path1);
    }
}
