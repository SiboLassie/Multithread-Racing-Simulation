package factory.state;
import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * State Design Pattern:
 * this class is for Active State, implementing RacerState interface.
 * taking care of the racer when he is active.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class ActiveState implements RacerState {
    // Fields:
    private Racer tempRacer;
    private Arena tempArena;

    // Constructor:
    public ActiveState(Racer racer, Arena arena) {
        this.tempRacer = racer;
        this.tempArena = arena;
    }

    /**
     * an override function from RacerState interface,
     * taking care specifically on the active state of the racer,
     * also if the racer repaired and became active again.
     */
    @Override
    public void updateState() {
        System.out.println("Racer rank: " + tempRacer.getRacerRank());
        // check if the racer is active (repaired)
        if (this.tempArena.getBrokenRacers().contains(tempRacer)) {
            //if so remove it from broken racers list and add it back to active racers list,
            this.tempArena.getBrokenRacers().remove(tempRacer);
            this.tempArena.getActiveRacers().add(tempRacer);
            this.tempArena.update(tempRacer, EnumContainer.RacerEvent.REPAIRED);
        }
    }
}
