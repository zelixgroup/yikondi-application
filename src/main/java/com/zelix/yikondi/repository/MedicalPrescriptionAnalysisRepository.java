package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.MedicalPrescriptionAnalysis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalPrescriptionAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalPrescriptionAnalysisRepository extends JpaRepository<MedicalPrescriptionAnalysis, Long> {

}
