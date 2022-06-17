package gr.xe.selenium.utilities;

import gr.xe.selenium.pom.enums.LocalizationEnum;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class acts as the first step in Localization and handling text/messages
 * in our code.
 */
public class LocalizationReader {

    /**
     * Get the localized text for the specified <code>Enum</code>
     * @param localizationEnum the text we want to get as specified in the
     * <code>LocalizationEnum</code>.
     * @return the localized text
     * @throws Exception 
     */
    public static String getLocalizedText(LocalizationEnum localizationEnum) throws Exception {
        //TODO: Expand this method to accept as a parameter the appropriate Locale (en,gr)
        
        //Enforce the system to use utf-8
        System.setOut(new PrintStream(System.out, true, "UTF8"));
        
        
        //Get the json file that contains the localizations
        String path = System.getProperty("user.dir");
        String localizationFilePath = path + "/src/main/localization/dummyResources.json";
        
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(
              new InputStreamReader(new FileInputStream(localizationFilePath),StandardCharsets.UTF_8));
        JSONObject jsonObject = (JSONObject) obj;
        
        return (String) jsonObject.get(localizationEnum.getJsonFormat());
    }
}
