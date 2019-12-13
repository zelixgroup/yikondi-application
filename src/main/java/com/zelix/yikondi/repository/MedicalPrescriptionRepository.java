package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.MedicalPrescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalPrescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalPrescriptionRepository extends JpaRepository<MedicalPrescription, Long> {

}
