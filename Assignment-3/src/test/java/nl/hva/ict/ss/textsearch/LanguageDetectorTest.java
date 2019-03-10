package nl.hva.ict.ss.textsearch;

import org.junit.Before;
import org.junit.Test;

public class LanguageDetectorTest {
    private LanguageDetector detector;

    private final char[] ENGLISH = {'z', 'q', 'x', 'j', 'k', 'v', 'b', 'p', 'y', 'g', 'f', 'w', 'm', 'u', 'c', 'l', 'd', 'r', 'h', 's', 'n', 'i', 'o', 'a', 't', 'e'};
    private final char[] DUTCH = {'q', 'x', 'y', 'f', 'c', 'b', 'p', 'z', 'w', 'j', 'u', 'v', 'm', 'k', 'g', 'h', 'l', 's', 'd', 'r', 'o', 'i', 't', 'a', 'n', 'e'};



    @Before
    public void setup() {
        detector = new LanguageDetector(getClass().getResourceAsStream("/edu/princeton/cs/algs4/Huffman.java"));
    }

    // Add your tests here. They are allowed  to NOT use assertXxxx... :-)

    @Test
    public void testJavadocRemoval() {
       detector.javadocDetector();
    }

    @Test
    public void testMethodCallCounter(){
        detector.countMethodCalls();
    }

    @Test
    public void testIfFrequencyDistributionCanBeCalculated(){
        detector.detectFrequencyDistribution();
    }

    @Test
    public void TestIfLanguageCanBeCompared(){
        detector.detectFrequencyDistribution();
        System.out.println("Percentage of English in the context: ");
        detector.callculateLanguage(ENGLISH, "English");
        System.out.println("Percentage of Dutch in the context: ");
        detector.callculateLanguage(DUTCH, "Dutch");
    }
}