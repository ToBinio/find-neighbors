package at.tobinio;

import at.toBinio.Timer;
import at.tobinio.quadTree.QuadTree;
import at.tobinio.spacialHashmap.SpacialHashmap;


/**
 * Created: ${DATE}
 *
 * @author Tobias Frischmann
 */

public class Main {
    public static void main(String[] args) {
        TestObj[] list = new TestObj[1_000_000];

        for (int i = 0; i < list.length; i++) {
            list[i] = new TestObj(Math.random() * 1000 - 500, Math.random() * 1000 - 500);
        }

        NeighborFinder<TestObj> spacialFinder = new SpacialHashmap<>(-500, 500, -500, 500, 300, 300);
        NeighborFinder<TestObj> quadTreeFinder = new QuadTree<>(-500, 500, -500, 500, 30);

        int radius = 5;

        Timer neighbors = new Timer("Neighbors", true);

        testNeighborFinder(neighbors, spacialFinder, "Spacial", list, radius);
        testNeighborFinder(neighbors, quadTreeFinder, "QuadTree", list, radius);

        neighbors.stop();
    }

    private static void testNeighborFinder(Timer timer, NeighborFinder<TestObj> finder, String name, TestObj[] list,
            int radius) {
        timer.startSubTimer(name);
        timer.startSubTimer("creation");
        for (TestObj testObj : list) {
            finder.add(testObj);
        }
        timer.stopSubTimer();
        timer.startSubTimer("neighbors");
        for (TestObj testObj : list) {
            finder.getInCircle(testObj.getX(), testObj.getY(), radius);
        }
        timer.stopSubTimer();
        timer.stopSubTimer();
    }

}