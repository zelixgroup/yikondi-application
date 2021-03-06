package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.HealthCentreDoctor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HealthCentreDoctor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthCentreDoctorRepository extends JpaRepository<HealthCentreDoctor, Long> {

}
