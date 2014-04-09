package paintByNumbers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelGUIDriver extends JFrame
  implements ActionListener, MouseListener
{
  private static final long serialVersionUID = 3141592653589793238L;
  private JButton myButton;
  private JButton ruleButton;
  private JButton printButton;
  private JButton quitButton;
  private JPanel myPanel;
  private PixelGrid myGrid;
  private int startX;
  private int startY;

  public PixelGUIDriver()
  {
    this.myGrid = new PixelGrid();
    initialize();
  }

  public PixelGUIDriver(int puzzle) {
    this.myGrid = new PixelGrid(puzzle);
    initialize();
  }

  public PixelGUIDriver(String imageLocation)
    throws FileNotFoundException
  {
    this.myGrid = new PixelGrid(imageLocation);
    initialize();
  }

  public void initialize()
  {
    setTitle("Pixel Puzzles");

    this.myGrid.addMouseListener(this);
    this.printButton = new JButton("Print");
    this.printButton.addActionListener(this);
    this.printButton.setVisible(true);
    this.printButton.setActionCommand("print");
    this.myButton = new JButton("New Puzzle");
    this.myButton.addActionListener(this);
    this.myButton.setVisible(true);
    this.myButton.setActionCommand("new game");
    this.ruleButton = new JButton("Solve step");
    this.ruleButton.addActionListener(this);
    this.ruleButton.setVisible(true);
    this.ruleButton.setActionCommand("rules");
    this.quitButton = new JButton("Quit");
    this.quitButton.addActionListener(this);
    this.quitButton.setVisible(true);
    this.quitButton.setActionCommand("quit");
    this.myPanel = new JPanel();
    this.myPanel.add(this.printButton, "Center");
    this.myPanel.add(this.ruleButton, "East");

    this.myPanel.add(this.myButton, "West");
    setLayout(new BorderLayout());
    add(this.myGrid, "North");
    add(this.myPanel, "South");
    setDefaultCloseOperation(3);
  }

  public static void main(String[] args)
    throws FileNotFoundException
  {
    PixelGUIDriver pass = new PixelGUIDriver();
    pass.pack();
    pass.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("new game")) {
      InGUIDriver input = new InGUIDriver();
      input.pack();
      input.setVisible(true);
      setVisible(false);
    }

    if (e.getActionCommand().equals("rules")) {
      this.myGrid.solve();
      this.myGrid.removeMouseListener(this);
    }

    if (e.getActionCommand().equals("quit")) {
      System.exit(128);
    }

    if (e.getActionCommand().equals("print"))
    {
      for (int j = 0; j < this.myGrid.getGridHeight(); j++) {
        String output = "";
        for (int i = 0; i < this.myGrid.getGridWidth(); i++) {
          output = output + this.myGrid.getFinalSquare(i, j);
        }
        System.out.println(output);
      }
    }
  }

  public void mousePressed(MouseEvent e) {
    this.startX = this.myGrid.posToElement(e.getX());
    this.startY = this.myGrid.posToElement(e.getY());
  }

  public void mouseReleased(MouseEvent e) {
    int smallX = Math.min(this.startX, this.myGrid.posToElement(e.getX()));
    int bigX = Math.max(this.startX, this.myGrid.posToElement(e.getX()));
    int smallY = Math.min(this.startY, this.myGrid.posToElement(e.getY()));
    int bigY = Math.max(this.startY, this.myGrid.posToElement(e.getY()));
    try {
      for (int i = smallX; i <= bigX; i++) {
        for (int j = smallY; j <= bigY; j++)
          this.myGrid.change(i, j);
      }
    }
    catch (Exception localException)
    {
    }
    if (this.myGrid.gameOver())
      this.myGrid.removeMouseListener(this);
  }

  public void mouseClicked(MouseEvent e)
  {
  }

  public void mouseEntered(MouseEvent e)
  {
  }

  public void mouseExited(MouseEvent e)
  {
  }
}
