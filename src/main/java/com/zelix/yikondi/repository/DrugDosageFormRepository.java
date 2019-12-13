package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.DrugDosageForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DrugDosageForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugDosageFormRepository extends JpaRepository<DrugDosageForm, Long> {

}
