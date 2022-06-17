package gr.xe.selenium.pom.enums;

/**
 * This <code>Enum</code> contains all the property options that we can select
 * from the property dropdown menu in xe.gr Main Page.
 * @author pkalogerop
 */
public enum PropertyDropdownEnum {
    
    RESIDENCE("[data-testid=re_residence]"),
    PROFESSIONAL("[data-testid=re_prof]"),
    LAND("[data-testid=re_land]"),
    PARKING("[data-testid=re_parking]"),
    MISC("[data-testid=re_misc]");
    
    
    private final String cssSelector;
    
    PropertyDropdownEnum(String cssSelector) {
        this.cssSelector = cssSelector;
    }
    
    /**
     * This is the css selector that we will use to properly fetch and click
     * the appropriate property.
     * @return a <code>String</code> representation of a css selector.
     */
    public String getCssSelector() {
        return cssSelector;
    }
    
    
}
