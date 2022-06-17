package gr.xe.selenium.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * A Page Object Model that represents the Login Page.
 *
 * @author pkalogerop
 */
public class LoginPage extends BasePOM {

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Click the Login tab.
     *
     * @return an instance of the <code>LoginPage</code> Page Object Model.
     */
    public LoginPage clickLoginTab() {
        getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("tab_login"))).click();

        return this;
    }

    /**
     * Set the email. This acts as the username.
     *
     * @param email the email we want to set.
     * @return an instance of the <code>LoginPage</code> Page Object Model.
     */
    public LoginPage setEmail(String email) {
        WebElement emailWebElement = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        emailWebElement.clear();
        emailWebElement.sendKeys(email);

        return this;
    }

    /**
     * Set the password.
     *
     * @param password the password we want to set.
     * @return an instance of the <code>LoginPage</code> Page Object Model.
     */
    public LoginPage setPassword(String password) {
        WebElement passwordWebElement = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("password")));

        passwordWebElement.clear();
        passwordWebElement.sendKeys(password);

        return this;
    }

    /**
     * Click the login button to login. Note that the landing page after this
     * action may differ (depending on where we were before the login), so we
     * return a <code>BasePOM</code> instance
     *
     * @return an instance of the <code>BasePOM</code>.
     */
    public BasePOM clickLoginButton() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(
                        "#box_login > p.login_button > a > span")))
                .click();
        return new BasePOM(getWebDriver());
    }

}
