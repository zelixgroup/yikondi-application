package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PharmacyAllNightPlanning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PharmacyAllNightPlanning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PharmacyAllNightPlanningRepository extends JpaRepository<PharmacyAllNightPlanning, Long> {

}
