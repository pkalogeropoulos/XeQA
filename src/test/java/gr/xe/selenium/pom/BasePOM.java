package gr.xe.selenium.pom;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This is the base class for all Page Object Models that we will create.
 * @author pkalogerop
 */
public class BasePOM {

    private WebDriver webDriver;
    private WebDriverWait wait;

    
    public BasePOM(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10).toSeconds());
    }

    /**
     * Get the current instance of our WebDriver.
     * @return 
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }
    
    /**
     * Get the current instance of our <code>WebDriverWait</code>. We will use 
     * this to wait for all <code>WebElement</code>.
     * @return 
     */
    public WebDriverWait getWait() {
        return wait;
    }
    
    /**
     * Checks if we are logged in the xe.gr app.
     * @return <code>true</code> if we are logged in, <code>false</code> otherwise.
     */
    public boolean isUserLoggedIn() {
        
        /*
        There are a few ways we can test if we are logged in.  One way would be
        to check for the existence of a specific Web Element like the 
        class="user-info-label", but the specifics of this element seem to 
        differ from page to page (so this will require a lot of code and will
        probably lead to bugs). We could check for cookies or in the database for an
        open session. 
        
        now the way we check it is:
        Go to a page that you would have access only if you were logged in such
        as: https://my.xe.gr/app/static/start/ ->
        If we are not logged in then we would be redirected to the following url:
        https://my.xe.gr/login?redirect=https://my.xe.gr/app/static/start/
        */
        
        //This page we will have access to only if we are logged in.
        String profileInfoUrl = "https://my.xe.gr/app/static/start/";
        webDriver.get(profileInfoUrl);
        
        //If we are logged in then the url will not change (if we are not logged
        //we will be redirected to the login page.
        return profileInfoUrl.equals(getWebDriver().getCurrentUrl());        
    }
    
    /**
     * Logs out the logged in user.
     * @return an instance of the <code>BasePOM</code>.
     */
    public BasePOM logout() {
        /*
        In order to logout we need to access the profile button (located on the
        top left mostly), hover or click and the click the logout option. The
        profile button has different implementations and tags that depend on
        the current page that the user is, also performs different requests.
        
        for instance we see this request:
        https://www.xe.gr/logoutsso where we are in the "my profile" page
        
        and this request:
        https://www.xe.gr/logout?return_url=https%3A%2F%2Fwww.xe.gr%2F
        when we are in the main page
        
        so for now, in order to log out we go the
        https://www.xe.gr/logoutsso (just a simple request).
        
        
        FIXME: Revisit this implementation when we have more knowledge
        about the business model and see if we can find a common way to logout
        */
        
        webDriver.get("https://www.xe.gr/logoutsso");
        
        return this;
    }
    
    /**
     * Scroll the web element into view. This is to avoid any miss-clicks and errors and also 
     * to emulate even more the proper user behavior.
     * @param webElement the <code>WebElement</code> that we want to scroll to view.
     */
    public void scrollToView(WebElement webElement) {
        
        ((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }
}
