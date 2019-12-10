package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientPathology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientPathology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientPathologyRepository extends JpaRepository<PatientPathology, Long> {

}
