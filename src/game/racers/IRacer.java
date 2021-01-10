package game.racers;

/**
 * Prototype Design Pattern:
 * This is an Interface for Racer Class made to help us use Prototype design pattern
 *  this class is derived from Cloneable so we can makes copes of objects.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public interface IRacer extends Cloneable{

    /**
     * This function helps to make a clone.
     * @return the clone we making.
     */
    Racer makeCopy();

    /**
     * this is a function made help us introduce a racer.
     */
    void introduce();

}
