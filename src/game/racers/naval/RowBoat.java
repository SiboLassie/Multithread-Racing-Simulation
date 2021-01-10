package game.racers.naval;
import game.racers.Racer;
import utilities.EnumContainer;

/**
 * This is a racer class made to race in the NavalArena for the game we build
 *  basically its set an rowBoat racer that derived from Racer class and extents it
 *  this class inherit from Racer class a racerName, a currLocation, maxSpeed, currSpeed
 *   and store that unique data for each racer so we can use them as individual racers
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see SpeedBoat
 */
public class RowBoat extends Racer implements NavalRacer{

    //Fields:
    private EnumContainer.BoatType type;
    private EnumContainer.Team team;

    //Getters & Setters:
    /**
     * This is a Getter to get this boat Type.
     * @return this boat type.
     */
    public EnumContainer.BoatType getType() {
        return type;
    }

    /**
     * This is Setter to set this boat Type.
     * @param type this boat type.
     */
    public void setType(EnumContainer.BoatType type) {
        this.type = type;
    }

    /**
     * This is a Getter to get this boat Team.
     * @return this boat team.
     */
    private EnumContainer.Team getTeam() {
        return team;
    }

    /**
     * This is a Setter to set this boat team.
     * @param team this boat team.
     */
    private void setTeam(EnumContainer.Team team) {
        this.team = team;
    }

    /**
     * This is an overLoaded method that describe the specific racer details,
     * @return a string that describe the specific racer details.
     */
    @Override
    public String describeSpecific() {
        return  " color: " + super.getColor() +
                " Type: " + this.getType() +
                " Team: " + this.getTeam();
    }

    //Constructors:
    /**
     * This is the class default constructor to initial an instance
     *  first we set the racerName rowBoat as default 'RowBoat' with a serial number
     *   then we set a default MaxSpeed & Acceleration for rowBoat (75, 10)
     *    finally we set default Color and Team and type for rowBoat (RED, DOUBLE, SKULLING)
     */
    public RowBoat() {
        super();
        super.setMyName("RowBoat #" + super.getSerialNumber());
        super.setMaxSpeed(75);
        super.setAcceleration(10);
        super.setColor(EnumContainer.Color.RED);
        this.setTeam(EnumContainer.Team.DOUBLE);
        this.setType(EnumContainer.BoatType.SKULLING);
    }

    /**
     * This is the class nonDefault constructor to initial an instance,
     *  first we set the racer racerName,
     *   then we set a MaxSpeed & Acceleration for the racer by the user.
     *    finally we set Color by user.
     *
     * @param name the racerName of the racer (the instance).
     * @param maxSpeed the MaxSpeed set by user.
     * @param acceleration the acceleration set by user.
     * @param color the color set by user.
     */
    public RowBoat(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super();
        super.setMyName(name);
        super.setMaxSpeed(maxSpeed);
        super.setAcceleration(acceleration);
        super.setColor(color);
        this.setTeam(EnumContainer.Team.DOUBLE);
        this.setType(EnumContainer.BoatType.SKULLING);
    }
}

