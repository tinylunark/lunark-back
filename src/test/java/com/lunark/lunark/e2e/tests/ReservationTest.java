package com.lunark.lunark.e2e.tests;
import com.lunark.lunark.e2e.TestBase;
import com.lunark.lunark.e2e.pages.HomePage;
import com.lunark.lunark.e2e.pages.ReservationsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.time.Duration;

@Disabled
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

        reservationsPage.selectAcceptedReservations();
        int sizeAfterCancellation = reservationsPage.getCards().size();
        Assertions.assertEquals(currentSize, sizeAfterCancellation);
        driver.quit();
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
        driver.quit();
    }

    @Test
    public void hostAcceptReservationTest() throws InterruptedException, ParseException {
        HomePage homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        homePage.loginWithCredentials("user2@example.com", "password2");
        homePage.clickReservations();

        ReservationsPage reservationsPage = new ReservationsPage(driver);
        wait.until(ExpectedConditions.textToBePresentInElement(reservationsPage.getHostHeader(), "incoming reservations"));

        Assertions.assertTrue(reservationsPage.isHostPageOpened());
        reservationsPage.selectPendingReservations();

        int currentSize = reservationsPage.getCards().size();
        reservationsPage.acceptReservation();
        Thread.sleep(1000);

        reservationsPage.selectPendingReservations();
        Thread.sleep(1000);

        int sizeAfterCancellation = reservationsPage.getCards().size();
        System.out.println(currentSize);
        System.out.println(sizeAfterCancellation);
        Assertions.assertNotEquals(currentSize, sizeAfterCancellation);
        driver.quit();
    }
}
