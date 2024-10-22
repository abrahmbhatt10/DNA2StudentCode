import javax.sound.midi.Sequence;

import static java.lang.Math.sqrt;

/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: [Agastya Brahmbhatt]
 *</p>
 */

public class DNA {
    private static long p = 54321102419L;
    private static int R = 256;
    private static long RM = 1;
    private static long strHash = 0;
    /**
     * This function, STRCount(), returns the longest consecutive run of STR in sequence.
     */
    public static long STRCount(String sequence, String STR) {
        return rabinKarp(sequence, STR);
    }

    /*
    Pseudocode from Mr. Blick's slides:
    Horner's Method:

    This uses a for loop with a large prime to calculate Hash.
     */

    public static long hash(String t, int firstIndex, int lastIndex){
        long h = 0;
        for(int i = 0; (i < t.length()) && (i < (lastIndex - firstIndex + 1)); i++){
            h = (h * R + t.charAt(i)) % p;
        }
        return h;
    }


    /*
        Rabin-Karp Algorithm from Mr. Blick's slides:
    */
    public static long rabinKarp(String sequence, String STR){
        // Stores the max value of consecutive repeated patterns
        // I use similar logic like finding the max integer in an array.
        long maxNumRepeats = 0;
        // Gives the current consecutive patterns and stores to maxNumRepeats if greater.
        long numRepeats = 0;
        // Initialized to first hash of STR length, and then shifted one character at a time
        // Rabin Karp hash calculation is efficient with one character shifting -> O(1)n.
        long seqHash = 0;

        int prevMatchIndex = -1;
        int m = STR.length();
        // Calculate hash for pattern we are searching for
        strHash = hash(STR, 0, m - 1);
        // RM is used to remove the leading character before the hash value for the new character can be added.
        // Code taken from the textbook algorithms 4th edition Sedgewick & Wayne.
        RM=1;
        for(int i = 1; i <= m - 1; i++){
            RM = (R * RM) % p;
        }
        int n = sequence.length();
        // Calculating sequence hash for the first time
        seqHash = hash(sequence, 0, m - 1);
        if(seqHash == strHash){
            prevMatchIndex = 0;
            numRepeats = 1;
            maxNumRepeats = 1;
        }

        // Loop through one character at a time through the sequence to find the first match.
        // After the first match, call the function getCountSTR() to get the subsequent matches through simple String
        // compare.
        for (int i = m; i < n; i++){
            // Calculate the hash value to remove the first character
            seqHash = (seqHash + p - RM * sequence.charAt(i - m) % p) % p;
            // Calculate the hash value to add the new character
            seqHash = (seqHash * R + sequence.charAt(i)) % p;
            if (strHash == seqHash) {
                // There is a match
                // Begins checking for consecutive appearances
                if((prevMatchIndex + m) == i){
                    // This is a consecutive match
                    numRepeats++;
                }
                else{
                    numRepeats = 1;
                }
                prevMatchIndex = i;
                if(numRepeats > maxNumRepeats){
                    maxNumRepeats = numRepeats;
                }
            }
        }
        return maxNumRepeats;
    }
}
