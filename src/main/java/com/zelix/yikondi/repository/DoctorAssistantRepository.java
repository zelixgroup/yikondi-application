package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.DoctorAssistant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DoctorAssistant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorAssistantRepository extends JpaRepository<DoctorAssistant, Long> {

}
