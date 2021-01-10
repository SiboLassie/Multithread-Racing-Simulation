package game;

/**
 *  This Class made to help us use Points
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class Point {
    //Fields:
    private double x;
    private double y;

    //Setters & Getters:

    /**
     * this is a Setter to set this object x coordinate
     * @param x this is x coordinate
     * @return True or False depend if we set  successfully
     */
    public boolean setX(double x) {
        // we accept only positive numbers!
        if (x>=0) {
            this.x = x;
            return true;
        }
        // not positive no set.
        else return false;
    }

    /**
     * this is a Setter to set this object y coordinate
     * @param y this is y coordinate
     * @return True or False depend if we set successfully
     */
    public boolean setY(double y) {
        // we accept only positive numbers!
        if (y>=0) {
            this.y = y;
            return true;
        }
        // not positive no set.
        else return false;
    }

    /**
     * This is a Getter to get this object x coordinate
     * @return this object x coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * This is a Getter to get this object y coordinate
     * @return this object y coordinate
     */
    public double getY() {
        return this.y;
    }

    //Methods & Constructors:
    /**
     * This is an override ToString function just to make a better display
     * @return Custom string of the specific class
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ')';
    }


    /**
     * This is the class Constructor to initial an instance
     *  first we set the X coordinate
     *   then we set the Y coordinate
     *
     * @param x is the X coordinate
     * @param y is the Y coordinate
     */
    public Point(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * This is the class Copy Constructor to copy an exist instance
     *  first we set the X coordinate by the copied one
     *   then we set the Y coordinate by the copied one
     *
     * @param p is an other instance of the class Point
     */
    public Point(Point p) {
        this.setX(p.getX());
        this.setY(p.getY());
    }

}
