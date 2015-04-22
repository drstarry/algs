// Finding and removing a seam involves three parts and a tiny bit of notation:

// Notation. In image processing, pixel (x, y) refers to the pixel in column x and row y, with pixel (0, 0) at the upper left corner and pixel (W − 1, H − 1) at the bottom right corner. This is consistent with the Picture data type in stdlib.jar. Warning: this is the opposite of the standard mathematical notation used in linear algebra where (i, j) refers to row i and column j and with Cartesian coordinates where (0, 0) is at the lower left corner.
// a 3-by-4 image
//   (0, 0)      (1, 0)      (2, 0)
//   (0, 1)      (1, 1)      (2, 1)
//   (0, 2)      (1, 2)      (2, 2)
//   (0, 3)      (1, 3)      (2, 3)
// We also assume that the color of a pixel is represented in RGB space, using three integers between 0 and 255. This is consistent with the java.awt.Color data type.

// Energy calculation. The first step is to calculate the energy of each pixel, which is a measure of the importance of each pixel—the higher the energy, the less likely that the pixel will be included as part of a seam (as we'll see in the next step). In this assignment, you will implement the dual gradient energy function, which is described below. Here is the dual gradient of the surfing image above:
// Dr. Hug as energy
// The energy is high (white) for pixels in the image where there is a rapid color gradient (such as the boundary between the sea and sky and the boundary between the surfing Josh Hug on the left and the ocean behind him). The seam-carving technique avoids removing such high-energy pixels.

// Seam identification. The next step is to find a vertical seam of minimum total energy. This is similar to the classic shortest path problem in an edge-weighted digraph except for the following:
// The weights are on the vertices instead of the edges.
// We want to find the shortest path from any of the W pixels in the top row to any of the W pixels in the bottom row.
// The digraph is acyclic, where there is a downward edge from pixel (x, y) to pixels (x − 1, y + 1), (x, y + 1), and (x + 1, y + 1), assuming that the coordinates are in the prescribed range.
// Vertical Seam
// Seam removal. The final step is to remove from the image all of the pixels along the seam.

// Corner cases. Your code should throw an exception when called with invalid arguments.

// By convention, the indices x and y are integers between 0 and W − 1 and between 0 and H − 1 respectively, where W is the width of the curent image and H is the height. Throw a java.lang.IndexOutOfBoundsException if either x or y is outside its prescribed range.
// Throw a java.lang.NullPointerException if either removeVerticalSeam() or removeHorizontalSeam() is called with a null argument.
// Throw a java.lang.IllegalArgumentException if removeVerticalSeam() or removeHorizontalSeam() is called with an array of the wrong length or if the array is not a valid seam (i.e., either an entry is outside its prescribed range or two adjacent entries differ by more than 1).
// Throw a java.lang.IllegalArgumentException if either removeVerticalSeam() or removeHorizontalSeam() is called when either the width or height is less than or equal to 1.
// Constructor. The data type may not mutate the Picture argument to the constructor.
// Computing the energy of a pixel. We will use the dual gradient energy function: The energy of pixel (x, y) is Δx2(x, y) + Δy2(x, y), where the square of the x-gradient Δx2(x, y) = Rx(x, y)2 + Gx(x, y)2 + Bx(x, y)2, and where the central differences Rx(x, y), Gx(x, y), and Bx(x, y) are the absolute value in differences of red, green, and blue components between pixel (x + 1, y) and pixel (x − 1, y). The square of the y-gradient Δy2(x, y) is defined in an analogous manner. We define the energy of pixels at the border of the image to be 2552 + 2552 + 2552 = 195075.
// As an example, consider the 3-by-4 image with RGB values (each component is an integer between 0 and 255) as shown in the table below.

//   (255, 101, 51)      (255, 101, 153)     (255, 101, 255)
//   (255,153,51)        (255,153,153)       (255,153,255)
//   (255,203,51)        (255,204,153)       (255,205,255)
//   (255,255,51)        (255,255,153)       (255,255,255)
// The ten border pixels have energy 195075. Only the pixels (1, 1) and (1, 2) are nontrivial. We calculate the energy of pixel (1, 2) in detail:

// Rx(1, 2) = 255 − 255 = 0,
// Gx(1, 2) = 205 − 203 = 2,
// Bx(1, 2) = 255 − 51 = 204,
// yielding Δx2(1, 2) = 22 + 2042 = 41620.

// Ry(1, 2) = 255 − 255 = 0,
// Gy(1, 2) = 255 − 153 = 102,
// By(1, 2) = 153 − 153 = 0,
// yielding Δy2(1, 2) = 1022 = 10404.

// Thus, the energy of pixel (1, 2) is 41620 + 10404 = 52024. Similarly, the energy of pixel (1, 1) is 2042 + 1032 = 52225.

import java.awt.Color;
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;
import java.lang.Math;
import java.lang.NullPointerException;
import java.util.Hashtable;

public class SeamCarver {

   Picture pic;
   int width;
   int height;
   double[][] engGrid;

   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture) {
      pic = new Picture(picture);
      width = pic.width();
      height = pic.height();
      engGrid = new double[height][width];
      for (int col = 0; col < width; col ++)
         for (int row = 0; row < height; row ++) {
            engGrid[row][col] = computeEng(col, row);
         }
   }

   private double computeEng(int col, int row) {
      double deltaX = 0.0, deltaY = 0.0;
      Color x1, x2, y1, y2, nil = new Color(0, 0, 0);
      x1 = (col == 0) ? nil : pic.get(col - 1, row);
      x2 = (col == width - 1) ? nil : pic.get(col + 1, row);
      y1 = (row == 0) ? nil : pic.get(col, row - 1);
      y2 = (row == height - 1) ? nil : pic.get(col, row + 1);
      deltaX += Math.pow((x1.getRed() - x2.getRed()), 2) + Math.pow((x1.getGreen() - x2.getGreen()), 2) + Math.pow((x1.getBlue() - x2.getBlue()), 2);
      deltaY += Math.pow((y1.getRed() - y2.getRed()), 2) + Math.pow((y1.getGreen() - y2.getGreen()), 2) + Math.pow((y1.getBlue() - y2.getBlue()), 2);
      return deltaX + deltaY;
   }

   // current picture
   public Picture picture() {
      return pic;
   }

   // width of current picture
   public int width() {
      return width;
   }

   // height of current picture
   public int height() {
      return height;
   }

   // energy of pixel at column x and row y
   public double energy(int x, int y) {
      if (x < 0 || x >= width() || y < 0 || y >= height())
         throw new IndexOutOfBoundsException();
      return engGrid[y][x];
   }

   // private int[] shortestPath(String mode, ) {

   // }

   // private
   // // sequence of indices for horizontal seam
   // public int[] findHorizontalSeam() {

   // }

   // // sequence of indices for vertical seam
   // public int[] findVerticalSeam() {

   // }

   // private boolean isValidSeam(int[] seam) {
   //    for (int i = 0; i < seam.length - 1; i++) {
   //       if (Math.abs(seam[i] - seam[i + 1]) > 1)
   //          return false;
   //    }
   //    return true;
   // }

   // // remove horizontal seam from current picture
   // public void removeHorizontalSeam(int[] seam) {
   //    if (width() <= 1 || height() <= 1 || seam.length < 0 || seam.length >= height || !isValidSeam(seam))
   //       throw new IllegalArgumentException();
   // }

   // // remove vertical seam from current picture
   // public void removeVerticalSeam(int[] seam) {
   //    if (width() <= 1 || height() <= 1 || seam.length < 0 || seam.length >= width || !isValidSeam(seam))
   //       throw new IllegalArgumentException();
   // }

   public static void main(String args[]) {
      Picture inputImg = new Picture(args[0]);
      SeamCarver sc = new SeamCarver(inputImg);

   }
}
