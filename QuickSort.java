import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class QuickSort{
     public static int count = 0;
     
     public static void swap(int[] a, int l, int r) {
         if (l == r)
            return;
         int temp = a[l];
         a[l] = a[r];
         a[r] = temp;
     }
     
     public static int choosePivot(int[] a, int l, int r) {
         if (l == r)
            return l;
         int mid = l + (r - l) / 2;
         int[] temp = new int[2];
         if (a[mid] > a[l]) {
            temp[0] = l;
            temp[1] = mid; 
         } else {
             temp[0] = mid;
             temp[1] = l;
         }
         if (a[r] > a[temp[1]]) {
             return temp[1];
         } else if (a[r] > a[temp[0]]) {
             return r;
         } else {
             return temp[0];
         }
     }
     
     public static int partition(int[] a, int l, int r, int p) {
         int pValue = a[p];
         swap(a, p, l);
         int i = l + 1;
         for (int j = l + 1; j <= r; j++) {
             if (pValue > a[j]) {
                 swap(a, i, j);
                 i++;
             }
         }
         swap(a, l, i - 1);
         count += r - l;
         return i - 1;
     }
     
     public static void quickSort(int[] a, int l, int r) {
        if (l >= r)
            return;
        int p = choosePivot(a, l, r);
        int mid = partition(a, l, r, p);
        quickSort(a, l, mid - 1);
        quickSort(a, mid + 1, r);
     }
     
     public static void quickSort(int[] a) {
         quickSort(a, 0, a.length - 1);
     }
     
     public static void main(String []args){
        System.out.println("Hello World");
        try (BufferedReader br = new BufferedReader (new FileReader("input.txt"))) {
            String line;
            int[] n = new int[10000];
            int i = 0;
            while ((line = br.readLine()) != null) {
              n[i++] = Integer.parseInt(line);
            }
            quickSort(n);
                // for (int e : n) {
                //     System.out.print(" " + e);
                // }
                // System.out.println("");
            System.out.println("\ntotal count:" + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}
