import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// HashMap Implementation of Graph for Karger’s Algorithm
// parallel edges allowed
public class GraphHashMap {
    private static Random rand = new Random(999);
    private static int numV;
    private static int numE;
    private static HashMap<Integer, List<Integer>> adj;
    
    public GraphHashMap(String file) {
        numE = 0;
        numV = 0;
        adj = new HashMap<Integer, List<Integer>>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            String[] token = null;
            // add vertices and edges
            while ((line = br.readLine()) != null) {
                numV++;
                token = line.split("\\s+");
                int startPoint = Integer.parseInt(token[0]);
                List<Integer> vertices = new LinkedList<Integer>(); 
                for (int v = 1; v <= token.length - 1; v++) {
                    numE++;
                    vertices.add(Integer.parseInt(token[v]));
                }
                adj.put(startPoint, vertices);
            }
            numE = numE / 2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void addEdge(HashMap<Integer, List<Integer>> adj, int v, int w) {
        adj.get(v).add(w);
        adj.get(w).add(v);
    }
    
    public static void removeEdge(HashMap<Integer, List<Integer>> adj, int v, int w) {
        while (adj.get(v).remove(Integer.valueOf(w)));
        while (adj.get(w).remove(Integer.valueOf(v)));
    }
    
    //replace endpoint w with v
    public static void replaceEndPoint(HashMap<Integer, List<Integer>> adj, int v, int w) {
        Set<Integer> keys = adj.keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            List<Integer> vertices = adj.get(iter.next());    // the value is a LinkedList
            while (vertices.remove(Integer.valueOf(w))) {
                vertices.add(v);
            }
        }
    }
    
    // merge two vertices. Keep v vertice alive
    public static void mergeVertices(HashMap<Integer, List<Integer>> adj, int v, int w) {
        // remove self-circle (when merging, x-w edges and w-x edges are self-circle)
        removeEdge(adj, v, w);
        
        // replace the endpoint of an edge from w to v
        replaceEndPoint(adj, v, w);
        
        // move all vertices connected to w into v
        List<Integer> wVertices = adj.get(w);
        for (int endPoint : wVertices)
            adj.get(v).add(endPoint);
            
        // remove startPoint w
        adj.remove(w);
    }
    
    public static void present(HashMap<Integer, List<Integer>> adj) {
        Set<Integer> keys = adj.keySet();
        for (int key : keys) {
            System.out.print(key + "    ");
            System.out.println(adj.get(key));
        }
    }
    
    public static LinkedList<int[]> getAllEdges(HashMap<Integer, List<Integer>> adj) {
        LinkedList<int[]> edges = new LinkedList<int[]>();
        Set<Integer> keys = adj.keySet();
        for (int key : keys) {
            List<Integer> values = adj.get(key);
            for (int value : values) {
                if (key < value) {
                    int[] edge = new int[] {key, value};
                    edges.add(edge);
                }
            }
        }
        return edges;
    }
    
    public static HashMap<Integer, List<Integer>> deepClone(HashMap<Integer, List<Integer>> adj) {
        HashMap<Integer, List<Integer>> newAdj = new HashMap<Integer, List<Integer>>();
        Set<Integer> keys = adj.keySet();
        for (int key : keys) {
            List<Integer> values = new LinkedList<Integer>();
            for (int v : adj.get(key))
                values.add(v);
            newAdj.put(key, values);
        }
        return newAdj;
    }
    
    public static void main(String[] args) {
        new GraphHashMap("input.txt");
		int mincuts = Integer.MAX_VALUE;
        
		// random minimum cuts algorithm
		for (int i = 1; i <= numV * numV; i++) {
            HashMap<Integer, List<Integer>> testAdj = (HashMap<Integer, List<Integer>>) deepClone(adj);
            while (testAdj.size() > 2) {
				LinkedList<int[]> edges = getAllEdges(testAdj);         
                int[] edgeChosen = edges.get(rand.nextInt(edges.size()));
				mergeVertices(testAdj, edgeChosen[0], edgeChosen[1]);
			}
			//present(testAdj);
            Set<Integer> keys = testAdj.keySet();
			int numOfEdges = testAdj.get(keys.iterator().next()).size();
           // System.out.println("cuts this turn: " + numOfEdges);
			if (numOfEdges < mincuts)
				mincuts = numOfEdges;
        }
		System.out.println("min cuts: " + mincuts);
		
    }
        
}
