public class CountInversions{

     public static int mergeCount(int[] a, int[] aux, int lo, int mid, int hi) {
         int i = lo;
         int j = mid + 1;
         int count = 0;
         
         for (int k = lo; k <= hi; k++) {
             aux[k] = a[k];
         }
         
         for (int k = lo; k <= hi; k++) {
            if (i > mid) { a[k] = aux[j++]; }
            else if (j > hi) { a[k]= aux[i++]; }
            else if (aux[i] <= aux[j]) { a[k] = aux[i++]; }
            else {
                a[k] = aux[j++];
                count += mid - i + 1;
            }
         }
         return count;
     }
     
     public static int sortCount(int[] a, int[] aux, int lo, int hi) {
         if (lo >= hi)
            return 0;
        int mid = lo + (hi - lo) / 2;
        int x = sortCount(a, aux, lo, mid);
        int y = sortCount(a, aux, mid+1, hi);
        int z = mergeCount(a, aux, lo, mid, hi);
        return x + y + z;
     }
     
     public static int sortCount(int[] a) {
         return sortCount(a, a.clone(), 0, a.length-1);
     }
     
     public static void main(String []args){
        System.out.println("Hello World");
        int[] a = {1,3,5,2,4,6};
        int count = sortCount(a);
        for (int e : a)
            System.out.print(" " + e);
        System.out.println("\ninverstions count:" + count);
     }
}
