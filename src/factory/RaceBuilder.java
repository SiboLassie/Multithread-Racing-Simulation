package factory;
import game.arenas.Arena;
import game.racers.Racer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *  This Class is made to build the race
 *
 *  @version 1.0;
 *  @author Avihay D Hemo
 */
public class RaceBuilder {
    //Fields:
    private static final RaceBuilder ourInstance = new RaceBuilder();

    //Getters & Setters:
    /**
     * This is a getter to get our singleton instance
     * @return ourInstance
     */
    public static RaceBuilder getInstance() {
        return ourInstance;
    }

    //Methods:
    /**
     * This Function purpose is to build an arena to the race
     *  Because we have different types of arenas we use Reflection to load each arena constructor
     *   Then we cast the instance to arena and init it with the parameters
     *    also we take care here of multiple Exceptions that can come up
     *
     * @param arenaType the current arena type we gonna build
     * @param length this parameter passed to the constructor of our arena
     * @param maxRacers this parameter also passed to the constructor
     * @return an initialize arena of our type or null if something gos wrong
     */
    public Arena buildArena(String arenaType, double length, int maxRacers) {
        Arena tempArena = null;
        ArenaFactory factory = new ArenaFactory();
        try {
            tempArena = factory.makeArena(arenaType,length,maxRacers);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace(); }
        return tempArena;
    }

    /**
     * This Function purpose is to build a racer to the race
     *  Because we have different types of racers we use Reflection to load each racer constructor
     *   Then we cast the instance to racer and init it with the right parameters
     *    also we take care here of multiple Exceptions that can came up
     *
     * @param racerType the current racer type we gonna build
     * @param name the name of the racer
     * @param maxSpeed the max speed of the racer
     * @param acceleration the acceleration of the racer
     * @param color the color of the racer
     * @return an initialize racer of our type or null if something gos wrong
     * @throws ClassNotFoundException throw exception if the class type not found
     * @throws NoSuchMethodException   also if there is no such method in the class that we get
     * @throws InstantiationException   and if there is a problem in initialing
     * @throws IllegalAccessException    and more
     * @throws InvocationTargetException   exceptions.
     */
    public Racer buildRacer(String racerType, String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color)
            throws
            ClassNotFoundException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        // using reflection method to get the right type
        Class c;
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        // getting the right class
        c = cl.loadClass(racerType);
        // then we load its constructor and initialize and return it
        Constructor con = c.getConstructor(String.class, double.class, double.class, utilities.EnumContainer.Color.class);
        return (Racer) con.newInstance(name,maxSpeed,acceleration,color);
    }

    /**
     * This Function purpose is to build a wheeled racer to the race
     *  Because we have different types of wheeled racers we use Reflection to load each racer constructor
     *   Then we cast the instance to racer and init it with the right parameters
     *    also we take care here of multiple Exceptions that can came up
     *
     * @param racerType the current racer type we gonna build
     * @param name the racerName of the racer
     * @param maxSpeed the max speed of the racer
     * @param acceleration the acceleration of the racer
     * @param color the color of the racer
     * @param numOfWheels how many wheels this racer have
     * @return an initialize racer of our type or null if something gos wrong
     * @throws ClassNotFoundException throw exception if the class type not found
     * @throws NoSuchMethodException   also if there is no such method in the class that we get
     * @throws InstantiationException   and if there is a problem in initialing
     * @throws IllegalAccessException    and more
     * @throws InvocationTargetException   exceptions.
     */
    public Racer buildWheeledRacer(String racerType, String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color, int numOfWheels)
            throws
            ClassNotFoundException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        // using reflection method to get the right type
        Class c;
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        // getting the right class
        c = cl.loadClass(racerType);
        // then we load its constructor and initialize and return it
        Constructor con = c.getConstructor(String.class, double.class, double.class, utilities.EnumContainer.Color.class, int.class);
        return (Racer) con.newInstance(name,maxSpeed,acceleration,color, numOfWheels);
    }

    //Constructors:
    /**
     * default constructor
     */
    private RaceBuilder() { }

}
