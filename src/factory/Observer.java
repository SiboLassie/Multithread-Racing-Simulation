package factory;
import game.racers.Racer;

/**
 * Observer Design Pattern:
 * The Observers notifyMsg method is called when the Subject changes
 *
 * @see GUI
 * @version 1.0
 * @author Avihay D Hemo
 */
public interface Observer extends java.util.Observer {

    /**
     * this function is called when the Subject (Racer) changes
     * @param racer the observable
     * @param msg the message the define the change.
     */
    void notifyMsg(Racer racer, String msg);

}
