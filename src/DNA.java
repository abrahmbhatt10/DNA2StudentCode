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

    public static long hash(String t){
        long h = 0;
        int R = 256;
        long p = 54321102419L;
        for(int i = 0; i < t.length(); i++){
            h = (h * R + t.charAt(i)) % p;
        }
        return h;
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
        long strHash = hash(STR);
        long maxNumRepeats = 0;
        long numRepeats = 0;
        long seqHash;
        for (int i = 0; i < (sequence.length() - STR.length()); i++){
            if((sequence.length() - i) < STR.length()){
                break;
            }
            seqHash = hash(sequence.substring(i, STR.length()));
            if (strHash == seqHash) {
                // begin checking for consecutive appearances
                numRepeats = countMatch(strHash, seqHash, sequence.substring(i), STR);
                if(numRepeats > maxNumRepeats){
                    maxNumRepeats = numRepeats;
                }
            }
        }
        return maxNumRepeats;
    }

    public static long countMatch(long strHash, long seqHash, String sequence, String STR){
        int numRepeats = 0;
        int i = 0;
        while((strHash == seqHash) && (i < (sequence.length() - STR.length()))){
            if((sequence.length() - i) < STR.length()){
                break;
            }
            seqHash = hash(sequence.substring(i, STR.length()));
            numRepeats++;
            i = i + STR.length();
        }
        return numRepeats;
    }
}
