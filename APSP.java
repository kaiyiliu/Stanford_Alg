package dynamicProgramming;

import java.io.*;

public class APSP{
    public static int V;
    public static int E;
    // public static HashMap<Integer, Double>[] adj; // do not store into adj, directly into dp[][]
    public static double[][] dp;
    public static double[] temp;   // record new value in every inner loop
    public static double[] K;		// at every fixed k loop, record the k row info
    public static double MAX = 999999.9;
    
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
//                    adj = new HashMap[V+1];
//                    for (int i = 0; i <= V; i++)
//                        adj[i] = new HashMap<Integer, Double>();
					System.out.println(V);
                    dp = new double[V+1][V+1];
                    temp = new double[V+1]; 
                } else {
                    int head = Integer.parseInt(tokens[0]);
                    int tail = Integer.parseInt(tokens[1]);
                    double weight = Double.parseDouble(tokens[2]);
                    //adj[head].put(tail, weight);
                    dp[head][tail] = weight;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void runFW() {
        //init k=0
        for (int i = 1; i <= V; i++)
        	for (int j = 1; j <= V; j++)
        		if (i != j && dp[i][j] == 0)
        			dp[i][j] = MAX;
        //dp
        for (int k = 1; k <= V; k++) {
        	K = dp[k];
            for (int i = 1; i <= V; i++) {
                for (int j = 1; j <= V; j++) {
                	temp[j] = Math.min(dp[i][j], dp[i][k] + K[j]);
                }
                // cope temp to dp[i]
                System.arraycopy(temp, 1, dp[i], 1, V);
            }
        }
    }
    
    public static boolean containsNC() {
        for (int i = 1; i <= V; i++) {
            if (dp[i][i] < 0)
                return true;
        }
        return false;
    }
    
    public static void main(String []args){
        System.out.println("Hello World");
        double startTime = System.currentTimeMillis();
        readIn("input1.txt");
        runFW();
        if (containsNC()) {
            System.out.println("Contains Negative Cycle.");
        } else {
            double result = MAX;
            for (int i = 1; i <= V; i++)
                for (int j = 1; j<= V; j++) {
                    if (i != j)
                        result = Math.min(result, dp[i][j]);
                }
            System.out.println("shortest shortest path length: " + result);
        }
        double stopTime = System.currentTimeMillis();
        double time = stopTime-startTime;
        System.out.println("time: " + time + "ms");
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
