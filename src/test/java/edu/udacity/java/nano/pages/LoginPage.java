package edu.udacity.java.nano.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public class LoginPage extends AbstractPage {
    private WebElement username;

    @FindBy(className = "submit")
    private WebElement submit;

    private String usernameText;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public ChatPage loginAndJoin(String username) {
        this.usernameText = username;
        this.username.sendKeys(username);
        this.submit.click();
        return PageFactory.initElements(super.driver, ChatPage.class);
    }

    public static LoginPage to(WebDriver driver) {
        driver.get("http://localhost:8080");
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public WebElement getUsername() {
        return username;
    }

    public void setUsername(WebElement username) {
        this.username = username;
    }

    public WebElement getSubmit() {
        return submit;
    }

    public void setSubmit(WebElement submit) {
        this.submit = submit;
    }

    public String getUsernameText() {
        return usernameText;
    }

    public void setUsernameText(String usernameText) {
        this.usernameText = usernameText;
    }
}
