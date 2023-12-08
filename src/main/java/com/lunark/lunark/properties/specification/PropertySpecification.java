package com.lunark.lunark.properties.specification;

import com.lunark.lunark.properties.dto.PropertySearchDto;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;

public class PropertySpecification implements Specification<Property> {

    private PropertySearchDto filter;

    public PropertySpecification(PropertySearchDto filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.disjunction();

        if (filter.getAddress() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.equal(root.get("address"), filter.getAddress()));
        }

        if (filter.getGuestNumber() > 0) {
            predicate.getExpressions()
                    .add(criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("maxGuests"), filter.getGuestNumber()),
                            criteriaBuilder.lessThanOrEqualTo(root.get("minGuests"), filter.getGuestNumber()))
                    );
        }

        if (filter.getStartDate() != null) {
            Join<PropertyAvailabilityEntry, LocalDate> dateJoin = root.join("availabilityEntries");

            predicate.getExpressions()
                    .add(criteriaBuilder.greaterThanOrEqualTo(dateJoin, filter.getStartDate()));
        }

        if (filter.getEndDate() != null) {
            Join<PropertyAvailabilityEntry, LocalDate> dateJoin = root.join("availabilityEntries");

            predicate.getExpressions()
                    .add(criteriaBuilder.lessThanOrEqualTo(dateJoin, filter.getEndDate()));
        }

        if (filter.getType() != null) {
            predicate.getExpressions()
                    .add(criteriaBuilder.equal(root.get("type"), filter.getType()));
        }

        if (filter.getMinPrice() > 0) {
            Join<PropertyAvailabilityEntry, Double> priceJoin = root.join("availabilityEntries");

            predicate.getExpressions()
                    .add(criteriaBuilder.greaterThanOrEqualTo(priceJoin, filter.getMinPrice()));
        }

        if (filter.getMinPrice() > 0) {
            Join<PropertyAvailabilityEntry, Double> priceJoin = root.join("availabilityEntries");

            predicate.getExpressions()
                    .add(criteriaBuilder.lessThanOrEqualTo(priceJoin, filter.getMinPrice()));
        }

        if (filter.getAmenityIds() != null) {
            predicate.getExpressions()
                    .add(root.get("amenities").in(filter.getAmenityIds())); // TODO: find a way to make this work
        }

        return predicate;
    }
}
