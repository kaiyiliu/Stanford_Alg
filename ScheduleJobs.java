import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class ScheduleJobs{
     int numOfJobs;
     PriorityQueue<Job> pq;
     
     public void addToHeap(String filename) {
         String line = null;
         boolean isFirstLine = true;
         try (BufferedReader br = new BufferedReader (new FileReader(filename)) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    numOfJobs = Integer.parseInt(line.trim());
                    pq = new PriorityQueue<Job>(numOfJobs);
                }
                String[] tokens = line.trim().split("\\s");
                pq.add(new Job(Integer.parseInt(tokens[0], Integer.parseInt(tokens[1]);
            }   
         }
     }
     
     public int scheduleByDiff() {
         int sum = 0;
         int accLength = 0;
         while (!pq.isEmpty()) {
             Job j = pq.poll();
             accLength += j.getLength();
             sum += accLength * j.getWeight();
         }
         return sum;
     }
     
     public static void main(String []args){
        System.out.println("Hello World");
        addToHeap("input.txt");
        int wct1 = scheduleByDiff();
        System.out.println("answer1: " +wct1);
        
     }
     
     private class compareByDiffReverse implements Comparator {
         private int(Object o1, Object o2) {
             Job j1 = (Job) o1;
             Job j2 = (Job) o2;
             return j2.getDiff() - j1.getDiff();
         }
     }
     
     private class Job implements Comparable {
         private int weight;
         private int length;
         private double ratio;
         private double diff;
         
         Job(){}
         
         Job(int weight, int length) {
             this.weight = weight;
             this.length = length;
             this.ratio = weight / length;
             this.diff = weight - length;
         }
         
         private int getWeight() {
             return weight;
         }
         
         private int getLength(){
             return length;
         }
         
         private int getRatio(){
             return ratio;
         }
         
        private int getDiff(){
             return diff;
         }
         
         private int compareTo(Object o) {
             Job j = (Job) o;
             return this.ratio- j.ratio;
         }
     }
}
