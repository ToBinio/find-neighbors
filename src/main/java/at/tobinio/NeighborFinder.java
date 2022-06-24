package at.tobinio;

/**
 * Created: 24.06.2022
 *
 * @author Tobias Frischmann
 */

public abstract class NeighborFinder<T extends Position> implements NeighborFinderI<T> {

    protected final double minX;
    protected final double width;

    protected final double minY;
    protected final double height;


    public NeighborFinder(double width, double height) {
        this(0, width, 0, height);
    }

    public NeighborFinder(double minX, double maxX, double minY, double maxY) {
        if (maxX < minX) throw new IllegalArgumentException("maxX can not be larger than minX");
        this.minX = minX;
        this.width = maxX - minX;

        if (maxY < minY) throw new IllegalArgumentException("maxY can not be larger than minY");
        this.minY = minY;
        this.height = maxY - minY;
    }
}
