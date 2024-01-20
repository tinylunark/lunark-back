package com.lunark.lunark.properties.specification;

import com.lunark.lunark.amenities.model.Amenity;
import com.lunark.lunark.properties.dto.PropertySearchDto;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.properties.model.PropertyAvailabilityEntry;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PropertySpecification implements Specification<Property> {

    private final PropertySearchDto filter;

    public PropertySpecification(PropertySearchDto filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Property> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getAddress() != null) {
            Expression<String> fullAddress = criteriaBuilder.concat(
                    root.get("address").get("street"),
                    criteriaBuilder.concat(
                            criteriaBuilder.literal(", "),
                            criteriaBuilder.concat(
                                    root.get("address").get("city"),
                                    criteriaBuilder.concat(
                                            criteriaBuilder.literal(", "),
                                            root.get("address").get("country")
                                    )
                            )
                    )
            );

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(fullAddress, "%" + filter.getAddress() + "%"));
        }

        if (filter.getGuestNumber() != null) {
            Predicate guestNumberPredicate = criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("maxGuests"), filter.getGuestNumber()),
                    criteriaBuilder.lessThanOrEqualTo(root.get("minGuests"), filter.getGuestNumber())
            );
            predicate = criteriaBuilder.and(predicate, guestNumberPredicate);
        }

        if (filter.getStartDate() != null) {
            Subquery<LocalDate> subquery = query.subquery(LocalDate.class);
            Root<Property> subRoot = subquery.correlate(root);
            Join<Property, PropertyAvailabilityEntry> entryJoin = subRoot.join("availabilityEntries");
            subquery.select(entryJoin.get("date"));
            subquery.where(criteriaBuilder.greaterThanOrEqualTo(entryJoin.get("date"), filter.getStartDate()));

            Predicate startDatePredicate = criteriaBuilder.exists(subquery);
            predicate = criteriaBuilder.and(predicate, startDatePredicate);
        }

        if (filter.getEndDate() != null) {
            Subquery<LocalDate> subquery = query.subquery(LocalDate.class);
            Root<Property> subRoot = subquery.correlate(root);
            Join<Property, PropertyAvailabilityEntry> entryJoin = subRoot.join("availabilityEntries");
            subquery.select(entryJoin.get("date"));
            subquery.where(criteriaBuilder.lessThanOrEqualTo(entryJoin.get("date"), filter.getEndDate()));

            Predicate endDatePredicate = criteriaBuilder.exists(subquery);
            predicate = criteriaBuilder.and(predicate, endDatePredicate);
        }

        if (filter.getType() != null) {
            Predicate typePredicate = criteriaBuilder.equal(root.get("type"), filter.getType());
            predicate = criteriaBuilder.and(predicate, typePredicate);
        }

        if (filter.getMinPrice() != null) {
            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Property> subRoot = subquery.correlate(root);
            Join<Property, PropertyAvailabilityEntry> entryJoin = subRoot.join("availabilityEntries");
            subquery.select(entryJoin.get("price"));
            subquery.where(criteriaBuilder.greaterThanOrEqualTo(entryJoin.get("price"), filter.getMinPrice()));

            Predicate minPricePredicate = criteriaBuilder.exists(subquery);
            predicate = criteriaBuilder.and(predicate, minPricePredicate);
        }

        if (filter.getMaxPrice() != null) {
            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Property> subRoot = subquery.correlate(root);
            Join<Property, PropertyAvailabilityEntry> entryJoin = subRoot.join("availabilityEntries");
            subquery.select(entryJoin.get("price"));
            subquery.where(criteriaBuilder.lessThanOrEqualTo(entryJoin.get("price"), filter.getMaxPrice()));

            Predicate maxPricePredicate = criteriaBuilder.exists(subquery);
            predicate = criteriaBuilder.and(predicate, maxPricePredicate);
        }

        if (filter.getAmenityIds() != null) {
            Subquery<Amenity> subquery = query.subquery(Amenity.class);
            Root<Property> subRoot = subquery.correlate(root);
            Join<Property, Amenity> amenityJoin = subRoot.join("amenities");
            subquery.select(amenityJoin.get("id"));
            subquery.where(criteriaBuilder.in(amenityJoin.get("id")).value(filter.getAmenityIds()));

            Predicate amenitiesPredicate = criteriaBuilder.exists(subquery);
            predicate = criteriaBuilder.and(predicate, amenitiesPredicate);
        }

        if (filter.getApproved() != null) {
            Predicate typePredicate = criteriaBuilder.equal(root.get("approved"), filter.getApproved());
            predicate = criteriaBuilder.and(predicate, typePredicate);
        }

        Order order;
        Subquery<LocalDate> subquery = query.subquery(LocalDate.class);
        Root<Property> subRoot = subquery.correlate(root);
        Join<Property, PropertyAvailabilityEntry> entryJoin = subRoot.join("availabilityEntries");
        subquery.select(entryJoin.get("date"));
        if (filter.getSort() == null || filter.getSort().equals("ASC")) {
            order = criteriaBuilder.asc(subquery);
        } else {
            order = criteriaBuilder.desc(subquery);
        }
        query.orderBy(order);

        return predicate;
    }

}
