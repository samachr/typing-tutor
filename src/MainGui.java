import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by sam on 10/21/14.
 */
public class MainGui extends JFrame implements KeyListener, ActionListener {
    private String prompts[] = {"A typed word is counted as any five keystrokes.",
            "Do not stop to correct your errors in these first tests but check them out.",
            "The beautiful scenic country of New Zealand is situated in the South Pacific to the east of Australia.",
            "The ferry crosses Cook Strait and cruises beautiful Queen Charlotte Sounds between Wellington, NZ's Capital City, and Picton.",
            "NZ's East Coast has many stretches of white sand and rolling surf which attract NZ and overseas surfers. They are popular NZ fmaily holiday places.",
            "New Zealand is a land of contrasts, which attract thousands of overseas tourists every year to climb, ski or snowboard our mountains, swim, fish or cruise on our lakes and rivers.",
            "Between The Southern Alps and the West Coast is a fantastic scenic drive taking the Haast Pass road. Here is our great rain forest. Most overseas and local tours include this route in their itinerary.",
            "New Zealand is a very sport oriented country and sometimes hosts world events. Sports include tennis, golf, yachting, canoeing, cruising, cricket, soccer, rugby, basketball, netball, swimming, surf lifesaving, athletics, and riding",
            "Watching events where they take place is fine but many can only watch at home as the event is screened on our TVs. New Zealand is proud too of our sporting participants who have entered and gained medals in many sporting events including Olympic Games.",
            "Masters' Games are very popular in New Zealand as in many other countries and NZ swimmers were really proud in the year 2002 to host the FINA World Masters Swimming Champs at Christchurch in the South Island, at which I gained 10th place medals for 100 m and 200 m backstroke.",
            "Some challenging events which draw overseas sports people include the annual Coast to Coast involving running, cycling and kayaking from the West Coast, through mountain passes to the East Coast, and the Iron Man including running, cycling, swimming. I am proud one of my sons twice took part in the Ironman"};

    private String keys[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ",
            "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "\nEnter",
            "a", "s", "d", "f", "g", "h", "j", "k", "l", ";", "'",
            "z", "x", "c", "v", "b", "n", "m", ",", ".", "/", "Shift"};

    private String shiftKeys[] = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", " ",
            "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "\nEnter",
            "A", "S", "D", "F", "G", "H", "J", "K", "L", ":", "\"",
            "Z", "X", "C", "V", "B", "N", "M", "<", ">", "?", "Shift"};

    private JButton virtualKeys[];
    private JButton btnPreviousPrompt, btnNextPrompt;
    private JMenuBar mbrMenuBar;
    private JMenu fileMenu;
    private JMenuItem quit, showStats, restart;

    private boolean shiftisDown;
    private Color defaultColor;
    private JLabel lblPrompt, lblErrorCount, lblCorrectCount;
    private int errorCount;
    private int currentPrompt;

    private int correctCount;

    private LinkedList<ErrorKey> errorKeyFrequency = new LinkedList<ErrorKey>();

    public MainGui() {
        this.setLayout(null);
        this.setTitle("Typing Tutor");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        errorCount = 0;
        currentPrompt = 0;
        shiftisDown = false;

        int btnSize = 50;

        Container pane = getContentPane();

        this.virtualKeys = new JButton[44];

        makeVirtualKeyboard(pane, btnSize);

        defaultColor = virtualKeys[0].getBackground();

        btnNextPrompt = new JButton("Next Prompt");
        //next to the space bar
        btnNextPrompt.setLocation(virtualKeys[10].getLocation().x + virtualKeys[10].getSize().width + 2, virtualKeys[10].getLocation().y);
        btnNextPrompt.setSize(btnSize * 3, btnSize);
        btnNextPrompt.addActionListener(this);
        pane.add(btnNextPrompt);

        btnPreviousPrompt = new JButton("Previous Prompt");
        //next to the space bar
        btnPreviousPrompt.setLocation(virtualKeys[10].getLocation().x - 3 * btnSize - 2, virtualKeys[10].getLocation().y);
        btnPreviousPrompt.setSize(btnSize * 3, btnSize);
        btnPreviousPrompt.addActionListener(this);
        pane.add(btnPreviousPrompt);

        lblPrompt = new JLabel(prompts[currentPrompt]);
        lblPrompt.setLocation(5, 10);
        lblPrompt.setSize(500, 20);
        pane.add(lblPrompt);

        lblCorrectCount = new JLabel("Correct: 0");
        lblCorrectCount.setLocation(535, 10);
        lblCorrectCount.setSize(150, 20);
        pane.add(lblCorrectCount);

        lblErrorCount = new JLabel("Errors: 0");
        lblErrorCount.setLocation(535, 30);
        lblErrorCount.setSize(150, 20);
        pane.add(lblErrorCount);

        restart = new JMenuItem("Restart");
        restart.addActionListener(this);
        showStats = new JMenuItem("Show Stats");
        showStats.addActionListener(this);
        quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        fileMenu = new JMenu("File");
        mbrMenuBar = new JMenuBar();
        fileMenu.add(restart);
        fileMenu.add(showStats);
        fileMenu.add(quit);
        mbrMenuBar.add(fileMenu);
        this.setJMenuBar(mbrMenuBar);

        this.setSize(btnSize * 12 + 5 + btnSize / 2, 5 * btnSize + 60 + 25 );

        //this.setResizable(false);
        this.addKeyListener(this);
        this.setVisible(true);
        this.requestFocus();
    }

    private void makeVirtualKeyboard(Container pane, int btnSize) {
        for (int row = 0; row < 4; row++) {
            for (int i = 0; i < 11; i++) {
                virtualKeys[i + row * 11] = new JButton(keys[i + row * 11]);
                virtualKeys[i + row * 11].setSize(btnSize, btnSize);
                virtualKeys[i + row * 11].setLocation(i * (btnSize + 2) + 5 + row * (btnSize / 2), row * (btnSize + 2) + 50);
                virtualKeys[i + row * 11].setFocusable(false);
                virtualKeys[i + row * 11].setEnabled(false);
                virtualKeys[i + row * 11].setFont(new Font(virtualKeys[i + row * 11].getFont().getName(), Font.BOLD, 12));
                pane.add(virtualKeys[i + row * 11]);
            }
        }

        //shift key
        virtualKeys[43].setLocation(5, virtualKeys[43].getLocation().y);
        virtualKeys[43].setSize((int) (1.5 * btnSize - 2), virtualKeys[43].getSize().height);

        //the enter key
        virtualKeys[21].setVisible(false);
        virtualKeys[21].setLocation(virtualKeys[32].getLocation().x + btnSize + 2, virtualKeys[32].getLocation().y);
        virtualKeys[21].setSize((int) (1.5 * btnSize - 2), virtualKeys[21].getSize().height);

        //the space bar
        virtualKeys[10].setLocation(virtualKeys[35].getLocation().x, virtualKeys[35].getLocation().y + btnSize + 2);
        virtualKeys[10].setSize(5 * btnSize + 8, virtualKeys[10].getSize().height);
    }

    private void toggleCapitals() {
        if (virtualKeys[0].getText().equals("1")) {
            for (int i = 0; i < 43; i++) {
                virtualKeys[i].setText(shiftKeys[i]);
            }
        } else {
            for (int i = 0; i < 43; i++) {
                virtualKeys[i].setText(keys[i]);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        boolean correctKeyPressed = false;
        char keyPressed = e.getKeyChar();
        int keyCode = e.getKeyCode();

        //check for correct characters and advance the text or add to the error count.
        if (keyPressed == lblPrompt.getText().charAt(0)) {

            doAnimation(keyPressed);

            correctKeyPressed = true;
            correctCount++;
            lblCorrectCount.setText("Correct: " + correctCount);

            lblPrompt.setText(lblPrompt.getText().substring(1));//remove the correct character

            //advance the prompt if it is empty
            if (lblPrompt.getText().equals("")) {
                if (currentPrompt < 10) {
                    lblPrompt.setText(prompts[++currentPrompt]);
                } else {
                    showEndPage();
                }
            }
        } else if (keyCode != 16 && keyCode != 37 && keyCode != 39) {
            errorCount++;

            //keeping track of top 5 most typed wrong keys in a linked list
            //search for the new missed key, if found, increment the frequency,
            //if not found, add it to the list and sort.
            boolean found = false;
            for  (ErrorKey i : errorKeyFrequency) {
                if (i.getKey() == e.getKeyChar()) {
                    i.setFrequency(i.getFrequency() + 1);
                    found = true;
                }
            }
            if (!found) {
                errorKeyFrequency.add(new ErrorKey(e.getKeyChar()));
                Collections.sort(errorKeyFrequency);
            } else {
                Collections.sort(errorKeyFrequency);
            }
            lblErrorCount.setText("Errors: " + Integer.toString(errorCount));
        }

        //------------------------Virtual Keyboard Highlighting-------------------

        if (keyCode == 16 && !shiftisDown) { //16 is shift
            toggleCapitals(); //show the keys capitalized
            shiftisDown = true;
            virtualKeys[43].setBackground(Color.blue); //43 is the shift key on virtual keyboard
        } else {
            for (int i = 0; i < 43; i++) { //find the key and highlight it
                if (keyPressed == ((shiftisDown) ? shiftKeys[i].charAt(0) : keys[i].charAt(0))) {
                    virtualKeys[i].setBackground((correctKeyPressed) ? Color.green : Color.red);
                }
            }
        }

        //this is a hack to make sure the last key pressed at the last prompt doesn't stay highlighted
        if (currentPrompt > 9) {
            for (int i = 0; i < 43; i++) { //find the key and unhighlight it
                if (keyPressed == ((shiftisDown) ? shiftKeys[i].charAt(0) : keys[i].charAt(0))) {
                    virtualKeys[i].setBackground(defaultColor);
                }
            }
        }
    }

    private void doAnimation(char keyPressed) {
        JLabel temp = new JLabel(lblPrompt.getText().substring(0,1));
        if (keyPressed == ' ') {temp.setText("_");}
        temp.setLocation(10, 10);
        temp.setSize(20, 20);
        this.getContentPane().add(temp);
        new GravityAnimator(temp, 600, 50, true).animate(1150);
    }

    private void showEndPage() {
        String mostMissed = (errorCount != 0) ? "Top five most missed keys:\n" : "Congratulations, You made no mistakes!!\n";
        for (int i = 0; i < 5; i++) {
            if (i < errorKeyFrequency.size()) {
                mostMissed += errorKeyFrequency.get(i).getKey() + ": " + errorKeyFrequency.get(i).getFrequency() + "\n";
            }
        }

        if (currentPrompt > 9) {
            lblPrompt.setText(" ");
            showEndAnimation();
        }

        JOptionPane.showMessageDialog(this, "Statistics \n\n" +
                mostMissed + "\n" +
                "Accuracy: " + String.format("%.2f", ((double)correctCount / (errorCount + correctCount)) * 100) + "%\n" +
                "Total Correct: " + correctCount + "\n" +
                "Total Errors: " + errorCount + "\n" +
                "Total Key's pressed: " + (errorCount + correctCount));
    }

//start the gravityanimations at slightly offset times.
    private Timer offsetTime = new Timer(20, new ActionListener() {
        private int count = 43;
        Random rand = new Random();
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (count < 0) {offsetTime.stop(); count = 43; return;}
            new GravityAnimator(virtualKeys[count], 600, 300, false, rand.nextInt() % 10 - 5, rand.nextInt() % 10 - 10).animate(4500);
            count--;
        }
    });

    private void showEndAnimation() {

        offsetTime.start();

        Random rand = new Random();

        new GravityAnimator(lblPrompt, 600, 300, false, rand.nextInt() % 10 - 5, rand.nextInt() % 10 - 5).animate(5000);
        new GravityAnimator(lblCorrectCount, 600, 300, false, rand.nextInt() % 10 - 5, rand.nextInt() % 10 - 5).animate(5000);
        new GravityAnimator(lblErrorCount, 600, 300, false, rand.nextInt() % 10 - 5, rand.nextInt() % 10 - 5).animate(5000);
        new GravityAnimator(btnNextPrompt, 600, 300, false, rand.nextInt() % 10 - 5, rand.nextInt() % 10 - 5).animate(5000);
        new GravityAnimator(btnPreviousPrompt, 600, 300, false, rand.nextInt() % 10 - 5, rand.nextInt() % 10 - 5).animate(5000);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //undo highlighting
        for (int i = 0; i < 43; i++) {
            if (e.getKeyChar() == shiftKeys[i].toCharArray()[0] || e.getKeyChar() == keys[i].toCharArray()[0]) {
                virtualKeys[i].setBackground(defaultColor);
            }
        }

        if (e.getKeyCode() == 16) {
            toggleCapitals();
            shiftisDown = false;
            virtualKeys[43].setBackground(defaultColor);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnNextPrompt)) {
            if (currentPrompt < 10)
                lblPrompt.setText(prompts[++currentPrompt]);
            else
                showEndPage();
            this.requestFocus(); //make sure the focus stays on the JPanel so we can receive input
        } else if (e.getSource().equals(btnPreviousPrompt)) {
            if (currentPrompt > 0)
                lblPrompt.setText(prompts[--currentPrompt]);
            this.requestFocus(); //make sure the focus stays on the JPanel so we can receive input
        } else if (e.getSource().equals(restart)) {
            currentPrompt = 0;
            lblPrompt.setText(prompts[currentPrompt]);
            errorCount = 0;
            correctCount = 0;
            lblCorrectCount.setText("Correct: 0");
            lblErrorCount.setText("Errors: 0");
            errorKeyFrequency.removeAll(errorKeyFrequency);
        } else if (e.getSource().equals(quit)) {
            System.exit(0);
        } else if (e.getSource().equals(showStats)) {
            showEndPage();
        }
        this.requestFocus();
    }
}
