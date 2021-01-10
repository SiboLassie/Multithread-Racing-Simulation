package factory;

import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.land.Car;
import java.lang.reflect.InvocationTargetException;

/**
 * Builder Design Pattern:
 * a concrete builder class that assembles the parts of the finished car race.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class CarRaceBuilder {
    // Fields:
    private final CarRace carRace;

    // Constructor:
    public CarRaceBuilder() {
        this.carRace = new CarRace();
    }

    /**
     * This function is for building the car race arena,
     * using Factory design pattern to make an arena,
     * then set it to the CarRace class.
     *
     * @see CarRace
     * @param numOfCars is how many cars decide by user input.
     */
    public void buildRaceArena(int numOfCars) {
        // initial a factory instance for making the arena.
        ArenaFactory factory = new ArenaFactory();
        Arena tempArena = null;

        try {
            // try to make the arena using the factory
            tempArena = factory.makeArena(RacingClassesFinder.getInstance().getArenasList().get(1),1000, numOfCars);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace(); }
        // if it all gos well we set it to the Car race.
        this.getCarRace().setLand(tempArena);
    }

    /**
     * Building the CarRace cars racers,
     * using Prototype design pattern to make many cars instances,
     * basically what's this function do, is make one Car and then make many clones of it,
     * then add all of them to the CarRace class.
     *
     * @see CarRace
     * @param numOfCars is how many cars, decide by user input.
     * @throws RacerTypeException it may be that the TYPE is not right for the arena.
     * @throws RacerLimitException it may be that there are no space for more racers.
     */
    public void buildRaceCars(int numOfCars) throws RacerTypeException, RacerLimitException {
        // initial a Prototype instance for making clones.
        Prototype copyRacer = new Prototype();
        // making a car and doing the same again to make many clones as needed.
        for (int i = 0; i < numOfCars; i++) {
            Racer car = new Car();
            // prototype process
            Racer clonedRacer = copyRacer.getClone(car);
            clonedRacer.setSerialNumber(i + 1);
            clonedRacer.setMyName("Car #" + clonedRacer.getSerialNumber());
            // adding the car to the arena & to the car race build.
            this.getCarRace().getArena().addRacer(clonedRacer);
            this.carRace.addCars(clonedRacer);
        }
    }

    /**
     * This is a Getter to get this CarRace class instance,
     * @return this CarRace class instance.
     */
    public CarRace getCarRace() {
        return carRace;
    }
}
