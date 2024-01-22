package com.lunark.lunark.e2e.property.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestReservationsPage {
    private WebDriver driver;
    public static final String HEADING = "your reservations";

    @FindBy(css = "h1")
    WebElement pageHeading;
    @FindBy(css = "mat-card")
    List<WebElement> reservationCards;

    public GuestReservationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private boolean isLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeading));
            wait.until(driver1 -> pageHeading.getText().equals(HEADING));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public List<ReservationDisplayDto> getReservations() {
        if (!isLoaded()) {
            throw new RuntimeException("Guest reservation page should have been loaded");
        }
        ArrayList<ReservationDisplayDto> reservations = new ArrayList<>();
        for (WebElement reservationCard: reservationCards) {
            String propertyName = reservationCard.findElement(new By.ByCssSelector("h2")).getText();
            String dateRange = reservationCard.findElement(new By.ByCssSelector("p:nth-of-type(1)")).getText();
            String numberOfGuests = reservationCard.findElement(new By.ByCssSelector("p:nth-of-type(2)")).getText();
            String status = reservationCard.findElement(new By.ByCssSelector("p:nth-of-type(3)")).getText().replace("pending ", "");
            String price = reservationCard.findElement(new By.ByCssSelector("p:nth-of-type(4)")).getText();
            reservations.add(new ReservationDisplayDto(propertyName, numberOfGuests, dateRange, status, price));
        }

        return reservations;
    }

    public static class ReservationDisplayDto {
        private String propertyName;
        private String dateRange;
        private String status;
        private String numberOfGuests;
        private String price;

        public ReservationDisplayDto(String propertyName, String numberOfGuests, String dateRange, String status, String price) {
            this.propertyName = propertyName;
            this.dateRange = dateRange;
            this.numberOfGuests = numberOfGuests;
            this.status = status;
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReservationDisplayDto that = (ReservationDisplayDto) o;
            return Objects.equals(propertyName, that.propertyName) && Objects.equals(dateRange, that.dateRange) && Objects.equals(numberOfGuests, that.numberOfGuests) && Objects.equals(status, that.status) && Objects.equals(price, that.price);
        }

        @Override
        public int hashCode() {
            return Objects.hash(propertyName, dateRange, numberOfGuests, status, price);
        }
    }
}
