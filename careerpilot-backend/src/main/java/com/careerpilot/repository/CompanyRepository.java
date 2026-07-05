package com.careerpilot.repository;

import com.careerpilot.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByOrderByCompanyNameAsc();

    boolean existsByCompanyNameIgnoreCase(String companyName);
}
