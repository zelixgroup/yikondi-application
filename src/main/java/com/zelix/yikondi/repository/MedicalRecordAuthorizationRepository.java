package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.MedicalRecordAuthorization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicalRecordAuthorization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalRecordAuthorizationRepository extends JpaRepository<MedicalRecordAuthorization, Long> {

}
