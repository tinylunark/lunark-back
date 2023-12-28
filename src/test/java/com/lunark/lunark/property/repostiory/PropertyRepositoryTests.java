package com.lunark.lunark.property.repostiory;

import com.lunark.lunark.properties.repostiory.IPropertyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PropertyRepositoryTests {
    @Autowired
    IPropertyRepository propertyRepository;

    @Test
    public void nonExistentPropertyAndNonExistentGuestShouldReturnNothing() {
        Assertions.assertTrue(propertyRepository.findPropertyReviewByGuest(1234L, 1234L).isEmpty());
    }

    @Test
    public void existentPropertyButGuestDidNotWriteReviewShouldReturnNothing() {
        Assertions.assertTrue(propertyRepository.findPropertyReviewByGuest(4L, 6L).isEmpty());
    }

    @Test
    public void guestWroteReviewForPropertyShouldReturnReview() {
        Assertions.assertTrue(propertyRepository.findPropertyReviewByGuest(4L, 1L).isPresent());
    }
}
