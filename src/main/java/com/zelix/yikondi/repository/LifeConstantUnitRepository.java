package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.LifeConstantUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LifeConstantUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LifeConstantUnitRepository extends JpaRepository<LifeConstantUnit, Long> {

}
