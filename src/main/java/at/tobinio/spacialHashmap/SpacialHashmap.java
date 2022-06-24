package at.tobinio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 23.06.2022
 *
 * @author Tobias Frischmann
 */

public class SpacialHashmap<T> implements NeighborFinder<T> {

    private final double minX;
    private final double width;

    private final double minY;
    private final double height;

    private final int rowCount;
    private final int columnCount;

    private final List<List<T>> map;

    public SpacialHashmap(double width, double height, int rowCount, int columnCount) {
        this(0, width, 0, height, rowCount, columnCount);
    }

    public SpacialHashmap(double minX, double maxX, double minY, double maxY, int rowCount, int columnCount) {
        if (maxX < minX) throw new IllegalArgumentException("maxX can not be larger than minX");
        this.minX = minX;
        this.width = maxX - minX;

        if (maxY < minY) throw new IllegalArgumentException("maxY can not be larger than minY");
        this.minY = minY;
        this.height = maxY - minY;

        if (rowCount <= 0) throw new IllegalArgumentException("rowCount must be larger than 0");
        this.rowCount = rowCount;

        if (columnCount <= 0) throw new IllegalArgumentException("columnCount must be larger than 0");
        this.columnCount = columnCount;

        // create map
        map = new ArrayList<>();

        for (int i = 0; i < rowCount * columnCount; i++) {
            map.add(new ArrayList<>());
        }

    }

    @Override
    public void add(double x, double y, T obj) {
        map.get((int) ((y / height * rowCount) * columnCount + (x / width * columnCount))).add(obj);
    }

    @Override
    public void clear() {
        for (List<T> cells : map) {
            cells.clear();
        }
    }

    @Override
    public List<T> getInBox(double x1, double y1, double x2, double y2) {
        if (x1 > x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }

        if (y1 > y2) {
            double temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int minX = (int) (x1 / width * columnCount);
        int maxX = (int) (x2 / width * columnCount);

        int minY = (int) (y1 / height * rowCount);
        int maxY = (int) (y2 / height * rowCount);

        ArrayList<T> list = new ArrayList<>();

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                list.addAll(map.get(i + j * columnCount));
            }
        }

        return list;
    }

    @Override
    public List<T> getInCircle(double centerX, double centerY, double radius) {
        return null;
    }
}
