package gr.xe.selenium.qaChallenge;

import gr.xe.selenium.pom.MainPage;
import gr.xe.selenium.pom.ResultsPage;
import gr.xe.selenium.pom.enums.PropertyDropdownEnum;
import gr.xe.selenium.pom.enums.TransactionDropdownEnum;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This is a simple Test Suite that covers the Part 1 and Part 2 of the xe.gr
 * assignment.
 */
public class XESimpleTestSuite {

    private WebDriver webDriver;

    /**
     * Perform all necessary initializations. In a more elaborate solution we
     * would check for additional browsers and set up parameters but for now we
     * stick only with setting up chrome.
     */
    @BeforeClass
    public void setUp() {
        this.setChromeDriver();
        webDriver.manage().window().maximize();
    }

    /**
     * Perform any necessary finalizations
     */
    @AfterClass
    public void finishTest() {
        webDriver.close();

    }

    private void setChromeDriver() {
        String path = System.getProperty("user.dir");
        String chromeDriverPath = path + "/src/main/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        webDriver = new ChromeDriver();
    }

    /**
     * PART 1 TEST
     *
     * This test checks that after we perform a simple (without any input)
     * search, we get a list of results. The flow of the test is this:
     *
     * <ul>
     * <li>Go to Main Page </li>
     * <li>Click the Property Tab</li>
     * <li>Click the Search Button (no need to enter something in the Search
     * textfield)</li>
     * <li><code>Assert</code> that the No Results container web element does not
     * appear. This container appears if we perform a search that returns no
     * results, so if this is not present then we have some results. If the
     * No Results container is not present then an Exception will be thrown
     * and the test will pass.</li>
     * </ul>
     * 
     * The test passes if a TimeoutExcpetion is thrown due to the the No Results container
     * not being present.
     *
     */
    @Test ( expectedExceptions = { TimeoutException.class } )
    public void propertySearchTabTest() {

        ResultsPage resultsPage = new MainPage(webDriver).
                goTo().
                clickPropertyTab().
                clickSearchButton();

        //check that we have at least one result. To check that we check if 
        //the No Results container exists. If everything goes well and we
        //have at least one result then this web element should be null.
        resultsPage.getNoResultsContainerWebElement();
    }

    /**
     * PART 2 TEST
     *
     * This test checks that the price filters in the Search Results page work
     * as expected. The way to check that is:
     *
     * <ul>
     * <li>go to Main Page</li>
     * <li>select Transaction:Buy and as Property:Residence</li>
     * <li>click the Search Button</li>
     * <li>set the filter to a minimum price (for this test is 500000)</li>
     * <li><code>Assert</code> that we get a list of results </li>
     * <li><code>Assert</code> that the first result's price is more than the
     * minimum test price.</li>
     * </ul>
     * 
     * The test passes if we get a list of results and the first result's price
     * is more than the minimum that we have set.
     */
    @Test
    public void checkPriceFiltersTest() {
        //The mimimum price that want to check against the search results.
        int minimumPrice = 500000;

        /*
        main test flow is here
         */
        ResultsPage resultsPage = new MainPage(webDriver).
                goTo().selectFromTransactionDropdownMenu(
                        TransactionDropdownEnum.BUY).
                selectFromPropertyDropdownMenu(
                        PropertyDropdownEnum.RESIDENCE).
                clickSearchButton().
                clickPriceFilter().
                setMinimumPriceFilter(minimumPrice);

        //We have entered the minimum price, now press enter to get the results.
        resultsPage.getMinimumPriceWebElement().sendKeys(Keys.ENTER);

        //Assert that we get a list of results
        try {
            List<WebElement> resultContainerList = resultsPage.getAllResultsList();
            assertTrue("It seems that the results list came as null or empty when we should have at least one result."
                    + "Check for a missing exception or if something changed"
                    + "in the logic of our test.", resultContainerList != null && !resultContainerList.isEmpty());
        } catch (Exception ex) {
            //If we are here then this means that the list of elements did not load properly.
            Assert.fail("The results list did not load properly or no results returned for our search", ex);
        }

        //Get the first result and check that the price is in the range we specified
        WebElement firstResultWebElement = resultsPage.getFirstResultWebElement();

        //Get the price text from the first result. It will contain a lot of 
        //unecessary info, we need to parse the price number properly.
        String firstSearchResultText = resultsPage.getPriceAsTextFromSearchResult(
                firstResultWebElement);
        String euro = "\u20ac";
        firstSearchResultText = firstSearchResultText.replace(euro, "").replace(".", "").trim();
        int firstSearchResultPrice = Integer.parseInt(firstSearchResultText);

        //Is the current price more than the minimum test price?
        assertTrue("The price in the first search result is lower"
                + " than the one we have set as our test minimum.", firstSearchResultPrice >= minimumPrice);
    }
}
