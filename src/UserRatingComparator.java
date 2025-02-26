import java.util.Comparator;

public class UserRatingComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        return Double.compare(o2.getUserRating(), o1.getUserRating());
    }
}
