package factory.state;

/**
 * Interface for our State Design Pattern
 * this interface is for the Racer State.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public interface RacerState {

    /**
     * this function helps make each state update specifically to the occasion.
     */
    void updateState();

}
