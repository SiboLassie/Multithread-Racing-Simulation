package factory.state;
import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * State Design Pattern:
 * this class is for Broken State, implementing RacerState interface.
 * taking care of the racer when he is broken.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class BrokenState implements RacerState {
    // Fields:
    private long brokeTime;
    private Racer tempRacer;
    private Arena tempArena;

    // Constructor:
    public BrokenState(Racer racer, Arena arena) {
        this.tempRacer = racer;
        this.tempArena = arena;
        this.brokeTime = (tempArena.getBrokeTime() - tempArena.getStartTime());
    }

    /**
     * an override function from RacerState interface,
     * taking care specifically on the broken state of the racer,
     * also display the time that the racer broke (relative to the start of the race).
     */
    @Override
    public void updateState() {
        System.out.printf("Racer has been broke on: %d MilliSeconds - (relative to the start of the race) %n", this.brokeTime);
        //remove racer from active racers list and add it to broken racers list,
        this.tempArena.getActiveRacers().remove(tempRacer);
        this.tempArena.getBrokenRacers().add(tempRacer);
        this.tempArena.update(tempRacer, EnumContainer.RacerEvent.BROKENDOWN);
    }
}
