package at.tobinio.quadTree;

import at.tobinio.NeighborFinder;
import at.tobinio.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 24.06.2022
 *
 * @author Tobias Frischmann
 */

public class QuadTree<T extends Position> extends NeighborFinder<T> {

    private final int nodeCapacity;
    private QuadTreeNode<T> mainNode;

    public QuadTree(double width, double height, int nodeCapacity) {
        this(0, width, 0, height, nodeCapacity);
    }

    public QuadTree(double minX, double maxX, double minY, double maxY, int nodeCapacity) {
        super(minX, maxX, minY, maxY);

        this.nodeCapacity = nodeCapacity;
        clear();
    }


    @Override
    public void add(T obj) {
        if (minY > obj.getY() || minY + height < obj.getY() || minX > obj.getX() || minX + width < obj.getX())
            throw new IllegalArgumentException("obj outOffBounce");

        mainNode.add(obj);
    }

    @Override
    public void clear() {
        mainNode = new QuadTreeNode<>(minX, minY, width, height, nodeCapacity);
    }

    @Override
    public List<T> getInBox(double x1, double y1, double x2, double y2) {

        List<T> list = new ArrayList<>();

        mainNode.getInBox(x1, y1, x2, y2, list);
        return list;
    }

    @Override
    public List<T> getInCircle(double centerX, double centerY, double radius) {
        List<T> list = new ArrayList<>();

        mainNode.getInCircle(centerX, centerY, radius, list);
        return list;
    }
}
