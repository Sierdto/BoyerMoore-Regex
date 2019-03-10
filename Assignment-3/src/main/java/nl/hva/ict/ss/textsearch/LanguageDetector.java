package nl.hva.ict.ss.textsearch;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author Koen van der Fluit
 * @author Sierd Kamminga
 */
public class LanguageDetector {
    private String content; // Once an instance is created this will hold the complete content of the file.

    //Pattern detects javadoc type comments. Normal comments are ignored TODO: comment with more *** at the end will not be found
    private final String COMMENT_PATTERN_STRING = "/\\*\\*(?:.|[\\n\\r])*?\\*/"; // \n \r Could be replaced with multiline and Dotall.
    private final String METHOD_PATTERN_STRING = "(([a-z_A-Z0-9.]+?(\\(\\))|(\\((.?)\\)(?<!\\(\\),?)))(?!\\s\\{))";

    private final char[] CHARACTER_ARRAY = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private char[] contentCharacterArray = {'a'};
    private Map<Character, Integer> characterCounter = new HashMap<>(Math.min(contentCharacterArray.length, CHARACTER_ARRAY.length));

    private static List<String> allMatches = new ArrayList<String>();
    private final Pattern Method_PATTERN = Pattern.compile(METHOD_PATTERN_STRING);


    LanguageDetector(InputStream input) {
        Scanner sc = new Scanner(input);
        sc.useDelimiter("\\Z"); // EOF marker
        content = sc.next().toLowerCase(); //everything lowercased
        contentCharacterArray = content.toCharArray();
    }

    /**
     * Gives visual proof that the javadoc can be detected and or removed
     */

    void javadocDetector() {
        Matcher m = Pattern.compile(COMMENT_PATTERN_STRING).matcher(content);
        while (m.find()) {
            allMatches.add(m.group());
        }

        System.out.println("Found Javadoc begin ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println(allMatches);
        System.out.println("Found Javadoc end   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");

    }

    /**
     * Removes the javadoc from the content and scans for method calls
     * The gotten methods  will be printed as proof
     */

    void countMethodCalls() {
        String commentCleanContent = content.replaceAll(COMMENT_PATTERN_STRING, "");

        //offset to later prevent out of bound on the array loops
        int callCounter = -1;

        Matcher methodMatcher = Method_PATTERN.matcher(commentCleanContent);
        while (methodMatcher.find()) {
            callCounter += 1;
            System.out.println(methodMatcher.group());
        }
        System.out.println("Amount of calls: " + callCounter);
    }

    /**
     * Detects the character frequency distribution and tries to check if the language
     * is according to the ENGLISH FREQUENCY DISTRIBUTION defined above
     */
    void detectFrequencyDistribution() {

        char character = 0, ch;

        Map<Integer, Float> distributionPercentageMap = new HashMap<>(Math.min(contentCharacterArray.length, CHARACTER_ARRAY.length));

        //Loop through the character array TODO Efficiency N2, can be better...
        for (int i = 0; i != contentCharacterArray.length; i++) {
            character = contentCharacterArray[i];
            //Remove whitespaces
            for (int j = 0; j != CHARACTER_ARRAY.length; j++) {
                if (CHARACTER_ARRAY[j] == character) {
                    //If the key doesn't exist in the map -> add it
                    if (!characterCounter.containsKey(character)) {
                        characterCounter.put(character, 1);
                    } else { //If it already exists -> update the count with 1
                        characterCounter.put(character, characterCounter.get(character) + 1);
                    }
                }
            }
        }

        //Should not be able to exceed more then the array size
        for (int i = 0; i != CHARACTER_ARRAY.length; i++) {
            //Define the amount with the total amount of chars in the content string
            float characterPercentage = (100 / ((float) content.length()) * (characterCounter.get(CHARACTER_ARRAY[i])));
            distributionPercentageMap.put(i, characterPercentage);
            //System.out.println("Distribution percentage for " + CHARACTER_ARRAY[i] + " is " + characterPercentage + " %");
        }

        //Print out the map
        System.out.println("Frequency of the found legal characters \n");
        System.out.println(characterCounter + "\n");
    }

    void callculateLanguage(char[] mostUsedCharacters, String language) {

        //sort the numchars on value
        Map<Character, Integer> sortedByValues = characterCounter.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        // only values are in the sorted Map, make it a array for easier comparison
        Object[] di = sortedByValues.values().toArray();

        int mostUsedLettersLength = mostUsedCharacters.length;
        int times = 0;

        System.out.println("frequency distribution of highest and lowest characters: ");
        for (int i = 0; i != mostUsedLettersLength; i++) {
            //compare the most used letters with the values
            if (sortedByValues.get(mostUsedCharacters[i]) == di[i]) {
                System.out.println("The letter: " + mostUsedCharacters[i] + ". Value: " + di[i]);
                times++;
            }
        }

        float percentage = (100 / ((float) mostUsedLettersLength) * (times));
        System.out.println("\n" + "Percentage of: " + language + ": " + percentage + "% \n");
    }
}
