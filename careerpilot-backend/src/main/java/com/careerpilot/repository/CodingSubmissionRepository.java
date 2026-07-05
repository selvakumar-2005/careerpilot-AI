package com.careerpilot.repository;

import com.careerpilot.entity.CodingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, Long> {

    List<CodingSubmission> findByUserIdOrderBySubmittedAtDesc(Long userId);

    List<CodingSubmission> findByUserIdAndQuestionIdOrderBySubmittedAtDesc(Long userId, Long questionId);

    Optional<CodingSubmission> findTopByUserIdAndQuestionIdOrderBySubmittedAtDesc(Long userId, Long questionId);

    long countByUserId(Long userId);

    @Query("SELECT COUNT(DISTINCT s.question.id) FROM CodingSubmission s WHERE s.user.id = :userId")
    long countDistinctQuestionsByUserId(@Param("userId") Long userId);

    List<CodingSubmission> findTop5ByUserIdOrderBySubmittedAtDesc(Long userId);
}
