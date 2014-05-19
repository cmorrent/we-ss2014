import HighsoreService.SimpleHighscoreService.SimpleHighscoreService;
import org.junit.Test;

import javax.xml.soap.SOAPException;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by willi on 5/14/14.
 */
public class SOAPTest {

    @Test
    public static void testMessage() throws IOException, SOAPException {
        SimpleHighscoreService.sendGame(null);
        assertTrue(false);
    }
}
