package gr.xe.selenium.pom;

import gr.xe.selenium.pom.enums.PropertyDropdownEnum;
import gr.xe.selenium.pom.enums.TransactionDropdownEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object Model for the https://www.xe.gr/ main page.
 *
 * @author pkalogerop
 */
public class MainPage extends BasePOM {

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Navigate to the page represented by this Page Object Model.
     *
     * @return an instance of the <code>MainPage</code> Page Object Model.
     */
    public MainPage goTo() {
        getWebDriver().get("https://www.xe.gr/");
        return this;
    }

    /**
     * Click the properties tab.
     *
     * @return an instance of the <code>MainPage</code> Page Object Model.
     */
    public MainPage clickPropertyTab() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("property-tab"))).click();
        return this;
    }

    /**
     * Set a search term in the search textfield.
     *
     * @param searchTerm a <code>String</code> representing our search term.
     * @return an instance of the <code>MainPage</code> Page Object Model.
     */
    public MainPage setSearchTerm(String searchTerm) {
        WebElement searchFieldWebElement = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-testid=area-input]")));
        searchFieldWebElement.clear();
        searchFieldWebElement.sendKeys(searchTerm);

        return this;
    }

    /**
     * Click the Search button. This will take us to the search results page
     * represented by the <code>ResultsPage</code> Page Object Model.
     *
     * @return an instance of <code>ResultsPage</code> Page Object Model
     */
    public ResultsPage clickSearchButton() {
        WebElement searchButton = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[data-testid=submit-input]")));
        searchButton.click();

        return new ResultsPage(getWebDriver());
    }

    /**
     * Select a transaction (such as Sale, Rent etc) from the Transaction
     * dropdown menu.
     *
     * @param transaction an <code>Enum</code> that represents one of the
     * appropriate transactions that we want to choose.
     * @return an instance of the <code>MainPage</code> Page Object Model.
     */
    public MainPage selectFromTransactionDropdownMenu(TransactionDropdownEnum transaction) {
        //Click on the Transaction dropdown menu for all the transactions to appear.        
        String dropdownSelector = "[data-testid=open-property-transaction-dropdown]";
        getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(dropdownSelector))).click();

        //Click on the given transaction
        String transactionCssSelector = transaction.getCssSelector();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(transactionCssSelector))).click();

        return this;
    }

    /**
     * Select a property (such as Residence, Land etc) from the Properties
     * dropdown menu.
     *
     * @param property an <code>Enum</code> that represents one of the
     * appropriate properties that we want to choose.
     * @return an instance of the <code>MainPage</code> Page Object Model.
     */
    public MainPage selectFromPropertyDropdownMenu(PropertyDropdownEnum property) {
        //Click on the Property dropdown menu for all the properties to appear.        
        String dropdownSelector = "[data-testid=open-property-type-dropdown]";
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(dropdownSelector))).click();

        //Click the given property
        String propertyCssSelector = property.getCssSelector();
        getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(propertyCssSelector))).click();

        return this;
    }

    /**
     * Click on the Land Sale link. 
     * @return an instance of the <code>MainPage</code> Page Object Model.
     */
    public MainPage clickLandSaleLink() {
        String landSaleCssSelector = "div.grid-container a[href='https://www.xe.gr/property/s/poliseis-gis-oikopedon']";
        WebElement landSaleWebElement = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(landSaleCssSelector)));

        //The element will be out of view so scroll to see it.
        scrollToView(landSaleWebElement);

        landSaleWebElement.click();

        return this;
    }
}
