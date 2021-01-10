package game.racers.air;

import game.racers.Racer;
import utilities.EnumContainer;

/**
 * This is a racer class made to race in the AerialArena for the game we build
 *  basically its set an helicopter racer that derived from Racer class and extents it
 *  this class inherit from Racer class a racerName, a currLocation, maxSpeed, currSpeed
 *   and store that unique data for each racer so we can use them as individual racers
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Airplane
 */
public class Helicopter extends Racer implements AerialRacer {

    /**
     * This is an overLoaded method that describe the specific racer details,
     * @return a string that describe the specific racer details.
     */
    @Override
    public String describeSpecific() {
        return " color: " + super.getColor();
    }

    //Constructors:
    /**
     * This is the class default constructor to initial an instance
     *  first we set the racerName helicopter as default 'Helicopter' with a serial number
     *   then we set a Default MaxSpeed & Acceleration for helicopter (400, 50)
     *    finally we set default color for helicopter (Blue)
     */
    public Helicopter() {
        super();
        super.setMyName("Helicopter #" + super.getSerialNumber());
        super.setMaxSpeed(400);
        super.setAcceleration(50);
        super.setColor(EnumContainer.Color.BLUE);
    }

    /**
     * This is the class nonDefault constructor to initial an instance,
     *  first we set the racer racerName,
     *   then we set a MaxSpeed & Acceleration for the racer by the user.
     *
     * @param name the name of the racer (the instance).
     * @param maxSpeed the MaxSpeed set by user.
     * @param acceleration the acceleration set by user.
     * @param color the color set by user.
     */
    public Helicopter(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super();
        super.setMyName(name);
        super.setMaxSpeed(maxSpeed);
        super.setAcceleration(acceleration);
        super.setColor(color);
    }
}




