package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientLifeConstant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientLifeConstant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientLifeConstantRepository extends JpaRepository<PatientLifeConstant, Long> {

}
