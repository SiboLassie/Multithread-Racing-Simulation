package factory.state;
import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * State Design Pattern:
 * this class is for Complete State, implementing RacerState interface.
 * taking care of the racer when he is complete.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class CompletedState implements RacerState {
    // Fields:
    private Racer tempRacer;
    private Arena tempArena;
    private static int racerRank;

    // Constructor:
    public CompletedState(Racer tempRacer, Arena tempArena) {
        this.tempRacer = tempRacer;
        this.tempArena = tempArena;
        racerRank = 1;
    }

    /**
     * an override function from RacerState interface,
     * taking care specifically on the complete state of the racer,
     * also remove the observer (racer finished so no need to notify anymore).
     */
    @Override
    public void updateState() {
        //each racer that finish, check if there are more racers to define this arena state.
        if (this.tempArena.getActiveRacers().size() == 0) {
            this.tempArena.setActive(false);
        }
        //if so remove it from active racers list and add it to completed racers list.
        if (!tempArena.getCompletedRacers().contains(tempRacer)) {
            this.tempArena.getActiveRacers().remove(tempRacer);
            this.tempArena.getCompletedRacers().add(tempRacer);
            this.tempArena.update(tempRacer, EnumContainer.RacerEvent.FINISHED);
            //delete observer.
            tempRacer.deleteObserver(tempArena);
        } else {
            this.tempArena.getActiveRacers().remove(tempRacer);
        }
        this.tempArena.getBrokenRacers().remove(tempRacer);
    }
}
