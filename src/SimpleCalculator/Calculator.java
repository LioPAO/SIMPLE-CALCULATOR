package SimpleCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//todo Implement calculations with parentheses ()
//Todo Set focus of JTextField to listen to keys at all time, so long as frame is active.
//NEW FEATURE
//TODO Add answerButton, to enable users retrieve and use answers after calculations. Place it in between History button and delete button, or shift the clear button

public class Calculator extends JFrame implements ActionListener {
    //Variables / Properties--------------------------------------------------------------------------------------------
    private JFrame historyFrame;
    private JPanel textPanel, optionsPanel, buttonPanel;
    private JTextArea historyArea;
    private JScrollPane historyAreaScrollPane;
    private JTextField inputField;
    private JLabel resultLbl = new JLabel("0");
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
        textPanel.add(resultLbl,constraints);

        //Create input field and its settings
        constraints.gridy =1;
        inputField = new JTextField();
        inputField.setFont(new Font("New Times Roman",Font.BOLD,24));
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

        //Event Handling for Keys (Uses ketAdapter and Anonymous inner class)
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                //With Help from Regex, checks if a typed character is not among allowed characters.
                // If true, it does not display that character by consuming the keyChar
                Pattern pattern = Pattern.compile("[^\\d.+\\-*/()]");
                Matcher matcher = pattern.matcher(Character.toString(e.getKeyChar()));//Converts char to String

                if (matcher.find()){
                    e.consume();
                    //inputField.getInputMap().put(KeyStroke.getKeyStroke(e.getKeyChar()),"none");
                }else{
                    inputField.requestFocusInWindow();
                }
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    computeInput();
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
            resultLbl.setText("");
        }
        else if(e.getSource() == btn[2]){
            btn02Implementation();
        }
        else if (e.getSource() == btn[16]){     // +/- Btn
             btn16Implementation();
        }
        else if (e.getSource() == btn[19]){     // Equals (=) Btn
            computeInput();
        }
        else if (e.getSource() == deleteBtn){   // Delete(<) Btn
            if(inputField.getText().isEmpty()) { return; }
            inputField.setText(inputField.getText().substring(0, inputField.getText().length() - 1));
        }
        else if (e.getSource() == historyBtn){  // History (H)Btn
            if (historyFrame.isVisible()){
                historyFrame.setVisible(false); }
            else{
                historyFrame.setVisible(true); }
        }
        else{                                   //Every other Btn
            inputField.setText( inputField.getText() + e.getActionCommand());
        }
    }

    private void btn02Implementation() {
        //If input is NOT invalid and it is TRUE that it did NOT find a */()
        if (!inputInValidTest(inputField) && !Pattern.compile("[*/()]").matcher(inputField.getText()).find() ){
            Pattern pattern = Pattern.compile("[+-]");
            Matcher matcher = pattern.matcher(inputField.getText());
            int count =0;
            //Counts the occurrences of + or -
            while (matcher.find()){
                count++;
            }
            //Invalid input if more than one occurrences or if 1 then it should be the first.
            if (count>1 || (count==1 && !Pattern.compile("^[\\-+]").matcher(inputField.getText()).find())){
                resultLbl.setText("Invalid input");
                inputField.setText(""); }
            else {
                double percent = Double.parseDouble(inputField.getText()) / 100;
                percent = BigDecimal.valueOf(percent).setScale(10, RoundingMode.HALF_UP).doubleValue();
                resultLbl.setText(Double.toString(percent));
                inputField.setText(""); }
        }else {
            resultLbl.setText("Invalid input");
            inputField.setText("");
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
     * @param input which is of type JTextField
     * @return true if input is invalid
     * */
    private boolean inputInValidTest(JTextField input){

        return (
            //Input is empty
            input.getText().isEmpty() ||

            //Illegal char at start
            //Pattern.compile("[/*]").matcher(input.getText().substring(0,1)).find() ||
                    Pattern.compile("^[*/]").matcher(input.getText()).find() ||
            //Illegal char at end
            Pattern.compile("[+\\-/*.]").matcher(Character.toString(input.getText().charAt(input.getText().length()-1))).find() ||

            // . Not followed by digit
            Pattern.compile("[.][^\\d]").matcher(input.getText()).find() ||

            // . followed by a digit followed by a .
            Pattern.compile("[.][\\d]*[.]").matcher(input.getText()).find() ||


            // Operators not followed by digit or .
            Pattern.compile("[\\-+/*][^\\d.]").matcher(input.getText()).find() ||

            //Operator followed by . must be followed by a digit
            Pattern.compile("[\\-+/*][.][^\\d]").matcher(input.getText()).find() ||

                    //Pattern.compile("([.][^\\d]) | ([\\-+/*][^\\d.]) | ([\\-+/*][.][^\\d])").matcher(inputField.getText()).find() ||

            //If input has no digits
            !Pattern.compile("[\\d]").matcher(input.getText()).find()
        );
    }

    //  Computes calculation on Input called , this method can be reused.
    private void computeInput() {
        // 1st checks if input is valid
        if (inputInValidTest(inputField)){
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
}
