package com.lunark.lunark.e2e.property.pages;

import org.h2.mvstore.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditPropertyPage {
    private WebDriver driver;
    public static String heading = "Edit Property";

    @FindBy(css = "h1")
    private WebElement pageHeading;

    @FindBy(xpath = "//a[contains(text(), 'other')]")
    private WebElement otherLink;

    @FindBy(xpath = "//a[contains(text(), 'calendar')]")
    private WebElement calendarLink;

    @FindBy(xpath = "//a[contains(text(), 'price table')]")
    private WebElement priceTableLink;

    @FindBy(id="name-input")
    private WebElement nameInput;

    public EditPropertyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.visibilityOf(pageHeading),
                    ExpectedConditions.textToBePresentInElement(pageHeading, heading)
            ));
            return pageHeading.getText().equals(heading);
        } catch(Exception ex) {
            return false;
        }
    }

    public String getPropertyName() {
        if (!this.isLoaded()) {
            throw new RuntimeException("Edit property page should have been loaded");
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(otherLink));
        otherLink.click();
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(nameInput),
                (ExpectedCondition<Boolean>) driver -> nameInput.getAttribute("value").length() != 0
        ));
        return nameInput.getAttribute("value");
    }

    public void openCalendar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(calendarLink));
        calendarLink.click();
        wait.until((ExpectedCondition<Boolean>) driver -> calendarLink.getAttribute("class").equals("active"));
    }

    public void openPriceTable() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(priceTableLink));
        priceTableLink.click();
        wait.until((ExpectedCondition<Boolean>) driver -> priceTableLink.getAttribute("class").equals("active"));
    }
}
