package game.racers.decorator;
import game.racers.IDecoratedRacer;

/**
 * Decorator Design Pattern:
 * this class is basically The Decorator,
 * it has a "Has a" relationship with IDecoratedRacer. This is an Aggregation Relationship.
 * this class implement IRacerDecorator to gain access for method and attributes.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public abstract class RacerDecorator implements IDecoratedRacer {
    //Fields:
    // make a reference for our IDecoratedRacer.
    private IDecoratedRacer decoratedRacer;

    // Constructor:
    /**
     * this constructor is important, in the parameter we getting the specific racer we want to decorate,
     * then we initial the decorated racer that we got by the parameter,
     * assigns the type instance to this attribute of a racer
     * all decorators can dynamically customize the racer instance because of this.
     *
     * @param decoratedRacer is a reference to the racer we want to decorate.
     */
    RacerDecorator(IDecoratedRacer decoratedRacer) {
        this.setDecoratedRacer( decoratedRacer );
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
        this.getDecoratedRacer().addAttribute(key, value);
    }

    /**
     * This is a Getter to get the decorated racer.
     * @return the decorated racer.
     */
    IDecoratedRacer getDecoratedRacer() {
        return decoratedRacer;
    }

    /**
     * This is a Setter to set this decorated racer.
     * @param decoratedRacer is the decorated racer to set.
     */
    private void setDecoratedRacer(IDecoratedRacer decoratedRacer) {
        this.decoratedRacer = decoratedRacer;
    }

}
