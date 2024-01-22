package com.lunark.lunark.e2e.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePage {
    private final WebDriver driver;

    private static final String URL = "http://localhost:4200/";

    private static final Duration DURATION = Duration.ofSeconds(10);

    @FindBy(xpath = "//input[@name=\"location\"]")
    private WebElement locationInput;

    @FindBy(xpath = "//i[@class=\'at-account\']")
    private WebElement loginButton;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(xpath = "//span[normalize-space()=\'Sign in\']" )
    private WebElement buttonSignIn;

    @FindBy(xpath = "//i[@class='cil-check']")
    private WebElement reservationsButton;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@name=\"guestNumber\"]")
    private WebElement guestNumberInput;

    @FindBy(css = "mat-form-field.mat-mdc-form-field-type-mat-date-range-input .mat-mdc-button-touch-target")
    private WebElement datePickerIcon;

    @FindBy(xpath = "//mat-calendar//span[contains(concat(' ', normalize-space(@class), ' '), 'mat-calendar-body-cell-content')][contains(text(), '20')]")
    private WebElement startDate;

    @FindBy(xpath = "//mat-calendar//span[contains(concat(' ', normalize-space(@class), ' '), 'mat-calendar-body-cell-content')][contains(text(), '30')]")
    private WebElement endDate;

    @FindBy(xpath = "//button[@mat-mini-fab]")
    private WebElement filtersButton;

    @FindBy(xpath = "//button[@color='primary']")
    private WebElement searchButton;

    @FindBy(css = "mat-calendar")
    private WebElement calendar;

    @FindBy(css = "app-filter-dialog")
    private WebElement filterDialog;

    @FindBy(css = "app-filter-dialog input[name=minPrice]")
    private WebElement minimumPriceInput;

    @FindBy(css = "app-filter-dialog input[name=maxPrice]")
    private WebElement maximumPriceInput;

    @FindBy(css = "mat-card:first-child")
    private WebElement card;

    @FindBy(css = "mat-card:first-child mat-card-title")
    private WebElement cardTitle;

    @FindBy(xpath = "mat-card")
    private List<WebElement> cards;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, DURATION);
    }

    public void enterLocation(String value) {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(locationInput)).sendKeys(value);
    }

    public void enterGuestNumber(String value) {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(guestNumberInput)).sendKeys(value);
    }

    public void enterDate() {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(datePickerIcon)).click();
        wait.until(ExpectedConditions.visibilityOf(calendar));
        startDate.click();
        endDate.click();
    }

    public void clickSearch() {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(searchButton)).click();
    }

    public void clickCard() {
        WebDriverWait wait = getWait();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.visibilityOf(card)).click();
    }

    public boolean isCardPresent() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return card.isDisplayed();
    }


    public void loginWithCredentials(String email, String password) {
        WebDriverWait wait = getWait();

        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(buttonSignIn)).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void clickReservations() {
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement check = wait.until(ExpectedConditions.visibilityOf(reservationsButton));
        check.click();
    }

    public boolean areCardsEmpty() {
        return cards.isEmpty();
    }

    public String getCardTitle() {
        return cardTitle.getText();
    }

}
