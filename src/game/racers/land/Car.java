package game.racers.land;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * This is a racer class made to race in the LandArena for the game we build
 *  basically its set an car racer that derived from Racer class and extents it
 *  this class inherit from Racer class a racerName, a currLocation, maxSpeed, currSpeed
 *   and store that unique data for each racer so we can use them as individual racers
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Bicycle
 */
public class Car extends Racer implements LandRacer{
    //Fields:
    private EnumContainer.Engine engine;

    //Getters & Setters:
    /**
     * This is a Getter to get this car engine.
     * @return this car engine.
     */
    private EnumContainer.Engine getEngine() {
        return engine;
    }

    /**
     * This is a Setter to set this car Engine.
     * @param engine a type of engine
     */
    private void setEngine(EnumContainer.Engine engine) {
        this.engine = engine;
    }

    /**
     * This is an overLoaded method that describe the specific racer details,
     * @return a string that describe the specific racer details.
     */
    @Override
    public String describeSpecific() {
        return  " color: " + super.getColor() +
                " Number of Wheels: " + this.getNumOfMyWheels() +
                " Engine Type:: " + this.getEngine();
    }

    //Constructors:
    /**
     * This is the class default constructor to initial an instance
     *  first we set the racerName Car as default 'Car' with a serial number
     *   then we set a default MaxSpeed & Acceleration for car (400, 20)
     *    finally we set default Color and Num of Wheels and Engine for car (RED, 4, FOURSTROKE)
     */
    public Car() {
        super();
        super.setMyName("Car #" + this.getSerialNumber());
        super.setMaxSpeed(400);
        super.setAcceleration(20);
        super.setColor(EnumContainer.Color.RED);
        this.setNumOfMyWheels(new Wheeled(4));
        this.setEngine(EnumContainer.Engine.FOURSTROKE);
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
    public Car(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super();
        super.setMyName(name);
        super.setMaxSpeed(maxSpeed);
        super.setAcceleration(acceleration);
        super.setColor(color);
        this.setNumOfMyWheels(new Wheeled(4));
        this.setEngine(EnumContainer.Engine.FOURSTROKE);
    }
}
