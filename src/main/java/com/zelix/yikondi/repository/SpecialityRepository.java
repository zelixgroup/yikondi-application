package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.Speciality;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Speciality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

}
