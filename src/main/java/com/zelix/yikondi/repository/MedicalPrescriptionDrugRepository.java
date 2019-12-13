package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.MedicalPrescriptionDrug;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalPrescriptionDrug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalPrescriptionDrugRepository extends JpaRepository<MedicalPrescriptionDrug, Long> {

}
