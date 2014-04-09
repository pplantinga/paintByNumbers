package paintByNumbers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PixelGrid extends JPanel
{
  private static final long serialVersionUID = 1788004601988008863L;
  private int[][] myGrid;
  private int[][] finalGrid;
  private int myWidth;
  private int myHeight;
  private int scaleFactor = 12; private int longest = 1;
  private ArrayList<ArrayList<Integer>> myHorizontal;
  private ArrayList<ArrayList<Integer>> myVertical;
  private String[] puzzles = { "test1.txt", "test2.txt", "test3.txt", "test4.txt", "brad.txt", "cheese.txt", "treble.txt", "scorp.txt" };

  protected int imageType = 1;
  protected BufferedImage bufferedImage;
  protected WritableRaster raster;

  public PixelGrid()
  {
    readFile("cheese.txt");
    initialize();
  }

  public PixelGrid(int puzzle) {
    readFile(this.puzzles[puzzle]);
    initialize();
  }

  public PixelGrid(int[][] grid) {
    this.finalGrid = grid;
    initialize();
  }

  public PixelGrid(String imageLocation) throws FileNotFoundException {
    load(imageLocation);

    initialize();
  }

  public void initialize() {
    this.myWidth = this.finalGrid[0].length;
    this.myHeight = this.finalGrid.length;
    this.myGrid = new int[this.myHeight][this.myWidth];
    this.myHorizontal = new ArrayList();
    this.myVertical = new ArrayList();
    boolean last = false;
    for (int i = 0; i < this.myHeight; i++) {
      this.myHorizontal.add(new ArrayList());
      for (int j = 0; j < this.myWidth; j++) {
        if ((this.finalGrid[i][j] == 2) && (!last)) {
          ((ArrayList)this.myHorizontal.get(i)).add(Integer.valueOf(1));
          last = true;
        } else if ((this.finalGrid[i][j] == 2) && (last)) {
          int size = ((ArrayList)this.myHorizontal.get(i)).size();
          int value = ((Integer)((ArrayList)this.myHorizontal.get(i)).get(size - 1)).intValue();
          ((ArrayList)this.myHorizontal.get(i)).set(size - 1, Integer.valueOf(value + 1));
        } else if ((this.finalGrid[i][j] != 2) && (last)) {
          last = false;
        }
      }
      last = false;
      if (((ArrayList)this.myHorizontal.get(i)).size() == 0)
        ((ArrayList)this.myHorizontal.get(i)).add(Integer.valueOf(0));
      if (((ArrayList)this.myHorizontal.get(i)).size() > this.longest) {
        this.longest = ((ArrayList)this.myHorizontal.get(i)).size();
      }
    }

    last = false;
    for (int i = 0; i < this.myWidth; i++) {
      this.myVertical.add(new ArrayList());
      for (int j = 0; j < this.myHeight; j++) {
        if ((this.finalGrid[j][i] == 2) && (!last)) {
          ((ArrayList)this.myVertical.get(i)).add(Integer.valueOf(1));
          last = true;
        } else if ((this.finalGrid[j][i] == 2) && (last)) {
          int size = ((ArrayList)this.myVertical.get(i)).size();
          int value = ((Integer)((ArrayList)this.myVertical.get(i)).get(size - 1)).intValue();
          ((ArrayList)this.myVertical.get(i)).set(size - 1, Integer.valueOf(value + 1));
        } else if ((this.finalGrid[j][i] != 2) && (last)) {
          last = false;
        }
      }
      last = false;
      if (((ArrayList)this.myVertical.get(i)).size() == 0)
        ((ArrayList)this.myVertical.get(i)).add(Integer.valueOf(0));
      if (((ArrayList)this.myVertical.get(i)).size() > this.longest) {
        this.longest = ((ArrayList)this.myVertical.get(i)).size();
      }
    }
    setPreferredSize(new Dimension(this.myHeight * this.scaleFactor + 1 + this.longest * this.scaleFactor, this.myWidth * this.scaleFactor + this.longest * 
      this.scaleFactor + 1));
  }

  public void paintComponent(Graphics pen) {
    super.paintComponent(pen);
    int size = 0;
    for (int i = 0; i <= this.myHeight; i++) {
      if (i < this.myHeight) {
        size = ((ArrayList)this.myHorizontal.get(i)).size();
        for (int j = 0; j < size; j++) {
          if (((Integer)((ArrayList)this.myHorizontal.get(i)).get(j)).intValue() < 10) {
            pen.drawString(Integer.toString(((Integer)((ArrayList)this.myHorizontal.get(i)).get(j)).intValue()), this.scaleFactor * this.longest + i * this.scaleFactor + 3, 
              this.scaleFactor * this.longest - this.scaleFactor * (size - j - 1) - 2);
          } else {
            pen.setFont(new Font("default", 0, 9));
            pen.drawString(Integer.toString(((Integer)((ArrayList)this.myHorizontal.get(i)).get(j)).intValue()), this.scaleFactor * this.longest + i * this.scaleFactor + 0, 
              this.scaleFactor * this.longest - this.scaleFactor * (size - j - 1) - 2);
            pen.setFont(new Font("default", 0, 12));
          }
        }
      }
      if (i % 5 != 0)
        pen.setColor(Color.gray);
      pen.drawLine(this.scaleFactor * this.longest + i * this.scaleFactor, 0, this.scaleFactor * this.longest + i * this.scaleFactor, this.myWidth * 
        this.scaleFactor + this.longest * this.scaleFactor);
      pen.setColor(Color.black);
    }
    for (int i = 0; i <= this.myWidth; i++) {
      if (i < this.myWidth) {
        size = ((ArrayList)this.myVertical.get(i)).size();
        for (int j = 0; j < size; j++) {
          if (((Integer)((ArrayList)this.myVertical.get(i)).get(j)).intValue() < 10) {
            pen.drawString(Integer.toString(((Integer)((ArrayList)this.myVertical.get(i)).get(j)).intValue()), this.scaleFactor * this.longest - this.scaleFactor * (
              size - j - 1) - 10, this.scaleFactor * this.longest + (i + 1) * this.scaleFactor - 1);
          } else {
            pen.setFont(new Font("default", 0, 9));
            pen.drawString(Integer.toString(((Integer)((ArrayList)this.myVertical.get(i)).get(j)).intValue()), this.scaleFactor * this.longest - this.scaleFactor * (
              size - j - 1) - 12, this.scaleFactor * this.longest + (i + 1) * this.scaleFactor - 2);
            pen.setFont(new Font("default", 0, 12));
          }
        }
      }
      if (i % 5 != 0)
        pen.setColor(Color.gray);
      pen.drawLine(0, this.scaleFactor * this.longest + i * this.scaleFactor, this.scaleFactor * this.longest + this.myHeight * this.scaleFactor, this.scaleFactor * 
        this.longest + i * this.scaleFactor);
      pen.setColor(Color.black);
    }
    for (int i = 0; i <= this.myHeight; i += 5) {
      pen.drawLine(this.scaleFactor * this.longest + i * this.scaleFactor, 0, this.scaleFactor * this.longest + i * this.scaleFactor, this.myWidth * 
        this.scaleFactor + this.longest * this.scaleFactor);
    }
    for (int i = 0; i < this.myHeight; i++) {
      for (int j = 0; j < this.myWidth; j++) {
        paintBlock(pen, i, j);
      }
    }

    if (gameOver()) {
      pen.setFont(new Font("default", 0, 20));
      pen.setColor(Color.blue);
      pen.drawString("YOU SOLVED IT!!!", 0, 0);
    }
  }

  public void paintBlock(Graphics pen, int x, int y) {
    if (this.myGrid[x][y] == 0) {
      pen.setColor(Color.white);
      pen.fillRect(this.scaleFactor * this.longest + x * this.scaleFactor + 1, this.scaleFactor * this.longest + y * this.scaleFactor + 1, 
        this.scaleFactor - 2, this.scaleFactor - 2);
    } else if (this.myGrid[x][y] == 2) {
      pen.setColor(Color.black);
      pen.fillRect(this.scaleFactor * this.longest + x * this.scaleFactor, this.scaleFactor * this.longest + y * this.scaleFactor, this.scaleFactor, 
        this.scaleFactor);
    } else {
      pen.setColor(Color.white);
      pen.fillRect(this.scaleFactor * this.longest + x * this.scaleFactor + 1, this.scaleFactor * this.longest + y * this.scaleFactor + 1, 
        this.scaleFactor - 2, this.scaleFactor - 2);
      pen.setColor(Color.black);
      pen.drawString(".", this.scaleFactor * this.longest + x * this.scaleFactor + this.scaleFactor / 2 - 1, this.scaleFactor * this.longest + y * 
        this.scaleFactor + this.scaleFactor / 2 + 2);
    }
  }

  public int getSquare(int x, int y) {
    return this.myGrid[x][y];
  }

  public int getFinalSquare(int x, int y) {
    return this.finalGrid[x][y];
  }

  public int[][] getGrid() {
    return this.myGrid;
  }

  public int[][] getFinalGrid() {
    return this.finalGrid;
  }

  public final int posToElement(int pos) {
    if (pos - this.longest * this.scaleFactor < 0)
      throw new IllegalArgumentException("Illegal position");
    return (pos - this.longest * this.scaleFactor) / this.scaleFactor;
  }

  public boolean solve()
  {
    this.myGrid = this.finalGrid;
    repaint();

    boolean change = false;

    for (int i = 0; i < this.myWidth; i++);
    for (int i = 0; i < this.myHeight; i++)
    {
      for (int j = 0; j < this.myWidth; j++);
    }

    repaint();
    return change;
  }

  public void change(int x, int y)
  {
    this.myGrid[x][y] += 1;
    if (this.myGrid[x][y] == 3)
      this.myGrid[x][y] = 0;
    if (gameOver()) {
      this.myGrid = this.finalGrid;
    }

    repaint();
  }

  public void readFile(String fileName) {
    BufferedReader br = null;
    InputStreamReader is = null;
    is = new InputStreamReader(getClass().getResourceAsStream(fileName));
    br = new BufferedReader(is);
    ArrayList grid = new ArrayList();
    try
    {
      int j = 0;
      String line;
      while ((line = br.readLine()) != null)
      {
        grid.add(new ArrayList());
        for (int i = 0; i < line.length(); i++) {
          ((ArrayList)grid.get(j)).add(Character.toString(line.charAt(i)));
        }
        j++;
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.myWidth = ((ArrayList)grid.get(0)).size();
    this.myHeight = grid.size();
    this.finalGrid = new int[this.myWidth][this.myHeight];

    for (int i = 0; i < this.myWidth; i++)
      for (int j = 0; j < this.myHeight; j++)
        this.finalGrid[i][j] = Integer.parseInt((String)((ArrayList)grid.get(j)).get(i));
  }

  public void load(String filename)
    throws FileNotFoundException
  {
    File f = new File(filename);
    if (!f.exists())
      throw new FileNotFoundException("File not found: " + filename);
    ImageIcon icon = new ImageIcon(filename);
    Image image = icon.getImage();
    this.bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), this.imageType);
    Graphics g = this.bufferedImage.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();

    this.raster = this.bufferedImage.getRaster();
    int width = image.getWidth(null); int height = image.getHeight(null);

    width -= width % this.scaleFactor;
    height -= height % this.scaleFactor;
    int arrayHeight;
    int arrayWidth;
    if (width / height > 1.5D) {
      arrayWidth = 100;
      arrayHeight = (int)(100.0D * height / width);
    } else {
      arrayHeight = 45;
      arrayWidth = (int)(45.0D * width / height);
    }

    int[][] grid = new int[arrayWidth][arrayHeight];
    int count = 0;
    int[] pixelArray = (int[])null;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        pixelArray = this.raster.getPixel(i, j, pixelArray);
        count += pixelArray[0] + pixelArray[1] + pixelArray[2];
        pixelArray = (int[])null;
      }
    }
    int average = count / (width * height);
    double widthFactor = width / arrayWidth; double heightFactor = height / arrayHeight;

    for (int i = 0; i < arrayWidth; i++) {
      for (int j = 0; j < arrayHeight; j++) {
        count = 0;

        for (int k = (int)(i * widthFactor); k < (i + 1) * widthFactor; k++) {
          for (int l = (int)(j * heightFactor); l < (j + 1) * heightFactor; l++) {
            pixelArray = this.raster.getPixel(k, l, pixelArray);
            count += pixelArray[0] + pixelArray[1] + pixelArray[2];
            pixelArray = (int[])null;
          }
        }
        double squareAverage = count / (widthFactor * heightFactor);
        if (squareAverage < average)
          grid[i][j] = 2;
        else {
          grid[i][j] = 0;
        }
      }
    }
    this.finalGrid = grid;
  }

  public boolean gameOver() {
    for (int i = 0; i < this.myHeight; i++) {
      for (int j = 0; j < this.myWidth; j++) {
        if (((this.myGrid[i][j] != 2) && (this.finalGrid[i][j] == 2)) || ((this.myGrid[i][j] == 2) && (this.finalGrid[i][j] != 2)))
          return false;
      }
    }
    return true;
  }

  public int getGridWidth() {
    return this.myHeight;
  }

  public int getGridHeight() {
    return this.myWidth;
  }
}
