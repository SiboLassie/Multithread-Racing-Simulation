package game.arenas;
import factory.state.ActiveState;
import game.Point;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import utilities.EnumContainer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is an Abstract class for the Arenas for the game we build
 *  This class is a super class for more Arenas and this class purpose is to be basic Arena class
 *  basically its got some function of the fields that common to all other arenas.
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Racer
 */
public abstract class Arena implements Observer {

    //Fields:
    protected ArrayList<Racer> activeRacers;
    private ArrayList<Racer> completedRacers;
    private ArrayList<Racer> brokenRacers;
    private ArrayList<Racer> disabledRacers;
    private final double FRICTION;
    private final int MAX_RACERS;
    private final static int MIN_Y_GAP = 10;
    private double length;
    private boolean isActive = false;
    private long startTime;
    private long brokeTime;


    //Constructor:
    /**
     * This is the Class Constructor here we initialize the class object,
     *  first we set the friction the the max racers
     *   then we set the length and finally the arrays for the racers
     * @param length the length of the arena
     * @param MAX_RACERS how many racers can join this arena
     * @param FRICTION the friction of this arena
     */
    public Arena(double length, int MAX_RACERS, double FRICTION) {
        this.FRICTION = FRICTION;
        this.MAX_RACERS = MAX_RACERS;
        this.setLength(length);
        this.setActiveRacers(new ArrayList<Racer>());
        this.setCompletedRacers(new ArrayList<Racer>());
        this.setBrokenRacers(new ArrayList<Racer>());
        this.setDisabledRacers(new ArrayList<Racer>());
    }
    //Methods:
    /**
     * this function purpose is to start the race on this arena,
     * we use here Executor Service, first we create a fixed thread pool by max racers number,
     * then we add all of the racers (observables) to observer (this),
     * than we loop in the active racers list and run each racer and repaint icons,
     * after the loop done we shutdown the pool,
     * finally we check if there are no more racers racing and mark as not active arena.
     */
    public synchronized void startRace() {
        // creating a thread pool using executor service.
        ExecutorService Deadpool = Executors.newFixedThreadPool(this.MAX_RACERS);

        ArrayList<Racer> racers = new ArrayList<Racer>();
        // loop to add each observable, and make a copy of our racers list for iterating.
        for (Racer racer : this.activeRacers) {
            racer.addObserver(this);
            racers.add(racer);
            racer.setState(new ActiveState(racer, this));
        }
        this.initRace();

        // set the current arena as active.
        if (this.getActiveRacers().size() != 0) {
            this.setActive(true);
        }
        // setting a relative starting time for the beginning of the race
        this.startTime = System.currentTimeMillis();

        // making each racer run, and update icons.
        for (Racer racer : racers) {
            try {
                if (racer.getState() instanceof ActiveState) {
                    // execute each racer, make them use their run function.
                    Deadpool.execute(racer);
                    // displaying, this is the part that makes you see them actually move on the background.
                    racer.getMyIcon().repaint();
                    racer.getMyIcon().updateUI();
                }
            } catch (NullPointerException | IllegalStateException e) {
                continue;
            }
        }
        // shutdown the pool.
        Deadpool.shutdown();
        // set the current state of the race.
        if (this.getActiveRacers().size() == 0) {
            this.setActive(false);
        }
    }

    /**
     * this function is an override function of observer that get updates from observable
     * each time the racer is changing hes state like finish the race or got a mishap we update it
     * we take care of each case separately and change the observable state
     *
     * @param o is the observable that is updating.
     * @param arg is a parameter we sent to recognize the case we are in.
     */
    @Override
    public synchronized void update(Observable o, Object arg) {
        Racer tempRacer = (Racer) o;
        //first we check this racer state if he finish,
        if (EnumContainer.RacerEvent.FINISHED.equals(arg)) {
            System.out.println("Finished!! =) ");
            //than we delete it from this (us) observer
            tempRacer.deleteObserver(this);

        // check if the racer is broken down
        } if (EnumContainer.RacerEvent.BROKENDOWN.equals(arg)) {
            //if so we remove it from active racers list and add it to broken racers list,
            System.out.println("I Broke.. =( ");

            // check if the racer is repaired,
        } if (EnumContainer.RacerEvent.REPAIRED.equals(arg)) {
            System.out.println("Repaired! =) ");

            // check if the racer explode/destroyed/died in the race,
        } if (EnumContainer.RacerEvent.DISABLED.equals(arg)) {
            System.out.println("Destroyed.. X( ");
            //than we delete it from this (us) observer
            tempRacer.deleteObserver(this);
        }
        // double check: if there are more racing to define this arena state,
        if (this.getActiveRacers().size() == 0) {
            this.setActive(false);
        }
    }

    /**
     * This function purpose is to add more racers to the arena,
     * its an Abstract function, all of the arenas must implement it.
     *
     * @param newRacer an instance of racer class.
     * @throws RacerLimitException throws an Exception if there is no space for racers.
     * @throws RacerTypeException throws an Exception if the type of a racer not match to the arena.
     */
    public abstract void addRacer(Racer newRacer) throws RacerTypeException, RacerLimitException;

    /**
     * This function is made to initialize the race we build,
     * basically we run on the active racers array and initial each racer.
     */
    public void initRace() {
        // init the active racers array.
        for (Racer racer: this.getActiveRacers()) {
            // we set the Y and calc it by: space between racers and amount of them from 0 to first.
            Point tmp = new Point(this.getLength(), getMinYGap() - this.getActiveRacers().size());
            // then we call initRace in each racer and set its start and finish points.
            racer.initRace(this,new Point(0,0), tmp);
        }
    }

    /**
     * This function purpose id to check if there are still active racers in the race.
     * @return check if the array is not empty, return boolean that's fit the situation.
     */
    public boolean hasActiveRacers() {
        return activeRacers.size() != 0;
    }

    /**
     * This function purpose is to display the result of the race,
     *  we go on the completed racers array and display each one of them.
     */
    public void showResults() {
        int i =0;
        for(Racer racer : this.getCompletedRacers()) {
            if (racer == null) {
                continue;
            }
            System.out.println("#" + i++ + "-> " + racer.describeRacer() + racer.describeSpecific());
        }
        for(Racer racer : this.getDisabledRacers()) {
            System.out.println("Disabled: "+"#" + i++ + "-> " + racer.describeRacer() + racer.describeSpecific());
        }
    }

    // Getters & Setters:
    /**
     * This is a function to check if this arena is active,
     *  means there is a race in process,
     * @return True or False depend the situation.
     */
    public boolean isActive() {
        return isActive;
    }
    /**
     * This is a Setter to set this active state.
     */
    public void setActive(boolean active) {
        isActive = active;
    }
    /**
     * This is a Getter to get this broken racers array list,
     * @return this broken racers array list.
     */
    public ArrayList<Racer> getBrokenRacers() {
        return brokenRacers;
    }
    /**
     * This is a Setter to set this broken racers array list.
     */
    private void setBrokenRacers(ArrayList<Racer> brokenRacers) {
        this.brokenRacers = brokenRacers;
    }
    /**
     * This is a Getter to get this disabled racers array list,
     * @return this disabled racers array list.
     */
    public ArrayList<Racer> getDisabledRacers() {
        return disabledRacers;
    }
    /**
     * This is a Setter to set this disabled racers array list.
     */
    private void setDisabledRacers(ArrayList<Racer> disabledRacers) {
        this.disabledRacers = disabledRacers;
    }
    /**
     * This is a Getter to get this active racers array list,
     * @return this active racers array list.
     */
    public ArrayList<Racer> getActiveRacers() {
        return activeRacers;
    }
    /**
     * This is a Setter to set this active racers array list.
     */
    private void setActiveRacers(ArrayList<Racer> activeRacers) {
        this.activeRacers = activeRacers;
    }
    /**
     * This is a Getter to get this completed racers array list,
     * @return this completed racers array list.
     */
    public ArrayList<Racer> getCompletedRacers() {
        return completedRacers;
    }
    /**
     * This is a Setter to set this completed racers array list.
     */
    private void setCompletedRacers(ArrayList<Racer> completedRacers) {
        this.completedRacers = completedRacers;
    }
    /**
     * This is a Getter to get the arena max racers value,
     * @return this arena max racers value.
     */
    public int getMAX_RACERS() {
        return MAX_RACERS;
    }
    /**
     * This is a Getter to get the arena min gap value,
     * @return this arena min gap value.
     */
    private static int getMinYGap() {
        return MIN_Y_GAP;
    }
    /**
     * This is a Getter to get the arena length value,
     * @return this arena length value.
     */
    public double getLength() {
        return length;
    }
    /**
     * This is a Setter to set the arena length value.
     */
    private void setLength(double length) {
        this.length = length;
    }
    /**
     * This is a Getter to get the arena FRICTION value,
     * @return this arena friction value.
     */
    public double getFRICTION() {
        return FRICTION;
    }
    /**
     * This is a Getter to get the arena start time of the race,
     * @return the start time of a race.
     */
    public long getStartTime() {
        return startTime;
    }
    /**
     * This is a Getter to get the arena broke time of a racer,
     * @return the broke time of a racer.
     */
    public long getBrokeTime() {
        return brokeTime;
    }

    /**
     * This is a Setter to set this arena broke time of a racer,
     * @param brokeTime of a racer.
     */
    public void setBrokeTime(long brokeTime) {
        this.brokeTime = brokeTime;
    }
}

