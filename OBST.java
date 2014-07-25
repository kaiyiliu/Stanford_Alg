import java.util.*;
import java.io.*;
import java.text.*;

public class OBST{
    public static double A[][];
    public static double P[];
    public static int size = 7;
    
    public static double getMin(int l, int r) {
        double value = 10000;
        double pSum = 0;
        for (int i = l; i<= r; i++)
            pSum += P[i];
        for (int root = l; root <= r; root++) {
            double aValue = pSum + A[l][root-1] + A[root+1][r];    // when first index is bigger thna second index, the A[][] is 0
            value = (aValue < value) ? aValue : value;
        }
        return value;
    }
    
    public static void buildOBST() {
        for (int s = 0; s < size; s++) {
            for (int i = 1; i <= size - s; i++) {
                A[i][i+s] = getMin(i, i + s);
            }
        }
    }
    
    public static void main(String []args){
        System.out.println("Hello World");
        A = new double[size+2][size+2];                 // create big enough matrix so that there are zeros when first index is bigger than second index
        P = new double[] {0.0, 0.05, 0.4, 0.08, 0.04, 0.1, 0.1, 0.23};
        buildOBST();
        System.out.println("min: " + A[1][size]);

    }
}
