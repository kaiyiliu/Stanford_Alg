public class EditDistance {
    public String[] w1;
    public String[] w2;
    
    // leading "" in the first index
    public String[] assure(String[] input) {
        if (w1[0].equals(""))
            return input;
        String[] output = new String[input.length + 1];
        output[0] = "";
        System.arraycopy(input, 0, output, 1, input.length);
        return output;
    }
    public int minDistance(String word1, String word2) {
        w1 = word1.split("");
        w2 = word2.split("");
        w1 = assure(w1);
        w2 = assure(w2);
        int len1 = w1.length;
        int len2 = w2.length;
        D = new int[len1][len2];
        
        // init
        for (int i = 0; i < len1; i++)
            D[i][0] = i;
        for (int j = 0; j < len2; j++)
            D[0][j] = j;
            
        // dp
        for (int i = 1; i < len1; i++) {
            for (int j = 1; j< len2; j++) {
                if (w1[i].equals(w2[j])) {
                    D[i][j] = D[i-1][j-1];
                } else {
                    D[i][j] = Math.min(D[i-1][j-1], Math.min(D[i-1][j], D[i][j-1])) + 1;
                }
            }
        }
        return D[len1-1][len2-1];
    }
}
