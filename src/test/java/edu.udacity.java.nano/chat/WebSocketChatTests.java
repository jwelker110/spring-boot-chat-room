package edu.udacity.java.nano.chat;

import edu.udacity.java.nano.pages.ChatPage;
import edu.udacity.java.nano.pages.LoginPage;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebSocketConfig.class})
@WebAppConfiguration
public class WebSocketChatTests {

    private WebDriver driver;

    @Before
    public void setup() {
        driver = new HtmlUnitDriver(true);
    }

    @After
    public void destroy() {
        if (driver != null) {
            driver.close();
        }
    }

    @Test
    public void loginAndJoin() throws Exception {
        LoginPage page = LoginPage.to(driver);
        ChatPage chat = page.loginAndJoin("jdoe");

        (new WebDriverWait(chat.driver, 10)).until(new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver input) {
                System.out.println("username: " + chat.getUsername().getText());
                return chat.getUsername().getText().equals(page.getUsernameText());
            }
        });
    }

    @Test
    public void sendMessage() throws Exception {
        LoginPage page = LoginPage.to(driver);
        ChatPage chat = page.loginAndJoin("jdoe");
        chat.sendChatMessage("Hello World!");

//        driver.get("http://localhost:8080/index?username=jdoe");
//        WebElement element = driver.findElement(By.id("msg"));
//        element.sendKeys("Hello World");
//
//        WebElement sendBtn = driver.findElement(By.id("sendBtn"));
//        sendBtn.click();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver input) {
                System.out.println("current URL: " + chat.getCurrentUrl());
                return chat.getMessageContent() != null && chat.getMessageContent().getText().equals(page.getUsernameText() + ": " + chat.getMessageText());
            }
        });
    }

    @Test
    public void logout() throws Exception {
        driver.get("http://localhost:8080/index?username=jdoe");
        WebElement element = driver.findElement(By.id("logout"));
        element.click();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver input) {
                System.out.println("logout triggered. current URL: " + driver.getCurrentUrl());
                return driver.getCurrentUrl().equals("http://localhost:8080/");
            }
        });
    }
}
