package game.racers;
import factory.Observer;
import factory.state.*;
import game.Point;
import game.arenas.Arena;
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.Bicycle;
import game.racers.land.Car;
import game.racers.land.Horse;
import game.racers.land.Wheeled;
import game.racers.naval.RowBoat;
import game.racers.naval.SpeedBoat;
import utilities.EnumContainer;
import utilities.Fate;
import utilities.Mishap;
import javax.swing.*;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * This is an Abstract Racer Class made to race in the All Arenas for the game we build
 *  this class keeps the racer attributes like a racerName, a currLocation, maxSpeed, currSpeed
 *   and store that unique data for each racer so we can use them as individual racers
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public abstract class Racer extends Observable implements Runnable , IRacer, IDecoratedRacer {

    //Fields:
    private int serialNumber;
    private String racerName;
    private Point currentLocation;
    private Point finish;
    private Arena arena;
    private double maxSpeed;
    private double acceleration;
    private double currentSpeed;
    private double failureProbability = 0.05;
    private EnumContainer.Color color;
    private static int i = 1;
    private Mishap mishap = null;
    private JLabel myIcon;
    private Wheeled numOfMyWheels;
    private RacerState state;
    private ArrayList<Observer> observers;
    private int racerRank = 1;

    //Constructors:
    /**
     * Default Constructor.
     * set the serial number.
     */
    public Racer() {
        this.observers = new ArrayList<>();
        this.setSerialNumber(i++);
    }

    /**
     * This is the class nonDefault constructor to initial an instance,
     * first we set the racer racerName,
     * then we set a MaxSpeed & Acceleration for the racer by the user.
     * finally we set Color by user.
     *
     * @param racerName    the name of the racer (the instance).
     * @param maxSpeed     the max speed set by user.
     * @param acceleration the acceleration set by user.
     * @param color        the color set by user.
     */
    public Racer(String racerName, double maxSpeed, double acceleration, EnumContainer.Color color) {
        this.setMyName(racerName);
        this.setMaxSpeed(maxSpeed);
        this.setAcceleration(acceleration);
        this.setColor(color);
        this.setCurrentLocation( new Point( 0,0 ) );
        this.setSerialNumber(i++);
        this.setState(new ActiveState(this, this.arena));
    }

    //Method:
    /**
     * This function is a part of the Observer Design pattern:
     * this function purpose is to add an observer to our observers list.
     *
     * @param newObserver this is the observer we need to add.
     */
    public void registerObserver(factory.Observer newObserver) {
        observers.add(newObserver);
    }

    /**
     * This function is a part of our Observer Design pattern:
     * this function purpose is just to remove an observer from our observers list.
     *
     * @param deleteObserver this is the observer we need to remove.
     */
    public void unregisterObserver(factory.Observer deleteObserver) {
        observers.remove(deleteObserver);
    }

    /**
     * This function is part of our Observer Design pattern:
     *  this function purpose is to take all of our observers and notify each one of them.
     * @param msg is message we sent to identify between different observers.
     */
    private void notifyObserver(String msg) {
        for (factory.Observer ob : observers) {
            ob.notifyMsg(this ,msg);
        }
    }

    /**
     * This function is part of our State Design pattern:
     *  basically we take our state and use updateState function to update each state whatever it is.
     */
    private synchronized void updateState() {
        this.state.updateState();
    }

    /**
     * This function is part of our Prototype Design pattern:
     * This is an override function that we derived from Prototype (Cloneable).
     *  bassicaly its help us create a clone of this class objects.
     *
     * @return racerObject is a cloned of this class object.
     */
    @Override
    public Racer makeCopy() {
        // first we making an instance if this class,
        Racer racerObject = null;
        try {
            //then we try to use clone function of Cloneable (our super class) and cast it to a Racer object.
            racerObject = (Racer) super.clone();
        } catch (CloneNotSupportedException e) { e.printStackTrace(); }
        // if its succeed we return the cloned racer.
        return racerObject;
    }

    /**
     * This function is part of our Decorator Design pattern:
     * This is an override function derived from IDecoratedRacer
     *  this function purpose is to select the right action depend of the msg,
     *   because there are more than one decorated classes we need to take care of them on different way,
     *    we take care of ot here.
     * @param key a message we get to define the action to perform.
     * @param value the value we need to assign.
     */
    @Override
    public void addAttribute(String key, Object value) {
        try {
            // checking the message type
            if (key.equals( "color" )) {
                // and taking care of the action
                this.setColor( (EnumContainer.Color) value );

            } else if (key.equals( "wheeled" )) {

                this.setNumOfMyWheels( (Wheeled) value );

            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * This function is part of our Observer Design pattern:
     * this is a run function of this observable class
     * basically we make a move and set the state and update the observer of current state
     * by notify observer and we stopped after the racer current location is above arena length!
     *
     * This function purpose is to make a move for each racer
     * first we check if there is a mishap and if so if it fixable?
     * then we check if the current speed (CS from now on) of the racer is not at the top speed
     * if so we set the CS of the racer to (CS += acceleration * friction)
     * then we check if the CS is more then the maximum speed
     * if so we set the CS to the max speed
     * then we actually make a "Move" set the currentLocation of the racer (currentLocation += CS)
     *
     */
    @Override
    public synchronized void run() {
        while ((this.getCurrentLocation().getX() < this.arena.getLength()) ) {
            try {
                //we check if there is a mishap and if so
                // this is if we Disabled can not move anymore.
                if (this.mishap != null) {
                    if (!this.mishap.isFixable()) {
                        // we set the racer state cause of the mishap.
                        this.setState(new DisabledState(this, this.arena));
                        // not forgetting to update the observers.
                        this.updateState();
                        System.out.println("The Racer " + this.getMyName() + " is Disabled. ");
                        break;
                    }// if it fixable? also that the turn to fix is > 0,
                    else if (this.mishap.isFixable() && this.mishap.getTurnsToFix() > 0) {
                        // after that we reduce the turns to fix the mishap,
                        this.mishap.nextTurn();
                    }
                    //we check if the current speed (CS from now on) of the racer is not at the top speed,
                    if (this.currentSpeed <= this.maxSpeed) {
                        //if so we set the CS of the racer with default acceleration.
                        this.currentSpeed += this.acceleration * this.arena.getFRICTION();
                        //we check if the CS is more then the maximum speed,
                        if (this.currentSpeed > this.maxSpeed)
                            //if so we take care of it and set the CS to the max speed,
                            this.currentSpeed = this.maxSpeed;
                    }
                    // update the current location of the racer,
                    this.notifyObserver("make a move");

                    // if we finish to wait the mishap is over and we are ready to go on!
                    if (this.mishap.getTurnsToFix() == 0) {
                        this.setMishap(null);
                        this.setState(new ActiveState(this, this.arena));
                        this.updateState();
                    }
                } else {
                    // This is all 'else block' is the same as above but,
                    // with one different here we Generate a new mishap by the fate!
                    // Generate a new mishap by the fate of the racer,
                    if (Fate.breakDown(this.failureProbability)) {
                        this.setMishap(Fate.generateMishap());
                        System.out.println(this.getMyName() + " Has a new mishap!" + mishap);
                        this.getArena().setBrokeTime(System.currentTimeMillis());
                        this.setState(new BrokenState(this, this.arena));
                        this.updateState();
                    }
                    // now acting normal with regular acceleration.
                    if (this.currentSpeed <= this.maxSpeed) {
                        this.currentSpeed += this.acceleration * this.arena.getFRICTION();
                        if (this.currentSpeed > this.maxSpeed) {
                            this.currentSpeed = this.maxSpeed;
                        }
                    }
                    //update current location,
                    this.notifyObserver("make a different move");
                }
                // here we check if the racer passed the finish line:
                if (this.getCurrentLocation().getX() >= this.arena.getLength()) {
                    // if so we update its status as finished,
                    this.setState(new CompletedState(this, this.arena));
                    this.updateState();
                } else {
                    // update icon location
                    if (this.getCurrentLocation().getX() < this.arena.getLength()) {
                        this.getMyIcon().setLocation((int) this.getCurrentLocation().getX(), this.getMyIcon().getY());
                        this.getMyIcon().updateUI();
                    }
                }
                if (this.getCurrentLocation().getX() >= this.arena.getLength()-205) {
                    this.getMyIcon().setLocation((int) this.arena.getLength()-205, this.getMyIcon().getY());
                    break;
                }
                // after each movement of a racer we sent it to sleep for a 100 millis
                sleep( 100 );

            } catch (NullPointerException | InterruptedException | IllegalStateException e) {
                continue;
            }
        }
        // here we check if the racer passed the finish line
        if (this.arena.getLength() >= 1000) {
            if (this.getCurrentLocation().getX() >= this.arena.getLength()-205) {
                // if so we update its status as finished,
                this.currentLocation.setX( this.getCurrentLocation().getX() + 205 );
                this.setState(new CompletedState(this, this.arena));
                this.updateState();
            } else if (this.getCurrentLocation().getX() >= this.arena.getLength()) {
                // if so we update its status as finished,
                this.currentLocation.setX( this.getCurrentLocation().getX() + 205 );
                this.setState(new CompletedState(this, this.arena));
                this.updateState();
            }
        } else if (this.getCurrentLocation().getX() >= this.arena.getLength()-205) {
            // if so we update its status as finished,
            this.setState(new CompletedState(this, this.arena));
            this.updateState();
            this.currentLocation.setX( this.getCurrentLocation().getX() + 205 );
        }
    }

    /**
     * This function purpose is to initial the race
     * first we set the arena
     * then we set the finish point
     * and then we set the currentLocation to the start point
     *
     * @param arena  that's the arena the racers competing
     * @param start  the start line
     * @param finish the finish line
     */
    public void initRace(Arena arena, Point start, Point finish) {
        this.setArena(arena);
        this.setCurrentLocation(start);
        this.setFinish(finish);
    }

    /**
     * This is an abstract method that describe the specific racer details,
     *  because each racer got individual specific details,
     *   each one of them must to implement this method,
     * @return a string that describe the specific racer details.
     */
    public abstract String describeSpecific();

    /**
     * This is a method that describe the common racer details,
     *  like racerName, serial number, max speed, acceleration.
     *   each one of the racers have those basic attributes,
     * @return a string that describe the common racer details.
     */
    public String describeRacer() {
        return  "serialNumber: " + this.getSerialNumber() +
                " racerName: " + this.getMyName() +
                " maxSpeed: " + this.getMaxSpeed() +
                " acceleration: " + this.getAcceleration();
    }

    /**
     * This is a method that introduce the common racer details and hes specific ones,
     *  like racerName, serial number, max speed, acceleration...
     *   also all of the individual specific details of each racer.
     */
    public void introduce() {
        System.out.println("[" + this.className() + "] " +
                this.describeRacer() + this.describeSpecific());
    }

    /**
     * This function is made to get this racer specific class name,
     *  so whats we do is basically check which instance of this is.
     * @return a String of the current instance Class.
     */
    public String className() {
        if (this instanceof Airplane)
            return "Airplane";
        else if (this instanceof Helicopter)
            return "Helicopter";
        else if (this instanceof Car)
            return "Car";
        else if (this instanceof Bicycle)
            return "Bicycle";
        else if (this instanceof Horse)
            return "Horse";
        else if (this instanceof RowBoat)
            return "RowBoat";
        else if (this instanceof SpeedBoat)
            return "SpeedBoat";
        else
            return "unknown";
    }

    /**
     * This function is made to get this racer specific class type and convert to index,
     *  so what's we do is basically check which instance of this is.
     * @return index that represent the type of the current instance Class.
     */
    public int classIndex() {
        // we choose to map all of the types as numbers for better access.
        if (this instanceof Airplane)
            return 0;
        else if (this instanceof Helicopter)
            return 1;
        else if (this instanceof Bicycle)
            return 2;
        else if (this instanceof Car)
            return 3;
        else if (this instanceof Horse)
            return 4;
        else if (this instanceof RowBoat)
            return 5;
        else if (this instanceof SpeedBoat)
            return 6;
        else
            return 0; // default
    }

//-------------------------------------------------------------------DO NOT GO DOWN ITS BORING C&P-------------------------------------------------//
    //Getters & Setters:
    /**
     * This is a Getter to get this Racer Serial Number.
     * @return this racer serial number.
     */
    public int getSerialNumber() {
        return serialNumber;
    }
    /**
     * This is a Setter to set this Racer Serial Number.
     * @param serialNumber a racer serial number.
     */
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
    /**
     * This is a Getter to get this Racer racerName.
     * @return this racer serial number.
     */
    public String getMyName() {
        return racerName;
    }
    /**
     * This is a Setter to set this Racer racerName.
     * @param name a racer racerName.
     */
    public void setMyName(String name) {
        this.racerName = name;
    }
    /**
     * This is a Getter to get this Racer Current Location.
     * @return this racer current location.
     */
    public Point getCurrentLocation() {
        return currentLocation;
    }
    /**
     * This is a Setter to set this Racer Current Location.
     * @param currentLocation a racer Current Location.
     */
    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }
    /**
     * This is a Getter to get this Racer Finish Point.
     * @return this racer finish point.
     */
    public Point getFinish() {
        return finish;
    }
    /**
     * This is a Setter to set this Racer Finish Point.
     * @param finish a racer Finish Point.
     */
    public void setFinish(Point finish) {
        this.finish = finish;
    }
    /**
     * This is a Getter to get this Racer Current Arena.
     * @return this racer current arena.
     */
    public Arena getArena() {
        return arena;
    }
    /**
     * This is a Setter to set this Racer Current Arena.
     * @param arena a racer current arena.
     */
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    /**
     * This is a Getter to get this Racer Max Speed.
     * @return this racer max speed.
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }
    /**
     * This is a Setter to set this Racer Max Speed.
     * @param maxSpeed a racer Max Speed.
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    /**
     * This is a Getter to get this Racer Acceleration.
     * @return this racer acceleration.
     */
    public double getAcceleration() {
        return acceleration;
    }
    /**
     * This is a Setter to set this Racer Acceleration.
     * @param acceleration a racer acceleration.
     */
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
    /**
     * This is a Getter to get this Racer Color.
     * @return this racer color.
     */
    public EnumContainer.Color getColor() {
        return color;
    }
    /**
     * This is a Setter to set this Racer Color.
     * @param color a racer color.
     */
    public void setColor(EnumContainer.Color color) {
        this.color = color;
    }
    /**
     * This is a Setter to set this Racer Potential Mishaps.
     * @param mishap this racer potential mishap.
     */
    private void setMishap(Mishap mishap) {
        this.mishap = mishap;
    }
    /**
     * This is a Getter to get this Racer Potential Mishaps.
     * @return  mishap this racer potential mishap.
     */
    public Mishap getMishap() {
        return mishap;
    }
    /**
     * This is a Getter to get this Racer current speed.
     * @return this racer current speed.
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }
    /**
     * This is a Getter to get this Racer icon.
     * @return this racer icon.
     */
    public JLabel getMyIcon() {
        return myIcon;
    }
    /**
     * This is a Setter to set this Racer Icon.
     * @param myIcon this racer icon.
     */
    public void setMyIcon(JLabel myIcon) {
        this.myIcon = myIcon;
    }
    /**
     * This function is increment I for our racers counting.
     */
    public  static void incI() {
        i++;
    }
    /**
     * this function is reset I for our racers counting.
     */
    public static void resetI() {
        i = 1;
    }
    /**
     * This is a Getter to get this Racer state.
     * @return this racer state.
     */
    public RacerState getState() {
        return state;
    }
    /**
     * This is a Setter to set this Racer state.
     * @param state this racer current state.
     */
    public void setState(RacerState state) {
        this.state = state;
    }
    /**
     * This is a Getter to get this Racer rank.
     * @return this racer rank.
     */
    public int getRacerRank() {
        return racerRank;
    }
    /**
     * This is a Setter to set this Racer rank.
     * @param racerRank this racer rank.
     */
    public void setRacerRank(int racerRank) {
        this.racerRank = this.getSerialNumber();
    }
    /**
     * This is a Getter to get this class Num of Wheels.
     * @return we get the num of wheels.
     */
    protected int getNumOfMyWheels() {
        return numOfMyWheels.getNumOfWheels();
    }
    /**
     * This function help us know if the racer is wheeled or not
     * @return True or False
     */
    public boolean isWheeled() {
        return this.numOfMyWheels != null;
    }
    /**
     * This is a Setter to set this class Num of Wheels.
     * @param numOfMyWheels we can pass how many wheels we want.
     */
    protected void setNumOfMyWheels(Wheeled numOfMyWheels) {
        this.numOfMyWheels = numOfMyWheels;
    }
}
