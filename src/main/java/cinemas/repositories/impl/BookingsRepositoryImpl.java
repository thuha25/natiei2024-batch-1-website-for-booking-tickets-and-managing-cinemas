package cinemas.repositories.impl;

import cinemas.dtos.Pageable;
import cinemas.enums.BookingStatusEnum;
import cinemas.models.Booking;
import cinemas.models.Screen;
import cinemas.models.Showtime;
import cinemas.models.Theater;
import cinemas.repositories.BookingsRepository;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository("bookingsRepository")
public class BookingsRepositoryImpl extends BaseRepositoryImpl<Booking, Integer> implements BookingsRepository {
    public BookingsRepositoryImpl() {
        super(Booking.class);
    }

    @Override
    public List<Booking> findPrintedBookingsSince(ZonedDateTime startDate) {
        return findPrintedBookingsSince(startDate, null);
    }

    @Override
    public List<Booking> findPrintedBookingsSince(ZonedDateTime startDate, Integer theaterId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
        Root<Booking> booking = cq.from(Booking.class);
        Join<Booking, Showtime> showtime = booking.join("showtime");
        Join<Showtime, Screen> screen = showtime.join("screen");
        Join<Screen, Theater> theater = screen.join("theater");

        // Base predicates
        Predicate statusPredicate = cb.equal(booking.get("status"), BookingStatusEnum.PRINTED); // Assuming status 1 means printed

        // Conditional date predicate
        Predicate datePredicate = cb.conjunction(); // Default to true
        if (startDate != null) {
            ZonedDateTime startOfStartDate = startDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
            datePredicate = cb.greaterThanOrEqualTo(booking.get("createdAt"), startOfStartDate);
        }

        // Conditional theater predicate
        Predicate theaterPredicate = cb.conjunction(); // Default to true
        if (theaterId != null) {
            theaterPredicate = cb.equal(theater.get("id"), theaterId);
        }

        // Combine predicates
        cq.where(cb.and(datePredicate, statusPredicate, theaterPredicate));

        TypedQuery<Booking> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    @Override
    public Booking findBookingByUser(int userId, int bookingId){
        String hql = "FROM Booking b WHERE b.id = :bookingId AND b.customer.id = :userId";
        Query query = entityManager.createQuery(hql, Booking.class);
        query.setParameter("bookingId", bookingId);
        query.setParameter("userId", userId);

        return (Booking) query.getSingleResult();
    }
    @Override
    public List<Booking> findBookingsByUserAndStatusWithCreatedDesc(int userId, BookingStatusEnum[] statusEnums, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> bookingRoot = criteriaQuery.from(Booking.class);

        // Build the base query
        CriteriaQuery<Booking> query = criteriaQuery.select(bookingRoot);

        // Create predicates
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(bookingRoot.get("customer").get("id"), userId));

        if (statusEnums != null && statusEnums.length > 0) {
            CriteriaBuilder.In<BookingStatusEnum> statusPredicate = criteriaBuilder.in(bookingRoot.get("status"));
            for (BookingStatusEnum statusEnum : statusEnums) {
                statusPredicate.value(statusEnum);
            }
            predicates.add(statusPredicate);
        }

        // Add predicates to the query
        query.where(predicates.toArray(new Predicate[0]));

        // Order by creation date descending
        query.orderBy(criteriaBuilder.desc(bookingRoot.get("createdAt")));

        // Create TypedQuery and apply pagination
        TypedQuery<Booking> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getOffset());
        typedQuery.setMaxResults(pageable.getSize());

        // Execute the query and get results
        return typedQuery.getResultList();
    }

    @Override
    public Integer countBookingsByUserAndStatus(int userId, BookingStatusEnum[] statusEnums) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Booking> bookingRoot = criteriaQuery.from(Booking.class);

        // Build the base query
        CriteriaQuery<Long> query = criteriaQuery.select(criteriaBuilder.count(bookingRoot));

        // Create predicates
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(bookingRoot.get("customer").get("id"), userId));

        if (statusEnums != null && statusEnums.length > 0) {
            CriteriaBuilder.In<BookingStatusEnum> statusPredicate = criteriaBuilder.in(bookingRoot.get("status"));
            for (BookingStatusEnum statusEnum : statusEnums) {
                statusPredicate.value(statusEnum);
            }
            predicates.add(statusPredicate);
        }

        // Add predicates to the query
        query.where(predicates.toArray(new Predicate[0]));

        // Create TypedQuery and execute the query
        return entityManager.createQuery(query).getSingleResult().intValue();
    }

    public List<Booking> getBookingsByIdOrCustomerNameWithCreatedDesc(String keyword, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
        Root<Booking> booking = cq.from(Booking.class);

        Predicate idPredicate = cb.like(cb.toString(booking.get("id")), "%" + keyword + "%");
        Predicate namePredicate = cb.like(booking.get("customerName"), "%" + keyword + "%");
        cq.where(cb.or(idPredicate, namePredicate));
        cq.orderBy(cb.desc(booking.get("createdAt")));

        TypedQuery<Booking> query = entityManager.createQuery(cq);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getSize());

        return query.getResultList();
    }

    @Override
    public Integer countBookingsByIdOrCustomerName(String keyword) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Booking> booking = cq.from(Booking.class);

        Predicate idPredicate = cb.like(cb.toString(booking.get("id")), "%" + keyword + "%");
        Predicate namePredicate = cb.like(booking.get("customerName"), "%" + keyword + "%");
        cq.select(cb.count(booking)).where(cb.or(idPredicate, namePredicate));
        TypedQuery<Long> query = entityManager.createQuery(cq);
        return Math.toIntExact(query.getSingleResult());
    }
}
