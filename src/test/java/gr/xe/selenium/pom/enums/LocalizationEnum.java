package gr.xe.selenium.pom.enums;

/**
 * This <code>Enum</code> acts as a helper to retrieve the appropriate texts and messages
 * without any concern about their locale.
 * @author pkalogerop
 */
public enum LocalizationEnum {

    /*
     * So far we check only one text that appears after we have properly
    saved our search results.
     */
    SUCCESS_MESSAGE_AFTER_SAVING_FILTERS("modalSuccessMessage");

    private final String jsonPath;

    LocalizationEnum(String jsonPath) {
        this.jsonPath = jsonPath;
    }
    
    public String getJsonFormat() {
        return jsonPath;
    }

}
