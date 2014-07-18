import java.io.*;
import java.util.*;

public class PrimMST{
    public static int numOfV;
    public static int numOfE;
    public static double path;
    public static LinkedList[] adj;
    public static PriorityQueue<Edge> pq;
    public static HashMap<Integer, Edge> book;            // endpoint and edge
    
    public static HashSet<Integer> T;
    
    public static void createUWG(String filename) {
        String line = null;
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (isFirstLine) {
                    isFirstLine = false;
                    numOfV = Integer.parseInt(tokens[0]);
                    numOfE = Integer.parseInt(tokens[1]);
                    adj = new LinkedList[numOfV + 1];
                    for (int i = 1; i <= numOfV; i++)
                        adj[i] = new LinkedList<Edge>();
                } else {
                    int one = Integer.parseInt(tokens[0]);
                    int other = Integer.parseInt(tokens[1]);
                    Edge anEdge = new Edge(one, other, Double.parseDouble(tokens[2]));
                    adj[one].add(anEdge);
                    adj[other].add(anEdge);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void prim() {
        init(1);
    while (T.size() < numOfV) {
		Edge anEdge = pq.poll();
		path += anEdge.getWeight();
		int one = anEdge.getOne();
        
		if (T.contains(one))
			transT(anEdge.getTheOther(one));
		else
			transT(one); 
		
        }
    }

    private static void init(int n) {
         pq = new PriorityQueue<Edge>();
         T = new HashSet<Integer>();
	     book = new HashMap<Integer, Edge>();
	     path = 0;
         
         T.add(n);
    	for (Object o : adj[n]) {
    		Edge e = (Edge) o;
            book.put(e.getTheOther(n), e);
    		pq.add(e);
    	}
    }

    
    private static void transT(int n) {
        T.add(n);
	    book.remove(n);
	    for (Object o : adj[n]) {
            Edge e = (Edge) o;
            int other = e.getTheOther(n);
		    if (!T.contains(other)) {
    			Edge oldEdge = book.get(other);
    			if (oldEdge == null) {
    				pq.add(e);
    				book.put(other, e);
    			} else {
    				if (e.getWeight() < oldEdge.getWeight()) {
    					pq.remove(oldEdge);
    					pq.add(e);
    					book.put(other, e);
    				}
			    }
		    }
        }
        
    }
    
    public static void main(String []args){
        System.out.println("Hello World");
        createUWG("input.txt");
        System.out.println(adj[1]);
        prim();
        System.out.println("path: " + path);
        
    }
    
    private static class Edge implements Comparable<Edge>{
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
