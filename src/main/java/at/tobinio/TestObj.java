package at.tobinio;

/**
 * Created: 23.06.2022
 *
 * @author Tobias Frischmann
 */

public class TestObj implements Position {

    private double x;
    private double y;

    public TestObj(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
