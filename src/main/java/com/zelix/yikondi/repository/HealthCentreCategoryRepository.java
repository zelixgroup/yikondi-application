package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.HealthCentreCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HealthCentreCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthCentreCategoryRepository extends JpaRepository<HealthCentreCategory, Long> {

}
