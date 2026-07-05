package com.careerpilot.repository;

import com.careerpilot.entity.AIRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIRequestLogRepository extends JpaRepository<AIRequestLog, Long> {

    /** Most-recent first — used by admin or analytics in future parts. */
    List<AIRequestLog> findByUserIdOrderByCreatedAtDesc(Long userId);

    /** Total count of AI calls made by a specific user. */
    long countByUserId(Long userId);
}
