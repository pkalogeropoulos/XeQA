package gr.xe.selenium.qaChallenge;

import gr.xe.selenium.pom.BasePOM;
import gr.xe.selenium.pom.LoginPage;
import gr.xe.selenium.pom.MainPage;
import gr.xe.selenium.pom.ResultsPage;
import gr.xe.selenium.pom.enums.LocalizationEnum;
import gr.xe.selenium.utilities.LocalizationReader;
import gr.xe.selenium.utilities.TestUser;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelectLandForSaleApplyPlotFilterSaveSearch {

    private WebDriver driver; //REFACTORED: set it to private
    WebDriverWait wait; //REFACTORED: set it to private

    String username = "FILL IN YOUR USERNAME HERE";
    String password = "FILL IN YOUR PASSWORD HERE";

    @BeforeClass
    public void initialize() {
        setChromeDriver();

        driver.manage().window().maximize();

    }

    private void setChromeDriver() {
        String path = System.getProperty("user.dir");
        String chromeDriverPath = path + "/src/main/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

    @Test(enabled = false, priority = 0, description = "Visit gr.xe.gr, select land for sale from the categories and perform a search")
    public void visitXeSelectLandForSaleFromCategoriesPerformSearch() {
        //We start the chromedriver
        driver = new ChromeDriver();
        //We define the implicit wait for this driver
        wait = new WebDriverWait(driver, 15);
        //We visit xe.gr
        driver.navigate().to("https://www.xe.gr/");
        //Select land for sale
        driver.findElement(By.cssSelector("a[href*='poliseis-gis']")).click();
        //Perform the search
        driver.findElement(By.cssSelector(".buttonSave")).click();
    }

    @Test(enabled = true, priority = 0, description = "Visit gr.xe.gr, select land for sale from the categories and perform a search")
    public void visitXeSelectLandForSaleFromCategoriesPerformSearchRefactored() {

        ResultsPage resultsPage = new MainPage(driver).goTo().clickLandSaleLink().clickSearchButton();

        //Assert that we get a list of results
        try {
            List<WebElement> resultContainerList = resultsPage.getAllResultsList();
            assertTrue("It seems that the results list came as null or empty when we should have at least one result."
                    + "Check for a missing exception or if something changed"
                    + "in the logic of our test.", resultContainerList != null && !resultContainerList.isEmpty());
        } catch (Exception ex) {
            //If we are here then no results are present in the DOM and an exception was thrown
            Assert.fail("No results are present in the result's page.", ex);
        }
    }

    @Test(enabled = false, priority = 1, description = "Expand the filters and apply plot")
    public void expandFiltersApplyPlot() {
        //The cookies banner obstructs our test here so we have to close it
        closeCookiesBannerIfPresent();
        //Expand the filters tab
        driver.findElement(By.cssSelector("div[data-toggle='expand_filters']")).click();
        //Select the plot option
        driver.findElement(By.cssSelector(".checkbox-filter-container label[id*='plot-checkbox']")).click();
        //Apply the filter
        driver.findElement(By.cssSelector(".buttons-container .submit-button")).click();
    }

    @Test(enabled = true, priority = 1, description = "Expand the filters and apply plot")
    public void expandFiltersApplyPlotRefactored() {
        ResultsPage resultsPage = new MainPage(driver).
                goTo().
                clickLandSaleLink().
                clickSearchButton().
                clickFiltersButton();
        /*
        We will check now if the filters modal is present in the DOM. If
        everything goes ok no exception will be thrown and the test will pass.
        If an exception is thrown (like the Web Element is not present) then
        the test fails.
         */
        try {
            resultsPage.getFiltersModalWebElement();
        } catch (Exception ex) {
            Assert.fail("The filters modal is not present and visible", ex);
        }

    }

    @Test(enabled = false, priority = 2, description = "Save the previous search")
    public void saveSearch() {
        //Click to save the search
        driver.findElement(By.cssSelector("[data-testid='save-search-btn']")).click();
        //Wait for username to be visible and fill it in
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
        driver.findElement(By.cssSelector("input#email")).sendKeys(username);
        //Fill in password
        driver.findElement(By.cssSelector("input#password")).sendKeys(password);
        //Click to connect
        driver.findElement(By.cssSelector(".login_button span")).click();
        //Hint: If your search is already saved you are redirected to your saved searches instead of being able to save your search.
        //Manually delete your search if you already saved it once and  want to save it again.
        //Click to save the search again
        driver.findElement(By.cssSelector("[data-testid='save-search-btn']")).click();
        //Deal with the save search pop-up
        saveSearchDoNothingIfAlreadySaved();
    }

    @Test(enabled = true, groups = {"loginFunctionality"}, priority = 2, description = "Save the previous search")
    public void saveSearchRefactored() throws Exception {
        /*
        We want to start our test clean so we check that we are logged out.
         */
        logoutIfNecessary();

        ResultsPage resultsPage = new MainPage(driver).
                goTo().
                clickLandSaleLink().
                clickSearchButton().
                clickFiltersButton().
                selectPlotLandFromFilters().
                clickSubmitFiltersButton().
                clickSaveSearchButton();

        //We should be logged out, so the login popup should have appeared.
        LoginPage loginPage = resultsPage.clickLoginButton();

        //Perform a proper login using correct credentials. May throw an exception
        //if the test user instance is not created correctly.
        TestUser testUser = TestUser.createTestUser();

        loginPage.setEmail(testUser.getUsername()).setPassword(testUser.getPassword()).clickLoginButton();

        //we are logged in properly now. We should also be at the Results page,
        //so try to save our search filters again.
        resultsPage.clickSaveSearchButton().clickSubmitSaveSearchButton();
        
        /*
        The filter has been saved, check that the proper success image and
        text are present.
         */
        assertTrue("The success image file after saving our search filters is not properly displayed",
                resultsPage.getSuccessImageAfterSavingSearch().isDisplayed());

        String successText = LocalizationReader.getLocalizedText(LocalizationEnum.SUCCESS_MESSAGE_AFTER_SAVING_FILTERS);
        assertEquals("The success text after saving our search filters is not the expected.",
                resultsPage.getSuccessMessageAfterSavingSearch(), successText);
    }

    /*
     * Logs out the current user session
     */
    private void logoutIfNecessary() {
        BasePOM basePom = new BasePOM(driver);
        if (basePom.isUserLoggedIn()) {
            basePom.logout();
        }
    }

    @AfterClass
    public void close() {
        //We close the driver
        driver.quit();
    }

    /*
    * Closes the cookies banner if it's present on the page
     */
    public void closeCookiesBannerIfPresent() {
        if (driver.findElements(By.cssSelector(".btn-disclaimer-ok")).size() > 0) {
            driver.findElement(By.cssSelector(".btn-disclaimer-ok")).click();
        }
    }

    /*
     * Clicks to save search in the save search pop up and then closes it.
     * If no pop-up is present then it does nothing.
     */
    public void saveSearchDoNothingIfAlreadySaved() {
        if (driver.findElements(By.cssSelector("input.button-property")).size() > 0) {
            //Save the search
            driver.findElement(By.cssSelector("input.button-property")).click();
            //Close the success modal
            driver.findElement(By.cssSelector(".xe-modal-close .xe.xe-close")).click();
        }
    }

}
