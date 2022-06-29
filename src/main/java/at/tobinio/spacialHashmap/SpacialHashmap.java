package at.tobinio.spacialHashmap;

import at.tobinio.NeighborFinder;
import at.tobinio.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 23.06.2022
 *
 * @author Tobias Frischmann
 */

public class SpacialHashmap<T extends Position> extends NeighborFinder<T> {


    private final List<List<T>> map;

    private final int rowCount;
    private final int columnCount;

    public SpacialHashmap(double width, double height, int rowCount, int columnCount) {
        this(0, width, 0, height, rowCount, columnCount);
    }

    public SpacialHashmap(double minX, double maxX, double minY, double maxY, int rowCount, int columnCount) {
        super(minX, maxX, minY, maxY);

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
    public void add(T obj) {
        if (minY > obj.getY() || minY + height < obj.getY() || minX > obj.getX() || minX + width < obj.getX())
            throw new IllegalArgumentException("obj outOffBounce");

        map.get((int) ((int) ((obj.getY() - minY) / height * rowCount) * columnCount + ((obj.getX() - minX) / width * columnCount))).add(obj);
    }

    @Override
    public void clear() {
        for (List<T> cells : map) {
            cells.clear();
        }
    }

    private int[] getMinMaxValues(double x1, double y1, double x2, double y2) {
        if (x1 > x2) throw new IllegalArgumentException("x1 can not be larger than x2");

        if (y1 > y2) throw new IllegalArgumentException("y1 can not be larger than y2");


        int[] values = new int[4];

        values[0] = (int) Math.max(Math.floor((x1 - minX) / width * columnCount), 0);
        values[1] = (int) Math.min(Math.ceil((x2 - minX) / width * columnCount), columnCount - 1);

        values[2] = (int) Math.max(Math.floor((y1 - minY) / height * rowCount), 0);
        values[3] = (int) Math.min(Math.ceil((y2 - minY) / height * rowCount), rowCount - 1);

        return values;
    }

    @Override
    public List<T> getInBox(double x1, double y1, double x2, double y2) {

        int[] minMaxValues = getMinMaxValues(x1, y1, x2, y2);

        ArrayList<T> list = new ArrayList<>();

        for (int i = minMaxValues[0]; i <= minMaxValues[1]; i++) {
            for (int j = minMaxValues[2]; j <= minMaxValues[3]; j++) {

                for (T potential : map.get(i + j * columnCount)) {

                    if (potential.getX() >= x1 && potential.getX() <= x2 && potential.getY() >= y1 && potential.getY() <= y2) {
                        list.add(potential);
                    }

                }

            }
        }

        return list;
    }

    @Override
    public List<T> getInCircle(double centerX, double centerY, double radius) {
        if (radius <= 0) throw new IllegalArgumentException("radius musst be larger than 0");

        int[] minMaxValues = getMinMaxValues(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        double width = this.width / columnCount;
        double height = this.height / rowCount;

        ArrayList<T> list = new ArrayList<>();

        for (int i = minMaxValues[0]; i <= minMaxValues[1]; i++) {
            for (int j = minMaxValues[2]; j <= minMaxValues[3]; j++) {

                if (!isOverlapping(centerX, centerY, radius, i * width + minX, j * height + minY, width, height))
                    continue;

                for (T potential : map.get(i + j * columnCount)) {

                    if ((centerX - potential.getX()) * (centerX - potential.getX()) + (centerY - potential.getY()) * (centerY - potential.getY()) <= radius * radius)
                        list.add(potential);

                }

            }
        }

        return list;
    }
}


