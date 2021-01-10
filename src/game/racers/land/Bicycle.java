package game.racers.land;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * This is a racer class made to race in the LandArena for the game we build
 *  basically its set an bicycle racer that derived from Racer class and extents it
 *  this class inherit from Racer class a racerName, a currLocation, maxSpeed, currSpeed
 *   and store that unique data for each racer so we can use them as individual racers
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Horse
 */
public class Bicycle extends Racer implements LandRacer {
    //Fields:
    private EnumContainer.BicycleType type;

    //Getters & Setters:
    /**
     * This is a Getter to get this class Type
     * @return we return the type
     */
    public EnumContainer.BicycleType getType() {
        return type;
    }

    /**
     * This is a Setter to set this class Type,
     * @param type we set the type by user.
     */
    public void setType(EnumContainer.BicycleType type) {
        this.type = type;
    }

    /**
     * This is an overLoaded method that describe the specific racer details,
     * @return a string that describe the specific racer details.
     */
    @Override
    public String describeSpecific() {
        return  " color: " + super.getColor() +
                " Number of Wheels: " + this.getNumOfMyWheels() +
                " Bicycle Type: " + this.getType();
    }

    //Constructors:
    /**
     * This is the class default constructor to initial an instance
     *  first we set the racerName bicycle as default 'Bicycle' with a serial number
     *   then we set a Default MaxSpeed & Acceleration for bicycle (270, 10)
     *    finally we set default Color and Num of Wheels and Type for bicycle (Green, 2, MOUNTAIN)
     */
    public Bicycle() {
        super();
        super.setMyName("Bicycle #" + super.getSerialNumber());
        super.setMaxSpeed(270);
        super.setAcceleration(10);
        super.setColor(EnumContainer.Color.GREEN);
        this.setNumOfMyWheels(new Wheeled(2));
        this.setType(EnumContainer.BicycleType.MOUNTAIN);
    }

    /**
     * This is the class nonDefault constructor to initial an instance,
     *  first we set the racer racerName,
     *   then we set a MaxSpeed & Acceleration for the racer by the user.
     *    finally we set Color and Num of Wheels by user.
     *
     * @param name the racerName of the racer (the instance).
     * @param maxSpeed the MaxSpeed set by user.
     * @param acceleration the acceleration set by user.
     * @param color the color set by user.
     */
    public Bicycle(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super();
        super.setMyName(name);
        super.setMaxSpeed(maxSpeed);
        super.setAcceleration(acceleration);
        super.setColor(color);
        this.setNumOfMyWheels(new Wheeled(2));
        this.setType(EnumContainer.BicycleType.MOUNTAIN);
    }
}
