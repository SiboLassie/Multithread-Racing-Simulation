package game.racers.air;
import game.racers.Racer;
import game.racers.land.Wheeled;
import utilities.EnumContainer;

/**
 * This is a racer class made to race in the AerialArena for the game we build
 *  basically its set an airplane racer that derived from Racer class and extents it
 *  this class inherit from Racer class a racerName, a currLocation, maxSpeed, currSpeed
 *   and store that unique data for each racer so we can use them as individual racers
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Helicopter
 */
public class Airplane extends Racer implements AerialRacer {
    //Fields:

    /**
     * This is an OverLoaded method that describe the specific racer details,
     * @return a string that describe the specific racer details.
     */
    @Override
    public String describeSpecific() {
        return  " color: " + super.getColor() +
                " Number of Wheels: " + this.getNumOfMyWheels();
    }

    //Constructors:
    /**
     * This is the class default constructor to initial an instance
     *  first we set the racerName airplane as default 'AirPlane' with a serial number
     *   then we set a Default MaxSpeed & Acceleration for airplane (885, 100)
     *    finally we set default Color and Num of Wheels for airplane (Black, 3)
     */
    public Airplane() {
        super();
        super.setMyName("AirPlane #" + super.getSerialNumber());
        super.setMaxSpeed(885);
        super.setAcceleration(100);
        super.setColor(EnumContainer.Color.BLACK);
        this.setNumOfMyWheels(new Wheeled(3));
    }

    /**
     * This is the class nonDefault constructor to initial an instance,
     *  first we set the racer racerName,
     *   then we set a MaxSpeed & Acceleration for the racer by the user.
     *
     * @param name the name of the racer (the instance).
     * @param maxSpeed the max speed set by user.
     * @param acceleration the acceleration set by user.
     * @param color the color set by user.
     */
    public Airplane(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super();
        super.setMyName(name);
        super.setMaxSpeed(maxSpeed);
        super.setAcceleration(acceleration);
        super.setColor(color);
        this.setNumOfMyWheels(new Wheeled(0));
    }
}
