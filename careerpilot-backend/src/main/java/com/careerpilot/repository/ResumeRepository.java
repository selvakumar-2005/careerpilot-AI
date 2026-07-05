package com.careerpilot.repository;

import com.careerpilot.entity.Resume;
import com.careerpilot.entity.ResumeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUserIdAndStatusNotOrderByUploadDateDesc(Long userId, ResumeStatus status);

    Optional<Resume> findByIdAndUserId(Long id, Long userId);

    long countByUserIdAndStatusNot(Long userId, ResumeStatus status);
}
