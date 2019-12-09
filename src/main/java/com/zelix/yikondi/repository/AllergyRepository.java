package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.Allergy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Allergy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

}
