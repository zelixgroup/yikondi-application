package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.Pharmacy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pharmacy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
