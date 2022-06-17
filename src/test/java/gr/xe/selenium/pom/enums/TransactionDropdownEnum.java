package gr.xe.selenium.pom.enums;

/**
 * This <code>Enum</code> contains all the transactions that we can select
 * from the transaction dropdown menu in xe.gr Main Page.
 * @author pkalogerop
 */
public enum TransactionDropdownEnum {
    
    BUY("[data-id=buy]"),
    RENT("[data-id=rent]"),
    VALUABLE_CONSIDERATION("[data-id=valuable-consideration]"),
    EXCHANGE("[data-id=exchange]");
    
        
    private final String cssSelector;
    
    TransactionDropdownEnum(String cssSelector) {
        this.cssSelector = cssSelector;
    }
    
    /**
     * This is the css selector that we will use to properly fetch and click
     * the appropriate transaction.
     * @return a <code>String</code> representation of a css selector.
     */
    public String getCssSelector() {
        return cssSelector;    
    }
}
