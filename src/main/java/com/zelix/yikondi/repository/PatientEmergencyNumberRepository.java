package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientEmergencyNumber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientEmergencyNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientEmergencyNumberRepository extends JpaRepository<PatientEmergencyNumber, Long> {

}
