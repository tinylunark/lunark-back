package com.lunark.lunark.reservations.specification;

import com.lunark.lunark.reservations.dto.ReservationSearchDto;
import com.lunark.lunark.reservations.model.Reservation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification implements Specification<Reservation> {
    private final ReservationSearchDto filter;
    private final boolean isHost;

    public ReservationSpecification(ReservationSearchDto filter, boolean isHost) {
        this.filter = filter;
        this.isHost = isHost;
    }


    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getPropertyName() != null) {
            String pattern = '%' + filter.getPropertyName() + '%';
            Predicate propertyNamePredicate = criteriaBuilder.like(root.get("property").get("name"), pattern);

            predicate = criteriaBuilder.and(predicate, propertyNamePredicate);
        }

        if (filter.getStartDate() != null) {
            Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), filter.getStartDate());

            predicate = criteriaBuilder.and(predicate, startDatePredicate);
        }

        if (filter.getEndDate() != null) {
            Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), filter.getEndDate());

            predicate = criteriaBuilder.and(predicate, endDatePredicate);
        }

        if (filter.getStatus() != null) {
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), filter.getStatus());

            predicate = criteriaBuilder.and(predicate, statusPredicate);
        }

        if (filter.getAccountId() != null) {
            Predicate accountIdPredicate;
            if (isHost) {
                accountIdPredicate = criteriaBuilder.equal(root.get("property").get("host").get("id"), filter.getAccountId());
            } else {
                accountIdPredicate = criteriaBuilder.equal(root.get("guest").get("id"), filter.getAccountId());
            }

            predicate = criteriaBuilder.and(predicate, accountIdPredicate);
        }

        return predicate;
    }
}
