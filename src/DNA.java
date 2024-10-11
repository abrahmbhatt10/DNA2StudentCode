import javax.sound.midi.Sequence;

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
    public static int STRCount(String sequence, String STR) {
        int maxNumCount = 0;
        int currentLength;
        int index = 0;
        int pCount = 0;
        /*
            The logic is similar to finding the max integer in an array.
            The local variable pCount returns the count of sequences at a given index.
            maxNumCount is the running max count of all the counts at different index locations.
         */
        while(sequence.length() > 0){
            /*
                Find the starting STR location.
             */
            index = getIndexSTR(sequence, STR);
            if(index >= 0){
                /*
                    Get the consecutive STR count.
                 */
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
        return maxNumCount;
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
}
