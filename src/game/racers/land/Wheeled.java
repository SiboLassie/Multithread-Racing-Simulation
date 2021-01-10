package game.racers.land;

/**
 * This is a class for Wheeled Racers.
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Car
 * @see game.racers.air.Airplane
 * @see Bicycle
 */
public class Wheeled {
    //Fields:
    private int numOfWheels;

    /**
     * This is a Getter to get this specific racer num of wheels.
     * @return this specific racer num of wheels.
     */
    public int getNumOfWheels() {
        return numOfWheels;
    }

    /**
     * This is a Setter to set this racer num of wheels by user.
     * @param numOfWheels how many wheels to set.
     */
    private void setNumOfWheels(int numOfWheels) {
        this.numOfWheels = numOfWheels;
    }

    //Constructors:

    /**
     * This is this Class nonDefault constructor
     *  here we initialize an instance of NumOfWheels so keep a racer number of wheels!!
     * @param numOfWheels number of wheels.
     */
    public Wheeled(int numOfWheels) {
        this.setNumOfWheels(numOfWheels);
    }

}
