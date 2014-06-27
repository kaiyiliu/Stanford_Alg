import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class CountInversions2{

     public static BigInteger mergeCount(int[] a, int[] aux, int lo, int mid, int hi) {
         int i = lo;
         int j = mid + 1;
         BigInteger count = BigInteger.valueOf(0);
         
         for (int k = lo; k <= hi; k++) {
             aux[k] = a[k];
         }
         
         for (int k = lo; k <= hi; k++) {
            if (i > mid) { a[k] = aux[j++]; }
            else if (j > hi) { a[k]= aux[i++]; }
            else if (aux[i] <= aux[j]) { a[k] = aux[i++]; }
            else {
                a[k] = aux[j++];
                count = count.add(BigInteger.valueOf(mid - i + 1));
            }
         }
         return count;
     }
     
     public static BigInteger sortCount(int[] a, int[] aux, int lo, int hi) {
         if (lo >= hi)
            return BigInteger.valueOf(0);
        int mid = lo + (hi - lo) / 2;
        BigInteger x = sortCount(a, aux, lo, mid);
        BigInteger y = sortCount(a, aux, mid+1, hi);
        BigInteger z = mergeCount(a, aux, lo, mid, hi);
        return x.add(y).add(z);
     }
     
     public static BigInteger sortCount(int[] a) {
         return sortCount(a, a.clone(), 0, a.length-1);
     }
     
     public static void main(String []args){
        System.out.println("Hello World");
        try (BufferedReader br = new BufferedReader (new FileReader("input.txt"))) {
            String line;
            int[] n = new int[100000];
            BigInteger totalCount;
            int i = 0;
            while ((line = br.readLine()) != null) {
              n[i++] = Integer.parseInt(line);
              // if (i == 1000)
              //   break;
            }
            totalCount = sortCount(n);
                // for (int e : n) {
                //     System.out.print(" " + e);
                // }
                // System.out.println("");
            System.out.println("\ntotal count:" + totalCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}
