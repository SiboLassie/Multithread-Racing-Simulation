package utilities;

/**
 * This Class is an Enum Container for all the useful Enums we have!!
 *  it stores different kinds of useful enums,
 *   then we can have access to them.
 *
 *  @version 1.0
 *  @author Avihay D Hemo
 */
public class EnumContainer {

    public static enum RacerEvent {
        FINISHED, BROKENDOWN, REPAIRED, DISABLED
    }

    /**
     * This is an Enum for the Vision of the Arena
     */
    public static enum Vision {
        CLOUDS,SUNNY,FOG
    }

    /**
     * This is an Enum for the Weather of the Arena
     */
    public static enum Weather {
        DRY,RAIN,SNOW
    }

    /**
     * This is an Enum for the Height of the Arena
     */
    public static enum Height {
        LOW,MEDIUM,HIGH
    }

    /**
     * This is an Enum for the Wind of the Arena
     */
    public static enum Wind {
        LOW,MEDIUM,HIGH
    }

    /**
     * This is an Enum for the Color of the Racer
     */
    public static enum Color {
        RED,GREEN,BLUE,BLACK,YELLOW
    }

    /**
     * This is an Enum for the Engine of some of the Racers
     */
    public static enum Engine {
        FOURSTROKE,VTYPE,STRAIGHT,BOXER,ROTARY
    }

    /**
     * This is an Enum for the Bicycle Type of some of the Racers
     */
    public static enum BicycleType {
        MOUNTAIN,HYBRID,CRUISER,ROAD
    }

    /**
     * This is an Enum for the Boat Type of some of the Racers
     */
    public static enum BoatType {
        SKULLING,SWEEP
    }

    /**
     * This is an Enum for the Breed of some of the Racers
     */
    public static enum Breed {
        THOROUGHBRED,STANDARDBRED,MORGAN,FRIESIAN
    }

    /**
     * This is an Enum for the Team of some of the Racers
     */
    public static enum Team {
        SINGLE,DOUBLE,QUAD,EIGHT
    }

    /**
     * This is an Enum for the Water of the Arena
     */
    public static enum Water {
        SALTED,SWEET
    }

    /**
     * This is an Enum for the Water Surface of the Arena
     */
    public static enum WaterSurface {
        FLAT,WAVY
    }

    /**
     * This is an Enum for the Land Surface of the Arena
     */
    public static enum LandSurface {
        FLAT,MOUNTAIN
    }

    /**
     * This is an Enum for the Water Body of the Arena
     */
    public static enum Body {
        SEA,LAKE,RIVER,OCEAN
    }

    /**
     * This is an Enum for the Coverage of the Arena
     */
    public static enum Coverage {
        SAND,GRASS,MUD
    }
}
