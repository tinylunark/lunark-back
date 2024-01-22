package com.lunark.lunark.e2e.property.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyPropertiesPage {
    public static final String heading = "my properties";
    WebDriver driver;

    @FindBy(css = "h1")
    WebElement pageHeading;

    public MyPropertiesPage(WebDriver driver) {
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

    public void editProperty(String name) {
        if(!this.isLoaded()) {
            throw new RuntimeException("My properties page should be loaded");
        }
        String editButtonPath = "//mat-card-title[.=\'" + name + "\']/../../../mat-card-footer/div/button[2]";
        WebElement editButton = driver.findElement(By.xpath(editButtonPath));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        editButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(pageHeading, EditPropertyPage.heading));
    }
}
