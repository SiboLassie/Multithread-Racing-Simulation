package game.racers;

/**
 * Decorator Design Pattern:
 * This is like a blueprint for classes that will have decorators
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public interface IDecoratedRacer {

    /**
     * this function purpose is to helping us decorate the Racer.
     * @param key the key to decoration type.
     * @param value the value of the specific decorate.
     */
    void addAttribute(String key, Object value);

}
