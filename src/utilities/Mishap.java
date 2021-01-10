package utilities;

import java.text.DecimalFormat;

/**
 * This is a class made for the mishap that happens to the poor racers,
 *  here we tacking care of all of the mishap attributes.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class Mishap {

    //Fields:
    private boolean fixable;
    private double reductionFactor;
    private int turnsToFix;

    //Getters & Setters:

    /**
     * This is a boolean function to check if the mishap is fixable,
     * @return True or False if it 'Fixable' is or not.
     */
    public boolean isFixable() {
        return fixable;
    }

    /**
     * This is a Setter to set this mishap fixable state,
     * @param fixable True or False depends of parameter.
     */
    private void setFixable(boolean fixable) {
        this.fixable = fixable;
    }

    /**
     * This is a Getter to get this mishap reduction factor,
     * @return this mishap reduction factor.
     */
    public double getReductionFactor() {
        return reductionFactor;
    }

    /**
     * This is a Setter to set this mishap reduction factor,
     * @param reductionFactor a mishap reduction factor.
     */
    private void setReductionFactor(double reductionFactor) {
        this.reductionFactor = reductionFactor;
    }

    /**
     * This is a Getter to get this mishap turns to fix,
     * @return this mishap turns to fix.
     */
    public int getTurnsToFix() {
        return turnsToFix;
    }

    /**
     * This is a Setter to set this mishap turns to fix,
     * @param turnsToFix a mishap turns to fix.
     */
    private void setTurnsToFix(int turnsToFix) {
        this.turnsToFix = turnsToFix;
    }

    //Methods:
    /**
     * This function purpose is to check if the mishap is fixable,
     *  if so we reduce the turns to fix by 1.
     */
    public void nextTurn() {
        if (this.fixable) {
            this.setTurnsToFix(this.turnsToFix-1);
        }
    }

    /**
     * This is an override function for a better display of the mishaps.
     * @return a string that display the mishap.
     */
    @Override
    public String toString() {
        return String.format(" (%s,%d,%s)", fixable, turnsToFix+1, new DecimalFormat("0.00").format(reductionFactor));
    }

    //Constructors:
    /**
     * This is the nonDefault constructor of this class,
     *  here we initialize the instance with all the parameters,
     *   as if it fixable, the turns to fix, and the reduction Factor.
     * @param fixable this is if the mishap is fixable.
     * @param turnsToFix that's how many turn to fix that mishap.
     * @param reductionFactor this is the reduction factor of the mishap.
     */
    public Mishap(boolean fixable, int turnsToFix, double reductionFactor) {
        this.setFixable(fixable);
        this.setReductionFactor(reductionFactor);
        this.setTurnsToFix(turnsToFix);
    }
}
