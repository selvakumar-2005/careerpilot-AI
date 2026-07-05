package com.careerpilot.repository;

import com.careerpilot.entity.CodingQuestion;
import com.careerpilot.entity.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, Long> {

    List<CodingQuestion> findByDifficultyOrderByTitleAsc(Difficulty difficulty);

    @Query("SELECT q FROM CodingQuestion q WHERE " +
           "(:difficulty IS NULL OR q.difficulty = :difficulty) AND " +
           "(:search IS NULL OR LOWER(q.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "   OR LOWER(q.topic) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<CodingQuestion> searchQuestions(
            @Param("difficulty") Difficulty difficulty,
            @Param("search") String search);

    long countByDifficulty(Difficulty difficulty);
}
