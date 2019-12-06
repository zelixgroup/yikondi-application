package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientAppointement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientAppointement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientAppointementRepository extends JpaRepository<PatientAppointement, Long> {

}
