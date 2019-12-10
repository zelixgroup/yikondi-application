package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientInsuranceCoverage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientInsuranceCoverage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientInsuranceCoverageRepository extends JpaRepository<PatientInsuranceCoverage, Long> {

}
