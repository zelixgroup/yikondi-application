package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.DoctorWorkingSlot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DoctorWorkingSlot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorWorkingSlotRepository extends JpaRepository<DoctorWorkingSlot, Long> {

}
