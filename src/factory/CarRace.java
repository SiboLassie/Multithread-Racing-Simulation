package factory;

import game.arenas.Arena;
import game.racers.Racer;
import java.util.ArrayList;

/**
 * Builder Design Pattern:
 * this class is for building a Car Race.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class CarRace implements CarRacePlan {
    // Fields:
    private Arena arena;
    private ArrayList<Racer> cars;

    // Constructor:
    public CarRace() {
        this.cars = new ArrayList<Racer>();
    }

    /**
     * an override function to set this car race arena.
     * @param arena is the land of the race.
     */
    @Override
    public void setLand(Arena arena) {
        this.setArena(arena);
    }

    /**
     * This is a kind of a Setter to add (Set) more cars to cars list.
     * @param racer is the car racer we add.
     */
    @Override
    public void addCars(Racer racer) {
        this.cars.add(racer);
    }

    /**
     * This is a Getter to get this car race arena,
     * @return this car race arena.
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * This is a Setter to set this car race arena.
     * @param arena is the arena of the race
     */
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    /**
     * This is a Getter to get this car race cars list,
     * @return this car race cars list.
     */
    public ArrayList<Racer> getCars() {
        return cars;
    }
}
