package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.Pathology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pathology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathologyRepository extends JpaRepository<Pathology, Long> {

}
