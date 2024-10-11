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
        while(sequence.length() > 0){
            index = getIndexSTR(sequence, STR);
            if(index >= 0){
                pCount = getCountSTR(sequence.substring(index), STR);
                if(pCount > maxNumCount){
                    maxNumCount = pCount;
                }
            }
            if((index + 1) < sequence.length()){
                sequence = sequence.substring(index + 1);
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
