package at.tobinio;

import at.toBinio.timer.BasicTimer;
import at.tobinio.quadTree.QuadTree;
import at.tobinio.spacialHashmap.SpacialHashmap;

import java.util.List;


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

        NeighborFinder<TestObj> spacialFinder = new SpacialHashmap<>(-500, 500, -500, 500, 500, 500);
        NeighborFinder<TestObj> quadTreeFinder = new QuadTree<>(-500, 500, -500, 500, 5);

        int radius = 5;

        BasicTimer mapBasicTimer = new BasicTimer("Map");
        testNeighborFinder(spacialFinder, "Spacial", list, radius);
        mapBasicTimer.stop();

        BasicTimer treeBasicTimer = new BasicTimer("Tree");
        testNeighborFinder(quadTreeFinder, "QuadTree", list, radius);
        treeBasicTimer.stop();

        System.out.println(compare(quadTreeFinder.getInCircle(0, 0, 10), spacialFinder.getInCircle(0, 0, 10)));
    }

    private static void testNeighborFinder(NeighborFinder<TestObj> finder, String name, TestObj[] list, int radius) {
        for (TestObj testObj : list) {
            finder.add(testObj);
        }
        for (TestObj testObj : list) {
            finder.getInCircle(testObj.getX(), testObj.getY(), radius);
        }
    }

    private static boolean compare(List<TestObj> a, List<TestObj> b) {
        for (TestObj testObj : a) {
            if (!b.contains(testObj)) return false;
        }

        for (TestObj testObj : b) {
            if (!a.contains(testObj)) return false;
        }

        return true;
    }

}