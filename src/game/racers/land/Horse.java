package game.racers.land;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * This is a racer class made to race in the LandArena for the game we build
 *  basically its set an horse racer that derived from Racer class and extents it
 *  this class inherit from Racer class a racerName, a currLocation, maxSpeed, currSpeed
 *   and store that unique data for each racer so we can use them as individual racers
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Car
 */
public class Horse extends Racer implements LandRacer{
    //Fields:
    private EnumContainer.Breed breed;

    //Getters & Setters:
    /**
     * This is a Getter to get this horse Breed.
     * @return this horse breed
     */
    private EnumContainer.Breed getBreed() {
        return breed;
    }

    /**
     * This is a Setter to set this horse breed.
     * @param breed this is a horse breed to set by user.
     */
    private void setBreed(EnumContainer.Breed breed) {
        this.breed = breed;
    }

    /**
     * This is an overLoaded method that describe the specific racer details,
     * @return a string that describe the specific racer details.
     */
    @Override
    public String describeSpecific() {
        return  " color: " + super.getColor() +
                " Breed: " + this.getBreed();
    }

    //Constructor:
    /**
     * This is the class default constructor to initial an instance
     *  first we set the racerName horse as default 'Horse' with a serial number
     *   then we set a Default MaxSpeed & Acceleration for horse (50, 3)
     *    finally we set default Color and Breed for horse (BLACK, THOROUGHBRED)
     */
    public Horse() {
        super();
        super.setMyName("Horse #" + super.getSerialNumber());
        super.setMaxSpeed(50);
        super.setAcceleration(3);
        super.setColor(EnumContainer.Color.BLACK);
        this.setBreed(EnumContainer.Breed.THOROUGHBRED);
    }

    /**
     * This is the class nonDefault constructor to initial an instance,
     *  first we set the racer racerName,
     *   then we set a MaxSpeed & Acceleration for the racer by the user.
     *    finally we set Color by user.
     *
     * @param name the racerName of the racer (the instance).
     * @param maxSpeed the MaxSpeed set by user.
     * @param acceleration the acceleration set by user.
     * @param color the color set by user.
     */
    public Horse(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super();
        super.setMyName(name);
        super.setMaxSpeed(maxSpeed);
        super.setAcceleration(acceleration);
        super.setColor(color);
        this.setBreed(EnumContainer.Breed.THOROUGHBRED);
    }

}

