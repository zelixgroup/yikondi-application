package com.zelix.yikondi.repository;
import com.zelix.yikondi.domain.FamilyRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FamilyRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, Long> {

}
