package factory.state;
import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * State Design Pattern:
 * this class is for Disabled State, implementing RacerState interface.
 * taking care of the racer when he is disabled.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class DisabledState implements RacerState {
    // Fields:
    private Racer tempRacer;
    private Arena tempArena;

    //Constructor:
    public DisabledState(Racer tempRacer, Arena tempArena) {
        this.tempRacer = tempRacer;
        this.tempArena = tempArena;
    }

    /**
     * an override function from RacerState interface,
     * taking care specifically on the disabled state of the racer,
     * also remove the observer because the racer disabled means he died and cannot notify anymore.
     */
    @Override
    public void updateState() {
        // each racer that destroyed we check if there are more racing to define this arena state,
        if (this.tempArena.getActiveRacers().size() == 0 && this.tempArena.getBrokenRacers().size() == 0) {
            this.tempArena.setActive(false);
        }
        //if so remove it from active racers list and add it to disabled racers list
        this.tempArena.getActiveRacers().remove(tempRacer);
        this.tempArena.getBrokenRacers().remove(tempRacer);
        this.tempArena.getDisabledRacers().add(tempRacer);
        this.tempArena.update(tempRacer, EnumContainer.RacerEvent.DISABLED);
        //delete observer
        tempRacer.deleteObserver(tempArena);
    }
}
