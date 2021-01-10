package game.arenas.air;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import utilities.EnumContainer;

/**
 * This is the class for the AerialArena for the game we build
 *  This class is inherits from class Arena and this class purpose is to extends Arena class
 *  basically its set an arena that can get 2 kind of racers: airplanes & helicopters
 *   and store them in a super class arrays fields
 *    there are maximum of 6 racers in this arena and the friction is 0.4
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Arena
 */
public class AerialArena extends Arena {
    //Fields:
    private EnumContainer.Vision Vision;
    private EnumContainer.Weather Weather;
    private EnumContainer.Height Height;
    private EnumContainer.Wind Wind;

    //Setters & Getters:
    /**
     * This is a Setter to set this arena Vision
     * @param vision what vision is going to be
     */
    private void setVision(EnumContainer.Vision vision) {
        Vision = vision;
    }

    /**
     * This is a Setter to set this arena Weather
     * @param weather what weather is going to be
     */
    private void setWeather(EnumContainer.Weather weather) {
        Weather = weather;
    }

    /**
     * This is a Setter to set this arena Height
     * @param height how height is going to be
     */
    private void setHeight(EnumContainer.Height height) {
        Height = height;
    }

    /**
     * This is a Setter to set this arena Wind level
     * @param wind how much wind is going to be
     */
    private void setWind(EnumContainer.Wind wind) {
        Wind = wind;
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
        if (newRacer instanceof Airplane | newRacer instanceof Helicopter) {
            // check if we have space for more racers
            if (super.activeRacers.size() >= this.getMAX_RACERS()) {
                // if the arena is full and reached max racers, we not adding the racer and throw an Exception
                throw new RacerLimitException("Arena is full! (" + activeRacers.size() + " active racers exist). racer #" + newRacer.getSerialNumber() + " was not added" );
            } else {
                // if we have space for more racers we add them
                this.activeRacers.add(newRacer);
            }
        } else // throw exception if the racer type is not fit for this arena
            throw new RacerTypeException("Invalid Racer of type " + '"' + newRacer.className() + '"' + " for Aerial arena." );
    }

    //Constructors:

    /**
     * This is the class Default Constructor
     * set by default the super class arena constructor the parameters length, maxRacers, friction
     * then set this class attributes i.e vision, weather,wind level.
     */
    public AerialArena() {
        super(1500,6,0.4);
        this.setVision(EnumContainer.Vision.SUNNY);
        this.setWeather(EnumContainer.Weather.DRY);
        this.setHeight(EnumContainer.Height.HIGH);
        this.setWind(EnumContainer.Wind.HIGH);
    }

    /**
     * This is the class NonDefault Constructor
     * in this Constructor we can pass a length and max racer by our choice
     * we set by default the super class arena constructor the parameters friction
     * then we set this class attributes i.e vision, weather,wind level.
     */
    public AerialArena (double length, int MAX_RACERS) {
        super(length,MAX_RACERS,0.4);
        this.setVision(EnumContainer.Vision.SUNNY);
        this.setWeather(EnumContainer.Weather.DRY);
        this.setHeight(EnumContainer.Height.HIGH);
        this.setWind(EnumContainer.Wind.HIGH);
    }
}
