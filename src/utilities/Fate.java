package utilities;

import java.util.Random;

/**
 * This is a most static class for Fate of the Race
 *
 * @version 1.0
 * @author Avihay D Hemo
 * @see Random
 * @see Mishap
 */
public class Fate {

    //Fields:
    private static Random rand = new Random();

    /**
     * This is a Setter to set a seed for a random,
     * @param seed its a seed for the randomization.
     */
    public static void setSeed(int seed) {
        rand.setSeed(seed);
    }

    /**
     * This is a static boolean method to generate by 'Fate' if a mishap is fixable.
     * @return True or False depend on "God".
     */
    private static boolean generateFixable() {
        return rand.nextInt(10) > 7;
    }

    /**
     * This is a static method to generate by 'Fate' the mishap reduction parameter.
     * @return some reduction parameter depend on "God".
     */
    private static float generateReduction() {
        return rand.nextFloat();
    }

    /**
     * This is a static method to generate by 'Fate' the mishap turn to fix parameter.
     * @return some turn to fix parameter depend on "God".
     */
    private static int generateTurns() {
        return rand.nextInt(5);
    }

    /**
     * This is a static boolean method to generate by 'Fate' the mishap chance to break down.
     *  basically a chance to break down,
     * @return True or False depend on "God".
     */
    public static boolean breakDown(double failureProbability) {
        return rand.nextFloat() <= failureProbability;
    }

    /**
     * This is a static method that generate by 'Fate' the mishap it self.
     * @return a brand new mishap with parameters that depend on "God".
     */
    public static Mishap generateMishap() {
        return new Mishap(generateFixable(), generateTurns(), generateReduction());
    }
}