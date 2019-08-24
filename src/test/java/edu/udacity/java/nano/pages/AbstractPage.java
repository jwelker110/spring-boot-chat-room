package edu.udacity.java.nano.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {

    public WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public <T> WebDriverWait webDriverWait(long timeoutInSeconds) {
        return new WebDriverWait(this.driver, timeoutInSeconds);
    }

}
