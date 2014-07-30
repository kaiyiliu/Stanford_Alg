import java.io.*;
import java.util.*;

public class APSP{
    public static int V;
    public static int E;
    public static HashMap<Integer, Double>[] adj;
    public static double[][][] dp;
    public static double MAX = 999999;
    
    public static void readIn(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            boolean isFirstLine = true;
            String line = null;
            String[] tokens;
            while ((line = br.readLine()) != null) {
                tokens = line.split("\\s+");
                if (isFirstLine) {
                    isFirstLine = false;
                    V = Integer.parseInt(tokens[0]);
                    E = Integer.parseInt(tokens[1]);
                    adj = new HashMap[V+1];
                    for (int i = 0; i <= V; i++)
                        adj[i] = new HashMap<Integer, Double>();
                    dp = new double[V+1][V+1][V+1];
                } else {
                    int head = Integer.parseInt(tokens[0]);
                    int tail = Integer.parseInt(tokens[1]);
                    double weight = Double.parseDouble(tokens[2]);
                    adj[head].put(tail, weight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void runFW() {
        //init k=0
        double weight = MAX;
        for (int i = 1; i <= V; i++)
            for (int j = 1; j <= V; j++) {
                if (i == j)
                    dp[i][j][0] = 0;
                else if ((weight = (double) adj[i].get(j)) != MAX)
                    dp[i][j][0] = weight;
                else
                    dp[i][j][0] = MAX;
            }
        //dp
        for (int k = 1; k <= V; k++)
            for (int i = 1; i <= V; i++)
                for (int j = 1; j <= V; j++)
                    dp[i][j][k] = Math.min(dp[i][j][k-1], dp[i][k][k-1] + dp[k][j][k-1]);
    }
    
    public static boolean containsNC() {
        for (int i = 1; i <= V; i++) {
            if (dp[i][i][V] < 0)
                return true;
        }
        return false;
    }
    
    public static void main(String []args){
        System.out.println("Hello World");
        readIn("input.txt");
        runFW();
        if (containsNC()) {
            System.out.println("Contains Negative Cycle.");
        } else {
            double result = MAX;
            for (int i = 1; i <= V; i++)
                for (int j = 1; j<= V; j++) {
                    if (i != j)
                        result = Math.min(result, dp[i][j][V]);
                }
            System.out.println("shortest shortest path length: " + result);
        }
    }
    
    // private static class Edge implements Comparable<Edge> {
    //     private int head;
    //     private int tail;
    //     private double weight;
    //     
    //     Edge() {}
    //     
    //     Edge(int a, int b, double w) {
    //         this.head = a;
    //         this.tail = b;
    //         this.weight = w;
    //     }
    //     
    //     private int getOne() {
    //         return v1;
    //     }
    //     
    //     private int getTheOther(int one) {
    //         return (one == v1) ? v2 : v1;
    //     }
    //     
    //     private double getWeight() {
    //         return weight;
    //     }
    //     
    //     public int compareTo(Edge that) {
    //         if (this.weight > that.weight)
    //             return 1;
    //         else if (this.weight < that.weight)
    //             return -1;
    //         else
    //             return 0;
    //     }
    //     
    //     public String toString() {
    //         return "" + v1 + "--" + v2 + ": " + weight;
    //     }
    // }
}
