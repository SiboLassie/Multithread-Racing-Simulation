package game.arenas.exceptions;

/**
 * This is a custom Exception class to help manege some Exceptions
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see RacerLimitException
 */
public class RacerTypeException extends Exception {

    /**
     * This is the class Constructor here we create and initialize the Exception
     * @param message send a message to appear when the Exception is cached.
     */
    public RacerTypeException(String message) {
        // calling the super class Exception constructor
        super(message);
    }
}
