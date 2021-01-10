package game.arenas.naval;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.naval.RowBoat;
import game.racers.naval.SpeedBoat;
import utilities.EnumContainer;

/**
 * This is the class for the Naval Arena for the game we build
 *  This class is inherits from class Arena and this class purpose is to extends Arena class
 *  basically its set an arena that can get 2 kind of racers: RowBoats & SpeedBoats
 *   and store them in a super class arrays fields
 *    there are maximum of 5 racers in this arena and the friction is 0.7
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Arena
 */
public class NavalArena extends Arena{
    //Fields:
    private EnumContainer.Water water;
    private EnumContainer.WaterSurface surface;
    private EnumContainer.Body body;

    //Getters & Setters:
    /**
     * This is a Setter to set this arena Water
     * @param water what kind of water is going to be
     */
    private void setWater(EnumContainer.Water water) {
        this.water = water;
    }

    /**
     * This is a Setter to set this arena Surface
     * @param surface hows the water surface is going to be
     */
    private void setSurface(EnumContainer.WaterSurface surface) {
        this.surface = surface;
    }

    /**
     * This is a Setter to set this arena Body
     * @param body how this arena body is going to be
     */
    public void setBody(EnumContainer.Body body) {
        this.body = body;
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
    public void addRacer(Racer newRacer) throws RacerTypeException, RacerLimitException, NullPointerException {
        if (newRacer instanceof RowBoat || newRacer instanceof SpeedBoat) {
            // check if we have space for more racers
            if (super.activeRacers.size() >= this.getMAX_RACERS()) {
                // if the arena is full and reached max racers, we not adding the racer and throw an Exception
                throw new RacerLimitException("Arena is full! (" + activeRacers.size() + " active racers exist). racer #" + newRacer.getSerialNumber() + " was not added" );
            } else {
                // if we have space for more racers we add them
                this.activeRacers.add(newRacer);
            }
        } else // we throw exception if the racer type is not fit for this arena
            throw new RacerTypeException("Invalid Racer of type " + '"' + newRacer.className() + '"' + " for Naval arena." );
    }

    //Constructors:
    /**
     * This is the class Default Constructor
     * we set by default the super class arena constructor the parameters length, maxRacers, friction
     * then we set this class attributes i.e water type, water surface.
     */
    public NavalArena() {
        super(800, 5, 0.7);
        this.setWater(EnumContainer.Water.SWEET);
        this.setSurface(EnumContainer.WaterSurface.FLAT);
        this.setBody(EnumContainer.Body.LAKE);
    }

    /**
     * This is the class NonDefault Constructor
     * in this Constructor we can pass a length and max racer by our choice
     * we set by default the super class arena constructor the parameters friction
     * then we set this class attributes i.e water type, water surface...
     */
    public NavalArena(double length, int maxRacers) {
        super(length, maxRacers, 0.7);
        this.setWater(EnumContainer.Water.SWEET);
        this.setSurface(EnumContainer.WaterSurface.FLAT);
        this.setBody(EnumContainer.Body.LAKE);
    }
}

