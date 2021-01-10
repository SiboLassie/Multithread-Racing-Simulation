package factory;
import game.racers.Racer;

/**
 * Prototype Design Pattern:
 * The Prototype receives any Racer, or Racer subclass,
 * makes a copy of it and stores it in its own location in memory,
 * Prototype has no idea what these objects are,
 * except that they are subclasses of Racer.
 *
 * @see GUI
 * @version 1.0
 * @author Avihay D Hemo
 *
 */
class Prototype {

    /**
     * This function is just using the racer makeCopy method that derived from Cloneable.
     *
     * @see Cloneable
     * @param racerSample is the Racer we want to clone.
     * @return the cloned racer.
     */
    Racer getClone(Racer racerSample) {

        return racerSample.makeCopy();
    }
}
