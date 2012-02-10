package thoughtworks;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: 1/4/12
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractPoint {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setXY(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

}
