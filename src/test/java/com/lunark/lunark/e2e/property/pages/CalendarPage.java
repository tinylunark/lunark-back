package com.lunark.lunark.e2e.property.pages;

import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class CalendarPage {
    private String currentMonth = "NOV";
    private String currentYear = "2023";

    private WebDriver driver;

    @FindBy(css = "button.mat-calendar-period-button")
    private WebElement periodButton;

    @FindBy(css = "button.mat-calendar-period-button span.mdc-button__label span")
    private WebElement periodButtonLabel;

    @FindBy(id = "change-availability-button")
    private WebElement changeAvailabilityButton;

    @FindBy(id = "delete-availability-button")
    private WebElement deleteAvailabilityButton;

    @FindBy(css = "tbody.mat-calendar-body")
    private WebElement calendarBody;

    @FindBy(css = "input[name='price']")
    private WebElement priceInput;

    @FindBy(css = "button.btn-save")
    private WebElement saveButton;

    @FindBy(css = "mat-dialog-container")
    private WebElement successDialogContainer;

    @FindBy(css = "mat-dialog-container button")
    private WebElement dialogOkButton;

    public CalendarPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private void pickYear(int year) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(periodButton));
        String currentLabel = periodButtonLabel.getText();
        periodButton.click();
        wait.until(driver1 -> !periodButtonLabel.getText().equals(currentLabel));
        WebElement yearButton = driver.findElement(new By.ByCssSelector("button[aria-label='" + year + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(yearButton));
        String currentLabel2 = periodButtonLabel.getText();
        yearButton.click();
        wait.until(driver1 -> !periodButtonLabel.getText().equals(currentLabel2));
    }
    private void pickMonth(String monthAbbreviation) {
        monthAbbreviation = monthAbbreviation.toUpperCase();
        WebElement monthButton = driver.findElement(new By.ByXPath("//span[.=' " + monthAbbreviation + " ']/.."));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(monthButton));
        String currentLabel = periodButtonLabel.getText();
        monthButton.click();
        wait.until(driver1 -> !periodButtonLabel.getText().equals(currentLabel));
    }

    private void pickDay(int day) {
        WebElement dayButton = driver.findElement(new By.ByXPath("//span[.=' " + day + " ']/.."));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(dayButton));
        dayButton.click();
        wait.until(driver1 -> dayButton.getAttribute("class").contains("mat-calendar-body-active"));
    }

    private void pickDate(LocalDate date) {
        pickYear(date.getYear());
        pickMonth(date.getMonth().getDisplayName(TextStyle.SHORT, Locale.US));
        pickDay(date.getDayOfMonth());
    }

    public void deleteRange(LocalDate from, LocalDate to) {
        pickDate(from);
        pickDate(to);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(deleteAvailabilityButton));
        deleteAvailabilityButton.click();
        wait.until(driver1 -> isDateSelectionClear());
    }

    public void addEntry(PropertyAvailabilityEntry entry) {
        pickDate(entry.getDate());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(priceInput));
        priceInput.sendKeys(Double.toString(entry.getPrice()));
        wait.until(ExpectedConditions.textToBePresentInElementValue(priceInput, Double.toString(entry.getPrice())));
        wait.until(ExpectedConditions.elementToBeClickable(changeAvailabilityButton));
        changeAvailabilityButton.click();
        wait.until(driver1 -> isDateSelectionClear());
    }

    public void addEntries(List<PropertyAvailabilityEntry> entries) {
        for (PropertyAvailabilityEntry entry: entries) {
            this.addEntry(entry);
        }
    }

    private boolean isDateSelectionClear() {
        return calendarBody.getAttribute("ng-reflect-start-value") == null && calendarBody.getAttribute("ng-reflect-end-value") == null;
    }

    public void save() {
        waitAndClick(saveButton);
        waitUntilVisible(successDialogContainer);
        waitAndClick(dialogOkButton);
    }

    public String causeSaveError() {
        waitAndClick(saveButton);
        return "";
    }

    private void waitAndClick(WebElement button) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    private void waitUntilVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
