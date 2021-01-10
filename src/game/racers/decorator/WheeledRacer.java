package game.racers.decorator;
import game.racers.IDecoratedRacer;
import game.racers.land.Wheeled;

/**
 * Decorator Design Pattern:
 * this class is basically a Wheels Decorator,
 * here we decorating a racer to set wheels.
 * this class extends RacerDecorator to gain access for methods and attributes.
 *
 * @see RacerDecorator
 * @version 1.0
 * @author Avihay D Hemo
 */
public class WheeledRacer extends RacerDecorator {
    // Fields:
    private Wheeled numOfWheels;

    // Constructor:
    /**
     * this constructor is important, in the parameter we getting the specific racer we want to decorate,
     * then we initial the super class decorated racer that we got by the parameter,
     * and then we set him a new wheels by user input,
     * finally we get to use the method of the Decorator Design pattern to assign the specific decorate.
     *
     * @param decoratedRacer a reference to the racer we want to decorate.
     * @param numOfWheels how many wheels to set, decide by the user.
     */
    public WheeledRacer(IDecoratedRacer decoratedRacer, int numOfWheels) {
        super( decoratedRacer );
        setNumOfWheels( new Wheeled( numOfWheels ) );
        this.addAttribute( "wheeled", this.getNumOfWheels() );
    }

    /**
     * This is an override function that we derived from IDecoratedRacer to help us decorate right.
     *
     * @see IDecoratedRacer
     * @param key pass the key as it is to the DecoratedRacer.
     * @param value pass the value as it is to the DecoratedRacer.
     */
    @Override
    public void addAttribute(String key, Object value) {
        getDecoratedRacer().addAttribute( key, value );
    }

    /**
     * This is a Getter to get the number of wheels.
     * @return the number of wheels.
     */
    public Wheeled getNumOfWheels() {
        return numOfWheels;
    }

    /**
     * This is a Setter to set this number of wheels.
     * @param numOfWheels is the number of wheels to set.
     */
    public void setNumOfWheels(Wheeled numOfWheels) {
        this.numOfWheels = numOfWheels;
    }
}
