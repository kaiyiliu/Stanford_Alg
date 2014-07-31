import java.io.*;
import java.util.*;

public class KClustering{
    public static int numOfV;
    public static int K = 4;
    public static PriorityQueue<Edge> pq;
    public static AdavancedUF uf;
    
    public static void createUWG(String filename) {
        String line = null;
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (isFirstLine) {
                    isFirstLine = false;
                    numOfV = Integer.parseInt(tokens[0]);
                    uf = new AdavancedUF(numOfV);
                    pq = new PriorityQueue<Edge>(numOfV);
                } else {
                    int v1 = Integer.parseInt(tokens[0]);
                    int v2 = Integer.parseInt(tokens[1]);
                    Edge anEdge = new Edge(v1, v2, Double.parseDouble(tokens[2]));
                    pq.add(anEdge);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static double maxSpacingClassify() {
        int v1;
        int v2;
        Edge anEdge;
        while (uf.getCount() != K) {
            while (true) {
                anEdge = pq.poll();
                v1 = anEdge.getOne();
                v2 = anEdge.getTheOther(v1);
                if (uf.isConnected(v1, v2))
                    continue;
                else {
                    uf.union(v1, v2);
                    break;
                }
            }
        }
        // find out the maximum spacing
        double maxSpacing;
        while (true) {
            anEdge = pq.poll();
            v1 = anEdge.getOne();
            v2 = anEdge.getTheOther(v1);
            if (uf.isConnected(v1, v2))
                continue;
            else {
                maxSpacing = anEdge.getWeight();
                break;
            }
        }
        return maxSpacing;    
    }
    
    public static void main(String []args){
        System.out.println("Hello World");
        createUWG("input.txt");
        System.out.println(pq.peek());
        System.out.println(maxSpacingClassify());
        
    }
    
    private static class AdavancedUF {
        private int[] leader;
        private int[] size;
        private int count;
        
        AdavancedUF() {}
        
        AdavancedUF(int n) {
            count = n;
            leader = new int[n + 1];
            size = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                leader[i] = i;
                size[i] = 0;
            }
        }
        
        private int getCount() {
            return count;
        }
        
        private int find(int i) {
            int root = i;
			while (leader[root] != root) {
                root = leader[root];
            }
			// path compression
			while (leader[i] != root) {
				i = leader[i];
				leader[i] = root;
			}
            return root;
        }
        
        private boolean isConnected(int i, int j) {
            return find(i) == find(j);
        }
        
        private boolean union(int i, int j) {
            int iRoot = find(i);
            int jRoot = find(j);
            if (iRoot == jRoot)
                return false;
            else if (size[iRoot] < size[jRoot]) {
                leader[iRoot] = jRoot;
                // size[jRoot] += size[iRoot];
            } else if (size[iRoot] > size[jRoot]){
                leader[jRoot] = iRoot;
                // size[iRoot] += size[jRoot];
            } else {
            	leader[iRoot] = jRoot;
				size[jRoot]++;
            }
            count --;
            return true;
        }
    }
    
    private static class Edge implements Comparable<Edge> {
        private int v1;
        private int v2;
        private double weight;
        
        Edge() {}
        
        Edge(int a, int b, double w) {
            this.v1 = a;
            this.v2 = b;
            this.weight = w;
        }
        
        private int getOne() {
            return v1;
        }
        
        private int getTheOther(int one) {
            return (one == v1) ? v2 : v1;
        }
        
        private double getWeight() {
            return weight;
        }
        
        public int compareTo(Edge that) {
            if (this.weight > that.weight)
                return 1;
            else if (this.weight < that.weight)
                return -1;
            else
                return 0;
        }
        
        public String toString() {
            return "" + v1 + "--" + v2 + ": " + weight;
        }
    }
}
