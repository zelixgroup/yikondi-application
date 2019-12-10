package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.LifeConstant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LifeConstant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LifeConstantRepository extends JpaRepository<LifeConstant, Long> {

}
