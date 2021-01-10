package factory;
import game.arenas.Arena;
import game.racers.Racer;

/**
 * Builder Design Pattern:
 * This is the interface that will be returned from the builder.
 *
 * @see CarRaceBuilder
 * @see CarRace
 * @version 1.0
 * @author Avihay D Hemo
 */
public interface CarRacePlan {

    /**
     * a Setter to set the land
     * @param arena the land.
     */
    void setLand(Arena arena);

    /**
     * Add cars to the race.
     * @param racer is the car to add.
     */
    void addCars(Racer racer);
}
