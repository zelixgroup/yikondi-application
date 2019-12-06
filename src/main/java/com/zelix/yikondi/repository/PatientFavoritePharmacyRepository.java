package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientFavoritePharmacy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientFavoritePharmacy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientFavoritePharmacyRepository extends JpaRepository<PatientFavoritePharmacy, Long> {

}
