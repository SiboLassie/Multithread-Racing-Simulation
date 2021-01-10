package game.arenas.land;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.land.Bicycle;
import game.racers.land.Car;
import game.racers.land.Horse;
import utilities.EnumContainer;

/**
 * This is the class for the LandArena for the game we build
 *  This class is inherits from class Arena and this class purpose is to extends Arena class
 *  basically its set an arena that can get 3 kind of racers: cars & horses & bicycle
 *   and store them in a super class arrays fields
 *    there are maximum of 8 racers in this arena and the friction is 0.5
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Arena
 */
public class LandArena extends Arena {
    //Fields:
    private EnumContainer.Coverage coverage;
    private EnumContainer.LandSurface surface;

    //Getters & Setters:
    /**
     * This is a Setter to set this arena Coverage
     * @param coverage how coverage is going to be
     */
    private void setCoverage(EnumContainer.Coverage coverage) {
        this.coverage = coverage;
    }

    /**
     * This is a Setter to set this arena Surface
     * @param surface what surface is going to be
     */
    private void setSurface(EnumContainer.LandSurface surface) {
        this.surface = surface;
    }

    //Methods:
    /**
     * This overloaded function purpose is to add more racers to the arena
     * first we check that the parameter we got is an instance of a Racer class
     * if so we check if we have space for more racers
     * if so we add the racer to the ArrayList of active racers in the super class
     *
     * @param newRacer this is an instance of Racer class
     */
    public void addRacer(Racer newRacer) throws RacerTypeException, RacerLimitException {
        if ((newRacer instanceof Car | newRacer instanceof Bicycle | newRacer instanceof Horse) || (newRacer.isWheeled())) {
            // check if we have space for more racers
            if (super.activeRacers.size() >= this.getMAX_RACERS()) {
                // if the arena is full and reached max racers, we can not add the racer and throw an Exception
                throw new RacerLimitException("Arena is full! (" + activeRacers.size() + " active racers exist). racer #" + newRacer.getSerialNumber() + " was not added" );
            } else {
                // if we have space for more racers we add them
                this.activeRacers.add(newRacer);
            }
        }
        else // throw exception if the racer type is not fit for this arena
            throw new RacerTypeException("Invalid Racer of type " + '"' + newRacer.className() + '"' + " for Land arena." );
    }

    //Constructors:
    /**
     * This is the class Default Constructor
     * we set by default the super class arena constructor the parameters length, maxRacers, friction
     * then we set this class attributes i.e coverage, surface.
     */
    public LandArena() {
        super(1000, 8, 0.5);
        this.setCoverage(EnumContainer.Coverage.GRASS);
        this.setSurface(EnumContainer.LandSurface.FLAT);
    }

    /**
     * This is the class NonDefault Constructor
     * in this Constructor we can pass a length and max racer by our choice
     * we set by default the super class arena constructor the parameters friction
     * then we set this class attributes i.e coverage, surface.
     */
    public LandArena(double length, int maxRacers) {
        super(length, maxRacers, 0.5);
        this.setCoverage(EnumContainer.Coverage.GRASS);
        this.setSurface(EnumContainer.LandSurface.FLAT);
    }

}
