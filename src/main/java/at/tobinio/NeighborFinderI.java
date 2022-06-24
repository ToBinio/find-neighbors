package at.tobinio;

import java.util.List;

/**
 * Created: 23.06.2022
 *
 * @author Tobias Frischmann
 */

public interface NeighborFinder<T extends Position> {

    void add(T obj);

    void clear();

    List<T> getInBox(double x1, double y1, double x2, double y2);

    List<T> getInCircle(double centerX, double centerY, double radius);
}
