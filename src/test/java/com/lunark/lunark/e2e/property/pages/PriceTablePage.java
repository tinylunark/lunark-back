package com.lunark.lunark.e2e.property.pages;

import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PriceTablePage {
    private WebDriver driver;

    @FindBy(css = "tbody")
    private WebElement priceTable;
    @FindBy(css = "tbody tr")
    private List<WebElement> rows;

    public PriceTablePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<AvailabilityTableRow> getCurrentlyDisplayedRows() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(priceTable));

        List<AvailabilityTableRow> rows = new ArrayList<>();

        for (WebElement row: this.rows) {
            rows.add(getAvailabilityTableRow(row));
        }

        return rows;
    }

    public AvailabilityTableRow getAvailabilityTableRow(WebElement row) {
        LocalDate from = LocalDate.parse(row.findElement(By.cssSelector("td:nth-child(1)")).getText());
        LocalDate to = LocalDate.parse(row.findElement(By.cssSelector("td:nth-child(2)")).getText());
        Double price = Double.parseDouble(row.findElement(By.cssSelector("td:nth-child(3)")).getText());
        return new AvailabilityTableRow(from, to, price);
    }

    public List<AvailabilityTableRow> convertAvailabilityEntriesToRows(List<PropertyAvailabilityEntry> entries) {
        if (entries.size() == 0) {
            return new ArrayList<>();
        }

        Collections.sort(entries, Comparator.comparing(e -> e.getDate()));

        List<AvailabilityTableRow> rows = new ArrayList<>();

        AvailabilityTableRow currentRow = new AvailabilityTableRow(entries.get(0).getDate(), entries.get(0).getDate(), entries.get(0).getPrice());

        for (int i = 1; i < entries.size(); i++) {
            PropertyAvailabilityEntry entry = entries.get(i);

            boolean samePrice = currentRow.getPrice().equals(entry.getPrice());

            boolean within24Hours = Math.abs(ChronoUnit.DAYS.between(currentRow.getTo(), entry.getDate())) <= 1;

            if (samePrice && within24Hours) {
                currentRow.setTo(entry.getDate());
            } else {
                rows.add(currentRow);
                currentRow = new AvailabilityTableRow(entry.getDate(), entry.getDate(), entry.getPrice());
            }
        }

        rows.add(currentRow);
        return rows;
    }

    public class AvailabilityTableRow {
        private LocalDate from;
        private LocalDate to;
        private Double price;

        public AvailabilityTableRow(LocalDate from, LocalDate to, Double price) {
            this.from = from;
            this.to = to;
            this.price = price;
        }

        public LocalDate getFrom() {
            return from;
        }

        public LocalDate getTo() {
            return to;
        }

        public Double getPrice() {
            return price;
        }

        public void setTo(LocalDate to) {
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AvailabilityTableRow that = (AvailabilityTableRow) o;
            return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(price, that.price);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, price);
        }
    }
}
