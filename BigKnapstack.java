import java.util.*;
import java.io.*;

public class BigKnapstack{
    public static int I;
    public static int W;
    public static TreeMap<Integer, Double> a;          //a: Weight, Value pair
//    public static Stack<Integer> bag;

    public static void readIn(String filename) {
        String line = null;
        boolean isFirstLine = true;
        double newValue;
        int newWeight;
        HashMap<Integer, Double> temp = new HashMap<Integer, Double>();
        HashSet<Integer> deleted = new HashSet<Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (isFirstLine) {
                    isFirstLine = false;
                    W = Integer.parseInt(tokens[0]);
                    I = Integer.parseInt(tokens[1]);
                    a = new TreeMap<Integer, Double>();
                   // bag = new Stack<Integer>();
                   a.put(0, 0.0);
                } else {
                    for (Map.Entry<Integer, Double> entry : a.entrySet()) {
                        newWeight = Integer.parseInt(tokens[1]) + entry.getKey();
                        newValue = Double.parseDouble(tokens[0]) + entry.getValue();
                        // the value must be monotonically increasing in one single column,
                        // and must also be increasing in one single row
                        // AND after the insertion, the following value must be larger!
                        if (newWeight <= W && isIncreased(newWeight, newValue)) {
                            temp.put(newWeight, newValue);              // cannot modify the original HashMap via iterating
                            checkDecreased(newWeight, newValue, deleted);     // check if the items after the new insersion are increasing
                        }
                    }
                    for (Integer w : deleted)
                        a.remove(w);
                    a.putAll(temp);
                    temp.clear();
                    deleted.clear();
                   //System.out.println(a);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(I);
    }
    
    public static boolean isIncreased(int w, double v) {
        if (a.get(a.floorKey(w)) < v)
            return true;
        else
            return false;
    }
    
    public static void checkDecreased(int w, double v, HashSet<Integer> deleted) {
        for (Map.Entry<Integer, Double> entry : a.tailMap(w, false).entrySet()) {
            if (v >= entry.getValue())
                deleted.add(entry.getKey());
            else
                return;
        }
    }
    
    // public static void fillIn() {
    //     for (int i = 1; i <= I; i++) {         // each item
    //         for (int j = 0; j <=W; j++) {     // each total weight permitted
    //             // if current item's weight is larger than the total weight permitted
    //             if (j < w[i]) {
    //                 a[i][j] = a[i-1][j];
    //             } else {
    //                 a[i][j] = Math.max(a[i-1][j], a[i-1][j-w[i]] + v[i]);
    //             }
    //         }
    //     }
    // }
    
    // public static void backTrack(int i, int j) {
    //     if (a[i][j] == 0 | i == 0 | j == 0)
    //         return;
    //     if (a[i][j] > a[i-1][j]) {
    //         
    //         bag.push(i);
    //         backTrack(i-1, j-w[i]);
    //     } else {
    //         backTrack(i-1, j);
    //     }
    // }
    
    public static void main(String []args){
        System.out.println("Hello World");
        readIn("input.txt");
        // int w = W;
        // while (!a.containsKey(w))
        //     w--;
        System.out.println("optimal value: " + a.get(a.lastKey()));
    }
}
