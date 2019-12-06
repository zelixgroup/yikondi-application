package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.HealthCentre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HealthCentre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthCentreRepository extends JpaRepository<HealthCentre, Long> {

}
