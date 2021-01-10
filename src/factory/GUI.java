package factory;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.IDecoratedRacer;
import game.racers.Racer;
import game.racers.decorator.ColoredRacer;
import game.racers.decorator.WheeledRacer;
import utilities.EnumContainer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;

/**
 * a GUI class for the game.
 * a friendly interface for the user, using Swing of java.
 * user can decide arena type, length, and max racers than user can build an arena
 * after that the user can choose a racer type & color then set a name, max speed, acceleration,
 * and add the racer to the arena, then start the game by press "Start Race" button,
 * there is also a "Show Info" button that display a table of current racers.
 *
 * @version 1.0
 * @author Avihay D Hemo
 */
public class GUI extends JFrame implements Observer {

    private myPanel racePanel;
    private JLabel backGround;
    private RaceBuilder builder = RaceBuilder.getInstance();
    private Arena arena;
    private ArrayList<Racer> racers;

    //Methods:
    /**
     * Observer Design pattern:
     * this function purpose is to get a message from an observable and act matching
     * basically this is where we make each racer move, means here we decide how the racer is progressing,
     * also taking care here of the gap between threads.
     *
     * @param racer is the observable that notify (GUI is the observer).
     * @param msg is message we get to identify between different observers.
     */
    @Override
    public void notifyMsg(Racer racer, String msg) {
        if (msg.equals("make a move")) {
            racer.getCurrentLocation().setX(racer.getCurrentLocation().getX() + racer.getCurrentSpeed() * racer.getMishap().getReductionFactor());

        } else if (msg.equals("make a different move")) {
            if (racer.getMishap() != null)
                racer.getCurrentLocation().setX(racer.getCurrentLocation().getX() + racer.getCurrentSpeed() * racer.getMishap().getReductionFactor());
            else
                racer.getCurrentLocation().setX(racer.getCurrentLocation().getX() + racer.getCurrentSpeed());
        }
        try {
            Thread.sleep(30);
            backGround.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Observer Design pattern:
     *  this function purpose is to notify the Observer with a message passed as a parameter.
     *
     * @param o is the Observable mean our racer that need to update (GUI is the Observer)
     * @param arg is message we sent to identify between different observers.
     */
    @Override
    public void update(Observable o, Object arg) {
        this.notifyMsg((Racer) o, (String) arg);
    }

    /**
     * this function purpose is to help the GUI initial the arena.
     *  first calc the new high and length of the GUI frame,
     *  then set the size of the frame & racing area & background image,
     *  finally we check if there are an existing arena, if so clear it and delete all.
     *
     * @param length is the length of the arena by user input.
     * @param maxRacers is the max racers on the arena by use input.
     */
    private void initArena(double length, int maxRacers) {
        int calcHigh;
        int calcLength = (int) length;
        int calcArea;

        // calculating new high and length.
        if (calcLength > 1000) {
            calcLength = (int) length;
        } else calcLength = 1000;

        if (maxRacers > 14)
            calcHigh = maxRacers * 75;
        else calcHigh = 1000;

        calcArea = calcLength - 130;
        // set sizes.
        this.setSize(calcLength, calcHigh);
        this.backGround.setPreferredSize( new Dimension(calcArea,calcHigh) );

        // changing the size of the area
        switch (this.racePanel.arenaType.getSelectedIndex()) {
            case 0:
                this.allArenas[0] = new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/AerialArena.jpg")).getImage().getScaledInstance(calcArea, calcHigh, Image.SCALE_SMOOTH));
                break;
            case 1:
                this.allArenas[1] = new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/LandArena.jpg")).getImage().getScaledInstance(calcArea, calcHigh, Image.SCALE_SMOOTH));
                break;
            case 2:
                this.allArenas[2] = new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/NavalArena.jpg")).getImage().getScaledInstance(calcArea, calcHigh, Image.SCALE_SMOOTH));
                break;
        }
        // if there is an arena, clear it to make a new one.
        if (arena != null) {
            this.backGround.removeAll();
            this.backGround.repaint();
            for (Racer racer : racers) {
                racer.unregisterObserver(this);
            }
            Racer.resetI();
            racers = new ArrayList<Racer>();
            racePanel.chooseNumber.removeAllItems();
        }
    }

    /**
     * this function purpose is to help GUI initial a Racer for the game,
     *  this function gets as parameter a Racer Object and initial it,
     *  first add it to a racers list so we can have access to it from GUI,
     *  then we using arena to init the racer start and finish points,
     *  after that we take care of the racer Icon, set hes size & location and add it to background.
     *
     * @param racer is a Racer object that we create using user inputs.
     */
    private void initRacers(Racer racer) {
        try {
            racers.add( racer );
            racer.addObserver(this);
            racer.registerObserver(this);
            arena.initRace();
            racer.setMyIcon(new JLabel());
            racer.getMyIcon().setSize(60, 60);
            racer.getMyIcon().setLocation(0, (arena.getActiveRacers().size() - 1) * 70);
            racer.getMyIcon().setIcon(allRacerIcons[racePanel.racerType.getSelectedIndex()][racePanel.colorType.getSelectedIndex()]);
            this.backGround.add(racer.getMyIcon());
            this.backGround.repaint();
            this.backGround.updateUI();
            JOptionPane.showMessageDialog(null, "Racer Added Successfully");
        }
        catch (  SecurityException | IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function purpose is to help GUI initial a Racer for the game,
     *  this function gets as parameter a Racer Object and initial it,
     *  first add it to a racers list so we can have access to it from GUI,
     *  then we using arena to init the racer start and finish points,
     *  after that we take care of the racer Icon, set hes size & location and add it to background.
     *
     * @param racer is a Racer object that we create using user inputs.
     */
    private void initClonedRacers(Racer racer) {
        try {
            racers.add( racer );
            arena.initRace();
            racer.setMyIcon(new JLabel());
            racer.getMyIcon().setSize(60, 60);
            racer.getMyIcon().setLocation(0, (arena.getActiveRacers().size() - 1) * 70);
            racer.getMyIcon().setIcon(allRacerIcons[racer.classIndex()][racePanel.copyColorType.getSelectedIndex()]);
            this.backGround.add(racer.getMyIcon());
            this.backGround.repaint();
            this.backGround.updateUI();
            JOptionPane.showMessageDialog(null, "Racer Added Successfully");
        }
        catch (  SecurityException | IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function purpose is to help GUI initial a cars racers for the game,
     *  this function gets as parameter a Racer Object and initial it,
     *  first add it to a racers list so we can have access to it from GUI,
     *  then we using arena to init the racer start and finish points,
     *  after that we take care of the racer Icon, set hes size & location and add it to background.
     *
     * @param racer is a Racer object that we create using user inputs.
     */
    private void initCars(Racer racer, int yGap) {
        try {
            racers.add( racer );
            racer.addObserver(this);
            racer.registerObserver(this);
            racer.setMyIcon(new JLabel());
            racer.getMyIcon().setSize(60, 60);
            racer.getMyIcon().setLocation(0, (yGap - 1) * 70);
            racer.getMyIcon().setIcon(allRacerIcons[3][0]);
            this.backGround.add(racer.getMyIcon());
            this.backGround.repaint();
            this.backGround.updateUI();
        }
        catch (  SecurityException | IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function purpose is to help GUI initial the Table of the racers,
     *  first we create a columns as the data we asked to,
     *  then we make 3 loop to fill the table with racers details,
     *  at top the finishers, at middle actives, and at bottom disables.
     *  at the end we using swing JTable to init a table with scroll panel & set the size we want,
     *  finally making a frame and put the table in it, display when the user click.
     */
    private void initTable() {
        int i,j,k;

        Object[] columns = {"Racer name", "Current speed", "Max speed", "Current X location", "Finished"};

        Object[][] info = new Object[racers.size()][5];

        for (i=0; i < arena.getCompletedRacers().size(); i++) {
            if (arena.getCompletedRacers().get( i ) == null) {
                continue;
            }
            info[i][0] = arena.getCompletedRacers().get( i ).getMyName();
            info[i][1] = arena.getCompletedRacers().get( i ).getCurrentSpeed();
            info[i][2] = arena.getCompletedRacers().get( i ).getMaxSpeed();
            info[i][3] = arena.getCompletedRacers().get( i ).getCurrentLocation().getX();
            info[i][4] = "YES";
        }
        for (k=0; k < arena.getActiveRacers().size(); k++) {
            if (arena.getActiveRacers().get( k ) == null) {
                continue;
            }
            info[i+k][0] = arena.getActiveRacers().get( k ).getMyName();
            info[i+k][1] = arena.getActiveRacers().get( k ).getCurrentSpeed();
            info[i+k][2] = arena.getActiveRacers().get( k ).getMaxSpeed();
            info[i+k][3] = arena.getActiveRacers().get( k ).getCurrentLocation().getX();
            info[i+k][4] = "NO";
        }
        for (j=0; j < arena.getDisabledRacers().size(); j++) {
            if (arena.getDisabledRacers().get( j ) == null) {
                continue;
            }
            info[i+k+j][0] = arena.getDisabledRacers().get( j ).getMyName();
            info[i+k+j][1] = arena.getDisabledRacers().get( j ).getCurrentSpeed();
            info[i+k+j][2] = arena.getDisabledRacers().get( j ).getMaxSpeed();
            info[i+k+j][3] = arena.getDisabledRacers().get( j ).getCurrentLocation().getX();
            info[i+k+j][4] = "DISABLED";
        }
        // initialing a table to display the info.
        JFrame tableFrame = new JFrame( "Racers information" );
        JTable table = new JTable( info, columns );
        table.setDefaultEditor( Object.class, null );
        table.getTableHeader().setReorderingAllowed( false );
        // adding a scroll pane
        JScrollPane jsp = new JScrollPane( table );
        // taking care of the table frame size and visibility.
        tableFrame.add( jsp, BorderLayout.CENTER );
        tableFrame.setSize( 600, 200);
        tableFrame.setVisible( true );
        tableFrame.setLocationRelativeTo( null );
    }

    //Constructor:
    /**
     * This is the GUI constructor here we initialize the main frame.
     * then set the Panels for the controls and arena,
     * and add everything into main frame.
     *
     * @throws HeadlessException to know if there is an head less exception.
     */
    private GUI() throws HeadlessException {

        super("Race");
        this.setResizable(false);
        racers = new ArrayList<>();

        racePanel = new myPanel(this);
        racePanel.add(Box.createRigidArea(new Dimension(1, 500)));

        JPanel bigPanel = new JPanel(new BorderLayout());
        bigPanel.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_END);

        backGround = new JLabel();

        bigPanel.add(backGround);

        add(racePanel, BorderLayout.EAST);
        add(bigPanel, BorderLayout.CENTER);

    }


    /**
     * This is a inner private class to custom our own build JPanel.
     * this class purpose is to help us build our user interface into a panel.
     * this class extends JPanel and implement ActionListener & Runnable
     *
     * the class use:
     * JPanel--> for our interface to have Panel features and behave like a Panel.
     * ActionListener--> to handle events that happens by user input & button clicks.
     * Runnable--> to work with treads and make a run function.
     */
    private class myPanel extends JPanel implements ActionListener {
        private JFrame theFrame;
        // Arena Fields:
        private JLabel setArena;
        private JComboBox<String> arenaType;
        private JLabel arenaLength;
        private JLabel arenaMaxRacers;
        private JTextField arenaMaxRacersText;
        private JTextField arenaLengthText;
        private JButton buildArenaButton;
        // Racer Fields:
        private JLabel setRacer;
        private JComboBox<String> racerType;
        private JLabel setColor;
        private JComboBox<EnumContainer.Color> colorType;
        private JLabel racerName;
        private JTextField nameTextField;
        private JLabel maxSpeed;
        private JTextField maxSpeedTextField;
        private JLabel acceleration;
        private JTextField accelerationTextField;
        // Decorator Fields:
        private JLabel decoratedRacer;
        private JLabel decoratedColor;
        private JComboBox<EnumContainer.Color> decColorType;
        private JLabel decoratedWheels;
        private JTextField decWheelsTextField;
        private JButton decorateRacerButton;
        // Prototype Fields:
        private JLabel serialNumber;
        private JComboBox chooseNumber;
        private JLabel copyColor;
        private JComboBox<EnumContainer.Color> copyColorType;
        private JButton copyRacerButton;
        // CarRaceFiled:
        private JLabel numOfCars;
        private JTextField numOfCarsText;
        private JButton buildCarRaceButton;
        // Buttons Fields:
        private JButton startRaceButton;
        private JButton addRacerButton;
        private JButton showInfoButton;
        /// action listener stuff
        private String message1 = "Invalid input values! Please try again.";
        private String listener;
        private int arenaTypeInput;
        private double lengthInput;
        private int maxRacersInput;
        private int racerTypeInput;
        private int colorTypeInput;
        private String nameInput;
        private double maxSpeedInput;
        private double accelerationInput;
        private int copyColorInput;
        private int copySerialNumberInput;
        private int numOfCarsInput;
        private int decColorInput;
        private int decWheelsInput;
        //create a vertical box to put all of the controls together.
        Box verticalBox = Box.createVerticalBox();

        /**
         * an Override function of Interface ActionListener.
         * this function purpose is to handle all of the events,
         * that can happens by user inputs and clicks of buttons,
         * also here we implement the setting of the game and make it logical.
         *
         * @param e is the event that trigger.
         */
        @Override
        public synchronized void actionPerformed(ActionEvent e) {
///////////////////////////////////////////////////////////////======Begin of Arena Panel======///////////////////////////////////////////////////
            // if there is a click on 'Build Arena Button'
            if (e.getSource() == racePanel.buildArenaButton) {
                // try to get the user inputs
                try {
                    arenaTypeInput = racePanel.arenaType.getSelectedIndex();
                    listener = racePanel.arenaLengthText.getText();
                    if (listener != null) { // if there is no blank input
                        try {
                            lengthInput = Integer.parseInt( listener );
                            // then check if the input check our match.
                            if (lengthInput > 3000 || lengthInput < 100) {
                                // if not the user get an Error message and we set the input into default.
                                JOptionPane.showMessageDialog( null, message1 );
                                return;
                            }
                        } catch (Exception e1) {
                            // if there is an exception the user get an Error message.
                            JOptionPane.showMessageDialog( null, message1 );
                            lengthInput = 1000;
                        }
                        // return on the same process but not with the max racers inputs.
                        listener = racePanel.arenaMaxRacersText.getText();
                        if (listener != null) {
                            try {
                                maxRacersInput = Integer.parseInt( listener );
                                if (maxRacersInput > 20 || maxRacersInput < 1) {
                                    JOptionPane.showMessageDialog( null, message1 );
                                    return;
                                }
                            } catch (Exception e2) {
                                JOptionPane.showMessageDialog( null, message1 );
                                maxRacersInput = 8;
                            }
                        }
                        // if we press the build arena button and we already got an arena
                        if (arena != null ) {  // if there is an arena there are 2 cases: race begin, or race complete,
                            // check if there is a race in process
                            if (!arena.isActive()) {
                                // but if there are no racing competing, means they all finished or destroyed on they way, so race is over,
                                // we clear it delete the arena and racers and we'll create a new one.
                                try {
                                    // now its a time to create our arena using our RaceBuilder Singleton,
                                    // then we initial the race by passing the user inputs to the right hands,
                                    // and finally we set all of the icons for a nice display.
                                    arena = builder.buildArena( RacingClassesFinder.getInstance().getArenasList().get( arenaTypeInput ), lengthInput, maxRacersInput );
                                    initArena( lengthInput, maxRacersInput );
                                    backGround.setIcon( allArenas[racePanel.arenaType.getSelectedIndex()] );
                                    backGround.updateUI();
                                    backGround.repaint();
                                    JOptionPane.showMessageDialog( null, "Arena build successfully" );

                                } catch (  SecurityException| IllegalArgumentException e1) {
                                    System.out.println( "Unable to build arena!" );
                                }
                            } else JOptionPane.showMessageDialog( null, "Error! Can not build arena right now, please wait for the race to end!" );
                        }
                        if (arena == null && !Objects.requireNonNull(listener).equals("")) {
                            // here we returning on the same process but with one different, no arena before.
                            try {
                                arena = builder.buildArena( RacingClassesFinder.getInstance().getArenasList().get( arenaTypeInput ), lengthInput, maxRacersInput );
                                initArena( lengthInput, maxRacersInput );
                                backGround.setIcon( allArenas[racePanel.arenaType.getSelectedIndex()] );
                                backGround.updateUI();
                                backGround.repaint();
                                JOptionPane.showMessageDialog( null, "Arena build successfully" );

                            } catch ( SecurityException | IllegalArgumentException e1) {
                                System.out.println( "Unable to build arena!" );
                            }
                        }
                    }
                } catch (Exception e1) { // there are might be exceptions.
                    e1.printStackTrace();
                }
            }
/////////////////////////////////////////////////////////////////////======End of Arena Panel======///////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////======Begin of Racer Panel======///////////////////////////////////////////////////
            // if the user clicks the 'Add Racer' button.
            if (e.getSource() == racePanel.addRacerButton) {
                // checking if there is an arena and its not active,
                if ((arena != null) && (!arena.isActive())) {
                    if ((arena.getCompletedRacers().size()>0) || (arena.getDisabledRacers().size()>0) ) {
                        JOptionPane.showMessageDialog( null, "Please build arena first!" );
                        return;
                    }
                }
                try {
                    // we try to get the user inputs.
                    racerTypeInput = racePanel.racerType.getSelectedIndex();
                    colorTypeInput = racePanel.colorType.getSelectedIndex();
                    nameInput = racePanel.nameTextField.getText();

                    // if there are no arena we cant add racer! they supposed to race somewhere guineas...
                    if (arena == null) {
                        // a friendly message to the user.
                        JOptionPane.showMessageDialog( null, "Please build arena first!" );
                    }
                    // checking if the user input a name to the racer
                    if (nameInput != null && !nameInput.equals( "" )) {
                        listener = racePanel.maxSpeedTextField.getText();
                        try {
                            // first we have to try to assign the user input to a variables, **must to be integers (length and racers number).
                            maxSpeedInput = Integer.parseInt( listener );
                            listener = racePanel.accelerationTextField.getText();
                            accelerationInput = Integer.parseInt( listener );
                            // check if the user put positive numbers so we can continue.
                            if ((maxSpeedInput < 0 && accelerationInput < 0)) {
                                //take care of negative numbers
                                JOptionPane.showMessageDialog( null, message1 );
                                return;
                            }
                            // take care of unveiled user input.
                        } catch (Exception e3) {
                            JOptionPane.showMessageDialog( null, message1 );
                            return;
                        }
                    } else {// friendly messages to the user.
                        JOptionPane.showMessageDialog( null, "Please enter racer name!" );
                        return;
                    }
                    // if there is an arena
                    if (arena != null) {
                        try {
                            // check if the race is begin, can not add racers to a running race.
                            if (!arena.isActive()) {
                                // check if there is a space in the arena for more racers.
                                if (arena.getActiveRacers().size() < arena.getMAX_RACERS()) {
                                    // create a racer using our RaceBuilder Singleton.
                                    // then we add the racer to the arena, and initialize it.
                                    Racer racer = builder.buildRacer( RacingClassesFinder.getInstance().getRacersList().get( racerTypeInput ), nameInput, maxSpeedInput, accelerationInput, EnumContainer.Color.values()[colorTypeInput] );
                                    arena.addRacer( racer );
                                    initRacers( racer );
                                    racePanel.chooseNumber.addItem(racer.getSerialNumber());
                                } else if (arena.getActiveRacers().size() >= arena.getMAX_RACERS()) {
                                    // if there is no space for more racers we sent a friendly message to the user.
                                    JOptionPane.showMessageDialog( null, "Error!! can not add more racers" );
                                }
                            } else if (arena.isActive()) {
                                // user get a message also if the race already begin.
                                JOptionPane.showMessageDialog( null, "Error! can not add racers now! please wait for the race to finish");
                            }
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                                | IllegalAccessException | IllegalArgumentException | NullPointerException
                                | RacerLimitException | RacerTypeException | InvocationTargetException e1) {
                            JOptionPane.showMessageDialog( null, e1.getMessage() );
                            // taking care of threads and add messages.
                            System.out.println( "[Error] " + e1.getMessage() );
                        }
                    }
                } catch (Exception e2) { e2.printStackTrace();}
            }
////////////////////////////////////////////////////////////////======End of Racer Panel======//////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////======Begin of Decorated Panel======//////////////////////////////////////////////////////////////////////
            // if the user clicks the 'Decorate Racer' button.
            if (e.getSource() == racePanel.decorateRacerButton) {
                // checking if there is an arena and its not active,
                if ((arena != null) && (!arena.isActive())) {
                    if ((arena.getCompletedRacers().size()>0) || (arena.getDisabledRacers().size()>0) ) {
                        JOptionPane.showMessageDialog( null, "Please build arena first!" );
                        return;
                    }
                }
                try {
                    // trying to get the user inputs,
                    racerTypeInput = racePanel.racerType.getSelectedIndex();
                    nameInput = racePanel.nameTextField.getText();
                    decColorInput = racePanel.decColorType.getSelectedIndex();
                    // check if there is an arena.
                    if (arena == null) {
                        // a friendly message to the user.
                        JOptionPane.showMessageDialog( null, "Please build arena first and add racers!" );
                    }
                    // checking if the user input a name to the racer
                    if (nameInput != null && !nameInput.equals( "" )) {
                        listener = racePanel.maxSpeedTextField.getText();
                        try {
                            // first we have to try to assign the user input to a variables, **must to be integers (length and racers number).
                            maxSpeedInput = Integer.parseInt( listener );
                            listener = racePanel.accelerationTextField.getText();
                            accelerationInput = Integer.parseInt( listener );
                            decWheelsInput = Integer.parseInt(racePanel.decWheelsTextField.getText());
                            // we check if the user put positive numbers so we can continue.
                            if ((maxSpeedInput < 0 && accelerationInput < 0)) {
                                //take care of negative numbers
                                JOptionPane.showMessageDialog( null, message1 );
                                return;
                            }
                            // check if user enter num of wheels.
                            if ((decWheelsInput <=0)) {
                                JOptionPane.showMessageDialog(null, message1);
                                return;
                            }
                            // take care of unveiled user input.
                        } catch (Exception e3) {
                            JOptionPane.showMessageDialog( null, message1 );
                            return;
                        }
                    } else {// and some more friendly messages to the user.
                        JOptionPane.showMessageDialog( null, "Please enter racer name!" );
                        return;
                    }
                    // if there is an arena
                    if (arena != null) {
                        try {
                            // check if the race is begin, can not add racers to a running race
                            if (!arena.isActive()) {
                                // check if there is a space in the arena for more racers.
                                if (arena.getActiveRacers().size() < arena.getMAX_RACERS()) {
                                    // create a racer using our RaceBuilder Singleton.
                                    IDecoratedRacer decoratedRacer = builder.buildRacer( RacingClassesFinder.getInstance().getRacersList().get( racerTypeInput ), nameInput, maxSpeedInput, accelerationInput, EnumContainer.Color.values()[decColorInput] );
                                    // using a design pattern called Decorator:
                                    new ColoredRacer( new WheeledRacer( decoratedRacer, decWheelsInput ) ,EnumContainer.Color.values()[decColorInput]);
                                    // then we add the racer to the arena, and initialize it.
                                    arena.addRacer((Racer) decoratedRacer );
                                    initRacers( (Racer) decoratedRacer );
                                    ( (Racer) decoratedRacer ).getMyIcon().setIcon(allRacerIcons[racePanel.racerType.getSelectedIndex()][racePanel.decColorType.getSelectedIndex()]);
                                    JOptionPane.showMessageDialog( null, "Racer is now decorated with new color: " + EnumContainer.Color.values()[decColorInput].toString() + " and number of wheels: " + decWheelsInput );
                                    racePanel.chooseNumber.addItem(((Racer) decoratedRacer).getSerialNumber());

                                } else if (arena.getActiveRacers().size() >= arena.getMAX_RACERS()) {
                                    // if there is no space for more racers we sent a friendly message to the user.
                                    JOptionPane.showMessageDialog( null, "Error!! can not add more racers." );
                                }
                            } else if (arena.isActive()) {
                                JOptionPane.showMessageDialog( null, "Error! can not add racers now! please wait for the race to finish!" );
                            }
                        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                                | IllegalAccessException | IllegalArgumentException | NullPointerException
                                | RacerLimitException | RacerTypeException | InvocationTargetException e1) {
                            JOptionPane.showMessageDialog( null, e1.getMessage() );
                            // taking care of threads and add some messages.
                            System.out.println( "[Error] " + e1.getMessage() );
                        }
                    }
                } catch (Exception e7) { e7.printStackTrace(); }
            }
////////////////////////////////////////////////////////////////======End of Decorated Panel======//////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////======Begin of Prototype Panel======//////////////////////////////////////////////////////////////////////
            // if the user clicks on 'Copy Racer' button.
            if (e.getSource() == racePanel.copyRacerButton) {
                try {
                    // trying to get user inputs,
                    copySerialNumberInput = racePanel.chooseNumber.getSelectedIndex();
                    copyColorInput = racePanel.copyColorType.getSelectedIndex();
                    // check if there is an arena.
                    // if not we send a message to the user also if there is arena but user haven't add at least 1 racer.
                    if (arena == null) {
                        JOptionPane.showMessageDialog(null, "Please build arena first and add racers!");
                    }
                    // if there is an arena & the user choose a number.
                    if (arena != null) {
                        try {
                            // check if there is a race in process, can not interrupt to a running race.
                            if (!arena.isActive()) {
                                // check if there's a space in the arena for more racers.
                                if (arena.getActiveRacers().size() < arena.getMAX_RACERS()) {
                                    // using a Prototype design patter,
                                    Prototype copyRacer = new Prototype();
                                    // getting our chosen racer,
                                    Racer tempRacer = racers.get(copySerialNumberInput);
                                    // making a clone of our chosen racer,
                                    Racer clonedRacer = copyRacer.getClone(tempRacer);
                                    // initial the cloned racer,
                                    clonedRacer.setColor(EnumContainer.Color.values()[copyColorInput]);
                                    clonedRacer.setSerialNumber(racers.size() + 1);
                                    arena.addRacer(clonedRacer);
                                    initClonedRacers(clonedRacer);
                                    racePanel.chooseNumber.addItem(clonedRacer.getSerialNumber());
                                    Racer.incI();
                                } else if (arena.getActiveRacers().size() >= arena.getMAX_RACERS()) {
                                    // if there is no space for more racers we sent a friendly message to the user.
                                    JOptionPane.showMessageDialog(null, "Error!! can not add any more racers!");
                                }
                            } else if (arena.isActive()) {
                                // user get a message also if the race already begin.
                                JOptionPane.showMessageDialog(null, "Error!! can not add racers now! please wait for the race to finish");
                            }
                        } catch (ArrayIndexOutOfBoundsException | RacerTypeException | RacerLimitException e1) {
                            JOptionPane.showMessageDialog(null, "Please add at least one racer!");
                        }
                    }
                } catch (Exception e3) { e3.printStackTrace(); }
            }
///////////////////////////////////////////////////////////////======End of Prototype Panel======//////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////======Begin of CarRace Panel======//////////////////////////////////////////////////////////////////////
            // if the user clicks on 'Build Car Race' button.
            if (e.getSource() == racePanel.buildCarRaceButton) {
                // using a Builder design pattern,
                CarRaceBuilder carRaceBuilder = new CarRaceBuilder();
                CarRaceEngineer engineer = new CarRaceEngineer(carRaceBuilder);
                // check if there is an arena, if so check if it is not active.
                if (arena == null || (arena!=null && !arena.isActive())) {
                    try {
                        // try to get user input,
                        listener = racePanel.numOfCarsText.getText();
                        numOfCarsInput = Integer.parseInt( listener );
                        if (numOfCarsInput < 15 && numOfCarsInput > 0) {
                            // using our engineer builder to construct a car race,
                            arena = engineer.constructCarRace(numOfCarsInput);
                            // initial the arena,
                            arena.initRace();
                            initArena(1000, numOfCarsInput);
                            backGround.setIcon(allArenas[1]);
                            int yGap = 1;
                            // loop on the cars and initial icons,
                            for (Racer racer : engineer.getCarRaceBuilder().getCarRace().getCars()) {
                                initCars(racer, yGap++);
                                racePanel.chooseNumber.addItem(racer.getSerialNumber());
                            }
                            JOptionPane.showMessageDialog(null, "Cars race build Successfully");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error! allowed 1-14 cars, please try again.");
                        }
                    } catch (SecurityException | IllegalArgumentException e5) {
                        System.out.println( "Unable to build cars race!" );
                        JOptionPane.showMessageDialog(null, "Error! please enter number of cars.");
                    } catch (RacerLimitException | RacerTypeException e1) {
                        e1.printStackTrace();
                    }
                }
            }
///////////////////////////////////////////////////////////////======End of CarRace Panel======//////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////======Begin of Button Panel======//////////////////////////////////////////////////////////////////////
            // if the user clicks on the 'Start Race' button.
            if (e.getSource() == racePanel.startRaceButton) {
                // check if there is an arena.
                // if not we send a message to the user also if there is arena but user have not add at least 1 racer.
                if (arena == null) {
                    JOptionPane.showMessageDialog(null, "Please build arena first and add racers!");
                } else if (arena.getActiveRacers().size() < 1 && !arena.isActive()) {
                    JOptionPane.showMessageDialog(null, "Please add at least one racer!"); }
                // if there is an arena.
                if (arena != null) {
                    // we check if there is a race in process, can not interrupt a running race.
                    if (!arena.isActive()) {
                        try {
                            // if the arena is available, start the race!
                            // first initial the arena, then start the race using arena thread pool function,
                            // last taking care of all of the icons
                            arena.initRace();
                            arena.startRace();
                            backGround.updateUI();
                            backGround.repaint();
                            if (!arena.isActive()) {
                                JOptionPane.showMessageDialog(null, "Please build arena first and add racers!");
                            } // if there is no arena, the user gets a message.
                            // also taking care of exceptions.
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else { // if the user want to start a new race but there is a race in process he gets a message.
                        JOptionPane.showMessageDialog(null, "Error!! Cant start a race now! please wait for the race to finish");
                    }
                }
            }
            // if the user clicks on the 'Show Info' button.
            if (e.getSource() == racePanel.showInfoButton) {
                try {
                    if (arena != null) {
                        //only if there is an arena we show information.
                        // display it as a table.
                        arena.showResults();
                        initTable();
                    }
                } catch (Exception e5) {
                    System.out.println( "ちくしょう, もう一度.." );
                    JOptionPane.showMessageDialog( null, "This is a System Message!\n you probably did something wrong, please build another arena and start a new Race." );
                    System.out.println( "[Notice]: This error occurs because the user has not finished building the arena,\n" +
                            " because the loading of the image of the arena is dynamic,\n" +
                            " it creates a sense of confusion that the scene was built and the user forgets to press the 'Build Arena' button.\n" +
                            " This is a common mistake. To continue simply create a new arena and continue playing " );
                    arena.setActive( false );
                }

            }
///////////////////////////////////////////////////////////////////======End of Button Panel======//////////////////////////////////////////////////////////////////////
        }
        /**
         * This is the class Constructor,
         * this function is basically builds the graphical user interface
         * and taking care of how is everything going to look eventually on the control panel.
         *
         * @param mainPanel is a reference of the main Frame of the GUI.
         */
        private myPanel(JFrame mainPanel) {
            // getting a reference of the main Frame,
            theFrame = mainPanel;
            Dimension yGap = new Dimension(0, 5);

            // now honestly everything below is boring, we doing the same again and again,
            // its all a way to organize our control panel, and its the same process:
            // first we create a rigid arena and add to the vertical box,
            // then we set our label/or other control components.
            setArena = new JLabel("Choose arena:");
            setArena.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(setArena);
            verticalBox.add(Box.createRigidArea(yGap));

            arenaType = new JComboBox<>();
            for (String string : RacingClassesFinder.getInstance().getArenasNamesList()) {
                arenaType.addItem(string);
            }
            arenaType.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(arenaType);

            verticalBox.add(Box.createRigidArea(yGap));
            arenaLength = new JLabel("Arena length:");
            arenaLength.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(arenaLength);
            verticalBox.add(Box.createRigidArea(yGap));

            arenaLengthText = new JTextField(9);
            arenaLengthText.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(arenaLengthText);

            verticalBox.add(Box.createRigidArea(yGap));
            arenaMaxRacers = new JLabel("Max racers number:");
            arenaMaxRacers.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(arenaMaxRacers);
            verticalBox.add(Box.createRigidArea(yGap));

            arenaMaxRacersText = new JTextField(9);
            arenaMaxRacersText.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(arenaMaxRacersText);

            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));

            buildArenaButton = new JButton("Build Arena");
            buildArenaButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(buildArenaButton);

            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(new JSeparator());

            //// Racer
            verticalBox.add(Box.createRigidArea(yGap));
            setRacer = new JLabel("Choose racer:");
            setRacer.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(setRacer);
            verticalBox.add(Box.createRigidArea(yGap));

            racerType = new JComboBox<String>();
            for (String string : RacingClassesFinder.getInstance().getRacersNamesList()) {
                racerType.addItem(string);
            }
            racerType.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(racerType);
            verticalBox.add(Box.createRigidArea(yGap));

            setColor = new JLabel("Choose color:");
            setColor.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(setColor);
            verticalBox.add(Box.createRigidArea(yGap));

            colorType = new JComboBox<>(EnumContainer.Color.values());
            colorType.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(colorType);
            verticalBox.add(Box.createRigidArea(yGap));

            racerName = new JLabel("Racer name:");
            racerName.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(racerName);
            verticalBox.add(Box.createRigidArea(yGap));

            nameTextField = new JTextField(9);
            nameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(nameTextField);
            verticalBox.add(Box.createRigidArea(yGap));

            maxSpeed = new JLabel("Max Speed:");
            maxSpeed.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(maxSpeed);
            verticalBox.add(Box.createRigidArea(yGap));

            maxSpeedTextField = new JTextField(9);
            maxSpeedTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(maxSpeedTextField);
            verticalBox.add(Box.createRigidArea(yGap));

            acceleration = new JLabel("Acceleration:");
            acceleration.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(acceleration);
            verticalBox.add(Box.createRigidArea(yGap));

            accelerationTextField = new JTextField(9);
            accelerationTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(accelerationTextField);
            verticalBox.add(Box.createRigidArea(new Dimension( 0,10 )));

            addRacerButton = new JButton("Add Racer");
            addRacerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(addRacerButton);

            verticalBox.add(Box.createRigidArea(new Dimension( 0,10 )));
            verticalBox.add(new JSeparator());

            // Decorator
            decoratedRacer = new JLabel("Decorate your Racer:");
            decoratedRacer.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(decoratedRacer);
            verticalBox.add(Box.createRigidArea(yGap));

            decoratedColor = new JLabel("Color:");
            decoratedColor.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(decoratedColor);
            verticalBox.add(Box.createRigidArea(yGap));

            decColorType = new JComboBox<>(EnumContainer.Color.values());
            decColorType.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(decColorType);
            verticalBox.add(Box.createRigidArea(yGap));

            decoratedWheels = new JLabel("Number of Wheels:");
            decoratedWheels.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(decoratedWheels);
            verticalBox.add(Box.createRigidArea(yGap));

            decWheelsTextField = new JTextField(9);
            decWheelsTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(decWheelsTextField);
            verticalBox.add(Box.createRigidArea(new Dimension( 0,10 )));

            decorateRacerButton = new JButton("Decorate Racer");
            decorateRacerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(decorateRacerButton);

            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(new JSeparator());

            // Prototype
            serialNumber = new JLabel("Serial Number:");
            serialNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(serialNumber);
            verticalBox.add(Box.createRigidArea(yGap));

            chooseNumber = new JComboBox<>();
            chooseNumber.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(chooseNumber);
            verticalBox.add(Box.createRigidArea(yGap));

            copyColor = new JLabel("Color:");
            copyColor.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(copyColor);
            verticalBox.add(Box.createRigidArea(yGap));

            copyColorType = new JComboBox<>(EnumContainer.Color.values());
            copyColorType.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(copyColorType);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));

            copyRacerButton = new JButton("Copy Racer");
            copyRacerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(copyRacerButton);

            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            verticalBox.add(new JSeparator());

            // CarRace
            numOfCars = new JLabel("Number of Cars:");
            numOfCars.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(numOfCars);
            verticalBox.add(Box.createRigidArea(yGap));

            numOfCarsText = new JTextField(9);
            numOfCarsText.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(numOfCarsText);
            verticalBox.add(Box.createRigidArea(new Dimension( 0,10 ) ));

            buildCarRaceButton = new JButton("Build Car Race");
            buildCarRaceButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(buildCarRaceButton);
            verticalBox.add(Box.createRigidArea(new Dimension(0, 8)));

            verticalBox.add(Box.createRigidArea(yGap));
            verticalBox.add(new JSeparator());

            // Buttons
            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            startRaceButton = new JButton("Start Race");
            startRaceButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(startRaceButton);

            verticalBox.add(Box.createRigidArea(new Dimension(0, 10)));
            showInfoButton = new JButton("Show info");
            showInfoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            verticalBox.add(showInfoButton);

            // adding to the main frame our sorted control vertical box
            theFrame.add(verticalBox);
            add(verticalBox);

            /// adding an action listener to any component that can create an event:
            arenaLengthText.addActionListener(this);
            arenaMaxRacersText.addActionListener(this);
            buildArenaButton.addActionListener(this);
            // display the background image as we click on it in the combo box.
            arenaType.addItemListener( event -> {
                if (event.getStateChange() == ItemEvent.SELECTED)
                    backGround.setIcon(allArenas[racePanel.arenaType.getSelectedIndex()]);
            } );
            colorType.addActionListener(this);
            arenaType.addActionListener(this);
            racerType.addActionListener(this);
            nameTextField.addActionListener(this);
            maxSpeedTextField.addActionListener(this);
            accelerationTextField.addActionListener(this);
            addRacerButton.addActionListener(this);
            chooseNumber.addActionListener(this);
            copyColorType.addActionListener(this);
            copyRacerButton.addActionListener(this);
            numOfCarsText.addActionListener(this);
            buildCarRaceButton.addActionListener(this);
            decColorType.addActionListener( this );
            decWheelsTextField.addActionListener( this );
            decorateRacerButton.addActionListener( this );
            startRaceButton.addActionListener(this);
            showInfoButton.addActionListener(this);
        }
    }

    /**
     * This is the Main to run the GUI and basically the all game we made.
     * @param args whatever.
     */
    public static void main(String[] args) {
        // creating a GUI object
        GUI myGui = new GUI();
        // all of the swing JFrame initialize
        myGui.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        myGui.setLocationRelativeTo(null);
        myGui.setSize(1000, 1000);
        myGui.setLocationRelativeTo( null );
        myGui.setVisible(true);
    }

    //--------------------------------------------------------ICONS---------------------------------------------------
    // all icons arrays:
    private Icon[] allArenas = {
            new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/AerialArena.jpg")).getImage().getScaledInstance(985, 1000, Image.SCALE_SMOOTH)),
            new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/LandArena.jpg")).getImage().getScaledInstance(985, 1000, Image.SCALE_SMOOTH)),
            new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/NavalArena.jpg")).getImage().getScaledInstance(985, 1000, Image.SCALE_SMOOTH))
    };

    private Icon[][] allRacerIcons = {
            {new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/AirplaneRed.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/AirplaneGreen.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/AirplaneBlue.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/AirplaneBlack.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/AirplaneYellow.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH))
            },

            {new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HelicopterRed.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HelicopterGreen.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HelicopterBlue.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HelicopterBlack.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HelicopterYellow.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH))
            },

            {new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/BicycleRed.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/BicycleGreen.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/BicycleBlue.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/BicycleBlack.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/BicycleYellow.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH))
            },

            {new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/CarRed.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/CarGreen.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/CarBlue.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/CarBlack.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/CarYellow.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH))
            },

            {new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HorseRed.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HorseGreen.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HorseBlue.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HorseBlack.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/HorseYellow.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH))
            },

            {new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/RowBoatRed.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/RowBoatGreen.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/RowBoatBlue.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/RowBoatBlack.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/RowBoatYellow.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH))
            },

            {new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/SpeedBoatRed.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/SpeedBoatGreen.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/SpeedBoatBlue.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/SpeedBoatBlack.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)),
                    new ImageIcon(new javax.swing.ImageIcon(this.getClass().getResource("/icons/SpeedBoatYellow.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH))
            }
    };
}