package nl.hva.ict.ss.textsearch;

/**
 * @author Sierd Kamminga
 * @author Koen van der Fluit
 */

public class BackwardsSearch {
    /**
     * Returns index of the right most location where <code>needle</code> occurs within <code>haystack</code>. Searching
     * starts at the right end side of the text (<code>haystack</code>) and proceeds to the first character (left side).
     *
     * @param needle   the text to search for.
     * @param haystack the text which might contain the <code>needle</code>.
     * @return -1 if the <code>needle</code> is not found and otherwise the left most index of the first
     * character of the <code>needle</code>.
     */
    int findLocation(String needle, String haystack) {

        //Length of the needle and haystack
        int needleLength = needle.length();
        int haystackLength = haystack.length();

        //256
        int[] right = new int[256];

        //start at the end
        int shiftIndex = haystackLength - 1;

        //do something with the bad characters
        BoyerMoore(needle, right);

        int occurrences = 0;
        int shift = 0;

        // if the remaining haystack has less letters then needlelength has, the loop stops
        while (0 <= (shiftIndex - needleLength)) {

            int j = needleLength - 1;
            int skip;

            //Test to see if it skips and goes down
//            System.out.println(shiftIndex);

            if (j >= 0 && needle.charAt(j) == haystack.charAt(shiftIndex)) {


                //keep going until the needle does not match the haystack
                while (needle.charAt(j) == haystack.charAt(shiftIndex)) {

                    j--;

                    //Found the word!?
                    if (j < 0) {
                        System.out.println("Pattern at shift = " + shiftIndex);

                        shift = shiftIndex;

                        //calculate what to skip
                        skip = (shiftIndex + needleLength < haystackLength) ?
                                needleLength - right[haystack.charAt(shiftIndex - needleLength)] : 1;

                        shiftIndex -= skip;

                        //count the occurrences
                        occurrences++;

                        //stop second loop
                        break;
                    } else {
                        //not found yet, to the next letter!
                        shiftIndex--;
                    }
                }
            } else {
                //calculate how much to skip
                skip = Math.max(1, j - right[haystack.charAt(shiftIndex - j)]);
                shiftIndex -= skip;
            }
        }

        if (occurrences == 1) {
            //at this shift it was found
            return shift;
        } else if (occurrences >= 2) {
            //multiple found
            return occurrences;
        } else {
            //nothing found
            return -1;
        }
    }

    private void BoyerMoore(String pat, int[] right) {

        // position of rightmost occurrence of c in the pattern
        for (int c = 0; c < right.length; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }

    /**
     * Returns the number of character compares that where performed during the last search.
     *
     * @return the number of character comparisons during the last search.
     */
    int getComparisonsForLastSearch() {

        return 0;
    }

}
