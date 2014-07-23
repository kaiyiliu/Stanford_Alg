import java.util.*;
import java.io.*;

public class Knapstack{
    public static int I;
    public static int W;
    public static double[] v;
    public static int[] w;
    public static double[][] a;
    public static Stack<Integer> bag;

    public static void readIn(String filename) {
        String line = null;
        boolean isFirstLine = true;
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (isFirstLine) {
                    isFirstLine = false;
                    W = Integer.parseInt(tokens[0]);
                    I = Integer.parseInt(tokens[1]);
                    v = new double[I + 1];
                    w = new int[I + 1];
                    a = new double[I + 1][W + 1];
                    bag = new Stack<Integer>();
                    i = 1;
                } else {
                    v[i] = Double.parseDouble(tokens[0]);
                    w[i] = Integer.parseInt(tokens[1]);
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(I);
    }
    
    public static void fillIn() {
        for (int i = 1; i <= I; i++) {         // each item
            for (int j = 0; j <=W; j++) {     // each total weight permitted
                // if current item's weight is larger than the total weight permitted
                if (j < w[i]) {
                    a[i][j] = a[i-1][j];
                } else {
                    a[i][j] = Math.max(a[i-1][j], a[i-1][j-w[i]] + v[i]);
                }
            }
        }
    }
    
    public static void backTrack(int i, int j) {
        if (a[i][j] == 0 | i == 0 | j == 0)
            return;
        if (a[i][j] > a[i-1][j]) {
            
            bag.push(i);
            backTrack(i-1, j-w[i]);
        } else {
            backTrack(i-1, j);
        }
    }
    
    public static void main(String []args){
        System.out.println("Hello World");
        readIn("input.txt");
        fillIn();
        backTrack(I, W);
        System.out.println("optimal value: " + a[I][W]);
        System.out.print("optimal path: ");
        while (!bag.isEmpty())
            System.out.print(bag.pop() + ", ");
     }
}
