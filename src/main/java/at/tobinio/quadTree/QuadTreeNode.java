package at.tobinio.quadTree;

import at.tobinio.Position;

import java.util.List;

/**
 * Created: 24.06.2022
 *
 * @author Tobias Frischmann
 */

public class QuadTreeNode<T extends Position> {

    private final double x;
    private final double y;

    private final double width;
    private final double height;

    private final Position[] data;
    private int dataCount;

    private QuadTreeNode<T> nodeBottomLeft;
    private QuadTreeNode<T> nodeTopLeft;
    private QuadTreeNode<T> nodeTopRight;
    private QuadTreeNode<T> nodeBottomRight;

    public QuadTreeNode(double x, double y, double width, double height, int capacity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        data = new Position[capacity];
    }

    public void add(T obj) {
        if (dataCount < data.length) {
            data[dataCount] = obj;
            dataCount++;
        } else {

            if (nodeBottomLeft == null) {
                nodeBottomLeft = new QuadTreeNode<>(x, y, width / 2, height / 2, data.length);
                nodeBottomRight = new QuadTreeNode<>(x + width / 2, y, width / 2, height / 2, data.length);
                nodeTopLeft = new QuadTreeNode<>(x, y + height / 2, width / 2, height / 2, data.length);
                nodeTopRight = new QuadTreeNode<>(x + width / 2, y + height / 2, width / 2, height / 2, data.length);
            }

            if (obj.getX() > x + width / 2) {
                if (obj.getY() > y + height / 2) {
                    nodeTopRight.add(obj);
                } else {
                    nodeBottomRight.add(obj);
                }
            } else {
                if (obj.getY() > y + height / 2) {
                    nodeTopLeft.add(obj);
                } else {
                    nodeBottomLeft.add(obj);
                }
            }

        }
    }

    public List<T> getInBox(double x1, double y1, double x2, double y2, List<T> list) {

        for (Position obj : data) {
            if (obj == null) return list;

            if (obj.getX() >= x1 && obj.getX() <= x2 && obj.getY() >= y1 && obj.getY() <= y2) {
                list.add((T) obj);
            }
        }

        if (nodeTopRight == null) return list;

        if (nodeTopRight.isOverlapping(x1, y1, x2, y2)) nodeTopRight.getInBox(x1, y1, x2, y2, list);
        if (nodeTopLeft.isOverlapping(x1, y1, x2, y2)) nodeTopLeft.getInBox(x1, y1, x2, y2, list);
        if (nodeBottomRight.isOverlapping(x1, y1, x2, y2)) nodeBottomRight.getInBox(x1, y1, x2, y2, list);
        if (nodeBottomLeft.isOverlapping(x1, y1, x2, y2)) nodeBottomLeft.getInBox(x1, y1, x2, y2, list);

        return list;
    }

    public List<T> getInCircle(double centerX, double centerY, double radius, List<T> list) {

        for (Position obj : data) {
            if (obj == null) return list;

            if ((Math.sqrt((centerX - obj.getX()) * (centerX - obj.getX()) + (centerY - obj.getY()) * (centerY - obj.getY())) <= radius)) {
                list.add((T) obj);
            }
        }

        if (nodeTopRight == null) return list;

        if (nodeTopRight.isOverlapping(centerX, centerY, radius))
            nodeTopRight.getInCircle(centerX, centerY, radius, list);
        if (nodeTopLeft.isOverlapping(centerX, centerY, radius))
            nodeTopLeft.getInCircle(centerX, centerY, radius, list);
        if (nodeBottomRight.isOverlapping(centerX, centerY, radius))
            nodeBottomRight.getInCircle(centerX, centerY, radius, list);
        if (nodeBottomLeft.isOverlapping(centerX, centerY, radius))
            nodeBottomLeft.getInCircle(centerX, centerY, radius, list);

        return list;
    }

    public boolean isOverlapping(double x1, double y1, double x2, double y2) {
        if (x1 > x + width || x2 < x) return false;
        if (y1 > y + height || y2 < y) return false;

        return true;
    }

    boolean isOverlapping(double centerX, double centerY, double radius) {

        // temporary variables to set edges for testing
        double testX = centerX;
        double testY = centerY;

        // which edge is closest?
        if (centerX < x) testX = x;      // test left edge
        else if (centerX > x + width) testX = x + width;   // right edge
        if (centerY < y) testY = y;      // top edge
        else if (centerY > y + height) testY = y + height;   // bottom edge

        // get distance from the closest edges
        double distX = centerX - testX;
        double distY = centerY - testY;
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        // if the distance is less than the radius, collision!
        return distance <= radius;
    }

}
