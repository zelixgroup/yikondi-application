package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.DoctorSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DoctorSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

}
