package com.lunark.lunark.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class PropertyPage {
    private final WebDriver driver;

    private static final Duration DURATION = Duration.ofSeconds(10);

    @FindBy(xpath = "(//h3)[1]")
    private WebElement location;

    @FindBy(xpath = "//p[contains(text(), 'Minimum')]")
    private WebElement minimumGuests;

    @FindBy(xpath = "//p[contains(text(), 'Maximum')]")
    private WebElement maximumGuests;

    public PropertyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, DURATION);
    }

    public String getLocation() {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(location));
        return location.getText();
    }

    public String getMinimumGuests() {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(minimumGuests));
        String text = minimumGuests.getText();
        String[] tokens = text.split(" ");
        return tokens[tokens.length - 1];
    }

    public String getMaximumGuests() {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(maximumGuests));
        String text = maximumGuests.getText();
        String[] tokens = text.split(" ");
        return tokens[tokens.length - 1];
    }
}

