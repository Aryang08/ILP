package com.tcs.ilp.servease.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.dto.CustomerFeedback;

@Repository
public class CustomerFeedbackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String BASE_SQL = """
        SELECT
            u.name AS customer_name,
            sc.name AS service_center_name,
            f.rating,
            f.comment,
            f.created_at AS feedback_date
        FROM dev.feedback f
        JOIN dev.customer_login c ON f.customer_id = c.customer_id
        JOIN dev."user" u ON c.user_id = u.user_id
        JOIN dev."service" s ON f.service_id = s.service_id
        JOIN dev.service_center sc ON s.service_center_id = sc.service_center_id
        """;

    // ✅ All feedbacks
    public List<CustomerFeedback> getAllFeedbacks(int page, int size) {
        return execute(BASE_SQL + " ORDER BY f.created_at DESC", page, size);
    }

    // ✅ Feedback by service center
    public List<CustomerFeedback> getFeedbackByServiceCenter(
            String serviceCenter, int page, int size) {

        String sql =
            BASE_SQL +
            " WHERE sc.name = :name ORDER BY f.created_at DESC";

        return execute(sql, page, size, "name", serviceCenter);
    }

    // ✅ Average rating
    public double getAverageRatingByServiceCenter(String serviceCenter) {

        String sql = """
            SELECT AVG(f.rating)
            FROM dev.feedback f
            JOIN dev."service" s ON f.service_id = s.service_id
            JOIN dev.service_center sc ON s.service_center_id = sc.service_center_id
            WHERE sc.name = :name
        """;

        Double avg = (Double) entityManager
                .createNativeQuery(sql)
                .setParameter("name", serviceCenter)
                .getSingleResult();

        return avg != null ? avg : 0.0;
    }

    // ✅ Sort ASC
    public List<CustomerFeedback> getSortedByRatingAsc(int page, int size) {
        return execute(BASE_SQL + " ORDER BY f.rating ASC", page, size);
    }

    // ✅ Sort DESC
    public List<CustomerFeedback> getSortedByRatingDesc(int page, int size) {
        return execute(BASE_SQL + " ORDER BY f.rating DESC", page, size);
    }

    // 🔹 Internal helper
    private List<CustomerFeedback> execute(
            String sql, int page, int size, Object... params) {

        int offset = page * size;

        Query query = entityManager.createNativeQuery(
            sql + " LIMIT :size OFFSET :offset"
        );

        query.setParameter("size", size);
        query.setParameter("offset", offset);

        if (params.length == 2) {
            query.setParameter(params[0].toString(), params[1]);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(r -> new CustomerFeedback(
                        (String) r[0],
                        (String) r[1],
                        ((Number) r[2]).intValue(),
                        (String) r[3],
                        r[4] != null ? r[4].toString() : null
                ))
                .toList();
    }
}