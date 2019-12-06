package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.PatientFavoriteDoctor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PatientFavoriteDoctor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientFavoriteDoctorRepository extends JpaRepository<PatientFavoriteDoctor, Long> {

}
