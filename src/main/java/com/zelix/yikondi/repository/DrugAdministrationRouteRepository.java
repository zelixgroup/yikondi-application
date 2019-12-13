package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.DrugAdministrationRoute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DrugAdministrationRoute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugAdministrationRouteRepository extends JpaRepository<DrugAdministrationRoute, Long> {

}
