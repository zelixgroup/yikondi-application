package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.EmergencyAmbulance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmergencyAmbulance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmergencyAmbulanceRepository extends JpaRepository<EmergencyAmbulance, Long> {

}
