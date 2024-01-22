package com.lunark.lunark.e2e.tests;

import com.lunark.lunark.configuration.TestConfiguration;
import com.lunark.lunark.e2e.TestBase;
import com.lunark.lunark.e2e.pages.HomePage;
import com.lunark.lunark.e2e.pages.PropertyPage;
import com.lunark.lunark.properties.model.Property;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.Sleeper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
@ActiveProfiles("test")
@Sql("classpath:property-search-test-data.sql")
@Disabled // So it does not run on Github actions, where there is no frontend running
public class SearchPropertiesTest extends TestBase {

    @Test
    public void shouldFindByLocation() {
        HomePage homePage = new HomePage(driver);
        PropertyPage propertyPage = new PropertyPage(driver);

        final var value = "Novi Sad";
        homePage.enterLocation(value);
        homePage.clickSearch();
        homePage.clickCard();

        String location = propertyPage.getLocation();

        Assertions.assertTrue(location.contains(value));
    }

    @Test
    public void shouldFindByGuestNumber() {
        HomePage homePage = new HomePage(driver);
        PropertyPage propertyPage = new PropertyPage(driver);

        final var value = 8;
        homePage.enterGuestNumber(String.valueOf(value));
        homePage.clickSearch();
        homePage.clickCard();

        String minimumGuests = propertyPage.getMinimumGuests();
        String maximumGuests = propertyPage.getMaximumGuests();

        Assertions.assertTrue(value >= Integer.parseInt(minimumGuests));
        Assertions.assertTrue(value <= Integer.parseInt(maximumGuests));
    }

    @Test
    public void shouldFindByDate() {
        HomePage homePage = new HomePage(driver);

        Assertions.assertTrue(homePage.isCardPresent());
        Assertions.assertEquals("Lakefront Retreat", homePage.getCardTitle());
    }

    @Test
    void shouldNotFind() {
        HomePage homePage = new HomePage(driver);

        homePage.enterLocation("blablalbakllbaks");
        homePage.enterGuestNumber("10");
        homePage.clickSearch();

        Assertions.assertTrue(homePage.areCardsEmpty());
    }

    @Test
    void shouldFindByMultipleCriteria() {
        HomePage homePage = new HomePage(driver);
        PropertyPage propertyPage = new PropertyPage(driver);

        final var locationValue = "Novi Sad";
        final var guestValue = 8;

        homePage.enterLocation(locationValue);
        homePage.enterGuestNumber(String.valueOf(guestValue));
        homePage.clickSearch();
        homePage.clickCard();

        String location = propertyPage.getLocation();
        String minimumGuests = propertyPage.getMinimumGuests();
        String maximumGuests = propertyPage.getMaximumGuests();

        Assertions.assertTrue(guestValue >= Integer.parseInt(minimumGuests));
        Assertions.assertTrue(guestValue <= Integer.parseInt(maximumGuests));
        Assertions.assertTrue(location.contains(locationValue));
    }
}
