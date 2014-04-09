package paintByNumbers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InGUIDriver extends JFrame
  implements ActionListener
{
  private static final long serialVersionUID = 1234567890123456789L;
  private JButton theBigRedButton;
  private JButton theBigBlueButton;
  private JTextField myField;
  private JLabel myLabel;
  private JLabel puzzlable;
  private int puzzles = 9; private int puzzle = 1;
  private JRadioButton[] myButtons = new JRadioButton[this.puzzles];
  private JPanel myPanel;
  private boolean isPath = false;

  public InGUIDriver()
  {
    initialize();
  }

  public void initialize()
  {
    setTitle("Opening Screen");

    this.myPanel = new JPanel();
    this.myPanel.setLayout(new GridLayout(1, this.puzzles));
    for (int i = 0; i < this.puzzles; i++) {
      this.myButtons[i] = new JRadioButton(Integer.toString(i));
      this.myButtons[i].setActionCommand(Integer.toString(i));
      this.myButtons[i].setFocusable(false);
      this.myButtons[i].addActionListener(this);
      this.myPanel.add(this.myButtons[i]);
      if (i == 1) {
        this.myButtons[i].setSelected(true);
      }
    }
    this.theBigRedButton = new JButton("Quit");
    this.theBigRedButton.addActionListener(this);
    this.theBigRedButton.setActionCommand("quit");
    this.theBigRedButton.setFocusable(false);
    this.theBigBlueButton = new JButton("Puzzleage Time!");
    this.theBigBlueButton.addActionListener(this);
    this.theBigBlueButton.setActionCommand("new");
    this.theBigBlueButton.setFocusable(false);
    this.myLabel = new JLabel("or path to picture:");
    this.myField = new JTextField();
    this.myField.setText("Desktop/picture.gif");

    this.puzzlable = new JLabel("Pick a puzzle:");
    setLayout(new GridLayout(3, 2));

    add(this.puzzlable);
    add(this.myPanel);
    add(this.myLabel);
    add(this.myField);
    add(this.theBigRedButton);
    add(this.theBigBlueButton);
    setDefaultCloseOperation(3);
  }

  public void actionPerformed(ActionEvent arg0) {
    if (arg0.getActionCommand().equalsIgnoreCase("Quit")) {
      System.exit(128);
    }
    if (arg0.getActionCommand().equalsIgnoreCase("New")) {
      if (!this.isPath) {
				PixelGUIDriver pass = new PixelGUIDriver(this.puzzle - 1);
				pass.pack();
				pass.setVisible(true);
				setVisible(false);
      }
      else
      {
        try
        {
          PixelGUIDriver pass = new PixelGUIDriver(this.myField.getText());
          pass.pack();
          pass.setVisible(true);
          setVisible(false);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    else {
      this.isPath = arg0.getActionCommand().equals("0");
      this.myButtons[this.puzzle].setSelected(false);
      this.puzzle = Integer.parseInt(arg0.getActionCommand());
    }
  }

  public static void main(String[] args)
  {
    InGUIDriver input = new InGUIDriver();
    input.pack();
    input.setVisible(true);
  }
}
