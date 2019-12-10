package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientAllergy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientAllergy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientAllergyRepository extends JpaRepository<PatientAllergy, Long> {

}
