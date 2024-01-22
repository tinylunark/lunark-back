package com.lunark.lunark.e2e.tests;
import com.lunark.lunark.e2e.TestBase;
import com.lunark.lunark.e2e.pages.HomePage;
import com.lunark.lunark.e2e.pages.ReservationsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.time.Duration;

public class ReservationTest extends TestBase {
    @Test
    public void guestCancelUncancellableReservationTest() throws InterruptedException, ParseException {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.loginWithCredentials("user1@example.com", "password1");
        homePage.clickReservations();

        ReservationsPage reservationsPage = new ReservationsPage(driver);
        wait.until(ExpectedConditions.textToBePresentInElement(reservationsPage.getHeader(), "your reservations"));

        Assertions.assertTrue(reservationsPage.isPageOpened());
        reservationsPage.selectAcceptedReservations();

        int currentSize = reservationsPage.getCards().size();
        reservationsPage.cancelUncancellableReservation();
        int sizeAfterCancellation = reservationsPage.getCards().size();
        Assertions.assertEquals(currentSize, sizeAfterCancellation);
    }

    @Test
    public void guestCancelCancellableResravtionTest() throws ParseException {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.loginWithCredentials("user1@example.com", "password1");
        homePage.clickReservations();

        ReservationsPage reservationsPage = new ReservationsPage(driver);
        wait.until(ExpectedConditions.textToBePresentInElement(reservationsPage.getHeader(), "your reservations"));

        Assertions.assertTrue(reservationsPage.isPageOpened());

        reservationsPage.selectAcceptedReservations();
        int currentSize = reservationsPage.getCards().size();
        Assertions.assertTrue(currentSize > 0, "There should be at least one reservation to cancel.");

        reservationsPage.cancelCancellableReservation();
        reservationsPage.selectAcceptedReservations();

        // Add a wait to ensure the page has updated
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.className("mat-card"), currentSize));

        int sizeAfterCancellation = reservationsPage.getCards().size();
        Assertions.assertNotEquals(currentSize, sizeAfterCancellation);
    }
}
