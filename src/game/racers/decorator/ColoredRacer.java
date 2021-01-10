package game.racers.decorator;
import game.racers.IDecoratedRacer;
import utilities.EnumContainer;

/**
 * Decorator Design Pattern:
 * this class is basically a Color Decorator,
 * here we decorating a racer to set hes new color.
 * this class extends RacerDecorator to gain access for method and attributes.
 *
 * @see RacerDecorator
 * @version 1.0
 * @author Avihay D Hemo
 */
public class ColoredRacer extends RacerDecorator {
    // Fields:
    private EnumContainer.Color color;

    // Constructor:
    /**
     * this constructor is important, in the parameter we getting the specific racer we want to decorate,
     * then we initial the super class decorated racer that we got by the parameter,
     * and then we set a new color by user input
     * finally we get to use the method of the Decorator Design pattern to assign the specific decorate.
     *
     * @param racer is a reference to the racer we want to decorate.
     * @param color is which color to give him decide by the user.
     */
    public ColoredRacer(IDecoratedRacer racer, EnumContainer.Color color) {
        super(racer);
        this.setColor( color );
        this.addAttribute( "color", this.getColor() );
    }

    /**
     * This is an override function that we derived from IDecoratedRacer to help us decorate right.
     *
     * @see IDecoratedRacer
     * @param key pass the key as it is to the DecoratedRacer.
     * @param value pass the value as it is to the DecoratedRacer.
     */
    @Override
    public void addAttribute(String key, Object value) {
        getDecoratedRacer().addAttribute( key, value );
    }

    /**
     * This is a Getter to get the color.
     * @return the color.
     */
    public EnumContainer.Color getColor() {
        return color;
    }

    /**
     * This is a Setter to set this color.
     * @param color is the color to set.
     */
    public void setColor(EnumContainer.Color color) {
        this.color = color;
    }
}
