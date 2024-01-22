package com.lunark.lunark.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReservationsPage {
    private final WebDriver driver;

    @FindBy(xpath = "//h1[normalize-space()='your reservations']")
    private WebElement header;

    @FindBy(xpath = "//mat-card")
    private List<WebElement> cards;

    @FindBy(id="mat-select-value-1")
    private WebElement statusDropDown;

    @FindBy(xpath = "//span[normalize-space()='ACCEPTED']")
    private WebElement acceptedStatusOption;

    @FindBy(xpath = "//*[@id=\"container\"]/app-reservation-search/form/button")
    private WebElement submitButton;

    public ReservationsPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.textToBePresentInElement(header, "your reservations"));
    }


    public WebElement getHeader() {
        return header;
    }

    public WebElement getStatusDropDown() {
        return statusDropDown;
    }

    public WebElement getAcceptedStatusOption() {
        return acceptedStatusOption;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public List<WebElement> getCards() {
        return cards;
    }

    public void selectAcceptedReservations() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(statusDropDown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(acceptedStatusOption)).click();
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }


    public void cancelUncancellableReservation() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();

        for (WebElement element : cards) {
            System.out.println("PRINTUJEM ELEMENT");

            List<WebElement> dateElements = element.findElements(By.cssSelector("p.date-range"));
            String dateText = "";
            for (WebElement dateElement : dateElements) {
                if (dateElement.getText().contains("/")) {
                    dateText = dateElement.getText();
                    break;
                }
            }

            System.out.println(dateText);
            String[] dateRange = dateText.split(" - ");

            if (dateRange.length == 2) {
                String secondDateTxt = dateRange[1].trim();
                Date secondDate = dateFormat.parse(secondDateTxt);
                if (secondDate.before(currentDate)) {
                    WebElement button = element.findElement(By.cssSelector(".approve-button"));
                    button.click();
                }
            }
        }
    }


    public void cancelCancellableReservation() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date(); // Get today's date

        for (WebElement element : cards) {
            List<WebElement> dateElements = element.findElements(By.cssSelector("p.date-range"));
            String dateText = "";
            for (WebElement dateElement : dateElements) {
                if (dateElement.getText().contains("/")) {
                    dateText = dateElement.getText();
                    break;
                }
            }

            System.out.println(dateText);
            String[] dateRange = dateText.split(" - ");

            if (dateRange.length == 2) {
                String secondDateTxt = dateRange[1].trim();
                System.out.println(secondDateTxt);
                Date secondDate = dateFormat.parse(secondDateTxt);
                if (secondDate.after(currentDate)) {
                    WebElement button = element.findElement(By.cssSelector(".approve-button"));
                    button.click();
                    return; // Exit after clicking the first cancellable reservation
                }
            }
        }
    }


}
