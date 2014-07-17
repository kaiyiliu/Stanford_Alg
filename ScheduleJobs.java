import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class ScheduleJobs{
     static int numOfJobs;
     static PriorityQueue<Job> pq;
     static PriorityQueue<Job> pq2;
     
     public static void addToHeap(String filename) {
         String line = null;
         boolean isFirstLine = true;
         try (BufferedReader br = new BufferedReader (new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    numOfJobs = Integer.parseInt(line.trim());
                    pq = new PriorityQueue<Job>(numOfJobs, new compareByDiffReverse());
                    pq2 = new PriorityQueue<Job>(numOfJobs, Collections.reverseOrder());
                }
                else {
                    String[] tokens = line.trim().split("\\s+");
              //      System.out.println(tokens[0] + " + " + tokens[1]);
                    pq.add(new Job(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
                    pq2.add(new Job(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));
                }
            }   
         } catch(IOException e) {
             e.printStackTrace();
         }
     }
     
     public static long scheduleByDiff(PriorityQueue<Job> p) {
         long sum = 0;
         long accLength = 0;
         while (!p.isEmpty()) {
             Job j = p.poll();
             accLength += j.getLength();
             sum += accLength * j.getWeight();
         }
         return sum;
     }
     
     public static void main(String []args){
        System.out.println("Hello World");
        addToHeap("input.txt");
        long wct1 = scheduleByDiff(pq);
        long wct2 = scheduleByDiff(pq2);
        System.out.println("answer1: " + wct1);
        System.out.println("answer2: " + wct2);
        
     }
     
     public static class compareByDiffReverse implements Comparator<Job> {
         public int compare(Job j1, Job j2) {
             int diff = j1.getDiff() - j2.getDiff();
             if (diff != 0)
                return -diff;
             else
                return -(j1.getWeight() - j2.getWeight());
         }
     }
     
     private static class Job implements Comparable {
         private int weight;
         private int length;
         private double ratio;
         private int diff;
         
         Job(){}
         
         Job(int weight, int length) {
             this.weight = weight;
             this.length = length;
             this.ratio = (double) weight / (double) length;
             this.diff = weight - length;
         }
         
         private int getWeight() {
             return weight;
         }
         
         private int getLength(){
             return length;
         }
         
         private double getRatio(){
             return ratio;
         }
         
        private int getDiff(){
             return diff;
         }
         
        public int compareTo(Object o) {
             Job j = (Job) o;
             double diff = this.ratio- j.ratio;
             if (diff > 0)
                return 1;
             else if (diff < 0)
                return -1;
             else if (this.weight == j.weight)
                return 0;
             else 
                return this.weight - j.weight;
         }
     }
}
