package nl.hva.ict.ss.textsearch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BackwardsSearchTest {
    protected BackwardsSearch searchEngine;

    @Before
    public void setup() {
        searchEngine = new BackwardsSearch();
    }

    @Test
    public void findSingleOccurrence() {
        int index = searchEngine.findLocation("needle", "whereistheneedleikthishaystack");

        assertEquals("whereisthe".length(), index);
    }

    @Test
    public void cantFindOccurrence() {
        int index = searchEngine.findLocation("needle", "thereisnothinginthishaystack");

        assertEquals(-1, index);
    }

    @Test
    public void findDoubleOccurrence() {
        int index = searchEngine.findLocation("needle", "whereistheneedleikthisneedlehaystack");

        //2 needles
        assertEquals(2, index);
    }

    @Test
    public void findNedStarksNeedle() {
        int index = searchEngine.findLocation("ned", "nnebbendennenednnebbendennledneedle");

        // lengte 12
        assertEquals("nnebbendenne".length(), index);
    }

    @Test
    public void findNeedleInGiganticText() {
        String text = "Needles Lighthouse is een vuurtoren die zich bevindt in het uiterste westen van de The Needles," +
                " een rij krijtachtige klippen. Zij vormen de uitloper van het schiereiland in het westen van het Isle Of Wight, " +
                "een graafschap voor de zuidkust van Engeland, ter hoogte van Southampton. Zeevaarders die langs dit " +
                "schiereiland het Isle of Wight naderen en vervolgens de zeestraat van de Solent willen invaren om de " +
                "havens van Southampton of Portsmouth te bereiken worden door de vuurtoren attent gemaakt op de gevaarlijke " +
                "krijtrotsen waarvan het hoogste piek zich op 137 m bevindt. De toren wordt gecontroleerd vanuit het " +
                "Planning Centre van Trinity House in Harwich in het Engelse Essex. Trinity House is verantwoordelijk " +
                "voor de navigatiemiddelen in Engeland, Wales, Gibraltar en de Kanaaleilanden.";

        String textTillNeedle = "Needles Lighthouse is een vuurtoren die zich bevindt in het uiterste westen van de The ";

        int index = searchEngine.findLocation("needle", text.toLowerCase());

        // lengte 87
        assertEquals(textTillNeedle.length(), index);
    }
}