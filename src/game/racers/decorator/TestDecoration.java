package game.racers.decorator;
import game.arenas.Arena;
import game.arenas.air.AerialArena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.IDecoratedRacer;
import game.racers.Racer;
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.Car;
import game.racers.naval.SpeedBoat;
import utilities.EnumContainer.Color;
import utilities.Fate;

public class TestDecoration {

    public static void main(String[] args) {

        // General decoration
        IDecoratedRacer  r = new WheeledRacer(new ColoredRacer(new WheeledRacer(new Car(), 2), Color.BLACK), 2);
        System.out.println(r);

        r = new SpeedBoat();
        new ColoredRacer(r, Color.YELLOW);

        System.out.println(r);

        // Full race
        System.out.println("--------------------");
        System.out.println("Race Test");
        Fate.setSeed(628279162);
        Arena a = new AerialArena();

        IDecoratedRacer r1, r2, r3, r4;
        r1 = new Airplane();
        new WheeledRacer(r1, 5);
        new ColoredRacer(r1, Color.BLUE);
        new WheeledRacer(r1, 2);
        try {
            a.addRacer( (Racer) r1 );
        } catch (RacerLimitException | RacerTypeException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        r2 = new Helicopter();
        new ColoredRacer(r2, Color.GREEN);
        try {
            a.addRacer( (Racer) r2 );
        } catch (RacerLimitException | RacerTypeException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        r3 = new Helicopter();
        new WheeledRacer(r3, 4);
        new ColoredRacer(r3, Color.YELLOW);
        try {
            a.addRacer( (Racer) r3 );
        } catch (RacerLimitException | RacerTypeException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        r4 = new Airplane();
        new ColoredRacer(r4, Color.RED);
        new ColoredRacer(r4, Color.BLACK);
        new WheeledRacer(r4, 3);
        try {
            a.addRacer( (Racer) r4 );
        } catch (RacerLimitException | RacerTypeException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        a.showResults();
        System.out.println("Start Race");
        a.startRace();
        a.showResults();
    }
}

// Strat Race
// Airplane #4 Has a new mishap! (true, 2, 0.57)
// Helicopter #2 Has a new mishap! (false, 3, 0.56)
// Airplane #1 Has a new mishap! (true, 2, 0.57)
// Helicopter #3 Has a new mishap! (true, 2, 0.03)
// Airplane #1 Has a new mishap! (true, 2, 0.73)
// Airplane #4 Has a new mishap! (true, 3, 0.25)
// Helicopter #3 Has a new mishap! (true, 4, 0.25)
// Airplane #4 Has a new mishap! (true, 4, 0.50)
// Helicopter #3 Has a new mishap! (true, 3, 0.98)
// Airplane #4 Has a new mishap! (true, 2, 0.96)
// Completed
// #1 -> name: Airplane #1, @(1500.0,0.0), SerialNumber: 1, maxSpeed: 885.0,
// acceleration: 100.0 color: [BLUE] numOfWheels: [5, 2]
// #2 -> name: Helicopter #3, @(1500.0,200.0), SerialNumber: 3, maxSpeed: 400.0,
// acceleration: 50.0 color: [YELLOW] numOfWheels: [4]
// #3 -> name: Airplane #4, @(1500.0,300.0), SerialNumber: 4, maxSpeed: 885.0,
// acceleration: 100.0 color: [RED, BLACK] numOfWheels: [3]
// Disabled
// #4 -> name: Helicopter #2, @(0.0,100.0), SerialNumber: 2, maxSpeed: 400.0,
// acceleration: 50.0 color: [GREEN]
// Broken
// Active