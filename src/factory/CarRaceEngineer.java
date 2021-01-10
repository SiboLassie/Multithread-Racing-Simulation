package factory;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;

/**
 * Builder Design Pattern:
 * this is The director/engineer class creates a CarRace,
 * using builder class that is defined (CarRaceBuilder)
 *
 * @see CarRaceBuilder
 * @version 1.0
 * @author Avihay D Hemo
 */
public class CarRaceEngineer {
    // Fields:
    private CarRaceBuilder carRaceBuilder;

    // Constructor:
    public CarRaceEngineer(CarRaceBuilder carRaceBuilder) {
        this.carRaceBuilder = carRaceBuilder;
    }

    /**
     * This function is made to helps construct a CarRace,
     * basically it just execute the methods specific to the CarRaceBuilder.
     * after finishing to construct the CarRace we return the CarRace land.
     *
     * @param numOfCars is the number of cars in the car race decide by the user input.
     * @return the land of the car race.
     * @throws RacerTypeException it may be that the TYPE is not right for the arena.
     * @throws RacerLimitException it may be that there are no space for more racers.
     */
    public Arena constructCarRace(int numOfCars) throws RacerTypeException, RacerLimitException {
        carRaceBuilder.buildRaceArena(numOfCars);
        carRaceBuilder.buildRaceCars(numOfCars);
        return this.carRaceBuilder.getCarRace().getArena();
    }

    /**
     * This is a Getter to get access to this CarRaceBuilder instance,
     * @return this CarRaceBuilder instance.
     */
    public CarRaceBuilder getCarRaceBuilder() {
        return carRaceBuilder;
    }
}
