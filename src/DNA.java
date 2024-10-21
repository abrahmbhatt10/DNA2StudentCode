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
        /*
    First Attempt:
    int maxNumCount = 0;
        int currentLength;
        int index = 0;
        int pCount = 0;
        /*
            The logic is similar to finding the max integer in an array.
            The local variable pCount returns the count of sequences at a given index.
            maxNumCount is the running max count of all the counts at different index locations.

        while(sequence.length() > 0){
            /*
                Find the starting STR location.

            index = getIndexSTR(sequence, STR);
            if(index >= 0){
                /*
                    Get the consecutive STR count.

                pCount = getCountSTR(sequence.substring(index), STR);
                if(pCount > maxNumCount){
                    maxNumCount = pCount;
                }
            }
            else{
                break;
            }
            if((index + 1) < sequence.length()){
                sequence = sequence.substring(index + 1);
            }
            else{
                break;
            }
        }
     */
        return rabinKarp(sequence, STR);
    }
    /*
        Returns starting index when the first instance of the STR is found.
     */
    public static int getIndexSTR(String sequence, String STR){
       if(sequence.contains(STR)){
           return sequence.indexOf(STR);
       }
       return -1;
    }

    /*
        Returns the number of STR count at the start of the string
     */
    public static int getCountSTR(String sequence, String STR){
        if(!sequence.startsWith(STR)){
            return 0;
        }
        int count = 0;
        String pSTR = sequence;
        while(pSTR.startsWith(STR)){
            count++;
            pSTR = pSTR.substring(STR.length());
        }
        return count;
    }

    /*
    Pseudocode from Mr. Blick's slides:

    Horner's Method:
     */

    public static long hash(String t, int length){
        long h = 0;
        for(int i = 0; (i < t.length()) && (i < length); i++){
            h = (h * R + t.charAt(i)) % p;
        }
        return h;
    }

     /*
    Pseudocode from Mr. Blick's slides:

    Horner's Method:
     */

    public static boolean hashCompare(String t, int length){
        long h = 0;
        for(int i = 0; (i < t.length()) && (i < length); i++){
            h = (h * R + t.charAt(i)) % p;
        }
        if(h == strHash)
           return true;
        return false;
    }

    /*
    This code finds the largest prime number that is less than INTEGER_MAX. I got it from this site:
    https://stackoverflow.com/questions/14037688/find-the-highest-prime-number-in-a-given-range
     */
    public static long findPrime(){
        int flag=0;
        int b= Integer.MAX_VALUE;
        double sq = sqrt(b);
        long i;
        for(i = b; i>=0; i--)
        {
            if(i%2!=0)
            {
                for(b=3;b<=sq;b++)
                {
                    if(i%b!=0)
                    {
                        flag=1;
                    }
                    else if(i%b==0)
                    {
                        flag=0;
                        break;
                    }
                }
                if(flag==1){
                    return i;
                }
            }
        }
        return i;
    }
    /*
        Rabin-Karp Algorithm from Mr. Blick's slides:
    */
    public static long rabinKarp(String sequence, String STR){
        // Calculate hash for pattern we are searching for
        strHash = hash(STR, STR.length());
        // Stores the max value of consecutive repeated patterns
        // I use similar logic like finding the max integer in an array.
        long maxNumRepeats = 0;
        // Gives the current consecutive patterns and stores to maxNumRepeats if greater.
        long numRepeats = 0;
        // Initialized to first hash of STR length, and then shifted one character at a time
        // Rabin Karp hash calculation is efficient with one character shifting -> O(1)n.
        long seqHash = 0;
        // RM is used to remove the leading character before the hash value for the new character can be added.
        // Code taken from the textbook algorithms 4th edition Sedgewick & Wayne.
        RM=1;
        for(int i = 1; i <= STR.length() - 1; i++){
            RM = (R * RM) % p;
        }
        // Loop through one character at a time through the sequence to find the first match.
        // After the first match, call the function getCountSTR() to get the subsequent matches through simple String
        // compare.

        for (int i = 0; i < (sequence.length() - STR.length()); i++){
            if(i == 0)
            {
                // Calculating sequence hash for the first time
                seqHash = hash(sequence.substring(0, STR.length()), STR.length());
            }
            else
            {
                // Calculate the hash value to remove the first character
                seqHash = (seqHash + p - RM * sequence.charAt(i - 1) % p) % p;
                // Calculate the hash value to add the new character
                seqHash = (seqHash * R + sequence.charAt(i+STR.length()-1)) % p;
            }
            if (strHash == seqHash) {
                // There is a match
                // Begins checking for consecutive appearances
                numRepeats = 1;
                if (i + STR.length() <= sequence.length()) {
                    numRepeats += getCountSTR(sequence.substring(i + STR.length()), STR);
                }
                // Store the max value in maxNumRepeats
                if (numRepeats > maxNumRepeats) {
                        maxNumRepeats = numRepeats;
                }
                // if not enough characters left, break.
                if(i + STR.length() > sequence.length()){
                    break;
                }
            }
        }
        return maxNumRepeats;
    }

    /*
        Finds consecutive pattern hash using the hash method.
     */
    public static long countMatch(String sequence, String STR){
        int numRepeats = 0;
        int i = 0;
        while(hashCompare(sequence.substring(i, i+STR.length()), STR.length()))
        {
            numRepeats++;
            i = i+ STR.length();
            if(i > sequence.length())
                break;
        }


        /*while (strHash == seqHash) && (i <= (sequence.length() - STR.length()))){
            numRepeats++;
            i = i + STR.length();
            seqHash = hash(sequence.substring(i, i + STR.length()),STR.length());
        }*/
         return numRepeats;
    }

}
