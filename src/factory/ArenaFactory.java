package factory;

import game.arenas.Arena;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory Design Pattern:
 * this class is basically a factory of arenas of all types,
 * taking care of building arenas.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class ArenaFactory {

    /**
     * This Function purpose is to Build an arena to the race
     *  There are different types of arenas, uses Reflection to load each arena constructor
     *   Then we cast the instance to arena and init it with the parameters
     *    also take care of multiple exceptions that can come up
     *
     * @param arenaType that's the current arena type we gonna build
     * @param length this parameter passed to the constructor of our arena
     * @param maxRacers this parameter also passed to the constructor
     * @return an initialize Arena of our type or null if something gos wrong
     * @throws ClassNotFoundException throw exception if the class type not found
     * @throws NoSuchMethodException   also if there is no such method in the class that we get
     * @throws InstantiationException   and if there is a problem in initialing
     * @throws IllegalAccessException    and more
     * @throws InvocationTargetException   exceptions.
     */
    public Arena makeArena(String arenaType, double length, int maxRacers) throws
            ClassNotFoundException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        // using reflection method to get the right type
        Class c;
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        // getting the right class
        c = cl.loadClass(arenaType);
        // then load its constructor and initialize and return it
        Constructor con = c.getConstructor(double.class, int.class);
        return (Arena) con.newInstance(length, maxRacers);
    }
}
