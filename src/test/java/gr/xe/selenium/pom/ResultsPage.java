package gr.xe.selenium.pom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object Model for the Search Results page.
 *
 * @author pkalogerop
 */
public class ResultsPage extends BasePOM {

    public ResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * In case we search for something and zero results come back, then this
     * container will appear.
     *
     * @return the container that holds all info about the No Results
     * <code>WebElement</code>
     */
    public WebElement getNoResultsContainerWebElement() {
        String noResultsContainerCssSelector = "[data-testid=no-results]";
        return getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(noResultsContainerCssSelector)));
    }

    /**
     * Click on the price Filter button. This will enable us to set minimum and
     * maximum values for the price.
     *
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage clickPriceFilter() {
        String selector = "[data-testid=price-filter-button]";
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector))).click();
        return this;
    }

    /**
     * Get the minimum price Web Element. This will appear after we have cliked
     * on the Price Filter using the
     * {@link #clickPriceFilter() clickPriceFilter}.
     *
     * @return the minimum price <code>WebElement</code>
     */
    public WebElement getMinimumPriceWebElement() {
        String minimumPriceCssSelector = "[data-testid=minimum_price_input]";
        return getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(minimumPriceCssSelector)));
    }

    /**
     * Set the minimum price for the minimum price filter.
     *
     * @param minimumPrice the minimum price we want to set
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage setMinimumPriceFilter(int minimumPrice) {
        WebElement minimumPriceWebElement = this.getMinimumPriceWebElement();
        minimumPriceWebElement.clear();
        minimumPriceWebElement.sendKeys(Integer.toString(minimumPrice));

        return this;
    }

    /**
     * Get a list of all the results that are visible after we have performed a
     * search or changed any filters.
     *
     * @return a <code>List</code> of <code>WebElement</code>s each one
     * representing a search result.
     */
    public List<WebElement> getAllResultsList() {
        String resultsSelector = "[data-testid='property-ad-container']";
        return getWait().until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector(resultsSelector)));
    }

    /**
     * This is a convenient method to get the first search result.
     *
     * @return A <code>WebElement</code> that contains all necessary info about
     * the first result.
     */
    public WebElement getFirstResultWebElement() {
        List<WebElement> allResults = this.getAllResultsList();
        /*
        We do not handle the case that something went wrong when we try to fetch
        the "all results list", if something fails we want to fail quickly
        and propagate that to our unit tests.
         */
        return allResults.get(0);
    }

    /**
     * Each search result that we get contains a lot of info, such as a picture,
     * texts etc. In this method we extract the price for the specified search
     * result.
     *
     * @param searchResult a <code>WebElement</code> representing the search
     * result from which we want to extract the price.
     * @return the price of this search result as <code>String</code>
     */
    public String getPriceAsTextFromSearchResult(WebElement searchResult) {
        return searchResult.findElement(
                By.cssSelector("[data-testid=property-ad-price]"))
                .getText();
    }

    /**
     * Click on the Filters Button.
     *
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage clickFiltersButton() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-testid=filters-button]"))).click();

        return this;
    }

    /**
     * This the modal window that contains all the filters in our search page.
     * It will appear after we click on the "More" button (located on top of the
     * results).
     *
     * @return a <code>WebElement</code> that acts as a container for all the
     * filters.
     */
    public WebElement getFiltersModalWebElement() {
        return getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-testid=xe-modal-wrapper] div.xe-modal-content")));
    }

    /**
     * Select the Plot Land from the Filters. Note that this will be visible
     * only after we have clicked the Filters button using the
     * {@link #clickFiltersButton() clickFiltersButton} method.
     *
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage selectPlotLandFromFilters() {
        WebElement plotLandWebElement = this.getFiltersModalWebElement().
                findElement(
                        By.cssSelector("[data-testid=building-type-option-plot-of-land-checkbox-message]"));

        scrollToView(plotLandWebElement);

        plotLandWebElement.click();

        return this;
    }

    /**
     * We click this button after we have selected our filters in order for them
     * to take effect. This button is part of the Filters modal. Check also the
     * {@link #getFiltersModalWebElement() getFiltersModalWebElement} method.
     *
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage clickSubmitFiltersButton() {
        WebElement submitButtonWebElement = this.getFiltersModalWebElement().
                findElement(
                        By.cssSelector("[data-testid=more-filters-submit-button]"));
        //It ay be out of view so scroll to see it.
        scrollToView(submitButtonWebElement);
        submitButtonWebElement.click();

        return this;
    }

    /**
     * Click the Save Search button. By clicking it we save our search filters
     * for future use.
     *
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage clickSaveSearchButton() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("save-search-btn"))).click();
        return this;
    }

    /**
     * Click this button to save your search filters and criteria. Note that
     * this button will be visible only if:
     * <ul>
     * <li>we are logged in</li>
     * <li>we have clicked the
     * {@link #clickSaveSearchButton() clickSaveSearchButton}</li>
     * </ul>
     *
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage clickSubmitSaveSearchButton() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-testid=submit-button]"))).click();

        return this;
    }

    /**
     * This is the modal <code>WebElement</code> that appears after we have
     * properly saved our search filters/criteria.
     *
     * @return a <code>WebElement</code> that acts as a container for the
     * Success Modal element.
     */
    public WebElement getSuccessModalAfterSavingSearch() {
        return getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.xe-modal-content")));
    }

    /**
     * Utility method that extracts the success message that appears after we
     * have properly saved our search filters/criteria. See also
     * {@link #getSuccessModalAfterSavingSearch() getSuccessModalAfterSavingSearch}
     * method for reference.
     *
     * @return the success message as <code>String</code>
     */
    public String getSuccessMessageAfterSavingSearch() {
        WebElement successModal = this.getSuccessModalAfterSavingSearch();
        return successModal.findElement(
                By.cssSelector("[data-testid=success-message] p")).getText();
    }

    /**
     * Utility method that gets the <code>WebElement</code> that contains an
     * image which shows that we have properly saved our search
     * filters/criteria. See also
     * {@link #getSuccessModalAfterSavingSearch() getSuccessModalAfterSavingSearch}
     * method for reference.
     *
     * @return the success image as <code>WebElement</code>
     */
    public WebElement getSuccessImageAfterSavingSearch() {
        WebElement successModal = this.getSuccessModalAfterSavingSearch();
        return successModal.findElement(By.cssSelector("[data-testid=success-message] img"));
    }

    /**
     * Closes the modal dialog that appears after we have saved our search
     * filters/criteria. After closing it we should be still on the Results
     * Page. See also
     * {@link #getSuccessModalAfterSavingSearch() getSuccessModalAfterSavingSearch}
     * method for reference.
     *
     * @return an instance of the <code>ResultsPage</code> Page Object Model.
     */
    public ResultsPage closeSuccessModalAfterSavingSearch() {
        WebElement successModal = this.getSuccessModalAfterSavingSearch();
        successModal.findElement(By.cssSelector("[data-testid=xe-modal-close]")).click();

        return this;
    }

    /**
     * This button will appear if we are not logged in and we want to perform an
     * action (such as saving your search) that needs an active session.
     *
     * @return an instance of the <code>LoginPage</code> Page Object Model.
     */
    public LoginPage clickLoginButton() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-testid=login-popup] a"))).click();

        return new LoginPage(getWebDriver());
    }

}
