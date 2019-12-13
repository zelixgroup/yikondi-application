package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.InsuranceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InsuranceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {

}
