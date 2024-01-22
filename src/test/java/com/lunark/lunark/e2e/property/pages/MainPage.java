package com.lunark.lunark.e2e.property.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    @FindBy(css="a:has(i.at-account)")
    WebElement loginLink;

    @FindBy(css="a:has(i.cil-folder)")
    WebElement myPropertiesLink;

    @FindBy(css="div.cdk-overlay-container")
    WebElement loginDialog;

    @FindBy(css = "input[name=\'email\']")
    WebElement emailInput;

    @FindBy(css = "input[name=\'password\']")
    WebElement passwordInput;

    @FindBy(id = "sign-in")
    WebElement signInButton;


    public MainPage(WebDriver driver) {
       this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openLoginDialog() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        loginLink.click();
        wait.until(ExpectedConditions.visibilityOf(loginDialog));
    }

    public void logIn(String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(emailInput, passwordInput));
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        wait.until(ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(signInButton),
                ExpectedConditions.textToBePresentInElementValue(emailInput, email),
                ExpectedConditions.textToBePresentInElementValue(passwordInput, password)
        ));
        signInButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginDialog));
    }

    public boolean isLoggedInAsHost() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.visibilityOf(myPropertiesLink));
            return myPropertiesLink.isDisplayed();
        }
        catch(Exception e) {
            return false;
        }
    }
}
