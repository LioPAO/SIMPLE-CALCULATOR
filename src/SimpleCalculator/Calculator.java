package SimpleCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//todo Implement calculations with parantheses ()
//todo Make inputField synchronize with keys and buttons, as of now they both individually alter inputField, which sets input field to diff values. Try MultiThreading
//Todo Set focus of Jtextfield to listen to keys at all time, so long as frame is active.
//NEW FEATURE
//TODO Add answerButton, to enable users retrieve and use answers after calculations. Place it in between History buuton and delete button, or shift the clear button

public class Calculator extends JFrame implements ActionListener {
    //Variables / Properties--------------------------------------------------------------------------------------------
    private JFrame historyFrame;
    private JPanel textPanel, optionsPanel, buttonPanel;
    private JTextArea historyArea;
    private JScrollPane historyAreaScrollPane;
    private JFormattedTextField inputField;
    public JLabel resultLbl = new JLabel("0");
    private JButton historyBtn, deleteBtn, historyClearBtn;
    private final JButton [] btn = new JButton[20];


    //CONSTRUCTOR ------------------------------------------------------------------------------------------------------
    Calculator(){

        addPanelsToFrame();

        setAddedPanels();

        eventHandler();

        initializeFrame();
    }

    //PRIVATE METHODS---------------------------------------------------------------------------------------------------

    //Adds and sets panels to Frame
    private void addPanelsToFrame() {
        // Set Layout Manager and its constraints
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbcon = new GridBagConstraints();

        //Set collective constraints
        gbcon.fill = GridBagConstraints.BOTH;
        gbcon.weightx = 1;
        gbcon.weighty = 1;
        gbcon.insets = new Insets(5,5,5,5); // Sets space between components

        //Create textPanel and Add to frame with its set constraints
        textPanel = new JPanel(new GridBagLayout());
        this.add(textPanel,gbcon);

        //Create optionsPanel and Add to frame with its set constraints
        optionsPanel = new JPanel(new BorderLayout());
        gbcon.gridy = 1;
        this.add(optionsPanel,gbcon);

        //Create buttonPanel and Add to frame with its set constraints
        buttonPanel = new JPanel(new GridLayout(5,4,5,5));
        gbcon.gridy = 2;
        gbcon.weighty = 4;
        this.add(buttonPanel,gbcon);
    }

    //Adds and sets buttons and text field to respective
    private void setAddedPanels() {
        //Set Constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx=1;
        constraints.weighty=1;

        //Create label with its settings
        resultLbl = new JLabel("0");
        resultLbl.setFont(new Font("New Times Roman",Font.BOLD,24));
        //resultLbl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textPanel.add(resultLbl,constraints);

        //Create input field and its settings
        constraints.gridy =1;
        inputField = new JFormattedTextField(NumberFormat.getNumberInstance());
        inputField.setFont(new Font("New Times Roman",Font.BOLD,24));
        //inputField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textPanel.add(inputField,constraints);

        //Adds buttons to optionPanel and setting size
        historyBtn = new JButton("H");
        historyBtn.setPreferredSize(new Dimension(100,optionsPanel.getHeight()));
        deleteBtn = new JButton("<");
        deleteBtn.setPreferredSize(new Dimension(100,optionsPanel.getHeight()));
        optionsPanel.add(historyBtn,BorderLayout.WEST);
        optionsPanel.add(deleteBtn,BorderLayout.EAST);

        //Adds buttons to buttonPanel with their labels
        String [] buttonLabel = {"C","()","%","/","7","8","9","*","4","5","6","-","1","2","3","+","+/-","0",".","="};
        for (int i = 0; i <20 ; i++) {
            btn[i] = new JButton(buttonLabel[i]);
            buttonPanel.add(btn[i]);
            //Sets action Listener for each btn
            btn[i].addActionListener(this);
        }
    }

    //Event Handling for different components
    private void eventHandler() {
        //Event Handling for delete btn
        deleteBtn.addActionListener(this);

        //Event Handling for history btn
        historyBtn.addActionListener(this);

        //TODO When key is Typed, it outputs two chars, solve it. (Arbitrary commas too)
        //Event Handling for Keys (Uses ketAdapter and Anonymous inner class)
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                //With Help from Regex, checks if a typed character is not among allowed characters.
                // If true, it does not display that character (consumes). If not it appends to existing character.
                Pattern pattern = Pattern.compile("[^\\d.+\\-*/()]");
                Matcher matcher = pattern.matcher(Character.toString(e.getKeyChar()));//Converts char to String

                if (matcher.find()){
                    e.consume();
                }else{
                    inputField.setText( inputField.getText() + e.getKeyChar());
                }
                if(e.getSource().equals(KeyEvent.VK_DELETE)){   //Set delete Key
                    if(inputField.getText().length() == 0) { return; }
                    inputField.setText(inputField.getText().substring(0, inputField.getText().length() - 1));
                }
            }
        });
    }

    //Initializes a new frame alongside the history fame with predefined settings
    private void initializeFrame() {
        this.setTitle("SIMPLE CALCULATOR");
        this.setLocation(1500,200);
        this.setSize(new Dimension(360,500));
        this.setResizable(false);
        WindowListener wndCloser = new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        };
        this.addWindowListener(wndCloser);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Todo Find out if historyFrame closes when close btn is pressed or just disappears. Fix it.
        historyFrame = new JFrame("History");
        historyBtn.setLayout(new BorderLayout());
        historyFrame.setLocation(900,200);
        historyFrame.setSize(new Dimension(360,500));
        //historyFrame.setResizable(false);
        //historyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        historyArea = new JTextArea();
        historyAreaScrollPane = new JScrollPane(historyArea);
        historyClearBtn = new JButton("CLEAR HISTORY");
        historyClearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyArea.setText("");
            }
        });
        historyClearBtn.setPreferredSize(new Dimension(historyFrame.getWidth(),50));
        historyArea.setEditable(false);
        historyArea.setFont(new Font("New Times Roman",Font.BOLD,24));
        historyArea.setBackground(Color.pink);
        historyFrame.add(historyAreaScrollPane);
        historyFrame.add(historyClearBtn,BorderLayout.SOUTH);
    }

    @Override
    //Event handling for all buttons.
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn[0]){            // Clear(C) Btn
            inputField.setText("");
        }
        //Todo Complete implementation for button -> %
        else if(e.getSource() == btn[2]){
            //If input is NOT invalid and it is TRUE that it did NOT find a */()
            if (!inputInValidTest() && !Pattern.compile("[*/()]").matcher(inputField.getText()).find() ){
                //If it finds (+ or -) it should be exactly 1, it can't find (+ and -) occurrence and if found, it must be at start index else output error.
                if (Pattern.compile("[\\-+]").matcher(inputField.getText()).find() ){

                }
                double percent = Double.parseDouble(inputField.getText()) / 100;
                resultLbl.setText(Double.toString(percent));
                inputField.setText("");
            }else {
                resultLbl.setText("Invalid input");
                inputField.setText("");
            }
        }
        else if (e.getSource() == btn[16]){     // +/- Btn
           btn16Implementation();
        }
        else if (e.getSource() == deleteBtn){   // Delete(<) Btn
            if(inputField.getText().length() == 0) { return; }
            inputField.setText(inputField.getText().substring(0, inputField.getText().length() - 1));
        }
        else if (e.getSource() == historyBtn){  // History (H)Btn
            if (historyFrame.isVisible()){
                historyFrame.setVisible(false); }
            else{
                historyFrame.setVisible(true); }
        }
        else if (e.getSource() == btn[19]){     // Equals (=) Btn

            // 1st checks if input is valid
            if (inputInValidTest()){
                resultLbl.setText("Invalid input");
                inputField.setText("");
            }
            //else if input has no operators [digit=digit]
            else if (!Pattern.compile("[+\\-*/()]").matcher(inputField.getText()).find()){
                historyArea.append(inputField.getText() +  "\n");
                resultLbl.setText(inputField.getText());
                inputField.setText("");
            }
            else { // else performs calculation and output the results

                double secretDivisionByZeroCode = -0.9999989999;
                double result = Calculation.calculationRecursion(inputField.getText());
                //Checks for secretDivisionByZeroCode before applying default
                if ( secretDivisionByZeroCode == result) {
                    resultLbl.setText("ERROR");
                    inputField.setText("");
                } else {
                    historyArea.append(inputField.getText() + " = " + result + "\n");
                    inputField.setText("");
                    resultLbl.setText(Double.toString(result));
                }
            }
        }
        else{ //Every other Btn
                inputField.setText( inputField.getText() + e.getActionCommand());
        }
    }

    //Method implementing the +/- button
    private void btn16Implementation (){
        String input = inputField.getText();
        //First checks if field is empty.If it is it adds a minus sign.
        if (input.isEmpty()){
            inputField.setText("-");
        }
        //If not empty and in the form -(String), set to String
        else if (input.length()>1 && input.charAt(0) == '-' &&
                input.charAt(1) == '(' && input.charAt(input.length()-1) == ')'){
            inputField.setText( input.substring(2,input.length()-1));

        }
        //If of the form -String set to String
        else if(input.charAt(0) == '-'){
            inputField.setText(inputField.getText().substring(1));
        }
        //Else set String to -(String)
        else{
            inputField.setText("-(" + inputField.getText() + ")");
        }
    }

    /**
     * Checks if Input is valid for calculation
     * @return true if input is invalid
     * */
    private boolean inputInValidTest(){

        return (
            //Input is empty
            inputField.getText().isEmpty() ||

            //Illegal char at start
            Pattern.compile("[/*]").matcher(inputField.getText().substring(0,1)).find() ||

            //Illegal char at end
            Pattern.compile("[+\\-/*.]").matcher(Character.toString(inputField.getText().charAt(inputField.getText().length()-1))).find() ||

            // . Not followed by digit
            Pattern.compile("[.][^\\d]").matcher(inputField.getText()).find() ||

            // Operators not followed by digit
            Pattern.compile("[\\-+/*][^\\d.]").matcher(inputField.getText()).find() ||

            //Operator followed by . must be followed by a digit
            Pattern.compile("[\\-+/*][.][^\\d]").matcher(inputField.getText()).find() ||

            //If input has no digits
            !Pattern.compile("[\\d]").matcher(inputField.getText()).find()
        );

    }
}
